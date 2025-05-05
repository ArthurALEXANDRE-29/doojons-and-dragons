package carteDuJeu.personnages;
public enum Classe {
    GUERRIER(20, 1, 0, 0),     // Guerrier : 20 PV, bonus en Force
    CLERC(16, 0, 0, 0),        // Clerc : 16 PV
    MAGICIEN(12, 0, 0, 0),     // Magicien : 12 PV
    ROUBLARD(16, 0, 0, 0);     // Roublard : 16 PV

    private final int m_pointsDeVie;
    private final int m_forceBonus;
    private final int m_dexteriteBonus;
    private final int m_initiativeBonus;

    // Constructeur de l'énumération avec les stats
    Classe(int pointsDeVie, int forceBonus, int dexteriteBonus, int initiativeBonus) {
        this.m_pointsDeVie = pointsDeVie;
        this.m_forceBonus = forceBonus;
        this.m_dexteriteBonus = dexteriteBonus;
        this.m_initiativeBonus = initiativeBonus;
    }

    // Getter pour chaque caractéristique
    public int getPointsDeVie() {
        return m_pointsDeVie;
    }

    public int getForceBonus() {
        return m_forceBonus;
    }

    public int getDexteriteBonus() {
        return m_dexteriteBonus;
    }

    public int getInitiativeBonus() {
        return m_initiativeBonus;
    }
}
