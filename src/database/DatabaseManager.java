package src.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {
    private static final String DB_URL = "jdbc:sqlite:src/database/database.db"; // nom du fichier SQLite

    static {
        try {
            Class.forName("org.sqlite.JDBC"); // Charge le driver
        } catch (ClassNotFoundException e) {
            System.err.println("Erreur de chargement du driver SQLite : " + e.getMessage());
        }
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(DB_URL);
        } catch (SQLException e) {
            System.err.println("Erreur de connexion à la base de données : " + e.getMessage());
            return null;
        }
    }
}
