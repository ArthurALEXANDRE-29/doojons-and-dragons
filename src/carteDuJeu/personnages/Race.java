package carteDuJeu.personnages;


public enum Race {
    HUMAIN(2, 2, 2, 2),    // Humain : bonus de base
    NAIN(6, 0, 0, 0),      // Nain : +6 en Force
    ELFE(0, 6, 0, 0),      // Elfe : +6 en Dextérité
    HALFELIN(0, 4, 2, 0);  // Halfelin : +4 en Dextérité et +2 en Vitesse

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
