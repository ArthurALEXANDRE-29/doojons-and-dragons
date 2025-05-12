package carteDuJeu;

import java.util.Random;

public class Des {
    private static final Random random = new Random();

    /* Interprète une notation classique : "2d6", "1d20", etc. */
    public static int lancer(String notation) {
        String[] parties = notation.toLowerCase().split("d");
        int nbDes = Integer.parseInt(parties[0]);
        int nbFaces = Integer.parseInt(parties[1]);
        return lancer(nbDes, nbFaces);
    }

    /* Lance un dé à n faces */
    public static int lancer(int nbFaces) {
        return random.nextInt(nbFaces) + 1;
    }

    /* Lance n dés à x faces (ex: 3d4 => nbDes=3, nbFaces=4) */
    public static int lancer(int nbDes, int nbFaces) {
        int total = 0;
        for (int i = 0; i < nbDes; i++) {
            total += lancer(nbFaces);
        }
        return total;
    }
}
