package src.service;

import src.database.DatabaseManager;
import src.metier.Critique;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CritiqueService {

    public boolean ajouterCritiquePourJeu(Critique c) {
        String sql = "INSERT OR REPLACE INTO critiques (texte, date_critique, utilisateur_id, jeu_id) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseManager.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, c.getTexte());
            stmt.setString(2, c.getDateCritique());
            stmt.setInt(3, c.getUtilisateurId());
            stmt.setInt(4, c.getJeuId());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean modifierCritiquePourJeu(int utilisateurId, int jeuId, String nouveauTexte) {
        String sql = "UPDATE critiques SET texte = ? WHERE utilisateur_id = ? AND jeu_id = ?";
        try (Connection conn = DatabaseManager.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nouveauTexte);
            stmt.setInt(2, utilisateurId);
            stmt.setInt(3, jeuId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean supprimerCritiquePourJeu(int utilisateurId, int jeuId) {
        String sql = "DELETE FROM critiques WHERE utilisateur_id = ? AND jeu_id = ?";
        try (Connection conn = DatabaseManager.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, utilisateurId);
            stmt.setInt(2, jeuId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    public void listerCritiquesParJeu(int jeuId) {
        String sql = "SELECT * FROM critiques WHERE jeu_id = ?";
        UtilisateurService utilisateurService = new UtilisateurService();

        try (Connection conn = DatabaseManager.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, jeuId);
            var rs = stmt.executeQuery();

            while (rs.next()) {
                int userId = rs.getInt("utilisateur_id");
                String pseudo = utilisateurService.getPseudoById(userId);
                String texte = rs.getString("texte");
                System.out.println("- " + pseudo + " : " + texte);
            }

        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des critiques : " + e.getMessage());
        }
    }
}
