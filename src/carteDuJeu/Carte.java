package carteDuJeu;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Carte {
    private final int m_largeur;
    private final int m_hauteur;
    private Case[][] m_cases;

    public Carte(int largeur, int hauteur) {
        this.m_largeur = largeur;
        this.m_hauteur = hauteur;
        this.m_cases = new Case[hauteur][largeur]; // [ligne][colonne]
        for (int y = 0; y < hauteur; y++) {
            for (int x = 0; x < largeur; x++) {
                m_cases[y][x] = new Case(x, y);
            }
        }
    }

    public int getLargeur() {
        return m_largeur;
    }

    public int getHauteur() {
        return m_hauteur;
    }

    public Case getCase(ElementMobile element) {
        for (int y = 0; y < m_hauteur; y++) {
            for (int x = 0; x < m_largeur; x++) {
                if (m_cases[y][x].contient(element)) {
                    return m_cases[y][x];
                }
            }
        }
        throw new IllegalArgumentException("L'élément mobile n'est pas présent sur la carte.");
    }

    public Case getCase(int x, int y) {
        if (x < 0 || x >= m_largeur || y < 0 || y >= m_hauteur) {
            throw new IndexOutOfBoundsException("Coordonnées en dehors de la carte");
        }
        return m_cases[y][x]; // attention à l'ordre
    }

    public void setCase(int x, int y, Case uneCase) {
        if (x < 0 || x >= m_largeur || y < 0 || y >= m_hauteur) {
            throw new IndexOutOfBoundsException("Coordonnées en dehors de la carte");
        }
        m_cases[y][x] = uneCase; // attention à l'ordre
    }

    public void ajouterContenu(int x, int y, ElementCarte element) {  // Changé de Object à ElementCarte
        if (x < 0 || x >= m_largeur || y < 0 || y >= m_hauteur) {
            throw new IndexOutOfBoundsException("Coordonnées en dehors de la carte");
        }
        m_cases[y][x].ajouterContenu(element);
    }

    public void afficher() {
        // Affiche les coordonnées X
        System.out.print("  ");
        for (int i = 0; i < m_largeur; i++) {
            System.out.printf("%4c", 'A' + i);
        }
        System.out.println();

        // Ligne supérieure du contour
        System.out.print("   ┌");
        for (int x = 0; x < m_largeur; x++) {
            System.out.print("────");
        }
        System.out.println("┐");

        for (int y = 0; y < m_hauteur; y++) {
            // Coordonnée Y
            System.out.printf("%2d │", y+1);

            // Contenu de la ligne
            for (int x = 0; x < m_largeur; x++) {
                System.out.print(m_cases[y][x].toString());
            }

            System.out.println("│");
        }

        // Ligne inférieure du contour
        System.out.print("   └");
        for (int x = 0; x < m_largeur; x++) {
            System.out.print("────");
        }
        System.out.println("┘");
    }


    public void genererObstaclesAleatoires(double tauxObstacle) {
        if (tauxObstacle < 0 || tauxObstacle > 1) {
            throw new IllegalArgumentException("Le taux d'obstacles doit être entre 0.0 et 1.0");
        }

        Random rand = new Random();
        for (int y = 0; y < m_hauteur; y++) {
            for (int x = 0; x < m_largeur; x++) {
                if (rand.nextDouble() < tauxObstacle) {
                    m_cases[y][x].setEstObstacle(true);
                }
            }
        }
    }
}

