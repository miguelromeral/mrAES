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

import es.uah.edu.miguelangelgarciar.mraes.objects.DES;
import es.uah.edu.miguelangelgarciar.mraes.objects.Utilidades;
import es.uah.edu.miguelangelgarciar.mraes.R;

/**
 * Created by miguelangel.garciar on 18/03/2018.
 */

public class TabDES extends Fragment implements View.OnClickListener{

    // Preferencias del fragment DES
    private static String PREFERENCES_DES = "preferncias_des";
    private static String DES_PASSWORD = "des_password";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance){
        View rootView = inflater.inflate(R.layout.fragment_des, container, false);

        // Indicamos que la acción de los botones se encuentre en esta misma clase, no en MainActivity.
        Button b = (Button) rootView.findViewById(R.id.bDESejecutar);
        b.setOnClickListener(this);
        b = (Button) rootView.findViewById(R.id.bDEScopiarsalida);
        b.setOnClickListener(this);
        b = (Button) rootView.findViewById(R.id.bDESgenerarpwd);
        b.setOnClickListener(this);
        b = (Button) rootView.findViewById(R.id.bDESpegarpwd);
        b.setOnClickListener(this);
        b = (Button) rootView.findViewById(R.id.bDESborrarpwd);
        b.setOnClickListener(this);
        b = (Button) rootView.findViewById(R.id.bDESpegarentrada);
        b.setOnClickListener(this);
        b = (Button) rootView.findViewById(R.id.bDESborrarentrada);
        b.setOnClickListener(this);
        b = (Button) rootView.findViewById(R.id.bDESborrarsalida);
        b.setOnClickListener(this);
        b = (Button) rootView.findViewById(R.id.cbDES);
        b.setOnClickListener(this);
        Switch s = (Switch) rootView.findViewById(R.id.swDES);
        s.setOnClickListener(this);

        // Intentamos recuperar los valores de preferencias.
        try{

            SharedPreferences sharedPreferences = getContext().getSharedPreferences(PREFERENCES_DES, Context.MODE_PRIVATE);
            EditText etDESpwd = (EditText)  rootView.findViewById(R.id.etDESpwd);
            String prueba = sharedPreferences.getString(DES_PASSWORD, "");
            etDESpwd.setText(prueba);
        }catch(Exception e){}

        return rootView;
    }


    @Override
    public void onClick(View view) {
        // Tratamos cada botón
        switch (view.getId()) {
            case R.id.bDESejecutar: execute_des(view);
                break;
            case R.id.swDES: cambiar_des(view);
                break;
            case R.id.bDEScopiarsalida: copiar_salida_des(view);
                break;
            case R.id.cbDES: mostrar_ocultar_clave_des(view);
                break;
            case R.id.bDESborrarentrada: borrar_entrada_des(view);
                break;
            case R.id.bDESpegarentrada: pegar_entrada_des(view);
                break;
            case R.id.bDESborrarsalida: borrar_salida_des(view);
                break;
            case R.id.bDESborrarpwd: borrar_pwd_des(view);
                break;
            case R.id.bDESpegarpwd: pegar_pwd_des(view);
                break;
            case R.id.bDESgenerarpwd: generar_pwd_des(view);
                break;
        }
    }

    // Guardamos las preferencias de este fragment
    @Override
    public void onStop(){
        super.onStop();
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(PREFERENCES_DES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        EditText etDESpwd = (EditText) getActivity().findViewById(R.id.etDESpwd);
        if (!etDESpwd.getText().toString().isEmpty()) {
            editor.putString(DES_PASSWORD, etDESpwd.getText().toString());
        }else{
            editor.putString(DES_PASSWORD, "");
        }
        editor.commit();
    }

    //Limpia el campo de entrada.
    public void borrar_pwd_des(View vista){ Utilidades.borrarEditText(getActivity(), R.id.etDESpwd); }

    public void pegar_pwd_des(View vista){  Utilidades.pegarEditText(getActivity(), R.id.etDESpwd); }

    //Muestra u oculta la clave con la que se encriptará el mensaje. Por comodidad del usuario.
    public void mostrar_ocultar_clave_des(View vista){
        Utilidades.mostrar_ocultar_clave(getActivity(), R.id.etDESpwd, R.id.cbDES);
    }

    public void cambiar_des(View vista){
        Utilidades.cambiarCifrarDescifrar(getActivity(), R.id.swDES, R.id.bDESejecutar, R.id.tvDESentrada, R.id.etDESentrada, R.id.etDESsalida);
    }

    public void borrar_entrada_des(View vista){ Utilidades.borrarEditText(getActivity(), R.id.etDESentrada); }

    public void pegar_entrada_des(View vista){ Utilidades.pegarEditText(getActivity(), R.id.etDESentrada); }

    public void borrar_salida_des(View vista){ Utilidades.borrarEditText(getActivity(), R.id.etDESsalida); }

    public void copiar_salida_des(View vista){ Utilidades.copiarEditText(getActivity(), R.id.etDESsalida); }

    // Genera una clave en Base64 para DES
    public void generar_pwd_des(View vista){
        EditText et = (EditText) getActivity().findViewById(R.id.etDESpwd);
        if(et.getText().toString().isEmpty()){
            String clave = Utilidades.claveToString(DES.generarClaveDES());
            et.setText(clave);
        }else {
            // Si ya hay una clave, advertimos que se sobreescribirá.
            new AlertDialog.Builder(getContext())
                    .setCancelable(true)
                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            String clave = Utilidades.claveToString(DES.generarClaveDES());
                            EditText et = (EditText) getActivity().findViewById(R.id.etDESpwd);
                            et.setText(clave);
                        }
                    })
                    .setMessage(R.string.adv_generar_pwd)
                    .show();
        }
    }

    public void execute_des(View vista) {
        TextView tin = (EditText) getActivity().findViewById(R.id.etDESentrada);
        TextView tout = (EditText) getActivity().findViewById(R.id.etDESsalida);
        Switch switch_e_d = (Switch) getActivity().findViewById(R.id.swDES);
        //Obtenemos el mensaje a ejecutar.
        String mensaje = tin.getText().toString().replaceAll("(?m)^[ \t]*\r?\n", "");
        //Obtenemos la clave.
        TextView te = (EditText) getActivity().findViewById(R.id.etDESpwd);

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
            // La clave se rellena con la letra 'a' si no llega a los 11 caracteres.
            String clave = Utilidades.rellena_clave(te.getText().toString(), 11, 'a');
            //Si no hay ningún mensaje, también se advierte al usuario.
            if(mensaje.isEmpty()){
                new AlertDialog.Builder(getContext())
                        .setCancelable(false)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        })
                        .setMessage(R.string.ex_men)
                        .show();
            }else{
                //Desencriptar
                if (switch_e_d.isChecked()) {
                    try {
                        //Desencripta el mensaje y lo muestra
                        String desencriptado = DES.decrypt(mensaje, Utilidades.stringToClave(clave, "DES"));
                        tout.setText(desencriptado);
                    } catch (Exception e) {
                        new AlertDialog.Builder(getContext()).setTitle(R.string.c_no_coincide_cab).setMessage(R.string.c_no_coincide).setPositiveButton("OK", null).show();
                    }
                } else {
                    //Encriptar
                    try {
                        //Encripta el mensaje y lo muestra
                        String encriptado = DES.encrypt(mensaje, Utilidades.stringToClave(clave, "DES"));
                        tout.setText(encriptado);
                    } catch (Exception e) {
                        Toast.makeText(getContext(), R.string.ex_e, Toast.LENGTH_LONG).show();
                    }
                }
            }
        }
    }

}
