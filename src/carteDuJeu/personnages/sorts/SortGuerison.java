package carteDuJeu.personnages.sorts;

import carteDuJeu.Carte;
import carteDuJeu.Des;
import carteDuJeu.ElementMobile;
import carteDuJeu.personnages.Personnage;
import carteDuJeu.personnages.classes.Classe;

public class SortGuerison extends Sort {
    public SortGuerison() {
        super("Guerison", "Rend entre 1 et 10 points de vie à la cible");
    }

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

    public boolean estUtilisablePar(Classe classe) {
        return classe.getNomClasse().equals("Clerc") || classe.getNomClasse().equals("Magicien");
    }
}