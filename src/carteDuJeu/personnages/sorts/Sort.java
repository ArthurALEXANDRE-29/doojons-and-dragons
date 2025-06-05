package carteDuJeu.personnages.sorts;

import carteDuJeu.Carte;
import carteDuJeu.ElementMobile;
import carteDuJeu.personnages.Personnage;
import carteDuJeu.personnages.classes.Classe;

/**
 * Classe abstraite représentant un sort utilisable par un personnage.
 * Un sort possède un nom, une description, et définit les méthodes à implémenter
 * pour savoir si une classe peut l'utiliser et pour lancer le sort.
 */
public abstract class Sort {
    private String m_nom;
    private String m_description;

    /**
     * Construit un sort avec un nom et une description.
     * @param nom le nom du sort
     * @param description la description du sort
     */
    public Sort(String nom, String description) {
        this.m_nom = nom;
        this.m_description = description;
    }

    /**
     * Retourne le nom du sort.
     * @return le nom du sort
     */
    public String getNom() {
        return m_nom;
    }

    /**
     * Indique si le sort peut être utilisé par la classe spécifiée.
     * @param classe la classe du personnage
     * @return true si la classe peut utiliser le sort, false sinon
     */
    public abstract boolean estUtilisablePar(Classe classe);

    /**
     * Lance le sort sur les cibles spécifiées.
     * @param carte la carte du jeu
     * @param lanceur le personnage lançant le sort
     * @param cibles les éléments mobiles ciblés par le sort
     * @return true si le sort a eu un effet, false sinon
     */
    public abstract boolean lancer(Carte carte, Personnage lanceur, ElementMobile[] cibles);

    /**
     * Retourne le nom et la description du sort.
     * @return le nom et la description du sort
     */
    @Override
    public String toString() {
        return m_nom + " : " + m_description;
    }

}