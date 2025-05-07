package carteDuJeu.personnages.equipements;

public class Armure extends Equipement {
    private ArmureType m_type;  // Le type d'armure
    private int m_classeArmure;  // La classe d'armure (par exemple, 9, 10, etc.)
    private boolean m_estLourde; // Si l'armure est lourde ou non

    // Constructeur
    public Armure(ArmureType type) {
        super(type.getNom());
        this.m_type = type;
        this.m_estLourde = type.estLourde();
        this.m_classeArmure = type.getClasseArmure();
    }

    // Accesseurs (getters)
    public ArmureType getType() {
        return m_type;
    }

    public int getClasseArmure() {
        return m_classeArmure;
    }

    public boolean estLourde() {
        return m_estLourde;
    }

    @Override
    public String toString() {
        return "Armure{" +
                "nom='" + m_type.getNom() + '\'' +
                ", classeArmure=" + m_classeArmure +
                ", estLourde=" + m_estLourde +
                '}';
    }
}