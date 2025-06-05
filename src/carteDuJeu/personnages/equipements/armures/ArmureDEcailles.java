package carteDuJeu.personnages.equipements.armures;

/**
 * Représente l'armure d'écailles.
 * L'armure d'écailles offre une protection de 9 et n'est pas considérée comme une armure lourde.
 */
public class ArmureDEcailles extends Armure {

    /**
     * Construit une armure d'écailles avec une protection de 9 et le statut d'armure non lourde.
     */
    public ArmureDEcailles() {
        super("Armure d'écailles", 9, false);
    }

    /**
     * Crée une copie de l'armure d'écailles.
     * @return une nouvelle instance de ArmureDEcailles
     */
    @Override
    public Armure copier() {
        ArmureDEcailles copie = new ArmureDEcailles();
        return copie;
    }
}