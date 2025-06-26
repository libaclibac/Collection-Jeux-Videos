package src.metier;

public class Utilisateur {
    private int id;
    private String pseudo;
    private String email;
    private String motDePasse;

    // Constructeur
    public Utilisateur(int id, String pseudo, String email, String motDePasse) {
        this.id = id;
        this.pseudo = pseudo;
        this.email = email;
        this.motDePasse = motDePasse;
    }

    // Getters
    public int getId() { return id; }
    public String getPseudo() { return pseudo; }
    public String getEmail() { return email; }
    public String getMotDePasse() { return motDePasse; }
}
