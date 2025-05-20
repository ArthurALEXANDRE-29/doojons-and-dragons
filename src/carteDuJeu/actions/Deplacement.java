package carteDuJeu.actions;

import carteDuJeu.Carte;
import carteDuJeu.Case;
import carteDuJeu.ElementMobile;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Deplacement {
    private final Carte m_carte;
    private final Scanner m_scanner;

    public Deplacement(Carte carte) {
        this.m_carte = carte;
        this.m_scanner = new Scanner(System.in);
    }

    public int[] trouverPosition(ElementMobile element) {
        for (int y = 0; y < m_carte.getHauteur(); y++) {
            for (int x = 0; x < m_carte.getLargeur(); x++) {
                if (m_carte.getCase(x, y).contient(element)) {
                    return new int[]{x, y};
                }
            }
        }
        return null;
    }

    public boolean gererDeplacement(ElementMobile element) {
        int[] position = trouverPosition(element);
        if (position == null) return false;

        int xActuel = position[0];
        int yActuel = position[1];
        int casesMax = element.getCasesMaxDeplacement();

        while (true) {
            System.out.print("Entrez la destination (ex: A5 ou quitter pour annuler) : ");
            String destination = m_scanner.nextLine().trim().toUpperCase();

            if (destination.equalsIgnoreCase("quitter")) {
                return false;
            }

            try {
                int[] coordonnees = parseCoordonnees(destination);
                int xCible = coordonnees[0];
                int yCible = coordonnees[1];
                int distance = Math.abs(xCible - xActuel) + Math.abs(yCible - yActuel);

                if (distance > casesMax) continue;
                if (!estDeplacementValide(xCible, yCible)) continue;

                return deplacer(xActuel, yActuel, xCible, yCible, element, casesMax);
            } catch (IllegalArgumentException e) {
                continue;
            }
        }
    }

    public int[] parseCoordonnees(String coordString) {
        if (coordString == null || coordString.length() < 2) {
            throw new IllegalArgumentException("Format de coordonnées incorrect.");
        }

        char colChar = coordString.charAt(0);
        if (colChar < 'A' || colChar > 'Z') {
            throw new IllegalArgumentException("La colonne doit être une lettre entre A et Z.");
        }

        int x = colChar - 'A';
        int y = Integer.parseInt(coordString.substring(1)) - 1;

        if (x < 0 || x >= m_carte.getLargeur() || y < 0 || y >= m_carte.getHauteur()) {
            throw new IllegalArgumentException("Coordonnées hors limites de la carte.");
        }

        return new int[]{x, y};
    }

    public String coordonneesToString(int x, int y) {
        return (char) ('A' + x) + String.valueOf(y + 1);
    }

    public boolean estDeplacementValide(int xCible, int yCible) {
        if (xCible < 0 || xCible >= m_carte.getLargeur() || yCible < 0 || yCible >= m_carte.getHauteur()) {
            return false;
        }

        Case caseCible = m_carte.getCase(xCible, yCible);
        if (caseCible.estObstacle()) return false;

        for (var e : caseCible.getContenu()) {
            if (e instanceof ElementMobile) return false;
        }

        return true;
    }

    public boolean deplacer(int xDepart, int yDepart, int xCible, int yCible, ElementMobile element, int casesMax) {
        Case caseDepart = m_carte.getCase(xDepart, yDepart);
        if (!caseDepart.contient(element)) return false;
        if (!estDeplacementValide(xCible, yCible)) return false;

        int distance = Math.abs(xCible - xDepart) + Math.abs(yCible - yDepart);
        if (distance > casesMax) return false;

        caseDepart.retirerContenu(element);
        m_carte.getCase(xCible, yCible).ajouterContenu(element);
        return true;
    }

    // Nouvelle méthode pour vérifier la portée entre deux positions
    public boolean estAPortee(int x1, int y1, int x2, int y2, int portee) {
        int distance = Math.max(Math.abs(x2 - x1), Math.abs(y2 - y1)); // Chebyshev
        return distance <= portee;
    }

    // Méthode surchargée pour les éléments mobiles
    public boolean estAPortee(ElementMobile e1, ElementMobile e2, int portee) {
        int[] pos1 = trouverPosition(e1);
        int[] pos2 = trouverPosition(e2);
        if (pos1 == null || pos2 == null) return false;
        return estAPortee(pos1[0], pos1[1], pos2[0], pos2[1], portee);
    }
}
