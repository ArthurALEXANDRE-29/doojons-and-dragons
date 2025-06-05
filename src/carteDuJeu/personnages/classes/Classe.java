package carteDuJeu.personnages.classes;

import carteDuJeu.personnages.equipements.Equipement;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe abstraite représentant une classe de personnage du jeu.
 * Contient les informations de base telles que le nom, les points de vie,
 * les bonus de caractéristiques et l'équipement initial.
 */
public abstract class Classe {
    private final String m_nomClasse;
    private final int m_pointsDeVie;
    private final int m_forceBonus;
    private final int m_dexteriteBonus;
    private final int m_initiativeBonus;
    protected List<Equipement> m_equipementInitial;

    /**
     * Constructeur de la classe de personnage.
     *
     * @param nom Nom de la classe
     * @param pointsDeVie Points de vie de base
     * @param forceBonus Bonus de force
     * @param dexteriteBonus Bonus de dextérité
     * @param initiativeBonus Bonus d'initiative
     */
    Classe(String nom, int pointsDeVie, int forceBonus, int dexteriteBonus, int initiativeBonus) {
        this.m_nomClasse = nom;
        this.m_pointsDeVie = pointsDeVie;
        this.m_forceBonus = forceBonus;
        this.m_dexteriteBonus = dexteriteBonus;
        this.m_initiativeBonus = initiativeBonus;
        this.m_equipementInitial = new ArrayList<>();
        initialiserEquipement();
    }

    /**
     * Initialise l'équipement de départ de la classe.
     */
    protected abstract void initialiserEquipement();

    /**
     * Retourne la liste de l'équipement initial.
     *
     * @return Liste d'équipement
     */
    public List<Equipement> getEquipementInitial() {
        return m_equipementInitial;
    }

    /**
     * Retourne le nom de la classe.
     *
     * @return Nom de la classe
     */
    public String getNomClasse() {
        return m_nomClasse;
    }

    /**
     * Retourne les points de vie de base.
     *
     * @return Points de vie
     */
    public int getPointsDeVie() {
        return m_pointsDeVie;
    }

    /**
     * Retourne le bonus de force.
     *
     * @return Bonus de force
     */
    public int getForceBonus() {
        return m_forceBonus;
    }

    /**
     * Retourne le bonus de dextérité.
     *
     * @return Bonus de dextérité
     */
    public int getDexteriteBonus() {
        return m_dexteriteBonus;
    }

    /**
     * Retourne le bonus d'initiative.
     *
     * @return Bonus d'initiative
     */
    public int getInitiativeBonus() {
        return m_initiativeBonus;
    }

    /**
     * Retourne une représentation textuelle de la classe de personnage.
     *
     * @return Chaîne décrivant la classe
     */
    @Override
    public String toString() {
        return "Classe{" +
                "nom='" + m_nomClasse + '\'' +
                ", pointsDeVie=" + m_pointsDeVie +
                ", forceBonus=" + m_forceBonus +
                ", dexteriteBonus=" + m_dexteriteBonus +
                ", initiativeBonus=" + m_initiativeBonus +
                ", equipementInitial=" + m_equipementInitial +
                '}';
    }
}