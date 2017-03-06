package com.kavsoftware.kaveer.yourturf.Fragment;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.kavsoftware.kaveer.yourturf.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * A simple {@link Fragment} subclass.
 */
public class Nomination extends Fragment {

    HttpURLConnection connection = null;
    BufferedReader reader = null;
    String line ="";
    String jsonObject;
    int id = 5;

    public Nomination() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("Nomination");
        String url = getActivity().getBaseContext().getResources().getString(R.string.GetNominationFromApEndPoint);

        View view = inflater.inflate(R.layout.fragment_nomination, container, false);




        if(isNetworkConnected()){
              new GetNominationFromApi().execute(GetUrl(GetId(), url));
        }
        else {
            Toast messageBox = Toast.makeText(getActivity() , "No internet connection" , Toast.LENGTH_LONG);
            messageBox.show();
        }

        return view;
    }

    private String GetUrl(int id, String url) {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http");
        builder.appendEncodedPath(url)
                .appendPath(String.valueOf(id));
        String myUrl = builder.build().toString();

        return myUrl;
    }

    private int GetId() {
        Bundle bundle = this.getArguments();
        int id = bundle.getInt("id");

        return id;
    }

    @Override
    public void onStart() {
        super.onStart();
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
            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));
                StringBuffer buffer = new StringBuffer();

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

            try {
                JSONObject parentObject = new JSONObject(result);

            }catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }
}
