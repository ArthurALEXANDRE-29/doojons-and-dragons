package carteDuJeu.actions;

import carteDuJeu.Des;
import carteDuJeu.Carte;
import carteDuJeu.Case;
import carteDuJeu.personnages.Personnage;
import carteDuJeu.monstres.Monstre;
import carteDuJeu.personnages.equipements.armes.Arme;
import carteDuJeu.personnages.equipements.armures.Armure;

/**
 * Gère les actions d'attaque entre personnages et monstres sur la carte.
 * Permet de réaliser des attaques, de vérifier la portée et d'appliquer les dégâts.
 */
public class Attaque {

    /** Gestionnaire de déplacement utilisé pour certaines vérifications. */
    private final Deplacement m_deplacement;

    /**
     * Construit un gestionnaire d'attaque avec le gestionnaire de déplacement associé.
     * @param deplacement Gestionnaire de déplacement
     */
    public Attaque(Deplacement deplacement) {
        this.m_deplacement = deplacement;
    }

    /**
     * Permet à un personnage d'attaquer un monstre.
     * @param carte Carte de jeu
     * @param attaquant Personnage attaquant
     * @param cible Monstre ciblé
     * @param caseAttaquant Case du personnage attaquant
     * @param caseCible Case du monstre ciblé
     * @return true si l'attaque a eu lieu, false sinon
     */
    public boolean attaquer(Carte carte, Personnage attaquant, Monstre cible, Case caseAttaquant, Case caseCible) {
        Arme arme = attaquant.getArmeEquipee();
        if (arme == null) {
            System.out.println(attaquant.getNom() + " n'a pas d'arme équipée.");
            return false;
        }

        if (!carte.estAPortee(caseAttaquant.getX(), caseAttaquant.getY(),
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
            if (cible.estMort()) {
                System.out.println(cible.getNom() + " est mort !");
                try {
                    Case c = carte.getCase(caseCible.getX(), caseCible.getY());
                    c.retirerContenu(cible);
                } catch (Exception e) {
                    System.out.println("Erreur lors du retrait de la cible de la case : " + e.getMessage());
                }
            }
            System.out.println("Attaque réussie ! " + cible.getNom() + " subit " + degats + " dégâts.");
        } else {
            System.out.println("Attaque manquée !");
        }

        return true;
    }

    /**
     * Permet à un monstre d'attaquer un personnage.
     * @param carte Carte de jeu
     * @param attaquant Monstre attaquant
     * @param cible Personnage ciblé
     * @param caseAttaquant Case du monstre attaquant
     * @param caseCible Case du personnage ciblé
     * @return true si l'attaque a eu lieu, false sinon
     */
    public boolean attaquer(Carte carte, Monstre attaquant, Personnage cible, Case caseAttaquant, Case caseCible) {
        if (!carte.estAPortee(caseAttaquant.getX(), caseAttaquant.getY(),
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
            if (cible.estMort()) {
                System.out.println(cible.getNom() + " est mort !");
                try {
                    int x = caseCible.getX();
                    int y = caseCible.getY();
                    Case c = carte.getCase(x, y);
                    c.retirerContenu(cible);
                } catch (Exception e) {
                    System.out.println("Erreur lors du retrait de la cible de la case : " + e.getMessage());
                }
            }
        } else {
            System.out.println("Attaque manquée !");
        }

        return true;
    }

    /**
     * Retourne une représentation textuelle de l'objet Attaque.
     * @return Chaîne décrivant l'objet Attaque
     */
    @Override
    public String toString() {
        return "Attaque{deplacement=" + (m_deplacement != null ? m_deplacement.toString() : "null") + "}";
    }
}