package carteDuJeu.personnages.equipements.armes;

public class ArbaleteLegere extends Arme {

    public ArbaleteLegere() {
        super("Arbalète légère", 8, 16, false,1);
    }
    @Override
    public Arme copier() {
        ArbaleteLegere copie = new ArbaleteLegere();
        return copie;
    }
}
