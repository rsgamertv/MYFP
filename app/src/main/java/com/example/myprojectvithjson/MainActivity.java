package com.example.myprojectvithjson;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.PixelCopy;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private EditText EditT;
    private TextView TempV;
    private TextView NameV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EditT = findViewById(R.id.editTextText);
        TempV = findViewById(R.id.tempText);
        NameV = findViewById(R.id.NameCity);
    }

    public void LoopaClick(View view) {
        if(EditT.getText().toString().trim().equals(""))
            Toast.makeText(MainActivity.this, R.string.UserNoInput, Toast.LENGTH_LONG).show();
        else{
             String City = EditT.getText().toString();
             String Key = "da8543a4c2607ca684b97e51701c1aa2";
             String Html = "https://api.openweathermap.org/data/2.5/weather?q=" + City + "&appid=" + Key + "&units=metric&lang=ru";
             new GetUrlData().execute(Html);
        }
    }
    private class GetUrlData extends AsyncTask<String, String, String> {
                protected void onPreExecute(){
                super.onPreExecute();
                NameV.setText("Ждите");
                }

        @Override
        protected String doInBackground(String... strings) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            try {
                URL url = new URL(strings[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));
                StringBuffer buffer = new StringBuffer();
                String line = "";
                while((line = reader.readLine()) != null)
                    buffer.append(line).append("\n");
                return buffer.toString();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if(connection != null)
                    connection.disconnect();

                try {
                    if (reader != null)
                        reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }
        @SuppressLint("SetTextI18n")
        @Override
        protected void onPostExecute(String result){
            super.onPostExecute(result);
            try {
                JSONObject obj =  new JSONObject(result);
                String UserText = EditT.getText().toString();
                TempV.setText(obj.getJSONObject("main").getInt("temp") + "°");
                NameV.setText(UserText);
                NameV.setText(obj.getJSONObject("name").length());
            } catch (JSONException e) {
                e.printStackTrace();
            }
    }
    }
    }

