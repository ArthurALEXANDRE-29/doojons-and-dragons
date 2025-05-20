package carteDuJeu.personnages.sorts;

import carteDuJeu.Carte;
import carteDuJeu.ElementMobile;
import carteDuJeu.personnages.Personnage;
import carteDuJeu.personnages.equipements.armes.Arme;

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

                // Vérifie si le personnage possède une arme dans son inventaire
                if (personnageCible.getInventaire().contains()) {
                    // Récupère l'arme et applique les bonus
                    Arme arme = personnageCible.getInventaire().getArme();
                    arme.ajouterBonusAttaque(1);
                    arme.ajouterBonusDegats(1);

                    System.out.println("L'arme de " + personnageCible.getNom() + " a été améliorée !");
                } else {
                    System.out.println(personnageCible.getNom() + " ne possède pas d'arme à améliorer.");
                }
            }
        }
        return true;
    }
}