package src.service;

import src.database.DatabaseManager;
import src.metier.Utilisateur;
import src.utils.SecurityUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthentificationService {

    public Utilisateur login(String email, String motDePasse) {
        String sql = "SELECT * FROM utilisateurs WHERE email = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String mdpStocke = rs.getString("mot_de_passe");

                if (SecurityUtils.hashPassword(motDePasse).equals(mdpStocke)) {
                    // Création de l'objet Utilisateur
                    Utilisateur u = new Utilisateur(
                        rs.getInt("id"),
                        rs.getString("pseudo"),
                        rs.getString("email"),
                        rs.getString("mot_de_passe")
                    );
                    return u;
                }
            }

        } catch (SQLException e) {
            System.err.println("Erreur lors de l'authentification : " + e.getMessage());
        }

        return null; // connexion échouée
    }
}
