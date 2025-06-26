package src.controller;

import src.metier.Utilisateur;
import src.metier.gestionnaires.UtilisateurGestionnaire;
import src.service.AuthentificationService;
import src.service.UtilisateurService;

import java.util.Scanner;

public class AuthentificationController {
    private Utilisateur utilisateurConnecte = null;
    private UtilisateurGestionnaire gestionnaire = null;

    public Utilisateur getUtilisateurConnecte() {
        return utilisateurConnecte;
    }

    public UtilisateurGestionnaire getGestionnaire() {
        return gestionnaire;
    }

    public void creerCompte(Scanner scanner, UtilisateurService userService) {
        System.out.print("Pseudo : ");
        String pseudo = scanner.nextLine();
        System.out.print("Email : ");
        String email = scanner.nextLine();
        System.out.print("Mot de passe : ");
        String mdp = scanner.nextLine();
        boolean ok = userService.createUtilisateur(pseudo, email, mdp);
        System.out.println(ok ? "Utilisateur créé !" : "Erreur lors de la création.");
    }

    public void seConnecter(Scanner scanner, AuthentificationService auth) {
        System.out.print("Email : ");
        String email = scanner.nextLine();
        System.out.print("Mot de passe : ");
        String mdp = scanner.nextLine();
        utilisateurConnecte = auth.login(email, mdp);
        if (utilisateurConnecte != null) {
            gestionnaire = new UtilisateurGestionnaire(utilisateurConnecte);
            System.out.println("Bienvenue, " + utilisateurConnecte.getPseudo() + " !");
        } else {
            System.out.println("Email ou mot de passe incorrect.");
        }
    }

    public void seDeconnecter() {
        utilisateurConnecte = null;
        gestionnaire = null;
        System.out.println("Vous êtes maintenant déconnecté.");
    }
}
