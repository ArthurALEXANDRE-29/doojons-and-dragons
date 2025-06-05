package carteDuJeu.personnages.equipements.armes;

public class EpeeLongue extends Arme {

    public EpeeLongue() {
        super("Épée longue", 8, 1, true,1);
    }
    @Override
    public Arme copier() {
        EpeeLongue copie = new EpeeLongue();
        return copie;
    }
}
