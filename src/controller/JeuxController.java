package src.controller;

import src.metier.*;
import src.metier.enums.StatusRapport;
import src.metier.gestionnaires.UtilisateurGestionnaire;
import src.service.*;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class JeuxController {

    public void afficherStatistiques(StatistiquesService statistiquesService, ListeDeSouhaitsService souhaitsService, Utilisateur utilisateurConnecte) {
        System.out.println("\n===== Statistiques =====");
        System.out.println("Pseudo: " + utilisateurConnecte.getPseudo());
        System.out.println("Email: " + utilisateurConnecte.getEmail());
        System.out.println("Statistiques des genres des jeux dans votre liste de souhaits :");

        List<Jeu> jeux = souhaitsService.listerJeuxSouhaitsUtilisateur(utilisateurConnecte.getId());

        if (jeux.isEmpty()) {
            System.out.println("Aucun jeu dans la liste de souhaits.");
            return;
        }

        Map<String, Integer> statsGenres = Statistiques.calculerGenres(jeux);
        Statistiques.afficherPourcentages(statsGenres);
    }

    public void signalerErreur(Scanner scanner, RapportErreurService rapportErreurService, Utilisateur utilisateurConnecte) {
        System.out.print("Description de l'erreur : ");
        String description = scanner.nextLine();
        String date = java.time.LocalDate.now().toString();
        RapportErreur rapport = new RapportErreur(0, description, date, utilisateurConnecte.getId(), 0, StatusRapport.EN_ATTENTE);
        boolean ok = rapportErreurService.creerRapport(rapport, "Jeu non spécifié");
        System.out.println(ok ? "Rapport envoyé." : "Échec de l'envoi.");
    }

    public void afficherListeSouhaits(ListeDeSouhaitsService service, Utilisateur utilisateurConnecte) {
        var jeux = service.listerTitresSouhaitsUtilisateur(utilisateurConnecte.getId());
        if (jeux.isEmpty()) System.out.println("Aucun jeu dans votre liste.");
        else jeux.forEach(titre -> System.out.println("- " + titre));
    }

    public void gererJeu(Scanner scanner, JeuService jeuService, UtilisateurService userService,
                         ListeDeSouhaitsService listeService, EvaluationService evalService,
                         CritiqueService critiqueService, Utilisateur utilisateurConnecte,
                         UtilisateurGestionnaire gestionnaire) {

        System.out.print("Titre du jeu : ");
        String titre = scanner.nextLine();
        Jeu jeu = jeuService.rechercherJeuParTitre(titre);
        if (jeu == null) {
            System.out.println("Jeu non trouvé.");
            return;
        }
        System.out.println("===== Détails du jeu =====");
        System.out.println("Titre : " + jeu.getTitre());
        System.out.println("Description : " + jeu.getDescription());
        System.out.println("Genre : " + jeu.getGenre());
        System.out.println("Plateforme : " + jeu.getPlateforme());
        System.out.println("Date de sortie : " + jeu.getDateSortie());
        System.out.println("Éditeur : " + jeu.getEditeur());
        System.out.println("Ajouté par : " + userService.getPseudoById(jeu.getCreateurId()));
        System.out.println("Critiques :");
        critiqueService.listerCritiquesParJeu(jeu.getId());
        System.out.println("Moyenne des évaluations : " + evalService.calculerMoyennePourJeu(jeu.getId()));
        System.out.println("Évaluations :");
        evalService.listerEvaluationsParJeu(jeu.getId());

        boolean sousMenuActif = true;
        while (sousMenuActif) {
            System.out.println("\n===== Actions sur le jeu \"" + jeu.getTitre() + "\" =====");
            System.out.println("1. Ajouter à la liste de souhaits");
            System.out.println("2. Retirer de la liste de souhaits");
            System.out.println("3. Ajouter une critique");
            System.out.println("4. Modifier ma critique");
            System.out.println("5. Supprimer ma critique");
            System.out.println("6. Ajouter une évaluation");
            System.out.println("7. Modifier mon évaluation");
            System.out.println("8. Supprimer mon évaluation");
            System.out.println("0. Retour");
            System.out.print("Choix : ");
            String choix = scanner.nextLine();

            switch (choix) {
                case "1" -> listeService.ajouterJeuALaListe(utilisateurConnecte.getId(), jeu.getId());
                case "2" -> listeService.retirerJeuDeLaListe(utilisateurConnecte.getId(), jeu.getId());
                case "3" -> {
                    System.out.print("Votre critique : ");
                    String texte = scanner.nextLine();
                    Critique c = new Critique(utilisateurConnecte.getId(), jeu.getId(), texte);
                    gestionnaire.ajouterCritique(c, jeu.getTitre());
                }
                case "4" -> {
                    System.out.print("Nouveau texte : ");
                    String texte = scanner.nextLine();
                    Critique c = new Critique(utilisateurConnecte.getId(), jeu.getId(), texte);
                    gestionnaire.modifierCritique(c, texte);
                }
                case "5" -> gestionnaire.supprimerCritique(new Critique(utilisateurConnecte.getId(), jeu.getId(), ""));
                case "6" -> {
                    System.out.print("Note (0-5) : ");
                    int note = Integer.parseInt(scanner.nextLine());
                    Evaluation e = new Evaluation(utilisateurConnecte.getId(), jeu.getId(), note);
                    gestionnaire.ajouterEvaluation(e, jeu.getTitre());
                }
                case "7" -> {
                    System.out.print("Nouvelle note : ");
                    int note = Integer.parseInt(scanner.nextLine());
                    Evaluation e = new Evaluation(utilisateurConnecte.getId(), jeu.getId(), 0);
                    gestionnaire.modifierEvaluation(e, note);
                }
                case "8" -> gestionnaire.supprimerEvaluation(new Evaluation(utilisateurConnecte.getId(), jeu.getId(), 0));
                case "0" -> sousMenuActif = false;
                default -> System.out.println("Choix invalide.");
            }
        }
    }

    public void gererMesJeux(Scanner scanner, JeuService jeuService, Utilisateur utilisateurConnecte, UtilisateurGestionnaire gestionnaire) {
        System.out.println("\n===== Gestion de mes Jeux =====");
        System.out.println("1. Ajouter un jeu");
        System.out.println("2. Modifier un jeu");
        System.out.println("3. Supprimer un jeu");
        System.out.print("Choix : ");
        String choix = scanner.nextLine();

        switch (choix) {
            case "1" -> {
                System.out.print("Titre : ");
                String titre = scanner.nextLine();

                // Vérifie si le jeu existe déjà
                if (jeuService.existeJeu(titre)) {
                    System.out.println("Ce jeu existe déjà !");
                    break;
                }

                System.out.print("Description : ");
                String description = scanner.nextLine();
                System.out.print("Plateforme : ");
                String plateforme = scanner.nextLine();
                System.out.print("Genre : ");
                String genre = scanner.nextLine();
                System.out.print("Date de sortie (YYYY-MM-DD) : ");
                String dateSortie = scanner.nextLine();
                System.out.print("Éditeur : ");
                String editeur = scanner.nextLine();

                Jeu nouveauJeu = new Jeu();
                nouveauJeu.setTitre(titre);
                nouveauJeu.setDescription(description);
                nouveauJeu.setPlateforme(plateforme);
                nouveauJeu.setGenre(genre);
                nouveauJeu.setDateSortie(dateSortie);
                nouveauJeu.setEditeur(editeur);
                gestionnaire.ajouterJeu(nouveauJeu);
            }
            case "2" -> {
                System.out.print("Titre du jeu à modifier : ");
                String titre = scanner.nextLine();
                Jeu jeu = jeuService.rechercherJeuParTitre(titre);
                if (jeu != null && gestionnaire.estCreateurDuJeu(jeu)) {
                    System.out.print("Nouveau titre (laisser vide pour ne pas changer) : ");
                    String nouveauTitre = scanner.nextLine();
                    System.out.print("Nouvelle description : ");
                    String description = scanner.nextLine();
                    System.out.print("Nouvelle plateforme : ");
                    String plateforme = scanner.nextLine();
                    System.out.print("Nouveau genre : ");
                    String genre = scanner.nextLine();
                    System.out.print("Nouvelle date de sortie : ");
                    String dateSortie = scanner.nextLine();
                    System.out.print("Nouvel éditeur : ");
                    String editeur = scanner.nextLine();

                    if (!nouveauTitre.isEmpty()) jeu.setTitre(nouveauTitre);
                    if (!description.isEmpty()) jeu.setDescription(description);
                    if (!plateforme.isEmpty()) jeu.setPlateforme(plateforme);
                    if (!genre.isEmpty()) jeu.setGenre(genre);
                    if (!dateSortie.isEmpty()) jeu.setDateSortie(dateSortie);
                    if (!editeur.isEmpty()) jeu.setEditeur(editeur);

                    gestionnaire.modifierJeu(jeu);
                } else {
                    System.out.println("Jeu introuvable ou non autorisé.");
                }
            }
            case "3" -> {
                System.out.print("Titre du jeu à supprimer : ");
                String titre = scanner.nextLine();
                Jeu jeu = jeuService.rechercherJeuParTitre(titre);
                if (jeu != null && gestionnaire.estCreateurDuJeu(jeu)) {
                    gestionnaire.supprimerJeu(jeu);
                } else {
                    System.out.println("Jeu introuvable ou non autorisé.");
                }
            }
            default -> System.out.println("Choix invalide.");
        }
    }
}
