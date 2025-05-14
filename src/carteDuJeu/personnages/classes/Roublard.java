package carteDuJeu.personnages.classes;

import carteDuJeu.personnages.equipements.armes.Rapiere;
import carteDuJeu.personnages.equipements.armes.ArcCourt;

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
