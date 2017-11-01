package com.example.bhawyyamittal.mytodo;



     //   import android.content.BroadcastReceiver;
        import android.content.Context;
        import android.content.Intent;
        import android.media.MediaPlayer;
        import android.provider.Settings;
        import android.support.v4.content.WakefulBroadcastReceiver;
        import android.widget.Toast;

/**
 * Created by BHAWYYA MITTAL on 24-10-2017.
 */

public class MyBroadcastReceiver extends WakefulBroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        MediaPlayer mp = MediaPlayer.create(context, Settings.System.DEFAULT_RINGTONE_URI);
        mp.start();
        Toast.makeText(context,"Alarm Ringing!!", Toast.LENGTH_LONG).show();
    }
}
