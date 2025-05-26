package carteDuJeu.personnages.equipements.armures;

public class Harnois extends Armure {
    public Harnois()
    {
        super("Harnois", 12, true);
    }
    public Armure copier() {
        Harnois copie = new Harnois();
        return copie;
    }
}
