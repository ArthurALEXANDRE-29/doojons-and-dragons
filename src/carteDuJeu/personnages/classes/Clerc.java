package carteDuJeu.personnages.classes;

import carteDuJeu.personnages.equipements.Equipement;
import carteDuJeu.personnages.equipements.armes.*;
import carteDuJeu.personnages.equipements.armures.*;

import java.util.ArrayList;
import java.util.List;

public class Clerc extends Classe{

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
