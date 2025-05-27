import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import carteDuJeu.*;
import carteDuJeu.personnages.*;
import carteDuJeu.personnages.classes.*;
import carteDuJeu.personnages.equipements.*;
import carteDuJeu.personnages.equipements.armures.*;
import carteDuJeu.personnages.equipements.armes.*;
import carteDuJeu.personnages.races.*;

public class Jeu {
    private List<Donjon> m_donjons;
    private List<Personnage> m_joueurs;
    private MaitreDuJeu m_maitreDuJeu;
    private int m_donjonActuel; // Index du donjon en cours
    private static final int NOMBRE_DONJONS_TOTAL = 3;

    public Jeu() {
        Scanner scanner = new Scanner(System.in);
        m_donjonActuel = 0;

        // Demander le nombre de joueurs AVANT de créer les donjons
        System.out.print("Combien de joueurs voulez-vous créer ? ");
        int nbJoueurs = scanner.nextInt();
        scanner.nextLine(); // Consommer le retour à la ligne

        // Créer les joueurs d'abord
        m_joueurs = new ArrayList<>();
        for (int i = 1; i <= nbJoueurs; i++) {
            System.out.print("Nom du joueur #" + i + " : ");
            String nom = scanner.nextLine();

            System.out.println("Races disponibles : Humain, Nain, Elfe, Halfelin");
            System.out.print("Race du joueur #" + i + " : ");
            String raceStr = scanner.nextLine();
            Race race = creerRace(raceStr);

            System.out.println("Classes disponibles : Guerrier, Clerc, Magicien, Roublard");
            System.out.print("Classe du joueur #" + i + " : ");
            String classeStr = scanner.nextLine();
            Classe classe = creerClasse(classeStr);

            m_joueurs.add(new Personnage(nom, race, classe));
        }

        // Créer le maître du jeu
        m_maitreDuJeu = new MaitreDuJeu(m_joueurs);

        // Créer la liste de tous les équipements disponibles
        List<Equipement> tousLesEquipements = creerTousLesEquipements();

        // Création des 3 donjons
        m_donjons = new ArrayList<>();
        for (int i = 1; i <= NOMBRE_DONJONS_TOTAL; i++) {
            System.out.println("\n=== Configuration du Donjon " + i + " ===");
            m_donjons.add(new Donjon(i, m_maitreDuJeu, tousLesEquipements, m_joueurs));
        }
    }

    private Race creerRace(String raceStr) {
        switch (raceStr.toLowerCase()) {
            case "humain":
                return new Humain();
            case "nain":
                return new Nain();
            case "elfe":
                return new Elfe();
            case "halfelin":
                return new Halfelin();
            default:
                System.out.println("Race inconnue, création d'un Humain par défaut");
                return new Humain();
        }
    }

    private Classe creerClasse(String classeStr) {
        switch (classeStr.toLowerCase()) {
            case "guerrier":
                return new Guerrier();
            case "clerc":
                return new Clerc();
            case "magicien":
                return new Magicien();
            case "roublard":
                return new Roublard();
            default:
                System.out.println("Classe inconnue, création d'un Guerrier par défaut");
                return new Guerrier();
        }
    }

    private List<Equipement> creerTousLesEquipements() {
        List<Equipement> equipements = new ArrayList<>();

        // Armes de corps à corps courantes
        equipements.add(new Baton());
        equipements.add(new MasseDarmes());

        // Armes de guerre de corps à corps
        equipements.add(new EpeeLongue());
        equipements.add(new Rapiere());

        // Armes à distance
        equipements.add(new ArbaleteLegere());
        equipements.add(new Fronde());
        equipements.add(new ArcCourt());

        // Armures légères
        equipements.add(new ArmureDEcailles());
        equipements.add(new DemiPlate());

        // Armures lourdes
        equipements.add(new CotteDeMailles());
        equipements.add(new Harnois());

        return equipements;
    }

    public void demarrer() {
        System.out.println("La partie commence !");
        m_maitreDuJeu.decrireContexte();

        // Boucle principale pour les 3 donjons
        while (m_donjonActuel < NOMBRE_DONJONS_TOTAL && !partiePerdue()) {
            System.out.println("\n" + "=".repeat(60));
            System.out.println("DONJON " + (m_donjonActuel + 1) + " / " + NOMBRE_DONJONS_TOTAL);
            System.out.println("=".repeat(60));

            Donjon donjonCourant = m_donjons.get(m_donjonActuel);

            // Phase d'équipement avant le donjon
            donjonCourant.premierePhase();

            // Mise en place du donjon
            donjonCourant.miseEnPlace();

            // Déroulement du donjon
            boolean donjonReussi = donjonCourant.deroulerDonjon();

            if (donjonReussi) {
                System.out.println("\n🎉 Donjon " + (m_donjonActuel + 1) + " terminé avec succès !");

                if (m_donjonActuel < NOMBRE_DONJONS_TOTAL - 1) {
                    // Régénération des PV entre les donjons
                    regenererPVJoueurs();
                    System.out.println("Les personnages récupèrent tous leurs points de vie !");
                    System.out.println("Préparez-vous pour le prochain donjon...");
                }

                m_donjonActuel++;
            } else {
                System.out.println("\n💀 Échec du donjon " + (m_donjonActuel + 1));
                break;
            }
        }

        // Fin de partie
        finPartie();
    }

    private boolean partiePerdue() {
        for (Personnage joueur : m_joueurs) {
            if (joueur.estMort()) {
                return true;
            }
        }
        return false;
    }

    private void regenererPVJoueurs() {
        for (Personnage joueur : m_joueurs) {
            joueur.setPointsDeVie(joueur.getPointsDeVieMax());
        }
    }

    public void finPartie() {
        System.out.println("\n" + "=".repeat(60));
        if (m_donjonActuel >= NOMBRE_DONJONS_TOTAL) {
            System.out.println("🏆 FÉLICITATIONS ! VOUS AVEZ GAGNÉ !");
            System.out.println("Vous avez triomphé des " + NOMBRE_DONJONS_TOTAL + " donjons !");
            System.out.println("Les aventuriers sont devenus des légendes !");
        } else {
            System.out.println("💀 VOUS AVEZ PERDU !");
            System.out.println("Cause de la défaite : Un ou plusieurs personnages sont morts au donjon " + (m_donjonActuel + 1));
            System.out.println("Les aventuriers ont péri dans les profondeurs...");
        }
        System.out.println("=".repeat(60));
        System.out.println("Merci d'avoir joué à DOOnjon&Dragon !");
    }

    // Getters et setters
    public List<Donjon> getDonjons() {
        return m_donjons;
    }

    public void setDonjons(List<Donjon> donjons) {
        this.m_donjons = donjons;
    }

    public List<Personnage> getJoueurs() {
        return m_joueurs;
    }

    public void setJoueurs(List<Personnage> joueurs) {
        this.m_joueurs = joueurs;
    }

    public MaitreDuJeu getMaitreDuJeu() {
        return m_maitreDuJeu;
    }

    public void setMaitreDuJeu(MaitreDuJeu maitreDuJeu) {
        this.m_maitreDuJeu = maitreDuJeu;
    }

    public int getDonjonActuel() {
        return m_donjonActuel;
    }
}