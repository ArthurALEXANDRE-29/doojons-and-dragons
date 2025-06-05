package carteDuJeu.monstres;

import carteDuJeu.ElementMobile;

/**
 * Représente un monstre dans le jeu.
 * Un monstre possède une espèce, un numéro d'identification, des caractéristiques de combat
 * (portée, dégâts, nombre de dés, points de vie, force, dextérité, vitesse, classe d'armure, initiative)
 * et peut subir des dégâts ou se déplacer.
 */
public class Monstre implements ElementMobile {

    // Attributs privés
    private String m_espece;
    private int m_numero;
    private int m_portee;
    private int m_maxDmg;
    private int m_nbDes;
    private int m_pointsDeVieMax;
    private int m_pointsDeVie;
    private int m_force;
    private int m_dexterite;
    private int m_vitesse;
    private int m_classeArmure;
    private int m_initiative;

    /**
     * Construit un monstre avec toutes ses caractéristiques.
     *
     * @param espece Espèce du monstre
     * @param numero Numéro d'identification
     * @param portee Portée d'attaque
     * @param maxDmg Dégâts maximum d'un dé
     * @param vitesse Vitesse de déplacement
     * @param nbDes Nombre de dés pour l'attaque
     * @param pointsDeVieMax Points de vie maximum
     * @param caracteristiqueDAttaque Force ou dextérité selon la portée
     * @param classeArmure Classe d'armure
     * @param initiative Initiative
     */
    public Monstre(String espece, int numero, int portee, int maxDmg, int vitesse, int nbDes, int pointsDeVieMax, int caracteristiqueDAttaque, int classeArmure, int initiative) {
        this.m_espece = espece;
        this.m_numero = numero;
        this.m_portee = portee;
        this.m_maxDmg = maxDmg;
        this.m_vitesse = vitesse;
        this.m_pointsDeVieMax = pointsDeVieMax;
        m_pointsDeVie = m_pointsDeVieMax;
        this.m_nbDes = nbDes;

        if (m_portee < 1) {
            System.out.println("Valeur inférieure à 1 impossible, portée mise en place à 1");
            m_portee = 1;
            m_force = caracteristiqueDAttaque;
        } else if (m_portee > 1) {
            m_dexterite = caracteristiqueDAttaque;
        } else {
            m_force = caracteristiqueDAttaque;
        }

        this.m_classeArmure = classeArmure;
        this.m_initiative = initiative;
    }

    // ============================ Getters et setters ============================

    public String getEspece() {
        return m_espece;
    }

    public void setEspece(String espece) {
        this.m_espece = espece;
    }

    public int getNumero() {
        return m_numero;
    }

    public void setNumero(int numero) {
        this.m_numero = numero;
    }

    public int getPortee() {
        return m_portee;
    }

    public int getM_maxDmg() {
        return m_maxDmg;
    }

    public int getNbDes() {
        return m_nbDes;
    }

    public void setMaxdmg(int dmgMax, int nbDes) {
        m_maxDmg = dmgMax;
        m_nbDes = nbDes;
    }

    public void setVitesse(int vitesse) {
        m_vitesse = vitesse;
    }

    /**
     * Définit la portée et la caractéristique d'attaque du monstre.
     * Si la portée est inférieure à 1, elle est fixée à 1 et la force est utilisée.
     * Si la portée est supérieure à 1, la dextérité est utilisée.
     *
     * @param portee Portée d'attaque
     * @param caracteristiqueDAttaque Caractéristique d'attaque (force ou dextérité)
     */
    public void setPorteeEtStat(int portee, int caracteristiqueDAttaque) {
        if (m_portee < 1) {
            System.out.println("Valeur inférieure à 1 impossible, portée mise en place à 1");
            m_portee = 1;
            m_force = caracteristiqueDAttaque;
        } else if (m_portee > 1) {
            m_dexterite = caracteristiqueDAttaque;
        } else {
            m_force = caracteristiqueDAttaque;
        }
    }

    /**
     * Change la caractéristique d'attaque du monstre en fonction de la portée.
     *
     * @param caracteristiqueDAttaque Nouvelle valeur de la caractéristique d'attaque
     */
    public void changementCarac(int caracteristiqueDAttaque) {
        if (m_portee != 1) {
            m_dexterite = caracteristiqueDAttaque;
        } else {
            m_force = caracteristiqueDAttaque;
        }
    }

    @Override
    public int getPointsDeVie() {
        return m_pointsDeVie;
    }

    public void setPointsDeVieMax(int pointsDeVie) {
        this.m_pointsDeVieMax = pointsDeVie;
        m_pointsDeVie = m_pointsDeVieMax;
    }

    @Override
    public int getPointsDeVieMax() {
        return m_pointsDeVieMax;
    }

    @Override
    public int getForce() {
        return m_force;
    }

    @Override
    public int getDexterite() {
        return m_dexterite;
    }

    public int getClasseArmure() {
        return m_classeArmure;
    }

    public void setClasseArmure(int classeArmure) {
        this.m_classeArmure = classeArmure;
    }

    @Override
    public int getInitiative() {
        return m_initiative;
    }

    public void setInitiative(int initiative) {
        this.m_initiative = initiative;
    }

    @Override
    public String getNom() {
        return m_espece + "#" + m_numero;
    }

    // ============================ Section Overrides ============================

    @Override
    public int getCasesMaxDeplacement() {
        int casesMax = m_vitesse / 3;
        return casesMax;
    }

    @Override
    public String getSymbole() {
        if (m_numero > 9) {
            return String.valueOf(m_espece.charAt(0)) + m_numero + " ";
        }
        return " " + String.valueOf(m_espece.charAt(0)) + m_numero + " ";
    }

    @Override
    public void subirDegats(int degats) {
        m_pointsDeVie -= degats;
        if (m_pointsDeVie < 0) {
            m_pointsDeVie = 0;
        }
    }

    @Override
    public boolean estMort() {
        return m_pointsDeVie <= 0;
    }

    @Override
    public boolean estPersonnage() {
        return false;
    }

    @Override
    public boolean estElementMobile() {
        return true;
    }

    @Override
    public boolean estEquipement() {
        return false;
    }

    @Override
    public String toString() {
        return  m_espece + '\n' +
                "  Portee : " + m_portee + '\n' +
                "  PointsDeVie : " + m_pointsDeVie + '\n' +
                "  Force : " + m_force + '\n' +
                "  Dexterite : " + m_dexterite + '\n' +
                "  Vitesse : " + m_vitesse;
    }
}