package carteDuJeu;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Carte {
    private final int m_largeur;
    private final int m_hauteur;
    private Case[][] m_cases;

    public Carte(int largeur, int hauteur) {
        this.m_largeur = largeur;
        this.m_hauteur = hauteur;
        this.m_cases = new Case[hauteur][largeur]; // [ligne][colonne]
        for (int y = 0; y < hauteur; y++) {
            for (int x = 0; x < largeur; x++) {
                m_cases[y][x] = new Case(x, y);
            }
        }
    }

    public int getLargeur() {
        return m_largeur;
    }

    public int getHauteur() {
        return m_hauteur;
    }

    /**
     * Trouve la case contenant un élément mobile
     */
    public Case getCase(ElementMobile element) {
        for (int y = 0; y < m_hauteur; y++) {
            for (int x = 0; x < m_largeur; x++) {
                if (m_cases[y][x].contient(element)) {
                    return m_cases[y][x];
                }
            }
        }
        throw new IllegalArgumentException("L'élément mobile n'est pas présent sur la carte.");
    }

    public boolean contientElement(ElementMobile element) {
        for (int y = 0; y < m_hauteur; y++) {
            for (int x = 0; x < m_largeur; x++) {
                if (m_cases[y][x].contient(element)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Trouve la position [x,y] d'un élément mobile
     */
    public int[] trouverPosition(ElementMobile element) {
        for (int y = 0; y < m_hauteur; y++) {
            for (int x = 0; x < m_largeur; x++) {
                if (m_cases[y][x].contient(element)) {
                    return new int[]{x, y};
                }
            }
        }
        return null;
    }

    public Case getCase(int x, int y) {
        if (x < 0 || x >= m_largeur || y < 0 || y >= m_hauteur) {
            throw new IndexOutOfBoundsException("Coordonnées en dehors de la carte");
        }
        return m_cases[y][x]; // attention à l'ordre
    }

    public void setCase(int x, int y, Case uneCase) {
        if (x < 0 || x >= m_largeur || y < 0 || y >= m_hauteur) {
            throw new IndexOutOfBoundsException("Coordonnées en dehors de la carte");
        }
        m_cases[y][x] = uneCase; // attention à l'ordre
    }

    public void ajouterContenu(int x, int y, ElementCarte element) {
        if (x < 0 || x >= m_largeur || y < 0 || y >= m_hauteur) {
            throw new IndexOutOfBoundsException("Coordonnées en dehors de la carte");
        }
        m_cases[y][x].ajouterContenu(element);
    }

    public boolean ajouterContenuAleatoire(ElementCarte element) {
        Random rand = new Random();
        int tentatives = 0;
        int maxTentatives = m_largeur * m_hauteur * 2; // Limite pour éviter boucle infinie

        try {
            while (tentatives < maxTentatives) {
                int x = rand.nextInt(m_largeur);
                int y = rand.nextInt(m_hauteur);
                if (m_cases[y][x].estVide()) {
                    m_cases[y][x].ajouterContenu(element);
                    return true;
                }
                tentatives++;
            }


            throw new Exception("Impossible d'ajouter l'élément : aucune case vide disponible.");
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return false;
    }

    /**
     * Vérifie si les coordonnées sont valides sur cette carte
     */
    public boolean coordonneesValides(int x, int y) {
        return x >= 0 && x < m_largeur && y >= 0 && y < m_hauteur;
    }

    /**
     * Vérifie si une case est accessible (pas obstacle, pas d'élément mobile)
     */
    public boolean estCaseAccessible(int x, int y) {
        Case caseCible = getCase(x, y);
        if (coordonneesValides(x, y)) {
            return caseCible.estAccessible();
        }
        return false;
    }

    /**
     * Déplace un élément d'une case à une autre sur la carte
     */
    public boolean deplacerElement(ElementMobile element, int xCible, int yCible) {
        // Trouver la case actuelle
        Case caseActuelle = null;
        try {
            caseActuelle = getCase(element);
        } catch (IllegalArgumentException e) {
            return false; // Élément pas sur la carte
        }

        // Vérifier que la destination est accessible
        if (!estCaseAccessible(xCible, yCible)) {
            return false;
        }

        // Effectuer le déplacement
        caseActuelle.retirerContenu(element);
        ajouterContenu(xCible, yCible, element);
        return true;
    }

    /**
     * Retourne les cases accessibles dans un rayon donné
     */
    public List<int[]> getCasesAccessibles(int xCentre, int yCentre, int rayon) {
        List<int[]> casesAccessibles = new ArrayList<>();

        for (int y = yCentre - rayon; y <= yCentre + rayon; y++) {
            for (int x = xCentre - rayon; x <= xCentre + rayon; x++) {
                if (coordonneesValides(x, y) && calculerDistance(xCentre, yCentre, x, y) <= rayon &&
                        estCaseAccessible(x, y)) {
                    casesAccessibles.add(new int[]{x, y});
                }
            }
        }

        return casesAccessibles;
    }

    /**
     * Calcule la distance entre deux points (distance de Chebyshev)
     */
    public static int calculerDistance(int x1, int y1, int x2, int y2) {
        return Math.max(Math.abs(x2 - x1), Math.abs(y2 - y1));
    }

    /**
     * Vérifie si deux positions sont à portée l'une de l'autre
     */
    public static boolean estAPortee(int x1, int y1, int x2, int y2, int portee) {
        return calculerDistance(x1, y1, x2, y2) <= portee;
    }

    /**
     * Parse une chaîne de coordonnées (ex: "A5") en coordonnées x,y
     */
    public int[] parseCoordonnees(String coordString) {
        if (coordString == null || coordString.length() < 2) {
            throw new IllegalArgumentException("Format de coordonnées incorrect.");
        }

        char colChar = coordString.charAt(0);
        if (colChar < 'A' || colChar > 'Z') {
            throw new IllegalArgumentException("La colonne doit être une lettre entre A et Z.");
        }

        int x = colChar - 'A';
        String ligneStr = coordString.substring(1);

        try {
            int y = Integer.parseInt(ligneStr) - 1;

            if (!coordonneesValides(x, y)) {
                throw new IllegalArgumentException("Coordonnées hors limites de la carte.");
            }

            return new int[]{x, y};
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Numéro de ligne invalide.");
        }
    }

    /**
     * Convertit des coordonnées x,y en chaîne (ex: A5)
     */
    public static String coordonneesToString(int x, int y) {
        if (x < 0 || x >= 26 || y < 0) {
            throw new IllegalArgumentException("Coordonnées invalides pour la conversion");
        }
        return (char) ('A' + x) + String.valueOf(y + 1);
    }

    public void afficher() {
        // Affiche les coordonnées X
        System.out.print("  ");
        for (int i = 0; i < m_largeur; i++) {
            System.out.printf("%4c", 'A' + i);
        }
        System.out.println();

        // Ligne supérieure du contour
        System.out.print("   ┌");
        for (int x = 0; x < m_largeur; x++) {
            System.out.print("────");
        }
        System.out.println("┐");

        for (int y = 0; y < m_hauteur; y++) {
            // Coordonnée Y
            System.out.printf("%2d │", y+1);

            // Contenu de la ligne
            for (int x = 0; x < m_largeur; x++) {
                System.out.print(m_cases[y][x].toString());
            }

            System.out.println("│");
        }

        // Ligne inférieure du contour
        System.out.print("   └");
        for (int x = 0; x < m_largeur; x++) {
            System.out.print("────");
        }
        System.out.println("┘");
    }

    public void genererObstaclesAleatoires(double tauxObstacle) {
        if (tauxObstacle < 0 || tauxObstacle > 1) {
            throw new IllegalArgumentException("Le taux d'obstacles doit être entre 0.0 et 1.0");
        }

        Random rand = new Random();
        for (int y = 0; y < m_hauteur; y++) {
            for (int x = 0; x < m_largeur; x++) {
                if (rand.nextDouble() < tauxObstacle) {
                    m_cases[y][x].setEstObstacle(true);
                }
            }
        }
    }
}