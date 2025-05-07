package carteDuJeu.Monstres;

/*Classe représentant l'attaque d'un monstre.
  Chaque monstre possède une attaque unique définie par ses dégâts et sa portée.*/
public class Attaque {
    private String degats;  // Format NdM (N dés à M faces, ex: "1d8", "2d6")
    private int portee;     // Portée en nombre de cases (1 pour corps à corps)

    //Constructeur de la classe Attaque
    public Attaque(String degats, int portee) {
        this.degats = degats;
        this.portee = portee;
    }

    //Obtenir la formule de dégâts de l'attaque
    public String getDegats() {
        return degats;
    }

    //Obtenir la portée de l'attaque
    public int getPortee() {
        return portee;
    }

    //Détermine si l'attaque est au corps à corps
    public boolean estCorpsACorps() {
        return portee == 1;
    }

    //Retourne une description de l'attaque
    @Override
    public String toString() {
        String typeAttaque = estCorpsACorps() ? "corps à corps" : "distance";
        return "Attaque à " + typeAttaque + " (dégâts: " + degats + ", portée: " + portee + ")";
    }
}