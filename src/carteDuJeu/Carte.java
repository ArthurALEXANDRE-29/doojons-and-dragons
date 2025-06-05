package carteDuJeu;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

/**
 * Représente une carte de jeu avec un système de coordonnées et des cases.
 * La carte utilise un système de coordonnées où (0,0) est en haut à gauche.
 */
public class Carte {
    private final int m_largeur;
    private final int m_hauteur;
    final Case[][] m_cases;
    private boolean m_carteParDefaut = false;

    /**
     * Constructeur de la carte.
     *
     * @param largeur la largeur de la carte (nombre de colonnes)
     * @param hauteur la hauteur de la carte (nombre de lignes)
     */
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

    /**
     * Retourne la largeur de la carte.
     *
     * @return la largeur en nombre de colonnes
     */
    public int getLargeur() {
        return m_largeur;
    }

    /**
     * Retourne la hauteur de la carte.
     *
     * @return la hauteur en nombre de lignes
     */
    public int getHauteur() {
        return m_hauteur;
    }

    /**
     * Trouve la case contenant un élément mobile.
     *
     * @param element l'élément mobile à rechercher
     * @return Optional contenant la case si trouvée, Optional.empty() sinon
     */
    public Optional<Case> getCase(ElementMobile element) {
        for (int y = 0; y < m_hauteur; y++) {
            for (int x = 0; x < m_largeur; x++) {
                if (m_cases[y][x].contient(element)) {
                    return Optional.of(m_cases[y][x]);
                }
            }
        }
        return Optional.empty();
    }

    /**
     * Vérifie si la carte contient un élément mobile spécifique.
     *
     * @param element l'élément mobile à rechercher
     * @return true si l'élément est présent sur la carte, false sinon
     */
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
     * Trouve la position [x,y] d'un élément mobile.
     *
     * @param element l'élément mobile à localiser
     * @return Optional contenant les coordonnées [x,y] si trouvées, Optional.empty() sinon
     */
    public Optional<int[]> trouverPosition(ElementMobile element) {
        for (int y = 0; y < m_hauteur; y++) {
            for (int x = 0; x < m_largeur; x++) {
                if (m_cases[y][x].contient(element)) {
                    return Optional.of(new int[]{x, y});
                }
            }
        }
        return Optional.empty();
    }

    /**
     * Retourne la case à la position spécifiée.
     *
     * @param x coordonnée x (colonne)
     * @param y coordonnée y (ligne)
     * @return la case à la position donnée
     * @throws IndexOutOfBoundsException si les coordonnées sont hors limites
     */
    public Case getCase(int x, int y) {
        if (x < 0 || x >= m_largeur || y < 0 || y >= m_hauteur) {
            throw new IndexOutOfBoundsException("Coordonnées en dehors de la carte");
        }
        return m_cases[y][x]; 
    }

    /**
     * Remplace la case à la position spécifiée.
     *
     * @param x coordonnée x (colonne)
     * @param y coordonnée y (ligne)
     * @param uneCase la nouvelle case à placer
     * @throws IndexOutOfBoundsException si les coordonnées sont hors limites
     */
    public void setCase(int x, int y, Case uneCase) {
        if (x < 0 || x >= m_largeur || y < 0 || y >= m_hauteur) {
            throw new IndexOutOfBoundsException("Coordonnées en dehors de la carte");
        }
        m_cases[y][x] = uneCase; 
    }

    /**
     * Ajoute un élément à la case spécifiée.
     *
     * @param x coordonnée x (colonne)
     * @param y coordonnée y (ligne)
     * @param element l'élément à ajouter
     * @throws IndexOutOfBoundsException si les coordonnées sont hors limites
     */
    public void ajouterContenu(int x, int y, ElementCarte element) {
        if (x < 0 || x >= m_largeur || y < 0 || y >= m_hauteur) {
            throw new IndexOutOfBoundsException("Coordonnées en dehors de la carte");
        }
        m_cases[y][x].ajouterContenu(element);
    }

    /**
     * Ajoute un élément à une position aléatoire sur la carte.
     * L'élément sera placé sur une case vide, non-obstacle et sans élément mobile.
     *
     * @param element l'élément à ajouter
     * @return true si l'élément a été ajouté avec succès, false sinon
     */
    public boolean ajouterContenuAleatoire(ElementCarte element) {
        Random rand = new Random();
        int tentatives = 0;
        int maxTentatives = m_largeur * m_hauteur * 2; // Limite pour éviter boucle infinie

        try {
            while (tentatives < maxTentatives) {
                int x = rand.nextInt(m_largeur);
                int y = rand.nextInt(m_hauteur);
                if (m_cases[y][x].estVide() && !m_cases[y][x].estObstacle() && !m_cases[y][x].contientElementMobile()) {
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
     * Vérifie si les coordonnées sont valides sur cette carte.
     *
     * @param x coordonnée x (colonne)
     * @param y coordonnée y (ligne)
     * @return true si les coordonnées sont dans les limites de la carte, false sinon
     */
    public boolean coordonneesValides(int x, int y) {
        return x >= 0 && x < m_largeur && y >= 0 && y < m_hauteur;
    }

    /**
     * Vérifie si une case est accessible (pas obstacle, pas d'élément mobile).
     *
     * @param x coordonnée x (colonne)
     * @param y coordonnée y (ligne)
     * @return true si la case est accessible, false sinon
     */
    public boolean estCaseAccessible(int x, int y) {
        Case caseCible = getCase(x, y);
        if (coordonneesValides(x, y)) {
            return caseCible.estAccessible();
        }
        return false;
    }

    /**
     * Déplace un élément d'une case à une autre sur la carte.
     *
     * @param element l'élément mobile à déplacer
     * @param xCible coordonnée x de la destination
     * @param yCible coordonnée y de la destination
     * @return true si le déplacement a réussi, false sinon
     */
    public boolean deplacerElement(ElementMobile element, int xCible, int yCible) {
        // Trouver la case actuelle
        Optional<Case> caseActuelleOpt = getCase(element);
        if (caseActuelleOpt.isEmpty()) {
            return false; // Élément pas sur la carte
        }

        Case caseActuelle = caseActuelleOpt.get();

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
     * Retourne les cases accessibles dans un rayon donné autour d'un point central.
     * Ne retourne jamais null, mais une liste vide si aucune case n'est accessible.
     *
     * @param xCentre coordonnée x du centre
     * @param yCentre coordonnée y du centre
     * @param rayon rayon de recherche
     * @return liste des coordonnées [x,y] des cases accessibles
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
     * Calcule la distance entre deux points (distance de Chebyshev).
     * La distance de Chebyshev est le maximum des différences absolues des coordonnées.
     *
     * @param x1 coordonnée x du premier point
     * @param y1 coordonnée y du premier point
     * @param x2 coordonnée x du second point
     * @param y2 coordonnée y du second point
     * @return la distance de Chebyshev entre les deux points
     */
    public static int calculerDistance(int x1, int y1, int x2, int y2) {
        return Math.max(Math.abs(x2 - x1), Math.abs(y2 - y1));
    }

    /**
     * Retourne si la carte est la carte par défaut.
     *
     * @return true si c'est la carte par défaut, false sinon
     */
    public boolean isCarteParDefaut() {
        return m_carteParDefaut;
    }

    /**
     * Définit si la carte est la carte par défaut.
     *
     * @param carteParDefaut true pour définir comme carte par défaut, false sinon
     */
    public void setCarteParDefaut(boolean carteParDefaut) {
        this.m_carteParDefaut = carteParDefaut;
    }

    /**
     * Vérifie si deux positions sont à portée l'une de l'autre.
     *
     * @param x1 coordonnée x du premier point
     * @param y1 coordonnée y du premier point
     * @param x2 coordonnée x du second point
     * @param y2 coordonnée y du second point
     * @param portee la portée maximale
     * @return true si les points sont à portée, false sinon
     */
    public static boolean estAPortee(int x1, int y1, int x2, int y2, int portee) {
        return calculerDistance(x1, y1, x2, y2) <= portee;
    }

    /**
     * Parse une chaîne de coordonnées (ex: "A5") en coordonnées x,y.
     * Format attendu : lettre majuscule suivie d'un nombre (A1, B5, etc.)
     *
     * @param coordString la chaîne de coordonnées à parser
     * @return Optional contenant les coordonnées [x,y] si valides, Optional.empty() sinon
     */
    public Optional<int[]> parseCoordonnees(String coordString) {
        if (coordString == null || coordString.length() < 2) {
            return Optional.empty();
        }

        char colChar = coordString.charAt(0);
        if (colChar < 'A' || colChar > 'Z') {
            return Optional.empty();
        }

        int x = colChar - 'A';
        String ligneStr = coordString.substring(1);

        try {
            int y = Integer.parseInt(ligneStr) - 1;

            if (!coordonneesValides(x, y)) {
                return Optional.empty();
            }

            return Optional.of(new int[]{x, y});
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    /**
     * Convertit des coordonnées x,y en chaîne (ex: A5).
     *
     * @param x coordonnée x (colonne, 0-25 pour A-Z)
     * @param y coordonnée y (ligne, 0+ pour 1+)
     * @return la chaîne de coordonnées si valide, "Position invalide" sinon
     */
    public static String coordonneesToString(int x, int y) {
        if (x < 0 || x >= 26 || y < 0) {
            return "Position invalide";
        }
        return (char) ('A' + x) + String.valueOf(y + 1);
    }

    /**
     * Génère des obstacles de manière aléatoire sur la carte.
     *
     * @param tauxObstacle taux d'obstacles entre 0.0 et 1.0 (0% à 100%)
     * @throws IllegalArgumentException si le taux n'est pas entre 0.0 et 1.0
     */
    public void genererObstaclesAleatoires(double tauxObstacle) {
        if (tauxObstacle < 0.0 || tauxObstacle > 1.0) {
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