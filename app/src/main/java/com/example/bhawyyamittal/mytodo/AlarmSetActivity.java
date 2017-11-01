package com.example.bhawyyamittal.mytodo;
import android.app.AlarmManager;
import java.util.Calendar;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import db.TaskDbHelper;

public class AlarmSetActivity extends AppCompatActivity {

    EditText description;
    Button add;
    TimePicker timePicker;
    int hour;
    int minute;
    AlarmManager alarmManager;
    PendingIntent pendingIntent;
    private TaskDbHelper mHelper;
    String task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_set);
        description = (EditText) findViewById(R.id.descriptionEditText);
        task = description.getText().toString();
        add = (Button) findViewById(R.id.addButton);
        timePicker = (TimePicker) findViewById(R.id.timePicker);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        add.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                hour = timePicker.getCurrentHour();
                minute = timePicker.getCurrentMinute();
                //insertDB();
                Log.d("MyActivity", "Alarm On");
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, hour);
                calendar.set(Calendar.MINUTE, minute);
                Intent myIntent = new Intent(AlarmSetActivity.this, MyBroadcastReceiver.class);
                pendingIntent = PendingIntent.getBroadcast(AlarmSetActivity.this, 0, myIntent, 0);
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY, pendingIntent);
                //  Toast.makeText(this,"Alarm is set", Toast.LENGTH_LONG).show();
                //        Toast.makeText(this,"Alarm is set", Toast.LENGTH_LONG).show();

                Intent intent=new Intent();
                intent.putExtra("MESSAGE","Alarm is set");
                intent.putExtra("TASK",task);
                intent.putExtra("HOUR",hour);
                intent.putExtra("MINUTE",minute);
                setResult(2,intent);
                finish();
            }
        });


    }




}

