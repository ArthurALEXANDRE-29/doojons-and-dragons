package carteDujeu.Monstres;

import carteDuJeu.Case;
import carteDuJeu.Monstres.Attaque;

/* Classe représentant un monstre.
   Les monstres sont contrôlés par le maître du jeu et affrontent les personnages des joueurs.*/
public class Monstre {
    private String m_espece;
    private int m_numero;
    private int m_pointsDeVie;
    private int m_force;         // 0 si attaque à distance
    private int m_dexterite;     // 0 si attaque au corps à corps
    private int m_classeArmure;
    private int m_initiative;
    private int m_vitesse;
    private Case m_position;
    private Attaque m_attaque;


    //Constructeur de la classe Monstre
    public Monstre(String espece, int numero, int pointsDeVie, int force, int dexterite, int classeArmure, int initiative, Attaque attaque) {
        this.m_espece = espece;
        this.m_numero = numero;
        this.m_attaque = attaque;
        this.m_pointsDeVie = pointsDeVie;
        this.m_force = force;
        this.m_dexterite = dexterite;
        this.m_classeArmure = classeArmure;
        this.m_initiative = initiative;
    }
}