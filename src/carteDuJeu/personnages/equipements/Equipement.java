package carteDuJeu.personnages.equipements;


import carteDuJeu.ElementCarte;

public abstract class Equipement implements ElementCarte {
    protected String m_nom;

    public Equipement(String nom) {
        this.m_nom = nom;
    }

    public String getNom() {
        return m_nom;
    }

    @Override
    public String getSymbole() {
        return "*"; // Symbole spécifique pour l'equipement
    }
}