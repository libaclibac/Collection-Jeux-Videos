package src.metier.gestionnaires;

import src.metier.Critique;
import src.metier.Jeu;

// Fonctionnalités utilisées par l'utilisateur et le bot mais pas la même implementation
public interface GestionnaireJeu {
    void ajouterJeu(Jeu jeu);
    void modifierJeu(Jeu jeu);
    void supprimerJeu(Jeu jeu);
    void supprimerCritique(Critique critique);
}
