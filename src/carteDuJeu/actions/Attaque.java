package carteDuJeu.actions;

import carteDuJeu.Des;
import carteDuJeu.Carte;
import carteDuJeu.Case;
import carteDuJeu.personnages.Personnage;
import carteDuJeu.Monstres.Monstre;
import carteDuJeu.personnages.equipements.armes.Arme;
import carteDuJeu.personnages.equipements.armures.Armure;

public class Attaque {

    private final Deplacement m_deplacement;

    public Attaque(Deplacement deplacement) {
        this.m_deplacement = deplacement;
    }

    public boolean attaquer(Carte carte, Personnage attaquant, Monstre cible, Case caseAttaquant, Case caseCible) {
        Arme arme = attaquant.getArmeEquipee();
        if (arme == null) {
            System.out.println(attaquant.getNom() + " n'a pas d'arme équipée.");
            return false;
        }

        if (!m_deplacement.estAPortee(caseAttaquant.getX(), caseAttaquant.getY(),
                caseCible.getX(), caseCible.getY(), arme.getPortee())) {
            System.out.println("Cible hors de portée.");
            return false;
        }

        int modificateur = (arme.getPortee() > 1) ? attaquant.getDexterite() : attaquant.getForce();


        int jetAttaque = Des.lancer(1, 20);

        int scoreAttaque = jetAttaque + modificateur;

        System.out.println(attaquant.getNom() + " attaque " + cible.getNom() +
                " avec un jet de " + jetAttaque + " + " + modificateur +
                " = " + scoreAttaque);

        if (scoreAttaque > cible.getClasseArmure()) {

            int degats = Des.lancer(arme.getDes(), arme.getDegats());
            cible.subirDegats(degats);
            System.out.println("Attaque réussie ! " + cible.getNom() + " subit " + degats + " dégâts.");
        } else {
            System.out.println("Attaque manquée !");
        }

        return true;
    }

    public boolean attaquer(Carte carte, Monstre attaquant, Personnage cible, Case caseAttaquant, Case caseCible) {
        if (!m_deplacement.estAPortee(caseAttaquant.getX(), caseAttaquant.getY(),
                caseCible.getX(), caseCible.getY(), attaquant.getPortee())) {
            System.out.println("Cible hors de portée.");
            return false;
        }

        int modificateur = (attaquant.getPortee() > 1) ? attaquant.getDexterite() : attaquant.getForce();

        int jetAttaque = Des.lancer(1, 20);


        int scoreAttaque = jetAttaque + modificateur;

        System.out.println(attaquant.getNom() + " attaque " + cible.getNom() +
                " avec un jet de " + jetAttaque + " + " + modificateur +
                " = " + scoreAttaque);

        Armure armureEquipee = cible.getArmureEquipee();
        int classeArmureCible = (armureEquipee != null) ? armureEquipee.getClasseArmure() : 10;

        if (scoreAttaque > classeArmureCible) {
            int degats = Des.lancer(attaquant.getNbDes(), attaquant.getM_maxDmg());
            cible.subirDegats(degats);
            System.out.println("Attaque réussie ! " + cible.getNom() + " subit " + degats + " dégâts.");
        } else {
            System.out.println("Attaque manquée !");
        }

        return true;
    }
}
