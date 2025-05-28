package carteDuJeu.Monstres;

import carteDuJeu.ElementCarte;
import carteDuJeu.ElementMobile;

public class Monstre implements ElementMobile {

    // Attributs privés
    private String m_espece;
    private int m_numero;

    private int m_portee;
    private int m_maxDmg; // max dmg d'un de
    private int m_nbDes; // nb de des que le monstre utilise

    private int m_pointsDeVieMax;
    private int m_pointsDeVie;

    private int m_force;
    private int m_dexterite;
    private int m_vitesse;

    private int m_classeArmure;
    private int m_initiative;

    // Constructeur
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

    // Getters et setters
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

    public int getPortee() {
        return m_portee;
    }

    public int getCasesMaxDeplacement() {
        int casesMax = m_vitesse / 3;
        return casesMax;
    }

    public void setVitesse(int vitesse) {
        m_vitesse = vitesse;
    }

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


    public void changementCarac(int caracteristiqueDAttaque) {
        if (m_portee != 1) {
            m_dexterite = caracteristiqueDAttaque;
        } else {
            m_force = caracteristiqueDAttaque;
        }
    }

    public int getPointsDeVie() {
        return m_pointsDeVie;
    }

    public void setPointsDeVieMax(int pointsDeVie) {
        this.m_pointsDeVieMax = pointsDeVie;
        m_pointsDeVie = m_pointsDeVieMax;
    }

    public int getPointsDeVieMax() {
        return m_pointsDeVieMax;
    }

    public int getForce() {
        return m_force;
    }

    public int getDexterite() {
        return m_dexterite;
    }

    public int getClasseArmure() {
        return m_classeArmure;
    }

    public void setClasseArmure(int classeArmure) {
        this.m_classeArmure = classeArmure;
    }

    public int getInitiative() {
        return m_initiative;
    }

    public void setInitiative(int initiative) {
        this.m_initiative = initiative;
    }


    @Override
    public String getSymbole() {
        if (m_numero > 9) {
            return String.valueOf(m_espece.charAt(0)) + m_numero + " ";
        }
        return " " + String.valueOf(m_espece.charAt(0)) + m_numero + " ";
    }

    public String getNom() {
        return m_espece + " #" + m_numero;
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
        return false;
    }

    public boolean estElementMobile() {
        return true;
    }
}

