package carteDuJeu.personnages.sorts;

import carteDuJeu.Carte;
import carteDuJeu.ElementMobile;
import carteDuJeu.personnages.Personnage;
import carteDuJeu.personnages.classes.Classe;


public abstract class Sort {
    private String m_nom;
    private String m_description;

    public Sort(String nom, String description) {
        this.m_nom = nom;
        this.m_description = description;
    }

    public abstract boolean estUtilisablePar(Classe classe);

    public abstract boolean lancer(Carte carte, Personnage lanceur, ElementMobile[] cibles);
}