package com.nick.finalyearproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

/**
 * Created by CompuCareInfotech on 4/7/2017.
 */

public class GovtPlaceInformation extends Activity {

    EditText place_name,attendant_name,total_capacity,current_capacity,distance;
    ImageButton directionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.govt_place_information);
        place_name=(EditText)findViewById(R.id.place_name);
        attendant_name=(EditText)findViewById(R.id.attendant_name);
        total_capacity=(EditText)findViewById(R.id.total_capacity);
        current_capacity=(EditText)findViewById(R.id.current_capacity);
        distance=(EditText)findViewById(R.id.dist);
        directionButton=(ImageButton)findViewById(R.id.directions);


        Bundle bundle = getIntent().getExtras();
        String place=bundle.getString("place");
        String place_id=bundle.getString("place_id");
        String total_capacity1=bundle.getString("total_capacity");
        String current_capacity1=bundle.getString("current_capacity");
        String attendant=bundle.getString("attendant");
        String dist=bundle.getString("dist");


        place_name.setText(place);
        attendant_name.setText(attendant);
        total_capacity.setText(total_capacity1);
        current_capacity.setText(current_capacity1);
        distance.setText(dist);


        directionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(GovtPlaceInformation.this,"Direction Button Clicked",Toast.LENGTH_SHORT).show();
                Intent i=new Intent(GovtPlaceInformation.this,Rentee_Route.class);
                Bundle bundle = getIntent().getExtras();
                String lati=bundle.getString("lati");
                String longi=bundle.getString("longi");
                String place=bundle.getString("place");
                i.putExtra("lati",lati);
                i.putExtra("longi",longi);
                i.putExtra("place",place);
                startActivity(i);
            }
        });


    }
}
