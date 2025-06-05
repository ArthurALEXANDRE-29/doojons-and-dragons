package carteDuJeu.personnages.races;

/**
 * Classe abstraite représentant une race de personnage.
 * Chaque race possède un nom et des bonus de caractéristiques (force, dextérité, vitesse, initiative).
 */
public abstract class Race {
    private final String m_nomRace;
    private final int m_forceBonus;
    private final int m_dexteriteBonus;
    private final int m_vitesseBonus;
    private final int m_initiativeBonus;

    /**
     * Construit une race avec son nom et ses bonus de caractéristiques.
     * @param nom le nom de la race
     * @param forceBonus le bonus de force
     * @param dexteriteBonus le bonus de dextérité
     * @param vitesseBonus le bonus de vitesse
     * @param initiativeBonus le bonus d'initiative
     */
    Race(String nom, int forceBonus, int dexteriteBonus, int vitesseBonus, int initiativeBonus) {
        this.m_nomRace = nom;
        this.m_forceBonus = forceBonus;
        this.m_dexteriteBonus = dexteriteBonus;
        this.m_vitesseBonus = vitesseBonus;
        this.m_initiativeBonus = initiativeBonus;
    }

    /**
     * Retourne le nom de la race.
     * @return le nom de la race
     */
    public String getNomRace() {
        return m_nomRace;
    }

    /**
     * Retourne le bonus de force de la race.
     * @return le bonus de force
     */
    public int getForceBonus() {
        return m_forceBonus;
    }

    /**
     * Retourne le bonus de dextérité de la race.
     * @return le bonus de dextérité
     */
    public int getDexteriteBonus() {
        return m_dexteriteBonus;
    }

    /**
     * Retourne le bonus de vitesse de la race.
     * @return le bonus de vitesse
     */
    public int getVitesseBonus() {
        return m_vitesseBonus;
    }

    /**
     * Retourne le bonus d'initiative de la race.
     * @return le bonus d'initiative
     */
    public int getInitiativeBonus() {
        return m_initiativeBonus;
    }
}