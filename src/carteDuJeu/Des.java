package carteDuJeu;

import java.util.Random;

/**
 * Classe utilitaire pour simuler des lancés de dés dans le jeu.
 * Supporte la notation classique des jeux de rôle (ex: "2d6", "1d20") ainsi que
 * des méthodes directes pour lancer un ou plusieurs dés.
 *
 * Tous les résultats sont générés de manière pseudo-aléatoire en utilisant
 * une instance partagée de Random. Les valeurs retournées sont comprises
 * entre 1 et le nombre de faces du dé (inclus).
 */
public class Des {
    /** Générateur de nombres aléatoires partagé pour tous les lancés de dés */
    private static final Random random = new Random();

    /**
     * Constructeur privé pour empêcher l'instanciation de cette classe utilitaire.
     */
    private Des() {
        throw new UnsupportedOperationException("Des est une classe utilitaire et ne doit pas être instanciée");
    }

    /**
     * Lance des dés en utilisant la notation classique des jeux de rôle.
     * Interprète une chaîne au format "XdY" où X est le nombre de dés et Y le nombre de faces.
     *
     * Exemples de notations supportées :
     * - "1d6" : lance 1 dé à 6 faces
     * - "2d10" : lance 2 dés à 10 faces
     * - "3D8" : lance 3 dés à 8 faces (insensible à la casse)
     *
     * @param notation la notation du lancer au format "XdY" (ne doit pas être null ou vide)
     * @return la somme des résultats de tous les dés lancés
     * @throws IllegalArgumentException si la notation est null, vide, ou mal formatée
     * @throws NumberFormatException si les nombres dans la notation ne sont pas valides
     */
    public static int lancer(String notation) {
        if (notation == null || notation.trim().isEmpty()) {
            throw new IllegalArgumentException("La notation ne peut pas être null ou vide");
        }

        String notationNormalisee = notation.toLowerCase().trim();

        if (!notationNormalisee.contains("d")) {
            throw new IllegalArgumentException("Format de notation invalide. Utilisez le format 'XdY' (ex: '2d6')");
        }

        String[] parties = notationNormalisee.split("d");

        if (parties.length != 2) {
            throw new IllegalArgumentException("Format de notation invalide. Utilisez le format 'XdY' (ex: '2d6')");
        }

        try {
            int nbDes = Integer.parseInt(parties[0]);
            int nbFaces = Integer.parseInt(parties[1]);
            return lancer(nbDes, nbFaces);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Les nombres dans la notation '" + notation + "' ne sont pas valides", e);
        }
    }

    /**
     * Lance un seul dé avec un nombre spécifié de faces.
     *
     * @param nbFaces le nombre de faces du dé (doit être supérieur à 0)
     * @return un nombre aléatoire entre 1 et nbFaces (inclus)
     * @throws IllegalArgumentException si le nombre de faces est inférieur ou égal à 0
     */
    public static int lancer(int nbFaces) {
        if (nbFaces <= 0) {
            throw new IllegalArgumentException("Le nombre de faces doit être supérieur à 0, reçu: " + nbFaces);
        }
        return random.nextInt(nbFaces) + 1;
    }

    /**
     * Lance plusieurs dés identiques et retourne la somme des résultats.
     *
     * @param nbDes le nombre de dés à lancer (doit être supérieur à 0)
     * @param nbFaces le nombre de faces de chaque dé (doit être supérieur à 0)
     * @return la somme des résultats de tous les dés lancés
     * @throws IllegalArgumentException si le nombre de dés ou de faces est inférieur ou égal à 0
     */
    public static int lancer(int nbDes, int nbFaces) {
        if (nbDes <= 0) {
            throw new IllegalArgumentException("Le nombre de dés doit être supérieur à 0, reçu: " + nbDes);
        }
        if (nbFaces <= 0) {
            throw new IllegalArgumentException("Le nombre de faces doit être supérieur à 0, reçu: " + nbFaces);
        }

        int total = 0;
        for (int i = 0; i < nbDes; i++) {
            total += lancer(nbFaces);
        }
        return total;
    }
}