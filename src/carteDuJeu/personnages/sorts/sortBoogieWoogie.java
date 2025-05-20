package carteDuJeu.personnages.sorts;

import carteDuJeu.Carte;
import carteDuJeu.Case;
import carteDuJeu.ElementMobile;
import carteDuJeu.personnages.Personnage;

public class sortBoogieWoogie extends Sort {
    public sortBoogieWoogie() {
        super("Boogie Woogie", "Le personnage détenteur du sort peut choisir deux personnages " +
                "(y compris lui-même), de deux monstres ou d'un personnage (y compris lui-même) " +
                "et d'un monstre et échanger leur position dans le donjon.");
    }

    @Override
    public boolean lancer(Carte carte, Personnage lanceur, ElementMobile[] cibles) {
        if (cibles.length != 2) {
            return false;
        }
        ElementMobile cible1 = cibles[0];
        ElementMobile cible2 = cibles[1];

        // Logique pour échanger les positions des deux cibles
        Case case1 = carte.getCase(cible1);
        Case case2 = carte.getCase(cible2);

        int tempX = case1.getX();
        int tempY = case1.getY();
        case1.setPosition(case2.getX(), case2.getY());
        case2.setPosition(tempX, tempY);

        return true;
    }
}