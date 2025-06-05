package carteDuJeu.personnages.sorts;

import carteDuJeu.Carte;
import carteDuJeu.Des;
import carteDuJeu.ElementMobile;
import carteDuJeu.personnages.Personnage;
import carteDuJeu.personnages.classes.Classe;

/**
 * Sort de guérison permettant de rendre entre 1 et 10 points de vie à une ou plusieurs cibles.
 * Ce sort peut être utilisé par les classes Clerc et Magicien.
 */
public class SortGuerison extends Sort {
    /**
     * Construit le sort de guérison avec son nom et sa description.
     */
    public SortGuerison() {
        super("Guerison", "Rend entre 1 et 10 points de vie à la cible");
    }

    /**
     * Lance le sort de guérison sur les cibles spécifiées.
     * Chaque cible qui est un personnage reçoit entre 1 et 10 points de vie supplémentaires.
     * @param carte la carte du jeu (non utilisée ici)
     * @param lanceur le personnage lançant le sort
     * @param cibles les éléments mobiles ciblés par le sort
     * @return true si au moins une cible a été soignée, false sinon
     */
    @Override
    public boolean lancer(Carte carte, Personnage lanceur, ElementMobile[] cibles) {
        if (cibles.length == 0) {
            return false;
        }
        for (ElementMobile cible : cibles) {
            if (cible.estPersonnage()) {
                Personnage personnageCible = (Personnage) cible;
                personnageCible.setPointsDeVie(personnageCible.getPointsDeVie() + Des.lancer("1d10"));
                System.out.println("La cible à maintenant " + personnageCible.getPointsDeVie() + " points de vie");
            }
        }
        return true;
    }

    /**
     * Indique si le sort peut être utilisé par la classe spécifiée.
     * @param classe la classe du personnage
     * @return true si la classe est Clerc ou Magicien, false sinon
     */
    @Override
    public boolean estUtilisablePar(Classe classe) {
        return classe.getNomClasse().equals("Clerc") || classe.getNomClasse().equals("Magicien");
    }
}