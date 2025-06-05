package carteDuJeu.personnages.sorts;

import carteDuJeu.Carte;
import carteDuJeu.Case;
import carteDuJeu.ElementMobile;
import carteDuJeu.personnages.Personnage;
import carteDuJeu.personnages.classes.Classe;

/**
 * Sort "Boogie Woogie" permettant d'échanger la position de deux éléments mobiles sur la carte.
 * Le lanceur peut choisir deux personnages (lui-même inclus), deux monstres,
 * ou un personnage (lui-même inclus) et un monstre, et échanger leur position dans le donjon.
 * Ce sort n'est utilisable que par la classe Magicien.
 */
public class SortBoogieWoogie extends Sort {
    /**
     * Construit le sort Boogie Woogie avec son nom et sa description.
     */
    public SortBoogieWoogie() {
        super("Boogie Woogie", "Le personnage détenteur du sort peut choisir deux personnages " +
                "(y compris lui-même), de deux monstres ou d'un personnage (y compris lui-même) " +
                "et d'un monstre et échanger leur position dans le donjon.");
    }

    /**
     * Lance le sort Boogie Woogie sur deux cibles : échange leur position sur la carte.
     * @param carte la carte du jeu
     * @param lanceur le personnage lançant le sort
     * @param cibles tableau de deux éléments mobiles à échanger
     * @return true si l'échange a réussi, false sinon
     */
    @Override
    public boolean lancer(Carte carte, Personnage lanceur, ElementMobile[] cibles) {
        if (cibles == null || cibles.length != 2) return false;
        ElementMobile cible1 = cibles[0];
        ElementMobile cible2 = cibles[1];

        if (cible1 == null || cible2 == null) return false;

        try {
            Case case1 = carte.getCase(cible1)
                    .orElseThrow(() -> new IllegalArgumentException("Case de la cible 1 introuvable"));
            Case case2 = carte.getCase(cible2)
                    .orElseThrow(() -> new IllegalArgumentException("Case de la cible 2 introuvable"));

            // Retirer les entités de leurs cases
            case1.retirerContenu(cible1);
            case2.retirerContenu(cible2);

            // Placer chaque entité dans la case de l'autre
            carte.ajouterContenu(case1.getX(), case1.getY(), cible2);
            carte.ajouterContenu(case2.getX(), case2.getY(), cible1);

            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * Indique si le sort peut être utilisé par la classe spécifiée.
     * @param classe la classe du personnage
     * @return true si la classe est Magicien, false sinon
     */
    @Override
    public boolean estUtilisablePar(Classe classe) {
        return classe.getNomClasse().equals("Magicien");
    }
}