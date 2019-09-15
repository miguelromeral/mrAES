package es.uah.edu.miguelangelgarciar.mraes.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import es.uah.edu.miguelangelgarciar.mraes.R;

/**
 * Created by Miguel on 16/07/2017.
 */

public class Novedades extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news);
        // Ponemos el botón de atras en la barra.
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.action_novedades);
    }
}
