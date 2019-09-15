package es.uah.edu.miguelangelgarciar.mraes.objects;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Base64;

import java.io.Serializable;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

import es.uah.edu.miguelangelgarciar.mraes.R;

public class RSA implements Serializable {

    public static int KEY_SIZE = 2048;
    public static String ALGORITMO = "RSA";
    public PublicKey pub;
    public PrivateKey priv;
    public KeyPair par_claves;

    public RSA(Activity act){
        try{
            KeyPair keyPair = ManagementRSAKeys.LoadKeyPair(act);
         //   KeyPair keyPair = buildKeyPair();
            this.par_claves = keyPair;
            this.pub = keyPair.getPublic();
            this.priv = keyPair.getPrivate();
        } catch (Exception e2) {
            new AlertDialog.Builder(null)
                    .setCancelable(false)
                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        }
                    })
                    .setMessage("Error al abrir RSA: "+e2.getMessage())
                    .show();
        }
    }

    public RSA(KeyPair keyPair){
        this.par_claves = keyPair;
        this.pub = keyPair.getPublic();
        this.priv = keyPair.getPrivate();
    }

    public void guardarClaves(Activity act) throws Exception{
        ManagementRSAKeys.SaveKeyPair(par_claves, act);
    }

    public static PublicKey bytesToPublicKey(byte[] terceros){
        try{
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(terceros);
            return keyFactory.generatePublic(pubKeySpec);
        }catch(Exception e){
        }
        return null;
    }


    public PublicKey getPublicKey(){
        return pub;
    }

    public String cifrar(PublicKey clave, String mensaje) throws Exception{
        return encrypt_pub(clave, mensaje);
    }
    public String descifrar(PublicKey clave, String cifrado) throws Exception{
        return new String(decrypt_pub(clave, Base64.decode(cifrado, Base64.DEFAULT)));
    }
    public String cifrar_firma(String mensaje) throws Exception{
        return encrypt_priv(priv, mensaje);
    }
    public String descifrar_firma(String cifrado) throws Exception{
        return new String(decrypt_priv(priv, Base64.decode(cifrado, Base64.DEFAULT)));
    }


    public static KeyPair buildKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(KEY_SIZE);
        return keyPairGenerator.genKeyPair();
    }


    private String encrypt_pub(PublicKey key, String message) {
        Cipher cipher;
        try {
            cipher = Cipher.getInstance(ALGORITMO);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return Base64.encodeToString(cipher.doFinal(message.getBytes()), Base64.DEFAULT);
        }catch(Exception e){
            new AlertDialog.Builder(null).setMessage("Excepcion al encriptar: "+e.getMessage()).setPositiveButton("OK", null).show();
        }
        return null;
    }

    private String encrypt_priv(PrivateKey key, String message) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITMO);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return Base64.encodeToString(cipher.doFinal(message.getBytes()), Base64.DEFAULT);
    }

    private byte[] decrypt_pub(PublicKey key, byte [] encrypted) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITMO);
        cipher.init(Cipher.DECRYPT_MODE, key);
        return cipher.doFinal(encrypted);
    }

    private byte[] decrypt_priv(PrivateKey key, byte [] encrypted) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITMO);
        cipher.init(Cipher.DECRYPT_MODE, key);
        return cipher.doFinal(encrypted);
    }
}


