package carteDuJeu.personnages.equipements;


public abstract class Equipement {
    protected String m_nom;

    public Equipement(String nom) {
        this.m_nom = nom;
    }

    public String getNom() {
        return m_nom;
    }
}