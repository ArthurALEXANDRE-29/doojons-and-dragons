import carteDuJeu.Carte;
import carteDuJeu.ElementCarte;
import carteDuJeu.Monstres.Monstre;
import carteDuJeu.personnages.Personnage;
import carteDuJeu.personnages.equipements.Equipement;
import carteDuJeu.personnages.equipements.armes.*;
import carteDuJeu.personnages.classes.*;
import carteDuJeu.personnages.races.*;

public class Main {
    public static void main(String[] args) {
        // Création de la carte
        Carte carte = new Carte(10, 10);

        // Création des éléments

        Equipement e1 = new EpeeLongue();

        // Placement sur la carte

        carte.ajouterContenu(1, 1, e1);

        // Création d'un monstre
        Monstre monstre = new Monstre("Gobelin", 1, 1, 6, 2, 10, 3, 12, 2);
        carte.ajouterContenu(2, 2, monstre);

        // Création d'un personnage
        Personnage personnage = new Personnage("Alexandru", new Humain(), new Magicien());
        carte.ajouterContenu(3, 3, personnage);

        // Affichage de la carte
        carte.afficher();
    }
}