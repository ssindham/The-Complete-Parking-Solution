package com.nick.finalyearproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by CompuCareInfotech on 2/25/2017.
 */

public class AttendantHome extends Activity {


    Button scanButton;
    Button details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.attendant_home);
        scanButton=(Button)findViewById(R.id.scan_button);
        details=(Button)findViewById(R.id.details_button);

        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AttendantHome.this,"Scan Button Clicked",Toast.LENGTH_SHORT).show();
                Intent i=new Intent(AttendantHome.this, ReaderActivity.class);
                startActivity(i);
            }
        });

    }
}
