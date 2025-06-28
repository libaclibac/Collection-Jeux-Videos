package src.service;

import src.database.DatabaseManager;
import src.metier.Jeu;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JeuService {

    public boolean ajouterJeu(Jeu jeu) {
        String sql = """
            INSERT INTO jeux (titre, description, plateforme, genre, date_sortie, editeur, createur_id)
            VALUES (?, ?, ?, ?, ?, ?, ?)
        """;
        try (Connection conn = DatabaseManager.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, jeu.getTitre());
            stmt.setString(2, jeu.getDescription());
            stmt.setString(3, jeu.getPlateforme());
            stmt.setString(4, jeu.getGenre());
            stmt.setString(5, jeu.getDateSortie());
            stmt.setString(6, jeu.getEditeur());
            stmt.setInt(7, jeu.getCreateurId());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean modifierJeu(Jeu jeu) {
        String sql = """
            UPDATE jeux
            SET titre = ?, description = ?, plateforme = ?, genre = ?, date_sortie = ?, editeur = ?
            WHERE id = ? AND createur_id = ?
        """;
        try (Connection conn = DatabaseManager.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, jeu.getTitre());
            stmt.setString(2, jeu.getDescription());
            stmt.setString(3, jeu.getPlateforme());
            stmt.setString(4, jeu.getGenre());
            stmt.setString(5, jeu.getDateSortie());
            stmt.setString(6, jeu.getEditeur());
            stmt.setInt(7, jeu.getId());
            stmt.setInt(8, jeu.getCreateurId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean supprimerJeu(int jeuId, int createurId) {
        String sql = "DELETE FROM jeux WHERE id = ? AND createur_id = ?";
        try (Connection conn = DatabaseManager.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, jeuId);
            stmt.setInt(2, createurId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }


    public Jeu rechercherJeuParTitre(String titre) {
        String sql = "SELECT * FROM jeux WHERE titre = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, titre);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Jeu(
                    rs.getInt("id"),
                    rs.getString("titre"),
                    rs.getString("description"),
                    rs.getString("plateforme"),
                    rs.getString("genre"),
                    rs.getString("date_sortie"),
                    rs.getString("editeur"),
                    rs.getInt("createur_id")
                );
            }
        } catch (Exception e) {
            System.err.println("Erreur recherche jeu : " + e.getMessage());
        }
        return null;
    }

    public boolean existeJeu(String titre, String plateforme) {
        String sql = "SELECT COUNT(*) FROM jeux WHERE titre = ? AND plateforme = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, titre);
            stmt.setString(2, plateforme);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.err.println("Erreur vérification existence jeu : " + e.getMessage());
        }
        return false;
    }

    public boolean existeJeu(String titre) {
        String sql = "SELECT COUNT(*) FROM jeux WHERE titre = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, titre);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.err.println("Erreur vérification existence jeu : " + e.getMessage());
        }
        return false;
    }
}
