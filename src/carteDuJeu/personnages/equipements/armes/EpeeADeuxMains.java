package carteDuJeu.personnages.equipements.armes;

/**
 * Représente l'arme Épée à deux mains.
 * L'épée à deux mains inflige 6 dégâts, a une portée de 1, est considérée comme une arme lourde
 * et utilise 2 dés d'attaque.
 */
public class EpeeADeuxMains extends Arme {

    /**
     * Construit une épée à deux mains avec 6 dégâts, une portée de 1, le statut d'arme lourde et 2 dés d'attaque.
     */
    public EpeeADeuxMains() {
        super("Épée à deux mains", 6, 1, true, 2);
    }

    /**
     * Crée une copie de l'épée à deux mains.
     * @return une nouvelle instance de EpeeADeuxMains
     */
    @Override
    public Arme copier() {
        return new EpeeADeuxMains();
    }
}