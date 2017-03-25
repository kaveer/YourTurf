package com.kavsoftware.kaveer.yourturf.Fragment.RaceCard;


import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.kavsoftware.kaveer.yourturf.CustomListView.RaceCardListView.RaceCardHorseDetailsListView;
import com.kavsoftware.kaveer.yourturf.R;
import com.kavsoftware.kaveer.yourturf.ViewModel.RaceCard.RaceHorse;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class RaceCardHorseDetails extends Fragment {

    ListView raceCardHorseDetailsListView;
    RaceCardHorseDetailsListView adapter;

    public RaceCardHorseDetails() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_race_card_horse_details, container, false);

        getActivity().setTitle("Horse Details");

        ArrayList<RaceHorse> horses = new ArrayList<>();
        Bundle bundle = getArguments();
        horses = (ArrayList<RaceHorse>) bundle.getSerializable("horses");

        GenerateListView(horses, view);

        return view;
    }

    private void GenerateListView(ArrayList<RaceHorse> horses, View view) {
        Resources res = getResources();
        raceCardHorseDetailsListView = (ListView)view.findViewById(R.id.ListViewRaceCardHorse);

        adapter = new RaceCardHorseDetailsListView(getActivity(), horses ,res);
        raceCardHorseDetailsListView.setAdapter(adapter);

    }

}
