package src.utils;

import java.security.MessageDigest;
import java.util.Base64;

public class SecurityUtils {

    public static String hashPassword(String motDePasse) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(motDePasse.getBytes());
            return Base64.getEncoder().encodeToString(hash);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors du hachage du mot de passe", e);
        }
    }
}
