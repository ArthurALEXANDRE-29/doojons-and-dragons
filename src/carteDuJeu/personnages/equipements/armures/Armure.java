package carteDuJeu.personnages.equipements.armures;

import carteDuJeu.personnages.equipements.Equipement;

/**
 * Classe abstraite représentant une armure.
 * Une armure possède une classe d'armure (valeur de protection) et peut être lourde ou non.
 */
public abstract class Armure extends Equipement {
    private int m_classeArmure;  // La classe d'armure (par exemple, 9, 10, etc.)
    private boolean m_estLourde; // Si l'armure est lourde ou non

    /**
     * Construit une armure avec le nom, la classe d'armure et le statut de lourdeur spécifiés.
     * @param nom le nom de l'armure
     * @param classeArmure la valeur de protection de l'armure
     * @param estLourde true si l'armure est lourde, false sinon
     */
    public Armure(String nom, int classeArmure, boolean estLourde) {
        super(nom);
        this.m_classeArmure = classeArmure;
        this.m_estLourde = estLourde;
    }

    /**
     * Retourne la classe d'armure (valeur de protection).
     * @return la classe d'armure
     */
    public int getClasseArmure() {
        return m_classeArmure;
    }

    /**
     * Indique si l'armure est lourde.
     * @return true si l'armure est lourde, false sinon
     */
    public boolean estLourde() {
        return m_estLourde;
    }

    /**
     * Retourne "oui" si l'armure est lourde, "non" sinon.
     * @return "oui" ou "non"
     */
    public String armureLourde() {
        return (m_estLourde) ? "oui" : "non";
    }

    /**
     * Retourne une représentation textuelle de l'armure.
     * @return une chaîne décrivant l'armure
     */
    @Override
    public String toString() {
        return getNom() +
                " | classe d'armure : " + m_classeArmure +
                " | armure lourde : " + armureLourde();
    }

    /**
     * Indique que l'armure n'est pas une arme.
     * @return false
     */
    @Override
    public boolean estUneArme() {
        return false;
    }

    /**
     * Indique que l'objet est une armure.
     * @return true
     */
    @Override
    public boolean estUneArmure() {
        return true;
    }
}