package carteDuJeu.personnages.equipements;

public class Arme extends Equipement {
    private String degats;
    private int portee;
    private boolean estLourde;

    public Arme(ArmeType type) {
        super(type.getNom()); // Hérite du nom depuis Equipement
        this.degats = type.getDegats();
        this.portee = type.getPortee();
        this.estLourde = type.isLourde();
    }

    // Getters
    public String getDegats() {
        return degats;
    }

    public int getPortee() {
        return portee;
    }

    public boolean estLourde() {
        return estLourde;
    }

    @Override
    public String toString() {
        return "Arme{" +
                "nom='" + getNom() + '\'' +
                ", degats='" + degats + '\'' +
                ", portee=" + portee +
                ", estLourde=" + estLourde +
                '}';
    }
}