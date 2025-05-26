package carteDuJeu;


import carteDuJeu.personnages.equipements.*;
import carteDuJeu.personnages.*;
import carteDuJeu.Monstres.*;
import carteDuJeu.MaitreDuJeu;
import carteDuJeu.Carte;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Donjon {

    // Attributs privés
    private Carte m_carte;
    private MaitreDuJeu m_maitreDuJeu;
    private List<Monstre> m_monstres;
    private List<Equipement> m_equipements;
    private List<Personnage> m_joueurs;
    private List<ElementMobile> m_entiteTour;

    // Constructeur
    public Donjon( MaitreDuJeu maitreDuJeu, List<Equipement> tousLesEquipements, List<Personnage> joueurs) {
        try {
            System.out.println("Quelle taille de carte veut le maitre du jeu ? (largeur) : ");
            Scanner scanner = new Scanner(System.in);
            int largeur = scanner.nextInt();
            System.out.println("Quelle taille de carte veut le maitre du jeu ? (hauteur) : ");
            int hauteur = scanner.nextInt();
            initialiserCarte(largeur, hauteur);
            } catch (Exception e) {
            System.out.println("Erreur lors de l'initialisation de la carte : " + e.getMessage());
            // Vous pouvez choisir de relancer l'initialisation ou de gérer l'erreur autrement
            return;
        }
        this.m_maitreDuJeu = maitreDuJeu;
        maitreDuJeu.phaseCreationDesMonstres();
        this.m_monstres = maitreDuJeu.getMonstres();

        try {
            System.out.println("Combien d'équipements souhaitez-vous dans le donjon ?");
            Scanner scanner = new Scanner(System.in);
            int nbEquipementsSouhaites = scanner.nextInt();
            scanner.nextLine(); // vider la ligne

            this.m_equipements = new ArrayList<>();
            Random random = new Random();

            for (int i = 0; i < nbEquipementsSouhaites; i++) {
                int index = random.nextInt(tousLesEquipements.size());
                Equipement equipementChoisi = tousLesEquipements.get(index);

                // Cloner ou créer une nouvelle instance si nécessaire
                // Ici on suppose que les équipements sont des objets distincts
                this.m_equipements.add(equipementChoisi.copier());
            }
        } catch (Exception e) {
            System.out.println("Erreur lors de l'initialisation des équipements : " + e.getMessage());
            // Vous pouvez choisir de relancer l'initialisation ou de gérer l'erreur autrement
            return;
        }
        this.m_joueurs = joueurs;
    }
    public void initialiserCarte(int x, int y)
    {
        // Initialisation de la carte avec une largeur et une hauteur données
        this.m_carte = new Carte(x, y);
    }


    // Méthode pour la mise en place du donjon
    public void miseEnPlace() {
        // Création des monstres par le Maitre du Jeu
        System.out.println("Création des monstres...");
        m_maitreDuJeu.phaseCreationDesMonstres();
        m_monstres = m_maitreDuJeu.getMonstres();

        // Placement aléatoire des monstres
        System.out.println("Placement aléatoire des monstres...");
        for (Monstre monstre : m_monstres) {
            m_carte.ajouterContenuAleatoire(monstre);
        }

        // Placement aléatoire des joueurs
        System.out.println("Placement aléatoire des joueurs...");
        for (Personnage joueur : m_joueurs) {
            m_carte.ajouterContenuAleatoire(joueur);
        }

        // Placement aléatoire des équipements
        System.out.println("Placement aléatoire des équipements...");
        for (Equipement equipement : m_equipements) {
            m_carte.ajouterContenuAleatoire(equipement);
        }

        // Affichage de la carte
        System.out.println("Affichage de la carte...");
        m_carte.afficherCarte();
        System.out.println("Le donjon est en place !");
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
