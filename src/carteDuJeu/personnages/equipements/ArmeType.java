package carteDuJeu.personnages.equipements;

public enum ArmeType {
    BATON("Bâton", "1d6", 1, false),
    MASSE_DARMES("Masse d'armes", "1d6", 1, false),
    EPEE_LONGUE("Épée longue", "1d8", 1, true),
    RAPIERE("Rapière", "1d8", 1, false),
    ARBALETE_LEGERE("Arbalète légère", "1d8", 16, true),
    FRONDE("Fronde", "1d4", 6, false),
    ARC_COURT("Arc court", "1d6", 16, false);

    private final String m_nom;
    private final String m_degats;
    private final int m_portee;
    private final boolean m_estLourde;

    ArmeType(String nom, String degats, int portee, boolean estLourde) {
        this.m_nom = nom;
        this.m_degats = degats;
        this.m_portee = portee;
        this.m_estLourde = estLourde;
    }

    public String getNom() { return m_nom; }
    public String getDegats() { return m_degats; }
    public int getPortee() { return m_portee; }
    public boolean isLourde() { return m_estLourde; }
}
