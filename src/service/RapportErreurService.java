package src.service;

import src.database.DatabaseManager;
import src.metier.RapportErreur;
import src.metier.enums.StatusRapport;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RapportErreurService {
    private final NotificationService notificationService = new NotificationService();

    public boolean creerRapport(RapportErreur r, String titreJeu) {
        String sql = "INSERT INTO rapports_erreurs (description, date_signalement, utilisateur_id, jeu_id, statut) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseManager.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, r.getDescription());
            stmt.setString(2, r.getDateSignalement());
            stmt.setInt(3, r.getUtilisateurId());
            stmt.setInt(4, r.getJeuId());
            stmt.setString(5, r.getStatus().name());
            stmt.executeUpdate();
            notificationService.notifierRapportErreur(r.getUtilisateurId(), titreJeu);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean changerStatut(int idRapport, StatusRapport nouveauStatut, int utilisateurId, String titreJeu) {
        String sql = "UPDATE rapports_erreurs SET statut = ? WHERE id = ?";
        try (Connection conn = DatabaseManager.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nouveauStatut.name());
            stmt.setInt(2, idRapport);
            stmt.executeUpdate();
            notificationService.notifierRapportTraité(utilisateurId, titreJeu, nouveauStatut.name());
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

}
