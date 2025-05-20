package carteDuJeu.personnages.sorts;

import carteDuJeu.Carte;
import carteDuJeu.ElementMobile;
import carteDuJeu.personnages.Personnage;


public abstract class Sort {
    private String m_nom;
    private String m_description;

    public Sort(String nom, String description) {
        this.m_nom = nom;
        this.m_description = description;
    }

    public abstract boolean lancer(Carte carte, Personnage lanceur, ElementMobile[] cibles);
}