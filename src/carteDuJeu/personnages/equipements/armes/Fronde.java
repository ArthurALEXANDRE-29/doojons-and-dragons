package carteDuJeu.personnages.equipements.armes;

public class Fronde extends Arme {

    public Fronde() {
        super("Fronde", 4, 6, false,1);
    }

    public Arme copier() {
        Fronde copie = new Fronde();
        return copie;
    }
}
