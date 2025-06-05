package carteDuJeu.personnages.equipements.armures;

/**
 * Représente l'armure Demi-plate.
 * La Demi-plate offre une protection de 10 et n'est pas considérée comme une armure lourde.
 */
public class DemiPlate extends Armure {
    /**
     * Construit une Demi-plate avec une protection de 10 et le statut d'armure non lourde.
     */
    public DemiPlate() {
        super("Demi-plate", 10, false);
    }

    /**
     * Crée une copie de l'armure Demi-plate.
     * @return une nouvelle instance de DemiPlate
     */
    @Override
    public Armure copier() {
        DemiPlate copie = new DemiPlate();
        return copie;
    }
}