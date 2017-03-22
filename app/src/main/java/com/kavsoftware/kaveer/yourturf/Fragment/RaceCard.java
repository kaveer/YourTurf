package com.kavsoftware.kaveer.yourturf.Fragment;


import android.content.Context;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.kavsoftware.kaveer.yourturf.CustomListView.RaceCardListView.RaceCardListView;
import com.kavsoftware.kaveer.yourturf.R;
import com.kavsoftware.kaveer.yourturf.ViewModel.HomeScreen.HomeScreenViewModel;
import com.kavsoftware.kaveer.yourturf.ViewModel.RaceCard.Race;
import com.kavsoftware.kaveer.yourturf.ViewModel.RaceCard.RaceCardViewModel;
import com.kavsoftware.kaveer.yourturf.ViewModel.RaceCard.RaceHorse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * A simple {@link Fragment} subclass.
 */
public class RaceCard extends Fragment {
    HttpURLConnection connection = null;
    BufferedReader reader = null;
    RaceCardViewModel raceCardList = new RaceCardViewModel();
    String homeUrl;
    String raceCardUrl;

    ListView raceCardListView;

    RaceCardListView adapter;

    public RaceCard() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("Race Card");
        View view = inflater.inflate(R.layout.fragment_race_card, container, false);

        homeUrl = getActivity().getBaseContext().getResources().getString(R.string.GetHomeScreenFromApEndPoint);
        raceCardUrl = getActivity().getBaseContext().getResources().getString(R.string.GetRaceCardFromApEndPoint);

        String result = GetRaceCard();
        if(result == ""){
            Toast messageBox = Toast.makeText(getActivity() , "No race card available" , Toast.LENGTH_LONG);
            messageBox.show();
        }
        else {
            DeserializeJsonObject(result);

            GenerateListView(view);
            
        }

        return view;
    }

    private void GenerateListView(View view) {
        Resources res = getResources();
        raceCardListView = (ListView)view.findViewById(R.id.ListViewRaceCard);

        adapter = new RaceCardListView(getActivity(), raceCardList.getRace() ,res);
        raceCardListView.setAdapter( adapter );
    }

    public void onItemClick(int mPosition, ArrayList data)
    {
        Object n = data.get(mPosition);

        int f = mPosition;
    }

    private void DeserializeJsonObject(String result) {
        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONArray raceDetailsObject = jsonObject.getJSONArray("race");

            raceCardList.setRaceCount(jsonObject.getInt("raceCount"));

            ArrayList<Race> races = new ArrayList<>();

            for(int i=0; i<raceDetailsObject.length(); i++) {
                JSONObject raceObject = raceDetailsObject.getJSONObject(i);
                JSONArray raceHorseDetailsObject = raceObject.getJSONArray("raceHorses");

                Race raceDetails = new Race();
                ArrayList<RaceHorse> raceHorses = new ArrayList<>();

                raceDetails.setRaceNumber(raceObject.getInt("raceNumber"));
                raceDetails.setDistance(raceObject.getString("distance"));
                raceDetails.setValueBenchmark(raceObject.getString("valueBenchmark"));
                raceDetails.setTime(raceObject.getString("time"));
                raceDetails.setRaceName(raceObject.getString("raceName"));
                raceDetails.setHorseCount(raceObject.getInt("horseCount"));

                for(int count=0;count<raceHorseDetailsObject.length();count++){
                    JSONObject raceHorseObject = raceHorseDetailsObject.getJSONObject(count);
                    RaceHorse raceHorseDetails = new RaceHorse();

                    raceHorseDetails.setPerf(raceHorseObject.getString("perf"));
                    raceHorseDetails.setAge(raceHorseObject.getInt("age"));
                    raceHorseDetails.setGear(raceHorseObject.getString("gear"));
                    raceHorseDetails.setWeight(raceHorseObject.getString("weight"));
                    raceHorseDetails.setJockey(raceHorseObject.getString("jockey"));
                    raceHorseDetails.setDraw(raceHorseObject.getInt("draw"));
                    raceHorseDetails.setTimeFactor(raceHorseObject.getString("timeFactor"));
                    raceHorseDetails.setHorseName(raceHorseObject.getString("horseName"));
                    raceHorseDetails.setHorseNumber(raceHorseObject.getInt("horseNumber"));
                    raceHorseDetails.setStable(raceHorseObject.getString("stable"));
                    //raceHorseDetails.setHandicap(raceHorseObject.getInt("handicap"));
                   // raceHorseDetails.setValue(raceHorseObject.getInt("value"));
                    raceHorseDetails.setRaceNumber(raceHorseObject.getInt("raceNumber"));

                    raceHorses.add(raceHorseDetails);
                }
                raceDetails.setRaceHorses(raceHorses);
                races.add(raceDetails);
            }

            raceCardList.setRace(races);

            System.out.print(raceCardList);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private String GetRaceCard() {
        int id;
        String result = "";
        Bundle bundle = this.getArguments();

        try {
            if(isNetworkConnected()){
                if (bundle == null){
                    result =  new GetHomeScreenFromApi().execute(homeUrl, raceCardUrl).get();
                }
                else {
                    id = bundle.getInt("id");
                    result = new GetRaceCardFromApi().execute(raceCardUrl+id).get();
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

    private class GetRaceCardFromApi extends AsyncTask<String, String, String> {
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
                result = GetRaceCard(params[1], jsonObjectHome);

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

        private String GetRaceCard(String param, String jsonObjectHome) {
            String jsonObjectRaceCard = "";
            HomeScreenViewModel home = new HomeScreenViewModel();

            try {
                JSONObject parentObject = new JSONObject(jsonObjectHome);
                home.setIsRaceCardAvailable(parentObject.getBoolean("isRaceCardAvailable"));
                home.setMeetingNumber(parentObject.getInt("meetingNumber"));

               //  home.setIsRaceCardAvailable(true);
                //to remove

                if(home.getIsRaceCardAvailable() == true){

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

                    jsonObjectRaceCard = bufferz.toString();
                }

            } catch (Exception e) {
                Log.e("MainActivity", e.getMessage(), e);
            }

            return jsonObjectRaceCard;
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
