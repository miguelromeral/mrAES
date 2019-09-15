package es.uah.edu.miguelangelgarciar.mraes.objects;

/**
 * Created by miguelangel.garciar on 03/02/2018.
 */

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import android.util.Base64;

/*
    Fuente: https://gist.github.com/SimoneStefani/99052e8ce0550eb7725ca8681e4225c5
 */
public class AESenc {


    private static String ALGO = "AES";

    /**
     * Encrypt a string with AES algorithm.
     *
     * @param data is a string
     * @return the encrypted string
     */
    public static String encrypt(String data, String clave) throws Exception {
        Key key = generateKey(clave);
        Cipher c = Cipher.getInstance(ALGO);
        c.init(Cipher.ENCRYPT_MODE, key);
        byte[] encVal = c.doFinal(data.getBytes());
        return Base64.encodeToString(encVal, Base64.DEFAULT);
    }

    /**
     * Decrypt a string with AES algorithm.
     *
     * @param encryptedData is a string
     * @return the decrypted string
     */
    public static String decrypt(String encryptedData, String clave) throws Exception {
        String decrypted = "";
        String[] lines = encryptedData.split("\\r?\\n");
        for (String line : lines) {
            Key key = generateKey(clave);
            Cipher c = Cipher.getInstance(ALGO);
            c.init(Cipher.DECRYPT_MODE, key);
            byte[] decordedValue = Base64.decode(line, Base64.DEFAULT);
            byte[] decValue = c.doFinal(decordedValue);
            decrypted += new String(decValue) + "\n";
        }
        return decrypted;

    }


    /**
     * Generate a new encryption key.
     */
    private static Key generateKey(String keyValue) throws Exception {
        return new SecretKeySpec(keyValue.getBytes(), ALGO);
    }

}
