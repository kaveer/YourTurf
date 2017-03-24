package com.kavsoftware.kaveer.yourturf.Activity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.kavsoftware.kaveer.yourturf.R;
import com.kavsoftware.kaveer.yourturf.ViewModel.HomeScreen.HomeScreenViewModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class SplashScreen extends AppCompatActivity {

    HttpURLConnection connection = null;
    BufferedReader reader = null;
    HomeScreenViewModel home = new HomeScreenViewModel();
    String homeUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_splash_screen);

        homeUrl = this.getBaseContext().getResources().getString(R.string.GetHomeScreenFromApEndPoint);
        String result = "";

        if(isNetworkConnected()){
           result = GetHomeScreenDetails();
        }
        else {
            Toast messageBox = Toast.makeText(this , "No internet connection" , Toast.LENGTH_LONG);
            messageBox.show();
        }

        if(result == ""){
            Toast messageBox = Toast.makeText(this , "Error occurred while loading" , Toast.LENGTH_LONG);
            messageBox.show();
        }
        else {
            home = DeserializeJsonObject(result);
            NavigateToMainActivity(home);
        }

    }

    private void NavigateToMainActivity(HomeScreenViewModel home) {
        Intent i = new Intent(SplashScreen.this, MainActivity.class);
        i.putExtra("HomeScreenDetails",  home);
        startActivity(i);
    }

    private HomeScreenViewModel DeserializeJsonObject(String result) {
        HomeScreenViewModel deserializeResult = new HomeScreenViewModel();

        try {
            JSONObject parentObject = new JSONObject(result);
            deserializeResult.setIsRaceCardAvailable(parentObject.getBoolean("isRaceCardAvailable"));
            deserializeResult.setMeetingNumber(parentObject.getInt("meetingNumber"));
            deserializeResult.setNotification(parentObject.getBoolean("notification"));
            deserializeResult.setNotificationMessage(parentObject.getString("notificationMessage"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return deserializeResult;
    }

    private String GetHomeScreenDetails() {
        String result = "";

        try {
            result =  new GetHomeScreenFromApi().execute(homeUrl).get();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return result;
    }

    protected boolean isNetworkConnected() {
        try {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            return (mNetworkInfo == null) ? false : true;

        }catch (NullPointerException e){
            return false;
        }
    }

    private class GetHomeScreenFromApi extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... params) {
            String result = "";
            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));
                StringBuffer buffer = new StringBuffer();

                String line ="";

                while ((line = reader.readLine()) != null){
                    buffer.append(line);
                }

                String  jsonObjectHome = buffer.toString();
                result = jsonObjectHome;

            } catch (Exception e) {

                Log.e("MainActivity", e.getMessage(), e);

            } finally {
                if(connection != null) {
                    connection.disconnect();
                }
                try {
                    if(reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return result;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
        }

    }
}
