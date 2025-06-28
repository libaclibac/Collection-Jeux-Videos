package src.service;

import src.database.DatabaseManager;
import src.utils.SecurityUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UtilisateurService {

    public boolean createUtilisateur(String pseudo, String email, String motDePasse) {
        String sql = "INSERT INTO utilisateurs (pseudo, email, mot_de_passe) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, pseudo);
            stmt.setString(2, email);
            stmt.setString(3, SecurityUtils.hashPassword(motDePasse));

            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.err.println("Erreur lors de la création de l'utilisateur : " + e.getMessage());
            return false;
        }
    }

    // Pas utilisé dans l'application
    public boolean deleteUtilisateur(String pseudo) {
        String sql = "DELETE FROM utilisateurs WHERE pseudo = ?";
        
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, pseudo);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression de l'utilisateur : " + e.getMessage());
            return false;
        }
    }

    public String getPseudoById(int id) {
        String pseudo = null;
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT pseudo FROM utilisateurs WHERE id = ?")) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                pseudo = rs.getString("pseudo");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pseudo != null ? pseudo : "Inconnu";
    }

    public void initBot() {
        String checkSql = "SELECT id FROM utilisateurs WHERE id = 0";
        String insertSql = "INSERT INTO utilisateurs (id, pseudo, email, mot_de_passe) VALUES (0, 'Bot', '', '')";

        try (Connection conn = DatabaseManager.getConnection();
            PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {

            ResultSet rs = checkStmt.executeQuery();
            if (!rs.next()) { // Si aucun utilisateur avec id = 0
                try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                    insertStmt.executeUpdate();
                    System.out.println("Utilisateur 'Bot' initialisé dans la base.");
                }
            } else {
                System.out.println("Utilisateur 'Bot' déjà présent.");
            }

        } catch (SQLException e) {
            System.err.println("Erreur lors de l'initialisation du bot : " + e.getMessage());
        }
    }
}
