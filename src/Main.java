import carteDuJeu.Carte;
import carteDuJeu.ElementMobile;
import carteDuJeu.MaitreDuJeu;
import carteDuJeu.actions.Deplacement;
import carteDuJeu.actions.Attaque;
import carteDuJeu.Monstres.Monstre;
import carteDuJeu.personnages.Personnage;
import carteDuJeu.personnages.classes.*;
import carteDuJeu.personnages.races.*;
import carteDuJeu.personnages.sorts.Sort;
import carteDuJeu.personnages.sorts.sortArmeMagique;
import carteDuJeu.personnages.sorts.sortBoogieWoogie;
import carteDuJeu.personnages.sorts.sortGuerison;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Création de la carte
        Carte carte = new Carte(10, 13);

        // Création du maître du jeu
        MaitreDuJeu mdj = new MaitreDuJeu(carte);

        // Phase de création et placement des monstres
        mdj.phaseCreationDesMonstres();
        mdj.placerMonstresAleatoirement();

        // Affichage de la carte après placement des monstres
        System.out.println("=== Carte après placement des monstres ===");
        carte.afficher();

        // Test déplacements
        Deplacement deplacement = new Deplacement(carte);
        Personnage joueur = new Personnage("Héros", new Humain(), new Guerrier());
        carte.ajouterContenu(0, 0, joueur); // Position initiale du joueur
        System.out.println("=== Déplacement du joueur ===");
        deplacement.gererDeplacement(joueur);
        carte.afficher();

        // Test attaque


        // Ici, tu peux ajouter d'autres interactions via mdj selon tes besoins
    }
}