package src.database;

import java.sql.Connection;
import java.sql.Statement;

public class DatabaseInit {
    public static void createTables() {
        String sql = """
        CREATE TABLE IF NOT EXISTS utilisateurs (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            pseudo TEXT NOT NULL,
            email TEXT UNIQUE NOT NULL,
            mot_de_passe TEXT NOT NULL
        );

        CREATE TABLE IF NOT EXISTS jeux (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            titre TEXT NOT NULL,
            description TEXT,
            plateforme TEXT,
            genre TEXT,
            date_sortie TEXT,
            editeur TEXT,
            createur_id INTEGER,
            FOREIGN KEY (createur_id) REFERENCES utilisateurs(id)
        );

        CREATE TABLE IF NOT EXISTS evaluations (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            note INTEGER NOT NULL,
            date_evaluation TEXT,
            utilisateur_id INTEGER,
            jeu_id INTEGER,
            UNIQUE(utilisateur_id, jeu_id),
            FOREIGN KEY (utilisateur_id) REFERENCES utilisateurs(id),
            FOREIGN KEY (jeu_id) REFERENCES jeux(id)
        );

        CREATE TABLE IF NOT EXISTS critiques (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            texte TEXT,
            date_critique TEXT,
            utilisateur_id INTEGER,
            jeu_id INTEGER,
            UNIQUE(utilisateur_id, jeu_id),
            FOREIGN KEY (utilisateur_id) REFERENCES utilisateurs(id),
            FOREIGN KEY (jeu_id) REFERENCES jeux(id)
        );


        CREATE TABLE IF NOT EXISTS liste_souhaits (
            utilisateur_id INTEGER,
            jeu_id INTEGER,
            PRIMARY KEY (utilisateur_id, jeu_id),
            FOREIGN KEY (utilisateur_id) REFERENCES utilisateurs(id),
            FOREIGN KEY (jeu_id) REFERENCES jeux(id)
        );

        CREATE TABLE IF NOT EXISTS rapports_erreurs (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            description TEXT,
            date_signalement TEXT,
            utilisateur_id INTEGER,
            jeu_id INTEGER,
            statut TEXT CHECK(statut IN ('EnAttente', 'EnTraitement', 'Résolu')),
            FOREIGN KEY (utilisateur_id) REFERENCES utilisateurs(id),
            FOREIGN KEY (jeu_id) REFERENCES jeux(id)
        );

        CREATE TABLE IF NOT EXISTS notifications (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            message TEXT,
            date_envoi TEXT,
            destinataire_id INTEGER,
            FOREIGN KEY (destinataire_id) REFERENCES utilisateurs(id)
        );
        """;

        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.executeUpdate(sql);
            System.out.println("Base de données initialisée avec succès.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
