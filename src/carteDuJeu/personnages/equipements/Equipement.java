package carteDuJeu.personnages.equipements;

import carteDuJeu.ElementCarte;

/**
 * Classe abstraite représentant un équipement.
 * Un équipement possède un nom et peut être une arme ou une armure.
 * Fournit des méthodes pour obtenir le nom, vérifier le type d'équipement,
 * copier l'équipement et obtenir un symbole pour l'affichage.
 */
public abstract class Equipement implements ElementCarte {
    protected String m_nom;

    /**
     * Construit un équipement avec le nom spécifié.
     * @param nom le nom de l'équipement
     */
    public Equipement(String nom) {
        this.m_nom = nom;
    }

    /**
     * Indique si l'équipement est une arme.
     * @return false par défaut, à redéfinir dans les sous-classes
     */
    public boolean estUneArme() {
        return false;
    }

    /**
     * Crée une copie de l'équipement.
     * @return une nouvelle instance de l'équipement
     */
    public abstract Equipement copier();

    /**
     * Indique si l'équipement est une armure.
     * @return false par défaut, à redéfinir dans les sous-classes
     */
    public boolean estUneArmure() {
        return false;
    }

    /**
     * Retourne le nom de l'équipement.
     * @return le nom de l'équipement
     */
    @Override
    public String getNom() {
        return m_nom;
    }

    /**
     * Retourne le symbole représentant l'équipement pour l'affichage.
     * @return le symbole de l'équipement
     */
    @Override
    public String getSymbole() {
        return " *  ";
    }

    /**
     * Indique si l'objet est un équipement.
     * @return true
     */
    @Override
    public boolean estEquipement() {
        return true;
    }

    /**
     * Indique si l'équipement est un élément mobile.
     * @return false
     */
    @Override
    public boolean estElementMobile() {
        return false;
    }

    /**
     * Retourne une représentation textuelle de l'équipement pour l'affichage.
     * @return le nom de l'équipement
     */
    @Override
    public String toString() {
        return m_nom;
    }
}