package carteDuJeu;

/**
 * Interface représentant un élément pouvant être placé sur la carte du jeu.
 * Un élément de carte peut être un équipement, un élément mobile, etc.
 */
public interface ElementCarte {
    /**
     * Retourne le symbole associé à l'élément pour l'affichage sur la carte.
     * @return le symbole de l'élément
     */
    String getSymbole();

    /**
     * Retourne le nom de l'élément pour son identification.
     * @return le nom de l'élément
     */
    String getNom();

    /**
     * Indique si l'élément est un équipement.
     * @return true si c'est un équipement, false sinon
     */
    boolean estEquipement();

    /**
     * Indique si l'élément est un élément mobile (personnage, monstre, etc.).
     * @return true si c'est un élément mobile, false sinon
     */
    boolean estElementMobile();
}