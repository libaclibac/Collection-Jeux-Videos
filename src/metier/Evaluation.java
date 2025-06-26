package src.metier;

public class Evaluation {
    private int id;
    private int note;
    private String dateEvaluation;

    private int utilisateurId;
    private int jeuId;

    // public Evaluation(int id, int note, String dateEvaluation, int utilisateurId, int jeuId) {
    //     this.id = id;
    //     this.note = note;
    //     this.dateEvaluation = dateEvaluation;
    //     this.utilisateurId = utilisateurId;
    //     this.jeuId = jeuId;
    // }

    // Constructeur
    public Evaluation(int utilisateurId, int jeuId, int note) {
        this.utilisateurId = utilisateurId;
        this.jeuId = jeuId;
        this.note = note;
    }
    // Getters
    public int getId() { return id; }
    public int getNote() { return note; }
    public String getDateEvaluation() { return dateEvaluation; }
    public int getUtilisateurId() { return utilisateurId; }
    public int getJeuId() { return jeuId; }

    // Setters si jamais tu veux en ajouter dans les services
    public void setNote(int note) { this.note = note; }
    public void setDateEvaluation(String dateEvaluation) { this.dateEvaluation = dateEvaluation; }
}
