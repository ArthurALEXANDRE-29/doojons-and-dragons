package carteDuJeu.personnages.sorts;

import carteDuJeu.Carte;
import carteDuJeu.Des;
import carteDuJeu.ElementMobile;
import carteDuJeu.personnages.Personnage;

public class sortGuerison extends Sort {
    public sortGuerison() {
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
}