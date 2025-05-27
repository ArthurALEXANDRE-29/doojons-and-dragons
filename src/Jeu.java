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

    public Jeu() {
        Scanner scanner = new Scanner(System.in);

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

        // Créer le maître du jeu AVANT les donjons
        m_maitreDuJeu = new MaitreDuJeu(m_joueurs, new ArrayList<>());

        // Créer la liste de tous les équipements disponibles
        List<Equipement> tousLesEquipements = creerTousLesEquipements();

        // Création des donjons (pour l'instant juste un)
        m_donjons = new ArrayList<>();
        m_donjons.add(new Donjon(m_maitreDuJeu, tousLesEquipements, m_joueurs));

        if (!m_donjons.isEmpty()) {
            m_donjons.get(0).premierePhase();
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

        // Lancer le premier donjon
        if (!m_donjons.isEmpty()) {
            Donjon premierDonjon = m_donjons.get(0);
            premierDonjon.miseEnPlace();
            // Ici vous pouvez ajouter la logique pour dérouler le donjon
        }
    }

    // Méthode pour finir la partie
    public void finPartie() {
        System.out.println("La partie est terminée.");
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
}