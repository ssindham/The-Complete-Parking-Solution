package com.nick.finalyearproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by CompuCareInfotech on 3/3/2017.
 */

public class Temp_Rentee extends Activity {
    EditText twowheeler;
    EditText fourwheeler;
    Button findButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.temp_rentee);
        twowheeler=(EditText)findViewById(R.id.twowheeler);
        fourwheeler=(EditText)findViewById(R.id.fourwheeler);
        findButton=(Button)findViewById(R.id.findButton);


    }
    public void find_rent_place_onclick(View view)
    {
        Toast.makeText(Temp_Rentee.this, "Find Button Clicked", Toast.LENGTH_SHORT).show();
        if (twowheeler.getText().toString().length() == 0) {
            twowheeler.setError("Enter No of Two wheelers");
            twowheeler.requestFocus();
          //  flag1 = 1;
        }

        if (fourwheeler.getText().toString().length() == 0) {
            fourwheeler.setError("Enter No of Four wheelers");
            fourwheeler.requestFocus();
            //flag2 = 1;
            //                  Toast.makeText(RegisterActivity.this, "Email Cannot Be Empty", Toast.LENGTH_SHORT).show();
        }
        Intent in=new Intent(Temp_Rentee.this,CustomMain.class);
        in.putExtra("twowheeler", twowheeler.getText().toString());
        in.putExtra("fourwheeler", fourwheeler.getText().toString());
        startActivity(in);
    }
}
