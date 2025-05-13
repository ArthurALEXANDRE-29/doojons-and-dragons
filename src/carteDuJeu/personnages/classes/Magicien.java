package carteDuJeu.personnages.classes;

import carteDuJeu.personnages.equipements.Equipement;
import carteDuJeu.personnages.equipements.armes.Baton;
import carteDuJeu.personnages.equipements.armes.Fronde;

import java.util.ArrayList;
import java.util.List;

public class Magicien extends Classe {

    public Magicien() {
        super("Magicien", 12, 0, 0, 0);
    }

    @Override
    public List<Equipement> getEquipementInitial() {
        List<Equipement> equipements = new ArrayList<>();
        equipements.add(new Baton());
        equipements.add(new Fronde());
        return equipements;
    }
}
