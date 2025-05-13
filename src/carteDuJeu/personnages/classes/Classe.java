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

    // Constructeur
    Classe(String nom, int pointsDeVie, int forceBonus, int dexteriteBonus, int initiativeBonus) {
        this.m_nomClasse = nom;
        this.m_pointsDeVie = pointsDeVie;
        this.m_forceBonus = forceBonus;
        this.m_dexteriteBonus = dexteriteBonus;
        this.m_initiativeBonus = initiativeBonus;
    }

    // À redéfinir dans chaque type de classe
    public abstract List<Equipement> getEquipementInitial();

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
