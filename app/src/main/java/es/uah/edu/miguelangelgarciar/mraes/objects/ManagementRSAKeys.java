package es.uah.edu.miguelangelgarciar.mraes.objects;

import android.app.Activity;
import android.os.Environment;

import java.io.*;
import java.security.*;
import java.security.spec.*;

/**
 * Created by miguelangel.garciar on 19/04/2018.
 */

/*
    Fuente: http://snipplr.com/view/18368/
 */

public class ManagementRSAKeys {

    // Ruta en la que se almacenarán las claves
    // Environment.DIRECTORY_DOCUMENTS lleva a esta ruta
    public static String PATH_KEYS="/storage/emulated/0/Android/data/es.uah.edu.miguelangelgarciar.mraes/files/Documents/";
    // Nombre de las claves publicas y privadas.
    public static String PUBLICA="miClavePublicamrCipher.pub";
    public static String PRIVADA="NO_EXPORTAR_miClavePrivadamrCipher";

    // Almacenamos las claves en diferentes archivos.
    public static void SaveKeyPair(KeyPair keyPair, Activity act) throws IOException {
        PrivateKey privateKey = keyPair.getPrivate();
        PublicKey publicKey = keyPair.getPublic();

        // Store Public Key.
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(
                publicKey.getEncoded());
        File file = new File(act.getExternalFilesDir(
                Environment.DIRECTORY_DOCUMENTS), PUBLICA);
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(x509EncodedKeySpec.getEncoded());
        fos.close();


        // Store Private Key.
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(
                privateKey.getEncoded());
        file = new File(act.getExternalFilesDir(
                Environment.DIRECTORY_DOCUMENTS), PRIVADA);
        fos = new FileOutputStream(file);
        fos.write(pkcs8EncodedKeySpec.getEncoded());
        fos.close();

    }

    // Recogemos el par de claves desde los ficheros.
    public static KeyPair LoadKeyPair(Activity act)
            throws IOException, NoSuchAlgorithmException,
            InvalidKeySpecException {
        // Read Public Key.
        File filePublicKey = new File(act.getExternalFilesDir(
                Environment.DIRECTORY_DOCUMENTS), PUBLICA);
        FileInputStream fis = new FileInputStream(filePublicKey);
        byte[] encodedPublicKey = new byte[(int) filePublicKey.length()];
        fis.read(encodedPublicKey);
        fis.close();

        // Read Private Key.
        File filePrivateKey = new File(act.getExternalFilesDir(
                Environment.DIRECTORY_DOCUMENTS), PRIVADA);
        fis = new FileInputStream(filePrivateKey);
        byte[] encodedPrivateKey = new byte[(int) filePrivateKey.length()];
        fis.read(encodedPrivateKey);
        fis.close();

        // Generate KeyPair.
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(
                encodedPublicKey);
        PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);

        PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(
                encodedPrivateKey);
        PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);

        return new KeyPair(publicKey, privateKey);
    }
}
