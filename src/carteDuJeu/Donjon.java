package carteDuJeu;

import carteDuJeu.actions.ChangerEquipement;
import carteDuJeu.personnages.equipements.*;
import carteDuJeu.personnages.*;
import carteDuJeu.Monstres.*;

import java.util.*;

public class Donjon {

    // Attributs privés
    private int m_numeroDonjon;
    private Carte m_carte;
    private MaitreDuJeu m_maitreDuJeu;
    private List<Monstre> m_monstres;
    private List<Equipement> m_equipements;
    private List<Personnage> m_joueurs;
    private List<ElementMobile> m_entiteTour;
    private Tours m_gestionTours;

    // Constructeur
    public Donjon(int numeroDonjon, MaitreDuJeu maitreDuJeu, List<Equipement> tousLesEquipements, List<Personnage> joueurs) {
        this.m_numeroDonjon = numeroDonjon;
        this.m_maitreDuJeu = maitreDuJeu;
        this.m_joueurs = new ArrayList<>(joueurs); // Copie défensive
        this.m_monstres = new ArrayList<>();
        this.m_equipements = new ArrayList<>();
        this.m_entiteTour = new ArrayList<>();

        // Initialisation de la carte spécifique à ce donjon
        initialiserCartePersonnalisee();

        // Initialisation des équipements du donjon
        initialiserEquipementsDonjon(tousLesEquipements);

        // Initialisation du gestionnaire de tours
        this.m_gestionTours = new Tours(this);
    }

    private void initialiserCartePersonnalisee() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Configuration du Donjon " + m_numeroDonjon);
        System.out.println("Voulez-vous utiliser une carte par défaut ? (o/n)");
        String choix = scanner.nextLine().toLowerCase();

        if (choix.equals("o")) {
            // Cartes par défaut selon le numéro du donjon
            switch (m_numeroDonjon) {
                case 1:
                    initialiserCarte(15, 10); // Donjon 1: petite carte
                    System.out.println("Carte par défaut du Donjon 1 : Caverne étroite (15x10)");
                    break;
                case 2:
                    initialiserCarte(20, 15); // Donjon 2: carte moyenne
                    System.out.println("Carte par défaut du Donjon 2 : Salle du trône (20x15)");
                    break;
                case 3:
                    initialiserCarte(25, 25); // Donjon 3: grande carte
                    System.out.println("Carte par défaut du Donjon 3 : Antre du dragon (25x25)");
                    break;
                default:
                    initialiserCarte(20, 15);
                    break;
            }
        } else {
            // Configuration personnalisée
            try {
                int largeur = demanderInt(scanner, "Quelle largeur pour le donjon " + m_numeroDonjon + " ? ( < 25) : ");
                int hauteur = demanderInt(scanner, "Quelle hauteur pour le donjon " + m_numeroDonjon + " ? ( < 25) : ");
                largeur = Math.max(1, Math.min(25, largeur));
                hauteur = Math.max(1, Math.min(25, hauteur));
                initialiserCarte(largeur, hauteur);
            } catch (Exception e) {
                System.out.println("Erreur lors de la configuration, utilisation des valeurs par défaut.");
                initialiserCarte(20, 15);
            }
        }
    }

    private void initialiserEquipementsDonjon(List<Equipement> tousLesEquipements) {
        try {
            Scanner scanner = new Scanner(System.in);
            int nbEquipementsSouhaites = demanderInt(scanner, "Combien d'équipements dans le donjon " + m_numeroDonjon + " ? (recommandé: " + (2 + m_numeroDonjon) + ") ");

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

    public void initialiserCarte(int largeur, int hauteur) {
        // Réinitialiser complètement la carte pour ce donjon
        this.m_carte = new Carte(largeur, hauteur);

        // Informer le maître du jeu de la nouvelle carte
        m_maitreDuJeu.setCarte(m_carte);
    }

    // Méthode pour la mise en place du donjon
    public void miseEnPlace() {
        System.out.println("=== Mise en place du Donjon " + m_numeroDonjon + " ===");

        // Nettoyer les listes précédentes
        m_monstres.clear();
        m_entiteTour.clear();

        // Création des monstres par le Maitre du Jeu
        System.out.println("Création des monstres du donjon " + m_numeroDonjon + "...");
        m_maitreDuJeu.phaseCreationDesMonstres();
        m_monstres = new ArrayList<>(m_maitreDuJeu.getMonstres());

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

        // Affichage de la carte
        System.out.println("Affichage de la carte du donjon " + m_numeroDonjon + "...");
        m_carte.afficher();
        System.out.println("Le donjon " + m_numeroDonjon + " est en place !");
    }

    // Méthode principale pour dérouler le donjon
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
     * Vérifie les conditions de victoire
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
     * Phase d'équipement avant le combat
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
                        afficherInventaire(joueur);
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
     * Affiche l'inventaire d'un personnage
     */
    private void afficherInventaire(Personnage personnage) {
        System.out.println("\n--- Inventaire de " + personnage.getNom() + " ---");
        if (personnage.getInventaire().isEmpty()) {
            System.out.println("Inventaire vide.");
        } else {
            for (int i = 0; i < personnage.getInventaire().size(); i++) {
                Equipement equip = personnage.getInventaire().get(i);
                if (equip.estUneArme()) {
                    System.out.println((i + 1) + ". " + equip.getNom() + " (Arme)");
                } else if (equip.estUneArmure()) {
                    System.out.println((i + 1) + ". " + equip.getNom() + " (Armure)");
                }
            }
        }
        System.out.println("----------------------------------------");
    }

    /**
     * Prépare et trie l'ordre d'initiative
     */
    public void preparerEtTrierInitiative() {
        Random random = new Random();
        m_entiteTour.clear();

        // Ajouter seulement les entités vivantes
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
     * Méthode appelée à la fin du donjon pour nettoyer
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
    public int getNumeroDonjon() {
        return m_numeroDonjon;
    }

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
        this.m_monstres = new ArrayList<>(monstres);
    }

    public List<Equipement> getEquipements() {
        return m_equipements;
    }

    public void setEquipements(List<Equipement> equipements) {
        this.m_equipements = new ArrayList<>(equipements);
    }

    public List<Personnage> getJoueurs() {
        return m_joueurs;
    }

    public void setJoueurs(List<Personnage> joueurs) {
        this.m_joueurs = new ArrayList<>(joueurs);
    }

    public List<ElementMobile> getEntiteTour() {
        return m_entiteTour;
    }

    public MaitreDuJeu getMaitreDuJeu() {
        return m_maitreDuJeu;
    }

    public Tours getGestionTours() {
        return m_gestionTours;
    }
}