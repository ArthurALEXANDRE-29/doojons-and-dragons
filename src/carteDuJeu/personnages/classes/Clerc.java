package carteDuJeu.personnages.classes;

import carteDuJeu.personnages.equipements.armes.MasseDarmes;
import carteDuJeu.personnages.equipements.armes.ArbaleteLegere;
import carteDuJeu.personnages.equipements.armures.ArmureDEcailles;

/**
 * Représente la classe Clerc.
 * Le clerc possède 16 points de vie,
 * et commence avec une masse d'armes, une armure d'écailles et une arbalète légère.
 */
public class Clerc extends Classe {

    public Clerc() {
        super("Clerc", 16, 0, 0, 0);
    }

    @Override
    protected void initialiserEquipement() {
        m_equipementInitial.add(new MasseDarmes());
        m_equipementInitial.add(new ArmureDEcailles());
        m_equipementInitial.add(new ArbaleteLegere());
    }
}