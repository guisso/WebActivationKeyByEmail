package io.github.guisso.jakartaee8.webacctivationkeybymail;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 *
 * <https://howtodoinjava.com/java/java-security/how-to-generate-secure-password-hash-md5-sha-pbkdf2-bcrypt-examples/>
 * @author Luis Guisso <luis.guisso at ifnmg.edu.br>
 */
public class Util {
    
    public static final byte ENCPASSWD = 0;
    public static final byte SALT = 1;
    
    public static String[] hash(
            String plainText) {
        return Util.hash(plainText, null);
    }

    public static String[] hash(
            String plainText, String previousSalt) {
        try {
            byte[] salt;
            if (previousSalt == null) {
                SecureRandom random = SecureRandom
                        .getInstance("SHA1PRNG");
                salt = new byte[16];
                random.nextBytes(salt);

                // Encodes new byte[]
                // to equivalent Base64 String
                salt = Base64.getEncoder()
                        .withoutPadding() // w/o *==
                        .encode(salt);

            } else {
                salt = previousSalt.getBytes();
            }

            // A (transparent) specification of 
            // the key material that constitutes 
            // a cryptographic key on
            // password-based encryption (PBE)
            KeySpec spec = new PBEKeySpec(
                    plainText.toCharArray(),
                    salt,
                    2147, // Iterations
                    128); // SHA1: 160 bits

            // PBKDF2WithHmacSHA1
            // Password-based-Key-Derivative-Function v2
            // Keyed-Hash Message Authentication Code
            // Secure Hash Algorithm 1
            // https://tools.ietf.org/html/rfc8018
            SecretKeyFactory factory = SecretKeyFactory
                    .getInstance("PBKDF2WithHmacSHA1");

            // Encodes password from bytes[]
            // to Base64 String
            byte[] hash = factory.generateSecret(spec)
                    .getEncoded();

            String encryptedPassword = new String(
                    Base64.getEncoder().withoutPadding()
                            .encode(hash));

            System.out.println(
                    ">>\rEncrypted password: "
                    + new String(hash)
                    + "\rEncrypted password Base64: "
                    + encryptedPassword
                    + "\rSalt Base64: "
                    + new String(salt));

            return new String[]{
                encryptedPassword,
                new String(salt)
            };

        } catch (NoSuchAlgorithmException
                | InvalidKeySpecException ex) {
            Logger.getLogger(Util.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static boolean isAuthentic(
            String password, User user) {
        if (password != null
                && user != null
                && user.getEncryptedPassword() != null
                && user.getSalt() != null) {
            if (hash(password, user.getSalt())[ENCPASSWD]
                    .equals(user.getEncryptedPassword())) {
                return true;
            }
        }

        return false;
    }

    private static String byteArrayToHex(byte[] array)
            throws NoSuchAlgorithmException {
        BigInteger bi = new BigInteger(1, array);
        String hex = bi.toString(16);
        int paddingLength
                = (array.length * 2) - hex.length();
        if (paddingLength > 0) {
            return String.format(
                    "%0" + paddingLength + "d", 0)
                    + hex;
        } else {
            return hex;
        }
    }

    private static byte[] hexToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ( //
                    (Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }

}
