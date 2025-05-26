package carteDuJeu.personnages.equipements.armures;

public class CotteDeMailles extends Armure{
    public CotteDeMailles(){
        super("Cotte de mailles", 11, true);
    }
    public Armure copier() {
        CotteDeMailles copie = new CotteDeMailles();
        return copie;
    }
}
