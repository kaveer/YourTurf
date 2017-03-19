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

import com.kavsoftware.kaveer.yourturf.CustomListView.CustomAdapter;
import com.kavsoftware.kaveer.yourturf.R;
import com.kavsoftware.kaveer.yourturf.ViewModel.HomeScreen.HomeScreenViewModel;
import com.kavsoftware.kaveer.yourturf.ViewModel.Nomination.NominationViewModel;
import com.kavsoftware.kaveer.yourturf.ViewModel.Nomination.Race;
import com.kavsoftware.kaveer.yourturf.ViewModel.Nomination.RaceHorse;

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
public class Nomination extends Fragment {

    HttpURLConnection connection = null;
    BufferedReader reader = null;
    NominationViewModel nominationTest = new NominationViewModel();
    String homeUrl;
    String nominationUrl;

    ListView nominationListView;

    CustomAdapter adapter;

    public Nomination nnn = null;


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

        nnn = this;

        if(result == ""){
            Toast messageBox = Toast.makeText(getActivity() , "No nomination available" , Toast.LENGTH_LONG);
            messageBox.show();
        }
        else {
            DeserializeJsonObject(result);

            GenerateListView(view);
        }



        return view;
    }

    private void GenerateListView(View view) {
        String[] values = new String[nominationTest.getRaceCount()];
        int count = 0;
        
//        for (Race item: nominationTest.getRace()) {
//            values[count] = item.getRaceNumber()
//                    + " " + item.getRaceName()
//                    + " distance" + item.getDistance()
//                    + " time" + item.getTime()
//                    + " horse" + item.horseCount
//                    + "value Benchmark" + item.getValueBenchmark();
//            count ++;
//            //Log.e("=========Race=======",item.getRaceName());
////            for (RaceHorse items: item.getRaceHorses()) {
////                Log.e("horseName", items.getHorseName());
////            }
//        }

        Resources res =getResources();
        nominationListView = (ListView)view.findViewById(R.id.ListViewNomination);

       // ArrayAdapter adapter = new ArrayAdapter(this.getContext(), android.R.layout.simple_list_item_1, values);
       // nominationListView.setAdapter(adapter);


        adapter = new CustomAdapter(getActivity(), nominationTest.getRace() ,res);
        nominationListView.setAdapter( adapter );


    }

    public void onItemClick(int mPosition, ArrayList data)
    {
        //ListModel tempValues = ( ListModel ) CustomListViewValuesArr.get(mPosition);


        // SHOW ALERT

      Object n = data.get(mPosition);


       // Toast.makeText(nnn.getActivity(), "sadfcsdf", Toast.LENGTH_LONG).show();

        int f = mPosition;
    }

    public void OnClickNominationListView() {
//        nominationListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view,
//                                    int position, long id) {
//
//                // ListView Clicked item index
//                int itemPosition     = position;
//
//                // ListView Clicked item value
//                String  itemValue    = (String) nominationListView.getItemAtPosition(position);
//
//                // Show Alert
//
//                Toast messageBox = Toast.makeText(getActivity() , +itemPosition+ "Insurance removed" +itemValue  , Toast.LENGTH_SHORT);
//                messageBox.show();
//
//
//            }
//
//        });
    }

    private void DeserializeJsonObject(String result) {
        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONArray raceArray = jsonObject.getJSONArray("race");

            nominationTest.setRaceCount(jsonObject.getInt("raceCount"));

            ArrayList<Race> raceList = new ArrayList<>();

            for(int i=0; i<raceArray.length(); i++) {
                JSONObject raceObject = raceArray.getJSONObject(i);
                JSONArray raceHorsesArray = raceObject.getJSONArray("raceHorses");

                Race item = new Race();
                ArrayList<RaceHorse> raceHorseList = new ArrayList<>();

                item.setRaceNumber(raceObject.getInt("raceNumber"));
                item.setDistance(raceObject.getString("distance"));
                item.setValueBenchmark(raceObject.getString("valueBenchmark"));
                item.setTime(raceObject.getString("time"));
                item.setRaceName(raceObject.getString("raceName"));
                item.setHorseCount(raceObject.getInt("horseCount"));

                for(int count=0;count<raceHorsesArray.length();count++){
                    JSONObject raceHorseObject = raceHorsesArray.getJSONObject(count);
                    RaceHorse items = new RaceHorse();

                    items.setHorseName(raceHorseObject.getString("horseName"));
                    items.setHorseNumber(raceHorseObject.getInt("horseNumber"));
                    items.setStable(raceHorseObject.getString("stable"));
                   // items.setHandicap(raceHorseObject.getInt("handicap"));
                   // items.setValue(raceHorseObject.getInt("value"));
                    items.setRaceNumber(raceHorseObject.getInt("raceNumber"));

                    raceHorseList.add(items);
                }

                item.setRaceHorses(raceHorseList);
                raceList.add(item);
            }

            nominationTest.setRace(raceList);

            System.out.print(nominationTest.getRaceCount());
        } catch (JSONException e) {
            e.printStackTrace();
        }
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
