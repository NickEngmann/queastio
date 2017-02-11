package com.example.quodcertamine.quaestio;

import android.app.ActionBar;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    TextView mTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Remove notification bar
        //this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //set content view AFTER ABOVE sequence (to avoid crash)
        this.setContentView(R.layout.activity_main);
        notifications();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {
            main();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void main(String... args) throws Exception {
        //URL url = new URL("http://stackoverflow.com/questions/2971155");
        URL url = new URL("https://www.reddit.com/r/WouldYouRather/top/.json?limit=1");
        BufferedReader reader = null;
        StringBuilder builder = new StringBuilder();
        try {
            reader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
            for (String line; (line = reader.readLine()) != null;) {
                builder.append(line.trim());
            }
        } finally {
            if (reader != null) try { reader.close(); } catch (IOException logOrIgnore) {}
        }
        String startQuestion = "\"title\": \"";
        String endQuestion = "?\"";
        String partQuestion = builder.substring(builder.indexOf(startQuestion) + startQuestion.length());
        String question = partQuestion.substring(0, partQuestion.indexOf(endQuestion));

        mTextView = (TextView) findViewById(R.id.topwyr);
        mTextView.setText(question);
        String startURL = "\"url\": \"";
        String endURL = "/\"";
        String partURL = builder.substring(builder.indexOf(startURL) + startURL.length());
        String urlGo = partURL.substring(0, partURL.indexOf(endURL));
        System.out.println(urlGo);
        Toast.makeText(getApplicationContext(), urlGo,
                Toast.LENGTH_LONG).show();
        mTextView = (TextView) findViewById(R.id.url);
        mTextView.setText(urlGo);
        mTextView.setMovementMethod(LinkMovementMethod.getInstance());
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.app_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.settings:
                //Toast.makeText(this, "Settings selected", Toast.LENGTH_SHORT).show();
                Intent settingsIntent = new Intent(this, SettingsActivity.class);
                startActivity(settingsIntent);
                break;
        }
        return true;
    }

    public void notifications(){
        // Daily Notifications
        Calendar calender = Calendar.getInstance();
        calender.set(Calendar.HOUR_OF_DAY,07);
        calender.set(Calendar.MINUTE,30);
        Intent intent = new Intent(getApplicationContext(), NotificationReciever.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 100, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calender.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        Toast.makeText(this, "Notifications are On", Toast.LENGTH_SHORT).show();
    }
}
