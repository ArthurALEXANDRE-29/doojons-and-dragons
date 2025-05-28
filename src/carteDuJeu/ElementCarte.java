package carteDuJeu;

public interface ElementCarte {
    String getSymbole();  // Pour l'affichage sur la carte
    String getNom();      // Pour l'identification de l'élément
    default boolean estEquipement() {
        return false;
    }
    default boolean estElementMobile() {
        return false; // Par défaut, un élément n'est pas mobile
    }
}