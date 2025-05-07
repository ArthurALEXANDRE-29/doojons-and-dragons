import java.util.List;

public class Jeu {

    // Attributs privés
    private List<Donjon> m_donjons;
    private List<Personnage> m_joueurs;
    private MaitreDuJeu m_maitreDuJeu;

    // Constructeur
    public Jeu(List<Donjon> donjons, List<Personnage> joueurs, MaitreDuJeu maitreDuJeu) {
        this.m_donjons = donjons;
        this.m_joueurs = joueurs;
        this.m_maitreDuJeu = maitreDuJeu;
    }

    // Méthode pour démarrer la partie
    public void demarrer() {
        // Logique de démarrage
        System.out.println("La partie commence !");
        m_maitreDuJeu.decrireContexte();
        m_maitreDuJeu.creerMonstres();
        m_maitreDuJeu.positionnerElements();
    }

    // Méthode pour finir la partie
    public void finPartie() {
        // Logique de fin de partie
        System.out.println("La partie est terminée.");
        // Vous pouvez ajouter d'autres actions à la fin de la partie comme afficher des résultats, etc.
    }

    // Getters et setters
    public List<Donjon> getDonjons() {
        return m_donjons;
    }

    public void setDonjons(List<Donjon> donjons) {
        this.m_donjons = donjons;
    }

    public List<Personnage> getJoueurs() {
        return m_joueurs;
    }

    public void setJoueurs(List<Personnage> joueurs) {
        this.m_joueurs = joueurs;
    }

    public MaitreDuJeu getMaitreDuJeu() {
        return m_maitreDuJeu;
    }

    public void setMaitreDuJeu(MaitreDuJeu maitreDuJeu) {
        this.m_maitreDuJeu = maitreDuJeu;
    }
}
