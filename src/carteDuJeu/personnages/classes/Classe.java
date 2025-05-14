package carteDuJeu.personnages.classes;

import carteDuJeu.personnages.equipements.*;
import carteDuJeu.personnages.equipements.armes.Arme;
import carteDuJeu.personnages.equipements.armures.Armure;

import java.util.ArrayList;
import java.util.List;

public abstract class Classe {

    private final String m_nomClasse;
    private final int m_pointsDeVie;
    private final int m_forceBonus;
    private final int m_dexteriteBonus;
    private final int m_initiativeBonus;
    protected List<Equipement> m_equipementInitial;

    // Constructeur
    Classe(String nom, int pointsDeVie, int forceBonus, int dexteriteBonus, int initiativeBonus) {
        this.m_nomClasse = nom;
        this.m_pointsDeVie = pointsDeVie;
        this.m_forceBonus = forceBonus;
        this.m_dexteriteBonus = dexteriteBonus;
        this.m_initiativeBonus = initiativeBonus;
        this.m_equipementInitial = new ArrayList<>();
        initialiserEquipement();
    }
    protected abstract void initialiserEquipement();

    public List<Equipement> getEquipementInitial() {
        return m_equipementInitial;
    }

    // À redéfinir dans chaque type de classe


    public String getNomClasse(){
        return m_nomClasse;
    }
    // Getters
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
