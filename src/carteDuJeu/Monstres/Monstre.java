package carteDuJeu.Monstres;

import carteDuJeu.personnages.Personnage;

import carteDuJeu.ElementCarte;

public class Monstre implements ElementCarte {

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

    @Override
    public String getSymbole() {
        // Un symbole par défaut ou basé sur l'espèce
        switch(espece.toLowerCase()) {
            case "gobelin": return "G";
            case "orc": return "O";
            case "troll": return "T";
            case "dragon": return "D";
            default: return "M";
        }
    }

    @Override
    public String getNom() {
        return espece + " #" + numero;
    }

}

