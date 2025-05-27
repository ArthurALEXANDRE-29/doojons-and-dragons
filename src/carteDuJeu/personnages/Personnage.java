package carteDuJeu.personnages;
import carteDuJeu.Des;

import carteDuJeu.ElementMobile;
import carteDuJeu.personnages.classes.Classe;
import carteDuJeu.personnages.equipements.*;
import carteDuJeu.personnages.equipements.armes.Arme;
import carteDuJeu.personnages.equipements.armures.Armure;
import carteDuJeu.personnages.races.Race;


import java.util.ArrayList;
import java.util.List;

public class Personnage implements ElementMobile {
    // attributs initialisés en fonction du joueur et ses choix / choix de la classe + race
    private String m_nom;
    private Race m_race;
    private Classe m_classe;
    private Arme m_armeEquipee;
    private Armure m_armureEquipee;
    private List<Equipement> m_inventaire;

    // caractéristiques du personnage
    private int m_pointsDeVie;
    private int m_pointsDeVieMax;
    private int m_forceBase;
    private int m_forceCurrent;
    private int m_dexteriteBase;
    private int m_vitesseBase;
    private int m_vitesseCurrent;
    private int m_initiativeBase;

    public Personnage(String nom, Race race, Classe classe) {
        // initialisation des attributs choisis / liés à la classe ou race + armes/ armures de base
        this.m_nom = nom;
        this.m_race = race;
        this.m_classe = classe;

        // Initialisation des attributs de base du personnage
        this.m_pointsDeVieMax = classe.getPointsDeVie();
        this.m_pointsDeVie = this.m_pointsDeVieMax;

        // Calcul des autres attributs en fonction de la race et de la classe
        // pour chaque base : faire un jet de dés


        this.m_forceBase = Des.lancer(4, 4) + 3+ race.getForceBonus() + classe.getForceBonus() ;
        this.m_dexteriteBase = Des.lancer(4, 4) + 3+ race.getDexteriteBonus() + classe.getDexteriteBonus();
        this.m_vitesseBase = Des.lancer(4, 4) + 3 + race.getVitesseBonus();
        this.m_initiativeBase = Des.lancer(4, 4) + 3 + race.getInitiativeBonus() + classe.getInitiativeBonus();
        m_forceCurrent = m_forceBase;
        m_vitesseCurrent = m_vitesseBase;

        this.m_inventaire = new ArrayList<>();

        // Ajouter l'équipement de classe (armes/armures) à l'inventaire ( initialisation)

        for (Equipement eq : classe.getEquipementInitial()) {

            // Si c’est une arme et aucune arme équipée, on l’équipe directement
            if (eq.estUneArme() && m_armeEquipee == null) {
                m_armeEquipee = (Arme) eq;

                if (m_armeEquipee.estLourde()) {
                    m_forceCurrent += 4;
                    m_vitesseCurrent -= 2;
                }

                // Ne pas ajouter à l’inventaire
                continue;
            }

            // Si c’est une armure et aucune armure équipée, on l’équipe directement
            if (eq.estUneArmure() && m_armureEquipee == null) {
                m_armureEquipee = (Armure) eq;

                if (m_armureEquipee.estLourde()) {
                    m_vitesseCurrent -= 4;
                }

                // Ne pas ajouter à l’inventaire
                continue;
            }

            // Si non équipé, ajouter à l'inventaire
            m_inventaire.add(eq);
        }

    }

    public void ajouterAInventaire(Equipement e) {
        this.m_inventaire.add(e);
    }

    public String getNom() {
        return m_nom;
    }

    public String getRace() {
        return m_race.getNomRace();
    }

    public String getClasse() {
        return m_classe.getNomClasse();
    }

    public int getPointsDeVie() {
        return m_pointsDeVie;
    }

    public void setPointsDeVie(int pointsDeVie) {
        this.m_pointsDeVie = pointsDeVie;
        if (this.m_pointsDeVie > m_pointsDeVieMax) {
            this.m_pointsDeVie = m_pointsDeVieMax;
        }
    }

    public int getPointsDeVieMax() {
        return m_pointsDeVieMax;
    }

    public int getForce() {
        return m_forceCurrent;
    }

    public int getDexterite() {
        return m_dexteriteBase;
    }

    public int getVitesse() {
        return m_vitesseCurrent;
    }

    public int getInitiative() {
        return m_initiativeBase;
    }

    public List<Equipement> getInventaire() {
        return m_inventaire;
    }

    public Arme getArmeEquipee() {
        return m_armeEquipee;
    }

    public Armure getArmureEquipee() {
        return m_armureEquipee;
    }


    public boolean setArmeEquipee(int index) {
        if (index < 0 || index >= m_inventaire.size()) {
            return false;
        }

        Equipement equipement = m_inventaire.get(index);
        if (equipement.estUneArme()) {
            Arme nouvelleArme = (Arme) equipement;

            if (m_armeEquipee != null) {
                m_inventaire.add(m_armeEquipee);
            }

            m_inventaire.remove(index);
            m_armeEquipee = nouvelleArme;

            recalculerStats();

            return true;
        }

        return false;
    }

    private void recalculerStats() {
        // Force de base
        m_forceCurrent = m_forceBase;

        // Vitesse de base
        m_vitesseCurrent = m_vitesseBase;

        // Bonus/malus lié à l'arme lourde
        if (m_armeEquipee != null && m_armeEquipee.estLourde()) {
            m_forceCurrent += 4;
            m_vitesseCurrent -= 2;
        }

        // Malus lié à l’armure lourde
        if (m_armureEquipee != null && m_armureEquipee.estLourde()) {
            m_vitesseCurrent -= 4;
        }
    }
    public boolean setArmureEquipee(int index) {
        if (index < 0 || index >= m_inventaire.size()) {
            return false;
        }

        Equipement equipement = m_inventaire.get(index);
        if (equipement.estUneArmure()) {
            Armure nouvelleArmure = (Armure) equipement;

            if (m_armureEquipee != null) {
                m_inventaire.add(m_armureEquipee);
            }

            m_inventaire.remove(index);
            m_armureEquipee = nouvelleArmure;

            recalculerStats();

            return true;
        }

        return false;
    }

    public int getCasesMaxDeplacement()
    {
        int casesMax = m_vitesseCurrent /3;
        return casesMax;
    }

    @Override
    public String getSymbole() {
        // Le symbole pour représenter un personnage sur la carte
        return m_nom.substring(0,3) + " ";
    }
    public void subirDegats(int degats) {
        m_pointsDeVie -= degats;
        if (m_pointsDeVie < 0) {
            m_pointsDeVie = 0;
        }
    }
    public boolean estMort() {
        return m_pointsDeVie <= 0;
    }

    public boolean estPersonnage() {
        return true;
    }
}


