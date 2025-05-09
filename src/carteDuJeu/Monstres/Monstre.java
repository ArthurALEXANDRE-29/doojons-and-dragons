package Monstres;

import carteDuJeu.personnages.Personnage;

public class Monstre {

    // Attributs privés
    private String espece;
    private int numero;
    private Attaque attaque;
    private int pointsDeVie;
    private int force;
    private int dexterite;
    private int classeArmure;
    private int initiative;

    // Constructeur
    public Monstre(String espece, int numero, Attaque attaque, int pointsDeVie, int force, int dexterite, int classeArmure, int initiative) {
        this.espece = espece;
        this.numero = numero;
        this.attaque = attaque;
        this.pointsDeVie = pointsDeVie;
        this.force = force;
        this.dexterite = dexterite;
        this.classeArmure = classeArmure;
        this.initiative = initiative;
    }

    // Méthode attaquer (modifiée pour prendre en compte l'attaque du monstre)
    public void attaquer(Personnage cible) {
        // Calcul des dégâts
        System.out.println("Le " + espece + " attaque " + cible.getNom() + " avec " + attaque.getDegats() + " de dégats.");
        // Logique d'attaque
        // cible.subirDegats(attaque.getDegats()); // Exemple d'interaction
    }

    // Getters et setters
    public String getEspece() {
        return espece;
    }

    public void setEspece(String espece) {
        this.espece = espece;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public Attaque getAttaque() {
        return attaque;
    }

    public void setAttaque(Attaque attaque) {
        this.attaque = attaque;
    }

    public int getPointsDeVie() {
        return pointsDeVie;
    }

    public void setPointsDeVie(int pointsDeVie) {
        this.pointsDeVie = pointsDeVie;
    }

    public int getForce() {
        return force;
    }

    public void setForce(int force) {
        this.force = force;
    }

    public int getDexterite() {
        return dexterite;
    }

    public void setDexterite(int dexterite) {
        this.dexterite = dexterite;
    }

    public int getClasseArmure() {
        return classeArmure;
    }

    public void setClasseArmure(int classeArmure) {
        this.classeArmure = classeArmure;
    }

    public int getInitiative() {
        return initiative;
    }

    public void setInitiative(int initiative) {
        this.initiative = initiative;
    }
}

class Attaque {

    // Attributs privés
    private String degats;
    private int portee;

    // Constructeur
    public Attaque(String degats, int portee) {
        this.degats = degats;
        this.portee = portee;
    }

    // Getters et setters
    public String getDegats() {
        return degats;
    }

    public void setDegats(String degats) {
        this.degats = degats;
    }

    public int getPortee() {
        return portee;
    }

    public void setPortee(int portee) {
        this.portee = portee;
    }
}
