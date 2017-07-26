package com.nick.finalyearproject;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

/**
 * Created by CompuCareInfotech on 11/9/2016.
 */

public class ForgotPassword extends Activity {

    ImageButton imgBtn;
    EditText email;
    Button submit_button;
    SQLiteDatabase db;
    String password,id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setTheme(android.R.style.Theme);
        setContentView(R.layout.forgot_password);

        imgBtn=(ImageButton)findViewById(R.id.imageButton1);
        email=(EditText)findViewById(R.id.email);
        submit_button=(Button)findViewById(R.id.btnSubmit);

        db=openOrCreateDatabase("SmartParking.db",MODE_PRIVATE,null);
        db.execSQL("create table IF NOT EXISTS users(id INTEGER PRIMARY KEY AUTOINCREMENT,name TEXT,email TEXT,mob TEXT,pass TEXT,profession TEXT,secretcode TEXT);");

        imgBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                finish();

            }
        });


        submit_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                if(email.getText().toString().length()==0){
                    email.setError("Email not entered");
                    email.requestFocus();
                    Toast.makeText(ForgotPassword.this, "Email Cannot Be Empty", Toast.LENGTH_SHORT).show();
                }
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";


                if (!(email.getText().toString().trim().matches(emailPattern)))
                {
                    email.setError("Email not valid");
                    email.requestFocus();
                    Toast.makeText(ForgotPassword.this, "Invalid email Id", Toast.LENGTH_SHORT).show();
                }

                else
                {
                    Cursor c1=db.rawQuery("select * from users where email ='"+email.getText().toString()+"' ", null);
                    if(c1.moveToNext())
                    {
                        if(email.getText().toString().equals(c1.getString(2)))
                        {

                            password=c1.getString(4);
                            id=c1.getString(2);
                            Toast.makeText(ForgotPassword.this,"Your password is:"+password,Toast.LENGTH_LONG).show();

                        }
                        else
                        {
                            Toast.makeText(ForgotPassword.this, "This Email address does not exist!",Toast.LENGTH_LONG).show();
                        }
                    }
                    c1.close();
                }


            }
        });





    }
}
