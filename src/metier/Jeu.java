package src.metier;

import java.util.ArrayList;
import java.util.List;

public class Jeu {
    private int id;
    private String titre;
    private String description;
    private String plateforme;
    private String genre;
    private String dateSortie;
    private String editeur;
    private int createurId; // ID de l'utilisateur qui a ajouté le jeu

    // Relations
    private List<Evaluation> evaluations;
    private List<Critique> critiques;

    // Constructeurs
    public Jeu(int id, String titre, String description, String plateforme, String genre,
               String dateSortie, String editeur, int createurId) {
        this.id = id;
        this.titre = titre;
        this.description = description;
        this.plateforme = plateforme;
        this.genre = genre;
        this.dateSortie = dateSortie;
        this.editeur = editeur;
        this.createurId = createurId;

        this.evaluations = new ArrayList<>();
        this.critiques = new ArrayList<>();
    }

    public Jeu() {
        this.evaluations = new ArrayList<>();
        this.critiques = new ArrayList<>();
    }

    // Getters & Setters
    public int getId() { return id; }
    public String getTitre() { return titre; }
    public String getDescription() { return description; }
    public String getPlateforme() { return plateforme; }
    public String getGenre() { return genre; }
    public String getDateSortie() { return dateSortie; }
    public String getEditeur() { return editeur; }
    public int getCreateurId() { return createurId; }

    public List<Evaluation> getEvaluations() { return evaluations; }
    public List<Critique> getCritiques() { return critiques; }

    public void setId(int id) { this.id = id; }
    public void setTitre(String titre) { this.titre = titre; }
    public void setDescription(String description) { this.description = description; }
    public void setPlateforme(String plateforme) { this.plateforme = plateforme; }
    public void setGenre(String genre) { this.genre = genre; }
    public void setDateSortie(String dateSortie) { this.dateSortie = dateSortie; }
    public void setEditeur(String editeur) { this.editeur = editeur; }
    public void setCreateurId(int createurId) { this.createurId = createurId; }
}
