package es.uah.edu.miguelangelgarciar.mraes.objects;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.widget.Toast;

import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

import es.uah.edu.miguelangelgarciar.mraes.R;

/**
 * Created by miguelangel.garciar on 15/04/2018.
 */

public class UpdateHelper {
    public static String KEY_UPDATE_ENABLE="is_update";
    public static String KEY_UPDATE_VERSION="version";
    public static String KEY_UPDATE_URL="update_url";

    public interface OnUpdateCheckListener{
        void onUpdateCheckListener(String urlApp);
    }


    public static Builder with(Context context)
    {
        return new Builder(context);
    }

    private OnUpdateCheckListener onUpdateCheckListener;
    private Context context;

    public UpdateHelper (Context context, OnUpdateCheckListener onUpdateCheckListener){
        this.onUpdateCheckListener = onUpdateCheckListener;
        this.context = context;
    }

    public void check(boolean avisar)
    {
   //     Toast.makeText(context, R.string.act_buscando, Toast.LENGTH_LONG).show();
        FirebaseRemoteConfig remoteConfig = FirebaseRemoteConfig.getInstance();
        if(remoteConfig.getBoolean(KEY_UPDATE_ENABLE)){
            try {
                // Version de Firebase
                String currentVersion = remoteConfig.getString(KEY_UPDATE_VERSION);
                // Version de mi app (build.gradle)
                String appVersion = getAppVersion(context);
                // URL que he peusto en Firebase
                String updateUrl = remoteConfig.getString(KEY_UPDATE_URL);

      //          Toast.makeText(context, "Version de Firebase: "+currentVersion, Toast.LENGTH_LONG).show();
      //          Toast.makeText(context, "Mi version: "+appVersion, Toast.LENGTH_LONG).show();


                if (!TextUtils.equals(currentVersion, appVersion) && onUpdateCheckListener != null) {
                    onUpdateCheckListener.onUpdateCheckListener(updateUrl);
                }else {
                    if (avisar) {
                        AlertDialog alertDialog = new AlertDialog.Builder(context)
                                .setMessage(R.string.act_actualizado)
                                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        //dialogInterface.dismiss();
                                    }
                                }).create();
                        alertDialog.show();
                    }
                }
            }catch(Exception e){

                Toast.makeText(context, "Error: "+e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }

    private String getAppVersion(Context context){
        String result="";
        try{
            result = this.context.getPackageManager().getPackageInfo(this.context.getPackageName(), 0).versionName;
            result = result.replaceAll("[a-zA-Z]|-","");
        }catch (PackageManager.NameNotFoundException e){
            e.printStackTrace();
        }
        return result;
    }


    public static class Builder{
        private Context context;
        private OnUpdateCheckListener onUpdateCheckListener;

        public Builder (Context context){
            this.context = context;
        }

        public Builder onUpdateCheck(OnUpdateCheckListener onUpdateCheckListener) {
            this.onUpdateCheckListener = onUpdateCheckListener;
            return this;
        }

        public UpdateHelper build(){
            return new UpdateHelper(context, onUpdateCheckListener);
        }

        public UpdateHelper check(boolean avisar){
            UpdateHelper updateHelper = build();
            updateHelper.check(avisar);
            return updateHelper;
        }
    };

}
