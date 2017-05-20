package ajateam.ajapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by adamp on 20. 05. 2017.
 */

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        boolean getState = intent.getExtras().getBoolean("extra");

        Log.e("The key: ", String.valueOf(getState));

        Intent serviceIntent = new Intent(context, RingtonePlaylistService.class);

        serviceIntent.putExtra("extra", getState);

        context.startService(serviceIntent);

    }
}
