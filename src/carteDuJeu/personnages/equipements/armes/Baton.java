package carteDuJeu.personnages.equipements.armes;


public class Baton extends Arme{


    public Baton() {
        super("Bâton", 6, 1, false,1);
    }
    public Arme copier() {
        Baton copie = new Baton();
        return copie;
    }

}
