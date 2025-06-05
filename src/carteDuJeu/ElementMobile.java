package carteDuJeu;

/**
 * Interface représentant un élément mobile sur la carte du jeu.
 * Un élément mobile peut se déplacer, subir des dégâts et possède des caractéristiques
 * telles que les points de vie, la force, la dextérité, etc.
 * Exemples : personnage, monstre, etc.
 */
public interface ElementMobile extends ElementCarte {
    /**
     * Retourne le nombre maximum de cases que l'élément peut parcourir en un tour.
     * @return le nombre de cases maximum de déplacement
     */
    int getCasesMaxDeplacement();

    /**
     * Retourne le nom de l'élément mobile.
     * @return le nom de l'élément
     */
    String getNom();

    /**
     * Indique si l'élément est un personnage joueur.
     * @return true si c'est un personnage, false sinon
     */
    boolean estPersonnage();

    /**
     * Applique des dégâts à l'élément mobile.
     * @param degats le nombre de points de dégâts à infliger
     */
    void subirDegats(int degats);

    /**
     * Indique si l'élément mobile est mort (points de vie à 0 ou moins).
     * @return true si l'élément est mort, false sinon
     */
    boolean estMort();

    /**
     * Retourne le nombre actuel de points de vie de l'élément mobile.
     * @return les points de vie actuels
     */
    int getPointsDeVie();

    /**
     * Retourne le nombre maximum de points de vie de l'élément mobile.
     * @return les points de vie maximum
     */
    int getPointsDeVieMax();

    /**
     * Retourne la force de l'élément mobile.
     * @return la valeur de force
     */
    int getForce();

    /**
     * Retourne la dextérité de l'élément mobile.
     * @return la valeur de dextérité
     */
    int getDexterite();

    /**
     * Retourne l'initiative de l'élément mobile.
     * @return la valeur d'initiative
     */
    int getInitiative();

    /**
     * Retourne le symbole associé à l'élément mobile pour l'affichage sur la carte.
     * @return le symbole de l'élément
     */
    String getSymbole();

    /**
     * Indique si l'élément est un élément mobile (toujours true ici).
     * @return true
     */
    boolean estElementMobile();
}