package com.nick.finalyearproject;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by CompuCareInfotech on 3/30/2017.
 */

public class RentPlaceErrorPage extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rent_place_errorpage);
      //  Button register=(Button)findViewById(R.id.register_button1);

    /*    register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(RentPlaceErrorPage.this,"Register Button Clicked",Toast.LENGTH_SHORT).show();
                Bundle bundle = getIntent().getExtras();
           //     Intent i = new Intent(RentPlaceErrorPage.this,Renter.class);
                Intent i = new Intent(RentPlaceErrorPage.this,ChoiceActivity.class);
                String u_id = bundle.getString("u_id");
                String email = bundle.getString("email");
                i.putExtra("u_id",u_id);
                i.putExtra("email",email);
                startActivity(i);
            }
        });*/
    }
}
