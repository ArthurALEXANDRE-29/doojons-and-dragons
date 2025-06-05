package carteDuJeu.personnages.classes;

import carteDuJeu.personnages.equipements.armes.ArbaleteLegere;
import carteDuJeu.personnages.equipements.armes.EpeeLongue;
import carteDuJeu.personnages.equipements.armures.CotteDeMailles;

/**
 * Représente la classe Guerrier.
 * Le guerrier possède 20 points de vie,
 * et commence avec une cotte de mailles, une épée longue et une arbalète légère.
 */
public class Guerrier extends Classe {

    public Guerrier() {
        super("Guerrier", 20, 0, 0, 0);
    }

    @Override
    protected void initialiserEquipement() {
        m_equipementInitial.add(new CotteDeMailles());
        m_equipementInitial.add(new EpeeLongue());
        m_equipementInitial.add(new ArbaleteLegere());
    }
}