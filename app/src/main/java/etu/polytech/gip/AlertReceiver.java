package etu.polytech.gip;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Vibrator;

public class AlertReceiver extends BroadcastReceiver {

    @Override

    public void onReceive(Context context, Intent intent) {
        Boolean entrer = intent.getBooleanExtra(LocationManager.KEY_PROXIMITY_ENTERING, true);
        if (entrer){
            Vibrator levibreur = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
            levibreur.vibrate(1000);
        }
    }
}

