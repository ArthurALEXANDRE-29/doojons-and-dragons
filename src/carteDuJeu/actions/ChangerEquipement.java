package carteDuJeu.actions;

import carteDuJeu.personnages.Personnage;
import java.util.Scanner;

public class ChangerEquipement {

    public void proposerChangement(Personnage personnage) {
        Scanner scanner = new Scanner(System.in);

        // Afficher l'inventaire
        System.out.println("Inventaire :");
        for (int i = 0; i < personnage.getInventaire().size(); i++) {
            System.out.println((i + 1) + ". " + personnage.getInventaire().get(i).getNom());
        }

        // Demander un index à l'utilisateur
        System.out.print("\nEntrez le numéro de l'équipement à équiper : ");
        int index = scanner.nextInt() - 1;

        // Vérifier si l'index est valide
        if (index < 0 || index >= personnage.getInventaire().size()) {
            System.out.println("Index invalide.");
            return;
        }

        // Vérifier si l'équipement est une arme ou une armure
        if (personnage.getInventaire().get(index).estUneArme()) {
            if (personnage.setArmeEquipee(index)) {
                System.out.println("Nouvelle arme équipée : " + personnage.getArmeEquipee().getNom());
            } else {
                System.out.println("Impossible d'équiper cette arme.");
            }
        } else if (personnage.getInventaire().get(index).estUneArmure()) {
            if (personnage.setArmureEquipee(index)) {
                System.out.println("Nouvelle armure équipée : " + personnage.getArmureEquipee().getNom());
            } else {
                System.out.println("Impossible d'équiper cette armure.");
            }
        } else {
            System.out.println("Cet équipement n'est ni une arme ni une armure.");
        }
    }
}