package com.kavsoftware.kaveer.yourturf.CustomListView;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kavsoftware.kaveer.yourturf.Fragment.Nomination;
import com.kavsoftware.kaveer.yourturf.R;
import com.kavsoftware.kaveer.yourturf.ViewModel.Nomination.Race;

import java.util.ArrayList;

/**
 * Created by kaveer on 3/18/2017.
 */

public class CustomAdapter extends BaseAdapter implements View.OnClickListener {

    private Activity activity;
    private ArrayList data;
    private static LayoutInflater inflater=null;
    public Resources res;

    public CustomAdapter(Activity a, ArrayList d,Resources resLocal) {

        activity = a;
        data=d;
        res = resLocal;

        inflater = ( LayoutInflater )activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        if(data.size()<=0)
            return 1;
        return data.size();
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
        View vi = convertView;
        ViewHolder holder;

        if(convertView==null){

            /****** Inflate tabitem.xml file for each row ( Defined below ) *******/
            vi = inflater.inflate(R.layout.nomination_listview, null);

            /****** View Holder Object to contain tabitem.xml file elements ******/

            holder = new ViewHolder();
            holder.text = (TextView) vi.findViewById(R.id.RaceNumberAndName);
            holder.text1=(TextView)vi.findViewById(R.id.RaceTimeAndDistance);
            holder.text3=(TextView)vi.findViewById(R.id.RaceHorseCount);
            holder.text4=(TextView)vi.findViewById(R.id.RaceValueBenchMark);


            // holder.image=(ImageView)vi.findViewById(R.id.testImg);

            /************  Set holder with LayoutInflater ************/
            vi.setTag( holder );
        }
        else
            holder=(ViewHolder)vi.getTag();

        if(data.size()<=0)
        {

            holder.text.setText("No Datazz");

        }
        else
        {
           // nomination =null;
            //nomination = ( NominationViewModel ) data.get( position );

            Race r = null;
            r = (Race) data.get(position);


            holder.text.setText( r.getRaceNumber() + " " + r.getRaceName() );
            holder.text1.setText("Time : " + r.getTime() + " " + " Distance : " + r.getDistance() );
            holder.text3.setText("Horse count : " + r.getHorseCount());
            holder.text4.setText("Bench mark : " + r.getValueBenchmark());
          //  holder.image.setImageResource(
                   // res.getIdentifier(
                           // "com.androidexample.customlistview:drawable/images",null,null));


            vi.setOnClickListener(new OnItemClickListener( position ));
        }
        return vi;
    }

    @Override
    public void onClick(View v) {

    }

    private class OnItemClickListener  implements View.OnClickListener {
        private int mPosition;

        OnItemClickListener(int position){
            mPosition = position;
        }

        @Override
        public void onClick(View arg0) {


            //Nomination sct = (Nomination);
            Nomination no = new Nomination();
            //no.OnClickNominationListView();
            no.onItemClick(mPosition, data);

            /****  Call  onItemClick Method inside CustomListViewAndroidExample Class ( See Below )****/

            //sct.onItemClick(mPosition);
        }
    }


    public static class ViewHolder{

        public ImageView image;
        public TextView text;
        public TextView text1;
        public TextView text3;
        public TextView text4;



    }
}
