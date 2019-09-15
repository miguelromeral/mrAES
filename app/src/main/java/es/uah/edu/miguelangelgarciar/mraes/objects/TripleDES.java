package es.uah.edu.miguelangelgarciar.mraes.objects;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;

/**
 * Created by miguelangel.garciar on 23/03/2018.
 */

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

public class TripleDES {
    private static final String UNICODE_FORMAT = "UTF8";
    public static final String DESEDE_ENCRYPTION_SCHEME = "DESede";

    private static SecretKey getSecretKeyFromString(String password){
        try{
            byte[] keyAsBytes = password.getBytes(UNICODE_FORMAT);
            KeySpec myKeySpec = new DESedeKeySpec(keyAsBytes);
            SecretKeyFactory mySecretKeyFactory = SecretKeyFactory.getInstance(DESEDE_ENCRYPTION_SCHEME);
            return mySecretKeyFactory.generateSecret(myKeySpec);
        }catch(UnsupportedEncodingException e){
            System.out.println("Algo salio mal: "+e.getMessage());
            return null;
        }catch(InvalidKeyException i){
            System.out.println("Algo salio mal: "+i.getMessage());
            return null;
        }catch(InvalidKeySpecException a){
            System.out.println("Algo salio mal: "+a.getMessage());
            return null;
        }catch(NoSuchAlgorithmException u){
            System.out.println("Algo salio mal: "+u.getMessage());
            return null;
        }
    }

    /**
     * Method To Encrypt The String
     */
    public static String encrypt(String unencryptedString, String password) {
        String encryptedString = null;
        try {
            Cipher cipher = Cipher.getInstance(DESEDE_ENCRYPTION_SCHEME);
            cipher.init(Cipher.ENCRYPT_MODE, getSecretKeyFromString(password));
            byte[] plainText = unencryptedString.getBytes(UNICODE_FORMAT);
            byte[] encryptedText = cipher.doFinal(plainText);
            encryptedString = android.util.Base64.encodeToString(encryptedText, android.util.Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encryptedString;
    }
    /**
     * Method To Decrypt An Ecrypted String
     */
    public static String decrypt(String encryptedString, String password) throws Exception{
        Cipher cipher = Cipher.getInstance(DESEDE_ENCRYPTION_SCHEME);
        cipher.init(Cipher.DECRYPT_MODE, getSecretKeyFromString(password));

        byte[] encryptedText = android.util.Base64.decode(encryptedString, android.util.Base64.DEFAULT);
        byte[] plainText = cipher.doFinal(encryptedText);

        return new String(plainText, UNICODE_FORMAT);
    }
}

