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

import es.uah.edu.miguelangelgarciar.mraes.objects.TripleDES;
import es.uah.edu.miguelangelgarciar.mraes.objects.Utilidades;
import es.uah.edu.miguelangelgarciar.mraes.R;

/**
 * Created by miguelangel.garciar on 18/03/2018.
 */

public class TabTripleDES extends Fragment implements View.OnClickListener{


    private static String PREFERENCES_3DES = "preferncias_3des";
    private static String TripleDES_PASSWORD = "3des_password";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance){
        View rootView = inflater.inflate(R.layout.fragment_3des, container, false);

        Button b = (Button) rootView.findViewById(R.id.b3DESejecutar);
        b.setOnClickListener(this);
        b = (Button) rootView.findViewById(R.id.b3DEScopiarsalida);
        b.setOnClickListener(this);
        b = (Button) rootView.findViewById(R.id.b3DESpegarpwd);
        b.setOnClickListener(this);
        b = (Button) rootView.findViewById(R.id.b3DESborrarpwd);
        b.setOnClickListener(this);
        b = (Button) rootView.findViewById(R.id.b3DESpegarentrada);
        b.setOnClickListener(this);
        b = (Button) rootView.findViewById(R.id.b3DESborrarentrada);
        b.setOnClickListener(this);
        b = (Button) rootView.findViewById(R.id.b3DESborrarsalida);
        b.setOnClickListener(this);
        b = (Button) rootView.findViewById(R.id.cb3DES);
        b.setOnClickListener(this);
        Switch s = (Switch) rootView.findViewById(R.id.sw3DES);
        s.setOnClickListener(this);

        try{

            SharedPreferences sharedPreferences = getContext().getSharedPreferences(PREFERENCES_3DES, Context.MODE_PRIVATE);
            EditText etDESpwd = (EditText)  rootView.findViewById(R.id.et3DESpwd);
            String prueba = sharedPreferences.getString(TripleDES_PASSWORD, "");
            etDESpwd.setText(prueba);
        }catch(Exception e){}

        return rootView;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.b3DESejecutar: execute_triple_des(view);
                break;
            case R.id.sw3DES: cambiar_triple_des(view);
                break;
            case R.id.b3DEScopiarsalida: copiar_salida_triple_des(view);
                break;
            case R.id.cb3DES: mostrar_ocultar_clave_triple_des(view);
                break;
            case R.id.b3DESborrarentrada: borrar_entrada_triple_des(view);
                break;
            case R.id.b3DESpegarentrada: pegar_entrada_triple_des(view);
                break;
            case R.id.b3DESborrarsalida: borrar_salida_triple_des(view);
                break;
            case R.id.b3DESborrarpwd: borrar_pwd_triple_des(view);
                break;
            case R.id.b3DESpegarpwd: pegar_pwd_triple_des(view);
                break;
        }
    }


    public void mostrar_ocultar_clave_triple_des(View vista){
        Utilidades.mostrar_ocultar_clave(getActivity(), R.id.et3DESpwd, R.id.cb3DES);
    }

    public void borrar_entrada_triple_des(View vista){ Utilidades.borrarEditText(getActivity(), R.id.et3DESentrada); }

    public void pegar_entrada_triple_des(View vista){ Utilidades.pegarEditText(getActivity(), R.id.et3DESentrada); }

    public void borrar_salida_triple_des(View vista){ Utilidades.borrarEditText(getActivity(), R.id.et3DESsalida); }

    public void borrar_pwd_triple_des(View vista){ Utilidades.borrarEditText(getActivity(), R.id.et3DESpwd); }

    public void pegar_pwd_triple_des(View vista){  Utilidades.pegarEditText(getActivity(), R.id.et3DESpwd); }

    public void copiar_salida_triple_des(View vista){ Utilidades.copiarEditText(getActivity(), R.id.et3DESsalida); }

    public void cambiar_triple_des(View vista){
        Utilidades.cambiarCifrarDescifrar(getActivity(), R.id.sw3DES, R.id.b3DESejecutar,
                R.id.tv3DESentrada, R.id.et3DESentrada, R.id.et3DESsalida);
    }

    public void execute_triple_des(View view) {
        TextView tin = (EditText) getActivity().findViewById(R.id.et3DESentrada);
        TextView tout = (EditText) getActivity().findViewById(R.id.et3DESsalida);
        Switch switch_e_d = (Switch) getActivity().findViewById(R.id.sw3DES);
        //Obtenemos el mensaje a ejecutar.
        String mensaje = tin.getText().toString().replaceAll("(?m)^[ \t]*\r?\n", "");
        //Obtenemos la clave.
        TextView te = (EditText) getActivity().findViewById(R.id.et3DESpwd);

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
            // String clave = te.getText().toString();
            String clave = Utilidades.rellena_clave(te.getText().toString(), 24, ' ');
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
                        String desencriptado = TripleDES.decrypt(mensaje, clave);
                        tout.setText(desencriptado);
                    } catch (Exception e) {
                        new AlertDialog.Builder(getContext()).setTitle(R.string.c_no_coincide_cab).setMessage(R.string.c_no_coincide).setPositiveButton("OK", null).show();
                    }
                } else {
                    //Encriptar
                    try {
                        //Encripta el mensaje y lo muestra
                        String encriptado = TripleDES.encrypt(mensaje, clave);
                        tout.setText(encriptado);
                    } catch (Exception e) {
                        Toast.makeText(getContext(), R.string.ex_e, Toast.LENGTH_LONG).show();
                    }
                }
            }
        }
    }

    @Override
    public void onStop(){
        super.onStop();
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(PREFERENCES_3DES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        EditText etDESpwd = (EditText) getActivity().findViewById(R.id.et3DESpwd);
        if (!etDESpwd.getText().toString().isEmpty()) {
            editor.putString(TripleDES_PASSWORD, etDESpwd.getText().toString());
        }else{
            editor.putString(TripleDES_PASSWORD, "");
        }
        editor.commit();
    }

}
