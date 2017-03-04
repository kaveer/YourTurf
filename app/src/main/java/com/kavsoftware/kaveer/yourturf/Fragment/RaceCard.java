package com.kavsoftware.kaveer.yourturf.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kavsoftware.kaveer.yourturf.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class RaceCard extends Fragment {


    public RaceCard() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getActivity().setTitle("Race Card");

        View view = inflater.inflate(R.layout.fragment_race_card, container, false);



        return view;
    }



}
