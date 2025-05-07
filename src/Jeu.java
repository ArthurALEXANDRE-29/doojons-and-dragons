import java.util.List;

public class Jeu {

    // Attributs privés
    private List<Donjon> donjons;
    private List<Personnage> joueurs;
    private MaitreDuJeu maitreDuJeu;

    // Constructeur
    public Jeu(List<Donjon> donjons, List<Personnage> joueurs, MaitreDuJeu maitreDuJeu) {
        this.donjons = donjons;
        this.joueurs = joueurs;
        this.maitreDuJeu = maitreDuJeu;
    }

    // Méthode pour démarrer la partie
    public void demarrer() {
        // Logique de démarrage
        System.out.println("La partie commence !");
        maitreDuJeu.decrireContexte();
        maitreDuJeu.creerMonstres();
        maitreDuJeu.positionnerElements();
    }

    // Méthode pour finir la partie
    public void finPartie() {
        // Logique de fin de partie
        System.out.println("La partie est terminée.");
        // Vous pouvez ajouter d'autres actions à la fin de la partie comme afficher des résultats, etc.
    }

    // Getters et setters
    public List<Donjon> getDonjons() {
        return donjons;
    }

    public void setDonjons(List<Donjon> donjons) {
        this.donjons = donjons;
    }

    public List<Personnage> getJoueurs() {
        return joueurs;
    }

    public void setJoueurs(List<Personnage> joueurs) {
        this.joueurs = joueurs;
    }

    public MaitreDuJeu getMaitreDuJeu() {
        return maitreDuJeu;
    }

    public void setMaitreDuJeu(MaitreDuJeu maitreDuJeu) {
        this.maitreDuJeu = maitreDuJeu;
    }
}
