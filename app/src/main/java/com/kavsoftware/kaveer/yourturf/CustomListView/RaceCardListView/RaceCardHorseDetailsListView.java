package com.kavsoftware.kaveer.yourturf.CustomListView.RaceCardListView;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kavsoftware.kaveer.yourturf.R;
import com.kavsoftware.kaveer.yourturf.ViewModel.RaceCard.RaceHorse;

import java.util.ArrayList;

/**
 * Created by kaveer on 3/24/2017.
 */

public class RaceCardHorseDetailsListView  extends BaseAdapter implements View.OnClickListener   {

    private Activity activity;
    private ArrayList raceList;
    private static LayoutInflater inflater=null;
    public Resources resource;

    public RaceCardHorseDetailsListView(Activity act , ArrayList race, Resources localResource){
        activity = act;
        raceList = race;
        resource = localResource;

        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public static class ViewHolder{

        public TextView horseNumberAndHorseName;
        public TextView nominationTimeAndDistance;
        public TextView nominationHorseCount;
        public TextView nominationBenchMark;

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public int getCount() {
        if(raceList.size()<=0)
            return 1;
        return raceList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder holder;

        if(convertView==null){

            view = inflater.inflate(R.layout.racecard_horsedetails_listview, null);

            holder = new ViewHolder();
            holder.horseNumberAndHorseName = (TextView)view.findViewById(R.id.RaceNumberAndName);
            holder.nominationTimeAndDistance =(TextView)view.findViewById(R.id.RaceTimeAndDistance);
            holder.nominationHorseCount =(TextView)view.findViewById(R.id.RaceHorseCount);
            holder.nominationBenchMark =(TextView)view.findViewById(R.id.RaceValueBenchMark);

            view.setTag( holder );

        }
        else{

            holder=(ViewHolder)view.getTag();

        }

        if(raceList.size()<=0)
        {
            holder.horseNumberAndHorseName.setText("Data unavailable");
            holder.nominationTimeAndDistance.setText("Data unavailable");
            holder.nominationHorseCount.setText("Data unavailable");
            holder.nominationBenchMark.setText("Data unavailable");

        }
        else {
            RaceHorse race = (RaceHorse) raceList.get(position);

            String chapterNumber = race.getHorseName();
            int index = chapterNumber.indexOf(")") + 1;
            String mainChapterNumber = chapterNumber.substring(0,index);

            holder.horseNumberAndHorseName.setText( race.getHorseNumber() + " " + mainChapterNumber );
            holder.nominationTimeAndDistance.setText("Time : " + race.getHorseName() + " " + " Distance : " + race.getHorseName() );
            holder.nominationHorseCount.setText("Horse count : " + race.getHorseName());
            holder.nominationBenchMark.setText("Bench mark : " + race.getHorseName());

        }

        return view;
    }
}
