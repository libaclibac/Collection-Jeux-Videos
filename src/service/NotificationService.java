package src.service;

import src.database.DatabaseManager;
import src.metier.Notification;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class NotificationService {

    private String getTimestamp() {
        return LocalDateTime.now().toString();
    }

    private boolean enregistrerNotification(int destinataireId, String message) {
        String sql = "INSERT INTO notifications (message, date_envoi, destinataire_id) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, message);
            stmt.setString(2, getTimestamp());
            stmt.setInt(3, destinataireId);

            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            return false;
        }
    }

    // Critique
    public boolean notifierCritique(int userId, String titreJeu) {
        String msg = "Vous avez rédigé une critique sur le jeu \"" + titreJeu + "\".";
        return enregistrerNotification(userId, msg);
    }

    // Évaluation
    public boolean notifierEvaluation(int userId, String titreJeu, int note) {
        String msg = "Vous avez donné une note de " + note + " au jeu \"" + titreJeu + "\".";
        return enregistrerNotification(userId, msg);
    }

    // Rapport d'erreur
    public boolean notifierRapportErreur(int userId, String titreJeu) {
        String msg = "Votre rapport d’erreur pour \"" + titreJeu + "\" a bien été envoyé.";
        return enregistrerNotification(userId, msg);
    }

    // Rapport traité
    public boolean notifierRapportTraité(int userId, String titreJeu, String nouveauStatut) {
        String msg = "Votre rapport sur \"" + titreJeu + "\" est maintenant : " + nouveauStatut + ".";
        return enregistrerNotification(userId, msg);
    }

    // Nouveau jeu ajouté – tous les utilisateurs
    public boolean notifierTousLesUtilisateurs(String titreJeu) {
        String sql = "SELECT id FROM utilisateurs";
        boolean allNotified = true;

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String msg = "Un nouveau jeu a été ajouté : \"" + titreJeu + "\".";
                boolean ok = enregistrerNotification(id, msg);
                if (!ok) allNotified = false;
            }

        } catch (SQLException e) {
            allNotified = false;
        }

        return allNotified;
    }

    // Lecture des notifications d’un utilisateur
    public List<Notification> getNotifications(int utilisateurId) {
        List<Notification> notifications = new ArrayList<>();
        String sql = "SELECT * FROM notifications WHERE destinataire_id = ? ORDER BY date_envoi DESC";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, utilisateurId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Notification notif = new Notification(
                        rs.getInt("id"),
                        rs.getString("message"),
                        rs.getString("date_envoi"),
                        rs.getInt("destinataire_id")
                );
                notifications.add(notif);
            }

        } catch (SQLException e) {
            // En cas d'erreur, la liste restera vide
        }

        return notifications;
    }
    
    public void afficherNotifications(int utilisateurId) {
        List<Notification> notifications = getNotifications(utilisateurId);
        if (notifications.isEmpty()) {
            System.out.println("Aucune notification.");
        } else {
            for (Notification notif : notifications) {
                System.out.printf(" %s | %s\n", notif.getDateEnvoi(), notif.getMessage());
            }
        }
    }
}
