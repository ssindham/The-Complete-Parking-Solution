package com.nick.finalyearproject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class LoginActivity extends Activity {
	
	ImageButton imgbtn1;
	EditText email;
	EditText pass;
	TextView registerScreen;
	TextView forgotPassword;
	Button login;

	//temporary
	Button temp,temp1,temp2,temp3,temp4;

	//SQLiteDatabase db;

	public static String NAMESPACE="http://tempuri.org/";
	//public static String URL = "http://192.168.1.8/WebSite1/Service.asmx";
	public static String URL =WebServiceClass.URL;

	public static String METHOD_NAME="login";
	public static String SOAP_ACTION="http://tempuri.org/login";
	

    

@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);

	requestWindowFeature(Window.FEATURE_NO_TITLE);
	getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
			WindowManager.LayoutParams.FLAG_FULLSCREEN);

	setTheme(android.R.style.Theme);
	setContentView(R.layout.login);
	if (android.os.Build.VERSION.SDK_INT > 9)
	{
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
	}

	imgbtn1 = (ImageButton) findViewById(R.id.imageButton1);
	email = (EditText) findViewById(R.id.login_email);
	pass = (EditText) findViewById(R.id.login_pass);
	login = (Button) findViewById(R.id.btnLogin);
	registerScreen = (TextView) findViewById(R.id.link_to_register);
	forgotPassword = (TextView) findViewById(R.id.link_to_forgot);

	//temporary
	temp=(Button)findViewById(R.id.button5);

	//temporary
	temp1=(Button)findViewById(R.id.button6);

	//temporary
	temp2=(Button)findViewById(R.id.button7);

	//temporary
	temp3=(Button)findViewById(R.id.button8);

	//temporary
	temp4=(Button)findViewById(R.id.button9);










	registerScreen.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
	forgotPassword.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);


//	db = openOrCreateDatabase("SmartParking.db", MODE_PRIVATE, null);

//	db.execSQL("create table IF NOT EXISTS users(id INTEGER PRIMARY KEY AUTOINCREMENT,name TEXT,email TEXT,mob TEXT,pass TEXT,profession TEXT,secretcode TEXT);");


	//temporary
/*	temp.setOnClickListener(new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent in=new Intent(LoginActivity.this,Main2Activity.class);
			startActivity(in);
		}
	});*/
	/*temp.setOnClickListener(new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent in=new Intent(LoginActivity.this,Temp_Renter.class);
		//	in.putExtra("email", email.getText().toString());
			in.putExtra("email", "nick1@gmail.com");
			startActivity(in);
		}
	});*/

    temp.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

	//		Intent in=new Intent(LoginActivity.this,Alarm.class);
			Intent in=new Intent(LoginActivity.this,CentralPark.class);
			in.putExtra("email","nick1@gmail.com");
			in.putExtra("u_id","53");

			startActivity(in);

            //Intent in=new Intent(LoginActivity.this,PayOnline.class);
         //   	in.putExtra("email", email.getText().toString());
			//Intent in=new Intent(LoginActivity.this,Renter.class);
	/*		Intent in=new Intent(LoginActivity.this,RentPlaceToEdit.class);

			in.putExtra("lati","89.22");
			in.putExtra("longi","23.2");
			in.putExtra("two_wheeler","3");
			in.putExtra("four_wheeler","4");
			in.putExtra("area","Dindoli");
			in.putExtra("week_days","MoWeTh");
			in.putExtra("start_time"," 2 pm");
			in.putExtra("end_time","4 pm");
			in.putExtra("price","34");

            RentPlaceToEdit.u_id="49";//
            RentPlaceToEdit.r_id="59";
            RentPlaceToEdit.email="nick1@gmail.com";
			//in.putExtra("email","nick1@gmail.com");
            startActivity(in);*/
        }
    });

	/*temp.setOnClickListener(new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent in=new Intent(LoginActivity.this,Temp_Rentee.class);
			startActivity(in);
		}
	});*/

	//temporary
	temp1.setOnClickListener(new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent i=new Intent(getApplicationContext(),CollectionAmount.class);
            i.putExtra("type","police_indirect_fine");
            i.putExtra("police_id","63");

			startActivity(i);
		}
	});

	//temporary
	temp2.setOnClickListener(new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent i=new Intent(getApplicationContext(),PoliceHome.class);
			startActivity(i);
		}
	});

	//temporary
	temp3.setOnClickListener(new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent i=new Intent(getApplicationContext(),AttendantHome.class);
			startActivity(i);
		}
	});

	temp4.setOnClickListener(new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			//Intent i=new Intent(getApplicationContext(),CentralPark.class);
			//startActivity(i);
			Intent in=new Intent(LoginActivity.this,ValidParking.class);
			//in.putExtra("email", email.getText().toString());
			startActivity(in);

		}
	});


	imgbtn1.setOnClickListener(new View.OnClickListener() {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			finish();

		}
	});
	// Listening to register new account link
	registerScreen.setOnClickListener(new View.OnClickListener() {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
			startActivity(i);

		}
	});


	forgotPassword.setOnClickListener(new View.OnClickListener() {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Intent i = new Intent(getApplicationContext(), ForgotPassword.class);
			startActivity(i);

		}
	});
}
	public void login_onclick(View view)
	{
		int flag1=1,flag2=1,flag3=1;
		email=(EditText)findViewById(R.id.login_email);
		pass=(EditText)findViewById(R.id.login_pass);
		Log.d("Clicked","Login Clicked");
		Toast.makeText(LoginActivity.this,"Login Clicked",Toast.LENGTH_LONG).show();
		if(email.getText().toString().length()==0)
		{
			email.setError("Email not entered");
			email.requestFocus();
			flag1=0;
			Toast.makeText(LoginActivity.this, "Email Cannot Be Empty ", Toast.LENGTH_SHORT).show();

		}
		String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";



		if (!(email.getText().toString().trim().matches(emailPattern)))
		{
			email.setError("Email not valid");
			email.requestFocus();
			flag2=0;
			Toast.makeText(LoginActivity.this, "Invalid email Id", Toast.LENGTH_SHORT).show();
		}

		if(pass.getText().toString().length()<6)
		{
			pass.setError("Min Password length:6 ");
			pass.requestFocus();
			flag3=0;
			Toast.makeText(LoginActivity.this, "Password Cannot Be Empty", Toast.LENGTH_SHORT).show();
		}
		if(flag1==1 && flag2==1 && flag3==1) {
			new Callwebservice1(email.getText().toString(), pass.getText().toString()).execute();
		}




	}

	private class Callwebservice1  extends AsyncTask<Void,Void,String>
	{
		ProgressDialog dialog;

		String email,pass;

		public Callwebservice1( String email,String pass)
		{
			this.email = email;
			this.pass = pass;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog = new ProgressDialog(LoginActivity.this);
			dialog.setMessage("Loading");
			dialog.setTitle("Please wait");
			dialog.show();
		}

		@Override
		protected String doInBackground(Void... params) {
			try
			{



				SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);


				request.addProperty("email", email);
				request.addProperty("pass", pass);



				SoapSerializationEnvelope envelope=new SoapSerializationEnvelope((SoapEnvelope.VER11));
				envelope.dotNet=true;
				envelope.setOutputSoapObject(request);
				HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
				androidHttpTransport.call(SOAP_ACTION, envelope);

				SoapPrimitive resultstr = (SoapPrimitive)envelope.getResponse();

				Log.d("message","MEssage : "+resultstr.toString());
				return resultstr.toString();


			}
			catch(Exception e)
			{
				Log.e("ERROR", e.toString());
				return "fail";

			}

		}

		@Override
		protected void onPostExecute(String u_id_profession) {
			super.onPostExecute(u_id_profession);
            Toast.makeText(LoginActivity.this, "Logged In User:" + u_id_profession, Toast.LENGTH_SHORT).show();
         //   String result="58,Citizen";
            String[] array1 = u_id_profession.split(",");
            String u_id=array1[0];
            String prof=array1[1];
            System.out.println( array1[0]);
            System.out.println( array1[1]);

			dialog.dismiss();
		//	if(u_id.equals("invalid") ||u_id.equals("fail") )// || u_id.equals("fail"))
            if(!u_id_profession.equals("invalid"))
                 {
                     if(prof.equals("Citizen")) {

                         Intent i = new Intent(LoginActivity.this, ChoiceActivity.class);


                         i.putExtra("email", email);
                         i.putExtra("u_id", u_id);
                         startActivity(i);
                     }

                     if(prof.equals("Police")) {
                         Intent i = new Intent(LoginActivity.this, PushPoliceId.class);

                         i.putExtra("police_id", u_id);
                         startActivity(i);
                     }
					 if(prof.equals("Attendant")) {
						 Intent i = new Intent(LoginActivity.this, ReaderActivity.class);

						 i.putExtra("attendant_id", u_id);
						 startActivity(i);
					 }



			}
			else
			{

                Toast.makeText(LoginActivity.this,"Invalid Credentials",Toast.LENGTH_SHORT).show();


			}
		}
	}
}

    /* login.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub

//			startActivity(new Intent(LoginActivity.this,ChoiceActivity.class));

			//changes
			
			
			if(email.getText().toString().length()==0){
                email.setError("Email not entered");
                email.requestFocus();
                Toast.makeText(LoginActivity.this, "Email Cannot Be Empty", Toast.LENGTH_SHORT).show();
         }
			  String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

			
			  if (!(email.getText().toString().trim().matches(emailPattern)))
                {
	        	  email.setError("Email not valid");
                    email.requestFocus();
	            Toast.makeText(LoginActivity.this, "Invalid email Id", Toast.LENGTH_SHORT).show();
	        } 
			  
			  if(pass.getText().toString().length()==0)
			  	{
			  			pass.setError("Password not entered");
			  			pass.requestFocus();
			  			Toast.makeText(LoginActivity.this, "Password Cannot Be Empty", Toast.LENGTH_SHORT).show();
			  	}
			  
			  else
			  {
				  Cursor c1=db.rawQuery("select * from users where email ='"+email.getText().toString()+"' and pass = '"+pass.getText().toString()+"' ", null);
					if(c1.moveToNext())
					{
						if(email.getText().toString().equals(c1.getString(2)) && pass.getText().toString().equals(c1.getString(4)) )
						{
							c1.close();
							startActivity(new Intent(LoginActivity.this,ChoiceActivity.class));
						}
						else
						{
							Toast.makeText(LoginActivity.this, "invalid id or pass",Toast.LENGTH_LONG).show();
						}	
					}
					else
					{
						Toast.makeText(LoginActivity.this, "invalid id or pass",Toast.LENGTH_LONG).show();
					}
					
					c1.close();
			  }
			  
		}
	});*/
//}
//}
