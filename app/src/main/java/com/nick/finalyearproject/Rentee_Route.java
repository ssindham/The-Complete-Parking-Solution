package com.nick.finalyearproject;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

/**
 * Created by CompuCareInfotech on 3/9/2017.
 */

public class Rentee_Route extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rentee_route);

     /*   Bundle bundle = getIntent().getExtras();
       String lati=bundle.getString("lati");
       String longi=bundle.getString("longi");
        String place=bundle.getString("place");*/
        String lati="21.1821515";
        String longi="72.8065711";
        String place="SCET";

        //Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse("google.navigation:q=https://www.google.co.in/maps/dir//'21.1821465,72.8065711'/@21.1737796,72.8044988,15z/data=!4m8!4m7!1m0!1m5!1m1!1s0x0:0x390b720b60d3fd24!2m2!1d72.8065711!2d21.1821465"));
        //Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?daddr=21.1821515,72.8065711"));//SCET
       Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse( "http://maps.google.com/maps?daddr="+lati+","+longi+place+"\" " ));
       // Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=37.423156,-122.084917 (" + place + ")"));
     //  Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse(  "geo:0,0?q=" + lati + "," +longi + "(\"" +place+ "\")" ));
        intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
        startActivity(intent);
    }
}
