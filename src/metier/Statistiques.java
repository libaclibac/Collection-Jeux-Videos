package src.metier;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Statistiques {

    public static Map<String, Integer> calculerGenresDepuisStrings(List<String> genresList) {
        Map<String, Integer> stats = new HashMap<>();

        for (String genreChaine : genresList) {
            String[] genres = genreChaine.split(",");
            for (String genre : genres) {
                genre = genre.trim().toLowerCase();
                stats.put(genre, stats.getOrDefault(genre, 0) + 1);
            }
        }

        return stats;
}

    public static void afficherPourcentages(Map<String, Integer> stats) {
        int totalGenres = stats.values().stream().mapToInt(Integer::intValue).sum();

        stats.entrySet().stream()
            .sorted(Map.Entry.<String, Integer>comparingByValue().reversed()) // tri optionnel
            .forEach(entry -> {
                double pourcentage = (entry.getValue() * 100.0) / totalGenres;
                System.out.printf("%-15s : %.2f %%\n", entry.getKey(), pourcentage);
            });
    }
}
