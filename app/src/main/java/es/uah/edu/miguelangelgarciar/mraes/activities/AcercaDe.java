package es.uah.edu.miguelangelgarciar.mraes.activities;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import es.uah.edu.miguelangelgarciar.mraes.R;

/**
 * Created by Miguel on 16/07/2017.
 */

public class AcercaDe extends AppCompatActivity {

    public static String URL_GITHUB = "https://github.com/miguelromeral/mrAES";
    public static String URL_PRIVACY = "https://github.com/miguelromeral/mrAES/blob/master/politica-privacidad-mrcipher-es.md";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acerca_de);
        // Ponemos el botón de atras en la barra.
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.action_about);
    }

    public void enviar_mail(View vista){
        String mailto = getApplicationContext().getString(R.string.mail_to) +
                "?subject=" + Uri.encode(getApplicationContext().getString(R.string.subject_mail)) +
                "&body=" + Uri.encode(getApplicationContext().getString(R.string.head_mail));

        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setData(Uri.parse(mailto));

        try {
            startActivity(emailIntent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, R.string.w_email, Toast.LENGTH_LONG).show();
        }
    }

    public void ir_github(View vista){
        Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(URL_GITHUB));
        startActivity(myIntent);
    }

    public void ver_privacidad(View vista){
        Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(URL_PRIVACY));
        startActivity(myIntent);
    }
}
