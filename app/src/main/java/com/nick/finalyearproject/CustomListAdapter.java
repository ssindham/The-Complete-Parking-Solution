package com.nick.finalyearproject;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by CompuCareInfotech on 2/23/2017.
 */

public class CustomListAdapter extends BaseAdapter {

    private Context mContext;
    private List<CustomMenu> mCustomList;

    //constructor


    public CustomListAdapter(Context mContext, List<CustomMenu> mCustomList) {
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

        View v=View.inflate(mContext,R.layout.item_list,null);
        TextView areaName=(TextView)v.findViewById(R.id.area_name);
        TextView chargeHour=(TextView)v.findViewById(R.id.chargeHour);
        TextView distance=(TextView)v.findViewById(R.id.distance);

        areaName.setText((mCustomList.get(position).getArea()));
        chargeHour.setText(String.valueOf(mCustomList.get(position).getCharge())+" Rs ");
        distance.setText(String.valueOf(mCustomList.get(position).getDist())+" KM ");

        v.setTag(mCustomList.get(position).getId());
        return v;
    }
}
