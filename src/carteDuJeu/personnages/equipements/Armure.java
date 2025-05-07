package carteDuJeu.personnages.equipements;

public class Armure extends Equipement {
    private ArmureType type;  // Le type d'armure
    private int classeArmure;  // La classe d'armure (par exemple, 9, 10, etc.)
    private boolean estLourde; // Si l'armure est lourde ou non

    // Constructeur
    public Armure(ArmureType type) {
        super(type.getNom());
        this.type = type;
        this.estLourde = type.estLourde();
        this.classeArmure = type.getClasseArmure();
    }

    // Accesseurs (getters)
    public ArmureType getType() {
        return type;
    }

    public int getClasseArmure() {
        return classeArmure;
    }

    public boolean estLourde() {
        return estLourde;
    }

    @Override
    public String toString() {
        return "Armure{" +
                "nom='" + type.getNom() + '\'' +
                ", classeArmure=" + classeArmure +
                ", estLourde=" + estLourde +
                '}';
    }
}