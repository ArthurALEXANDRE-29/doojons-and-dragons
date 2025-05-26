package carteDuJeu.personnages.equipements.armes;

public class EpeeADeuxMains extends Arme{
    public EpeeADeuxMains() {
        super("Épée à deux mains", 6, 1, true,2);
    }
    public Arme copier() {
        EpeeADeuxMains copie = new EpeeADeuxMains();
        return copie;
    }
}
