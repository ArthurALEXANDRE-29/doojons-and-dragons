package carteDuJeu.personnages.sorts;

import carteDuJeu.Carte;
import carteDuJeu.ElementMobile;
import carteDuJeu.personnages.Personnage;
import carteDuJeu.personnages.equipements.armes.Arme;

import java.util.List;

public class sortArmeMagique extends Sort {
    public sortArmeMagique() {
        super("Arme magique", "Le personnage détenteur du pouvoir peut choisir une arme détenue par un personnage (mais pas forcément équipée) à améliorer. " +
                "L'arme gagne alors un bonus de 1 lors des jets d'attaque et de 1 lors des jets de dégâts " +
                "(les bonus peuvent se cumuler).");
    }

    @Override
    public boolean lancer(Carte carte, Personnage lanceur, ElementMobile[] cibles) {
        if (cibles.length == 0) {
            return false;
        }
        for (ElementMobile cible : cibles) {
            if (cible.estPersonnage()) {
                Personnage personnageCible = (Personnage) cible;

                // Récupération des armes disponibles
                List<Arme> armesDisponibles = personnageCible.getInventaire().stream()
                        .filter(e -> e.estUneArme())
                        .map(e -> (Arme) e)
                        .toList();

                if (!armesDisponibles.isEmpty()) {
                    System.out.println("Choisissez une arme à améliorer parmi les suivantes :");
                    for (int i = 0; i < armesDisponibles.size(); i++) {
                        System.out.println((i + 1) + ". " + armesDisponibles.get(i).getNom());
                    }


                    int choix = 0;
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
}