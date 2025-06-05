package carteDuJeu.personnages.classes;

import carteDuJeu.personnages.equipements.armes.Baton;
import carteDuJeu.personnages.equipements.armes.Fronde;

/**
 * Représente la classe Magicien.
 * Le magicien possède 12 points de vie,
 * et commence avec un bâton et une fronde.
 */
public class Magicien extends Classe {

    public Magicien() {
        super("Magicien", 12, 0, 0, 0);
    }

    @Override
    protected void initialiserEquipement() {
        m_equipementInitial.add(new Baton());
        m_equipementInitial.add(new Fronde());
    }
}