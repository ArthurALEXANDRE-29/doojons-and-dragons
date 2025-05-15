package carteDuJeu;

import java.util.ArrayList;
import java.util.List;

public class Case {
    private final int m_x;
    private final int m_y;
    private boolean m_estObstacle;
    private List<ElementCarte> m_contenu;  // Changé de Object à ElementCarte

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

    public boolean estObstacle() {
        return m_estObstacle;
    }

    public void setEstObstacle(boolean estObstacle) {
        this.m_estObstacle = estObstacle;
    }

    public void ajouterContenu(ElementCarte element) {  // Changé de Object à ElementCarte
        if (element != null) {
            m_contenu.add(element);
        }
    }

    public boolean retirerContenu(ElementCarte element) {  // Changé de Object à ElementCarte
        return m_contenu.remove(element);
    }

    public boolean estVide() {
        return m_contenu.isEmpty();
    }

    public List<ElementCarte> getContenu() {  // Changé le type de retour
        return new ArrayList<>(m_contenu);
    }

    public boolean contient(ElementCarte element) {  // Changé de Object à ElementCarte
        return m_contenu.contains(element);
    }

    @Override
    public String toString() {
        if (m_estObstacle) {
            return "[  ]";
        } else if (!m_contenu.isEmpty()) {
            // Utilise le symbole du premier élément
            return m_contenu.get(0).getSymbole();
        } else {
            return " .  ";  /* Case vide */
        }
    }
}
