package carteDuJeu.personnages.equipements.armes;

/**
 * Représente l'arme Arc court.
 * L'arc court inflige 6 dégâts, a une portée de 16, n'est pas considéré comme une arme lourde
 * et utilise 1 dé d'attaque.
 */
public class ArcCourt extends Arme {

    /**
     * Construit un arc court avec 6 dégâts, une portée de 16, le statut d'arme non lourde et 1 dé d'attaque.
     */
    public ArcCourt() {
        super("Arc court", 6, 16, false, 1);
    }

    /**
     * Crée une copie de l'arc court.
     * @return une nouvelle instance de ArcCourt
     */
    @Override
    public Arme copier() {
        return new ArcCourt();
    }
}