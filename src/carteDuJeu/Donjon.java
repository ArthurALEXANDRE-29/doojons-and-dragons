package carteDuJeu;


import carteDuJeu.personnages.equipements.*;
import carteDuJeu.personnages.*;
import carteDuJeu.Monstres.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Donjon {

    // Attributs privés
    private Carte m_carte;
    private List<Monstre> m_monstres;
    private List<Equipement> m_equipements;
    private List<Personnage> m_joueurs;
    private List<ElementMobile> m_entiteTour;

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
    public void preparerEtTrierInitiative() {
        Random random = new Random();

        if (m_entiteTour == null) {
            m_entiteTour = new ArrayList<>();
        }

        m_entiteTour.clear();
        m_entiteTour.addAll(m_joueurs);
        m_entiteTour.addAll(m_monstres);

        // Trier la liste m_entiteTour par initiative totale (lancer + initiative de base)
        m_entiteTour.sort((e1, e2) -> {
            int initiativeE1 = random.nextInt(20) + 1 + e1.getInitiative();
            int initiativeE2 = random.nextInt(20) + 1 + e2.getInitiative();
            return Integer.compare(initiativeE2, initiativeE1);
        }
        );
    }

    // Méthode pour terminer le donjon
    public void finDonjon() {
        boolean unPersonnageMort = false;
        for (Personnage personnage : m_joueurs) {
            if (personnage.getPointsDeVie() <= 0) {
                unPersonnageMort = true;
                break; // Si un personnage est mort, on peut arrêter la vérification
            }
        }

        // Vérifier si tous les monstres sont morts
        boolean tousLesMonstresMorts = true;
        for (Monstre monstre : m_monstres) {
            if (monstre.getPointsDeVie() > 0) {
                tousLesMonstresMorts = false;
                break; // Si un monstre est encore vivant, on arrête la vérification
            }
        }

        // Si un personnage est mort, la partie est finie avec une défaite
        if (unPersonnageMort) {
            System.out.println("Un personnage est mort. Vous avez perdu !");
        } else if (tousLesMonstresMorts) {
            System.out.println("Tous les monstres ont été vaincus ! Le donjon est terminé. Vous avez gagné !");
        } else {

        }
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
