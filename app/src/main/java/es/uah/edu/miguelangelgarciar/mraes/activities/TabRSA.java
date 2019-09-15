package es.uah.edu.miguelangelgarciar.mraes.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import es.uah.edu.miguelangelgarciar.mraes.objects.Utilidades;
import es.uah.edu.miguelangelgarciar.mraes.R;

/**
 * Created by miguelangel.garciar on 18/03/2018.
 */

public class TabRSA extends Fragment implements View.OnClickListener{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance){
        View rootView = inflater.inflate(R.layout.fragment_rsa, container, false);

        // Indicamos que la acción de los botones se encuentre en esta misma clase, no en MainActivity.
        Button b = (Button) rootView.findViewById(R.id.bRSAborrarPWD);
        b.setOnClickListener(this);
        b = (Button) rootView.findViewById(R.id.button2);
        b.setOnClickListener(this);
        b = (Button) rootView.findViewById(R.id.button);
        b.setOnClickListener(this);
        b = (Button) rootView.findViewById(R.id.bBorrarRSASalida);
        b.setOnClickListener(this);
        b = (Button) rootView.findViewById(R.id.bCopyRSA);
        b.setOnClickListener(this);
        b = (Button) rootView.findViewById(R.id.bBorrarRSAExport);
        b.setOnClickListener(this);
        Switch s = (Switch) rootView.findViewById(R.id.swRSA);
        s.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View view) {
        // Tratamos cada botón
        switch (view.getId()) {
            case R.id.bRSAborrarPWD: borrar_pwd_rsa(view);
                break;
            case R.id.button2: borrar_entrada_rsa(view);
                break;
            case R.id.bBorrarRSAExport: borrar_export_rsa(view);
                break;
            case R.id.button: pegar_entrada_rsa(view);
                break;
            case R.id.bBorrarRSASalida: borrar_salida_rsa(view);
                break;
            case R.id.bCopyRSA: copiar_rsa(view);
                break;
            case R.id.swRSA: cambiar_rsa(view);
                break;
        }
    }


    //Se copia el resultado de la encriptación (la salida) en el campo de entrada.
    public void cambiar_rsa(View vista){
        Utilidades.cambiarCifrarDescifrar(getActivity(), R.id.swRSA, R.id.bExeRSA, R.id.teMensajeRSA, R.id.etMRSA, R.id.etCRSA);
    }

    public void pegar_entrada_rsa(View vista){ Utilidades.pegarEditText(getActivity(), R.id.etMRSA); }

    public void borrar_entrada_rsa(View vista){ Utilidades.borrarEditText(getActivity(), R.id.etMRSA); }

    public void borrar_export_rsa(View vista){ Utilidades.borrarEditText(getActivity(), R.id.etExportar); }

    public void borrar_pwd_rsa(View vista){ Utilidades.borrarEditText(getActivity(), R.id.etRSApwd); }

    public void borrar_salida_rsa(View vista){ Utilidades.borrarEditText(getActivity(), R.id.etCRSA); }

    //Copia el resultado de la salida en el portapapeles.
    public void copiar_rsa(View vista){ Utilidades.copiarEditText(getActivity(), R.id.etCRSA); }

    public void ponerRSATerceros(View vista){
        EditText et = (EditText) getActivity().findViewById(R.id.etRSApwd);
        et.setText("");
        et.setEnabled(true);
    }

}
