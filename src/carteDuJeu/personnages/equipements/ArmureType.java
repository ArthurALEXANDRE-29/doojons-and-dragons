package carteDuJeu.personnages.equipements;

public enum ArmureType {
    ARMURE_ECAILLES("Armure d'écailles", 9, false),
    DEMI_PLATE("Demi-plate", 10, false),
    COTTE_DE_MAILLES("Cotte de mailles", 11, true),
    HARNOIS("Harnois", 12, true);

    private final String m_nom;           // Le nom de l'armure
    private final int m_classeArmure;     // Classe d'armure
    private final boolean m_estLourde;    // Si l'armure est lourde ou non

    // Constructeur de l'énumération
    ArmureType(String nom, int classeArmure, boolean estLourde) {
        this.m_nom = nom;
        this.m_classeArmure = classeArmure;
        this.m_estLourde = estLourde;
    }

    // Méthodes pour accéder aux valeurs
    public String getNom() {
        return m_nom;
    }

    public int getClasseArmure() {
        return m_classeArmure;
    }

    public boolean estLourde() {
        return m_estLourde;
    }
}