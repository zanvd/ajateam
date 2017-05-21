package ajateam.ajapp;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Fragment;
import android.app.ListActivity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.ListFragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;

import org.w3c.dom.Text;

import java.sql.Time;
import java.util.Calendar;

public class FragmentPagerSupport extends AppCompatActivity {

    AlarmManager alarmManager;
    TimePicker alarmTimePicker;
    TextView updateStatus;
    Context context;
    PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_pager);

        this.context = this;

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(new CustomPagerAdapter(this));

        // Watch for button clicks.
        ImageButton button = (ImageButton) findViewById(R.id.waker);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(0);

                alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                alarmTimePicker = (TimePicker) findViewById(R.id.alarmOn);
                updateStatus = (TextView) findViewById(R.id.updateText);
                setAlarmText("No alarm is set");

                final Calendar calendar = Calendar.getInstance();

                final Intent intent = new Intent(context, AlarmReceiver.class);

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

        });
        button = (ImageButton)findViewById(R.id.health);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(1);
            }
        });
        button = (ImageButton)findViewById(R.id.morning_activity);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(2);
            }
        });
        button = (ImageButton)findViewById(R.id.commute);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(3);
            }
        });
    }
//
//    public void setAlarmText(String output) {
//        updateStatus.setText(output);
//    }
}