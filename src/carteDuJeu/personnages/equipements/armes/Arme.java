package carteDuJeu.personnages.equipements.armes;

import carteDuJeu.personnages.equipements.Equipement;

public abstract class Arme extends Equipement {
    private final int m_degats;
    private final int m_portee;
    private final boolean m_estLourde;
    private final int m_desMax;
    private int m_bonusAttaque; // Ajout de l'attribut
    private int m_bonusDegats;  // Ajout de l'attribut

    public Arme(String nom, int degats, int portee, boolean estLourde, int des) {
        super(nom); // Le nom est transmis à la classe Equipement
        this.m_degats = degats;
        this.m_portee = portee;
        this.m_estLourde = estLourde;
        this.m_desMax = des;
        this.m_bonusAttaque = 0; // Initialisation
        this.m_bonusDegats = 0;  // Initialisation
    }

    public int getDegats() {
        return m_degats;
    }

    public int getPortee() {
        return m_portee;
    }

    public boolean estLourde() {
        return m_estLourde;
    }
    public String armeLourde() {
        return (m_estLourde)? "oui" : "non";
    }

    public int getDes() {
        return m_desMax;
    }



    @Override
    public String toString() {
        return getNom() +
                "\n degats : " + m_degats +
                "\n portee : " + m_portee +
                "\n est lourde : " + armeLourde() +
                "\n dés d'attaque : " + m_desMax +
                "\n bonus dés : " + m_bonusAttaque +
                "\n bonus dégâts : " + m_bonusDegats;

    }
    public void ajouterBonusAttaque(int bonus) {
        this.m_bonusAttaque += bonus;
    }

    public void ajouterBonusDegats(int bonus) {
        this.m_bonusDegats += bonus;
    }
    public boolean estUneArme() {
        return true;
    }
    public boolean estUneArmure(){return false;}
}