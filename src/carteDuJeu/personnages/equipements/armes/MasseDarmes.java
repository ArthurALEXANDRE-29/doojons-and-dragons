package carteDuJeu.personnages.equipements.armes;

/**
 * Représente l'arme Masse d'armes.
 * La masse d'armes inflige 6 dégâts, a une portée de 1, n'est pas considérée comme une arme lourde
 * et utilise 1 dé d'attaque.
 */
public class MasseDarmes extends Arme {

    /**
     * Construit une masse d'armes avec 6 dégâts, une portée de 1, le statut d'arme non lourde et 1 dé d'attaque.
     */
    public MasseDarmes() {
        super("Masse d'armes", 6, 1, false, 1);
    }

    /**
     * Crée une copie de la masse d'armes.
     * @return une nouvelle instance de MasseDarmes
     */
    @Override
    public Arme copier() {
        return new MasseDarmes();
    }
}