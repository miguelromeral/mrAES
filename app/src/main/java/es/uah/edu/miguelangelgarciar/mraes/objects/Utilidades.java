package es.uah.edu.miguelangelgarciar.mraes.objects;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.text.InputType;
import android.util.Base64;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import es.uah.edu.miguelangelgarciar.mraes.R;

/**
 * Created by miguelangel.garciar on 23/03/2018.
 */
// Utilidades varias para diferentes fragmentos y actividades.
public class Utilidades {

    // Convierte una cadena de Base64 a texto en hexadecimal
    public static String base64ToHexadecimal(String encoded){
        byte[] decoded = Base64.decode(encoded, Base64.DEFAULT);
        StringBuilder hexadecimal = new StringBuilder();
        int num=0;
        for (byte b : decoded) {
            hexadecimal.append(String.format("%02X ", b));
            num++;
            if(num % 16 == 0){
                hexadecimal.append("\n");
            }
        }
        return hexadecimal.toString();
    }

    // Obtiene un caracter en ASCII o '.' si no se puede imprimir.
    public static char getAscii(char c) {
        CharsetEncoder ce = Charset.forName("ISO-8859-1").newEncoder();
        if(ce.canEncode(c)){
            return c;
        }else{
            return '.';
        }
    }

    // Convierte una cadena de Base64 a caracteres (si se pueden imrpimir)
    public static String base64ToCharString(String encoded){
        byte[] decoded = Base64.decode(encoded, Base64.DEFAULT);
        StringBuilder caracteres = new StringBuilder();
        int num=0;
        for (byte b : decoded) {
            caracteres.append(getAscii((char) b));
            num++;
            if(num % 16 == 0){
                caracteres.append("\n");
            }
        }
        return caracteres.toString();
    }

    // Borra el texto de un EditText
    public static void borrarEditText(Activity ac, int id){
        EditText te = (EditText) ac.findViewById(id);
        te.setText("");
    }

    // Pega el contenido del portapapeles en un EditText
    public static void pegarEditText(Activity ac, int id){
        try {
            TextView tin = (TextView) ac.findViewById(id);
            ClipboardManager myClipboard;
            ClipData myClip;
            myClipboard = (ClipboardManager) ac.getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData abc = myClipboard.getPrimaryClip();
            ClipData.Item item = abc.getItemAt(0);
            String text = item.getText().toString();
            tin.setText(text);
        }catch(Exception ex){
            Toast.makeText(ac.getApplicationContext(), R.string.ex_portapapeles, Toast.LENGTH_LONG).show();
        }
    }

    // Copia el contenido de un EditText al portapapeles.
    public static void copiarEditText(Activity ac, int id){
        try {
            TextView tout = (TextView) ac.findViewById(id);
            if(!tout.getText().toString().isEmpty()) {
                ClipboardManager myClipboard;
                ClipData myClip;
                myClipboard = (ClipboardManager) ac.getSystemService(Context.CLIPBOARD_SERVICE);
                String text = tout.getText().toString();
                myClip = ClipData.newPlainText("text", text);
                myClipboard.setPrimaryClip(myClip);
            }else{
                Toast.makeText(ac.getApplicationContext(), R.string.ex_salida, Toast.LENGTH_LONG).show();
            }
        }catch(Exception ex){
            Toast.makeText(ac.getApplicationContext(), R.string.ex_salida, Toast.LENGTH_LONG).show();
        }
    }

    // Rellena la clave si es necesario con el caracter de relleno.
    public static String rellena_clave(String clave, int lim, char padding){

        return Hash.generateHash(clave, "", "SHA-512").substring(0, lim);
        /*
        String rellenada = clave;
        int len = clave.length();
        while(len < lim){
            rellenada += padding;
            len++;
        }
        return rellenada;*/
    }

    // Muestra el cnotenido de una contraseña o no
    public static void mostrar_ocultar_clave(Activity act, int pass, int check){
        EditText ete = (EditText) act.findViewById(pass);
        CheckBox cbe = (CheckBox) act.findViewById(check);
        //Dependiendo del tipo de formato del cuadro para la clave, se pone como con contraseña o plano.
        if(cbe.isChecked()){
            ete.setInputType(InputType.TYPE_CLASS_TEXT);
            cbe.setText(R.string.ver_pwd);
        }else{
            ete.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            cbe.setText(R.string.ver_pwd);
        }
    }

    // Cambia el mensaje del botón de cifrado en función del switch.
    public static void cambiarCifrarDescifrar(Activity act, int sw, int boton, int etpromptplano, int etplano, int etcifrado){
        TextView ti = (TextView) act.findViewById(etplano);
        TextView to = (TextView) act.findViewById(etcifrado);
        Switch switch_e_d = (Switch) act.findViewById(sw);
        TextView tvED = (TextView) act.findViewById(etpromptplano);
        Button bExe = (Button) act.findViewById(boton);
        if(switch_e_d.isChecked()){
            tvED.setText(R.string.input_d);
            bExe.setText(R.string.desenc);
            ti.setHint(R.string.cifrado);
            to.setHint(R.string.plano);
        }else{
            tvED.setText(R.string.input_e);
            bExe.setText(R.string.enc);
            ti.setHint(R.string.plano);
            to.setHint(R.string.cifrado);
        }
    }

    // Codifica una clave secreta a string.
    public static String claveToString(SecretKey key){
        return android.util.Base64.encodeToString(key.getEncoded(), android.util.Base64.DEFAULT);
    }

    // Decodifica una cadena de texto a clave SECRETA en DES
    public static SecretKey stringToClave(String clave, String algoritmo){
        byte [] decodedKey = android.util.Base64.decode(clave, android.util.Base64.DEFAULT);
        return new SecretKeySpec(decodedKey, 0, decodedKey.length, algoritmo);
    }

}
