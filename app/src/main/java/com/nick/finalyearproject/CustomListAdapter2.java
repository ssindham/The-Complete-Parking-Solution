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

public class CustomListAdapter2 extends BaseAdapter {

    private Context mContext;
    private List<CustomMenu2> mCustomList;

    //constructor


    public CustomListAdapter2(Context mContext, List<CustomMenu2> mCustomList) {
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

        name.setText("Name:"+(mCustomList.get(position).getArea()));
        chargeHour.setText("Charge:"+String.valueOf(mCustomList.get(position).getCharge())+" Rs ");
        distance.setText("Days:"+String.valueOf(mCustomList.get(position).getWeek_days()));

        v.setTag(mCustomList.get(position).getId());
        return v;
    }
}

