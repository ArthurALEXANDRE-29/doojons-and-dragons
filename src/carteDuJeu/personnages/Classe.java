package carteDuJeu.personnages;

import carteDuJeu.personnages.equipements.*;

import java.util.ArrayList;
import java.util.List;

public enum Classe {
    GUERRIER(20, 1, 0, 0) {
        @Override
        public List<Equipement> getEquipementInitial() {
            List<Equipement> equipements = new ArrayList<>();
            equipements.add(new Arme(ArmeType.EPEE_LONGUE));
            equipements.add(new Armure(ArmureType.HARNOIS));
            return equipements;
        }
    },
    CLERC(16, 0, 0, 0) {
        @Override
        public List<Equipement> getEquipementInitial() {
            List<Equipement> equipements = new ArrayList<>();
            equipements.add(new Arme(ArmeType.MASSE_DARMES));
            equipements.add(new Armure(ArmureType.COTTE_DE_MAILLES));
            return equipements;
        }
    },
    MAGICIEN(12, 0, 0, 0) {
        @Override
        public List<Equipement> getEquipementInitial() {
            List<Equipement> equipements = new ArrayList<>();
            equipements.add(new Arme(ArmeType.BATON));
            // Pas d'armure
            return equipements;
        }
    },
    ROUBLARD(16, 0, 0, 0) {
        @Override
        public List<Equipement> getEquipementInitial() {
            List<Equipement> equipements = new ArrayList<>();
            equipements.add(new Arme(ArmeType.RAPIERE));
            equipements.add(new Armure(ArmureType.ARMURE_ECAILLES));
            return equipements;
        }
    };

    private final int m_pointsDeVie;
    private final int m_forceBonus;
    private final int m_dexteriteBonus;
    private final int m_initiativeBonus;

    // Constructeur
    Classe(int pointsDeVie, int forceBonus, int dexteriteBonus, int initiativeBonus) {
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
