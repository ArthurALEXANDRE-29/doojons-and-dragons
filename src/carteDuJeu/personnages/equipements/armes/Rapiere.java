package carteDuJeu.personnages.equipements.armes;

/**
 * Représente l'arme Rapière.
 * La rapière inflige 8 dégâts, a une portée de 1, est considérée comme une arme lourde
 * et utilise 1 dé d'attaque.
 */
public class Rapiere extends Arme {

    /**
     * Construit une rapière avec 8 dégâts, une portée de 1, le statut d'arme lourde et 1 dé d'attaque.
     */
    public Rapiere() {
        super("Rapière", 8, 1, true, 1);
    }

    /**
     * Crée une copie de la rapière.
     * @return une nouvelle instance de Rapiere
     */
    @Override
    public Arme copier() {
        Rapiere copie = new Rapiere();
        return copie;
    }
}