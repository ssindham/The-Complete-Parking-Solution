package com.nick.finalyearproject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class RegisterActivity extends Activity {
    EditText name;
    EditText email;
    EditText mob;
    EditText pass;
    EditText cpass;
    Button register;
    TextView loginScreen;
    RadioButton citizen;
    RadioButton police;
    RadioButton attendant;
    ImageButton imgbtn;
    static int countPolice = 0;
    static int countAttendant = 0;

    // SQLiteDatabase db;
    LinearLayout l1;
    EditText secret;

    //int flag1=0,flag2=0,flag3=0,flag4=0,flag5=0,flag6=0,flag7=0;

    public static String NAMESPACE = "http://tempuri.org/";
    //public static String URL = "http://192.168.1.8/WebSite1/Service.asmx";
    public static String URL =WebServiceClass.URL;

    public static String METHOD_NAME = "Reg";
    public static String SOAP_ACTION = "http://tempuri.org/Reg";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setTheme(android.R.style.Theme);

        setContentView(R.layout.register);
        imgbtn = (ImageButton) findViewById(R.id.imageButton1);
        name = (EditText) findViewById(R.id.reg_fullname);
        email = (EditText) findViewById(R.id.reg_email);
        mob = (EditText) findViewById(R.id.reg_mob);
        pass = (EditText) findViewById(R.id.reg_password);
        cpass = (EditText) findViewById(R.id.reg_cpassword);
        l1 = (LinearLayout) findViewById(R.id.secretCodeHolder);
        // String profession=new String();
        citizen = (RadioButton) findViewById(R.id.radio0);
        police = (RadioButton) findViewById(R.id.radio1);
        attendant = (RadioButton) findViewById(R.id.radio2);
        register = (Button) findViewById(R.id.btnRegister);
        secret = (EditText) findViewById(R.id.reg_secret_code);

        loginScreen = (TextView) findViewById(R.id.link_to_login);
        loginScreen.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);


        l1.setVisibility(View.GONE);
        RadioGroup radiogroup = (RadioGroup) findViewById(R.id.rg1);
        radiogroup.clearCheck();

        radiogroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub
                if (citizen.isChecked()) {
                    l1.setVisibility(View.VISIBLE);
                    secret.setHint("Enter Vehicle Number");
                } else if (police.isChecked()) {
                    l1.setVisibility(View.VISIBLE);
                    secret.setHint("Enter Secret Code");
                } else {
                    l1.setVisibility(View.VISIBLE);
                    secret.setHint("Enter Secret Code");
                }

            }
        });


        imgbtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                finish();
            }
        });

        loginScreen.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent i1 = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(i1);
            }
        });


    }

    public void register_onclick(View view) {
        //Toast.makeText(RegisterActivity.this, "sa", Toast.LENGTH_LONG);
        int flag1 = 0, flag2 = 0, flag3 = 0, flag4 = 0, flag5 = 0, flag6 = 0, flag7 = 0, flag8 = 0, flag9 = 0, flag10 = 0;
        //String profession=new String();
        String profession = "Citizen";
        String secret_code = "Dummy";
        String veh_no="Dummy";
        RadioGroup radiogroup = (RadioGroup) findViewById(R.id.rg1);
        int checkedradiobuttonid=radiogroup.getCheckedRadioButtonId();
        if(checkedradiobuttonid==-1)
        {

        }
        else {
            if(checkedradiobuttonid==R.id.radio0) {
                profession = "Citizen";
                veh_no=secret.getText().toString();
            }
            else if(checkedradiobuttonid==R.id.radio1) {
                profession = "Police";
                secret_code=secret.getText().toString();
            }
            if(checkedradiobuttonid==R.id.radio2)
                profession="Attendant";
            secret_code=secret.getText().toString();
        }
        //String secret_code=new String();

        if (name.getText().toString().length() == 0) {
            name.setError("Name not entered");
            name.requestFocus();
            flag1 = 1;
        }

        if (email.getText().toString().length() == 0) {
            email.setError("Email not entered");
            email.requestFocus();
            flag2 = 1;
            //                  Toast.makeText(RegisterActivity.this, "Email Cannot Be Empty", Toast.LENGTH_SHORT).show();
        }
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";


        if (!(email.getText().toString().trim().matches(emailPattern))) {
            email.setError("Email not valid");
            email.requestFocus();
            flag3 = 1;
            //             Toast.makeText(RegisterActivity.this, "Invalid email Id", Toast.LENGTH_SHORT).show();
        }

        Boolean go = true;

        //TO BE CODED
        //* Cursor c1=db.rawQuery("select * from users where email ='"+email.getText().toString()+"' ", null);
       /* if(c1.moveToFirst())
        {
            email.setError("Email already exists");
            email.requestFocus();
            go=false;
            flag4=1;
        }*/

        if (mob.getText().toString().length() != 10 || (mob.getText().toString().matches("^[0-9]*$") == false)) {
            if (mob.getText().toString().length() == 0) {
                mob.setError("Mobile no. not entered");

                //          Toast.makeText(RegisterActivity.this, "Mobile no Cannot Be Empty", Toast.LENGTH_SHORT).show();
            } else {
                mob.setError("Invalid Mobile number");

                //         Toast.makeText(RegisterActivity.this, "Invalid mobile no.", Toast.LENGTH_SHORT).show();
            }
            mob.requestFocus();
            flag5 = 1;
        }

        if (pass.getText().toString().length() < 6) {
            if (pass.getText().toString().length() == 0) {
                pass.setError("Password not entered");
                pass.requestFocus();

                //          Toast.makeText(RegisterActivity.this, "Password Cannot Be Empty", Toast.LENGTH_SHORT).show();
            } else {
                pass.setError("Min length:6");
                pass.requestFocus();
                //       Toast.makeText(RegisterActivity.this, "Short Password", Toast.LENGTH_SHORT).show();
            }

            flag6 = 1;
        }

        if ((cpass.getText().toString().equals(pass.getText().toString()) == false)) {
            cpass.setError("Passwords didn't match");
            cpass.requestFocus();
            flag7 = 1;
            // Toast.makeText(RegisterActivity.this, "Passwords didn't match", Toast.LENGTH_SHORT).show();
        } else if (((pass.getText().toString().length() == 0 && cpass.getText().toString().length() == 0))) {
            pass.setError("Password not entered");
            cpass.setError("Password not entered");
        }

        else {
            if ( (police.isChecked() ||attendant.isChecked()) && secret.getText().toString().length() < 6) {
                secret.setError("Secret Code must be of 6 characters");
                secret.requestFocus();

            }

        }




        //TO BE CODED (All validations check)

                /*while (flag1 == 0 && flag2 == 0 && flag3 == 0 && flag4 == 0 && flag5 == 0 && flag6 == 0 && flag8==0 && flag9==0 && flag10==0)
                {
                    Toast.makeText(RegisterActivity.this, "Registeration Successful", Toast.LENGTH_SHORT).show();

                    //users(id INTEGER PRIMARY KEY AUTOINCREMENT,name TEXT,email TEXT,mob TEXT,pass TEXT,profession TEXT)
                    // db.execSQL("insert into users(name,email,mob,pass,profession,secretcode) VALUES('" + name.getText().toString() + "','" + email.getText().toString() + "','" + mob.getText().toString() + "','" + pass.getText().toString() + "','" + profession + "','"+secret_code+"');");
                    Intent i = new Intent(RegisterActivity.this,LoginActivity.class);
                    startActivity(i);
                    break;
                }*/
        //      } else {
        //   Toast.makeText(RegisterActivity.this, "Not registered completely!!", Toast.LENGTH_SHORT).show();
        //    }
        //  }


        //    while (flag1 == 0 && flag2 == 0 && flag3 == 0 && flag4 == 0 && flag5 == 0 && flag6 == 0 && flag8==0 && flag9==0 && flag10==0) {
        //new Callwebservice(name,email,mob,pass,prof,secret).execute();
        new Callwebservice(name.getText().toString(), email.getText().toString(), mob.getText().toString(), pass.getText().toString(), profession,secret_code).execute();
        //  }

    }



    private class Callwebservice extends AsyncTask<Void, Void, String> {
        ProgressDialog dialog;

        String name, email, mob, pass, profession, secret_code;

        public Callwebservice(String name, String email, String mob, String pass, String profession, String secret_code) {
            this.name = name;
            this.email = email;
            this.mob = mob;
            this.pass = pass;
            this.profession = profession;
            this.secret_code = secret_code;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(RegisterActivity.this);
            dialog.setMessage("Loading");
            dialog.setTitle("Please wait");
            dialog.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            try {


                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

                request.addProperty("name", name);
                request.addProperty("email", email);
                request.addProperty("mob", mob);
                request.addProperty("pass", pass);
                request.addProperty("profession", profession);
                request.addProperty("secretcode", secret_code);
                //request.addProperty("veh_no", veh_no);


                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet = true;
                envelope.setOutputSoapObject(request);
                HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
                androidHttpTransport.call(SOAP_ACTION, envelope);

                SoapPrimitive resultstr = (SoapPrimitive) envelope.getResponse();

                Log.d("message", "MEssage : " + resultstr.toString());
                return resultstr.toString();


            } catch (Exception e) {
                Toast.makeText(RegisterActivity.this,"Exception Caught",Toast.LENGTH_SHORT).show();
                Log.e("ERROR", e.toString());
                return "fail";

            }

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            dialog.dismiss();
            if(s.equals("reg successfully"))
            {
                Toast.makeText(RegisterActivity.this,"User Registered",Toast.LENGTH_SHORT).show();
                Intent i = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(i);
            }
            else if(s.equals("Invalid Police Code"))
            {
                Toast.makeText(RegisterActivity.this,"Wrong Police Code",Toast.LENGTH_SHORT).show();
            }
            else if(s.equals("Invalid Attendant Code"))
            {
                Toast.makeText(RegisterActivity.this,"Wrong Attendant Code",Toast.LENGTH_SHORT).show();
            }
            else if(s.equals("invalid") || s.equals("error") )
            {
                Toast.makeText(RegisterActivity.this,"User Not Registered",Toast.LENGTH_SHORT).show();
            }


        }
    }
}





































   /*register.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                int flag1=0,flag2=0,flag3=0,flag4=0,flag5=0,flag6=0,flag7=0,flag8=0,flag9=0,flag10=0;
                String profession=new String();
                String secret_code=new String();
                if(name.getText().toString().length()==0){
                    name.setError("Name not entered");
                    name.requestFocus();
                    flag1 =1;


//                    Toast.makeText(RegisterActivity.this, "Name Cannot Be Empty", Toast.LENGTH_SHORT).show();
                }
                if(email.getText().toString().length()==0){
                    email.setError("Email not entered");
                    email.requestFocus();
                    flag2 = 1;
                    //                  Toast.makeText(RegisterActivity.this, "Email Cannot Be Empty", Toast.LENGTH_SHORT).show();
                }
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";


                if (!(email.getText().toString().trim().matches(emailPattern)))
                {
                    email.setError("Email not valid");
                    email.requestFocus();
                    flag3 = 1;
                    //             Toast.makeText(RegisterActivity.this, "Invalid email Id", Toast.LENGTH_SHORT).show();
                }
                Boolean go=true;
              Cursor c1=db.rawQuery("select * from users where email ='"+email.getText().toString()+"' ", null);
                if(c1.moveToFirst())
                {
                    email.setError("Email already exists");
                    email.requestFocus();
                    go=false;
                    flag4=1;
                }

                if(mob.getText().toString().length()!=10 || (mob.getText().toString().matches("^[0-9]*$")==false))
                {
                    if(mob.getText().toString().length()==0)
                    {
                        mob.setError("Mobile no. not entered");

                        //          Toast.makeText(RegisterActivity.this, "Mobile no Cannot Be Empty", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        mob.setError("Invalid Mobile number");

                        //         Toast.makeText(RegisterActivity.this, "Invalid mobile no.", Toast.LENGTH_SHORT).show();
                    }
                    mob.requestFocus();
                    flag5=1;
                }

                if(pass.getText().toString().length()<6)
                {
                    if(pass.getText().toString().length()==0)
                    {
                        pass.setError("Password not entered");
                        pass.requestFocus();

                        //          Toast.makeText(RegisterActivity.this, "Password Cannot Be Empty", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        pass.setError("Min length:6");
                        pass.requestFocus();
                        //       Toast.makeText(RegisterActivity.this, "Short Password", Toast.LENGTH_SHORT).show();
                    }

                    flag6=1;
                }


                if((cpass.getText().toString().equals(pass.getText().toString())==false))
                {
                    cpass.setError("Passwords didn't match");
                    cpass.requestFocus();
                    flag7=1;
                    // Toast.makeText(RegisterActivity.this, "Passwords didn't match", Toast.LENGTH_SHORT).show();
                }
                else if(((pass.getText().toString().length()==0 && cpass.getText().toString().length()==0)))
                {
                   pass.setError("Password not entered");
                   cpass.setError("Password not entered");
                }
                else
                {

                   if( (police.isChecked()||attendant.isChecked()) && secret.getText().toString().length()<5 && (!secret.getText().toString().equals("dummy")))
                {
                   secret.setError("Secret Code must be of at least 5 characters");
                        secret.requestFocus();
                        flag8=1;
                }
                    if(go==true) {
                        if (citizen.isChecked()) {
                            profession = "Citizen";
                            secret_code="dummy";
                        }
                        if (police.isChecked()) {
                            profession = "Police";
                            secret_code=secret.getText().toString();


                  Cursor c2=db.rawQuery("select code from police where code ='"+secret.getText().toString()+"' ", null);
                            if(c2.moveToFirst()==false)
                            {
                                secret.setError("Invalid Code");
                                secret.requestFocus();
                                flag9=1;
                            }

                        }


                        if (attendant.isChecked()) {
                            profession = "Attendant";
                            secret_code=secret.getText().toString();


                          Cursor c3=db.rawQuery("select code from attendant where code ='"+secret.getText().toString()+"' ", null);
                            if(c3.moveToFirst()==false)
                            {
                                secret.setError("Invalid Code");
                                secret.requestFocus();
                                flag10=1;
                            }

                        }
                        while (flag1 == 0 && flag2 == 0 && flag3 == 0 && flag4 == 0 && flag5 == 0 && flag6 == 0 && flag8==0 && flag9==0 && flag10==0) {
                            Toast.makeText(RegisterActivity.this, "Registeration Successful", Toast.LENGTH_SHORT).show();

                            //users(id INTEGER PRIMARY KEY AUTOINCREMENT,name TEXT,email TEXT,mob TEXT,pass TEXT,profession TEXT)
                           // db.execSQL("insert into users(name,email,mob,pass,profession,secretcode) VALUES('" + name.getText().toString() + "','" + email.getText().toString() + "','" + mob.getText().toString() + "','" + pass.getText().toString() + "','" + profession + "','"+secret_code+"');");
                            Intent i = new Intent(RegisterActivity.this,LoginActivity.class);
                            startActivity(i);
                            break;
                        }
                    }
                    else
                    {
                            Toast.makeText(RegisterActivity.this,"Not registered completely!!",Toast.LENGTH_SHORT).show();
                    }



                }
            }
        });*/