package carteDuJeu.actions;

import carteDuJeu.Carte;
import carteDuJeu.ElementMobile;

import java.util.List;
import java.util.Scanner;

public class Deplacement {
    private final Carte m_carte;
    private final Scanner m_scanner;

    public Deplacement(Carte carte) {
        this.m_carte = carte;
        this.m_scanner = new Scanner(System.in);
    }

    /**
     * Gère le déplacement interactif d'un élément
     * C'est la seule vraie responsabilité de cette classe
     */
    public boolean gererDeplacement(ElementMobile element) {
        int[] position;
        try {
            position = m_carte.trouverPosition(element);
        } catch (Exception e) {
            System.out.println("Erreur lors de la recherche de la position : " + e.getMessage());
            return false;
        }
        if (position == null) {
            System.out.println("Élément non trouvé sur la carte !");
            return false;
        }

        int xActuel = position[0];
        int yActuel = position[1];
        int casesMax = element.getCasesMaxDeplacement();

        System.out.println("=== Déplacement de " + element.getNom() + " ===");
        System.out.println("Position actuelle : " + Carte.coordonneesToString(xActuel, yActuel));
        System.out.println("Déplacement maximum : " + casesMax + " cases");

        // Afficher les cases accessibles
        try {
            afficherCasesAccessibles(element);
        } catch (Exception e) {
            System.out.println("Erreur lors de l'affichage des cases accessibles : " + e.getMessage());
        }

        while (true) {
            System.out.print("Entrez la destination (ex: A5 ou 'quitter' pour annuler) : ");
            String destination = m_scanner.nextLine().trim().toUpperCase();

            if (destination.equalsIgnoreCase("quitter")) {
                System.out.println("Déplacement annulé.");
                return false;
            }

            try {
                int[] coordonnees = m_carte.parseCoordonnees(destination);
                int xCible = coordonnees[0];
                int yCible = coordonnees[1];

                if (!peutSeDeplacer(element, xCible, yCible)) {
                    continue;
                }

                try {
                    if (m_carte.deplacerElement(element, xCible, yCible)) {
                        System.out.println(element.getNom() + " s'est déplacé vers " +
                                Carte.coordonneesToString(xCible, yCible));
                        return true;
                    } else {
                        System.out.println("Erreur lors du déplacement !");
                    }
                } catch (Exception e) {
                    System.out.println("Erreur lors du déplacement : " + e.getMessage());
                }

            } catch (IllegalArgumentException e) {
                System.out.println("Erreur : " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Erreur inattendue : " + e.getMessage());
            }
        }
    }

    /**
     * Vérifie si un élément peut se déplacer vers une position donnée
     */
    public boolean peutSeDeplacer(ElementMobile element, int xCible, int yCible) {
        int[] position = m_carte.trouverPosition(element);
        if (position == null) return false;

        int xActuel = position[0];
        int yActuel = position[1];
        int casesMax = element.getCasesMaxDeplacement();

        // Vérifier la distance
        int distance = Carte.calculerDistance(xActuel, yActuel, xCible, yCible);
        if (distance > casesMax) {
            System.out.println("Distance trop grande : " + distance + " cases (max: " + casesMax + ")");
            return false;
        }

        // Vérifier l'accessibilité de la case cible
        if (!m_carte.estCaseAccessible(xCible, yCible)) {
            System.out.println("Case inaccessible : " + Carte.coordonneesToString(xCible, yCible));
            return false;
        }

        return true;
    }

    /**
     * Affiche les cases accessibles pour un élément
     */
    public void afficherCasesAccessibles(ElementMobile element) {
        int[] position = m_carte.trouverPosition(element);
        if (position == null) return;

        List<int[]> casesAccessibles = m_carte.getCasesAccessibles(
                position[0], position[1], element.getCasesMaxDeplacement()
        );

        if (casesAccessibles.isEmpty()) {
            System.out.println("Aucune case accessible !");
            return;
        }

        System.out.print("Cases accessibles : ");
        for (int i = 0; i < casesAccessibles.size() && i < 10; i++) { // Limite à 10 pour l'affichage
            int[] coords = casesAccessibles.get(i);
            System.out.print(Carte.coordonneesToString(coords[0], coords[1]));
            if (i < Math.min(casesAccessibles.size(), 10) - 1) {
                System.out.print(", ");
            }
        }
        if (casesAccessibles.size() > 10) {
            System.out.print("... (+" + (casesAccessibles.size() - 10) + " autres)");
        }
        System.out.println();
    }

    /**
     * Déplace un élément automatiquement vers une destination si possible
     */
    public boolean deplacerAutomatiquement(ElementMobile element, int xCible, int yCible) {
        if (!peutSeDeplacer(element, xCible, yCible)) {
            return false;
        }
        return m_carte.deplacerElement(element, xCible, yCible);
    }

    /**
     * Déplace un élément automatiquement vers une destination donnée en string
     */
    public boolean deplacerAutomatiquement(ElementMobile element, String destination) {
        try {
            int[] coords = m_carte.parseCoordonnees(destination.toUpperCase());
            return deplacerAutomatiquement(element, coords[0], coords[1]);
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}