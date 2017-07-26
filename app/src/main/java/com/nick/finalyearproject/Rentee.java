package com.nick.finalyearproject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * Created by CompuCareInfotech on 3/5/2017.
 */

public class Rentee extends Activity {

    ImageButton backButton;


    LinearLayout l1,l2;
    CheckBox two;
    CheckBox four;

    EditText twowheeler;
    EditText fourwheeler;
    Button findButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rentee);

        backButton=(ImageButton)findViewById(R.id.imageButton1);
        l1=(LinearLayout)findViewById(R.id.editTextHolder1);
        l2=(LinearLayout)findViewById(R.id.editTextHolder2);
        two=(CheckBox)findViewById(R.id.two);
        four=(CheckBox)findViewById(R.id.four);

        twowheeler=(EditText)findViewById(R.id.twowheeler);
        fourwheeler=(EditText)findViewById(R.id.fourwheeler);
        findButton=(Button)findViewById(R.id.findButton);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        l1.setVisibility(View.GONE);
        l2.setVisibility(View.GONE);

        two.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(two.isChecked())
                {
                    l1.setVisibility(View.VISIBLE);
                    twowheeler.setHintTextColor(Color.GRAY);
                    twowheeler.setHint("Number of Two-Wheelers");

                }
                else
                {
                    l1.setVisibility(View.GONE);
                }



            }
        });

        four.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(four.isChecked())
                {
                    l2.setVisibility(View.VISIBLE);
                    fourwheeler.setHintTextColor(Color.GRAY);

                    fourwheeler.setHint("Number of Four-Wheelers");
                }
                else
                {
                    l2.setVisibility(View.GONE);
                }



            }
        });

    }

    public void find_rent_place_onclick(View view)
    {
        Toast.makeText(Rentee.this, "Find Button Clicked", Toast.LENGTH_SHORT).show();
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
        Intent in=new Intent(Rentee.this,CustomMain.class);
        Bundle bundle = getIntent().getExtras();
        String email = bundle.getString("email");
        String u_id = bundle.getString("u_id");
        in.putExtra("email",email);
        in.putExtra("u_id",u_id);
        in.putExtra("twowheeler", twowheeler.getText().toString());
        in.putExtra("fourwheeler", fourwheeler.getText().toString());
        startActivity(in);
    }

}
