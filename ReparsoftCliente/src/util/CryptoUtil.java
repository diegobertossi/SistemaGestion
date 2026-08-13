package util;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

/**
 * Utilidad para encriptación/desencriptación AES simétrica de contraseñas.
 * La clave maestra se deriva de una passphrase fija (configurable en producción).
 */
public class CryptoUtil {

    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES/ECB/PKCS5Padding";
    
    // Clave maestra derivada de passphrase (en producción: externalizar a config/keystore)
    private static final String MASTER_PASSPHRASE = "ReparSoft2026MasterKey!Seguro";
    private static SecretKey secretKey;

    static {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance(ALGORITHM);
            keyGen.init(128); // AES-128
            // Derivar clave determinísticamente de la passphrase
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("SHA-256");
            byte[] keyBytes = md.digest(MASTER_PASSPHRASE.getBytes("UTF-8"));
            // Tomar solo 16 bytes para AES-128
            byte[] key128 = new byte[16];
            System.arraycopy(keyBytes, 0, key128, 0, 16);
            secretKey = new SecretKeySpec(key128, ALGORITHM);
        } catch (Exception e) {
            throw new RuntimeException("Error inicializando CryptoUtil", e);
        }
    }

    /**
     * Encripta un texto plano y devuelve Base64.
     */
    public static String encrypt(String plainText) {
        if (plainText == null || plainText.isEmpty()) return plainText;
        try {
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encrypted = cipher.doFinal(plainText.getBytes("UTF-8"));
            return Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception e) {
            throw new RuntimeException("Error encriptando", e);
        }
    }

    /**
     * Desencripta un Base64 y devuelve texto plano.
     */
    public static String decrypt(String encryptedBase64) {
        if (encryptedBase64 == null || encryptedBase64.isEmpty()) return encryptedBase64;
        // Detectar si ya es hash BCrypt legacy (empieza con $2a$/$2b$/$2y$)
        if (encryptedBase64.startsWith("$2a$") || encryptedBase64.startsWith("$2b$") || encryptedBase64.startsWith("$2y$")) {
            return "[legacy-hash]"; // No se puede desencriptar BCrypt
        }
        try {
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decoded = Base64.getDecoder().decode(encryptedBase64);
            byte[] decrypted = cipher.doFinal(decoded);
            return new String(decrypted, "UTF-8");
        } catch (Exception e) {
            // Si falla, asumir legacy o corrupto
            return "[corrupt]";
        }
    }

    /** Verifica si un string es un hash BCrypt legacy */
    public static boolean isLegacyBcrypt(String value) {
        return value != null && (value.startsWith("$2a$") || value.startsWith("$2b$") || value.startsWith("$2y$"));
    }
}