package carteDuJeu;

import carteDuJeu.actions.*;
import carteDuJeu.personnages.Personnage;
import carteDuJeu.personnages.equipements.Equipement;
import carteDuJeu.Monstres.Monstre;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Tours {
    private Donjon m_donjon;
    private Scanner m_scanner;
    private ChangerEquipement m_gestionEquipement;
    private Deplacement m_deplacement;
    private carteDuJeu.actions.Attaque m_attaque;
    private int m_indexTourActuel;
    private int m_numeroTour;

    public Tours(Donjon donjon) {
        this.m_donjon = donjon;
        this.m_scanner = new Scanner(System.in);
        this.m_gestionEquipement = new ChangerEquipement();
        this.m_deplacement = new Deplacement(donjon.getCarte());
        this.m_attaque = new carteDuJeu.actions.Attaque(m_deplacement);
        this.m_indexTourActuel = 0;
        this.m_numeroTour = 1;
    }

    /**
     * Lance la boucle principale des tours de jeu
     */
    public void commencerTours() {
        System.out.println("\n=== DÉBUT DES TOURS DE JEU ===\n");

        while (!estFinDePartie()) {
            System.out.println("--- TOUR " + m_numeroTour + " ---");

            // Jouer le tour de chaque entité
            for (m_indexTourActuel = 0; m_indexTourActuel < m_donjon.getEntiteTour().size(); m_indexTourActuel++) {
                ElementMobile entiteActuelle = m_donjon.getEntiteTour().get(m_indexTourActuel);

                // Vérifier si l'entité est encore vivante
                if (entiteActuelle.estMort()) {
                    continue;
                }

                jouerTour(entiteActuelle);

                // Vérifier après chaque tour si la partie est finie
                if (estFinDePartie()) {
                    break;
                }

                // Pause entre les tours pour la lisibilité
                System.out.println("\n" + "=".repeat(50) + "\n");
            }

            m_numeroTour++;

            // Afficher la carte après chaque tour complet
            System.out.println("État de la carte après le tour " + (m_numeroTour - 1) + " :");
            m_donjon.getCarte().afficher();
        }

        m_donjon.finDonjon();
    }

    /**
     * Gère le tour d'une entité (personnage ou monstre)
     */
    private void jouerTour(ElementMobile entite) {
        System.out.println(">>> Tour de " + entite.getNom() + " <<<");

        if (entite instanceof Personnage) {
            jouerTourPersonnage((Personnage) entite);
        } else if (entite instanceof Monstre) {
            jouerTourMonstre((Monstre) entite);
        }
    }

    /**
     * Gère le tour d'un personnage (joueur)
     */
    private void jouerTourPersonnage(Personnage personnage) {
        System.out.println("C'est au tour de " + personnage.getNom() +
                " (PV: " + personnage.getPointsDeVie() + "/" + personnage.getPointsDeVieMax() + ")");

        int actionsRestantes = 3;

        while (actionsRestantes > 0) {
            System.out.println("\nActions restantes : " + actionsRestantes);
            System.out.println("Actions disponibles :");
            System.out.println("1. S'équiper");
            System.out.println("2. Se déplacer");
            System.out.println("3. Attaquer");
            System.out.println("4. Ramasser un équipement");
            System.out.println("5. Terminer le tour");

            System.out.print("Choisissez une action : ");
            int choix = m_scanner.nextInt();
            m_scanner.nextLine(); // Consommer la ligne

            boolean actionEffectuee = false;

            switch (choix) {
                case 1:
                    actionEffectuee = actionSEquiper(personnage);
                    break;
                case 2:
                    actionEffectuee = actionSeDeplacer(personnage);
                    break;
                case 3:
                    actionEffectuee = actionAttaquer(personnage);
                    break;
                case 4:
                    actionEffectuee = actionRamasserEquipement(personnage);
                    break;
                case 5:
                    System.out.println(personnage.getNom() + " termine son tour.");
                    return;
                default:
                    System.out.println("Choix invalide, réessayez.");
                    continue;
            }

            if (actionEffectuee) {
                actionsRestantes--;
                demanderCommentaire();
            }
        }

        System.out.println(personnage.getNom() + " a épuisé ses actions pour ce tour.");
    }

    /**
     * Gère le tour d'un monstre (contrôlé par le maître du jeu)
     */
    private void jouerTourMonstre(Monstre monstre) {
        System.out.println("C'est au tour du monstre " + monstre.getNom() +
                " (PV: " + monstre.getPointsDeVie() + "/" + monstre.getPointsDeVieMax() + ")");
        System.out.println("Maître du jeu, contrôlez ce monstre.");

        int actionsRestantes = 3;

        while (actionsRestantes > 0) {
            System.out.println("\nActions restantes pour " + monstre.getNom() + " : " + actionsRestantes);
            System.out.println("Actions disponibles :");
            System.out.println("1. Se déplacer");
            System.out.println("2. Attaquer");
            System.out.println("3. Terminer le tour");

            System.out.print("Choisissez une action : ");
            int choix = m_scanner.nextInt();
            m_scanner.nextLine(); // Consommer la ligne

            boolean actionEffectuee = false;

            switch (choix) {
                case 1:
                    actionEffectuee = actionSeDeplacer(monstre);
                    break;
                case 2:
                    actionEffectuee = actionAttaquerMonstre(monstre);
                    break;
                case 3:
                    System.out.println(monstre.getNom() + " termine son tour.");
                    return;
                default:
                    System.out.println("Choix invalide, réessayez.");
                    continue;
            }

            if (actionEffectuee) {
                actionsRestantes--;
                demanderCommentaire();
            }
        }

        System.out.println(monstre.getNom() + " a épuisé ses actions pour ce tour.");
    }

    /**
     * Action : S'équiper (personnages uniquement)
     */
    private boolean actionSEquiper(Personnage personnage) {
        if (personnage.getInventaire().isEmpty()) {
            System.out.println(personnage.getNom() + " n'a aucun équipement dans son inventaire.");
            return false;
        }

        System.out.println("\n--- Action : S'équiper ---");
        m_gestionEquipement.proposerChangement(personnage);
        return true;
    }

    /**
     * Action : Se déplacer
     */
    private boolean actionSeDeplacer(ElementMobile entite) {
        System.out.println("\n--- Action : Se déplacer ---");
        return m_deplacement.gererDeplacement(entite);
    }

    /**
     * Action : Attaquer (personnage)
     */
    private boolean actionAttaquer(Personnage personnage) {
        System.out.println("\n--- Action : Attaquer ---");

        if (personnage.getArmeEquipee() == null) {
            System.out.println(personnage.getNom() + " n'a pas d'arme équipée !");
            return false;
        }

        // Lister les monstres à portée
        List<Monstre> monstresAPortee = getMonstresAPortee(personnage);

        if (monstresAPortee.isEmpty()) {
            System.out.println("Aucun monstre à portée !");
            return false;
        }

        System.out.println("Monstres à portée :");
        for (int i = 0; i < monstresAPortee.size(); i++) {
            Monstre monstre = monstresAPortee.get(i);
            System.out.println((i + 1) + ". " + monstre.getNom() +
                    " (PV: " + monstre.getPointsDeVie() + "/" + monstre.getPointsDeVieMax() + ")");
        }

        System.out.print("Choisissez votre cible : ");
        int choixCible = m_scanner.nextInt() - 1;
        m_scanner.nextLine();

        if (choixCible < 0 || choixCible >= monstresAPortee.size()) {
            System.out.println("Choix invalide.");
            return false;
        }

        Monstre cible = monstresAPortee.get(choixCible);
        Case casePersonnage = m_donjon.getCarte().getCase(personnage);
        Case caseCible = m_donjon.getCarte().getCase(cible);

        return m_attaque.attaquer(m_donjon.getCarte(), personnage, cible, casePersonnage, caseCible);
    }

    /**
     * Action : Attaquer (monstre)
     */
    private boolean actionAttaquerMonstre(Monstre monstre) {
        System.out.println("\n--- Action : Attaquer ---");

        // Lister les personnages à portée
        List<Personnage> personnagesAPortee = getPersonnagesAPortee(monstre);

        if (personnagesAPortee.isEmpty()) {
            System.out.println("Aucun personnage à portée !");
            return false;
        }

        System.out.println("Personnages à portée :");
        for (int i = 0; i < personnagesAPortee.size(); i++) {
            Personnage personnage = personnagesAPortee.get(i);
            System.out.println((i + 1) + ". " + personnage.getNom() +
                    " (PV: " + personnage.getPointsDeVie() + "/" + personnage.getPointsDeVieMax() + ")");
        }

        System.out.print("Choisissez votre cible : ");
        int choixCible = m_scanner.nextInt() - 1;
        m_scanner.nextLine();

        if (choixCible < 0 || choixCible >= personnagesAPortee.size()) {
            System.out.println("Choix invalide.");
            return false;
        }

        Personnage cible = personnagesAPortee.get(choixCible);
        Case caseMonstre = m_donjon.getCarte().getCase(monstre);
        Case caseCible = m_donjon.getCarte().getCase(cible);

        return m_attaque.attaquer(m_donjon.getCarte(), monstre, cible, caseMonstre, caseCible);
    }

    /**
     * Action : Ramasser un équipement (personnages uniquement)
     */
    private boolean actionRamasserEquipement(Personnage personnage) {
        System.out.println("\n--- Action : Ramasser un équipement ---");

        Case casePersonnage = m_donjon.getCarte().getCase(personnage);
        // Récupérer tous les équipements présents sur la case
        List<Equipement> equipementsSurCase = new ArrayList<>();
        for (ElementCarte element : casePersonnage.getContenu()) {
            equipementsSurCase.add((Equipement) element);
        }

        if (equipementsSurCase.isEmpty()) {
            System.out.println("Aucun équipement sur cette case !");
            return false;
        }

        System.out.println("Équipements disponibles :");
        for (int i = 0; i < equipementsSurCase.size(); i++) {
            System.out.println((i + 1) + ". " + equipementsSurCase.get(i).getNom());
        }

        System.out.print("Choisissez l'équipement à ramasser : ");
        int choix = m_scanner.nextInt() - 1;
        m_scanner.nextLine();

        if (choix < 0 || choix >= equipementsSurCase.size()) {
            System.out.println("Choix invalide.");
            return false;
        }

        Equipement equipementChoisi = equipementsSurCase.get(choix);
        personnage.ajouterAInventaire(equipementChoisi);
        casePersonnage.retirerContenu(equipementChoisi);

        System.out.println(personnage.getNom() + " a ramassé " + equipementChoisi.getNom());
        return true;
    }

    /**
     * Demande un commentaire pour le role play
     */
    private void demanderCommentaire() {
        System.out.print("\nSouhaitez-vous ajouter un commentaire pour le role play ? (o/n) : ");
        String reponse = m_scanner.nextLine().trim().toLowerCase();

        if (reponse.equals("o") || reponse.equals("oui")) {
            System.out.print("Votre commentaire : ");
            String commentaire = m_scanner.nextLine();
            System.out.println(">>> " + commentaire + " <<<");
        }
    }

    /**
     * Obtient la liste des monstres à portée d'un personnage
     */
    private List<Monstre> getMonstresAPortee(Personnage personnage) {
        Case casePersonnage = m_donjon.getCarte().getCase(personnage);
        int porteeArme = personnage.getArmeEquipee().getPortee();

        return m_donjon.getMonstres().stream()
                .filter(m -> !m.estMort())
                .filter(m -> {
                    Case caseMonstre = m_donjon.getCarte().getCase(m);
                    return m_donjon.getCarte().estAPortee(
                            casePersonnage.getX(), casePersonnage.getY(),
                            caseMonstre.getX(), caseMonstre.getY(),
                            porteeArme
                    );
                })
                .collect(java.util.stream.Collectors.toList());
    }

    /**
     * Obtient la liste des personnages à portée d'un monstre
     */
    private List<Personnage> getPersonnagesAPortee(Monstre monstre) {
        Case caseMonstre = m_donjon.getCarte().getCase(monstre);
        int porteeMonstre = monstre.getPortee();

        return m_donjon.getJoueurs().stream()
                .filter(p -> !p.estMort())
                .filter(p -> {
                    Case casePersonnage = m_donjon.getCarte().getCase(p);
                    return m_donjon.getCarte().estAPortee(
                            caseMonstre.getX(), caseMonstre.getY(),
                            casePersonnage.getX(), casePersonnage.getY(),
                            porteeMonstre
                    );
                })
                .collect(java.util.stream.Collectors.toList());
    }

    /**
     * Vérifie si la partie est terminée
     */
    private boolean estFinDePartie() {
        // Vérifier si tous les personnages sont morts
        boolean tousPersonnagesMorts = m_donjon.getJoueurs().stream()
                .allMatch(ElementMobile::estMort);

        // Vérifier si tous les monstres sont morts
        boolean tousMonstresMorts = m_donjon.getMonstres().stream()
                .allMatch(ElementMobile::estMort);

        return tousPersonnagesMorts || tousMonstresMorts;
    }

    // Getters
    public int getNumeroTour() {
        return m_numeroTour;
    }

    public ElementMobile getEntiteActuelle() {
        if (m_indexTourActuel >= 0 && m_indexTourActuel < m_donjon.getEntiteTour().size()) {
            return m_donjon.getEntiteTour().get(m_indexTourActuel);
        }
        return null;
    }
}