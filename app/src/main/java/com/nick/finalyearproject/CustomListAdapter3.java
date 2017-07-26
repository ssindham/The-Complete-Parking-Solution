package com.nick.finalyearproject;

/**
 * Created by CompuCareInfotech on 3/21/2017.
 */

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;


/**
 * Created by CompuCareInfotech on 2/23/2017.
 */

public class CustomListAdapter3 extends BaseAdapter {

    private Context mContext;
    private List<CustomMenu3> mCustomList;

    //constructor


    public CustomListAdapter3(Context mContext, List<CustomMenu3> mCustomList) {
        this.mContext = mContext;
        this.mCustomList = mCustomList;
    }

    @Override
    public int getCount() {
        return mCustomList.size();
    }

    @Override
    public Object getItem(int position) {
        return mCustomList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v=View.inflate(mContext,R.layout.item_list2,null);
        TextView name=(TextView)v.findViewById(R.id.name);
        TextView chargeHour=(TextView)v.findViewById(R.id.chargeHour);
        TextView distance=(TextView)v.findViewById(R.id.distance);

        name.setText("Name:"+(mCustomList.get(position).getPlace_name()));
        chargeHour.setText("Attendant:"+String.valueOf(mCustomList.get(position).getAttendant_name()));
        distance.setText("Distance:"+String.valueOf(mCustomList.get(position).getDist()));

        v.setTag(mCustomList.get(position).getId());
        return v;
    }
}

