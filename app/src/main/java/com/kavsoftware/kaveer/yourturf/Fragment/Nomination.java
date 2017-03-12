package com.kavsoftware.kaveer.yourturf.Fragment;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.kavsoftware.kaveer.yourturf.R;
import com.kavsoftware.kaveer.yourturf.ViewModel.HomeScreenViewModel;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

/**
 * A simple {@link Fragment} subclass.
 */
public class Nomination extends Fragment {

    HttpURLConnection connection = null;
    BufferedReader reader = null;

    String homeUrl;
    String nominationUrl;

    public Nomination() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("Nomination");
        View view = inflater.inflate(R.layout.fragment_nomination, container, false);

        homeUrl = getActivity().getBaseContext().getResources().getString(R.string.GetHomeScreenFromApEndPoint);
        nominationUrl = getActivity().getBaseContext().getResources().getString(R.string.GetNominationFromApEndPoint);

        String result = GetNomination();
        if(result == ""){
            Toast messageBox = Toast.makeText(getActivity() , "No nomination available please check race card" , Toast.LENGTH_LONG);
            messageBox.show();
        }
        else {
            Toast messageBox = Toast.makeText(getActivity() , result , Toast.LENGTH_LONG);
            messageBox.show();
        }

        return view;
    }

    private String GetNomination() {
        int id;
        String result = "";
        Bundle bundle = this.getArguments();

        try {
            if(isNetworkConnected()){
                if (bundle == null){
                    result =   new GetHomeScreenFromApi().execute(homeUrl, nominationUrl).get();
                }
                else {
                    id = bundle.getInt("id");
                    result = new GetNominationFromApi().execute(nominationUrl+id).get();
                }
            }
            else {
                Toast messageBox = Toast.makeText(getActivity() , "No internet connection" , Toast.LENGTH_LONG);
                messageBox.show();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return result;
    }

    protected boolean isNetworkConnected() {
        try {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            return (mNetworkInfo == null) ? false : true;

        }catch (NullPointerException e){
            return false;
        }
    }

    private class GetNominationFromApi extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... params) {
            String jsonObject = "";
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

                jsonObject = buffer.toString();

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

            return jsonObject;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            Toast messageBox = Toast.makeText(getActivity() , "Loading please wait.." , Toast.LENGTH_LONG);
            messageBox.show();
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

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
                result = GetNomination(params[1], jsonObjectHome);

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

        private String GetNomination(String param, String jsonObjectHome) {
            String jsonObjectNomination = "";
            HomeScreenViewModel home = new HomeScreenViewModel();

            try {
                JSONObject parentObject = new JSONObject(jsonObjectHome);
                home.setIsRaceCardAvailable(parentObject.getBoolean("isRaceCardAvailable"));
                home.setMeetingNumber(parentObject.getInt("meetingNumber"));

               // home.setIsRaceCardAvailable(true);

                if(home.getIsRaceCardAvailable() == false){

                    URL url = new URL(param + home.getMeetingNumber());
                    connection = (HttpURLConnection) url.openConnection();
                    connection.connect();
                    InputStream stream = connection.getInputStream();
                    reader = new BufferedReader(new InputStreamReader(stream));
                    StringBuffer bufferz = new StringBuffer();

                    String line ="";

                    while ((line = reader.readLine()) != null){
                        bufferz.append(line);
                    }

                    jsonObjectNomination = bufferz.toString();
                }

            } catch (Exception e) {
                Log.e("MainActivity", e.getMessage(), e);
            }

            return jsonObjectNomination;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            Toast messageBox = Toast.makeText(getContext() , "Loading please wait.." , Toast.LENGTH_SHORT);
            messageBox.show();
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
        }

    }



}
