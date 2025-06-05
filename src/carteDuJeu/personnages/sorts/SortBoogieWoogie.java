package carteDuJeu.personnages.sorts;

import carteDuJeu.Carte;
import carteDuJeu.Case;
import carteDuJeu.ElementMobile;
import carteDuJeu.personnages.Personnage;
import carteDuJeu.personnages.classes.Classe;

public class SortBoogieWoogie extends Sort {
    public SortBoogieWoogie() {
        super("Boogie Woogie", "Le personnage détenteur du sort peut choisir deux personnages " +
                "(y compris lui-même), de deux monstres ou d'un personnage (y compris lui-même) " +
                "et d'un monstre et échanger leur position dans le donjon.");
    }

    @Override
    public boolean lancer(Carte carte, Personnage lanceur, ElementMobile[] cibles) {
        if (cibles == null || cibles.length != 2) return false;
        ElementMobile cible1 = cibles[0];
        ElementMobile cible2 = cibles[1];

        if (cible1 == null || cible2 == null) return false;

        Case case1 = carte.getCase(cible1);
        Case case2 = carte.getCase(cible2);
        if (case1 == null || case2 == null) return false;

        // Retirer les entités de leurs cases
        case1.retirerContenu(cible1);
        case2.retirerContenu(cible2);

        // Placer chaque entité dans la case de l'autre
        carte.ajouterContenu(case1.getX(), case1.getY(), cible2);
        carte.ajouterContenu(case2.getX(), case2.getY(), cible1);

        return true;
    }

    public boolean estUtilisablePar(Classe classe) {
        return classe.getNomClasse().equals("Magicien");
    }
}