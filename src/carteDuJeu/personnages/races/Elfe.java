package carteDuJeu.personnages.races;

/**
 * Représente la race Elfe.
 * L'Elfe bénéficie d'un bonus de +6 en dextérité.
 */
public class Elfe extends Race {
    /**
     * Construit un Elfe avec un bonus de 6 en dextérité.
     */
    public Elfe() {
        super("Elfe", 0, 6, 0, 0);
    }
}