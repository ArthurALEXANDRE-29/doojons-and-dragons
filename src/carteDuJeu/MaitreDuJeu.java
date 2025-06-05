package carteDuJeu;

import carteDuJeu.monstres.Monstre;
import carteDuJeu.personnages.Personnage;

import java.util.ArrayList;
import java.util.InputMismatchException;
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
        if (m_carteActuelle != null) Affichage.afficherCarte(m_carteActuelle);
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

        int nombreMonstres = 0;
        while (true) {
            System.out.print("Combien de monstres voulez-vous créer ? ");
            try {
                nombreMonstres = scanner.nextInt();
                scanner.nextLine();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Entrée invalide, veuillez entrer un nombre.");
                scanner.nextLine();
            }
        }

        for (int i = 1; i <= nombreMonstres; i++) {
            System.out.println("\nCréation du monstre #" + i);
            String espece = "";
            while (true) {
                try {
                    System.out.print("Entrez le nom du monstre : ");
                    espece = scanner.nextLine().trim();
                    if (espece.isEmpty()) {
                        throw new IllegalArgumentException("Le nom ne peut pas être vide.");
                    }
                    break; // nom valide, on sort de la boucle
                } catch (IllegalArgumentException e) {
                    System.out.println("Erreur : " + e.getMessage());
                }
            }

            int portee = demanderInt(scanner, "Portée (1 pour mêlée, >1 pour distance) : ");
            int maxDmg = demanderInt(scanner, "Dégâts max par dé : ");
            int nbDes = demanderInt(scanner, "Nombre de dés : ");
            int pvMax = demanderInt(scanner, "Points de vie max : ");
            int caracAttaque = demanderInt(scanner, "Caractéristique d'attaque (force ou dextérité selon portée) : ");
            int classeArmure = demanderInt(scanner, "Classe d'armure : ");
            int initiative = demanderInt(scanner, "Initiative : ");
            int vitesse = demanderInt(scanner, "Vitesse : ");

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

    // Méthode pour infliger des dégâts génériques
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

    public void deplacerCibleParNom() {
        if (m_carteActuelle == null) {
            System.out.println("❌ Aucune carte disponible pour le déplacement.");
            return;
        }

        Scanner scanner = new Scanner(System.in);

        Affichage.afficherEntitesDeplacables( m_joueurs,m_monstres, m_carteActuelle);
        System.out.print("Entrez le nom du monstre ou joueur à déplacer : ");
        String nomCible = scanner.nextLine().trim();

        ElementMobile cible = null;

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
            System.out.println("❌ Aucun monstre ou joueur trouvé avec ce nom.");
            return;
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

        System.out.print("Entrez la nouvelle coordonnée X (lettre de A à " + (char)('A' + m_carteActuelle.getLargeur() - 1) + ") : ");
        char lettreX = scanner.next().toUpperCase().charAt(0);
        int newX = lettreX - 'A';

        int newYUtilisateur = demanderInt(scanner, "Entrez la nouvelle coordonnée Y (nombre de 1 à " + m_carteActuelle.getHauteur() + ") : ");
        int newY = newYUtilisateur - 1;

        if (newX < 0 || newX >= m_carteActuelle.getLargeur() || newY < 0 || newY >= m_carteActuelle.getHauteur()) {
            System.out.println("❌ Coordonnées en dehors de la carte.");
        } else if (newX == caseActuelle.getX() && newY == caseActuelle.getY()) {
            System.out.println("❌ La cible est déjà à cette position.");
        } else if (!m_carteActuelle.estCaseAccessible(newX, newY)) {
            System.out.println("❌ Déplacement impossible : la case (" + newX + ", " + newY + ") n'est pas accessible.");
        }
        else {
            System.out.println("Déplacement de " + cible.getNom() + " vers (" + newX + ", " + newY + ")");
            deplacerElementMobile(cible, newX, newY);
        }
    }

    public void deplacerElementMobile(ElementMobile cible, int x, int y) {
        if (m_carteActuelle == null) {
            System.out.println("❌ Aucune carte disponible.");
            return;
        }

        try {
            // Récupère la case actuelle de la cible
            Case caseActuelle = m_carteActuelle.getCase(cible)
                    .orElseThrow(() -> new IllegalArgumentException("Case introuvable"));
            // Récupère la case de destination
            Case caseDestination = m_carteActuelle.getCase(x, y);

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

        int yUtilisateur = demanderInt(scanner, "Entrez la coordonnée Y de l'obstacle (nombre de 1 à " + m_carteActuelle.getHauteur() + ") : ");
        int y = yUtilisateur - 1;

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
    public void lireCommentaire(String commentaire) {
        System.out.println("📜 Récit du Maître du Jeu :");
        System.out.println(commentaire);
    }

}