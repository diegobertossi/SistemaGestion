package util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Valida CryptoUtil (encriptación AES de contraseñas y detección de legados).
 */
public class CryptoUtilTest {

    /** Encriptar y desencriptar devuelve el texto original. */
    @Test
    public void encryptDecrypt_roundtripDevuelveElTextoOriginal() {
        String texto = "MiPasswordSeguro123";
        String encriptado = CryptoUtil.encrypt(texto);
        assertNotEquals(texto, encriptado);
        assertEquals(texto, CryptoUtil.decrypt(encriptado));
    }

    /** La misma passphrase genera el mismo ciphertext (clave determinística). */
    @Test
    public void encrypt_esDeterministicoConLaMismaClave() {
        assertEquals(CryptoUtil.encrypt("abc"), CryptoUtil.encrypt("abc"));
    }

    /** Encriptados distintos para textos distintos. */
    @Test
    public void encrypt_textosDistintosGeneranCiphertextsDistintos() {
        assertNotEquals(CryptoUtil.encrypt("uno"), CryptoUtil.encrypt("dos"));
    }

    /** Null y vacío pasan tal cual (sin encriptar). */
    @Test
    public void encrypt_nullOVacioNoEncripta() {
        assertEquals(null, CryptoUtil.encrypt(null));
        assertEquals("", CryptoUtil.encrypt(""));
    }

    /** Null y vacío no se intentan desencriptar. */
    @Test
    public void decrypt_nullOVacioDevuelveTalCual() {
        assertEquals(null, CryptoUtil.decrypt(null));
        assertEquals("", CryptoUtil.decrypt(""));
    }

    /** Un hash BCrypt legacy no se puede desencriptar: se marca como legacy. */
    @Test
    public void decrypt_hashBcryptLegacyDevuelveMarcador() {
        assertEquals("[legacy-hash]", CryptoUtil.decrypt("$2a$10$abcdefghijklmnopqrstuv"));
        assertEquals("[legacy-hash]", CryptoUtil.decrypt("$2b$10$abcdefghijklmnopqrstuv"));
        assertEquals("[legacy-hash]", CryptoUtil.decrypt("$2y$10$abcdefghijklmnopqrstuv"));
    }

    /** Un Base64 corrupto o que no es AES devuelve el marcador de corrupto (sin excepción). */
    @Test
    public void decrypt_base64CorruptoDevuelveMarcadorSinExcepcion() {
        assertEquals("[corrupt]", CryptoUtil.decrypt("no-es-base64!!!"));
        assertEquals("[corrupt]", CryptoUtil.decrypt("c3VwZXJzZWNyZXRh!!!!")); // Base64 válido pero no AES
    }

    /** isLegacyBcrypt detecta los prefijos de BCrypt. */
    @Test
    public void isLegacyBcrypt_detectaPrefijos() {
        assertTrue(CryptoUtil.isLegacyBcrypt("$2a$10$x"));
        assertTrue(CryptoUtil.isLegacyBcrypt("$2b$10$x"));
        assertTrue(CryptoUtil.isLegacyBcrypt("$2y$10$x"));
        assertFalse(CryptoUtil.isLegacyBcrypt("pAhOil/HgaCSWNcRgoiCqw=="));
        assertFalse(CryptoUtil.isLegacyBcrypt(null));
        assertFalse(CryptoUtil.isLegacyBcrypt(""));
    }

    /** Texto con caracteres especiales (ñ, á, emojis) sobrevive el roundtrip UTF-8. */
    @Test
    public void encryptDecrypt_conCaracteresEspeciales() {
        String texto = "contraseña ñandú áéíóú €";
        assertEquals(texto, CryptoUtil.decrypt(CryptoUtil.encrypt(texto)));
    }
}