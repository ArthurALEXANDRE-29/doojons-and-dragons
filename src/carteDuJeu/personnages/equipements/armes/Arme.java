package carteDuJeu.personnages.equipements.armes;

import carteDuJeu.personnages.equipements.Equipement;

/**
 * Classe abstraite représentant une arme.
 * Une arme possède des dégâts, une portée, un statut de lourdeur, un nombre de dés d'attaque,
 * ainsi que des bonus d'attaque et de dégâts.
 */
public abstract class Arme extends Equipement {
    private final int m_degats;
    private final int m_portee;
    private final boolean m_estLourde;
    private final int m_desMax;
    private int m_bonusAttaque;
    private int m_bonusDegats;

    /**
     * Construit une arme avec les caractéristiques spécifiées.
     * @param nom le nom de l'arme
     * @param degats les dégâts de base de l'arme
     * @param portee la portée de l'arme
     * @param estLourde true si l'arme est lourde, false sinon
     * @param des le nombre de dés d'attaque de base
     */
    public Arme(String nom, int degats, int portee, boolean estLourde, int des) {
        super(nom);
        this.m_degats = degats;
        this.m_portee = portee;
        this.m_estLourde = estLourde;
        this.m_desMax = des;
        this.m_bonusAttaque = 0;
        this.m_bonusDegats = 0;
    }

    /**
     * Retourne les dégâts totaux de l'arme (base + bonus).
     * @return les dégâts totaux
     */
    public int getDegats() {
        return m_degats + m_bonusDegats;
    }

    /**
     * Retourne la portée de l'arme.
     * @return la portée
     */
    public int getPortee() {
        return m_portee;
    }

    /**
     * Indique si l'arme est lourde.
     * @return true si l'arme est lourde, false sinon
     */
    public boolean estLourde() {
        return m_estLourde;
    }

    /**
     * Retourne "oui" si l'arme est lourde, "non" sinon.
     * @return "oui" ou "non"
     */
    public String armeLourde() {
        return (m_estLourde) ? "oui" : "non";
    }

    /**
     * Retourne le nombre total de dés d'attaque (base + bonus).
     * @return le nombre de dés d'attaque
     */
    public int getDes() {
        return m_desMax + m_bonusAttaque;
    }

    /**
     * Ajoute un bonus aux dés d'attaque.
     * @param bonus le bonus à ajouter
     */
    public void ajouterBonusAttaque(int bonus) {
        this.m_bonusAttaque += bonus;
    }

    /**
     * Ajoute un bonus aux dégâts.
     * @param bonus le bonus à ajouter
     */
    public void ajouterBonusDegats(int bonus) {
        this.m_bonusDegats += bonus;
    }

    /**
     * Indique que l'objet est une arme.
     * @return true
     */
    @Override
    public boolean estUneArme() {
        return true;
    }

    /**
     * Indique que l'objet n'est pas une armure.
     * @return false
     */
    @Override
    public boolean estUneArmure() {
        return false;
    }

    /**
     * Retourne une représentation textuelle de l'arme.
     * @return une chaîne décrivant l'arme
     */
    @Override
    public String toString() {
        return getNom() +
                " | dégâts : " + m_degats +
                " | portée : " + m_portee +
                " | dés d'attaque : " + m_desMax +
                " | bonus dés : " + m_bonusAttaque +
                " | bonus dégâts : " + m_bonusDegats;
    }
}