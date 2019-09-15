package es.uah.edu.miguelangelgarciar.mraes.objects;

/**
 * Created by miguelangel.garciar on 23/03/2018.
 */

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

/*
    Fuente: http://www.java2s.com/Code/Java/Security/EncryptingaStringwithDES.htm
 */
public class DES{

    // Genera una clave secreta en DES
    public static SecretKey generarClaveDES(){
        try{
            return KeyGenerator.getInstance("DES").generateKey();
        }catch(Exception e){
            return null;
        }
    }

    // Cifra el mensaje con una clave secreta DES
    public static String encrypt(String str, SecretKey clave) throws Exception {
        Cipher ecipher = Cipher.getInstance("DES");
        ecipher.init(Cipher.ENCRYPT_MODE, clave);
        // Encode the string into bytes using utf-8
        byte[] utf8 = str.getBytes("UTF8");
        // Encrypt
        byte[] enc = ecipher.doFinal(utf8);
        // Encode bytes to base64 to get a string
        return android.util.Base64.encodeToString(enc, android.util.Base64.DEFAULT);
    }

    // Descifra el mensaje con la clave secreta.
    public static String decrypt(String str, SecretKey clave) throws Exception {
        Cipher dcipher = Cipher.getInstance("DES");
        dcipher.init(Cipher.DECRYPT_MODE, clave);
        // Decode base64 to get bytes
        byte[] dec = android.util.Base64.decode(str, android.util.Base64.DEFAULT);
        byte[] utf8 = dcipher.doFinal(dec);
        // Decode using utf-8
        return new String(utf8, "UTF8");
    }

}

