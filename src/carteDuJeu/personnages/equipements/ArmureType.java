package carteDuJeu.personnages.equipements;

public enum ArmureType {
    ARMURE_ECAILLES("Armure d'écailles", 9, false),
    DEMI_PLATE("Demi-plate", 10, false),
    COTTE_DE_MAILLES("Cotte de mailles", 11, true),
    HARNOIS("Harnois", 12, true);

    private final String nom;           // Le nom de l'armure
    private final int classeArmure;     // Classe d'armure
    private final boolean estLourde;    // Si l'armure est lourde ou non

    // Constructeur de l'énumération
    ArmureType(String nom, int classeArmure, boolean estLourde) {
        this.nom = nom;
        this.classeArmure = classeArmure;
        this.estLourde = estLourde;
    }

    // Méthodes pour accéder aux valeurs
    public String getNom() {
        return nom;
    }

    public int getClasseArmure() {
        return classeArmure;
    }

    public boolean estLourde() {
        return estLourde;
    }
}