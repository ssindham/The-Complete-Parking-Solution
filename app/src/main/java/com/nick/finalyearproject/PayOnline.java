package com.nick.finalyearproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Sandeep on 11 March 2017.
 */

public class PayOnline extends Activity {

    TextView t1;
    EditText payment_text;
    Button pay_button;
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setTheme(android.R.style.Theme);

        setContentView(R.layout.payonline);
        t1 = (TextView) findViewById(R.id.textView11);
        payment_text = (EditText) findViewById(R.id.editText4);
        pay_button = (Button) findViewById(R.id.button4);


        Bundle bundle = getIntent().getExtras();


        String s=bundle.getString("msg");
        String type=bundle.getString("type");
        String amnt=new String();
         String police_id="dummy";
        if(type.equals("police_direct_fine")) {
            String[] parts = s.split("y");
            String part1 = parts[0]; // 004
            String part2 = parts[1];
            amnt = part1.replaceAll("[^0-9]", "");
             police_id = part2.replaceAll("[^0-9]", "");
            Toast.makeText(PayOnline.this,"You are fined Rs."+amnt+" By Police Id:"+police_id,Toast.LENGTH_SHORT).show();
        }

        if(type.equals("rent_charge"))
        {
            amnt = bundle.getString("amnt");
        }
        if(type.equals("central_rent_charge"))
        {
            amnt = bundle.getString("amnt");
        }



      //  Toast.makeText(PayOnline.this,msg,Toast.LENGTH_SHORT).show();
        payment_text.setText(amnt);

        pay_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent in = new Intent(PayOnline.this,paypal.class);
                Bundle bundle = getIntent().getExtras();

                String email = bundle.getString("email");
                String u_id = bundle.getString("u_id");
                in.putExtra("email",email);
                in.putExtra("u_id",u_id);
                String type=bundle.getString("type");
                if(type.equals("police_direct_fine"))
                {
                    String s=bundle.getString("msg");
                     String[] parts = s.split("y");
                    String part2 = parts[1];
                    String police_id = part2.replaceAll("[^0-9]", "");
                    in.putExtra("police_id",police_id);
                }
                in.putExtra("type",type);
                in.putExtra("amnt",payment_text.getText().toString());
                startActivity(in);
                finish();
            }
        });




    }
}
