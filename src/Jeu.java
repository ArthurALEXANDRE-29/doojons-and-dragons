import java.util.InputMismatchException;
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

/**
 * Classe principale qui gère le déroulement global du jeu DOOnjon&Dragon.
 * Elle orchestre la création des joueurs, des donjons, le déroulement des parties,
 * la gestion des victoires/défaites et la régénération des personnages.
 */
public class Jeu {
    private List<Donjon> m_donjons;
    private List<Personnage> m_joueurs;
    private MaitreDuJeu m_maitreDuJeu;
    private int m_donjonActuel; // Index du donjon en cours
    private static final int NOMBRE_DONJONS_TOTAL = 3;

    /**
     * Constructeur du jeu. Initialise les joueurs, le maître du jeu, les équipements et les donjons.
     */
    public Jeu() {
        m_donjonActuel = 0;

        messageBienvenue();

        // Demander le nombre de joueurs AVANT de créer les donjons
        int nbJoueurs = 0;
        Scanner scanner = new Scanner(System.in);
        while (nbJoueurs < 1) {
            nbJoueurs = demanderInt(scanner, "Combien de joueurs voulez-vous créer ? (minimum 1) ");
            if (nbJoueurs < 1) {
                System.out.println("Le nombre de joueurs doit être au moins 1. Veuillez réessayer.");
            }
        }

        // Créer les joueurs d'abord
        m_joueurs = new ArrayList<>();
        for (int i = 1; i <= nbJoueurs; i++) {

            System.out.print("Nom du joueur #" + i + " : ");
            String nom = scanner.nextLine().trim();
            while (nom.isEmpty()) {
                System.out.println("Le nom ne peut pas être vide. Veuillez réessayer.");
                System.out.print("Nom du joueur #" + i + " : ");
                nom = scanner.nextLine().trim();
            }

            System.out.print("Choix de la race du joueur #" + i + " : ");
            Race race = creerRace(scanner);
            System.out.print("Choix de la classe du joueur #" + i + " : ");
            Classe classe = creerClasse(scanner);

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

    /**
     * Affiche un message de bienvenue au joueur.
     */
    private void messageBienvenue() {
        System.out.println("\u001B[31m╔═══════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                                                                               ║");
        System.out.println("║    ██████╗ ██╗   ██╗███╗   ██╗ ██████╗ ███████╗ ██████╗ ███╗   ██╗███████╗    ║");
        System.out.println("║    ██╔══██╗██║   ██║████╗  ██║██╔════╝ ██╔════╝██╔═══██╗████╗  ██║██╔════╝    ║");
        System.out.println("║    ██║  ██║██║   ██║██╔██╗ ██║██║  ███╗█████╗  ██║   ██║██╔██╗ ██║███████╗    ║");
        System.out.println("║    ██║  ██║██║   ██║██║╚██╗██║██║   ██║██╔══╝  ██║   ██║██║╚██╗██║╚════██║    ║");
        System.out.println("║    ██████╔╝╚██████╔╝██║ ╚████║╚██████╔╝███████╗╚██████╔╝██║ ╚████║███████║    ║");
        System.out.println("║    ╚═════╝  ╚═════╝ ╚═╝  ╚═══╝ ╚═════╝ ╚══════╝ ╚═════╝ ╚═╝  ╚═══╝╚══════╝    ║");
        System.out.println("║                                       &                                       ║");
        System.out.println("║          ██████╗ ██████╗  █████╗  ██████╗  ██████╗ ███╗   ██╗███████╗         ║");
        System.out.println("║          ██╔══██╗██╔══██╗██╔══██╗██╔════╝ ██╔═══██╗████╗  ██║██╔════╝         ║");
        System.out.println("║          ██║  ██║██████╔╝███████║██║  ███╗██║   ██║██╔██╗ ██║███████╗         ║");
        System.out.println("║          ██║  ██║██╔══██╗██╔══██║██║   ██║██║   ██║██║╚██╗██║╚════██║         ║");
        System.out.println("║          ██████╔╝██║  ██║██║  ██║╚██████╔╝╚██████╔╝██║ ╚████║███████║         ║");
        System.out.println("║          ╚═════╝ ╚═╝  ╚═╝╚═╝  ╚═╝ ╚═════╝  ╚═════╝ ╚═╝  ╚═══╝╚══════╝         ║");
        System.out.println("║                                                                               ║");
        System.out.println("╚═══════════════════════════════════════════════════════════════════════════════╝\u001B[0m");
        System.out.println();
        System.out.println("Les ténèbres s'étendent sur le royaume. Trois donjons maudits se dressent devant vous,");
        System.out.println("chacun grouillant de créatures maléfiques qui terrorisent les terres. Votre mission");
        System.out.println("est simple mais mortelle : pénétrer dans chaque donjon et éliminer toute forme de");
        System.out.println("vie hostile. Aucune créature ne doit survivre. Seule l'extermination totale brisera");
        System.out.println("la malédiction qui ronge ces lieux. Préparez-vous à affronter l'horreur.");
        System.out.println();
        System.out.println("Que votre lame soit aiguisée et votre courage inébranlable...");
        System.out.println();
    }

    /**
     * Crée une instance de Race à partir d'une chaîne de caractères.
     * @param scanner le scanner utilisé pour la saisie
     * @return l'objet Race correspondant
     */
    private Race creerRace(Scanner scanner) {
        while (true) {
            System.out.println("Races disponibles : Humain, Nain, Elfe, Halfelin");
            System.out.print("Race du joueur : ");
            String raceStr = scanner.nextLine();
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
                    System.out.println("Race inconnue, veuillez réessayer.");
            }
        }
    }

    /**
     * Crée une instance de Classe à partir d'une chaîne de caractères.
     * @param scanner le scanner utilisé pour la saisie
     * @return l'objet Classe correspondant
     */
    private Classe creerClasse(Scanner scanner) {
        while (true) {
            System.out.println("Classes disponibles : Guerrier, Clerc, Magicien, Roublard");
            System.out.print("Classe du joueur : ");
            String classeStr = scanner.nextLine();
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
                    System.out.println("Classe inconnue, veuillez réessayer.");
            }
        }
    }

    /**
     * Crée la liste de tous les équipements disponibles dans le jeu.
     * @return la liste des équipements
     */
    private List<Equipement> creerTousLesEquipements() {
        List<Equipement> equipements = new ArrayList<>();

        // Armes de corps à corps courantes
        equipements.add(new Baton());
        equipements.add(new MasseDarmes());

        // Armes de guerre de corps à corps
        equipements.add(new EpeeLongue());
        equipements.add(new Rapiere());
        equipements.add(new EpeeADeuxMains());

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

    /**
     * Démarre la partie et gère la boucle principale des donjons.
     */
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

    /**
     * Vérifie si la partie est perdue (si un ou plusieurs personnages sont morts).
     * @return true si la partie est perdue, false sinon
     */
    private boolean partiePerdue() {
        for (Personnage joueur : m_joueurs) {
            if (joueur.estMort()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Régénère les points de vie de tous les joueurs à leur maximum.
     */
    private void regenererPVJoueurs() {
        for (Personnage joueur : m_joueurs) {
            joueur.setPointsDeVie(joueur.getPointsDeVieMax());
        }
    }

    /**
     * Affiche le message de fin de partie selon la victoire ou la défaite.
     */
    public void finPartie() {
        System.out.println("\n" + "=".repeat(60));
        if (m_donjonActuel >= NOMBRE_DONJONS_TOTAL) {
            System.out.println("🏆 Félicitations ! Vous avez réussi à terminer tous les donjons !");
            System.out.println("Vos héros ont triomphé des ténèbres et des monstres qui les habitaient. ");
            System.out.println("Les donjons sont désormais scellé, et la paix revient dans le royaume. ");
            System.out.println("Vos personnages sont devenus des légendes\n");
        } else {
            System.out.println("💀 L'intégralité de nos héros ont succombé aux ténèbres... ");
            System.out.println("Les monstres ont triomphé dans ce ballet macabre. ");
            System.out.println("Le donjon restera ouvert, attendant que d'autres aventuriers ");
            System.out.println("téméraires viennent y trouver leur destin funeste...\n");
        }
        System.out.println("=".repeat(60));
        System.out.println("Merci d'avoir joué à DOOnjon&Dragon !");
    }

    /**
     * Demande à l'utilisateur de saisir un entier avec gestion des erreurs.
     * @param scanner le scanner utilisé pour la saisie
     * @param message le message à afficher
     * @return la valeur entière saisie
     */
    private int demanderInt(Scanner scanner, String message) {
        int valeur;
        while (true) {
            System.out.print(message);
            try {
                valeur = scanner.nextInt();
                scanner.nextLine();
                return valeur;
            } catch (InputMismatchException e) {
                System.out.println("Entrée invalide, veuillez entrer un nombre.");
                scanner.nextLine();
            }
        }
    }

    // Getters et setters

    /**
     * Retourne la liste des donjons.
     * @return la liste des donjons
     */
    public List<Donjon> getDonjons() {
        return m_donjons;
    }

    /**
     * Définit la liste des donjons.
     * @param donjons la nouvelle liste de donjons
     */
    public void setDonjons(List<Donjon> donjons) {
        this.m_donjons = donjons;
    }

    /**
     * Retourne la liste des joueurs.
     * @return la liste des joueurs
     */
    public List<Personnage> getJoueurs() {
        return m_joueurs;
    }

    /**
     * Définit la liste des joueurs.
     * @param joueurs la nouvelle liste de joueurs
     */
    public void setJoueurs(List<Personnage> joueurs) {
        this.m_joueurs = joueurs;
    }

    /**
     * Retourne le maître du jeu.
     * @return le maître du jeu
     */
    public MaitreDuJeu getMaitreDuJeu() {
        return m_maitreDuJeu;
    }

    /**
     * Définit le maître du jeu.
     * @param maitreDuJeu le nouveau maître du jeu
     */
    public void setMaitreDuJeu(MaitreDuJeu maitreDuJeu) {
        this.m_maitreDuJeu = maitreDuJeu;
    }

    /**
     * Retourne l'index du donjon actuel.
     * @return l'index du donjon actuel
     */
    public int getDonjonActuel() {
        return m_donjonActuel;
    }

    /*============================Section Overrides============================*/

    @Override
    public String toString() {
        return "Jeu{" +
                "donjonActuel=" + m_donjonActuel +
                ", nombreDonjons=" + (m_donjons != null ? m_donjons.size() : 0) +
                ", joueurs=" + (m_joueurs != null ? m_joueurs.toString() : "Aucun joueurs") +
                ", maitreDuJeu=" + (m_maitreDuJeu != null ? m_maitreDuJeu.toString() : "Aucun maitre du jeu") +
                '}';
    }
}