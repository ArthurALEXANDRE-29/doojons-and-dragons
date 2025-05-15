package carteDuJeu.actions;

import carteDuJeu.Des;
import carteDuJeu.Carte;
import carteDuJeu.Case;
import carteDuJeu.personnages.Personnage;
import carteDuJeu.Monstres.Monstre;
import carteDuJeu.personnages.equipements.armes.Arme;
import carteDuJeu.personnages.equipements.armures.Armure;

public class Attaque {

    public static boolean attaquer(Carte carte, Personnage attaquant, Monstre cible, Case caseAttaquant, Case caseCible) {
        Arme arme = attaquant.getArmeEquipee();
        if (arme == null) {
            System.out.println(attaquant.getNom() + " n'a pas d'arme équipée.");
            return false;
        }

        int distance = calculDistance(caseAttaquant, caseCible);
        if (distance > arme.getPortee()) {
            System.out.println("Cible hors de portée.");
            return false;
        }

        int modificateur = (arme.getPortee() > 1) ? attaquant.getDexterite() : attaquant.getForce();
        int jetAttaque = Des.lancer(1, 20);
        int scoreAttaque = jetAttaque + modificateur;

        System.out.println(attaquant.getNom() + " attaque " + cible.getNom() + " avec un jet de " + jetAttaque + " + " + modificateur + " = " + scoreAttaque);

        if (scoreAttaque > cible.getClasseArmure()) {  // Pour Monstre, getClasseArmure est OK
            int degats = Des.lancer(1, arme.getDegats());
            cible.subirDegats(degats);
            System.out.println("Attaque réussie ! " + cible.getNom() + " subit " + degats + " dégâts.");
        } else {
            System.out.println("Attaque manquée !");
        }

        return true;
    }

    public static boolean attaquer(Carte carte, Monstre attaquant, Personnage cible, Case caseAttaquant, Case caseCible) {
        int distance = calculDistance(caseAttaquant, caseCible);
        if (distance > attaquant.getPortee()) {
            System.out.println("Cible hors de portée.");
            return false;
        }

        int modificateur = (attaquant.getPortee() > 1) ? attaquant.getDexterite() : attaquant.getForce();
        int jetAttaque = Des.lancer(1, 20);
        int scoreAttaque = jetAttaque + modificateur;

        System.out.println(attaquant.getNom() + " attaque " + cible.getNom() + " avec un jet de " + jetAttaque + " + " + modificateur + " = " + scoreAttaque);

        Armure armureEquipee = cible.getArmureEquipee();
        int classeArmureCible = (armureEquipee != null) ? armureEquipee.getClasseArmure() : 10; // Défaut 10 si pas d'armure

        if (scoreAttaque > classeArmureCible) {
            int degats = Des.lancer(attaquant.getNbDes(), attaquant.getM_maxDmg());
            cible.subirDegats(degats);
            System.out.println("Attaque réussie ! " + cible.getNom() + " subit " + degats + " dégâts.");
        } else {
            System.out.println("Attaque manquée !");
        }

        return true;
    }

    private static int calculDistance(Case a, Case b) {
        return Math.abs(a.getX() - b.getX()) + Math.abs(a.getY() - b.getY());
    }
}
