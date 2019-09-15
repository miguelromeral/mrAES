package es.uah.edu.miguelangelgarciar.mraes.activities;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import es.uah.edu.miguelangelgarciar.mraes.R;

/**
 * Created by Miguel on 16/07/2017.
 */

public class Secreto extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_secreto);
        // Ponemos el botón de atras en la barra.
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void copiar_secreto(View vista){
        try {
            TextView tout = (TextView) findViewById(R.id.etSecreto);
            ClipboardManager myClipboard;
            ClipData myClip;
            myClipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
            String text = tout.getText().toString();
            myClip = ClipData.newPlainText("text", text);
            myClipboard.setPrimaryClip(myClip);
            Toast.makeText(this, R.string.secreto7, Toast.LENGTH_SHORT).show();
        }catch(Exception ex){
            Toast.makeText(this, R.string.ex_salida, Toast.LENGTH_LONG).show();
        }
    }
}
