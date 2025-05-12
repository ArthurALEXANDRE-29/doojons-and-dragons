package carteDuJeu;

import carteDuJeu.*;
import carteDuJeu.*;
import carteDuJeu.personnages.equipements.*;
import carteDuJeu.personnages.*;
import java.util.List;

public class Donjon {

    // Attributs privés
    private Carte m_carte;
    private List<Monstre> m_monstres;
    private List<Equipement> m_equipements;
    private List<Personnage> m_joueurs;

    // Constructeur
    public Donjon(Carte carte, List<Monstre> monstres, List<Equipement> equipements, List<Personnage> joueurs) {
        this.m_carte = carte;
        this.m_monstres = monstres;
        this.m_equipements = equipements;
        this.m_joueurs = joueurs;
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
        return m_carte;
    }

    public void setCarte(Carte carte) {
        this.m_carte = carte;
    }

    public List<Monstre> getMonstres() {
        return m_monstres;
    }

    public void setMonstres(List<Monstre> monstres) {
        this.m_monstres = monstres;
    }

    public List<Equipement> getEquipements() {
        return m_equipements;
    }

    public void setEquipements(List<Equipement> equipements) {
        this.m_equipements = equipements;
    }

    public List<Personnage> getJoueurs() {
        return m_joueurs;
    }

    public void setJoueurs(List<Personnage> joueurs) {
        this.m_joueurs = joueurs;
    }
}
