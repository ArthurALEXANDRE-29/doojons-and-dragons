package carteDuJeu.personnages.equipements.armures;

public class DemiPlate extends Armure {
    public DemiPlate()
    {
        super("Demi-plate", 10, false);
    }
    @Override
    public Armure copier() {
        DemiPlate copie = new DemiPlate();
        return copie;
    }
}
