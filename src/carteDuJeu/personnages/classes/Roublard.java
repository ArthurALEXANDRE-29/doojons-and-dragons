package carteDuJeu.personnages.classes;

import carteDuJeu.personnages.equipements.Equipement;
import carteDuJeu.personnages.equipements.armes.Rapiere;
import carteDuJeu.personnages.equipements.armes.ArcCourt;

import java.util.ArrayList;
import java.util.List;

public class Roublard extends Classe {

    public Roublard() {
        super("Roublard", 16, 0, 0, 0);
    }

    @Override
    public List<Equipement> getEquipementInitial() {
        List<Equipement> equipements = new ArrayList<>();
        equipements.add(new Rapiere());
        equipements.add(new ArcCourt());
        return equipements;
    }
}
