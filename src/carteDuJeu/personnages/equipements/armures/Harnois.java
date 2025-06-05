package carteDuJeu.personnages.equipements.armures;

/**
 * Représente l'armure Harnois.
 * Le Harnois offre une protection de 12 et est considéré comme une armure lourde.
 */
public class Harnois extends Armure {
    /**
     * Construit un Harnois avec une protection de 12 et le statut d'armure lourde.
     */
    public Harnois() {
        super("Harnois", 12, true);
    }

    /**
     * Crée une copie de l'armure Harnois.
     * @return une nouvelle instance de Harnois
     */
    @Override
    public Armure copier() {
        Harnois copie = new Harnois();
        return copie;
    }
}