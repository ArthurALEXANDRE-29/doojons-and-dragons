package carteDuJeu.personnages.races;

/**
 * Représente la race Halfelin.
 * Le Halfelin bénéficie d'un bonus de +4 en dextérité et de +2 en vitesse.
 */
public class Halfelin extends Race {
    /**
     * Construit un Halfelin avec un bonus de 4 en dextérité et 2 en vitesse.
     */
    public Halfelin() {
        super("Halfelin", 0, 4, 2, 0);
    }
}