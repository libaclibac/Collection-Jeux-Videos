package src.metier;

import java.util.List;
import src.service.ListeDeSouhaitsService;

public class ListeDeSouhaits {
    private final Utilisateur proprietaire;
    private final ListeDeSouhaitsService service;

    public ListeDeSouhaits(Utilisateur proprietaire) {
        this.proprietaire = proprietaire;
        this.service = new ListeDeSouhaitsService();
    }

    public Utilisateur getProprietaire() {
        return proprietaire;
    }

    public void ajouterJeu(Jeu jeu, Utilisateur utilisateur) {
        if (utilisateur.getId() == proprietaire.getId()) {
            service.ajouterJeuALaListe(proprietaire.getId(), jeu.getId());
        } else {
            throw new SecurityException("Vous n'êtes pas autorisé à modifier cette liste de souhaits.");
        }
    }

    public void supprimerJeu(Jeu jeu, Utilisateur utilisateur) {
        if (utilisateur.getId() == proprietaire.getId()) {
            service.retirerJeuDeLaListe(proprietaire.getId(), jeu.getId());
        } else {
            throw new SecurityException("Vous n'êtes pas autorisé à modifier cette liste de souhaits.");
        }
    }

    // Consultation ouverte à tous
    public List<String> listerTitresJeux() {
        return service.listerTitresSouhaitsUtilisateur(proprietaire.getId());
    }
}
