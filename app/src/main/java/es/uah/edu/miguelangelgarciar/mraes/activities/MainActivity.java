package es.uah.edu.miguelangelgarciar.mraes.activities;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.PublicKey;

import es.uah.edu.miguelangelgarciar.mraes.objects.RSA;
import es.uah.edu.miguelangelgarciar.mraes.objects.UpdateHelper;
import es.uah.edu.miguelangelgarciar.mraes.objects.Utilidades;
import es.uah.edu.miguelangelgarciar.mraes.R;

public class MainActivity extends AppCompatActivity implements UpdateHelper.OnUpdateCheckListener {
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    // Adaptador de los fragmentos
    private SectionsPagerAdapter mSectionsPagerAdapter;
    public RSA rsa;
    // Preferencias del main
    private static String PREFERENCES_MAIN = "preferncias_main";
    private int veces_ejecutado=0;
    private static String MAIN_EJECUTADO = "main_ejecutado";
    private static int N_ACTUALIZACIONES=10;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
        // Creamos la instacia de RSA
        try {
            rsa = new RSA(this);
        }catch(Exception e){
            // Si no hemos podido cargar las claves, generamos otras nuevas.
            try {
                rsa = new RSA(RSA.buildKeyPair());
                rsa.guardarClaves(this);
            }catch(Exception e2){
                new AlertDialog.Builder(this)
                        .setCancelable(false)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        })
                        .setMessage(R.string.clave_nueva_error)
                        .show();
            }
        }
        // Intentamos recuperar las prefeerncias del main.
        try{
            SharedPreferences sharedPreferences = getSharedPreferences(PREFERENCES_MAIN, Context.MODE_PRIVATE);
        }catch(Exception e){
            veces_ejecutado = 0;
        }
    }

    // Listener que se pondrá en la aplicación cuando haya una actualización.
    @Override
    public void onUpdateCheckListener(final String urlApp){
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle(R.string.nueva_actualizacion)
                .setMessage(R.string.actualizacion_mensaje)
                .setPositiveButton(R.string.act_si, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        try {
                            Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlApp));
                            startActivity(myIntent);
                        } catch (ActivityNotFoundException e) {

                           Toast.makeText(null, R.string.act_error, Toast.LENGTH_LONG).show();

                        }
                    }
                })
                .setNegativeButton(R.string.act_no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                      //  dialogInterface.dismiss();
                    }
                }).create();
        alertDialog.show();
    }

    // Cuando se pare la ejecución, guardamos las preferencias del main.
    @Override
    public void onStop(){
        super.onStop();
        SharedPreferences sharedPreferences = getSharedPreferences(PREFERENCES_MAIN, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(MAIN_EJECUTADO, veces_ejecutado + 1);
        editor.commit();
    }

    // Creamos el menú en la toolbar.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    // Tratamos cada opción elegida en el menú
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        Intent i;
        switch (id){
            case R.id.action_settings:
                i = new Intent(this, AcercaDe.class);
                startActivity(i);
                return true;
            case R.id.action_novedades:
                i = new Intent(this, Novedades.class);
                startActivity(i);
                return true;
            case R.id.action_base64:
                i = new Intent(this, Base64Tool.class);
                startActivity(i);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A {@link FragmentPagerAdapter} Devuelve el fragmento según el panel elegido
     * o pestaña clickada.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        // En función de la posición del fragment, devolvemos uno de ellos.
        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    TabAES tab1 = new TabAES();
                    return tab1;
                case 1:
                    TabHashes tab2 = new TabHashes();
                    return tab2;
                case 2:
                    TabRSA tab3 = new TabRSA();
                    return tab3;
                case 3:
                    TabTripleDES tab5 = new TabTripleDES();
                    return tab5;
                case 4:
                    TabDES tab4 = new TabDES();
                    return tab4;
                case 5:
                    TabVigenere tab6 = new TabVigenere();
                    return tab6;
                default:
                    return null;
            }
        }

        // Especificamos la cuenta de fragments.
        @Override
        public int getCount() {
            return 6;
        }

        // Indicamos el titulo de cada fragment resumidamente.
        @Override
        public CharSequence getPageTitle(int position){
            switch (position){
                case 0:
                    return "AES";
                case 1:
                    return "HASH";
                case 2:
                    return "RSA";
                case 3:
                    return "3DES";
                case 4:
                    return "DES";
                case 6:
                    return "VIG";
                default:
                    return null;
            }
        }
    }

    // Especifica la clave pública RSA
    public void ponerRSAPublica(View vista){
        EditText et = (EditText) findViewById(R.id.etRSApwd);
        et.setText(getResources().getString(R.string.rsa_mensaje_publica));
    }

    // Especifica la clave privada RSA
    public void ponerRSAPrivada(View vista){
        EditText et = (EditText) findViewById(R.id.etRSApwd);
        et.setText(getResources().getString(R.string.rsa_mensaje_privada));
    }

    // Realiza el cifrado o descifrado en RSA
    public void execute_rsa(View vista) {
        TextView tin = (EditText) findViewById(R.id.etMRSA);
        TextView tout = (EditText) findViewById(R.id.etCRSA);
        Switch switch_e_d = (Switch) findViewById(R.id.swRSA);
        // Comprobamos que existe un mensaje.
        if(tin.getText().toString().isEmpty()){
            new AlertDialog.Builder(this)
                    .setCancelable(false)
                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        }
                    })
                    .setMessage(R.string.ex_men)
                    .show();
        }else {
            String mensaje = tin.getText().toString();
            EditText etClave = (EditText) findViewById(R.id.etRSApwd);
            // Comprobamos que existe una clave.
            if(etClave.getText().toString().isEmpty()){
                new AlertDialog.Builder(this)
                        .setCancelable(false)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        })
                        .setMessage(R.string.w_clave_rsa)
                        .show();
            }else {
                String clave = etClave.getText().toString();
                // Vemos si se está usando nuestra clave privada, porque el comportamiento será diferente.
                boolean usar_mi_privada = clave.equals(getResources().getString(R.string.rsa_mensaje_privada));
                //Desencriptar
                if (switch_e_d.isChecked()) {
                    String desencriptado = null;
                    if (usar_mi_privada) {
                        // Firmando para descifrar.
                        try {
                            desencriptado = rsa.descifrar_firma(mensaje);
                            tout.setText(desencriptado);
                        } catch (ArrayIndexOutOfBoundsException aiobe) {
                            Toast.makeText(this, R.string.w_too_much_data, Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            Toast.makeText(this, R.string.c_no_coincide, Toast.LENGTH_LONG).show();
                        }
                    } else {
                        // Descifrando con clave pública.
                        PublicKey usada;
                        // Si se usa nuestra clave pública, se usa.
                        if(clave.equals(getResources().getString(R.string.rsa_mensaje_publica))){
                            usada = rsa.getPublicKey();
                        }else{
                            // Sino, se intenta exportar la que tiene ese nombre de archivo.
                            usada = ponerRSATerceros(vista);
                            // Si no existe, se advierte.
                            if(usada == null){
                                new AlertDialog.Builder(this)
                                        .setCancelable(false)
                                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                            }
                                        })
                                        .setMessage(R.string.w_falta_terceros)
                                        .show();
                                return;
                            }
                        }
                        try {
                            // Descifrado.
                            desencriptado = rsa.descifrar(usada, mensaje);
                            tout.setText(desencriptado);
                        } catch (ArrayIndexOutOfBoundsException aiobe) {
                            Toast.makeText(this, R.string.w_too_much_data, Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            Toast.makeText(this, R.string.c_no_coincide, Toast.LENGTH_LONG).show();
                        }
                    }


                } else {
                    //Encriptar
                    String encriptado = null;
                    if (usar_mi_privada) {
                        // Firmando
                        try {
                            encriptado = rsa.cifrar_firma(mensaje);
                            tout.setText(encriptado);
                        } catch (ArrayIndexOutOfBoundsException aiobe) {
                            Toast.makeText(this, R.string.w_too_much_data, Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            Toast.makeText(this, R.string.ex_e, Toast.LENGTH_LONG).show();
                        }
                    } else {
                        // Igual, utilizamos nuestra publica o intentamos importar otra.
                        PublicKey usada;
                        if(clave.equals(getResources().getString(R.string.rsa_mensaje_publica))){
                            usada = rsa.getPublicKey();
                        }else{
                            usada = ponerRSATerceros(vista);
                            if(usada == null){
                                new AlertDialog.Builder(this)
                                        .setCancelable(false)
                                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                            }
                                        })
                                        .setMessage(R.string.w_falta_terceros)
                                        .show();
                                return;
                            }
                        }
                        try {
                            //Ciframos
                            encriptado = rsa.cifrar(usada, mensaje);
                            tout.setText(encriptado);
                        } catch (ArrayIndexOutOfBoundsException aiobe) {
                            Toast.makeText(this, R.string.w_too_much_data, Toast.LENGTH_LONG).show();
                        }catch (Exception e) {
                            Toast.makeText(this, R.string.ex_e, Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }
        }
    }


    // Guarda la clave pública en un fichero.
    public void exportarClavePublica(String nombreArchivo, PublicKey publica) {
        try {
            byte [] clave = publica.getEncoded();
            File file = new File(this.getExternalFilesDir(
                    Environment.DIRECTORY_DOCUMENTS), nombreArchivo);
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(clave);
            fos.close();
            Toast.makeText(this, R.string.exito_clave, Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(this, R.string.error_clave, Toast.LENGTH_SHORT).show();
        }
    }

    // Obtiene la clave pública en bytes desde el PATH donde se guardan
    public byte[] importarClavePublica(String nombreArchivo){
        File file = new File("/storage/emulated/0/Android/data/es.uah.edu.miguelangelgarciar.mraes/files/Documents/"+nombreArchivo);
        int size = (int) file.length();
        byte[] bytes = new byte[size];
        try {
            BufferedInputStream buf = new BufferedInputStream(new FileInputStream(file));
            buf.read(bytes, 0, bytes.length);
            buf.close();
            return bytes;
        } catch (Exception e) {
        }
        return null;
    }


    // Muestra un mensaje de ayuda, para mostrar la localización de las claves
    public void mostrarAyudaRSA(View vista){
        new AlertDialog.Builder(this)
                .setCancelable(false)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                })
                .setMessage(R.string.rsa_mensaje_ayuda)
                .show();
    }

    // Obtiene una clave pública desde el nombre de archivo
    public PublicKey ponerRSATerceros(View vista){
        // Obtenemos el nombre de archivo de la clave.
        EditText et = (EditText) findViewById(R.id.etRSApwd);
        if(et.getText().toString().isEmpty()){
            new AlertDialog.Builder(this)
                    .setCancelable(false)
                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        }
                    })
                    .setMessage(R.string.w_falta_terceros)
                    .show();
            return null;
        }
        String archivo = et.getText().toString();
        // Obtenemos el chorro de bytes perteneciente a la clave.
        byte[] ter = importarClavePublica(archivo);
        if(ter == null){
            //Si no se pudo abrir el archivo, no se genera nada.
            return null;
        }else{
            // Si tenemos bytes, generamos la clave pública.
            Toast.makeText(this, "Clave publica importada con éxito", Toast.LENGTH_SHORT).show();
            return RSA.bytesToPublicKey(ter);
        }
    }

    // Enviamos nuestra clave pública
    public void enviar_clave_publica(View vista){
        // Comprobamos que el nombre del fichero no es vacio
        EditText et = (EditText) findViewById(R.id.etExportar);
        if(et.getText().toString().isEmpty()) {
            new AlertDialog.Builder(this)
                    .setCancelable(false)
                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        }
                    })
                    .setMessage(R.string.w_falta_export)
                    .show();
            return;
        }
        // Guardamos en un archivo la clave pública
        exportarClavePublica(et.getText().toString(), rsa.getPublicKey());
    }

    // Mostramos la actividad secreta solo si se pulsan 7 veces el mismo botón.
    int secreto_pulsado = 0;
    public void secretoPulsado() {
        if (secreto_pulsado == 7) {
            Intent i = new Intent(this, Secreto.class);
            startActivity(i);
            secreto_pulsado = 0;
            return;
        }

        if(secreto_pulsado == 4)
            Toast.makeText(this, R.string.secreto0, Toast.LENGTH_LONG).show();

        secreto_pulsado++;

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                secreto_pulsado = 0;
            }
        }, 5000);
    }

    //Indica si se ha pulsado dos veces el botón de back.
    boolean doubleBackToExitPressedOnce = false;
    //Acción cuando se pulsa el botón de atrás, tal vez podría ser onPause.
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, R.string.w_salir, Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

    //Acción cuando se reanuda la app.
    public void onResume(){
        super.onResume();
    }

    public void onPuase(){
        super.onPause();
    }


    // Borra la salida de AES. Esta función debe estar aquí para poder redirigir al secreto.
    public void borrar_salida(View vista){
        Utilidades.borrarEditText(this, R.id.etSalida);
        secretoPulsado();
    }

    // Borra el portapapeles desde cualquier fragment.
    public void borrar_portapapeles(View vista){
        new AlertDialog.Builder(this)
                .setCancelable(true)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ClipboardManager clipBoard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                        ClipData data = ClipData.newPlainText("", "");
                        clipBoard.setPrimaryClip(data);
                        Toast.makeText(getApplicationContext(), R.string.forgotten, Toast.LENGTH_LONG).show();
                    }
                })
                .setMessage(R.string.w_forget)
                .show();

    }

    // Comparte la Salida de AES
    public void compartir(View vista){
        TextView teCifrado = (TextView) findViewById(R.id.etSalida);
        if(!teCifrado.getText().toString().isEmpty()) {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, "Cifrado desde mrCiphER= mediante AES: "+teCifrado.getText().toString());
            startActivity(Intent.createChooser(intent, "Share with"));
        }else{
            Toast.makeText(this, R.string.w_compartir, Toast.LENGTH_LONG).show();
        }
    }

    // Comparte la salida de DES
    public void compartir_salida_des(View vista){
        TextView teCifrado = (TextView) findViewById(R.id.etDESsalida);
        if(!teCifrado.getText().toString().isEmpty()) {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, "Cifrado desde mrCiphER= mediante DES: "+teCifrado.getText().toString());
            startActivity(Intent.createChooser(intent, "Share with"));
        }else{
            Toast.makeText(this, R.string.w_compartir, Toast.LENGTH_LONG).show();
        }
    }

    // Comparte la salida de Vigenere
    public void compartir_salida_vig(View vista){
        TextView teCifrado = (TextView) findViewById(R.id.etVigsalida);
        if(!teCifrado.getText().toString().isEmpty()) {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, "Cifrado desde mrCiphER= mediante Vigenere: "+teCifrado.getText().toString());
            startActivity(Intent.createChooser(intent, "Share with"));
        }else{
            Toast.makeText(this, R.string.w_compartir, Toast.LENGTH_LONG).show();
        }
    }

    // Comparte la salida de Triple DES
    public void compartir_salida_triple_des(View vista){
        TextView teCifrado = (TextView) findViewById(R.id.et3DESsalida);
        if(!teCifrado.getText().toString().isEmpty()) {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, "Cifrado desde mrCiphER= mediante 3DES: "+teCifrado.getText().toString());
            startActivity(Intent.createChooser(intent, "Share with"));
        }else{
            Toast.makeText(this, R.string.w_compartir, Toast.LENGTH_LONG).show();
        }
    }

    // Comparte la salida de Triple DES
    public void compartir_salida_rsa(View vista){
        TextView teCifrado = (TextView) findViewById(R.id.etCRSA);
        if(!teCifrado.getText().toString().isEmpty()) {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, "Cifrado desde mrCiphER= mediante RSA: "+teCifrado.getText().toString());
            startActivity(Intent.createChooser(intent, "Share with"));
        }else{
            Toast.makeText(this, R.string.w_compartir, Toast.LENGTH_LONG).show();
        }
    }

}


