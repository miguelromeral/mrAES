package es.uah.edu.miguelangelgarciar.mraes.objects;

import android.util.Base64;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

/**
 * Created by miguelangel.garciar on 25/03/2018.
 */

/*
 Fuente: https://stackoverflow.com/a/33085670/9530499
 */
public class Hash {

    // Genera una salt aleatoria de 8 bytes.
    public static String randomSalt(){
        Random r = new SecureRandom();
        byte[] salt = new byte[8];
        r.nextBytes(salt);
        return Base64.encodeToString(salt, Base64.DEFAULT);
    }

    // Genera el hash con la salt por delante.
    public static String generateHash(String passwordToHash, String salt, String algoritmo){
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance(algoritmo);
            // Ponemos la salt delante
            md.update(salt.getBytes(StandardCharsets.ISO_8859_1));
            byte[] bytes = md.digest(passwordToHash.getBytes(StandardCharsets.ISO_8859_1));
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++){
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        }
        catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }
        return generatedPassword;
    }

    // Obtiene el hash de una salida tipo [salt,hash] --> hash.
    public static String getHashFromString(String hashFormateado){
        String[] hash = hashFormateado.split(",");
        String nuevo = hash[1].substring(0, hash[1].length() - 1);
        return nuevo;
    }
}
