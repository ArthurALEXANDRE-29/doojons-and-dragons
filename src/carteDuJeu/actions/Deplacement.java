package carteDuJeu.actions;

import carteDuJeu.Carte;
import carteDuJeu.Case;
import carteDuJeu.ElementCarte;
import carteDuJeu.Monstres.Monstre;
import carteDuJeu.personnages.Personnage;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Deplacement {
    private final Carte m_carte;
    private final Scanner m_scanner;

    public Deplacement(Carte carte) {
        this.m_carte = carte;
        this.m_scanner = new Scanner(System.in);
    }

    public int[] trouverPosition(ElementCarte element) {
        for (int y = 0; y < m_carte.getHauteur(); y++) {
            for (int x = 0; x < m_carte.getLargeur(); x++) {
                Case caseActuelle = m_carte.getCase(x, y);
                if (caseActuelle.contient(element)) {
                    return new int[]{x, y};
                }
            }
        }
        return null;
    }

    public boolean gererDeplacement(ElementCarte element) {
        int[] position = trouverPosition(element);
        if (position == null) {
            System.out.println("Erreur: L'élément n'a pas été trouvé sur la carte.");
            return false;
        }

        int xActuel = position[0];
        int yActuel = position[1];

        int casesMax;
        String nomElement;

        if (element instanceof Personnage) {
            casesMax = ((Personnage) element).getCasesMaxDeplacement();
            nomElement = ((Personnage) element).getNom();
        } else if (element instanceof Monstre) {
            casesMax = ((Monstre) element).getCasesMaxDeplacement();
            nomElement = ((Monstre) element).getNom();
        } else {
            System.out.println("Erreur: Type d'élément non supporté pour le déplacement.");
            return false;
        }

        System.out.println(nomElement + " est actuellement en position: " +
                coordonneesToString(xActuel, yActuel));
        System.out.println("Nombre maximum de cases de déplacement: " + casesMax);

        boolean deplacementReussi = false;
        while (!deplacementReussi) {
            System.out.print("Entrez la destination (ex: A5 ou quitter pour annuler): ");
            String destination = m_scanner.nextLine().trim().toUpperCase();

            if (destination.equalsIgnoreCase("quitter")) {
                System.out.println("Déplacement annulé.");
                return false;
            }

            try {
                int[] coordonnees = parseCoordonnees(destination);
                int xCible = coordonnees[0];
                int yCible = coordonnees[1];

                int distance = Math.abs(xCible - xActuel) + Math.abs(yCible - yActuel);
                if (distance > casesMax) {
                    System.out.println("Déplacement impossible: La destination est trop éloignée (maximum " + casesMax + " cases).");
                    continue;
                }

                if (!estDeplacementValide(xCible, yCible)) {
                    System.out.println("Déplacement impossible: La case " + destination + " est invalide ou occupée.");
                    continue;
                }

                deplacementReussi = deplacer(xActuel, yActuel, xCible, yCible, element);
                return deplacementReussi;

            } catch (IllegalArgumentException e) {
                System.out.println("Format de coordonnées incorrect: " + e.getMessage() + " Veuillez réessayer.");
            }
        }

        return false;
    }

    public int[] parseCoordonnees(String coordString) {
        if (coordString == null || coordString.length() < 2) {
            throw new IllegalArgumentException("Format de coordonnées incorrect.");
        }

        char colChar = coordString.charAt(0);
        if (colChar < 'A' || colChar > 'Z') {
            throw new IllegalArgumentException("La colonne doit être une lettre entre A et Z.");
        }
        int x = colChar - 'A';

        try {
            int y = Integer.parseInt(coordString.substring(1)) - 1;
            if (x < 0 || x >= m_carte.getLargeur() || y < 0 || y >= m_carte.getHauteur()) {
                throw new IllegalArgumentException("Coordonnées hors limites de la carte.");
            }
            return new int[]{x, y};
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("La ligne doit être un nombre.");
        }
    }

    public String coordonneesToString(int x, int y) {
        char colChar = (char) ('A' + x);
        return colChar + "" + (y + 1);
    }

    public boolean estDeplacementValide(int xCible, int yCible) {
        if (xCible < 0 || xCible >= m_carte.getLargeur() || yCible < 0 || yCible >= m_carte.getHauteur()) {
            return false;
        }

        Case caseCible = m_carte.getCase(xCible, yCible);
        if (caseCible.estObstacle()) {
            return false;
        }

        List<ElementCarte> contenu = caseCible.getContenu();
        for (ElementCarte element : contenu) {
            if (element instanceof Personnage || element instanceof Monstre) {
                return false;
            }
        }

        return true;
    }

    public boolean deplacer(int xDepart, int yDepart, int xCible, int yCible, ElementCarte element) {
        Case caseDepart = m_carte.getCase(xDepart, yDepart);
        if (!caseDepart.contient(element)) {
            System.out.println("Erreur: L'élément n'est pas sur la case de départ.");
            return false;
        }

        if (!estDeplacementValide(xCible, yCible)) {
            System.out.println("Erreur: La case cible n'est pas accessible.");
            return false;
        }

        int casesMax;
        if (element instanceof Personnage) {
            casesMax = ((Personnage) element).getCasesMaxDeplacement();
        } else if (element instanceof Monstre) {
            casesMax = ((Monstre) element).getCasesMaxDeplacement();
        } else {
            System.out.println("Erreur: Type d'élément non supporté pour le déplacement.");
            return false;
        }

        int distance = Math.abs(xCible - xDepart) + Math.abs(yCible - yDepart);
        if (distance > casesMax) {
            System.out.println("Erreur: La destination est trop éloignée (maximum " + casesMax + " cases).");
            return false;
        }

        caseDepart.retirerContenu(element);
        Case caseCible = m_carte.getCase(xCible, yCible);
        caseCible.ajouterContenu(element);

        System.out.println("Déplacement réussi vers " + coordonneesToString(xCible, yCible) + ".");
        return true;
    }

    public List<int[]> trouverChemin(int xDepart, int yDepart, int xCible, int yCible, int casesMax) {
        if (!estDeplacementValide(xCible, yCible)) {
            return null;
        }

        List<List<int[]>> queue = new ArrayList<>();
        boolean[][] visites = new boolean[m_carte.getHauteur()][m_carte.getLargeur()];

        List<int[]> cheminInitial = new ArrayList<>();
        cheminInitial.add(new int[]{xDepart, yDepart});
        queue.add(cheminInitial);
        visites[yDepart][xDepart] = true;

        int[][] directions = {{0, -1}, {0, 1}, {-1, 0}, {1, 0}};

        while (!queue.isEmpty()) {
            List<int[]> cheminCourant = queue.remove(0);
            int[] dernierPoint = cheminCourant.get(cheminCourant.size() - 1);
            int xCourant = dernierPoint[0];
            int yCourant = dernierPoint[1];

            if (xCourant == xCible && yCourant == yCible) {
                cheminCourant.remove(0);
                return cheminCourant;
            }

            if (cheminCourant.size() > casesMax + 1) {
                continue;
            }

            for (int[] dir : directions) {
                int nouveauX = xCourant + dir[0];
                int nouveauY = yCourant + dir[1];

                if (nouveauX >= 0 && nouveauX < m_carte.getLargeur() &&
                        nouveauY >= 0 && nouveauY < m_carte.getHauteur() &&
                        !visites[nouveauY][nouveauX] &&
                        estDeplacementValide(nouveauX, nouveauY)) {

                    visites[nouveauY][nouveauX] = true;

                    List<int[]> nouveauChemin = new ArrayList<>(cheminCourant);
                    nouveauChemin.add(new int[]{nouveauX, nouveauY});
                    queue.add(nouveauChemin);
                }
            }
        }

        return null;
    }
}
