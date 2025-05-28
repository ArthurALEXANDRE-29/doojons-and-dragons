package carteDuJeu;

import carteDuJeu.Monstres.Monstre;
import carteDuJeu.personnages.Personnage;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MaitreDuJeu {
    private List<Monstre> m_monstres;
    private List<Personnage> m_joueurs;
    private final String m_nomMdj = "Maitre du Jeu";
    private Carte m_carteActuelle; // Carte du donjon actuel

    public MaitreDuJeu(List<Personnage> joueurs) {
        this.m_monstres = new ArrayList<>();
        this.m_joueurs = new ArrayList<>(joueurs); // Copier la liste des joueurs
        this.m_carteActuelle = null;
    }

    public void setCarte(Carte carte) {
        this.m_carteActuelle = carte;
        System.out.println("Le Maître du Jeu prend contrôle d'une nouvelle carte (" +
                carte.getLargeur() + "x" + carte.getHauteur() + ")");
        if (m_carteActuelle != null) m_carteActuelle.afficher();
    }

    public Carte getCarte() {
        return m_carteActuelle;
    }

    public void decrireContexte() {
        System.out.println("Bienvenue dans le donjon mystérieux !");
        System.out.println("Vous incarnez des aventuriers courageux, prêts à affronter des monstres redoutables.");
        System.out.println("Tuez tout les monstres qui se dressent sur votre chemin");
        System.out.println("Bonne chance à vous !");
    }

    public void phaseCreationDesMonstres() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Combien de monstres voulez-vous créer ? ");
        int nombreMonstres = scanner.nextInt();
        scanner.nextLine(); // consommer le retour à la ligne

        for (int i = 1; i <= nombreMonstres; i++) {
            System.out.println("\nCréation du monstre #" + i);

            System.out.print("Espèce : ");
            String espece = scanner.nextLine();

            System.out.print("Portée (1 pour mêlée, >1 pour distance) : ");
            int portee = scanner.nextInt();

            System.out.print("Dégâts max par dé : ");
            int maxDmg = scanner.nextInt();

            System.out.print("Nombre de dés : ");
            int nbDes = scanner.nextInt();

            System.out.print("Points de vie max : ");
            int pvMax = scanner.nextInt();

            System.out.print("Caractéristique d'attaque (force ou dextérité selon portée) : ");
            int caracAttaque = scanner.nextInt();

            System.out.print("Classe d'armure : ");
            int classeArmure = scanner.nextInt();

            System.out.print("Initiative : ");
            int initiative = scanner.nextInt();

            System.out.print("Vitesse : ");
            int vitesse = scanner.nextInt();
            scanner.nextLine(); // pour sauter à la ligne suivante

            Monstre monstre = new Monstre(
                    espece, i, portee, maxDmg, vitesse, nbDes,
                    pvMax, caracAttaque, classeArmure, initiative
            );

            m_monstres.add(monstre);
        }
    }

    public List<Monstre> getMonstres() {
        return m_monstres;
    }

    public void faireDmg(List<Personnage> joueurs) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Voulez-vous utiliser la foudre divine ? (y/n) ");
        String rep = scanner.nextLine().trim().toLowerCase();

        if (!rep.equals("y")) return;

        // Affichage des cibles disponibles
        System.out.println("\n--- Cibles disponibles ---");
        for (Monstre m : m_monstres) {
            System.out.println("[Monstre] " + m.getNom() + " (PV: " + m.getPointsDeVie() + "/" + m.getPointsDeVieMax() + ")");
        }
        for (Personnage j : joueurs) {
            System.out.println("[Joueur] " + j.getNom() + " (PV: " + j.getPointsDeVie() + "/" + j.getPointsDeVieMax() + ")");
        }

        System.out.print("\nTapez le nom exact de la cible : ");
        String nomCible = scanner.nextLine().trim();

        // Chercher d'abord parmi les monstres
        for (Monstre m : m_monstres) {
            if (m.getNom().equalsIgnoreCase(nomCible)) {
                System.out.println("Donnez les dégats à infliger à " + m.getNom() + " :");
                int dmg = scanner.nextInt();
                infligerDegats(m, dmg);
                return;
            }
        }

        // Puis chercher parmi les joueurs
        for (Personnage j : joueurs) {
            if (j.getNom().equalsIgnoreCase(nomCible)) {
                System.out.println("Donnez les dégats à infliger à " + j.getNom() + " :");
                int dmg = scanner.nextInt();
                infligerDegats(j, dmg);
                return;
            }
        }

        System.out.println("❌ Aucun monstre ou joueur trouvé avec ce nom.");
    }

    // Méthode pour infliger des dégâts génériques
    private void infligerDegats(ElementMobile cible, int degats) {
        cible.subirDegats(degats);
        System.out.println("⚡ " + cible.getNom() + " a été frappé par la foudre divine et subit " + degats + " dégâts !");

        if (cible.estMort()) {
            System.out.println("💀 " + cible.getNom() + " est mort !");
            if (m_carteActuelle != null && m_carteActuelle.contientElement(cible)) {
                Case caseCible = m_carteActuelle.getCase(cible);
                if (caseCible != null) {
                    caseCible.retirerContenu(cible);
                }
            }
        }
    }

    public void deplacerCibleParNom() {
        if (m_carteActuelle == null) {
            System.out.println("❌ Aucune carte disponible pour le déplacement.");
            return;
        }

        Scanner scanner = new Scanner(System.in);

        System.out.print("Entrez le nom du monstre ou joueur à déplacer : ");
        String nomCible = scanner.nextLine().trim();

        ElementMobile cible = null;

        // Chercher dans les monstres
        for (Monstre m : m_monstres) {
            if (m.getNom().equalsIgnoreCase(nomCible)) {
                cible = m;
                break;
            }
        }

        // Si pas trouvé dans les monstres, chercher dans les joueurs
        if (cible == null) {
            for (Personnage j : m_joueurs) {
                if (j.getNom().equalsIgnoreCase(nomCible)) {
                    cible = j;
                    break;
                }
            }
        }

        if (cible == null) {
            System.out.println("❌ Aucun monstre ou joueur trouvé avec ce nom.");
            return;
        }

        // Afficher la position actuelle de la cible
        Case caseActuelle;
        try {
            caseActuelle = m_carteActuelle.getCase(cible);
            System.out.println(cible.getNom() + " est actuellement en (" + caseActuelle.getX() + ", " + caseActuelle.getY() + ")");
        } catch (IllegalArgumentException e) {
            System.out.println("❌ Impossible de trouver la case actuelle de la cible.");
            return;
        }

        // Demander les nouvelles coordonnées
        System.out.print("Entrez la nouvelle coordonnée X (lettre de A à " + (char)('A' + m_carteActuelle.getLargeur() - 1) + ") : ");
        char lettreX = scanner.next().toUpperCase().charAt(0);
        int newX = lettreX - 'A';  // Convertit la lettre en indice (A->0, B->1, etc.)

        System.out.print("Entrez la nouvelle coordonnée Y (nombre de 1 à " + m_carteActuelle.getHauteur() + ") : ");
        int newYUtilisateur = scanner.nextInt();
        int newY = newYUtilisateur - 1;  // Convertit l'entrée utilisateur en indice 0-based

        // Vérifier que newX et newY sont valides avant d'appeler le déplacement
        if (newX < 0 || newX >= m_carteActuelle.getLargeur() || newY < 0 || newY >= m_carteActuelle.getHauteur()) {
            System.out.println("❌ Coordonnées invalides.");
        } else {
            deplacerElementMobile(cible, newX, newY);
        }
    }

    public void deplacerElementMobile(ElementMobile cible, int x, int y) {
        if (m_carteActuelle == null) {
            System.out.println("❌ Aucune carte disponible.");
            return;
        }

        try {
            Case caseDestination = m_carteActuelle.getCase(x, y);
            Case caseActuelle = m_carteActuelle.getCase(cible);

            caseActuelle.retirerContenu(cible);
            caseDestination.ajouterContenu(cible);

            System.out.println(cible.getNom() + " déplacé en (" + x + ", " + y + ").");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("❌ Déplacement impossible : coordonnées (" + x + "," + y + ") hors de la carte.");
        } catch (IllegalArgumentException e) {
            System.out.println("❌ Déplacement impossible : " + e.getMessage());
        }
    }

    public void ajouterObstacle() {
        if (m_carteActuelle == null) {
            System.out.println("❌ Aucune carte disponible.");
            return;
        }

        Scanner scanner = new Scanner(System.in);

        System.out.print("Entrez la coordonnée X de l'obstacle (lettre de A à " + (char)('A' + m_carteActuelle.getLargeur() - 1) + ") : ");
        char lettreX = scanner.next().toUpperCase().charAt(0);
        int x = lettreX - 'A';

        System.out.print("Entrez la coordonnée Y de l'obstacle (nombre de 1 à " + m_carteActuelle.getHauteur() + ") : ");
        int yUtilisateur = scanner.nextInt();
        int y = yUtilisateur - 1;

        // Vérification des coordonnées
        if (x < 0 || x >= m_carteActuelle.getLargeur() || y < 0 || y >= m_carteActuelle.getHauteur()) {
            System.out.println("❌ Coordonnées invalides.");
            return;
        }

        Case caseCible = m_carteActuelle.getCase(x, y);

        if (caseCible.estObstacle()) {
            System.out.println("❌ Il y a déjà un obstacle à cet endroit.");
            return;
        }

        if (!caseCible.estVide()) {
            System.out.println("❌ La case est occupée par un élément mobile, impossible de poser un obstacle.");
            return;
        }

        caseCible.setEstObstacle(true);
        System.out.println("✅ Obstacle ajouté en (" + lettreX + ", " + yUtilisateur + ").");
    }
}