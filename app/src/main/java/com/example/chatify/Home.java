package com.example.chatify;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;

public class Home extends AppCompatActivity {

    private Date currentTime;
    private TextView textViewTime;
    private BroadcastReceiver minuteUpdateReceiver;
    private TextView weatherTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        textViewTime = (TextView)findViewById(R.id.textViewTime);
        weatherTextView = (TextView) findViewById(R.id.weatherTextView);

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

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(minuteUpdateReceiver);
    }

    public class WeatherTask extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... urls) {
            String result="";
            URL url;
            HttpURLConnection urlConnection = null;

            try {
                url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int data = reader.read();
                while(data != -1){

                    char current = (char) data;
                    result += current;
                    data = reader.read();
                }
                return result;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            try {
                JSONObject jsonObject = new JSONObject(result);
                String temp = jsonObject.getString("main");
                JSONObject jsonTemp = new JSONObject(temp);
                String currentTemp = jsonTemp.getString("temp");
                weatherTextView.setText(convertTemp(currentTemp));
                Toast.makeText(getApplicationContext(), "Weather Updated Successfully!", Toast.LENGTH_SHORT).show();
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }

    public void weatherRefresh(View view){
        WeatherTask task = new WeatherTask();
        task.execute("http://api.openweathermap.org/data/2.5/weather?q=Lahore&appid=dc31b5af70b15eebcd73af4d546e1e1b");
        //Toast.makeText(getApplicationContext(), "Refreshing Temperature...", Toast.LENGTH_SHORT).show();

    }

    public String convertTemp(String kelvin){

        double kelvinDouble = Double.parseDouble(kelvin);
        double celsius = kelvinDouble - 273.15;
        int celsiusInt = (int) Math.round(celsius);
        String temp = Integer.toString(celsiusInt);
        temp = temp + "C";

        return temp;

    }



}
