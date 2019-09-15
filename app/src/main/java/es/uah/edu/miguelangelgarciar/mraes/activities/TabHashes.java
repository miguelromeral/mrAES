package es.uah.edu.miguelangelgarciar.mraes.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import es.uah.edu.miguelangelgarciar.mraes.objects.Hash;
import es.uah.edu.miguelangelgarciar.mraes.objects.Utilidades;
import es.uah.edu.miguelangelgarciar.mraes.R;

/**
 * Created by miguelangel.garciar on 18/03/2018.
 */

public class TabHashes extends Fragment implements View.OnClickListener{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance){
        View rootView = inflater.inflate(R.layout.fragment_hash, container, false);

        // Indicamos que la acción de los botones se encuentre en esta misma clase, no en MainActivity.
        Button b = (Button) rootView.findViewById(R.id.bBorrarSal);
        b.setOnClickListener(this);
        b = (Button) rootView.findViewById(R.id.bPasteSal);
        b.setOnClickListener(this);
        b = (Button) rootView.findViewById(R.id.bSalt);
        b.setOnClickListener(this);
        b = (Button) rootView.findViewById(R.id.bBorrarEntradaHash);
        b.setOnClickListener(this);
        b = (Button) rootView.findViewById(R.id.bPasteHash);
        b.setOnClickListener(this);
        b = (Button) rootView.findViewById(R.id.bBorrarSalidaHash);
        b.setOnClickListener(this);
        b = (Button) rootView.findViewById(R.id.bCopiarHash);
        b.setOnClickListener(this);
        b = (Button) rootView.findViewById(R.id.bHash);
        b.setOnClickListener(this);
        b = (Button) rootView.findViewById(R.id.bBorrarEntradaHashCompara);
        b.setOnClickListener(this);
        b = (Button) rootView.findViewById(R.id.bPasteHashCompara);
        b.setOnClickListener(this);
        Switch s = (Switch) rootView.findViewById(R.id.swComparaHash);
        s.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View view) {
        // Tratamos cada botón
        switch (view.getId()) {
            case R.id.bBorrarSal: borrar_sal(view);
                break;
            case R.id.bPasteSal: pegar_sal(view);
                break;
            case R.id.bSalt: salt_aleatoria(view);
                break;
            case R.id.bBorrarEntradaHash: borrar_mensaje(view);
                break;
            case R.id.bPasteHash: pegar_mensaje(view);
                break;
            case R.id.bBorrarSalidaHash: borrar_hash(view);
                break;
            case R.id.bCopiarHash: copiar_hash(view);
                break;
            case R.id.bHash: hashear(view);
                break;
            case R.id.bBorrarEntradaHashCompara: borrar_hash_compara(view);
                break;
            case R.id.bPasteHashCompara: pegar_hash_compara(view);
                break;
            case R.id.swComparaHash: mostrarComparaHash(view);
                break;
        }
    }

    // Permite comparar el hash generado con otro
    public void mostrarComparaHash(View vista){
        Switch sw = (Switch) getActivity().findViewById(R.id.swComparaHash);
        LinearLayout l = (LinearLayout) getActivity().findViewById(R.id.lComparaHash);
        if(sw.isChecked()){
            l.setVisibility(View.GONE);
        }else{
            l.setVisibility(View.VISIBLE);
        }
    }

    // Ejecuta el hash
    public void hashear(View vista){
        TextView tin = (EditText) getActivity().findViewById(R.id.etHashPlano);
        TextView tout = (EditText) getActivity().findViewById(R.id.etSalidaHash);
        TextView ts = (EditText) getActivity().findViewById(R.id.etSalt);
        // Obtenemos el algoritmo
        Spinner algoritmo = (Spinner) getActivity().findViewById(R.id.spinnerHash);
        // Si no hay mensaje, no hace nada.
        if(tin.getText().toString().isEmpty()) {
            new AlertDialog.Builder(getContext())
                    .setCancelable(false)
                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        }
                    })
                    .setMessage(R.string.ex_men)
                    .show();
        }else{
            // Cogemos el mensaje y salt (si la hay)
            String mensaje = tin.getText().toString();
            String sal = "";
            if(!ts.getText().toString().isEmpty()){
                sal = ts.getText().toString();
            }
            // Generamos el hash y lo mostramos.
            String hasheado = Hash.generateHash(mensaje,sal,algoritmo.getSelectedItem().toString()).toUpperCase();
            tout.setText("["+sal+","+hasheado+"]");

            // Si se quiere comparar con otro...
            Switch sw = (Switch) getActivity().findViewById(R.id.swComparaHash);
            if(!sw.isChecked()){
                TextView tch = (EditText) getActivity().findViewById(R.id.etHashAComparar);
                // Si hay otro hash a comparar...
                if(!tch.getText().toString().isEmpty()){
                    TextView resultado = (TextView) getActivity().findViewById(R.id.teCoincideHash);
                    String hashAComparar = tch.getText().toString();
                    // Si el hash contiene salt en el formato d ela app, se quita la salt y
                    // solo cogemos el hash.
                    if(hashAComparar.contains(",")){
                        String hashACompararModificado = Hash.getHashFromString(hashAComparar);
                        // Si son iguales, se muestra de verde, sino, de rojo
                        tch.setText(hashACompararModificado);
                        if(hasheado.equals(hashACompararModificado)){
                            resultado.setText(R.string.hashCoincide);
                            resultado.setBackgroundColor(Color.GREEN);
                            resultado.setTextColor(Color.BLACK);
                        }else{
                            resultado.setText(R.string.hashNoCoincide);
                            resultado.setBackgroundColor(Color.RED);
                            resultado.setTextColor(Color.WHITE);
                        }
                    }else{
                        // Si son iguales, se muestra de verde, sino, de rojo
                        if(hashAComparar.equals(hasheado)){
                            resultado.setText(R.string.hashCoincide);
                            resultado.setBackgroundColor(Color.GREEN);
                            resultado.setTextColor(Color.BLACK);
                        }else{
                            resultado.setText(R.string.hashNoCoincide);
                            resultado.setBackgroundColor(Color.RED);
                            resultado.setTextColor(Color.WHITE);
                        }
                    }
                }
                else{
                    // Quitamos los colores del aviso
                    reset_hash_comparar(vista);
                }
            }else{
                //Quitamos los colores del aviso.
                reset_hash_comparar(vista);
            }
        }
    }

    // Quitamos los colores del aviso.
    public void reset_hash_comparar(View vista){
        TextView resultado = (TextView) getActivity().findViewById(R.id.teCoincideHash);
        resultado.setBackgroundColor(Color.TRANSPARENT);
        resultado.setTextColor(Color.WHITE);
        resultado.setText("");
    }

    public void borrar_sal(View vista){ Utilidades.borrarEditText(getActivity(), R.id.etSalt); }

    public void borrar_hash_compara(View vista){ Utilidades.borrarEditText(getActivity(), R.id.etHashAComparar); }

    public void borrar_mensaje(View vista){ Utilidades.borrarEditText(getActivity(), R.id.etHashPlano); }

    public void borrar_hash(View vista){ Utilidades.borrarEditText(getActivity(), R.id.etSalidaHash); }

    public void pegar_sal(View vista){ Utilidades.pegarEditText(getActivity(), R.id.etSalt); }

    public void pegar_hash_compara(View vista){ Utilidades.pegarEditText(getActivity(), R.id.etHashAComparar); }

    public void pegar_mensaje(View vista){ Utilidades.pegarEditText(getActivity(), R.id.etHashPlano); }

    public void copiar_hash(View vista){ Utilidades.copiarEditText(getActivity(), R.id.etSalidaHash); }

    // Generamos una salt aleatoria
    public void salt_aleatoria(View vista){
        TextView ts = (EditText) getActivity().findViewById(R.id.etSalt);
        ts.setText(Hash.randomSalt().toString());
    }

}
