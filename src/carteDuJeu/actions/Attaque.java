package carteDuJeu.actions;

/*Classe représentant l'attaque d'un monstre.
  Chaque monstre possède une attaque unique définie par ses dégâts et sa portée.*/
public class Attaque {
    private String m_degats;  // Format NdM (N dés à M faces, ex: "1d8", "2d6")
    private int m_portee;     // Portée en nombre de cases (1 pour corps à corps)

    //Constructeur de la classe Attaque
    public Attaque(String degats, int portee) {
        this.m_degats = degats;
        this.m_portee = portee;
    }

    //Obtenir la formule de dégâts de l'attaque
    public String getDegats() {
        return m_degats;
    }

    //Obtenir la portée de l'attaque
    public int getPortee() {
        return m_portee;
    }

    //Détermine si l'attaque est au corps à corps
    public boolean estCorpsACorps() {
        return m_portee == 1;
    }

    //Retourne une description de l'attaque
    @Override
    public String toString() {
        String typeAttaque = estCorpsACorps() ? "corps à corps" : "distance";
        return "Attaque à " + typeAttaque + " (dégâts: " + m_degats + ", portée: " + m_portee + ")";
    }
}