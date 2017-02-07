package com.example.quodcertamine.quaestio;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            main();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void main(String... args) throws Exception {
        URL url = new URL("http://stackoverflow.com/questions/2971155");
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

        String start = "<div class=\"post-text\"><p>";
        String end = "</p>";
        String part = builder.substring(builder.indexOf(start) + start.length());
        String question = part.substring(0, part.indexOf(end));
        System.out.println(question);
        Toast.makeText(getApplicationContext(), question,
                Toast.LENGTH_LONG).show();
    }
}
