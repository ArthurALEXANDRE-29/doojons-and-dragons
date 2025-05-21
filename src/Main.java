import carteDuJeu.Carte;
import carteDuJeu.ElementMobile;
import carteDuJeu.actions.Deplacement;
import carteDuJeu.actions.Attaque;
import carteDuJeu.Monstres.Monstre;
import carteDuJeu.personnages.Personnage;
import carteDuJeu.personnages.classes.*;
import carteDuJeu.personnages.races.*;
import carteDuJeu.personnages.sorts.Sort;
import carteDuJeu.personnages.sorts.sortArmeMagique;
import carteDuJeu.personnages.sorts.sortBoogieWoogie;
import carteDuJeu.personnages.sorts.sortGuerison;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Création de la carte
        Carte carte = new Carte(10, 13);1
        Deplacement deplacement = new Deplacement(carte);
        Attaque attaque = new Attaque(deplacement);

        // Création des personnages
        Personnage joueur1 = new Personnage("Arthur", new Humain(), new Guerrier());
        Personnage joueur2 = new Personnage("Alexandru", new Elfe(), new Magicien());
        carte.ajouterContenu(3, 2, joueur1);
        carte.ajouterContenu(4, 3, joueur2);

        // Création et placement du monstre
        Monstre goblin = new Monstre("Gobelin", 1, 1, 6, 12, 1, 10, 12, 12, 10);
        carte.ajouterContenu(5, 5, goblin);

        System.out.println("=== État initial de la carte ===");
        carte.afficher();

        boolean partieEnCours = true;
        while (partieEnCours) {
            for (Personnage joueur : new Personnage[]{joueur1, joueur2}) {
                System.out.println("\n=== Tour de " + joueur.getNom() + " ===");
                System.out.println("1. Se déplacer");
                System.out.println("2. Attaquer");
                if (joueur.getClasse().equals("Magicien")) {
                    System.out.println("3. Lancer un sort");
                }
                System.out.println("4. Passer le tour");
                System.out.print("Choisissez une action : ");
                int choix = scanner.nextInt();

                switch (choix) {
                    case 1 -> {
                        System.out.println("Veuillez entrer une destination pour " + joueur.getNom() + "...");
                        System.out.print("Nbre de case max : " + joueur.getCasesMaxDeplacement());
                        deplacement.gererDeplacement(joueur);
                    }
                    case 2 -> {
                        System.out.println("Choisissez une cible à attaquer.");
                        if (deplacement.estAPortee(joueur, goblin, joueur.getArmeEquipee().getPortee())) {
                            attaque.attaquer(carte, joueur, goblin, carte.getCase(joueur), carte.getCase(goblin));
                            System.out.println("Points de vie du goblin après attaque : " + goblin.getPointsDeVie());
                        } else {
                            System.out.println("Aucune cible à portée.");
                        }
                    }
                    case 3 -> {
                        if (joueur.getClasse().equals("Magicien")) {
                            // Liste des sorts disponibles
                            List<Sort> sortsDisponibles = List.of(
                                    new sortArmeMagique(),
                                    new sortBoogieWoogie(),
                                    new sortGuerison()
                            );

                            // Conversion de la classe en instance de `Classe`
                            Classe classeJoueur = new Magicien(); // Exemple, ajustez selon votre logique

                            // Filtrer les sorts utilisables par la classe du joueur
                            List<Sort> sortsUtilisables = sortsDisponibles.stream()
                                    .filter(sort -> sort.estUtilisablePar(classeJoueur))
                                    .toList();

                            if (sortsUtilisables.isEmpty()) {
                                System.out.println("Aucun sort disponible pour votre classe.");
                            } else {
                                System.out.println("Choisissez un sort à lancer parmi les suivants :");
                                for (int i = 0; i < sortsUtilisables.size(); i++) {
                                    System.out.println((i + 1) + ". " + sortsUtilisables.get(i).getNom());
                                }

                                int choixSort = scanner.nextInt() - 1;
                                if (choixSort >= 0 && choixSort < sortsUtilisables.size()) {
                                    Sort sortChoisi = sortsUtilisables.get(choixSort);
                                    System.out.println("Choisissez une cible pour le sort.");
                                    // Implémentation de la sélection des cibles
                                    ElementMobile[] cibles = {joueur1, goblin}; // ou joueur2, goblin, selon le contexte
                                    if (sortChoisi.lancer(carte, joueur, cibles)) {
                                        System.out.println("Le sort " + sortChoisi.getNom() + " a été lancé avec succès !");
                                    } else {
                                        System.out.println("Le sort a échoué.");
                                    }
                                } else {
                                    System.out.println("Choix invalide.");
                                }
                            }
                        } else {
                            System.out.println("Action non disponible.");
                        }
                    }
                    case 4 -> System.out.println(joueur.getNom() + " passe son tour.");
                    default -> System.out.println("Choix invalide.");
                }

                if (goblin.estMort()) {
                    System.out.println("Le goblin est mort ! Fin de la partie.");
                    partieEnCours = false;
                    break;
                }
            }

            // Tour du monstre
            if (partieEnCours) {
                System.out.println("\n=== Tour du Gobelin ===");
                if (deplacement.estAPortee(goblin, joueur1, goblin.getPortee())) {
                    attaque.attaquer(carte, goblin, joueur1, carte.getCase(goblin), carte.getCase(joueur1));
                    System.out.println("Points de vie de " + joueur1.getNom() + " après attaque : " + joueur1.getPointsDeVie());
                } else if (deplacement.estAPortee(goblin, joueur2, goblin.getPortee())) {
                    attaque.attaquer(carte, goblin, joueur2, carte.getCase(goblin), carte.getCase(joueur2));
                    System.out.println("Points de vie de " + joueur2.getNom() + " après attaque : " + joueur2.getPointsDeVie());
                } else {
                    System.out.println("Le goblin ne peut attaquer personne.");
                }

                if (joueur1.getPointsDeVie() <= 0 && joueur2.getPointsDeVie() <= 0) {
                    System.out.println("Tous les personnages sont morts. Fin de la partie.");
                    partieEnCours = false;
                }

                carte.afficher();
            }
        }

        scanner.close();
    }
}