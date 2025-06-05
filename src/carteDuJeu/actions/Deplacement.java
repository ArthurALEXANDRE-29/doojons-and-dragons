package carteDuJeu.actions;

import carteDuJeu.Carte;
import carteDuJeu.ElementMobile;

import java.util.List;
import java.util.Scanner;

/**
 * Gère les déplacements d'un élément mobile sur la carte.
 * Cette classe permet de gérer les déplacements interactifs ou automatiques,
 * de vérifier la validité d'un déplacement et d'afficher les cases accessibles.
 */
public class Deplacement {
    private final Carte m_carte;
    private final Scanner m_scanner;

    /**
     * Construit un gestionnaire de déplacement pour une carte donnée.
     * @param carte Carte de jeu
     */
    public Deplacement(Carte carte) {
        this.m_carte = carte;
        this.m_scanner = new Scanner(System.in);
    }

    /**
     * Gère le déplacement interactif d'un élément mobile.
     * @param element Élément à déplacer
     * @return true si le déplacement a eu lieu, false sinon
     */
    public boolean gererDeplacement(ElementMobile element) {
        int[] position;
        try {
            position = m_carte.trouverPosition(element)
                    .orElseThrow(() -> new IllegalArgumentException("Position introuvable"));
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
                int[] coordonnees = m_carte.parseCoordonnees(destination.toUpperCase())
                        .orElseThrow(() -> new IllegalArgumentException("Coordonnées invalides"));
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
     * Vérifie si un élément peut se déplacer vers une position donnée.
     * @param element Élément à déplacer
     * @param xCible Abscisse de la case cible
     * @param yCible Ordonnée de la case cible
     * @return true si le déplacement est possible, false sinon
     */
    public boolean peutSeDeplacer(ElementMobile element, int xCible, int yCible) {
        int[] position = m_carte.trouverPosition(element)
                .orElseThrow(() -> new IllegalArgumentException("Position introuvable"));
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
     * Affiche les cases accessibles pour un élément mobile.
     * @param element Élément concerné
     */
    public void afficherCasesAccessibles(ElementMobile element) {
        int[] position = m_carte.trouverPosition(element)
                .orElseThrow(() -> new IllegalArgumentException("Position introuvable"));
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
     * Déplace automatiquement un élément vers une destination donnée.
     * @param element Élément à déplacer
     * @param xCible Coordonnée X de la destination
     * @param yCible Coordonnée Y de la destination
     * @return true si le déplacement a eu lieu, false sinon
     */
    public boolean deplacerAutomatiquement(ElementMobile element, int xCible, int yCible) {
        if (!peutSeDeplacer(element, xCible, yCible)) {
            return false;
        }
        return m_carte.deplacerElement(element, xCible, yCible);
    }

    /**
     * Déplace automatiquement un élément vers une destination donnée sous forme de chaîne.
     * @param element Élément à déplacer
     * @param destination Destination sous forme de chaîne (ex: "A5")
     * @return true si le déplacement a eu lieu, false sinon
     */
    public boolean deplacerAutomatiquement(ElementMobile element, String destination) {
        try {
            int[] coords = m_carte.parseCoordonnees(destination.toUpperCase())
                    .orElseThrow(() -> new IllegalArgumentException("Coordonnées invalides"));
            return deplacerAutomatiquement(element, coords[0], coords[1]);
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * Retourne une représentation textuelle du gestionnaire de déplacement.
     * @return Chaîne décrivant l'objet Deplacement
     */
    @Override
    public String toString() {
        return "Deplacement{" +
                "carte=" + (m_carte != null ? m_carte.toString() : "null") +
                '}';
    }
}