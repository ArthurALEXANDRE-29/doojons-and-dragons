package carteDuJeu.personnages.equipements.armes;

public class Rapiere extends Arme {

    public Rapiere() {
        super("Rapière", 8, 1, true,1);
    }
    public Arme copier() {
        Rapiere copie = new Rapiere();
        return copie;
    }
}
