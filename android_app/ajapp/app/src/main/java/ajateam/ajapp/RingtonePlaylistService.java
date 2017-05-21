package ajateam.ajapp;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.os.ResultReceiver;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.util.Log;

import java.security.Provider;

/**
 * Created by adamp on 20. 05. 2017.
 */

public class RingtonePlaylistService extends Service {

    MediaPlayer mediaPlayer;
    boolean isRunning;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        boolean getState = intent.getExtras().getBoolean("extra");

        Log.e("State", String.valueOf(getState));

        /*Intent intentMainActivity = new Intent(this.getApplicationContext(), MainActivity.class);
        PendingIntent pendingIntentMainActivity = PendingIntent.getActivity(this, (int) System.currentTimeMillis(),
                intentMainActivity, 0);
        Notification notificationPopUp = new Notification.Builder(this)
                .setContentTitle("Alarm is going off.")
                .setContentIntent(pendingIntentMainActivity)
                .setAutoCancel(false)
                .setVisibility(1)
                .build();

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        notificationManager.notify(0,notificationPopUp);
        */
        if (getState) {
            startId = 1;
        } else {
            startId = 0;
        }

        Log.e("Is running", String.valueOf(isRunning));
        Log.e("StateId", String.valueOf(startId));

        if (!this.isRunning && startId == 1) {

            Log.e("I came here", "gj");

            mediaPlayer = MediaPlayer.create(this, R.raw.alarm);
            mediaPlayer.start();

            this.isRunning = true;

            Intent intentMainActivity = new Intent(this.getApplicationContext(), SnoozeButton.class);
            intentMainActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intentMainActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentMainActivity);

        } else if (this.isRunning && startId == 0) {

            mediaPlayer.stop();
            mediaPlayer.reset();

            this.isRunning = false;

        } else if (!this.isRunning && startId == 0) {

            this.isRunning = false;

        } else if (this.isRunning && startId == 1) {

            this.isRunning = true;

        }

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {

        Log.e("It's going down: ", "ajajajaj");

        super.onDestroy();
        this.isRunning = false;
    }
}
