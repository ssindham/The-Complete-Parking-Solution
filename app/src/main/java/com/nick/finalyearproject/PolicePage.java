package com.nick.finalyearproject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


/**
 * Created by CompuCareInfotech on 3/18/2017.
 */

public class PolicePage extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.police_home);
        EditText vh_no=(EditText)findViewById(R.id.vh_no);
        EditText charge=(EditText)findViewById(R.id.fine);
        Button take_fine=(Button)findViewById(R.id.fine_button);

        take_fine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

    }
}
