package src.metier;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Statistiques {

    // Cette méthode calcule les statistiques de genre de jeux
    public static Map<String, Integer> calculerGenres(List<Jeu> jeux) {
        Map<String, Integer> stats = new HashMap<>();

        for (Jeu jeu : jeux) {
            String[] genres = jeu.getGenre().split(",");
            for (String genre : genres) {
                genre = genre.trim().toLowerCase();
                stats.put(genre, stats.getOrDefault(genre, 0) + 1);
            }
        }

        return stats;
    }

    public static void afficherPourcentages(Map<String, Integer> stats) {
        int total = stats.values().stream().mapToInt(Integer::intValue).sum();

        for (Map.Entry<String, Integer> entry : stats.entrySet()) {
            double pourcentage = (entry.getValue() * 100.0) / total;
            System.out.printf("%s : %.2f %%\n", entry.getKey(), pourcentage);
        }
    }
}
