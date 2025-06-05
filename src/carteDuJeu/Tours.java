package carteDuJeu;

import carteDuJeu.actions.*;
import carteDuJeu.personnages.Personnage;
import carteDuJeu.personnages.equipements.Equipement;
import carteDuJeu.monstres.Monstre;
import carteDuJeu.personnages.sorts.*;

import java.util.ArrayList;
import java.util.InputMismatchException;
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
    private MaitreDuJeu m_maitreDuJeu;
    private StringBuilder historiqueActions;

    public Tours(Donjon donjon) {
        this.m_donjon = donjon;
        this.m_scanner = new Scanner(System.in);
        this.m_gestionEquipement = new ChangerEquipement();
        this.m_deplacement = new Deplacement(donjon.getCarte());
        this.m_attaque = new carteDuJeu.actions.Attaque(m_deplacement);
        this.m_indexTourActuel = 0;
        this.m_numeroTour = 1;
        this.m_maitreDuJeu = donjon.getMaitreDuJeu();
        this.historiqueActions = null;
    }

    /**
     * Lance la boucle principale des tours de jeu
     */
    public void commencerTours() {
        System.out.println("\n=== DÉBUT DES TOURS DE JEU ===\n");

        // Afficher la carte au début du combat
        System.out.println("État initial de la carte :");
        Affichage.afficherCarte(java.util.Optional.ofNullable(m_donjon.getCarte()));
        System.out.println("\n" + "=".repeat(50) + "\n");

        while (!estFinDePartie()) {
            System.out.println("--- TOUR " + m_numeroTour + " ---");

            // Jouer le tour de chaque entité
            for (m_indexTourActuel = 0; m_indexTourActuel < m_donjon.getEntiteTour().size(); m_indexTourActuel++) {
                ElementMobile entiteActuelle = m_donjon.getEntiteTour().get(m_indexTourActuel);

                // Vérifier si l'entité est encore vivante
                if (entiteActuelle.estMort()) {
                    continue;
                }

                // Afficher la carte au début de chaque tour d'entité
                System.out.println("\n📍 État de la carte avant le tour de " + entiteActuelle.getNom() + " :");
                Affichage.afficherCarte(java.util.Optional.ofNullable(m_donjon.getCarte()));
                System.out.println();

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
            if (!estFinDePartie()) {
                System.out.println("📊 État de la carte après le tour " + (m_numeroTour - 1) + " :");
                Affichage.afficherCarte(java.util.Optional.ofNullable(m_donjon.getCarte()));
                System.out.println("\n" + "=".repeat(80) + "\n");
            }
        }

        // Afficher la carte finale
        System.out.println("🏁 État final de la carte :");
        Affichage.afficherCarte(java.util.Optional.ofNullable(m_donjon.getCarte()));

        m_donjon.finDonjon();
    }

    /**
     * Gère le tour d'une entité (personnage ou monstre)
     */
    private void jouerTour(ElementMobile entite) {
        System.out.println(">>> Tour de " + entite.getNom() + " <<<");

        if (entite.estPersonnage()) {
            jouerTourPersonnage((Personnage) entite);
        } else if (!entite.estPersonnage()) {
            jouerTourMonstre((Monstre) entite);
        }
    }

    /**
     * Gère le tour d'un personnage (joueur)
     */
    private void jouerTourPersonnage(Personnage personnage) {
        System.out.println("C'est au tour de " + personnage.getNom() +
                " (PV: " + personnage.getPointsDeVie() + "/" + personnage.getPointsDeVieMax() + ")");
        historiqueActions = new StringBuilder();
        int actionsRestantes = 3;


        while (actionsRestantes > 0) {
            if(personnage.estMort()) {
                return;
            }
            Affichage.afficherCarte(java.util.Optional.ofNullable(m_donjon.getCarte()));
            System.out.println("\nActions restantes : " + actionsRestantes);
            System.out.println("Actions disponibles :");
            System.out.println("1. S'équiper");
            System.out.println("2. Se déplacer");
            System.out.println("3. Attaquer");
            System.out.println("4. Lancer un sort");
            System.out.println("5. Ramasser un équipement");
            System.out.println("6. Terminer le tour");

            int choix = -1;
            while (true) {
                System.out.print("Choisissez une action : ");
                try {
                    choix = m_scanner.nextInt();
                    m_scanner.nextLine(); // Consommer la ligne
                    break;
                } catch (InputMismatchException e) {
                    System.out.println("Entrée invalide, veuillez entrer un nombre.");
                    m_scanner.nextLine(); // Vider la ligne incorrecte
                }
            }

            boolean actionEffectuee = false;
            boolean consommerAction = true;

            switch (choix) {
                case 1:
                    actionEffectuee = actionSEquiper(personnage);
                    consommerAction = false;
                    break;
                case 2:
                    actionEffectuee = actionSeDeplacer(personnage);
                    if (actionEffectuee) {
                        // Afficher la carte après un déplacement
                        System.out.println("\n🚶 Carte après déplacement de " + personnage.getNom() + " :");
                        historiqueActions.append(personnage.getNom())
                                .append(" avance prudemment dans les sombres couloirs du donjon, ses pas résonnant sur les pierres froides. ")
                                .append("Chaque ombre semble cacher un danger, et les grognements lointains des monstres rôdent dans l'air, ")
                                .append("rappelant à ").append(personnage.getNom())
                                .append(" que le danger est partout. Une sueur froide coule sur son front alors qu'il s'efforce de rester vigilant.");
                        historiqueActions.append("\n");

                        Affichage.afficherCarte(java.util.Optional.ofNullable(m_donjon.getCarte()));
                    }
                    break;
                case 3:
                    actionEffectuee = actionAttaquer(personnage);
                    if (actionEffectuee) {
                        // Afficher la carte après une attaque (pour voir les effets)
                        System.out.println("\n⚔️ Carte après attaque de " + personnage.getNom() + " :");
                        Affichage.afficherCarte(java.util.Optional.ofNullable(m_donjon.getCarte()));
                    }
                    break;
                case 4:
                    actionEffectuee = actionLancerSort(personnage);
                    if (actionEffectuee) {
                        // Afficher la carte après le lancement du sort
                        System.out.println("\n🪄 Carte après lancement de sort de " + personnage.getNom() + " :");
                        Affichage.afficherCarte(java.util.Optional.ofNullable(m_donjon.getCarte()));
                    }
                break;
                case 5:
                    actionEffectuee = actionRamasserEquipement(personnage);
                    consommerAction = false;
                    if (actionEffectuee) {
                        // Afficher la carte après ramassage d'équipement
                        System.out.println("\n📦 Carte après ramassage d'équipement :");
                        Affichage.afficherCarte(java.util.Optional.ofNullable(m_donjon.getCarte()));
                    }
                    break;
                case 6:
                    System.out.println(personnage.getNom() + " termine son tour.");
                    demanderCommentaire();
                    actionMDJ(m_donjon.getJoueurs());
                    return;
                default:
                    System.out.println("Choix invalide, réessayez.");
                    continue;
            }

            // Si l'action a été effectuée ou si le joueur a choisi de terminer son tour
            if (actionEffectuee && consommerAction) {
                actionsRestantes--;
                demanderCommentaire();
                actionMDJ(m_donjon.getJoueurs());
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

            int choix = -1;
            while (true) {
                if(monstre.estMort())
                {
                    return;
                }
                System.out.print("Choisissez une action : ");
                try {
                    choix = m_scanner.nextInt();
                    m_scanner.nextLine(); // Consommer la ligne
                    break;
                } catch (InputMismatchException e) {
                    System.out.println("Entrée invalide, veuillez entrer un nombre.");
                    m_scanner.nextLine(); // Vider la ligne incorrecte
                }
            }

            boolean actionEffectuee = false;

            switch (choix) {
                case 1:
                    actionEffectuee = actionSeDeplacer(monstre);
                    if (actionEffectuee) {
                        // Afficher la carte après déplacement du monstre
                        System.out.println("\n👹 Carte après déplacement de " + monstre.getNom() + " :");
                        Affichage.afficherCarte(java.util.Optional.ofNullable(m_donjon.getCarte()));
                    }
                    break;
                case 2:
                    actionEffectuee = actionAttaquerMonstre(monstre);
                    if (actionEffectuee) {
                        // Afficher la carte après attaque du monstre
                        System.out.println("\n🗡️ Carte après attaque de " + monstre.getNom() + " :");
                        Affichage.afficherCarte(java.util.Optional.ofNullable(m_donjon.getCarte()));
                    }
                    break;
                case 3:
                    System.out.println(monstre.getNom() + " termine son tour.");
                    actionMDJ(m_donjon.getJoueurs());
                    return;
                default:
                    System.out.println("Choix invalide, réessayez.");
                    continue;
            }

            if (actionEffectuee) {
                actionsRestantes--;

                actionMDJ(m_donjon.getJoueurs());
            }
        }

        System.out.println(monstre.getNom() + " a épuisé ses actions pour ce tour.");
    }

    public void actionMDJ(List<Personnage> joueurs) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n--- Actions du Maître du Jeu ---");
            System.out.println("1. Frapper avec la foudre divine");
            System.out.println("2. Déplacer un monstre ou joueur");
            System.out.println("3. Ajouter un obstacle");
            System.out.println("4. Terminer l'action du Maître du Jeu");
            int choix = -1;
            while (true) {
                System.out.print("Choisissez une action : ");
                try {
                    choix = m_scanner.nextInt();
                    m_scanner.nextLine(); // Consommer la ligne
                    break;
                } catch (InputMismatchException e) {
                    System.out.println("Entrée invalide, veuillez entrer un nombre.");
                    m_scanner.nextLine(); // Vider la ligne incorrecte
                }
            }

            switch (choix) {
                case 1:
                    m_maitreDuJeu.faireDmg(joueurs);
                    historiqueActions.append("⚡ Un éclair divin fend l’obscurité du donjon, frappant sa cible avec la colère des dieux.\n");
                    break;
                case 2:
                    m_maitreDuJeu.deplacerCibleParNom();
                    historiqueActions.append("👁️ Des forces obscures manipulent les fils du destin et déplacent une entité dans les ténèbres du donjon.\n");
                    break;
                case 3:
                    m_maitreDuJeu.ajouterObstacle();
                    historiqueActions.append("🧱 Un grondement sourd résonne... un nouvel obstacle émerge pour piéger les aventuriers imprudents.\n");
                    break;
                case 4:
                    System.out.println("Fin des actions du Maître du Jeu.");
                    historiqueActions.append("🎭 Les forces obscures du donjon semblent être satisfaites des actions menées précedemment...\n");
                    return;
                default:
                    System.out.println("Choix invalide, réessayez.");
            }
        }
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

        int choixCible = -1;
        while (true) {
            System.out.print("Choisissez votre cible : ");
            try {
                choixCible = m_scanner.nextInt() - 1;
                m_scanner.nextLine();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Entrée invalide, veuillez entrer un nombre.");
                m_scanner.nextLine();
            }
        }

        if (choixCible < 0 || choixCible >= monstresAPortee.size()) {
            System.out.println("Choix invalide.");
            return false;
        }

        Monstre cible = monstresAPortee.get(choixCible);
        Carte carte = m_donjon.getCarte();
        Case casePersonnage = carte.getCase(personnage)
                .orElseThrow(() -> new IllegalArgumentException("Case du personnage introuvable"));
        Case caseCible = m_donjon.getCarte().getCase(cible)
                .orElseThrow(() -> new IllegalArgumentException("Case de la cible introuvable"));

        // Mettre à jour l'historique des actions
        historiqueActions.append(personnage.getNom())
                .append(" pris son courage a deux mains et frappa alors le monstre ayant une apprarence de ")
                .append(cible.getNom())
                .append(" de manière violente.");

        if (cible.estMort()) {
            historiqueActions.append(" Il en est mort.");
        }
        historiqueActions.append("\n");

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

        int choixCible = -1;
        while (true) {
            System.out.print("Choisissez votre cible : ");
            try {
                choixCible = m_scanner.nextInt() - 1;
                m_scanner.nextLine();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Entrée invalide, veuillez entrer un nombre.");
                m_scanner.nextLine();
            }
        }

        if (choixCible < 0 || choixCible >= personnagesAPortee.size()) {
            System.out.println("Choix invalide.");
            return false;
        }

        Personnage cible = personnagesAPortee.get(choixCible);
        Case caseMonstre = m_donjon.getCarte().getCase(monstre)
                .orElseThrow(() -> new IllegalArgumentException("Case du monstre introuvable"));
        Case caseCible = m_donjon.getCarte().getCase(cible)
                .orElseThrow(() -> new IllegalArgumentException("Case de la cible introuvable"));

        return m_attaque.attaquer(m_donjon.getCarte(), monstre, cible, caseMonstre, caseCible);
    }

    /**
     * Action : Lancer un sort (personnages uniquement)
     */
    private boolean actionLancerSort(Personnage personnage) {
        System.out.println("\n--- Action : Lancer un sort ---");
        //améliorer avec un getSorts dans personnages peut etre
        if (personnage.getClasse().equals("Magicien")) {
            System.out.println("Sorts disponibles pour le Magicien :");
            System.out.println("1. Arme magique");
            System.out.println("2. Boogie Woogie");
            System.out.println("3. Guérison");

            int choix = -1;
            while (true) {
                System.out.print("Choisissez le chiffre du sort à lancer : ");
                try {
                    choix = m_scanner.nextInt() - 1;
                    m_scanner.nextLine();
                    break;
                } catch (InputMismatchException e) {
                    System.out.println("Entrée invalide, veuillez entrer un nombre.");
                    m_scanner.nextLine();
                }
            }

            if (choix < 0 || choix > 2) {
                System.out.println("Choix invalide.");
                return false;
            }

            String nomSort = "";
            boolean sortLance = false;

            switch (choix) {
                case 0:
                    nomSort = "Arme magique";
                    // Logique pour lancer le sort Arme magique
                    sortLance = lancerSortArmeMagique(personnage);
                    break;
                case 1:
                    nomSort = "Boogie Woogie";
                    // Logique pour lancer le sort Boogie Woogie
                    sortLance = lancerSortBoogieWoogie(personnage);
                    break;
                case 2:
                    nomSort = "Guérison";
                    // Logique pour lancer le sort de guérison
                    sortLance = lancerSortGuerison(personnage);
                    break;
            }

            if (sortLance) {
                historiqueActions.append(personnage.getNom())
                        .append(" a lancé le sort ")
                        .append(nomSort)
                        .append(" avec succès.\n");
                return true;
            } else {
                System.out.println("Le sort n'a pas pu être lancé.");
                return false;
            }

        } else if (personnage.getClasse().equals("Clerc")) {
            System.out.println("Sorts disponibles pour le Clerc :");
            System.out.println("1. Guérison");

            int choix = -1;
            while (true) {
                System.out.print("Choisissez le chiffre du sort à lancer : ");
                try {
                    choix = m_scanner.nextInt() - 1;
                    m_scanner.nextLine();
                    break;
                } catch (InputMismatchException e) {
                    System.out.println("Entrée invalide, veuillez entrer un nombre.");
                    m_scanner.nextLine();
                }
            }

            if (choix != 0) {
                System.out.println("Choix invalide.");
                return false;
            }

            boolean sortLance = lancerSortGuerison(personnage);
            if (sortLance) {
                historiqueActions.append(personnage.getNom())
                        .append(" a lancé le sort Guérison avec succès.\n");
                return true;
            } else {
                System.out.println("Le sort n'a pas pu être lancé.");
                return false;
            }

        } else {
            System.out.println(personnage.getNom() + " ne lance aucun sort !");
            return false;
        }
    }

    /**
     * Méthodes auxiliaires pour lancer les différents sorts
     */
    private boolean lancerSortArmeMagique(Personnage personnage) {
        SortArmeMagique sort = new SortArmeMagique();

        // Demander au joueur de choisir une cible
        System.out.println("Choisissez un personnage pour améliorer ses armes :");
        List<Personnage> personnagesDisponibles = m_donjon.getJoueurs();

        for (int i = 0; i < personnagesDisponibles.size(); i++) {
            Personnage p = personnagesDisponibles.get(i);
            if (!p.estMort()) {
                System.out.println((i + 1) + ". " + p.getNom());
            }
        }

        int choix = -1;
        while (true) {
            System.out.print("Choisissez la cible : ");
            try {
                choix = m_scanner.nextInt() - 1;
                m_scanner.nextLine();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Entrée invalide, veuillez entrer un nombre.");
                m_scanner.nextLine();
            }
        }

        if (choix < 0 || choix >= personnagesDisponibles.size() ||
                personnagesDisponibles.get(choix).estMort()) {
            System.out.println("Choix invalide.");
            return false;
        }

        ElementMobile[] cibles = {personnagesDisponibles.get(choix)};
        return sort.lancer(m_donjon.getCarte(), personnage, cibles);
    }

    private boolean lancerSortBoogieWoogie(Personnage personnage) {
        SortBoogieWoogie sort = new SortBoogieWoogie();

        // Créer une liste de toutes les entités mobiles
        List<ElementMobile> entitesDisponibles = new ArrayList<>();

        // Ajouter les joueurs vivants
        for (Personnage p : m_donjon.getJoueurs()) {
            if (!p.estMort()) {
                entitesDisponibles.add(p);
            }
        }

        // Ajouter les monstres vivants
        for (Monstre m : m_donjon.getMonstres()) {
            if (!m.estMort()) {
                entitesDisponibles.add(m);
            }
        }

        if (entitesDisponibles.size() < 2) {
            System.out.println("Il faut au moins 2 entités vivantes pour utiliser ce sort.");
            return false;
        }

        System.out.println("Choisissez deux entités à échanger :");
        for (int i = 0; i < entitesDisponibles.size(); i++) {
            ElementMobile e = entitesDisponibles.get(i);
            System.out.println((i + 1) + ". " + e.getNom() +
                    (e.estPersonnage() ? " (Personnage)" : " (Monstre)"));
        }

        int choix1 = -1, choix2 = -1;

        while (true) {
            System.out.print("Choisissez la première entité : ");
            try {
                choix1 = m_scanner.nextInt() - 1;
                m_scanner.nextLine();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Entrée invalide, veuillez entrer un nombre.");
                m_scanner.nextLine();
            }
        }

        while (true) {
            System.out.print("Choisissez la deuxième entité : ");
            try {
                choix2 = m_scanner.nextInt() - 1;
                m_scanner.nextLine();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Entrée invalide, veuillez entrer un nombre.");
                m_scanner.nextLine();
            }
        }

        if (choix1 < 0 || choix1 >= entitesDisponibles.size() ||
                choix2 < 0 || choix2 >= entitesDisponibles.size() ||
                choix1 == choix2) {
            System.out.println("Choix invalide.");
            return false;
        }

        ElementMobile[] cibles = {entitesDisponibles.get(choix1), entitesDisponibles.get(choix2)};
        return sort.lancer(m_donjon.getCarte(), personnage, cibles);
    }

    private boolean lancerSortGuerison(Personnage personnage) {
        SortGuerison sort = new SortGuerison();

        // Demander au joueur de choisir une cible
        System.out.println("Choisissez un personnage à soigner :");
        List<Personnage> personnagesDisponibles = m_donjon.getJoueurs();

        for (int i = 0; i < personnagesDisponibles.size(); i++) {
            Personnage p = personnagesDisponibles.get(i);
            if (!p.estMort() && p.getPointsDeVie() < p.getPointsDeVieMax()) {
                System.out.println((i + 1) + ". " + p.getNom() +
                        " (PV: " + p.getPointsDeVie() + "/" + p.getPointsDeVieMax() + ")");
            }
        }

        int choix = -1;
        while (true) {
            System.out.print("Choisissez la cible : ");
            try {
                choix = m_scanner.nextInt() - 1;
                m_scanner.nextLine();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Entrée invalide, veuillez entrer un nombre.");
                m_scanner.nextLine();
            }
        }

        if (choix < 0 || choix >= personnagesDisponibles.size() ||
                personnagesDisponibles.get(choix).estMort()) {
            System.out.println("Choix invalide.");
            return false;
        }

        ElementMobile[] cibles = {personnagesDisponibles.get(choix)};
        return sort.lancer(m_donjon.getCarte(), personnage, cibles);
    }

    /**
     * Action : Ramasser un équipement (personnages uniquement)
     */
    private boolean actionRamasserEquipement(Personnage personnage) {
        System.out.println("\n--- Action : Ramasser un équipement ---");

        Case casePersonnage = m_donjon.getCarte().getCase(personnage)
                .orElseThrow(() -> new IllegalArgumentException("Case du personnage introuvable"));
        // Récupérer tous les équipements présents sur la case
        List<Equipement> equipementsSurCase = new ArrayList<>();
        for (ElementCarte element : casePersonnage.getContenu()) {
            System.out.println("Type: " + element.getClass() + ", estEquipement: " + element.estEquipement());
            if (element.estEquipement()) {
                equipementsSurCase.add((Equipement) element);
            }
        }

        if (equipementsSurCase.isEmpty()) {
            System.out.println("Aucun équipement sur cette case !");
            return false;
        }

        System.out.println("Équipements disponibles :");
        for (int i = 0; i < equipementsSurCase.size(); i++) {
            System.out.println((i + 1) + ". " + equipementsSurCase.get(i).getNom());
        }

        int choix = -1;
        while (true) {
            System.out.print("Choisissez le chiffre de l'équipement à récuperer : ");
            try {
                choix = m_scanner.nextInt() - 1;
                m_scanner.nextLine();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Entrée invalide, veuillez entrer un nombre.");
                m_scanner.nextLine();
            }
        }

        if (choix < 0 || choix >= equipementsSurCase.size()) {
            System.out.println("Choix invalide.");
            return false;
        }

        Equipement equipementChoisi = equipementsSurCase.get(choix);
        personnage.ajouterAInventaire(equipementChoisi);
        casePersonnage.retirerContenu(equipementChoisi);

        // ajout commentaire au role play
        historiqueActions.append("✨ ").append(personnage.getNom())
        .append(" trouva en marchant une nouvelle pièce d'équipement, son éclat étincelant redonne espoir à notre héro... Pourra-il triompher des monstres avec celle-ci ?\n");
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
            if (historiqueActions.length() == 0) {
                System.out.println("Vous n'avez encore rien fait.");
            } else {
                m_maitreDuJeu.lireCommentaire(historiqueActions.toString());
            }
        }
    }

    /**
     * Obtient la liste des monstres à portée d'un personnage
     */
    private List<Monstre> getMonstresAPortee(Personnage personnage) {
        List<Monstre> monstresAPortee = new ArrayList<>();
        Case casePersonnage = m_donjon.getCarte().getCase(personnage)
                .orElseThrow(() -> new IllegalArgumentException("Case du personnage introuvable"));
        int porteeArme = personnage.getArmeEquipee().getPortee();

        for (Monstre monstre : m_donjon.getMonstres()) {
            if (!monstre.estMort()) {
                Case caseMonstre = m_donjon.getCarte().getCase(monstre)
                        .orElseThrow(() -> new IllegalArgumentException("Case du monstre introuvable"));
                if (m_donjon.getCarte().estAPortee(
                        casePersonnage.getX(), casePersonnage.getY(),
                        caseMonstre.getX(), caseMonstre.getY(),
                        porteeArme)) {
                    monstresAPortee.add(monstre);
                }
            }
        }
        return monstresAPortee;
    }

    /**
     * Obtient la liste des personnages à portée d'un monstre
     */
    private List<Personnage> getPersonnagesAPortee(Monstre monstre) {
        List<Personnage> personnagesAPortee = new ArrayList<>();
        Case caseMonstre = m_donjon.getCarte().getCase(monstre)
                .orElseThrow(() -> new IllegalArgumentException("Case du monstre introuvable"));
        int porteeMonstre = monstre.getPortee();

        for (Personnage personnage : m_donjon.getJoueurs()) {
            if (!personnage.estMort()) {
                Case casePersonnage = m_donjon.getCarte().getCase(personnage)
                        .orElseThrow(() -> new IllegalArgumentException("Case du personnage introuvable"));
                if (m_donjon.getCarte().estAPortee(
                        caseMonstre.getX(), caseMonstre.getY(),
                        casePersonnage.getX(), casePersonnage.getY(),
                        porteeMonstre)) {
                    personnagesAPortee.add(personnage);
                }
            }
        }
        return personnagesAPortee;
    }

    /**
     * Vérifie si la partie est terminée
     */
    private boolean estFinDePartie() {
        boolean tousPersonnagesMorts = true;
        historiqueActions = new StringBuilder();
        historiqueActions.append("L'intégralités de nos héros sont mort, les monstres ont " +
                "   triomphé de ceux-ci, le donjon restera ouvert pour que d'autres aventuriers malheureux " +
                "y trouvent leur fin...\n");
        for (Personnage p : m_donjon.getJoueurs()) {
            if (!p.estMort()) {
                tousPersonnagesMorts = false;
                historiqueActions.setLength(0);
                break;
            }
        }
        // Vérifier si tous les monstres sont morts
        historiqueActions.append("Tous les monstres sont morts, les aventuriers ont triomphé du donjon !\n");
        boolean tousMonstresMorts = true;
        for (Monstre m : m_donjon.getMonstres()) {
            if (!m.estMort()) {
                tousMonstresMorts = false;
                historiqueActions.setLength(0);
                break;
            }
        }
        // La partie est terminée si tous les personnages sont morts ou si tous les monstres sont morts

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