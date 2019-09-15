package es.uah.edu.miguelangelgarciar.mraes;

import android.app.Application;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

/**
 * Created by miguelangel.garciar on 15/04/2018.
 */

public class App extends Application {

    @Override
    public void onCreate(){
        super.onCreate();
        final FirebaseRemoteConfig remoteConfig = FirebaseRemoteConfig.getInstance();

    /*    Map<String, Object> defaultValue = new HashMap<>();
        defaultValue.put(UpdateHelper.KEY_UPDATE_ENABLE, false);
        defaultValue.put(UpdateHelper.KEY_UPDATE_URL, "1.0");
        defaultValue.put(UpdateHelper.KEY_UPDATE_VERSION, "https://um62b.app.goo.gl/mrCiphER");

        remoteConfig.setDefaults(defaultValue);
   */    // remoteConfig.fetch()
        remoteConfig.fetch(120) // Fetch data from Firebase every X seconds . En realidad deberia ser algo mas grande, como un minuto.
        .addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    remoteConfig.activateFetched();
                }
            }
        });

    }

}
