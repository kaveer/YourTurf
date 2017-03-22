package com.kavsoftware.kaveer.yourturf.CustomListView.NonimationListView;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kavsoftware.kaveer.yourturf.Fragment.Nomination;
import com.kavsoftware.kaveer.yourturf.R;
import com.kavsoftware.kaveer.yourturf.ViewModel.Nomination.Race;

import java.util.ArrayList;

/**
 * Created by kaveer on 3/20/2017.
 */

public class NominationListView extends BaseAdapter implements View.OnClickListener {

    private Activity activity;
    private ArrayList raceList;
    private static LayoutInflater inflater=null;
    public Resources resource;

    public NominationListView(Activity act , ArrayList race, Resources localResource){

        activity = act;
        raceList = race;
        resource = localResource;

        inflater = ( LayoutInflater )activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public static class ViewHolder{

        public TextView nominationNumberAndName;
        public TextView nominationTimeAndDistance;
        public TextView nominationHorseCount;
        public TextView nominationBenchMark;

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
    public void onClick(View v) {

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder holder;

        if(convertView==null){

            view = inflater.inflate(R.layout.nomination_listview, null);

            holder = new ViewHolder();
            holder.nominationNumberAndName = (TextView)view.findViewById(R.id.RaceNumberAndName);
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
            holder.nominationNumberAndName.setText("Data unavailable");
            holder.nominationTimeAndDistance.setText("Data unavailable");
            holder.nominationHorseCount.setText("Data unavailable");
            holder.nominationBenchMark.setText("Data unavailable");

        }
        else {
            Race race = (Race) raceList.get(position);

            holder.nominationNumberAndName.setText( race.getRaceNumber() + " " + race.getRaceName() );
            holder.nominationTimeAndDistance.setText("Time : " + race.getTime() + " " + " Distance : " + race.getDistance() );
            holder.nominationHorseCount.setText("Horse count : " + race.getHorseCount());
            holder.nominationBenchMark.setText("Bench mark : " + race.getValueBenchmark());

            view.setOnClickListener(new OnItemClickListener( position ));
        }

        return view;
    }

    private class OnItemClickListener  implements View.OnClickListener {
        private int mPosition;

        OnItemClickListener(int position){
            mPosition = position;
        }

        @Override
        public void onClick(View arg0) {

            Nomination nominationOnClick = new Nomination();
            nominationOnClick.onItemClick(mPosition, raceList);

        }
    }


}
