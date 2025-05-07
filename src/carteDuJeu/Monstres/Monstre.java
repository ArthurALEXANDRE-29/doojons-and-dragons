package carteDujeu.Monstres;

import carteDuJeu.Case;
import carteDuJeu.Monstres.Attaque;

/* Classe représentant un monstre.
   Les monstres sont contrôlés par le maître du jeu et affrontent les personnages des joueurs.*/
public class Monstre {
    private String espece;
    private int numero;
    private int pointsDeVie;
    private int force;         // 0 si attaque à distance
    private int dexterite;     // 0 si attaque au corps à corps
    private int classeArmure;
    private int initiative;
    private int vitesse;
    private Case position;
    private Attaque attaque;


    //Constructeur de la classe Monstre
    public Monstre(String espece, int numero, int pointsDeVie, int force, int dexterite, int classeArmure, int initiative, Attaque attaque) {
        this.espece = espece;
        this.numero = numero;
        this.attaque = attaque;
        this.pointsDeVie = pointsDeVie;
        this.force = force;
        this.dexterite = dexterite;
        this.classeArmure = classeArmure;
        this.initiative = initiative;
    }
}