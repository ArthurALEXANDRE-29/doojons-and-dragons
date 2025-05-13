package carteDuJeu.personnages.equipements.armures;

import carteDuJeu.personnages.equipements.ArmureType;
import carteDuJeu.personnages.equipements.Equipement;

public abstract class Armure extends Equipement {
    private int m_classeArmure;  // La classe d'armure (par exemple, 9, 10, etc.)
    private boolean m_estLourde; // Si l'armure est lourde ou non

    // Constructeur
    public Armure(String nom, int classeArmure, boolean estLourde) {
        super(nom);
        this.m_classeArmure = classeArmure;
        this.m_estLourde = estLourde;
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
                "nom='" + getNom() + '\'' +
                ", classeArmure=" + m_classeArmure +
                ", estLourde=" + m_estLourde +
                '}';
    }
}