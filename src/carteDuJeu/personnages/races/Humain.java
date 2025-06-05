package carteDuJeu.personnages.races;

/**
 * Représente la race Humain.
 * L'Humain bénéficie d'un bonus de +2 en force, dextérité, vitesse et initiative.
 */
public class Humain extends Race {
    /**
     * Construit un Humain avec un bonus de 2 en force, dextérité, vitesse et initiative.
     */
    public Humain() {
        super("Humain", 2, 2, 2, 2);
    }
}