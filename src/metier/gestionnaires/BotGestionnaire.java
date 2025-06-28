package src.metier.gestionnaires;

import org.json.JSONArray;
import org.json.JSONObject;
import src.metier.Critique;
import src.metier.Jeu;
import src.service.JeuService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class BotGestionnaire implements GestionnaireJeu {

    private final JeuService jeuService = new JeuService();

    @Override
    public void ajouterJeu(Jeu jeu) {
        // Supposons que le bot a un ID fixe, par exemple 0, ou utilisez une constante appropriée
        jeu.setCreateurId(0);
        if (!jeuService.existeJeu(jeu.getTitre(), jeu.getPlateforme())) {
            boolean ok = jeuService.ajouterJeu(jeu);
            System.out.println(ok ? "Jeu ajouté (bot)." : "Échec ajout (bot).");
        } else {
            System.out.println("Jeu déjà présent : " + jeu.getTitre());
        }
    }

    @Override
    public void modifierJeu(Jeu jeu) {
        // Optionnel : non utilisé par le bot
    }

    @Override
    public void supprimerJeu(Jeu jeu) {
        // Optionnel : non utilisé par le bot
    }

    @Override
    public void supprimerCritique(Critique critique) {
        // Optionnel : non utilisé par le bot
    }

    public void importerDepuisJSON(String cheminFichierJson) {
        try {
            String contenu = new String(Files.readAllBytes(Paths.get(cheminFichierJson)));
            JSONArray jeuxArray = new JSONArray(contenu);

            for (int i = 0; i < jeuxArray.length(); i++) {
                JSONObject obj = jeuxArray.getJSONObject(i);

                Jeu jeu = new Jeu();
                jeu.setTitre(obj.getString("titre"));
                jeu.setDescription(obj.getString("description"));
                jeu.setPlateforme(obj.getString("plateforme"));
                jeu.setGenre(String.join(", ", obj.getJSONArray("genre").toList().stream()
                        .map(Object::toString).toList()));
                jeu.setDateSortie(obj.getString("date_sortie"));
                jeu.setEditeur(obj.getString("editeur"));

                ajouterJeu(jeu);
            }

        } catch (IOException e) {
            System.err.println("Erreur lecture fichier JSON : " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Erreur traitement JSON : " + e.getMessage());
        }
    }
}
