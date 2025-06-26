package src.metier;

public class Notification {
    private int id;
    private String message;
    private String dateEnvoi;
    private int destinataireId;

    // Constructeur
    public Notification(int id, String message, String dateEnvoi, int destinataireId) {
        this.id = id;
        this.message = message;
        this.dateEnvoi = dateEnvoi;
        this.destinataireId = destinataireId;
    }

    public void envoyer() {
        System.out.println("Notification envoyée à l'utilisateur " + destinataireId + " : " + message);
    }

    // Getters
    public int getId() { return id; }
    public String getMessage() { return message; }
    public String getDateEnvoi() { return dateEnvoi; }
    public int getDestinataireId() { return destinataireId; }

    // Setters si besoin
    public void setMessage(String message) { this.message = message; }
    public void setDateEnvoi(String dateEnvoi) { this.dateEnvoi = dateEnvoi; }
}
