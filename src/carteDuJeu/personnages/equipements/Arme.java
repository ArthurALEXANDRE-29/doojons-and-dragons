package carteDuJeu.personnages.equipements;

public class Arme extends Equipement {
    private String m_degats;
    private int m_portee;
    private boolean m_estLourde;

    public Arme(ArmeType type) {
        super(type.getNom()); // Hérite du nom depuis Equipement
        this.m_degats = type.getDegats();
        this.m_portee = type.getPortee();
        this.m_estLourde = type.isLourde();
    }

    // Getters
    public String getDegats() {
        return m_degats;
    }

    public int getPortee() {
        return m_portee;
    }

    public boolean estLourde() {
        return m_estLourde;
    }

    @Override
    public String toString() {
        return "Arme{" +
                "nom='" + getNom() + '\'' +
                ", degats='" + m_degats + '\'' +
                ", portee=" + m_portee +
                ", estLourde=" + m_estLourde +
                '}';
    }
}