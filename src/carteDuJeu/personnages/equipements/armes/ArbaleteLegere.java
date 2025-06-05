package carteDuJeu.personnages.equipements.armes;

/**
 * Représente l'arme Arbalète légère.
 * L'arbalète légère inflige 8 dégâts, a une portée de 16, n'est pas considérée comme une arme lourde
 * et utilise 1 dé d'attaque.
 */
public class ArbaleteLegere extends Arme {

    /**
     * Construit une arbalète légère avec 8 dégâts, une portée de 16, le statut d'arme non lourde et 1 dé d'attaque.
     */
    public ArbaleteLegere() {
        super("Arbalète légère", 8, 16, false, 1);
    }

    /**
     * Crée une copie de l'arbalète légère.
     * @return une nouvelle instance de ArbaleteLegere
     */
    @Override
    public Arme copier() {
        return new ArbaleteLegere();
    }
}