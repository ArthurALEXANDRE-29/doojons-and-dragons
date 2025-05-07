package carteDuJeu.personnages.equipements;

public enum ArmeType {
    BATON("Bâton", "1d6", 1, false),
    MASSE_DARMES("Masse d'armes", "1d6", 1, false),
    EPEE_LONGUE("Épée longue", "1d8", 1, true),
    RAPIERE("Rapière", "1d8", 1, false),
    ARBALETE_LEGERE("Arbalète légère", "1d8", 16, true),
    FRONDE("Fronde", "1d4", 6, false),
    ARC_COURT("Arc court", "1d6", 16, false);

    private final String nom;
    private final String degats;
    private final int portee;
    private final boolean estLourde;

    ArmeType(String nom, String degats, int portee, boolean estLourde) {
        this.nom = nom;
        this.degats = degats;
        this.portee = portee;
        this.estLourde = estLourde;
    }

    public String getNom() { return nom; }
    public String getDegats() { return degats; }
    public int getPortee() { return portee; }
    public boolean isLourde() { return estLourde; }
}
