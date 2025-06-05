package carteDuJeu.personnages.equipements.armes;

public class MasseDarmes extends Arme {

    public MasseDarmes() {
        super("Masse d'armes", 6, 1, false,1);
    }
    @Override
    public Arme copier() {
        MasseDarmes copie = new MasseDarmes();
        return copie;
    }
}
