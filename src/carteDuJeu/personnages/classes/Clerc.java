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
    public List<Equipement> getEquipementInitial() {
        List<Equipement> equipements = new ArrayList<>();
        equipements.add(new MasseDarmes());
        equipements.add(new ArmureDEcailles());
        equipements.add(new ArbaleteLegere());
        return equipements;
    }
}
