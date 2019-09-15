package es.uah.edu.miguelangelgarciar.mraes.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import es.uah.edu.miguelangelgarciar.mraes.objects.Utilidades;
import es.uah.edu.miguelangelgarciar.mraes.objects.Vigenere;
import es.uah.edu.miguelangelgarciar.mraes.R;

/**
 * Created by miguelangel.garciar on 18/03/2018.
 */

public class TabVigenere extends Fragment implements View.OnClickListener{

    //Valores para las preferencias
    private static String PREFERENCES_VIG = "preferncias_vig";
    private static String VIG_PASSWORD = "vig_password";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance){
        View rootView = inflater.inflate(R.layout.fragment_vigenere, container, false);

        // Indicamos que el listener de cada uno de los botones es el propio fragment (luego los tratamos)
        Button b = (Button) rootView.findViewById(R.id.bVigejecutar);
        b.setOnClickListener(this);
        b = (Button) rootView.findViewById(R.id.bVigcopiarsalida);
        b.setOnClickListener(this);
        b = (Button) rootView.findViewById(R.id.bVigpegarpwd);
        b.setOnClickListener(this);
        b = (Button) rootView.findViewById(R.id.bVigborrarpwd);
        b.setOnClickListener(this);
        b = (Button) rootView.findViewById(R.id.bVigpegarentrada);
        b.setOnClickListener(this);
        b = (Button) rootView.findViewById(R.id.bVigborrarentrada);
        b.setOnClickListener(this);
        b = (Button) rootView.findViewById(R.id.bVigborrarsalida);
        b.setOnClickListener(this);
        b = (Button) rootView.findViewById(R.id.cbVig);
        b.setOnClickListener(this);
        b = (Button) rootView.findViewById(R.id.bVigAyuda);
        b.setOnClickListener(this);
        Switch s = (Switch) rootView.findViewById(R.id.swVig);
        s.setOnClickListener(this);

        // Intentamos recuperar los valores de las preferencias compartidas (la contraseña)
        try{
            SharedPreferences sharedPreferences = getContext().getSharedPreferences(PREFERENCES_VIG, Context.MODE_PRIVATE);
            EditText etDESpwd = (EditText)  rootView.findViewById(R.id.etVigpwd);
            String prueba = sharedPreferences.getString(VIG_PASSWORD, "");
            etDESpwd.setText(prueba);
        }catch(Exception e){}

        return rootView;
    }

    @Override
    public void onClick(View view) {
        // En función de qué botón se ha pulsado, ejecuta la acción correcta:
        switch (view.getId()) {
            case R.id.bVigejecutar: execute_vigenere(view);
                break;
            case R.id.swVig: cambiar_vigenere(view);
                break;
            case R.id.bVigcopiarsalida: copiar_salida_vigenere(view);
                break;
            case R.id.cbVig: mostrar_ocultar_clave_vigenere(view);
                break;
            case R.id.bVigborrarentrada: borrar_entrada_vigenere(view);
                break;
            case R.id.bVigpegarentrada: pegar_entrada_vigenere(view);
                break;
            case R.id.bVigborrarsalida: borrar_salida_vigenere(view);
                break;
            case R.id.bVigborrarpwd: borrar_pwd_vigenere(view);
                break;
            case R.id.bVigpegarpwd: pegar_pwd_vigenere(view);
                break;
            case R.id.bVigAyuda: ayuda_vignere(view);
                break;
        }
    }

    // Muestra el password en plano o lo oculta (en función del estado del check)
    public void mostrar_ocultar_clave_vigenere(View vista){
        Utilidades.mostrar_ocultar_clave(getActivity(), R.id.etVigpwd, R.id.cbVig);
    }

    // Muestra un aviso de que se puede conseguir un cifrado Cesar
    public void ayuda_vignere(View vista){
        new AlertDialog.Builder(getContext())
            .setCancelable(true)
            .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                }
            })
            .setMessage(R.string.w_cesar)
            .show();
    }

    public void borrar_entrada_vigenere(View vista){ Utilidades.borrarEditText(getActivity(), R.id.etVigentrada); }

    public void pegar_entrada_vigenere(View vista){ Utilidades.pegarEditText(getActivity(), R.id.etVigentrada); }

    public void borrar_salida_vigenere(View vista){ Utilidades.borrarEditText(getActivity(), R.id.etVigsalida); }

    public void borrar_pwd_vigenere(View vista){ Utilidades.borrarEditText(getActivity(), R.id.etVigpwd); }

    public void pegar_pwd_vigenere(View vista){  Utilidades.pegarEditText(getActivity(), R.id.etVigpwd); }

    public void copiar_salida_vigenere(View vista){ Utilidades.copiarEditText(getActivity(), R.id.etVigsalida); }

    // Cambia el botón texto del botón en función de si se cifra o descifra.
    public void cambiar_vigenere(View vista){
        Utilidades.cambiarCifrarDescifrar(getActivity(), R.id.swVig, R.id.bVigejecutar,
                R.id.tvVigentrada, R.id.etVigentrada, R.id.etVigsalida);
    }

    // Realiza el cifrado o descifrado.
    public void execute_vigenere(View view) {
        TextView tin = (EditText) getActivity().findViewById(R.id.etVigentrada);
        final Switch switch_e_d = (Switch) getActivity().findViewById(R.id.swVig);
        //Obtenemos el mensaje a ejecutar.
        final String mensaje = tin.getText().toString().replaceAll("(?m)^[ \t]*\r?\n", "");
        //Obtenemos la clave.
        TextView te = (EditText) getActivity().findViewById(R.id.etVigpwd);

        //Si no se ha introducido ninguna clave, se advierte al usuario.
        if(te.getText().toString().isEmpty()){
            new AlertDialog.Builder(getContext())
                    .setCancelable(false)
                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        }
                    })
                    .setMessage(R.string.w_falta_pwd)
                    .show();
        }else {
            String clave = te.getText().toString().toUpperCase();
            // Comprobamos que la clave solo tiene letras
            if(!clave.matches("[a-zA-Z]+")){
                new AlertDialog.Builder(getContext())
                        .setCancelable(true)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        })
                        .setMessage(R.string.w_pwd_solo_letras)
                        .show();
            }else {
                // Advertimos al usuario que las letras y acentos no serán cifrados.
                if(!Vigenere.solo_letras(mensaje)) {
                    new AlertDialog.Builder(getContext())
                            .setCancelable(true)
                            .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                }

                            })
                            .setMessage(R.string.w_solo_letras)
                            .show();
                }
                 //Si no hay ningún mensaje, también se advierte al usuario.
                if (mensaje.isEmpty()) {
                    new AlertDialog.Builder(getContext())
                            .setCancelable(false)
                            .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                }
                            })
                            .setMessage(R.string.ex_men)
                            .show();
                } else {
                    TextView tout = (EditText) getActivity().findViewById(R.id.etVigsalida);
                    //Desencriptar
                    if (switch_e_d.isChecked()) {
                        try {
                            //Desencripta el mensaje y lo muestra
                            String desencriptado = Vigenere.decrypt(mensaje, clave);
                            tout.setText(desencriptado);
                        } catch (Exception e) {
                            new AlertDialog.Builder(getContext()).setTitle(R.string.c_no_coincide_cab).setMessage(R.string.c_no_coincide).setPositiveButton("OK", null).show();
                        }
                    } else {
                        //Encriptar
                        try {
                            //Encripta el mensaje y lo muestra
                            String encriptado = Vigenere.encrypt(mensaje, clave);
                            tout.setText(encriptado);
                        } catch (Exception e) {
                            Toast.makeText(getContext(), R.string.ex_e, Toast.LENGTH_LONG).show();
                        }
                    }

                }
            }
        }
    }

    @Override
    public void onStop(){
        // Al parar, guardamos los valores de las preferencias
        super.onStop();
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(PREFERENCES_VIG, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        EditText etDESpwd = (EditText) getActivity().findViewById(R.id.etVigpwd);
        if (!etDESpwd.getText().toString().isEmpty()) {
            editor.putString(VIG_PASSWORD, etDESpwd.getText().toString());
        }else{
            editor.putString(VIG_PASSWORD, "");
        }
        editor.commit();
    }

}
