package carteDuJeu.personnages.classes;

import carteDuJeu.personnages.equipements.armes.Rapiere;
import carteDuJeu.personnages.equipements.armes.ArcCourt;

/**
 * Représente la classe Roublard.
 * Le roublard possède 16 points de vie,
 * et commence avec une rapière et un arc court.
 */
public class Roublard extends Classe {

    public Roublard() {
        super("Roublard", 16, 0, 0, 0);
    }

    @Override
    protected void initialiserEquipement() {
        m_equipementInitial.add(new Rapiere());
        m_equipementInitial.add(new ArcCourt());
    }
}