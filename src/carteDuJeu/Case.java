package carteDuJeu;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import carteDuJeu.personnages.equipements.Equipement;

/**
 * Représente une case sur la carte du jeu.
 * Une case est définie par ses coordonnées (x, y) et peut contenir des éléments de jeu.
 * Elle peut également être marquée comme obstacle, empêchant le passage des éléments mobiles.
 */
public class Case {
    /** Coordonnée x de la case */
    private int m_x;

    /** Coordonnée y de la case */
    private int m_y;

    /** Indique si la case est un obstacle */
    private boolean m_estObstacle;

    /** Liste des éléments contenus dans cette case */
    private List<ElementCarte> m_contenu;

    /**
     * Constructeur principal pour créer une case avec des coordonnées et un statut d'obstacle spécifiques.
     *
     * @param x la coordonnée x de la case
     * @param y la coordonnée y de la case
     * @param estObstacle true si la case est un obstacle, false sinon
     */
    public Case(int x, int y, boolean estObstacle) {
        this.m_x = x;
        this.m_y = y;
        this.m_estObstacle = estObstacle;
        this.m_contenu = new ArrayList<>();
    }

    /**
     * Constructeur pour créer une case non-obstacle avec des coordonnées spécifiques.
     *
     * @param x la coordonnée x de la case
     * @param y la coordonnée y de la case
     */
    public Case(int x, int y) {
        this(x, y, false);
    }

    /**
     * Retourne la coordonnée x de la case.
     *
     * @return la coordonnée x
     */
    public int getX() {
        return m_x;
    }

    /**
     * Retourne la coordonnée y de la case.
     *
     * @return la coordonnée y
     */
    public int getY() {
        return m_y;
    }

    /**
     * Modifie la position de la case en définissant de nouvelles coordonnées.
     *
     * @param x la nouvelle coordonnée x
     * @param y la nouvelle coordonnée y
     */
    public void setPosition(int x, int y) {
        this.m_x = x;
        this.m_y = y;
    }

    /**
     * Vérifie si la case est un obstacle.
     *
     * @return true si la case est un obstacle, false sinon
     */
    public boolean estObstacle() {
        return m_estObstacle;
    }

    /**
     * Définit le statut d'obstacle de la case.
     *
     * @param estObstacle true pour marquer la case comme obstacle, false sinon
     */
    public void setEstObstacle(boolean estObstacle) {
        this.m_estObstacle = estObstacle;
    }

    /**
     * Ajoute un élément à la case.
     * Si l'élément est null, aucune action n'est effectuée.
     *
     * @param element l'élément à ajouter à la case
     */
    public void ajouterContenu(ElementCarte element) {
        if (element != null) {
            m_contenu.add(element);
        }
    }

    /**
     * Retire un élément de la case.
     *
     * @param element l'élément à retirer
     * @return true si l'élément a été retiré avec succès, false si l'élément n'était pas présent
     */
    public boolean retirerContenu(ElementCarte element) {
        return m_contenu.remove(element);
    }

    /**
     * Vérifie si la case est complètement vide.
     * Une case est considérée comme vide si elle n'est pas un obstacle,
     * ne contient aucun élément et n'a aucun élément mobile.
     *
     * @return true si la case est vide, false sinon
     */
    public boolean estVide() {
        return !m_estObstacle && m_contenu.isEmpty() && getElementsMobiles().isEmpty();
    }

    /**
     * Retourne une copie de la liste des éléments contenus dans la case.
     * Cette méthode retourne toujours une liste (jamais null), même si elle est vide.
     *
     * @return une nouvelle liste contenant tous les éléments de la case
     */
    public List<ElementCarte> getContenu() {
        return new ArrayList<>(m_contenu);
    }

    /**
     * Vérifie si la case contient un élément spécifique.
     *
     * @param element l'élément à rechercher
     * @return true si l'élément est présent dans la case, false sinon
     */
    public boolean contient(ElementCarte element) {
        return m_contenu.contains(element);
    }

    /**
     * Vérifie si la case contient au moins un élément mobile.
     *
     * @return true si la case contient un élément mobile, false sinon
     */
    public boolean contientElementMobile() {
        for (ElementCarte element : m_contenu) {
            if (element.estElementMobile()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Retourne le premier élément mobile trouvé sur la case.
     * Utilise Optional pour éviter les références null.
     *
     * @return un Optional contenant le premier élément mobile trouvé, ou Optional.empty() si aucun élément mobile n'est présent
     */
    public Optional<ElementMobile> getElementMobile() {
        for (ElementCarte element : m_contenu) {
            if (element.estElementMobile()) {
                return Optional.of((ElementMobile) element);
            }
        }
        return Optional.empty();
    }

    /**
     * Retourne tous les éléments mobiles présents sur la case.
     * Cette méthode retourne toujours une liste (jamais null), même si elle est vide.
     *
     * @return une liste contenant tous les éléments mobiles de la case
     */
    public List<ElementMobile> getElementsMobiles() {
        List<ElementMobile> elementsMobiles = new ArrayList<>();
        for (ElementCarte element : m_contenu) {
            if (element.estElementMobile()) {
                elementsMobiles.add((ElementMobile) element);
            }
        }
        return elementsMobiles;
    }

    /**
     * Vérifie si la case est accessible pour un déplacement.
     * Une case est accessible si elle n'est pas un obstacle et ne contient pas d'élément mobile.
     *
     * @return true si la case est accessible, false sinon
     */
    public boolean estAccessible() {
        return !m_estObstacle && !contientElementMobile();
    }

    /*============================Section Overrides============================*/

    /**
     * Retourne une représentation textuelle de la case pour l'affichage.
     * - "[ ] " pour un obstacle
     * - Le symbole du premier élément mobile s'il y en a un
     * - Le symbole du premier élément non mobile s'il n'y a pas d'élément mobile
     * - " .  " pour une case vide
     *
     * @return une chaîne de caractères représentant visuellement la case
     */
    @Override
    public String toString() {
        if (m_estObstacle) {
            return "[ ] ";
        } else if (!m_contenu.isEmpty()) {
            for (ElementCarte element : m_contenu) {
                if (element.estElementMobile()) {
                    return element.getSymbole();
                }
            }
            ElementCarte premierElement = m_contenu.get(0);
            return premierElement.getSymbole(); // Retourne le symbole du premier élément non mobile
        } else {
            return " .  ";  /* Case vide */
        }
    }
}