package carteDuJeu.personnages.equipements.armes;

import carteDuJeu.personnages.equipements.Equipement;

public abstract class Arme extends Equipement {
    private final int m_degats;
    private final int m_portee;
    private final boolean m_estLourde;

    public Arme(String nom, int degats, int portee, boolean estLourde) {
        super(nom); // Le nom est transmis à la classe Equipement
        this.m_degats = degats;
        this.m_portee = portee;
        this.m_estLourde = estLourde;
    }

    public int getDegats() {
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