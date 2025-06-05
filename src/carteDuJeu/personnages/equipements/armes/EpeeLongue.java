package carteDuJeu.personnages.equipements.armes;

/**
 * Représente l'arme Épée longue.
 * L'épée longue inflige 8 dégâts, a une portée de 1, est considérée comme une arme lourde
 * et utilise 1 dé d'attaque.
 */
public class EpeeLongue extends Arme {

    /**
     * Construit une épée longue avec 8 dégâts, une portée de 1, le statut d'arme lourde et 1 dé d'attaque.
     */
    public EpeeLongue() {
        super("Épée longue", 8, 1, true, 1);
    }

    /**
     * Crée une copie de l'épée longue.
     * @return une nouvelle instance de EpeeLongue
     */
    @Override
    public Arme copier() {
        return new EpeeLongue();
    }
}