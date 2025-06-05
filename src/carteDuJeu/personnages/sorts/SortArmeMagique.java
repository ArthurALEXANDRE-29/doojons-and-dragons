package carteDuJeu.personnages.sorts;

import carteDuJeu.Carte;
import carteDuJeu.ElementMobile;
import carteDuJeu.personnages.Personnage;
import carteDuJeu.personnages.classes.Classe;
import carteDuJeu.personnages.equipements.armes.Arme;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Sort "Arme magique" permettant d'améliorer une arme détenue par un personnage.
 * Le lanceur choisit une arme (équipée ou dans l'inventaire) d'un personnage :
 * l'arme gagne un bonus de +1 aux jets d'attaque et de +1 aux jets de dégâts (cumulable).
 * Ce sort n'est utilisable que par la classe Magicien.
 */
public class SortArmeMagique extends Sort {
    /**
     * Construit le sort Arme magique avec son nom et sa description.
     */
    public SortArmeMagique() {
        super("Arme magique", "Le personnage détenteur du pouvoir peut choisir une arme détenue par un personnage (mais pas forcément équipée) à améliorer. " +
                "L'arme gagne alors un bonus de 1 lors des jets d'attaque et de 1 lors des jets de dégâts " +
                "(les bonus peuvent se cumuler).");
    }

    /**
     * Lance le sort Arme magique sur les cibles spécifiées.
     * Pour chaque personnage ciblé, le lanceur choisit une arme à améliorer :
     * l'arme reçoit un bonus de +1 à l'attaque et +1 aux dégâts.
     * @param carte la carte du jeu (non utilisée ici)
     * @param lanceur le personnage lançant le sort
     * @param cibles les éléments mobiles ciblés par le sort
     * @return true si au moins une arme a été améliorée, false sinon
     */
    @Override
    public boolean lancer(Carte carte, Personnage lanceur, ElementMobile[] cibles) {
        if (cibles.length == 0) {
            return false;
        }
        for (ElementMobile cible : cibles) {
            if (cible.estPersonnage()) {
                Personnage personnageCible = (Personnage) cible;

                // Initialisation de la liste des armes disponibles
                List<Arme> armesDisponibles = new ArrayList<>();

                // Ajout de l'arme équipée si elle existe
                if (personnageCible.getArmeEquipee() != null) {
                    armesDisponibles.add(personnageCible.getArmeEquipee());
                }

                // Ajout des armes de l'inventaire
                armesDisponibles.addAll(personnageCible.getInventaire().stream()
                        .filter(e -> e.estUneArme())
                        .map(e -> (Arme) e)
                        .toList());

                if (!armesDisponibles.isEmpty()) {
                    System.out.println("Choisissez une arme à améliorer parmi les suivantes :");
                    for (int i = 0; i < armesDisponibles.size(); i++) {
                        System.out.println((i + 1) + ". " + armesDisponibles.get(i).getNom());
                    }

                    int choix = -1;
                    while (true) {
                        try {
                            Scanner scanner = new Scanner(System.in);
                            System.out.print("Entrez le numéro de l'arme à améliorer : ");
                            choix = scanner.nextInt() - 1;
                            scanner.nextLine();

                            if (choix >= 0 && choix < armesDisponibles.size()) {
                                break;
                            } else {
                                System.out.println("Choix invalide, veuillez réessayer.");
                            }
                        } catch (Exception e) {
                            System.out.println("Entrée invalide, veuillez entrer un nombre.");
                        }
                    }
                    Arme armeChoisie = armesDisponibles.get(choix);

                    armeChoisie.ajouterBonusAttaque(1);
                    armeChoisie.ajouterBonusDegats(1);

                    System.out.println("L'arme " + armeChoisie.getNom() + " de " + personnageCible.getNom() + " a été améliorée !");
                } else {
                    System.out.println(personnageCible.getNom() + " ne possède pas d'arme à améliorer.");
                }
            }
        }
        return true;
    }

    /**
     * Indique si le sort peut être utilisé par la classe spécifiée.
     * @param classe la classe du personnage
     * @return true si la classe est Magicien, false sinon
     */
    @Override
    public boolean estUtilisablePar(Classe classe) {
        return classe.getNomClasse().equals("Magicien");
    }
}