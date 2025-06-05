package carteDuJeu;

import java.util.List;
import java.util.Optional;

import carteDuJeu.personnages.Personnage;
import carteDuJeu.monstres.Monstre;
import carteDuJeu.personnages.equipements.Equipement;

/**
 * Classe utilitaire pour gérer l'affichage des messages et des entités dans le jeu.
 * Cette classe fournit des méthodes statiques pour afficher différents types d'informations :
 * messages de bienvenue, état des entités, cartes, inventaires, etc.
 *
 * Toutes les méthodes utilisent System.out pour l'affichage console.
 */
public class Affichage {

    /**
     * Affiche le message de bienvenue du jeu.
     * Ce message présente le contexte du jeu et les objectifs aux joueurs.
     */
    public static void afficherMessageBienvenue() {
        System.out.println("Bienvenue dans le donjon mystérieux !");
        System.out.println("Vous incarnez des aventuriers courageux, prêts à affronter des monstres redoutables.");
        System.out.println("Tuez tous les monstres qui se dressent sur votre chemin.");
        System.out.println("Bonne chance à vous !");
    }

    /**
     * Affiche la liste des entités déplaçables (joueurs et monstres) avec leurs positions sur la carte.
     * Pour chaque entité, affiche son nom, son type et sa position si elle est sur la carte.
     *
     * @param joueurs la liste des personnages joueurs (ne doit pas être null)
     * @param monstres la liste des monstres (ne doit pas être null)
     * @param carteActuelle la carte sur laquelle vérifier les positions des entités
     *
     * @throws IllegalArgumentException si une des listes est null
     */
    public static void afficherEntitesDeplacables(List<Personnage> joueurs, List<Monstre> monstres, Carte carteActuelle) {
        if (joueurs == null) {
            throw new IllegalArgumentException("La liste des joueurs ne peut pas être null");
        }
        if (monstres == null) {
            throw new IllegalArgumentException("La liste des monstres ne peut pas être null");
        }

        if (carteActuelle == null) {
            System.out.println("❌ Aucune carte disponible.");
            return;
        }

        System.out.println("\n--- Entités disponibles pour le déplacement ---");

        // Affichage des joueurs
        for (Personnage joueur : joueurs) {
            if (!carteActuelle.contientElement(joueur)) {
                System.out.println("[Joueur] " + joueur.getNom() + " n'est pas sur la carte.");
                continue;
            }

            Optional<Case> caseJoueurOpt = carteActuelle.getCase(joueur);
            if (caseJoueurOpt.isPresent()) {
                Case caseJoueur = caseJoueurOpt.get();
                afficherConfirmation("[Joueur] " + joueur.getNom() + " est en (" + caseJoueur.getX() + ", " + caseJoueur.getY() + ")");
            } else {
                System.out.println("[Joueur] " + joueur.getNom() + " : Position introuvable.");
            }
        }

        // Affichage des monstres
        for (Monstre monstre : monstres) {
            if (!carteActuelle.contientElement(monstre)) {
                System.out.println("[Monstre] " + monstre.getNom() + " n'est pas sur la carte.");
                continue;
            }

            Optional<Case> caseMonstreOpt = carteActuelle.getCase(monstre);
            if (caseMonstreOpt.isPresent()) {
                Case caseMonstre = caseMonstreOpt.get();
                System.out.println("[Monstre] " + monstre.getNom() + " est en (" + caseMonstre.getX() + ", " + caseMonstre.getY() + ")");
            } else {
                System.out.println("[Monstre] " + monstre.getNom() + " : Position introuvable.");
            }
        }
    }

    /**
     * Affiche un message d'erreur formaté avec un préfixe visuel.
     *
     * @param message le message d'erreur à afficher (ne doit pas être null)
     * @throws IllegalArgumentException si le message est null
     */
    public static void afficherErreur(String message) {
        if (message == null) {
            throw new IllegalArgumentException("Le message ne peut pas être null");
        }
        System.out.println("❌ " + message);
    }

    /**
     * Affiche un message de confirmation ou d'information.
     *
     * @param message le message à afficher (ne doit pas être null)
     * @throws IllegalArgumentException si le message est null
     */
    public static void afficherConfirmation(String message) {
        if (message == null) {
            throw new IllegalArgumentException("Le message ne peut pas être null");
        }
        System.out.println(message);
    }

    /**
     * Affiche la liste des cibles disponibles pour une attaque.
     * Montre les monstres et joueurs avec leurs points de vie actuels et maximums.
     *
     * @param monstres la liste des monstres disponibles comme cibles (ne doit pas être null)
     * @param joueurs la liste des joueurs disponibles comme cibles (ne doit pas être null)
     * @throws IllegalArgumentException si une des listes est null
     */
    public static void afficherCiblesDisponibles(List<Monstre> monstres, List<Personnage> joueurs) {
        if (monstres == null) {
            throw new IllegalArgumentException("La liste des monstres ne peut pas être null");
        }
        if (joueurs == null) {
            throw new IllegalArgumentException("La liste des joueurs ne peut pas être null");
        }

        System.out.println("\n--- Cibles disponibles ---");

        // Affichage des monstres
        for (Monstre monstre : monstres) {
            if (!monstre.estMort()) {
                System.out.println("[Monstre] " + monstre.getNom() + " (PV: " + monstre.getPointsDeVie() + "/" + monstre.getPointsDeVieMax() + ")");
            }
        }

        // Affichage des joueurs
        for (Personnage joueur : joueurs) {
            System.out.println("[Joueur] " + joueur.getNom() + " (PV: " + joueur.getPointsDeVie() + "/" + joueur.getPointsDeVieMax() + ")");
        }
    }

    /**
     * Affiche l'inventaire complet d'un personnage.
     * Liste tous les équipements avec leur numéro et leurs caractéristiques.
     * Si l'inventaire est vide, affiche un message approprié.
     *
     * @param personnage le personnage dont l'inventaire doit être affiché (ne doit pas être null)
     * @throws IllegalArgumentException si le personnage est null
     */
    public static void afficherInventaire(Personnage personnage) {
        if (personnage == null) {
            throw new IllegalArgumentException("Le personnage ne peut pas être null");
        }

        System.out.println("\n----- Inventaire de " + personnage.getNom() + " -----");

        if (personnage.getInventaire().isEmpty()) {
            System.out.println("Inventaire vide.");
        } else {
            for (int i = 0; i < personnage.getInventaire().size(); i++) {
                Equipement equip = personnage.getInventaire().get(i);
                if (equip.estUneArme()) {
                    System.out.println((i + 1) + ". " + equip.toString());
                } else if (equip.estUneArmure()) {
                    System.out.println((i + 1) + ". " + equip.toString());
                }
            }
        }
    }

    /**
     * Affiche la carte de jeu avec un système de coordonnées et des bordures décoratives.
     * La carte est affichée avec :
     * - Les coordonnées X sous forme de lettres (A, B, C, ...)
     * - Les coordonnées Y sous forme de nombres (1, 2, 3, ...)
     * - Un contour décoratif avec des caractères Unicode
     * - Le contenu de chaque case selon sa méthode toString()
     *
     * @param carte la carte à afficher (peut être null)
     */
    public static void afficherCarte(Optional<Carte> carte) {
        if (carte == null || carte.isEmpty()) {
            System.out.println("❌ Aucune carte à afficher.");
            return;
        }
        Carte c = carte.get();

        // Affiche les coordonnées X (lettres)
        System.out.print("  ");
        for (int i = 0; i < c.getLargeur(); i++) {
            System.out.printf("%4c", 'A' + i);
        }
        System.out.println();

        // Ligne supérieure du contour
        System.out.print("   ┌");
        for (int x = 0; x < c.getLargeur(); x++) {
            System.out.print("────");
        }
        System.out.println("┐");

        // Affichage du contenu de la carte
        for (int y = 0; y < c.getHauteur(); y++) {
            System.out.printf("%2d │", y + 1);
            for (int x = 0; x < c.getLargeur(); x++) {
                Case caseActuelle = c.getCase(x, y);
                System.out.print(caseActuelle.toString());
            }
            System.out.println("│");
        }

        // Ligne inférieure du contour
        System.out.print("   └");
        for (int x = 0; x < c.getLargeur(); x++) {
            System.out.print("────");
        }
        System.out.println("┘");
    }
}