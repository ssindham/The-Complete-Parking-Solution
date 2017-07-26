package com.nick.finalyearproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

/**
 * Created by CompuCareInfotech on 3/5/2017.
 */

public class RenterRentee extends Activity {


    ImageButton backButton;
    Button renter,tenant;
    public  static  String u_id;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.renter_rentee);
        Bundle bundle = getIntent().getExtras();
        final String email = bundle.getString("email");

        backButton=(ImageButton)findViewById(R.id.imageButton1);
        renter=(Button)findViewById(R.id.btnRenter);
        tenant=(Button)findViewById(R.id.btnTenant);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        renter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),Renter.class);
                Bundle bundle = getIntent().getExtras();
                String u_id = bundle.getString("u_id");
                String email = bundle.getString("email");
                intent.putExtra("u_id",u_id);
                intent.putExtra("email",email);
                startActivity(intent);
            }
        });

        tenant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),Rentee.class);
                Bundle bundle = getIntent().getExtras();
                String email = bundle.getString("email");
                String u_id = bundle.getString("u_id");
                intent.putExtra("email",email);
                intent.putExtra("u_id",u_id);
                startActivity(intent);
            }
        });


    }
}
