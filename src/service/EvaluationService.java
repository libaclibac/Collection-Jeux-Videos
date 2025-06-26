package src.service;

import src.database.DatabaseManager;
import src.metier.Evaluation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EvaluationService {
    private final NotificationService notificationService = new NotificationService();

    public boolean ajouterEvaluationPourJeu(Evaluation e, String titreJeu) {
        String sql = "INSERT OR REPLACE INTO evaluations (note, date_evaluation, utilisateur_id, jeu_id) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseManager.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, e.getNote());
            stmt.setString(2, e.getDateEvaluation());
            stmt.setInt(3, e.getUtilisateurId());
            stmt.setInt(4, e.getJeuId());
            stmt.executeUpdate();
            notificationService.notifierEvaluation(e.getUtilisateurId(), titreJeu, e.getNote());
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }

    public boolean modifierEvaluationPourJeu(int utilisateurId, int jeuId, int nouvelleNote) {
        String sql = "UPDATE evaluations SET note = ? WHERE utilisateur_id = ? AND jeu_id = ?";
        try (Connection conn = DatabaseManager.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, nouvelleNote);
            stmt.setInt(2, utilisateurId);
            stmt.setInt(3, jeuId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean supprimerEvaluationPourJeu(int utilisateurId, int jeuId) {
        String sql = "DELETE FROM evaluations WHERE utilisateur_id = ? AND jeu_id = ?";
        try (Connection conn = DatabaseManager.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, utilisateurId);
            stmt.setInt(2, jeuId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    public double calculerMoyennePourJeu(int jeuId) {
        String sql = "SELECT AVG(note) AS moyenne FROM evaluations WHERE jeu_id = ?";
        try (Connection conn = DatabaseManager.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, jeuId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getDouble("moyenne");
            }

        } catch (Exception e) {
            System.err.println("Erreur moyenne : " + e.getMessage());
        }
        return 0.0;
    }

    public void listerEvaluationsParJeu(int jeuId) {
    String sql = "SELECT * FROM evaluations WHERE jeu_id = ?";
    UtilisateurService utilisateurService = new UtilisateurService();

    try (Connection conn = DatabaseManager.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setInt(1, jeuId);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            int userId = rs.getInt("utilisateur_id");
            String pseudo = utilisateurService.getPseudoById(userId);
            int note = rs.getInt("note");

            System.out.println("- " + pseudo + " : " + note + "/5 ");
        }

    } catch (SQLException e) {
        System.err.println("Erreur lors de la récupération des évaluations : " + e.getMessage());
    }
}


}
