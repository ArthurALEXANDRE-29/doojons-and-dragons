java
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import carteDuJeu.*;
import carteDuJeu.personnages.*;
import carteDuJeu.Monstres.*;
import carteDuJeu.personnages.classes.Classe;
import carteDuJeu.personnages.races.Race;

public class Jeu {
    private List<Donjon> m_donjons;
    private List<Personnage> m_joueurs;
    private MaitreDuJeu m_maitreDuJeu;

    public Jeu() {
        Scanner scanner = new Scanner(System.in);

        // Création des donjons
        m_donjons = new ArrayList<>();
        m_donjons.add(new Donjon("Donjon du Début"));

        // Demander le nombre de joueurs
        System.out.print("Combien de joueurs voulez-vous créer ? ");
        int nbJoueurs = scanner.nextInt();
        scanner.nextLine(); // Consommer le retour à la ligne

        m_joueurs = new ArrayList<>();
        for (int i = 1; i <= nbJoueurs; i++) {
            System.out.print("Nom du joueur #" + i + " : ");
            String nom = scanner.nextLine();

            System.out.print("Race du joueur #" + i + " : ");
            Race race = scanner.nextLine();

            System.out.print("Classe du joueur #" + i + " : ");
            String classe = scanner.nextLine();

            m_joueurs.add(new Personnage(nom, race, classe));
        }

        // Création de la carte et du maître du jeu
        m_maitreDuJeu = new MaitreDuJeu(m_joueurs, m_donjons);
    }

    public void demarrer() {
        System.out.println("La partie commence !");
        m_maitreDuJeu.decrireContexte();
        m_maitreDuJeu.phaseCreationDesMonstres();
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
