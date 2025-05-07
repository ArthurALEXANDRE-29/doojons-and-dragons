package carteDuJeu.personnages.equipements;


public abstract class Equipement {
    protected String nom;

    public Equipement(String nom) {
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }
}