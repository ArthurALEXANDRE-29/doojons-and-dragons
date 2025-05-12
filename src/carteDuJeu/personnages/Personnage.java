package carteDuJeu.personnages;
import carteDuJeu.Des;

import carteDuJeu.personnages.equipements.*;


import java.util.ArrayList;
import java.util.List;

public class Personnage {
    // attributs initialisés en fonction du joueur et ses choix / choix de la classe + race
    private String nom;
    private Race race;
    private Classe classe;
    private Arme armeEquipee;
    private Armure armureEquipee;
    private List<Equipement> inventaire;

    // caractéristiques du personnage
    private int pointsDeVie;
    private int pointsDeVieMax;
    private int force;
    private int dexterite;
    private int vitesse;
    private int initiative;

    public Personnage(String nom, Race race, Classe classe) {
        // initialisation des attributs choisis / liés à la classe ou race + armes/ armures de base
        this.nom = nom;
        this.race = race;
        this.classe = classe;

        // Initialisation des attributs de base du personnage
        this.pointsDeVieMax = classe.getPointsDeVie();
        this.pointsDeVie = this.pointsDeVieMax;

        // Calcul des autres attributs en fonction de la race et de la classe
        // pour chaque base : faire un jet de dés


        this.force = Des.lancer(4, 4) + 3+ race.getForceBonus() + classe.getForceBonus() ;
        this.dexterite = Des.lancer(4, 4) + 3+ race.getDexteriteBonus() + classe.getDexteriteBonus();
        this.vitesse = Des.lancer(4, 4) + 3 + race.getVitesseBonus();
        this.initiative = Des.lancer(4, 4) + 3 + race.getInitiativeBonus() + classe.getInitiativeBonus();


        this.inventaire = new ArrayList<>();

        // Ajouter l'équipement de classe (armes/armures) à l'inventaire ( initialisation)

        for (Equipement eq : classe.getEquipementInitial()) {
            inventaire.add(eq);
            // parcours l'inventaire et ajoute la première arme rencontrée / armue aussi
            // si il commence avec plusieurs armes / armures => à modifier
            if (eq instanceof Arme && armeEquipee == null) {
                armeEquipee = (Arme) eq;
            }
            if (eq instanceof Armure && armureEquipee == null) {
                armureEquipee = (Armure) eq;
            }
        }
    }
    public String getNom() {
        return nom;
    }

    public Race getRace() {
        return race;
    }

    public Classe getClasse() {
        return classe;
    }

    public int getPointsDeVie() {
        return pointsDeVie;
    }

    public int getPointsDeVieMax() {
        return pointsDeVieMax;
    }

    public int getForce() {
        return force;
    }

    public int getDexterite() {
        return dexterite;
    }

    public int getVitesse() {
        return vitesse;
    }

    public int getInitiative() {
        return initiative;
    }

    public List<Equipement> getInventaire() {
        return inventaire;
    }

    public Arme getArmeEquipee() {
        return armeEquipee;
    }

    public Armure getArmureEquipee() {
        return armureEquipee;
    }
}


