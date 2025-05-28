package carteDuJeu;

import java.util.ArrayList;
import java.util.List;

public class Case {
    private int m_x;
    private int m_y;
    private boolean m_estObstacle;
    private List<ElementCarte> m_contenu;

    public Case(int x, int y, boolean estObstacle) {
        this.m_x = x;
        this.m_y = y;
        this.m_estObstacle = estObstacle;
        this.m_contenu = new ArrayList<>();
    }

    public Case(int x, int y) {
        this(x, y, false);
    }

    public int getX() {
        return m_x;
    }

    public int getY() {
        return m_y;
    }

    public void setPosition(int x, int y) {
        this.m_x = x;
        this.m_y = y;
    }

    public boolean estObstacle() {
        return m_estObstacle;
    }

    public void setEstObstacle(boolean estObstacle) {
        this.m_estObstacle = estObstacle;
    }

    public void ajouterContenu(ElementCarte element) {
        if (element != null) {
            m_contenu.add(element);
        }
    }

    public boolean retirerContenu(ElementCarte element) {
        return m_contenu.remove(element);
    }

    public boolean estVide() {
        return m_contenu.isEmpty();
    }

    public List<ElementCarte> getContenu() {
        return new ArrayList<>(m_contenu);
    }

    public boolean contient(ElementCarte element) {
        return m_contenu.contains(element);
    }

    /**
     * Vérifie si la case contient un élément mobile
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
     * Retourne le premier élément mobile trouvé sur la case
     */
    public ElementMobile getElementMobile() {
        for (ElementCarte element : m_contenu) {
            if (element.estElementMobile()) {
                return (ElementMobile) element;
            }
        }
        return null;
    }

    /**
     * Retourne tous les éléments mobiles sur la case
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
     * Vérifie si la case est accessible pour un déplacement
     * (pas obstacle et pas d'élément mobile)
     */
    public boolean estAccessible() {
        return !m_estObstacle && !contientElementMobile();
    }

    @Override
    public String toString() {
        if (m_estObstacle) {
            return "[  ]";
        } else if (!m_contenu.isEmpty()) {
            for (ElementCarte element : m_contenu) {
                if (element.estElementMobile()) {
                    return element.getSymbole();
                }
            }
            return m_contenu.get(0).getSymbole();
        } else {
            return " .  ";  /* Case vide */
        }
    }
}