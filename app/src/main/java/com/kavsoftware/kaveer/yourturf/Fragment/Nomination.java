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

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * A simple {@link Fragment} subclass.
 */
public class Nomination extends Fragment {


    public Nomination() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("Nomination");

        View view = inflater.inflate(R.layout.fragment_nomination, container, false);


        if(isNetworkConnected()){
              new GetNominationFromApi().execute();
        }
        else {
            Toast messageBox = Toast.makeText(getActivity() , "No internet connection" , Toast.LENGTH_LONG);
            messageBox.show();
        }

        return view;
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

    private class GetNominationFromApi extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            try {


                URL url = new URL("http://lekourse.azurewebsites.net/v1/nominations/5");
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));
                StringBuffer buffer = new StringBuffer();
                String line ="";
                while ((line = reader.readLine()) != null){
                    buffer.append(line);
                }

                String finalJson = buffer.toString();

                //System.out.print(finalJson);



//                System.out.println("Name: ");
//                final String url = "http://lekourse.azurewebsites.net/v1/nominations/5";
//                RestTemplate restTemplate = new RestTemplate();
//                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
//                NominationViewModel nomination = restTemplate.getForObject(url, NominationViewModel.class);
                return finalJson;
            } catch (Exception e) {
                Log.e("MainActivity", e.getMessage(), e);

            }

            return null;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            System.out.print(" ++++++++++++SS success  +++++++++++" + s);

        }

    }
}
