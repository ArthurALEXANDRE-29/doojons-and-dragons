package carteDuJeu.personnages.equipements.armes;

/**
 * Représente l'arme Fronde.
 * La fronde inflige 4 dégâts, a une portée de 6, n'est pas considérée comme une arme lourde
 * et utilise 1 dé d'attaque.
 */
public class Fronde extends Arme {

    /**
     * Construit une fronde avec 4 dégâts, une portée de 6, le statut d'arme non lourde et 1 dé d'attaque.
     */
    public Fronde() {
        super("Fronde", 4, 6, false, 1);
    }

    /**
     * Crée une copie de la fronde.
     * @return une nouvelle instance de Fronde
     */
    @Override
    public Arme copier() {
        return new Fronde();
    }
}