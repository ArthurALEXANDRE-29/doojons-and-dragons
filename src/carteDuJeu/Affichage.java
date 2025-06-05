package carteDuJeu;

import java.util.List;

import carteDuJeu.personnages.Personnage;
import carteDuJeu.Monstres.Monstre;
import carteDuJeu.personnages.equipements.Equipement;
import carteDuJeu.Carte;

/**
 * Classe pour gérer l'affichage des messages et des entités dans le jeu.
 */



public class Affichage {

    public static void afficherMessageBienvenue() {
        System.out.println("Bienvenue dans le donjon mystérieux !");
        System.out.println("Vous incarnez des aventuriers courageux, prêts à affronter des monstres redoutables.");
        System.out.println("Tuez tous les monstres qui se dressent sur votre chemin.");
        System.out.println("Bonne chance à vous !");
    }

    public static void afficherEntitesDeplacables(List<Personnage> joueurs, List<Monstre> monstres, Carte carteActuelle) {
        if (carteActuelle == null) {
            System.out.println("❌ Aucune carte disponible.");
            return;
        }

        System.out.println("\n--- Entités disponibles pour le déplacement ---");

        for (Personnage joueur : joueurs) {
            if (!carteActuelle.contientElement(joueur)) {
                System.out.println("[Joueur] " + joueur.getNom() + " n'est pas sur la carte.");
                continue;
            }
            try {
                Case caseJoueur = carteActuelle.getCase(joueur);
                Affichage.afficherConfirmation("[Joueur] " + joueur.getNom() + " est en (" + caseJoueur.getX() + ", " + caseJoueur.getY() + ")");
            } catch (IllegalArgumentException e) {
                System.out.println("[Joueur] " + joueur.getNom() + " : Position introuvable.");
            }
        }

        for (Monstre monstre : monstres) {
            if (!carteActuelle.contientElement(monstre)) {
                System.out.println("[Monstre] " + monstre.getNom() + " n'est pas sur la carte.");
                continue;
            }
            try {
                Case caseMonstre = carteActuelle.getCase(monstre);
                System.out.println("[Monstre] " + monstre.getNom() + " est en (" + caseMonstre.getX() + ", " + caseMonstre.getY() + ")");
            } catch (IllegalArgumentException e) {
                System.out.println("[Monstre] " + monstre.getNom() + " : Position introuvable.");
            }
        }
    }

    public static void afficherErreur(String message) {
        System.out.println("❌ " + message);
    }

    public static void afficherConfirmation(String message) {
        System.out.println(message);
    }
    
    public static void afficherCiblesDisponibles(List<Monstre> monstres, List<Personnage> joueurs) {
        System.out.println("\n--- Cibles disponibles ---");

        for (Monstre monstre : monstres) {
            System.out.println("[Monstre] " + monstre.getNom() + " (PV: " + monstre.getPointsDeVie() + "/" + monstre.getPointsDeVieMax() + ")");
        }

        for (Personnage joueur : joueurs) {
            System.out.println("[Joueur] " + joueur.getNom() + " (PV: " + joueur.getPointsDeVie() + "/" + joueur.getPointsDeVieMax() + ")");
        }
    }
    /**
     * Affiche l'inventaire d'un personnage
     */
    public void afficherInventaire(Personnage personnage) {
        System.out.println("\n----- Inventaire de " + personnage.getNom() + "-----");
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
}
