/**
 * Représente un donjon dans le jeu, contenant une carte, des monstres, des personnages,
 * des équipements, et la gestion des tours.
 * Gère l'initialisation du donjon, le placement des entités, la phase d'équipement,
 * le déroulement des combats, et la vérification des conditions de victoire.
 */

package carteDuJeu;

import carteDuJeu.actions.ChangerEquipement;
import carteDuJeu.personnages.equipements.*;
import carteDuJeu.personnages.*;
import carteDuJeu.monstres.*;

import java.util.*;

/**
 * Représente un Donjon dans le jeu.
 * Un donjon est constitué d'une carte, de monstres, d'équipements et de personnages joueurs.
 * Il est contrôlé par un maître du jeu, et gère les phases d'équipement, de placement et de combat.
 *
 * Responsabilités principales :
 * - Initialisation de la carte et des équipements
 * - Placement des entités (joueurs, monstres, équipements)
 * - Gestion des tours de jeu via {@link Tours}
 * - Évaluation de la victoire ou défaite à la fin du donjon
 *
 * Le donjon utilise des interactions console pour la configuration et les phases de jeu.
 */
public class Donjon {
    private int m_numeroDonjon;
    private Carte m_carte;
    private MaitreDuJeu m_maitreDuJeu;
    private List<Monstre> m_monstres;
    private List<Equipement> m_equipements;
    private List<Personnage> m_joueurs;
    private List<ElementMobile> m_entiteTour;
    private Tours m_gestionTours;

    /**
     * Crée un donjon avec son numéro, un maître du jeu, une liste d'équipements,
     * et les personnages/joueurs qui y participeront.
     * @param numeroDonjon numéro identifiant le donjon
     * @param maitreDuJeu maître du jeu responsable de ce donjon
     * @param tousLesEquipements liste globale des équipements disponibles
     * @param joueurs liste des personnages participant au donjon
     */
    public Donjon(int numeroDonjon, MaitreDuJeu maitreDuJeu, List<Equipement> tousLesEquipements, List<Personnage> joueurs) {
        this.m_numeroDonjon = numeroDonjon;
        this.m_maitreDuJeu = maitreDuJeu;
        this.m_joueurs = new ArrayList<>(joueurs);
        this.m_monstres = new ArrayList<>();
        this.m_equipements = new ArrayList<>();
        this.m_entiteTour = new ArrayList<>();
        initialiserCartePersonnalisee();
        initialiserEquipementsDonjon(tousLesEquipements);
        this.m_gestionTours = new Tours(this);
    }

    /**
     * Initialise la carte du donjon selon le choix de l'utilisateur (par défaut ou personnalisée).
     */
    private void initialiserCartePersonnalisee() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Configuration du Donjon " + m_numeroDonjon);

        String choix = "";
        while (!choix.equals("o") && !choix.equals("n")) {
            System.out.println("Voulez-vous utiliser une carte par défaut ? (o/n)");
            choix = scanner.nextLine().toLowerCase().trim();
            if (!choix.equals("o") && !choix.equals("n")) {
                System.out.println("Réponse invalide. Veuillez répondre par 'o' pour oui ou 'n' pour non.");
            }
        }

        if (choix.equals("o")) {
            Random random = new Random();
            switch (m_numeroDonjon) {
                case 1:
                    initialiserCarte(8, 10);
                    m_carte.setCarteParDefaut(true);
                    System.out.println("Carte par défaut du Donjon 1 : Caverne étroite");
                    m_carte.genererObstaclesAleatoires(random.nextDouble() * 0.05);
                    break;
                case 2:
                    initialiserCarte(14, 12);
                    m_carte.setCarteParDefaut(true);
                    System.out.println("Carte par défaut du Donjon 2 : Salle du trône");
                    m_carte.genererObstaclesAleatoires(random.nextDouble() * 0.075);
                    break;
                case 3:
                    initialiserCarte(21, 24);
                    m_carte.setCarteParDefaut(true);
                    System.out.println("Carte par défaut du Donjon 3 : Antre du dragon");
                    m_carte.genererObstaclesAleatoires(random.nextDouble() * 0.14);

                    break;
                default:
                    initialiserCarte(20, 15);
                    m_carte.setCarteParDefaut(true);
                    break;
            }
        } else {
            boolean carteValide = false;
            while (!carteValide) {
                try {
                    int largeur = demanderInt(scanner, "Quelle largeur pour le donjon " + m_numeroDonjon + " ? (1-25) : ");
                    int hauteur = demanderInt(scanner, "Quelle hauteur pour le donjon " + m_numeroDonjon + " ? (1-25) : ");
                    int obstacles = demanderInt(scanner, "Pourcentage d'obstacles (0-100) : ");
                    if (obstacles < 0 || obstacles > 100) {
                        System.out.println("Le pourcentage d'obstacles doit être compris entre 0 et 100. Veuillez réessayer.");
                        continue;
                    }
                    if (largeur < 1 || largeur > 25 || hauteur < 1 || hauteur > 25) {
                        System.out.println("Les dimensions doivent être comprises entre 1 et 25. Veuillez réessayer.");
                    } else {
                        initialiserCarte(largeur, hauteur);
                        m_carte.genererObstaclesAleatoires(obstacles / 100.0);
                        carteValide = true;
                    }
                } catch (Exception e) {
                    System.out.println("Erreur lors de la saisie. Veuillez réessayer.");
                }
            }
        }
    }

    /**
     * Initialise les équipements du donjon à partir de la liste globale.
     *
     * @param tousLesEquipements la liste de tous les équipements disponibles
     */
    private void initialiserEquipementsDonjon(List<Equipement> tousLesEquipements) {
        try {
            Scanner scanner = new Scanner(System.in);
            int nbEquipementsSouhaites;
            boolean carteParDefaut = m_carte != null && m_carte.isCarteParDefaut();

            if (carteParDefaut) {
                // Nombre d'équipements défini selon la carte par défaut
                switch (m_numeroDonjon) {
                    case 1:
                        nbEquipementsSouhaites = 3;
                        break;
                    case 2:
                        nbEquipementsSouhaites = 9;
                        break;
                    case 3:
                        nbEquipementsSouhaites = 16;
                        break;
                    default:
                        nbEquipementsSouhaites = 4;
                }
                System.out.println(nbEquipementsSouhaites + " équipements ajoutés automatiquement au donjon " + m_numeroDonjon);
            } else {
                nbEquipementsSouhaites = demanderInt(scanner, "Combien d'équipements dans le donjon " + m_numeroDonjon + " ?");
            }

            Random random = new Random();
            for (int i = 0; i < nbEquipementsSouhaites && !tousLesEquipements.isEmpty(); i++) {
                int index = random.nextInt(tousLesEquipements.size());
                Equipement equipementChoisi = tousLesEquipements.get(index);
                this.m_equipements.add(equipementChoisi.copier());
            }

            System.out.println(m_equipements.size() + " équipements ajoutés au donjon " + m_numeroDonjon);
        } catch (Exception e) {
            System.out.println("Erreur lors de l'initialisation des équipements : " + e.getMessage());
        }
    }

    /**
     * Initialise la carte du donjon avec la largeur et la hauteur spécifiées.
     *
     * @param largeur la largeur de la carte
     * @param hauteur la hauteur de la carte
     */
    public void initialiserCarte(int largeur, int hauteur) {
        if (this.m_carte == null) {
            this.m_carte = new Carte(largeur, hauteur);
            m_maitreDuJeu.setCarte(m_carte);
        } else {
            System.out.println("⚠️ Carte déjà initialisée, conservation du contenu existant");
        }
    }

    /**
     * Prépare le donjon en nettoyant les listes, générant les obstacles, plaçant monstres, joueurs et équipements.
     * Met à jour la carte du maître du jeu.
     */
    public void miseEnPlace() {
        System.out.println("=== Mise en place du Donjon " + m_numeroDonjon + " ===");

        // Nettoyer les listes précédentes
        m_monstres.clear();
        m_entiteTour.clear();

        // Création des monstres par le Maitre du Jeu
        System.out.println("Création des monstres du donjon " + m_numeroDonjon + "...");
        m_maitreDuJeu.phaseCreationDesMonstres();
        m_monstres = new ArrayList<>(m_maitreDuJeu.getMonstres());
        Affichage.afficherCarte(java.util.Optional.ofNullable(m_carte));
        // Vérification que des monstres ont été créés
        if (m_monstres.isEmpty()) {
            System.out.println("⚠️ Aucun monstre créé pour ce donjon !");
        }
        // Placement aléatoire des monstres
        System.out.println("Placement des monstres...");
        for (Monstre monstre : m_monstres) {
            boolean placementReussi = m_carte.ajouterContenuAleatoire(monstre);
            if (!placementReussi) {
                System.out.println("⚠️ Impossible de placer " + monstre.getNom() + " sur la carte");
            }
        }

        // Placement aléatoire des joueurs
        System.out.println("Placement des joueurs...");
        for (Personnage joueur : m_joueurs) {
            if (!joueur.estMort()) {  // Ne placer que les joueurs vivants
                boolean placementReussi = m_carte.ajouterContenuAleatoire(joueur);
                if (!placementReussi) {
                    System.out.println("⚠️ Impossible de placer " + joueur.getNom() + " sur la carte");
                }
            }
        }

        // Placement aléatoire des équipements
        System.out.println("Placement des équipements...");
        for (Equipement equipement : m_equipements) {
            boolean placementReussi = m_carte.ajouterContenuAleatoire(equipement);
            if (!placementReussi) {
                System.out.println("⚠️ Impossible de placer " + equipement.getNom() + " sur la carte");
            }
        }
        m_maitreDuJeu.setCarte(m_carte); // Mettre à jour la carte du Maitre du Jeu
        // Affichage de la carte
        System.out.println("Affichage de la carte du donjon " + m_numeroDonjon + "...");
        Affichage.afficherCarte(java.util.Optional.ofNullable(m_carte));
        System.out.println("Le donjon " + m_numeroDonjon + " est en place !");
    }

    /**
     * Déroule le donjon : prépare l'initiative, lance les tours et vérifie la victoire.
     *
     * @return true si le donjon est réussi, false sinon
     */
    public boolean deroulerDonjon() {
        System.out.println("=== Début du combat dans le donjon " + m_numeroDonjon + " ===");

        // Vérifications préliminaires
        if (m_joueurs.stream().allMatch(ElementMobile::estMort)) {
            System.out.println("💀 Tous les joueurs sont morts ! Impossible de commencer le donjon.");
            return false;
        }

        if (m_monstres.isEmpty()) {
            System.out.println("🎉 Aucun monstre dans ce donjon ! Victoire automatique.");
            return true;
        }

        // Préparer l'ordre d'initiative
        preparerEtTrierInitiative();

        // Utiliser le gestionnaire de tours
        m_gestionTours.commencerTours();

        // Déterminer le résultat
        return verifierVictoire();
    }

    /**
     * Vérifie les conditions de victoire du donjon.
     *
     * @return true si tous les monstres sont morts, false si un joueur est mort
     */
    private boolean verifierVictoire() {
        boolean unJoueurMort = m_joueurs.stream().anyMatch(ElementMobile::estMort);
        boolean tousMonstresMorts = m_monstres.stream().allMatch(ElementMobile::estMort);

        if (unJoueurMort) {
            System.out.println("💀 Défaite ! Tous les personnages sont morts dans le donjon " + m_numeroDonjon);
            return false;
        } else if (tousMonstresMorts) {
            System.out.println("🎉 Victoire ! Tous les monstres du donjon " + m_numeroDonjon + " ont été vaincus !");
            return true;
        }

        // Cas où ni tous les joueurs ni tous les monstres sont morts (ne devrait pas arriver)
        System.out.println("⚠️ État incohérent du donjon détecté");
        return false;
    }

    /**
     * Gère la phase d'équipement pour chaque joueur avant le début du donjon.
     */
    public void premierePhase() {
        Scanner scanner = new Scanner(System.in);
        ChangerEquipement gestionEquipement = new ChangerEquipement();

        System.out.println("\n--- PHASE D'ÉQUIPEMENT - DONJON " + m_numeroDonjon + " ---\n");

        for (Personnage joueur : m_joueurs) {
            if (joueur.estMort()) {
                System.out.println("💀 " + joueur.getNom() + " est mort et ne peut pas s'équiper.");
                continue;
            }

            System.out.println("Joueur : " + joueur.getNom());
            System.out.println("Classe : " + joueur.getClasse() + " | Race : " + joueur.getRace());
            System.out.println("PV: " + joueur.getPointsDeVie() + "/" + joueur.getPointsDeVieMax());
            System.out.println("Arme équipée : " +
                    (joueur.getArmeEquipee() != null ? joueur.getArmeEquipee().getNom() : "Aucune"));
            System.out.println("Armure équipée : " +
                    (joueur.getArmureEquipee() != null ? joueur.getArmureEquipee().getNom() : "Aucune"));

            boolean continuer = true;
            while (continuer) {
                System.out.println("\nQue voulez-vous faire ?");
                System.out.println("1. Changer d'équipement");
                System.out.println("2. Voir l'inventaire");
                System.out.println("3. Ne rien changer");

                int choix = demanderInt(scanner, "Choix : ");

                switch (choix) {
                    case 1:
                        gestionEquipement.proposerChangement(joueur);
                        break;
                    case 2:
                        Affichage affichage = new Affichage();
                        affichage.afficherInventaire(joueur);
                        break;
                    case 3:
                        continuer = false;
                        break;
                    default:
                        System.out.println("Choix invalide. Réessayez.");
                        break;
                }
            }
            System.out.println("------------------------------------\n");
        }

        System.out.println("Tous les joueurs vivants sont prêts pour le donjon " + m_numeroDonjon + ".\n");
    }

    /**
     * Prépare et trie l'ordre d'initiative des entités mobiles du donjon.
     */
    public void preparerEtTrierInitiative() {
        Random random = new Random();
        m_entiteTour.clear();

        // Ajouter seulement les entités mobiles
        for (Personnage joueur : m_joueurs) {
            if (!joueur.estMort()) {
                m_entiteTour.add(joueur);
            }
        }

        for (Monstre monstre : m_monstres) {
            if (!monstre.estMort()) {
                m_entiteTour.add(monstre);
            }
        }

        // Trier la liste par initiative totale (lancer + initiative de base)
        m_entiteTour.sort((e1, e2) -> {
            int lancerE1 = random.nextInt(20) + 1;
            int lancerE2 = random.nextInt(20) + 1;
            int initiativeE1 = lancerE1 + e1.getInitiative();
            int initiativeE2 = lancerE2 + e2.getInitiative();

            System.out.println(e1.getNom() + " lance " + lancerE1 + " + " + e1.getInitiative() + " = " + initiativeE1);
            System.out.println(e2.getNom() + " lance " + lancerE2 + " + " + e2.getInitiative() + " = " + initiativeE2);

            return Integer.compare(initiativeE2, initiativeE1); // Ordre décroissant
        });

        System.out.println("\n--- Ordre d'initiative pour le donjon " + m_numeroDonjon + " ---");
        for (int i = 0; i < m_entiteTour.size(); i++) {
            ElementMobile entite = m_entiteTour.get(i);
            String type = entite.estPersonnage() ? "[JOUEUR]" : "[MONSTRE]";
            System.out.println((i + 1) + ". " + type + " " + entite.getNom());
        }
        System.out.println("--------------------------------------------------------\n");
    }

    /**
     * Affiche les statistiques finales et l'état des personnages à la fin du donjon.
     */
    public void finDonjon() {
        System.out.println("\n=== Fin du donjon " + m_numeroDonjon + " ===");

        // Statistiques finales
        int joueursVivants = (int) m_joueurs.stream().filter(j -> !j.estMort()).count();
        int monstresVivants = (int) m_monstres.stream().filter(m -> !m.estMort()).count();

        System.out.println("Joueurs vivants : " + joueursVivants + "/" + m_joueurs.size());
        System.out.println("Monstres vivants : " + monstresVivants + "/" + m_monstres.size());

        // Affichage de l'état final des joueurs
        System.out.println("\n--- État final des personnages ---");
        for (Personnage joueur : m_joueurs) {
            String statut = joueur.estMort() ? "💀 MORT" : "❤️ VIVANT";
            System.out.println(joueur.getNom() + " : " + statut +
                    " (PV: " + joueur.getPointsDeVie() + "/" + joueur.getPointsDeVieMax() + ")");
        }
    }

    /**
     * Demande à l'utilisateur de saisir un entier avec un message personnalisé.
     *
     * @param scanner le scanner à utiliser pour la saisie
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
     * Retourne le numéro du donjon.
     *
     * @return le numéro du donjon
     */
    public int getNumeroDonjon() {
        return m_numeroDonjon;
    }

    /**
     * Retourne la carte du donjon.
     *
     * @return la carte du donjon
     */
    public Carte getCarte() {
        return m_carte;
    }

    /**
     * Définit la carte du donjon.
     *
     * @param carte la nouvelle carte
     */
    public void setCarte(Carte carte) {
        this.m_carte = carte;
    }

    /**
     * Retourne la liste des monstres du donjon.
     *
     * @return la liste des monstres
     */
    public List<Monstre> getMonstres() {
        return m_monstres;
    }

    /**
     * Définit la liste des monstres du donjon.
     *
     * @param monstres la nouvelle liste de monstres
     */
    public void setMonstres(List<Monstre> monstres) {
        this.m_monstres = new ArrayList<>(monstres);
    }

    /**
     * Retourne la liste des équipements du donjon.
     *
     * @return la liste des équipements
     */
    public List<Equipement> getEquipements() {
        return m_equipements;
    }

    /**
     * Définit la liste des équipements du donjon.
     *
     * @param equipements la nouvelle liste d'équipements
     */
    public void setEquipements(List<Equipement> equipements) {
        this.m_equipements = new ArrayList<>(equipements);
    }

    /**
     * Récupère la liste des joueurs du donjon.
     *
     * @return la liste des joueurs
     */
    public List<Personnage> getJoueurs() {
        return m_joueurs;
    }

    /**
     * Définit la liste des joueurs du donjon.
     *
     * @param joueurs la nouvelle liste de joueurs
     */
    public void setJoueurs(List<Personnage> joueurs) {
        this.m_joueurs = new ArrayList<>(joueurs);
    }

    /**
     * Retourne la liste des entités pour l'ordre de tour.
     *
     * @return la liste des entités du tour
     */
    public List<ElementMobile> getEntiteTour() {
        return m_entiteTour;
    }

    /**
     * Retourne le maître du jeu associé à ce donjon.
     *
     * @return le maître du jeu
     */
    public MaitreDuJeu getMaitreDuJeu() {
        return m_maitreDuJeu;
    }

    /**
     * Retourne le gestionnaire de tours du donjon.
     *
     * @return le gestionnaire de tours
     */
    public Tours getGestionTours() {
        return m_gestionTours;
    }
}