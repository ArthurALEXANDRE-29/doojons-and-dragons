package carteDuJeu.personnages.equipements.armures;

/**
 * Représente l'armure Cotte de mailles.
 * La Cotte de mailles offre une protection de 11 et est considérée comme une armure lourde.
 */
public class CotteDeMailles extends Armure {
    /**
     * Construit une Cotte de mailles avec une protection de 11 et le statut d'armure lourde.
     */
    public CotteDeMailles() {
        super("Cotte de mailles", 11, true);
    }

    /**
     * Crée une copie de l'armure Cotte de mailles.
     * @return une nouvelle instance de CotteDeMailles
     */
    @Override
    public Armure copier() {
        CotteDeMailles copie = new CotteDeMailles();
        return copie;
    }
}