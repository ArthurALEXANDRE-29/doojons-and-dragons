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

/**
 * Classe représentant un personnage joueur du jeu.
 * Un personnage possède une race, une classe, des caractéristiques (force, dextérité, vitesse, initiative),
 * un inventaire, une arme et une armure équipées, ainsi que des points de vie.
 * Il peut se déplacer, subir des dégâts, changer d'équipement et interagir avec la carte.
 */
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

    /**
     * Construit un personnage avec un nom, une race et une classe.
     * Initialise les caractéristiques et l'inventaire selon la classe et la race.
     * @param nom le nom du personnage
     * @param race la race du personnage
     * @param classe la classe du personnage
     */
    public Personnage(String nom, Race race, Classe classe) {
        this.m_nom = nom;
        this.m_race = race;
        this.m_classe = classe;

        this.m_pointsDeVieMax = classe.getPointsDeVie();
        this.m_pointsDeVie = this.m_pointsDeVieMax;

        this.m_forceBase = Des.lancer(4, 4) + 3 + race.getForceBonus() + classe.getForceBonus();
        this.m_dexteriteBase = Des.lancer(4, 4) + 3 + race.getDexteriteBonus() + classe.getDexteriteBonus();
        this.m_vitesseBase = Des.lancer(4, 4) + 3 + race.getVitesseBonus();
        this.m_initiativeBase = Des.lancer(4, 4) + 3 + race.getInitiativeBonus() + classe.getInitiativeBonus();
        m_forceCurrent = m_forceBase;
        m_vitesseCurrent = m_vitesseBase;

        this.m_inventaire = new ArrayList<>();

        for (Equipement eq : classe.getEquipementInitial()) {
            if (eq.estUneArme() && m_armeEquipee == null) {
                m_armeEquipee = (Arme) eq;
                if (m_armeEquipee.estLourde()) {
                    m_forceCurrent += 4;
                    m_vitesseCurrent -= 2;
                }
                continue;
            }
            if (eq.estUneArmure() && m_armureEquipee == null) {
                m_armureEquipee = (Armure) eq;
                if (m_armureEquipee.estLourde()) {
                    m_vitesseCurrent -= 4;
                }
                continue;
            }
            m_inventaire.add(eq);
        }
    }

    /**
     * Ajoute un équipement à l'inventaire du personnage.
     * @param e l'équipement à ajouter
     */
    public void ajouterAInventaire(Equipement e) {
        this.m_inventaire.add(e);
    }

    /**
     * Retourne le nom de la race du personnage.
     * @return le nom de la race
     */
    public String getRace() {
        return m_race.getNomRace();
    }

    /**
     * Retourne le nom de la classe du personnage.
     * @return le nom de la classe
     */
    public String getClasse() {
        return m_classe.getNomClasse();
    }

    /**
     * Définit les points de vie du personnage (ne peut pas dépasser le maximum).
     * @param pointsDeVie le nouveau nombre de points de vie
     */
    public void setPointsDeVie(int pointsDeVie) {
        this.m_pointsDeVie = pointsDeVie;
        if (this.m_pointsDeVie > m_pointsDeVieMax) {
            this.m_pointsDeVie = m_pointsDeVieMax;
        }
    }

    /**
     * Retourne la vitesse actuelle du personnage.
     * @return la vitesse actuelle
     */
    public int getVitesse() {
        return m_vitesseCurrent;
    }

    /**
     * Retourne l'inventaire du personnage.
     * @return la liste des équipements dans l'inventaire
     */
    public List<Equipement> getInventaire() {
        return m_inventaire;
    }

    /**
     * Retourne un équipement spécifique de l'inventaire par son index.
     * @param index l'index de l'équipement dans l'inventaire
     * @return l'équipement à l'index spécifié, ou null si l'index est invalide
     */
    public Equipement getInventaire(int index) {
        if (index < 0 || index >= m_inventaire.size()) {
            return null;
        }
        return m_inventaire.get(index);
    }

    /**
     * Retourne l'arme actuellement équipée du personnage.
     * @return l'arme équipée, ou null si aucune arme n'est équipée
     */
    public Arme getArmeEquipee() {
        return m_armeEquipee;
    }

    /**
     * Retourne l'armure actuellement équipée du personnage.
     * @return l'armure équipée, ou null si aucune armure n'est équipée
     */
    public Armure getArmureEquipee() {
        return m_armureEquipee;
    }

    /**
     * Équipe une arme de l'inventaire à l'index donné.
     * @param index l'index de l'arme dans l'inventaire
     * @return true si l'arme a été équipée, false sinon
     */
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

    /**
     * Recalcule les statistiques du personnage en fonction de l'arme et de l'armure équipées.
     * Met à jour la force et la vitesse actuelles.
     */
    private void recalculerStats() {
        m_forceCurrent = m_forceBase;
        m_vitesseCurrent = m_vitesseBase;
        if (m_armeEquipee != null && m_armeEquipee.estLourde()) {
            m_forceCurrent += 4;
            m_vitesseCurrent -= 2;
        }
        if (m_armureEquipee != null && m_armureEquipee.estLourde()) {
            m_vitesseCurrent -= 4;
        }
    }

    /**
     * Équipe une armure de l'inventaire à l'index donné.
     * @param index l'index de l'armure dans l'inventaire
     * @return true si l'armure a été équipée, false sinon
     */
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


    /*============================Section Overrides============================*/

    /**
     * Retourne le symbole du personnage pour l'affichage sur la carte.
     * @return le symbole (3 premières lettres du nom)
     */
    @Override
    public String getSymbole() {
        String nom = m_nom.length() >= 3 ? m_nom.substring(0, 3) : m_nom;
        while (nom.length() < 3) {
            nom += " ";
        }
        return " " + nom;
    }

    /**
     * Retourne le nom du personnage.
     * @return le nom
     */
    @Override
    public String getNom() {
        return m_nom;
    }

    /**
     * Retourne les points de vie actuels.
     * @return les points de vie
     */
    @Override
    public int getPointsDeVie() {
        return m_pointsDeVie;
    }

    /**
     * Retourne le nombre maximum de cases que le personnage peut parcourir en un tour.
     * @return le nombre de cases maximum
     */
    @Override
    public int getCasesMaxDeplacement() {
        int casesMax = m_vitesseCurrent / 3;
        return casesMax;
    }

    /**
     * Retourne le nombre maximum de points de vie du personnage.
     * @return les points de vie maximum
     */
    @Override
    public int getPointsDeVieMax() {
        return m_pointsDeVieMax;
    }

    /**
     * Retourne la force actuelle du personnage.
     * @return la force
     */
    @Override
    public int getForce() {
        return m_forceCurrent;
    }

    /**
     * Retourne la dextérité actuelle du personnage.
     * @return la dextérité
     */
    @Override
    public int getDexterite() {
        return m_dexteriteBase;
    }

    /**
     * Retourne l'initiative du personnage.
     * @return l'initiative
     */
    @Override
    public int getInitiative() {
        return m_initiativeBase;
    }

    /**
     * Inflige des dégâts au personnage.
     * @param degats le nombre de points de dégâts à infliger
     */
    @Override
    public void subirDegats(int degats) {
        m_pointsDeVie -= degats;
        if (m_pointsDeVie < 0) {
            m_pointsDeVie = 0;
        }
    }

    /**
     * Vérifie si le personnage est mort (points de vie à 0 ou moins).
     * @return true si le personnage est mort, false sinon
     */
    @Override
    public boolean estMort() {
        return m_pointsDeVie <= 0;
    }

    /**
     * Vérifie si l'élément est un personnage.
     * @return true, car cette classe représente un personnage
     */
    @Override
    public boolean estPersonnage() {
        return true;
    }

    /**
     * Indique que cet élément est mobile.
     * @return true
     */
    @Override
    public boolean estElementMobile() {
        return true;
    }

    /**
     * Indique que cet élément n'est pas un équipement.
     * @return false
     */
    @Override
    public boolean estEquipement() {
        return false;
    }

    /**
     * Retourne une représentation textuelle du personnage.
     * @return une chaîne de caractères décrivant le personnage
     */
    @Override
    public String toString() {
        return  m_nom + '\n' +
                "  Race : " + (m_race != null ? m_race.getNomRace() + " | " : "inconnue") +
                "Classe : " + (m_classe != null ? m_classe.getNomClasse() : "inconnue") + '\n' +
                "    PV : " + m_pointsDeVie + "/" + m_pointsDeVieMax + '\n' +
                "    Force : " + m_forceCurrent + '\n' +
                "    Dextérité : " + m_dexteriteBase + '\n' +
                "    Vitesse : " + m_vitesseCurrent + '\n' +
                "    Arme : " + (m_armeEquipee != null ? m_armeEquipee.toString() : "aucune") + '\n' +
                "    Armure : " + (m_armureEquipee != null ? m_armureEquipee.toString() : "aucune") + '\n' +
                "    Inventaire : " + m_inventaire.size() + " équipement(s)";
    }
}