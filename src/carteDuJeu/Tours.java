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

/**
 * Gère la boucle principale des tours de jeu, l'enchaînement des actions des entités,
 * et les interactions avec le Maître du Jeu.
 */
public class Tours {
    private Donjon m_donjon;
    private Scanner m_scanner;
    private ChangerEquipement m_gestionEquipement;
    private Deplacement m_deplacement;
    private carteDuJeu.actions.Attaque m_attaque;
    private int m_indexTourActuel;
    private int m_numeroTour;
    private MaitreDuJeu m_maitreDuJeu;
    private StringBuilder m_historiqueActions;

    /**
     * Constructeur de la classe Tours.
     * @param donjon le donjon dans lequel se déroule la partie
     */
    public Tours(Donjon donjon) {
        this.m_donjon = donjon;
        this.m_scanner = new Scanner(System.in);
        this.m_gestionEquipement = new ChangerEquipement();
        this.m_deplacement = new Deplacement(donjon.getCarte());
        this.m_attaque = new carteDuJeu.actions.Attaque(m_deplacement);
        this.m_indexTourActuel = 0;
        this.m_numeroTour = 1;
        this.m_maitreDuJeu = donjon.getMaitreDuJeu();
        this.m_historiqueActions = new StringBuilder();
    }

    /**
     * Lance la boucle principale des tours de jeu.
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
     * Gère le tour d'une entité (personnage ou monstre).
     * @param entite l'entité dont c'est le tour
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
     * Gère le tour d'un personnage (joueur).
     * @param personnage le personnage joueur
     */
    private void jouerTourPersonnage(Personnage personnage) {
        int actionsRestantes = 3;

        while (actionsRestantes > 0) {
            if(personnage.estMort()) {
                return;
            }
            Affichage.afficherCarte(java.util.Optional.ofNullable(m_donjon.getCarte()));
            System.out.println("\nC'est au tour de " + personnage.toString());
            System.out.println("\nActions restantes : " + actionsRestantes);
            System.out.println("Actions disponibles :");
            System.out.println("1. S'équiper");
            System.out.println("2. Se déplacer");
            System.out.println("3. Attaquer");
            System.out.println("4. Lancer un sort");
            System.out.println("5. Ramasser un équipement");
            System.out.println("6. Voir l'inventaire");
            System.out.println("7. Terminer le tour");

            int choixAction = -1;
            while (true) {
                System.out.print("Choisissez une action : ");
                try {
                    choixAction = m_scanner.nextInt();
                    m_scanner.nextLine(); // Consommer la ligne
                    break;
                } catch (InputMismatchException e) {
                    System.out.println("Entrée invalide, veuillez entrer un nombre.");
                    m_scanner.nextLine(); // Vider la ligne incorrecte
                }
            }

            boolean actionEffectuee = false;
            boolean consommerAction = true;

            switch (choixAction) {
                case 1:
                    actionEffectuee = actionSEquiper(personnage);
                    consommerAction = false;
                    break;
                case 2:
                    actionEffectuee = actionSeDeplacer(personnage);
                    if (actionEffectuee) {
                        // Afficher la carte après un déplacement
                        System.out.println("\n🚶 Carte après déplacement de " + personnage.getNom() + " :");
                        m_historiqueActions.append("🚶‍♂️ Les pas de ").append(personnage.getNom())
                                .append(" résonnent dans les couloirs sombres du donjon, chaque pierre froide ")
                                .append("sous ses pieds murmurant des secrets oubliés. Les ombres dansent ")
                                .append("autour de notre héros, et les échos lointains de créatures tapies ")
                                .append("dans l'obscurité rappellent constamment que le danger rôde.\n");

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
                        System.out.println("\n🧙 Carte après lancement de sort de " + personnage.getNom() + " :");
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
                    actionEffectuee = actionVoirInventaire(personnage);
                    consommerAction = false;
                    break;
                case 7:
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
     * Gère le tour d'un monstre (contrôlé par le maître du jeu).
     * @param monstre le monstre à jouer
     */
    private void jouerTourMonstre(Monstre monstre) {
        System.out.println("Maître du jeu, contrôlez ce monstre.");
        int actionsRestantes = 3;

        while (actionsRestantes > 0) {
            System.out.println("C'est au tour du monstre "+ monstre.toString());
            System.out.println("\nActions restantes pour " + monstre.getNom() + " : " + actionsRestantes);
            System.out.println("Actions disponibles :");
            System.out.println("1. Se déplacer");
            System.out.println("2. Attaquer");
            System.out.println("3. Terminer le tour");

            int choixAction = -1;
            while (true) {
                if(monstre.estMort())
                {
                    return;
                }
                System.out.print("Choisissez une action : ");
                try {
                    choixAction = m_scanner.nextInt();
                    m_scanner.nextLine(); // Consommer la ligne
                    break;
                } catch (InputMismatchException e) {
                    System.out.println("Entrée invalide, veuillez entrer un nombre.");
                    m_scanner.nextLine(); // Vider la ligne incorrecte
                }
            }

            boolean actionEffectuee = false;

            switch (choixAction) {
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


    /**
     * Permet au Maître du Jeu d'effectuer des actions spéciales.
     * @param joueurs la liste des joueurs
     */
    public void actionMDJ(List<Personnage> joueurs) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n--- Actions du Maître du Jeu ---");
            System.out.println("1. Frapper avec la foudre divine");
            System.out.println("2. Déplacer un monstre ou joueur");
            System.out.println("3. Ajouter un obstacle");
            System.out.println("4. Terminer l'action du Maître du Jeu");
            int choixAction = -1;
            while (true) {
                System.out.print("Choisissez une action : ");
                try {
                    choixAction = m_scanner.nextInt();
                    m_scanner.nextLine(); // Consommer la ligne
                    break;
                } catch (InputMismatchException e) {
                    System.out.println("Entrée invalide, veuillez entrer un nombre.");
                    m_scanner.nextLine(); // Vider la ligne incorrecte
                }
            }

            switch (choixAction) {
                case 1:
                    m_maitreDuJeu.faireDmg(joueurs);
                    m_historiqueActions.append("⚡ Un fracas assourdissant déchire le silence du donjon ! ")
                            .append("Un éclair divin, pur et terrible, transperce les ténèbres pour ")
                            .append("frapper sa cible d'une colère céleste. La lumière aveuglante ")
                            .append("révèle brièvement les secrets cachés dans l'obscurité.\n");
                    break;
                case 2:
                    m_maitreDuJeu.deplacerCibleParNom();
                    m_historiqueActions.append("🌀 Des forces mystérieuses s'éveillent dans les profondeurs... ")
                            .append("Invisible et impitoyable, une main spectrale saisit sa proie et ")
                            .append("la déplace selon la volonté du destin. Les pierres du donjon ")
                            .append("tremblent sous le poids de cette magie ancienne.\n");
                    break;
                case 3:
                    m_maitreDuJeu.ajouterObstacle();
                    m_historiqueActions.append("🗿 Un grondement sourd résonne dans les entrailles du donjon... ")
                            .append("La terre se fissure et un obstacle surgit du néant, ")
                            .append("comme si le donjon lui-même conspirait contre les intrus. ")
                            .append("Les murs semblent ricaner d'une joie malveillante.\n");
                    break;
                case 4:
                    System.out.println("Fin des actions du Maître du Jeu.");
                    m_historiqueActions.append("🎭 Les forces obscures du donjon se retirent dans l'ombre, ")
                            .append("satisfaites de leur œuvre. Un silence pesant s'installe, ")
                            .append("chargé de promesses sinistres pour la suite de l'aventure...\n");
                    return;
                default:
                    System.out.println("Choix invalide, réessayez.");
            }
        }
    }

    /**
     * Action : S'équiper (personnages uniquement).
     * @param personnage le personnage qui souhaite s'équiper
     * @return true si l'action a été effectuée, false sinon
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
     * Action : Se déplacer.
     * @param entite l'entité à déplacer
     * @return true si le déplacement a eu lieu, false sinon
     */
    private boolean actionSeDeplacer(ElementMobile entite) {
        System.out.println("\n--- Action : Se déplacer ---");
        return m_deplacement.gererDeplacement(entite);
    }

    /**
     * Action : Voir l'inventaire (personnages uniquement).
     * @param personnage le personnage qui consulte son inventaire
     * @return true si l'inventaire a été affiché, false sinon
     */
    private boolean actionVoirInventaire(Personnage personnage) {
        System.out.println("\n----- Inventaire de " + personnage.getNom() + " -----");
        if (personnage.getInventaire().isEmpty()) {
            System.out.println(personnage.getNom() + " n'a aucun équipement dans son inventaire.\n\n");
            return false;
        }

        for (Equipement equipement : personnage.getInventaire()) {
            System.out.println("- " + equipement.toString() + "\n");
            System.out.println("------------------------------------------------------\n\n");
        }
        return true;
    }

    /**
     * Action : Attaquer (personnage).
     * @param personnage le personnage attaquant
     * @return true si l'attaque a eu lieu, false sinon
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
        m_historiqueActions.append("⚔️ ").append(personnage.getNom())
                .append(" brandit son arme avec une détermination farouche ! Ses yeux brillent ")
                .append("d'une lueur guerrière tandis qu'il attaque ")
                .append(cible.getNom()).append(", prêt à tout pour survivre dans ce donjon maudit. ");

        // Effectuer l'attaque et vérifier si la cible est morte
        boolean attaqueReussie = m_attaque.attaquer(m_donjon.getCarte(), personnage, cible, casePersonnage, caseCible);

        if (attaqueReussie && cible.estMort()) {
            m_historiqueActions.append("Un cri perçant déchire l'air ! Le monstre ")
                    .append(cible.getNom())
                    .append(" s'effondre dans un râle d'agonie, ses dernières forces s'échappant ")
                    .append("comme un souffle dans la nuit. La victoire a un goût amer dans ce lieu maudit.");
        }
        m_historiqueActions.append("\n");

        return attaqueReussie;
    }

    /**
     * Action : Attaquer (monstre).
     * @param monstre le monstre attaquant
     * @return true si l'attaque a eu lieu, false sinon
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
     * Action : Lancer un sort (personnages uniquement).
     * @param personnage le personnage lançant le sort
     * @return true si le sort a été lancé, false sinon
     */
    private boolean actionLancerSort(Personnage personnage) {
        System.out.println("\n--- Action : Lancer un sort ---");
        //améliorer avec un getSorts dans personnages peut etre
        if (personnage.getClasse().equals("Magicien")) {
            System.out.println("Sorts disponibles pour le Magicien :");
            System.out.println("1. Arme magique");
            System.out.println("2. Boogie Woogie");
            System.out.println("3. Guérison");

            int choixSort = -1;
            while (true) {
                System.out.print("Choisissez le chiffre du sort à lancer : ");
                try {
                    choixSort = m_scanner.nextInt() - 1;
                    m_scanner.nextLine();
                    break;
                } catch (InputMismatchException e) {
                    System.out.println("Entrée invalide, veuillez entrer un nombre.");
                    m_scanner.nextLine();
                }
            }

            if (choixSort < 0 || choixSort > 2) {
                System.out.println("Choix invalide.");
                return false;
            }

            String nomSort = "";
            boolean sortLance = false;

            switch (choixSort) {
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
                m_historiqueActions.append("✨ ").append(personnage.getNom())
                        .append(" lève les mains vers les voûtes sombres du donjon, ses doigts ")
                        .append("crépitant d'énergie mystique. Les incantations anciennes résonnent ")
                        .append("dans l'air tandis qu'il invoque le sort '").append(nomSort)
                        .append("', pliant la réalité à sa volonté arcane.\n");
                return true;
            } else {
                System.out.println("Le sort n'a pas pu être lancé.");
                return false;
            }

        } else if (personnage.getClasse().equals("Clerc")) {
            System.out.println("Sorts disponibles pour le Clerc :");
            System.out.println("1. Guérison");

            int choixSort = -1;
            while (true) {
                System.out.print("Choisissez le chiffre du sort à lancer : ");
                try {
                    choixSort = m_scanner.nextInt() - 1;
                    m_scanner.nextLine();
                    break;
                } catch (InputMismatchException e) {
                    System.out.println("Entrée invalide, veuillez entrer un nombre.");
                    m_scanner.nextLine();
                }
            }

            if (choixSort != 0) {
                System.out.println("Choix invalide.");
                return false;
            }

            boolean sortLance = lancerSortGuerison(personnage);
            if (sortLance) {
                m_historiqueActions.append("🕊️ ").append(personnage.getNom())
                        .append(" ferme les yeux et joint ses mains dans une prière fervente. ")
                        .append("Une aura dorée l'enveloppe tandis qu'il canalise la bénédiction divine, ")
                        .append("faisant naître des fils de lumière purificatrice qui dansent autour ")
                        .append("de sa cible, chassant la douleur et restaurant l'espoir.\n");
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
     * Lance le sort Arme Magique.
     * @param personnage le magicien lançant le sort
     * @return true si le sort a été lancé, false sinon
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

        int choixCible = -1;
        while (true) {
            System.out.print("Choisissez la cible : ");
            try {
                choixCible = m_scanner.nextInt() - 1;
                m_scanner.nextLine();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Entrée invalide, veuillez entrer un nombre.");
                m_scanner.nextLine();
            }
        }

        if ( (choixCible < 0 || choixCible >= personnagesDisponibles.size() ||
                personnagesDisponibles.get(choixCible).estMort())) {
            System.out.println("Choix invalide.");
            return false;
        }

        ElementMobile[] cibles = {personnagesDisponibles.get(choixCible)};
        return sort.lancer(m_donjon.getCarte(), personnage, cibles);
    }

    /**
     * Lance le sort Boogie Woogie.
     * @param personnage le magicien lançant le sort
     * @return true si le sort a été lancé, false sinon
     */
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

        int choixCible1 = -1, choixCible2 = -1;

        while (true) {
            System.out.print("Choisissez la première entité : ");
            try {
                choixCible1 = m_scanner.nextInt() - 1;
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
                choixCible2 = m_scanner.nextInt() - 1;
                m_scanner.nextLine();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Entrée invalide, veuillez entrer un nombre.");
                m_scanner.nextLine();
            }
        }

        if (choixCible1 < 0 || choixCible1 >= entitesDisponibles.size() ||
                choixCible2 < 0 || choixCible2 >= entitesDisponibles.size() ||
                choixCible1 == choixCible2) {
            System.out.println("Choix invalide.");
            return false;
        }

        ElementMobile[] cibles = {entitesDisponibles.get(choixCible1), entitesDisponibles.get(choixCible2)};
        return sort.lancer(m_donjon.getCarte(), personnage, cibles);
    }

    /**
     * Lance le sort de Guérison.
     * @param personnage le personnage lançant le sort
     * @return true si le sort a été lancé, false sinon
     */
    private boolean lancerSortGuerison(Personnage personnage) {
        SortGuerison sort = new SortGuerison();

        // Demander au joueur de choisir une cible
        System.out.println("Choisissez un personnage à soigner :");
        List<Personnage> personnagesDisponibles = m_donjon.getJoueurs();

        // Créer une liste des personnages pouvant être soignés (vivants et pas à 100% de PV)
        List<Personnage> personnagesSoignables = new ArrayList<>();

        for (Personnage p : personnagesDisponibles) {
            if (!p.estMort() && p.getPointsDeVie() < p.getPointsDeVieMax()) {
                personnagesSoignables.add(p);
            }
        }

        if (personnagesSoignables.isEmpty()) {
            System.out.println("Aucun personnage ne peut être soigné (tous sont morts ou en pleine santé).");
            return false;
        }

        // Afficher les options avec une indication spéciale pour le self-target
        for (int i = 0; i < personnagesSoignables.size(); i++) {
            Personnage p = personnagesSoignables.get(i);
            String selfIndicator = p.equals(personnage) ? " (Vous-même)" : "";
            System.out.println((i + 1) + ". " + p.getNom() + selfIndicator +
                    " (PV: " + p.getPointsDeVie() + "/" + p.getPointsDeVieMax() + ")");
        }

        int choixCible = -1;
        while (true) {
            System.out.print("Choisissez la cible : ");
            try {
                choixCible = m_scanner.nextInt() - 1;
                m_scanner.nextLine();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Entrée invalide, veuillez entrer un nombre.");
                m_scanner.nextLine();
            }
        }

        if (choixCible < 0 || choixCible >= personnagesSoignables.size()) {
            System.out.println("Choix invalide.");
            return false;
        }

        Personnage cibleChoisie = personnagesSoignables.get(choixCible);

        // Message spécial pour le self-target
        if (cibleChoisie.equals(personnage)) {
            System.out.println(personnage.getNom() + " se soigne lui-même !");
        } else {
            System.out.println(personnage.getNom() + " soigne " + cibleChoisie.getNom() + " !");
        }

        ElementMobile[] cibles = {cibleChoisie};
        return sort.lancer(m_donjon.getCarte(), personnage, cibles);
    }

    /**
     * Action : Ramasser un équipement (personnages uniquement).
     * @param personnage le personnage qui ramasse l'équipement
     * @return true si un équipement a été ramassé, false sinon
     */
    private boolean actionRamasserEquipement(Personnage personnage) {
        System.out.println("\n--- Action : Ramasser un équipement ---");

        Case casePersonnage = m_donjon.getCarte().getCase(personnage)
                .orElseThrow(() -> new IllegalArgumentException("Case du personnage introuvable"));
        // Récupérer tous les équipements présents sur la case
        List<Equipement> equipementsSurCase = new ArrayList<>();
        for (ElementCarte element : casePersonnage.getContenu()) {
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

        int choixCible = -1;
        while (true) {
            System.out.print("Choisissez le chiffre de l'équipement à récuperer : ");
            try {
                choixCible = m_scanner.nextInt() - 1;
                m_scanner.nextLine();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Entrée invalide, veuillez entrer un nombre.");
                m_scanner.nextLine();
            }
        }

        if (choixCible < 0 || choixCible >= equipementsSurCase.size()) {
            System.out.println("Choix invalide.");
            return false;
        }

        Equipement equipementChoisi = equipementsSurCase.get(choixCible);
        personnage.ajouterAInventaire(equipementChoisi);
        casePersonnage.retirerContenu(equipementChoisi);

        // ajout commentaire au role play
        m_historiqueActions.append("✨ ").append(personnage.getNom())
                .append(" découvre ").append(equipementChoisi.getNom())
                .append(" abandonné dans les ombres du donjon. Son éclat mystérieux ")
                .append("redonne espoir à notre héros... Pourra-t-il triompher des monstres avec cet objet ?\n");
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
            if (m_historiqueActions.length() == 0) {
                System.out.println("Vous n'avez encore rien fait.");
            } else {
                m_maitreDuJeu.lireCommentaire(m_historiqueActions.toString());
            }
        }
    }


    /**
     * Vérifie si la partie est terminée.
     * @return true si la partie est finie, false sinon
     */
    private boolean estFinDePartie() {
        boolean tousPersonnagesMorts = true;
        for (Personnage p : m_donjon.getJoueurs()) {
            if (!p.estMort()) {
                tousPersonnagesMorts = false;
                break;
            }
        }
        if (tousPersonnagesMorts) {
            m_historiqueActions.append("L'intégralités de nos héros sont mort, les monstres ont " +
                    "triomphé de ceux-ci, le donjon restera ouvert pour que d'autres aventuriers malheureux " +
                    "y trouvent leur fin...\n");
        }
        // Vérifier si tous les monstres sont morts
        boolean tousMonstresMorts = true;
        for (Monstre m : m_donjon.getMonstres()) {
            if (!m.estMort()) {
                tousMonstresMorts = false;
                break;
            }
        }
        if (tousMonstresMorts){
            m_historiqueActions.append("Tous les monstres gisent vaincus ! Nos vaillants aventuriers ")
                    .append("ont triomphé des ténèbres qui hantaient ce donjon maudit. ")
                    .append("La lumière perce enfin l'obscurité, et les héros peuvent ")
                    .append("repartir, chargés de gloire et de trésors !\n");
        }
        // La partie est terminée si tous les personnages sont morts ou si tous les monstres sont morts
        return tousPersonnagesMorts || tousMonstresMorts;
    }

    // Getters
    /**
     * Retourne le numéro du tour actuel.
     * @return le numéro du tour
     */
    public int getNumeroTour() {
        return m_numeroTour;
    }

    /**
     * Retourne l'entité actuellement en train de jouer.
     * @return l'entité actuelle ou null si aucune
     */
    public ElementMobile getEntiteActuelle() {
        if (m_indexTourActuel >= 0 && m_indexTourActuel < m_donjon.getEntiteTour().size()) {
            return m_donjon.getEntiteTour().get(m_indexTourActuel);
        }
        return null;
    }

    /**
     * Vérifie si la partie est terminée.
     * @return true si la partie est finie, false sinon
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
     * Obtient la liste des monstres à portée d'un personnage.
     * @param personnage le personnage attaquant
     * @return la liste des monstres à portée
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

    /*============================Section Overrides============================*/

    @Override
    public String toString() {
        return "Tours : " +
                "numeroTour=" + m_numeroTour +
                ", indexTourActuel=" + m_indexTourActuel +
                ", donjon=" + (m_donjon != null ? m_donjon.toString() : "null");
    }
}