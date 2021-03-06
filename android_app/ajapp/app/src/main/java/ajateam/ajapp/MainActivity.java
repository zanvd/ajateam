package ajateam.ajapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    AlarmReceiver alarmReceiver;
    AlarmManager alarmManager;
    TimePicker alarmTimePicker;
    TextView updateStatus = (TextView) findViewById(R.id.updateText);
    Context context;
    PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.context = this;
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmTimePicker = (TimePicker) findViewById(R.id.alarmOn);
//        updateStatus = (TextView) findViewById(R.id.updateText);
        setAlarmText("No alarm is set");

        final Calendar calendar = Calendar.getInstance();

        final Intent intent = new Intent(this.context, AlarmReceiver.class);

        Button alarmOn = (Button) findViewById(R.id.setAlarm);
        alarmOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("setOnClickListener triggered");
                calendar.set(Calendar.HOUR_OF_DAY, alarmTimePicker.getCurrentHour());
                calendar.set(Calendar.MINUTE, alarmTimePicker.getCurrentMinute());

                int hour = alarmTimePicker.getCurrentHour();
                int min = alarmTimePicker.getCurrentMinute();

                setAlarmText("Alarm is set for " + hour + ":" + min);

                intent.putExtra("extra", true);

                pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
            }
        });

        Button alarmOff = (Button) findViewById(R.id.cancelAlarm);
        alarmOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setAlarmText("No alarm is set");

                alarmManager.cancel(pendingIntent);

                intent.putExtra("extra", false);

                sendBroadcast(intent);
            }
        });
    }

    public void setAlarmText(String output) {
        updateStatus.setText(output);
    }
}
