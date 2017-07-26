package com.nick.finalyearproject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gcm.GCMRegistrar;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;


public class ChoiceActivity extends Activity {
	
	android.app.ActionBar bar;
	ImageButton setting;

	public static String NAMESPACE = "http://tempuri.org/";
	//public static String URL = "http://192.168.1.173/WebSite1/Service.asmx";
	public static String URL =WebServiceClass.URL;


	public static String METHOD_NAME = "RegisterPushId";
	public static String SOAP_ACTION = "http://tempuri.org/RegisterPushId";


	ImageButton module1,module2,module3,module4;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.choice);
	/*	Bundle bundle = getIntent().getExtras();
		final String email = bundle.getString("email");*/



		//for changing the color of ActionBar
		// bar= getActionBar();
//    	bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#29A9D2")));
    	
    	setting=(ImageButton) findViewById(R.id.imageButton1);
    	
    	module1=(ImageButton) findViewById(R.id.validSpot);
    	module2=(ImageButton) findViewById(R.id.rentSpace);
    	module3=(ImageButton) findViewById(R.id.payOnline);
    	module4=(ImageButton) findViewById(R.id.payOnce);
    	
    	
    	setting.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
//				Toast.makeText(ChoiceActivity.this,"Profile Settings",Toast.LENGTH_LONG).show();
			}
		});
    	
        module1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Toast.makeText(ChoiceActivity.this,"Check the valid spot!",Toast.LENGTH_LONG).show();
				Intent i=new Intent(ChoiceActivity.this,ValidParking.class);
			//	i.putExtra("email",email);
				startActivity(i);
			}
		});
        
        module2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Toast.makeText(ChoiceActivity.this,"Rent Parking Space!",Toast.LENGTH_LONG).show();
				Intent intent=new Intent(getApplicationContext(),RenterRentee.class);
				Bundle bundle = getIntent().getExtras();
				String email = bundle.getString("email");
				String u_id = bundle.getString("u_id");
				intent.putExtra("email",email);
				intent.putExtra("u_id",u_id);


				startActivity(intent);

			}
		});
        
        module3.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Toast.makeText(ChoiceActivity.this,"Pay Online!",Toast.LENGTH_LONG).show();
				Intent i=new Intent(ChoiceActivity.this,PayOnline.class);
				startActivity(i);
			}
		});
        
        module4.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Toast.makeText(ChoiceActivity.this,"Pay Once,Park Anywhere!",Toast.LENGTH_LONG).show();
                Intent in=new Intent(ChoiceActivity.this,CentralPark.class);
                Bundle bundle = getIntent().getExtras();
                String u_id=bundle.getString("u_id");
                String email=bundle.getString("email");
                in.putExtra("email",email);
                in.putExtra("u_id",u_id);
                startActivity(in);

			}
		});
        callapi1();

		}

	private void callapi1() {

		String regId;
		GCMRegistrar.checkDevice(ChoiceActivity.this);
		GCMRegistrar.checkManifest(ChoiceActivity.this);
		regId = GCMRegistrar.getRegistrationId(ChoiceActivity.this);
		if (regId.equals("")) {
			//GCMRegistrar.register(ChoiceActivity.this, "481100784977");
			GCMRegistrar.register(ChoiceActivity.this, "1034075539594");
			Toast.makeText(ChoiceActivity.this,regId,Toast.LENGTH_LONG).show();
			Log.v("Push",regId+"hi");


		} else {
            Bundle bundle = getIntent().getExtras();
            String u_id=bundle.getString("u_id");
			Toast.makeText(ChoiceActivity.this,u_id+" "+ regId,Toast.LENGTH_LONG).show();
			new Callwebservice1(u_id,regId).execute();
			Log.v("Push",regId);


			//new deviceinfo().execute();
		}


	}

	private class Callwebservice1 extends AsyncTask<Void,Void,String>
	{
		ProgressDialog dialog;

		String u_id=null,regId=null;

		public Callwebservice1( String u_id,String regId)
		{
			this.u_id = u_id;
			RenterRentee.u_id=u_id;
			this.regId = regId;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog = new ProgressDialog(ChoiceActivity.this);
			dialog.setMessage("Loading");
			dialog.setTitle("Please wait");
			dialog.show();
		}

		@Override
		protected String doInBackground(Void... params) {
			try {


				SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

				request.addProperty("u_id", u_id);
				request.addProperty("push_id", regId);

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
	//			Toast.makeText(ChoiceActivity.this,"Exception Caught",Toast.LENGTH_SHORT).show();
				Log.e("ERROR", e.toString());
				return "fail";

			}

		}
		/*@Override
		protected String doInBackground(Void... params) {
			try
			{
				SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

				request.addProperty("u_id", u_id);
				request.addProperty("p_id", r_id);


				SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
				envelope.dotNet = true;
				envelope.setOutputSoapObject(request);
				HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
				androidHttpTransport.call(SOAP_ACTION, envelope);

				SoapPrimitive resultstr = (SoapPrimitive) envelope.getResponse();

				Log.d("message", "MEssage : " + resultstr.toString());
				return resultstr.toString();


			}
			catch (Exception e) {
//				Toast.makeText(ChoiceActivity.this,"Exception Caught",Toast.LENGTH_SHORT).show();
				Log.e("ERROR", e.toString());
                e.printStackTrace();
				return "fail";

			}

		}*/

		@Override
		protected void onPostExecute(String totalString) {
			super.onPostExecute(totalString);

			dialog.dismiss();

			Toast.makeText(ChoiceActivity.this, totalString,Toast.LENGTH_SHORT).show();



		}
	}

	}

	

