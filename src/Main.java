import carteDuJeu.Carte;

public class Main {
    public static void main(String[] args) {
        Carte carte = new Carte(20, 15); // largeur x hauteur
        carte.genererObstaclesAleatoires(0.1); // 10% d'obstacles
        carte.afficher(); // affiche la carte dans la console
    }
}