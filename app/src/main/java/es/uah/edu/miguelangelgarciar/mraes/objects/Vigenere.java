package es.uah.edu.miguelangelgarciar.mraes.objects;

/**
 * Created by miguelangel.garciar on 26/03/2018.
 */

/*
Fuente: https://www.sanfoundry.com/java-program-implement-vigenere-cypher/
 */

public class Vigenere {

    public static String encrypt(String text, final String key)
    {
        String res = "";
        text = text.toUpperCase();
        for (int i = 0, j = 0; i < text.length(); i++)
        {
            char c = text.charAt(i);
            if (c < 'A' || c > 'Z')
                continue;
            res += (char) ((c + key.charAt(j) - 2 * 'A') % 26 + 'A');
            j = ++j % key.length();
        }
        return res;
    }

    public static String decrypt(String text, final String key)
    {
        String res = "";
        text = text.toUpperCase();
        for (int i = 0, j = 0; i < text.length(); i++)
        {
            char c = text.charAt(i);
            if (c < 'A' || c > 'Z')
                continue;
            res += (char) ((c - key.charAt(j) + 26) % 26 + 'A');
            j = ++j % key.length();
        }
        return res;
    }

    // Determina si solo hay letras en el mensaje
    public static boolean solo_letras(String mensaje){
        mensaje = mensaje.toUpperCase();
        for (int i = 0, j = 0; i < mensaje.length(); i++)
        {
            char c = mensaje.charAt(i);
            if (c < 'A' || c > 'Z'){
                return false;
            }
        }
        return true;
    }

}
