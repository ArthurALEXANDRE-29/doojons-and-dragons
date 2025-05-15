import carteDuJeu.Carte;
import carteDuJeu.actions.Deplacement;
import carteDuJeu.ElementCarte;
import carteDuJeu.Monstres.Monstre;
import carteDuJeu.personnages.Personnage;
import carteDuJeu.personnages.equipements.Equipement;
import carteDuJeu.personnages.equipements.armes.*;
import carteDuJeu.personnages.classes.*;
import carteDuJeu.personnages.races.*;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Créer une instance de Deplacement
        Carte carte = new Carte(20, 15);
        Deplacement deplacement = new Deplacement(carte);

// Pour déplacer un personnage ou un monstre
        Personnage personnage = new Personnage("Alexandru", new Humain(), new Magicien());
        carte.ajouterContenu(3, 2, personnage);

        // Affichage de la carte
        carte.afficher();
        deplacement.gererDeplacement(personnage);
        carte.afficher();
    }
}