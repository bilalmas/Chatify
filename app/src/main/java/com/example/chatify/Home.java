package com.example.chatify;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

public class Home extends AppCompatActivity {

    private Date currentTime;
    private TextView textViewTime;
    private BroadcastReceiver minuteUpdateReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        textViewTime = (TextView)findViewById(R.id.textViewTime);
       // currentTime = Calendar.getInstance().getTime();
        //textViewTime.setText(currentTime.toString());
    }

    public void startTimeUpdater() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_TIME_TICK);
        minuteUpdateReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                currentTime = Calendar.getInstance().getTime();
                textViewTime.setText(currentTime.toString());

            }
        };
        registerReceiver(minuteUpdateReceiver, intentFilter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        startTimeUpdater();
    }



}
