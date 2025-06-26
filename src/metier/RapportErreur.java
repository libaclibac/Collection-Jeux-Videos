package src.metier;

import src.metier.enums.StatusRapport;

public class RapportErreur {
    private int id;
    private String description;
    private String dateSignalement;
    private int utilisateurId;
    private int jeuId;
    private StatusRapport status;

    // Constructeur
    public RapportErreur(int id, String description, String dateSignalement,
                         int utilisateurId, int jeuId, StatusRapport status) {
        this.id = id;
        this.description = description;
        this.dateSignalement = dateSignalement;
        this.utilisateurId = utilisateurId;
        this.jeuId = jeuId;
        this.status = status;
    }

    public void changerStatus(StatusRapport nouveauStatus) {
        this.status = nouveauStatus;
    }

    // Getters
    public int getId() { return id; }
    public String getDescription() { return description; }
    public String getDateSignalement() { return dateSignalement; }
    public int getUtilisateurId() { return utilisateurId; }
    public int getJeuId() { return jeuId; }
    public StatusRapport getStatus() { return status; }

    // Setters
    public void setDescription(String description) { this.description = description; }
}
