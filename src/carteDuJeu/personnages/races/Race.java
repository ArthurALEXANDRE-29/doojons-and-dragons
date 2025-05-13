package carteDuJeu.personnages.races;


public abstract class Race {

    private final int m_forceBonus;
    private final int m_dexteriteBonus;
    private final int m_vitesseBonus;
    private final int m_initiativeBonus;

    // Constructeur de l'énumération avec les bonus
    Race(int forceBonus, int dexteriteBonus, int vitesseBonus, int initiativeBonus) {
        this.m_forceBonus = forceBonus;
        this.m_dexteriteBonus = dexteriteBonus;
        this.m_vitesseBonus = vitesseBonus;
        this.m_initiativeBonus = initiativeBonus;
    }

    // Getter pour chaque bonus
    public int getForceBonus() {
        return m_forceBonus;
    }

    public int getDexteriteBonus() {
        return m_dexteriteBonus;
    }

    public int getVitesseBonus() {
        return m_vitesseBonus;
    }

    public int getInitiativeBonus() {
        return m_initiativeBonus;
    }
}
