package carteDuJeu.personnages.equipements.armes;

/**
 * Représente l'arme Bâton.
 * Le bâton inflige 6 dégâts, a une portée de 1, n'est pas considéré comme une arme lourde
 * et utilise 1 dé d'attaque.
 */
public class Baton extends Arme {

    /**
     * Construit un bâton avec 6 dégâts, une portée de 1, le statut d'arme non lourde et 1 dé d'attaque.
     */
    public Baton() {
        super("Bâton", 6, 1, false, 1);
    }

    /**
     * Crée une copie du bâton.
     * @return une nouvelle instance de Baton
     */
    @Override
    public Arme copier() {
        return new Baton();
    }
}