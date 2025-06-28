package src;

import src.controller.AuthentificationController;
import src.controller.JeuxController;
import src.metier.gestionnaires.BotGestionnaire;
import src.service.*;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        src.database.DatabaseInit.createTables();
        Scanner scanner = new Scanner(System.in);

        // Services
        AuthentificationService auth = new AuthentificationService();
        UtilisateurService userService = new UtilisateurService();
        JeuService jeuService = new JeuService();
        StatistiquesService statistiquesService = new StatistiquesService();
        RapportErreurService rapportErreurService = new RapportErreurService();
        ListeDeSouhaitsService listeSouhaitsService = new ListeDeSouhaitsService();
        EvaluationService evaluationService = new EvaluationService();
        CritiqueService critiqueService = new CritiqueService();

        // Contrôleurs
        AuthentificationController authController = new AuthentificationController();
        JeuxController jeuxController = new JeuxController();

        userService.initBot();

        BotGestionnaire bot = new BotGestionnaire();
        bot.importerDepuisJSON("src/database/jeux.json");

        boolean running = true;
        while (running) {
            if (authController.getUtilisateurConnecte() == null) {
                System.out.println("\n===== Menu Principal =====");
                System.out.println("1. Créer un compte");
                System.out.println("2. Se connecter");
                System.out.println("0. Quitter");
                System.out.print("Choix : ");
                switch (scanner.nextLine()) {
                    case "1" -> authController.creerCompte(scanner, userService);
                    case "2" -> authController.seConnecter(scanner, auth);
                    case "0" -> running = false;
                    default -> System.out.println("Choix invalide.");
                }
            } else {
                System.out.println("\n===== Menu Utilisateur =====");
                System.out.println("1. Voir mes statistiques");
                System.out.println("2. Signaler une erreur");
                System.out.println("3. Rechercher un jeu");
                System.out.println("4. Gérer mes jeux");
                System.out.println("5. Voir ma liste de souhaits");
                System.out.println("6. Se déconnecter");
                System.out.println("0. Quitter");
                System.out.print("Choix : ");

                switch (scanner.nextLine()) {
                    case "1" -> jeuxController.afficherStatistiques(statistiquesService, listeSouhaitsService, authController.getUtilisateurConnecte());
                    case "2" -> jeuxController.signalerErreur(scanner, rapportErreurService, authController.getUtilisateurConnecte());
                    case "3" -> jeuxController.gererJeu(scanner, jeuService, userService, listeSouhaitsService, evaluationService, critiqueService,
                            authController.getUtilisateurConnecte(), authController.getGestionnaire());
                    case "4" -> jeuxController.gererMesJeux(scanner, jeuService, authController.getUtilisateurConnecte(), authController.getGestionnaire());
                    case "5" -> jeuxController.afficherListeSouhaits(listeSouhaitsService, authController.getUtilisateurConnecte());
                    case "6" -> authController.seDeconnecter();
                    case "0" -> running = false;
                    default -> System.out.println("Choix invalide.");
                }
            }
        }

        scanner.close();
        System.out.println("A bientôt !");
    }
}
