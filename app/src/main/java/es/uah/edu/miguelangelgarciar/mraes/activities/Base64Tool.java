package es.uah.edu.miguelangelgarciar.mraes.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import es.uah.edu.miguelangelgarciar.mraes.objects.Utilidades;
import es.uah.edu.miguelangelgarciar.mraes.R;

/**
 * Created by Miguel on 16/07/2017.
 */

public class Base64Tool extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base64tool);
        // Ponemos el botón de atras en la barra.
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.b64_titulo);
    }

    public void convertir(View vista){
        TextView tin = (EditText) findViewById(R.id.et64entrada);
        TextView tout = (EditText) findViewById(R.id.et64salida);
        TextView toutc = (EditText) findViewById(R.id.et64salidaChar);

        if(tin.getText().toString().isEmpty()) {
            new AlertDialog.Builder(this)
                    .setCancelable(false)
                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        }
                    })
                    .setMessage(R.string.ex_men)
                    .show();
        }else{
            try {
                String mensaje = tin.getText().toString();
                tout.setText(Utilidades.base64ToHexadecimal(mensaje));
                toutc.setText(Utilidades.base64ToCharString(mensaje));
            }catch(Exception e){
                new AlertDialog.Builder(this)
                        .setCancelable(false)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        })
                        .setMessage(R.string.ex_b64)
                        .show();
            }
        }

    }

    public void borrar_entrada(View vista){
        Utilidades.borrarEditText(this, R.id.et64entrada);
    }
    public void pegar_entrada(View vista){
        Utilidades.pegarEditText(this, R.id.et64entrada);
    }

    public void borrar_hex(View vista){
        Utilidades.copiarEditText(this, R.id.et64salida);
    }
    public void borrar_char(View vista){
        Utilidades.copiarEditText(this, R.id.et64salidaChar);
    }


}
