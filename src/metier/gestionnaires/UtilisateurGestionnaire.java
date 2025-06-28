package src.metier.gestionnaires;

import src.metier.Critique;
import src.metier.Evaluation;
import src.metier.Jeu;
import src.metier.Utilisateur;
import src.service.CritiqueService;
import src.service.EvaluationService;
import src.service.JeuService;

public class UtilisateurGestionnaire implements GestionnaireJeu {

    private final Utilisateur utilisateur;
    private final CritiqueService critiqueService = new CritiqueService();
    private final EvaluationService evaluationService = new EvaluationService();
    private final JeuService jeuService = new JeuService();

    public UtilisateurGestionnaire(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public boolean estCreateurDuJeu(Jeu jeu) {
        return jeu.getCreateurId() == utilisateur.getId();
    }

    public int getUtilisateurId() {
        return utilisateur.getId();
    }

    // Fonctionnalités de l'utilisateur
    @Override
    public void ajouterJeu(Jeu jeu) {
        jeu.setCreateurId(utilisateur.getId());
        boolean ok = jeuService.ajouterJeu(jeu);
        System.out.println(ok ? "Jeu ajouté avec succès." : "Erreur lors de l'ajout du jeu.");
    }

    @Override
    public void modifierJeu(Jeu jeu) {
        if (estCreateurDuJeu(jeu)) {
            boolean ok = jeuService.modifierJeu(jeu);
            System.out.println(ok ? "Jeu modifié avec succès." : "Échec de la modification.");
        } else {
            System.out.println("Vous n’êtes pas le créateur de ce jeu.");
        }
    }

    @Override
    public void supprimerJeu(Jeu jeu) {
        if (estCreateurDuJeu(jeu)) {
            boolean ok = jeuService.supprimerJeu(jeu.getId(), utilisateur.getId());
            System.out.println(ok ? "Jeu supprimé avec succès." : "Suppression impossible.");
        } else {
            System.out.println("Vous ne pouvez pas supprimer ce jeu.");
        }
    }

    @Override
    public void supprimerCritique(Critique critique) {
        if (critique.getUtilisateurId() == utilisateur.getId()) {
            boolean ok = critiqueService.supprimerCritiquePourJeu(utilisateur.getId(), critique.getJeuId());
            System.out.println(ok ? "Critique supprimée." : "Suppression impossible.");
        } else {
            System.out.println("Vous ne pouvez supprimer que vos critiques.");
        }
    }

    public void ajouterCritique(Critique critique, String titreJeu) {
        if (critique.getUtilisateurId() == utilisateur.getId()) {
            boolean ok = critiqueService.ajouterCritiquePourJeu(critique);
            System.out.println(ok ? "Critique ajoutée." : "Erreur critique.");
        } else {
            System.out.println("Vous ne pouvez ajouter une critique que pour vous-même.");
        }
    }

    public void modifierCritique(Critique critique, String nouveauTexte) {
        if (critique.getUtilisateurId() == utilisateur.getId()) {
            boolean ok = critiqueService.modifierCritiquePourJeu(utilisateur.getId(), critique.getJeuId(), nouveauTexte);
            System.out.println(ok ? "Critique modifiée." : "Modification impossible.");
        } else {
            System.out.println("Vous ne pouvez modifier que vos critiques.");
        }
    }

    public void ajouterEvaluation(Evaluation evaluation, String titre) {
        if (evaluation.getUtilisateurId() == utilisateur.getId()) {
            boolean ok = evaluationService.ajouterEvaluationPourJeu(evaluation, titre);
            System.out.println(ok ? "Évaluation ajoutée." : "Échec évaluation.");
        } else {
            System.out.println("Vous ne pouvez ajouter une évaluation que pour vous-même.");
        }
    }

    public void modifierEvaluation(Evaluation evaluation, int nouvelleNote) {
        if (evaluation.getUtilisateurId() == utilisateur.getId()) {
            boolean ok = evaluationService.modifierEvaluationPourJeu(utilisateur.getId(), evaluation.getJeuId(), nouvelleNote);
            System.out.println(ok ? "Évaluation modifiée." : "Échec de modification.");
        } else {
            System.out.println("Vous ne pouvez modifier que vos évaluations.");
        }
    }

    public void supprimerEvaluation(Evaluation evaluation) {
        if (evaluation.getUtilisateurId() == utilisateur.getId()) {
            boolean ok = evaluationService.supprimerEvaluationPourJeu(utilisateur.getId(), evaluation.getJeuId());
            System.out.println(ok ? "Évaluation supprimée." : "Suppression échouée.");
        } else {
            System.out.println("Vous ne pouvez supprimer que vos évaluations.");
        }
    }
}
