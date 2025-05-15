import carteDuJeu.Carte;
import carteDuJeu.Case;
import carteDuJeu.actions.Deplacement;
import carteDuJeu.ElementCarte;
import carteDuJeu.Monstres.Monstre;
import carteDuJeu.personnages.Personnage;
import carteDuJeu.personnages.equipements.Equipement;
import carteDuJeu.personnages.equipements.armes.*;
import carteDuJeu.personnages.classes.*;
import carteDuJeu.personnages.races.*;
import carteDuJeu.actions.Attaque;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Créer une instance de carte et de déplacement
        Carte carte = new Carte(20, 15);
        Deplacement deplacement = new Deplacement(carte);
        Attaque attaque = new Attaque(deplacement);

        // Création et placement du personnage
        Personnage joueur = new Personnage("Alexandru", new Humain(), new Magicien());
        carte.ajouterContenu(3, 2, joueur);
        Case caseJoueur = carte.getCase(3, 2);

        // Création et placement du monstre
        Monstre goblin = new Monstre("Gobelin", 1, 1, 6, 12, 1, 10, 12, 12, 10);
        carte.ajouterContenu(5, 5, goblin);
        Case caseGoblin = carte.getCase(5, 5);

        System.out.println("=== État initial de la carte ===");
        carte.afficher();

        // Vérification de la portée et attaque si possible
        if (deplacement.estAPortee(joueur, goblin, joueur.getArmeEquipee().getPortee())) {
            System.out.println("\n=== Le personnage attaque le monstre ===");
            attaque.attaquer(carte, joueur, goblin, caseJoueur, caseGoblin);
            System.out.println("Points de vie du goblin après attaque: " + goblin.getPointsDeVie());
        } else {
            System.out.println("\nLe monstre est hors de portée.");
        }

        // Test de déplacement du joueur
        System.out.println("\n=== Déplacement du joueur ===");
        System.out.println("Veuillez entrer une destination pour le joueur...");
        deplacement.gererDeplacement(joueur);

        System.out.println("\n=== État de la carte après déplacement ===");
        carte.afficher();

        // Récupération de la nouvelle position du joueur après déplacement
        int[] posJoueur = deplacement.trouverPosition(joueur);
        caseJoueur = carte.getCase(posJoueur[0], posJoueur[1]);

        System.out.println("\n=== Vérification de la portée après déplacement ===");
        System.out.println("Arme équipée: " + joueur.getArmeEquipee().getNom() + " (portée: " + joueur.getArmeEquipee().getPortee() + ")");

        // Vérification si le joueur peut attaquer après déplacement
        if (deplacement.estAPortee(joueur, goblin, joueur.getArmeEquipee().getPortee())) {
            System.out.println("\n=== Le personnage attaque le monstre après déplacement ===");
            attaque.attaquer(carte, joueur, goblin, caseJoueur, caseGoblin);
            System.out.println("Points de vie du goblin après attaque: " + goblin.getPointsDeVie());
        } else {
            System.out.println("\nLe monstre est toujours hors de portée.");
        }

        // Vérification si le monstre peut contre-attaquer
        if (deplacement.estAPortee(goblin, joueur, goblin.getPortee())) {
            System.out.println("\n=== Le monstre contre-attaque ===");
            attaque.attaquer(carte, goblin, joueur, caseGoblin, caseJoueur);
            System.out.println("Points de vie du joueur après attaque: " + joueur.getPointsDeVie());
        } else {
            System.out.println("\nLe joueur est hors de portée du monstre.");
        }
    }
}