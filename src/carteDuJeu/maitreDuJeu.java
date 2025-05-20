package carteDuJeu;

import carteDuJeu.Monstres.Monstre;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
public class maitreDuJeu {
    private List<Monstre> monstres;
    private Carte carte;

    public maitreDuJeu(Carte carte) {
        this.carte = carte;
        this.monstres = new ArrayList<>();
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

            System.out.print("Classe d’armure : ");
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

            monstres.add(monstre);
        }
    }

    public void placerMonstresAleatoirement() {
        Random rand = new Random();

        for (Monstre monstre : monstres) {
            boolean place = false;

            while (!place) {
                int x = rand.nextInt(carte.getLargeur());
                int y = rand.nextInt(carte.getHauteur());
                Case uneCase = carte.getCase(x, y);

                if (!uneCase.estObstacle() && uneCase.estVide()) {
                    uneCase.ajouterContenu(monstre);
                    place = true;
                }
            }
        }
    }

    public List<Monstre> getMonstres() {
        return monstres;
    }

}
