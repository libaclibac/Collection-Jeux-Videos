package src.service;

import src.database.DatabaseManager;
import src.metier.Jeu;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ListeDeSouhaitsService {

    // Ajouter un jeu à la liste de souhaits d’un utilisateur
    public void ajouterJeuALaListe(int utilisateurId, int jeuId) {
        String sql = "INSERT OR IGNORE INTO liste_souhaits (utilisateur_id, jeu_id) VALUES (?, ?)";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, utilisateurId);
            stmt.setInt(2, jeuId);
            stmt.executeUpdate();
            System.out.println("Jeu ajouté à la liste de souhaits.");

        } catch (SQLException e) {
            System.err.println("Erreur ajout souhait : " + e.getMessage());
        }
    }

    // Supprimer un jeu de la liste
    public void retirerJeuDeLaListe(int utilisateurId, int jeuId) {
        String sql = "DELETE FROM liste_souhaits WHERE utilisateur_id = ? AND jeu_id = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, utilisateurId);
            stmt.setInt(2, jeuId);
            stmt.executeUpdate();
            System.out.println("🗑️ Jeu retiré de la liste de souhaits.");

        } catch (SQLException e) {
            System.err.println("Erreur retrait souhait : " + e.getMessage());
        }
    }

    // Lister tous les jeux de la liste de souhaits de l’utilisateur
    public List<String> listerTitresSouhaitsUtilisateur(int utilisateurId) {
        List<String> titres = new ArrayList<>();
        String sql = """
            SELECT j.titre FROM jeux j
            INNER JOIN liste_souhaits l ON l.jeu_id = j.id
            WHERE l.utilisateur_id = ?
        """;

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, utilisateurId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                titres.add(rs.getString("titre"));
            }

        } catch (SQLException e) {
            System.err.println("Erreur récupération titres souhaits : " + e.getMessage());
        }

        return titres;
    }

    public List<Jeu> listerJeuxSouhaitsUtilisateur(int utilisateurId) {
        List<Jeu> jeux = new ArrayList<>();
        String sql = """
            SELECT j.* FROM jeux j
            INNER JOIN liste_souhaits l ON l.jeu_id = j.id
            WHERE l.utilisateur_id = ?
        """;

        try (Connection conn = DatabaseManager.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, utilisateurId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Jeu jeu = new Jeu();
                jeu.setId(rs.getInt("id"));
                jeu.setTitre(rs.getString("titre"));
                jeu.setDescription(rs.getString("description"));
                jeu.setPlateforme(rs.getString("plateforme"));
                jeu.setGenre(rs.getString("genre"));
                jeu.setDateSortie(rs.getString("date_sortie"));
                jeu.setEditeur(rs.getString("editeur"));
                jeu.setCreateurId(rs.getInt("createur_id"));
                jeux.add(jeu);
            }

        } catch (SQLException e) {
            System.err.println("Erreur récupération jeux souhaits : " + e.getMessage());
        }

        return jeux;
    }

}
