import carteDuJeu.Carte;

import java.util.List;

public class Donjon {

    // Attributs privés
    private Carte carte;
    private List<Monstre> monstres;
    private List<Equipement> equipements;
    private List<Personnage> joueurs;

    // Constructeur
    public Donjon(Carte carte, List<Monstre> monstres, List<Equipement> equipements, List<Personnage> joueurs) {
        this.carte = carte;
        this.monstres = monstres;
        this.equipements = equipements;
        this.joueurs = joueurs;
    }

    // Méthode pour la mise en place du donjon
    public void miseEnPlace() {
        // Logique pour la mise en place du donjon
        System.out.println("Le donjon est en place !");
        // Positionner les monstres, les équipements, les joueurs, etc.
        // Exemple : carte.afficherCarte();
        // Positionnement des éléments sur la carte
    }

    // Méthode pour dérouler un combat
    public void deroulerCombat() {
        // Logique pour gérer le combat entre les joueurs et les monstres
        System.out.println("Le combat commence !");
        // Interaction avec les monstres et les personnages
        // Exemple : un monstre attaque un joueur, un joueur attaque un monstre
    }

    // Méthode pour terminer le donjon
    public void finDonjon() {
        // Logique pour finir le donjon
        System.out.println("Le donjon est terminé.");
        // Vous pouvez ajouter des récompenses ou des conséquences ici
    }

    // Getters et setters
    public Carte getCarte() {
        return carte;
    }

    public void setCarte(Carte carte) {
        this.carte = carte;
    }

    public List<Monstre> getMonstres() {
        return monstres;
    }

    public void setMonstres(List<Monstre> monstres) {
        this.monstres = monstres;
    }

    public List<Equipement> getEquipements() {
        return equipements;
    }

    public void setEquipements(List<Equipement> equipements) {
        this.equipements = equipements;
    }

    public List<Personnage> getJoueurs() {
        return joueurs;
    }

    public void setJoueurs(List<Personnage> joueurs) {
        this.joueurs = joueurs;
    }
}
