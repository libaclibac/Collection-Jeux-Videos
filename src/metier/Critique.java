package src.metier;

public class Critique {
    private int id;
    private String texte;
    private String dateCritique;

    private int utilisateurId;
    private int jeuId;

    // public Critique(int id, String texte, String dateCritique, int utilisateurId, int jeuId) {
    //     this.id = id;
    //     this.texte = texte;
    //     this.dateCritique = dateCritique;
    //     this.utilisateurId = utilisateurId;
    //     this.jeuId = jeuId;
    // }

    // Constructeur
    public Critique(int utilisateurId, int jeuId, String texte) {
        this.utilisateurId = utilisateurId;
        this.jeuId = jeuId;
        this.texte = texte;
    }

    // Getters
    public int getId() { return id; }
    public String getTexte() { return texte; }
    public String getDateCritique() { return dateCritique; }
    public int getUtilisateurId() { return utilisateurId; }
    public int getJeuId() { return jeuId; }

    // Setters si nécessaires
    public void setTexte(String texte) { this.texte = texte; }
}
