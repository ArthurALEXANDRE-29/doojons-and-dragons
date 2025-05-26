package carteDuJeu.personnages.equipements.armures;

import carteDuJeu.personnages.equipements.armes.Arme;

public class ArmureDEcailles extends Armure{

    public ArmureDEcailles(){
        super("Armure d'écailles", 9, false);
    }
    public Armure copier() {
        ArmureDEcailles copie = new ArmureDEcailles();
        return copie;
    }
}
