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

import es.uah.edu.miguelangelgarciar.mraes.objects.AESenc;
import es.uah.edu.miguelangelgarciar.mraes.objects.Utilidades;
import es.uah.edu.miguelangelgarciar.mraes.R;

/**
 * Created by miguelangel.garciar on 18/03/2018.
 */

public class TabAES extends Fragment implements View.OnClickListener{

    // Preferencias de AES
    private static String PREFERENCES_AES = "preferncias_aes";
    private static String AES_PASSWORD = "aes_password";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance){
        View rootView = inflater.inflate(R.layout.fragment_aes, container, false);

        // Indicamos que las acciones vengan aquí, y no a MainActivity
        Button b = (Button) rootView.findViewById(R.id.cbe);
        b.setOnClickListener(this);
        b = (Button) rootView.findViewById(R.id.bBorrarPWDaes);
        b.setOnClickListener(this);
        b = (Button) rootView.findViewById(R.id.bPegarPWDaes);
        b.setOnClickListener(this);
        b = (Button) rootView.findViewById(R.id.button4);
        b.setOnClickListener(this);
        b = (Button) rootView.findViewById(R.id.button5);
        b.setOnClickListener(this);
        b = (Button) rootView.findViewById(R.id.bCopy);
        b.setOnClickListener(this);
        b = (Button) rootView.findViewById(R.id.bExe);
        b.setOnClickListener(this);
        Switch s = (Switch) rootView.findViewById(R.id.switch2);
        s.setOnClickListener(this);

        // Intentamos recuperar las preferencias
        try{

            SharedPreferences sharedPreferences = getContext().getSharedPreferences(PREFERENCES_AES, Context.MODE_PRIVATE);
            EditText etpwd = (EditText) rootView.findViewById(R.id.etPublica);
            String prueba = sharedPreferences.getString(AES_PASSWORD, "");
            etpwd.setText(prueba);
        }catch(Exception e){        }

        return rootView;
    }

    @Override
    public void onStop(){
        super.onStop();
        // Guardamos los valores de las preferencias
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(PREFERENCES_AES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        EditText etpwd = (EditText) getActivity().findViewById(R.id.etPublica);
        if (!etpwd.getText().toString().isEmpty()) {
            editor.putString(AES_PASSWORD, etpwd.getText().toString());
        }else{
            editor.putString(AES_PASSWORD, "");
        }
        editor.commit();
    }

    @Override
    public void onClick(View view) {
        // Tratamos cada botón a su manera
        switch (view.getId()) {
            case R.id.cbe: mostrar_ocultar_clave_aes(view);
                break;
            case R.id.bBorrarPWDaes: borrar_pwd_aes(view);
                break;
            case R.id.bPegarPWDaes: pegar_pwd_aes(view);
                break;
            case R.id.button4: borrar_entrada(view);
                break;
            case R.id.button5: pegar_entrada(view);
                break;
            case R.id.bCopy: copiar(view);
                break;
            case R.id.bExe: execute(view);
                break;
            case R.id.switch2: cambiar(view);
                break;
        }
    }


    //Realiza el encriptado/desencriptado
    public void execute(View vista) {
        TextView tin = (EditText) getActivity().findViewById(R.id.etMensaje);
        TextView tout = (EditText) getActivity().findViewById(R.id.etSalida);
        Switch switch_e_d = (Switch) getActivity().findViewById(R.id.switch2);
        //Obtenemos el mensaje a ejecutar.
        String mensaje = tin.getText().toString().replaceAll("(?m)^[ \t]*\r?\n", "");
        //Obtenemos la clave.
        TextView te = (EditText) getActivity().findViewById(R.id.etPublica);

        //Si no se ha introducido ninguna clave, se advierte al usuario.
        if(te.getText().toString().isEmpty()){
            new AlertDialog.Builder(getContext())
                    .setCancelable(false)
                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        }
                    })
                    .setMessage(R.string.w_falta_e)
                    .show();
        }else {
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
                //Rellenamos la clave par que ocupe 16 bytes.
                String clave = Utilidades.rellena_clave(te.getText().toString(), 16, ' ');
                //te.setText(quitaEspacios(clave));
                //Desencriptar
                if (switch_e_d.isChecked()) {
                    try {
                        //Desencripta el mensaje y lo muestra
                        String desencriptado = AESenc.decrypt(mensaje, clave);
                        tout.setText(desencriptado);
                    } catch (Exception e) {
                        new AlertDialog.Builder(getContext()).setTitle(R.string.c_no_coincide_cab).setMessage(R.string.c_no_coincide).setPositiveButton("OK", null).show();
                        //new AlertDialog.Builder(getContext()).setTitle(e.toString()).setMessage(e.getMessage()).setPositiveButton("OK", null).show();
                    }
                } else {
                    //Encriptar
                    try {
                        //Encripta el mensaje y lo muestra
                        String encriptado = AESenc.encrypt(mensaje, clave);
                        tout.setText(encriptado);
                    } catch (Exception e) {
                        Toast.makeText(getContext(), R.string.ex_e, Toast.LENGTH_LONG).show();
                    }
                }
            }
        }
    }

    //Cogemos el primer elemento del portapapeles y lo ponemos en la entrada. Función hecha para la comodidad
    // del usuario.
    public void pegar_entrada(View vista){ Utilidades.pegarEditText(getActivity(), R.id.etMensaje); }

    public void pegar_pwd_aes(View vista){ Utilidades.pegarEditText(getActivity(), R.id.etPublica); }

    public void borrar_pwd_aes(View vista){ Utilidades.borrarEditText(getActivity(), R.id.etPublica); }

    public void mostrar_ocultar_clave_aes(View vista){
        Utilidades.mostrar_ocultar_clave(getActivity(), R.id.etPublica, R.id.cbe);
    }

    //Se copia el resultado de la encriptación (la salida) en el campo de entrada.
    public void cambiar(View vista){
        Utilidades.cambiarCifrarDescifrar(getActivity(), R.id.switch2, R.id.bExe, R.id.tvED, R.id.etMensaje, R.id.etSalida);
    }

    //Copia el resultado de la salida en el portapapeles.
    public void copiar(View vista){ Utilidades.copiarEditText(getActivity(), R.id.etSalida); }

    public void borrar_entrada(View vista){ Utilidades.borrarEditText(getActivity(), R.id.etMensaje); }


}
