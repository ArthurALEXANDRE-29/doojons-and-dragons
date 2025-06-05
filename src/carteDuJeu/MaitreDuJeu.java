package carteDuJeu;

import carteDuJeu.monstres.Monstre;
import carteDuJeu.personnages.Personnage;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * Gère le rôle du Maître du Jeu : création des monstres, gestion de la carte,
 * interactions spéciales et actions administratives sur le donjon.
 * Le Maître du Jeu contrôle la carte courante, la création des monstres et peut
 * infliger des dégâts ou déplacer des entités.
 */
public class MaitreDuJeu {
    private List<Monstre> m_monstres;
    private List<Personnage> m_joueurs;
    private final String m_nomMdj = "Maitre du Jeu";
    private Carte m_carteActuelle;

    /**
     * Construit un Maître du Jeu avec la liste des joueurs.
     * @param joueurs la liste des personnages joueurs
     */
    public MaitreDuJeu(List<Personnage> joueurs) {
        this.m_monstres = new ArrayList<>();
        this.m_joueurs = new ArrayList<>(joueurs);
        this.m_carteActuelle = null;
    }

    /**
     * Définit la carte courante contrôlée par le Maître du Jeu.
     * @param carte la carte à contrôler
     */
    public void setCarte(Carte carte) {
        this.m_carteActuelle = carte;
        System.out.println("Le Maître du Jeu prend contrôle d'une nouvelle carte (" +
                carte.getLargeur() + "x" + carte.getHauteur() + ")");
    }

    /**
     * Retourne la carte actuellement contrôlée.
     * @return la carte courante
     */
    public Carte getCarte() {
        return m_carteActuelle;
    }

    /**
     * Affiche le contexte narratif du donjon pour les joueurs.
     */
    public void decrireContexte() {
        System.out.println("Bienvenue dans le donjon mystérieux !");
        System.out.println("Vous incarnez des aventuriers courageux, prêts à affronter des monstres redoutables.");
        System.out.println("Tuez tout les monstres qui se dressent sur votre chemin");
        System.out.println("Bonne chance à vous !");
    }

    /**
     * Lance la phase de création des monstres : demande à l'utilisateur les caractéristiques
     * de chaque monstre à créer et les ajoute à la liste des monstres.
     */
    public void phaseCreationDesMonstres() {
        m_monstres.clear();
        Scanner scanner = new Scanner(System.in);
        int nombreMonstres = demanderInt(scanner, "Combien de monstres voulez-vous créer ? ");
        for (int i = 1; i <= nombreMonstres; i++) {
            Monstre monstre = creerMonstreInteractif(scanner, i);
            m_monstres.add(monstre);
            System.out.println("Monstre " + monstre.getEspece() + " (ID: " + monstre.getNumero() + ") a été crée avec succès.");
            System.out.println("-------------------------------------------\n");
        }
        System.out.println("Création du/des monstre(s) terminée(s). " + m_monstres.size() + " monstres créés.");
    }

    /**
     * Crée un monstre interactif en demandant les caractéristiques à l'utilisateur.
     * @param scanner le scanner pour la saisie utilisateur
     * @param numero le numéro du monstre (pour l'affichage)
     * @return le monstre créé
     */
    private Monstre creerMonstreInteractif(Scanner scanner, int numero) {
        String espece = demanderNomMonstre(scanner, numero);
        int portee = demanderInt(scanner, "Portée (1 pour mêlée, >1 pour distance) : ");
        int maxDmg = demanderInt(scanner, "Dégâts max par dé : ");
        int nbDes = demanderInt(scanner, "Nombre de dés : ");
        int pvMax = demanderInt(scanner, "Points de vie max : ");
        int caracAttaque = demanderInt(scanner, "Caractéristique d'attaque (force ou dextérité selon portée) : ");
        int classeArmure = demanderInt(scanner, "Classe d'armure : ");
        int initiative = demanderInt(scanner, "Initiative : ");
        int vitesse = demanderInt(scanner, "Vitesse : ");
        return new Monstre(espece, numero, portee, maxDmg, vitesse, nbDes, pvMax, caracAttaque, classeArmure, initiative);
    }

    /**
     * Retourne la liste des monstres créés par le Maître du Jeu.
     * @return la liste des monstres
     */
    public List<Monstre> getMonstres() {
        return m_monstres;
    }

    /**
     * Permet au Maître du Jeu d'infliger des dégâts à un monstre ou un joueur via la foudre divine.
     * @param joueurs la liste des personnages joueurs pouvant être ciblés
     */
    public void faireDmg(List<Personnage> joueurs) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Voulez-vous utiliser la foudre divine ? (o/n) ");
        String rep = scanner.nextLine().trim().toLowerCase();

        if (!rep.equals("o")) return;

        Affichage.afficherCiblesDisponibles(m_monstres, m_joueurs);

        System.out.print("\nTapez le nom exact de la cible : ");
        String nomCible = scanner.nextLine().trim();

        for (Monstre m : m_monstres) {
            if (m.getNom().equalsIgnoreCase(nomCible)) {
                int dmg = demanderInt(scanner, "Donnez les dégats à infliger à " + m.getNom() + " : ");
                infligerDegats(m, dmg);
                return;
            }
        }

        for (Personnage j : joueurs) {
            if (j.getNom().equalsIgnoreCase(nomCible)) {
                int dmg = demanderInt(scanner, "Donnez les dégats à infliger à " + j.getNom() + " : ");
                infligerDegats(j, dmg);
                return;
            }
        }

        System.out.println("❌ Aucun monstre ou joueur trouvé avec ce nom.");
    }

    /**
     * Inflige des dégâts à une cible (monstre ou joueur) et gère sa mort éventuelle.
     * @param cible l'entité à blesser
     * @param degats le nombre de dégâts à infliger
     */
    private void infligerDegats(ElementMobile cible, int degats) {
        cible.subirDegats(degats);
        System.out.println("⚡ " + cible.getNom() + " a été frappé par la foudre divine et subit " + degats + " dégâts !");
        if (cible.estMort()) {
            System.out.println("💀 " + cible.getNom() + " est mort !");
            if (m_carteActuelle != null && m_carteActuelle.contientElement(cible)) {
                Case caseCible = m_carteActuelle.getCase(cible)
                        .orElseThrow(() -> new IllegalArgumentException("Case introuvable"));
                if (caseCible != null) {
                    caseCible.retirerContenu(cible);
                }
            }
        }
    }

    /**
     * Permet de déplacer un monstre ou un joueur par son nom, après saisie utilisateur.
     */
    public void deplacerCibleParNom() {
        if (m_carteActuelle == null) {
            System.out.println("❌ Aucune carte disponible pour le déplacement.");
            return;
        }

        Scanner scanner = new Scanner(System.in);

        Affichage.afficherEntitesDeplacables(m_joueurs, m_monstres, m_carteActuelle);

        ElementMobile cible = null;
        while (cible == null) {
            System.out.print("Entrez le nom du monstre ou joueur à déplacer : ");
            String nomCible = scanner.nextLine().trim();

            for (Monstre m : m_monstres) {
                if (m.getNom().equalsIgnoreCase(nomCible)) {
                    cible = m;
                    break;
                }
            }
            if (cible == null) {
                for (Personnage j : m_joueurs) {
                    if (j.getNom().equalsIgnoreCase(nomCible)) {
                        cible = j;
                        break;
                    }
                }
            }

            if (cible == null) {
                System.out.println("❌ Aucun monstre ou joueur trouvé avec ce nom. Veuillez réessayer.");
            }
        }

        Case caseActuelle;
        try {
            caseActuelle = m_carteActuelle.getCase(cible)
                    .orElseThrow(() -> new IllegalArgumentException("Case introuvable"));
            System.out.println(cible.getNom() + " est actuellement en (" + caseActuelle.getX() + ", " + caseActuelle.getY() + ")");
        } catch (IllegalArgumentException e) {
            System.out.println("❌ Impossible de trouver la case actuelle de la cible.");
            return;
        }

        boolean coordonneesValides = false;
        while (!coordonneesValides) {
            System.out.print("Entrez la nouvelle coordonnée X (lettre de A à " + (char)('A' + m_carteActuelle.getLargeur() - 1) + ") : ");
            String input = scanner.nextLine().trim().toUpperCase();

            if (input.length() != 1) {
                System.out.println("❌ Veuillez entrer une seule lettre.");
                continue;
            }

            char lettreX = input.charAt(0);
            int newX = lettreX - 'A';

            int newYUtilisateur = demanderInt(scanner, "Entrez la nouvelle coordonnée Y (nombre de 1 à " + m_carteActuelle.getHauteur() + ") : ");
            int newY = newYUtilisateur - 1;

            if (!m_carteActuelle.coordonneesValides(newX, newY)) {
                System.out.println("❌ Coordonnées en dehors de la carte. Veuillez réessayer.");
            } else if (newX == caseActuelle.getX() && newY == caseActuelle.getY()) {
                System.out.println("❌ La cible est déjà à cette position. Veuillez choisir une autre case.");
            } else if (!m_carteActuelle.estCaseAccessible(newX, newY)) {
                System.out.println("❌ Déplacement impossible : la case (" + newX + ", " + newY + ") n'est pas accessible. Veuillez choisir une autre case.");
            } else {
                System.out.println("Déplacement de " + cible.getNom() + " vers la case (" + newX + ", " + newY + ")");
                deplacerElementMobile(cible, newX, newY);
                coordonneesValides = true;
            }
        }
    }

    /**
     * Déplace une entité mobile (monstre ou joueur) vers une nouvelle case de la carte.
     * @param cible l'entité à déplacer
     * @param x la nouvelle abscisse
     * @param y la nouvelle ordonnée
     */
    public void deplacerElementMobile(ElementMobile cible, int x, int y) {
        if (m_carteActuelle == null) {
            System.out.println("❌ Aucune carte disponible.");
            return;
        }

        try {
            Case caseActuelle = m_carteActuelle.getCase(cible)
                    .orElseThrow(() -> new IllegalArgumentException("Case introuvable"));
            Case caseDestination = m_carteActuelle.getCase(x, y);

            caseActuelle.retirerContenu(cible);
            caseDestination.ajouterContenu(cible);

            char lettreX = (char) ('A' + x);
            int numeroY = y + 1;
            System.out.println(cible.getNom() + " a été déplacé en " + lettreX + numeroY + ".");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("❌ Déplacement impossible : coordonnées hors de la carte.");
        } catch (IllegalArgumentException e) {
            System.out.println("❌ Déplacement impossible : " + e.getMessage());
        }
    }

    /**
     * Permet d'ajouter un obstacle sur la carte à une position choisie par l'utilisateur.
     */
    public void ajouterObstacle() {
        if (m_carteActuelle == null) {
            System.out.println("❌ Aucune carte disponible.");
            return;
        }

        Scanner scanner = new Scanner(System.in);
        boolean obstacleAjoute = false;

        while (!obstacleAjoute) {
            System.out.print("Entrez la coordonnée X de l'obstacle (lettre de A à " + (char)('A' + m_carteActuelle.getLargeur() - 1) + ") : ");
            String input = scanner.nextLine().trim().toUpperCase();

            if (input.length() != 1) {
                System.out.println("❌ Veuillez entrer une seule lettre. Réessayez.");
                continue;
            }

            char lettreX = input.charAt(0);
            int x = lettreX - 'A';

            int yUtilisateur = demanderInt(scanner, "Entrez la coordonnée Y de l'obstacle (nombre de 1 à " + m_carteActuelle.getHauteur() + ") : ");
            int y = yUtilisateur - 1;

            if (!m_carteActuelle.coordonneesValides(x, y)) {
                System.out.println("❌ Coordonnées invalides. Veuillez réessayer.");
                continue;
            }

            Case caseCible = m_carteActuelle.getCase(x, y);

            if (caseCible.estObstacle()) {
                System.out.println("❌ Il y a déjà un obstacle à cet endroit. Choisissez une autre case.");
                continue;
            }

            if (!caseCible.estVide()) {
                System.out.println("❌ La case est occupée par un personnage, monstre ou équipement. Impossible de poser un obstacle. Choisissez une autre case.");
                continue;
            }

            caseCible.setEstObstacle(true);
            System.out.println("✅ Obstacle ajouté en (" + lettreX + ", " + yUtilisateur + ").");
            obstacleAjoute = true;
        }
    }

    /**
     * Demande à l'utilisateur de saisir le nom d'un monstre.
     * @param scanner le scanner à utiliser pour la saisie
     * @param numero le numéro du monstre (pour l'affichage)
     * @return le nom du monstre saisi
     */
    private String demanderNomMonstre(Scanner scanner, int numero) {
        String nom = "";
        while (nom.trim().isEmpty()) {
            System.out.print("Entrez le nom du monstre #" + numero + " : ");
            nom = scanner.nextLine().trim();
            if (nom.isEmpty()) {
                System.out.println("Le nom ne peut pas être vide. Veuillez réessayer.");
            }
        }
        return nom;
    }

    /**
     * Demande à l'utilisateur de saisir un entier avec un message personnalisé.
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

    /**
     * Affiche un commentaire ou une narration du Maître du Jeu.
     * @param commentaire le texte à afficher
     */
    public void lireCommentaire(String commentaire) {
        System.out.println("📜 Récit du Maître du Jeu :");
        System.out.println(commentaire);
    }

    /*============================Section Overrides============================*/

    @Override
    public String toString() {
        return "MaitreDuJeu{" +
                "nom='" + m_nomMdj + '\'' +
                ", monstres=" + m_monstres.size() +
                ", joueurs=" + m_joueurs.size() +
                ", carteActuelle=" + (m_carteActuelle != null ? "présente" : "absente") +
                '}';
    }
}