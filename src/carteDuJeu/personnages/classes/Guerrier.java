package carteDuJeu.personnages.classes;

import carteDuJeu.personnages.equipements.Equipement;
import carteDuJeu.personnages.equipements.armes.ArbaleteLegere;
import carteDuJeu.personnages.equipements.armes.EpeeLongue;
import carteDuJeu.personnages.equipements.armures.CotteDeMailles;

import java.util.ArrayList;
import java.util.List;

public class Guerrier extends Classe {

    public Guerrier() {
        super("Guerrier", 20, 1, 0, 0);
    }

    @Override
    public List<Equipement> getEquipementInitial() {
        List<Equipement> equipements = new ArrayList<>();
        equipements.add(new CotteDeMailles());
        equipements.add(new EpeeLongue());
        equipements.add(new ArbaleteLegere());
        return equipements;
    }
}
