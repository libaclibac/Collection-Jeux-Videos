package src.service;

import src.database.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

public class StatistiquesService {

    public Map<String, Integer> calculerGenres(int utilisateurId) {
        Map<String, Integer> genreStats = new HashMap<>();

        String sql = "SELECT genre, COUNT(*) as total FROM jeux WHERE createur_id = ? GROUP BY genre";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, utilisateurId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                genreStats.put(rs.getString("genre"), rs.getInt("total"));
            }

        } catch (Exception e) {
            System.err.println("Erreur calcul statistiques : " + e.getMessage());
        }

        return genreStats;
    }

    public void afficherPourcentages(Map<String, Integer> stats) {
        int total = stats.values().stream().mapToInt(Integer::intValue).sum();

        if (total == 0) {
            System.out.println("Aucune donnée disponible.");
            return;
        }

        for (Map.Entry<String, Integer> entry : stats.entrySet()) {
            double pourcentage = (entry.getValue() * 100.0) / total;
            System.out.printf("%s : %.2f %%\n", entry.getKey(), pourcentage);
        }
    }
}
