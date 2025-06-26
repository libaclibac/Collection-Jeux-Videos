package src.metier.gestionnaires;
import src.metier.Critique;
import src.metier.Jeu;

public class BotGestionnaire implements GestionnaireJeu{

	// Ajouter des jeux par lui même, va chercher les jeux sur internet ou autre source
	@Override
	public void ajouterJeu(Jeu jeu) {
		// TODO: implement method
	}

	@Override
	public void supprimerJeu(Jeu jeu) {
		// TODO: implement method
	}

	// Faire des recherches internet ou autre pour modifier les données des jeux si besoin
	@Override
	public void modifierJeu(Jeu jeu) {
		// TODO: implement method
	}

	// filtrer les critiques insultantes ou inappropriées
	@Override
	public void supprimerCritique(Critique critique) {
		// TODO: implement method
	}
}
