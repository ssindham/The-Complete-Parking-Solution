package com.nick.finalyearproject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

/**
 * Created by CompuCareInfotech on 4/8/2017.
 */

public class TimeStarted extends Activity {
    TextView msg;
    Button rent_places;
    Button receipt_button;


    static String u_id;
    public static String NAMESPACE="http://tempuri.org/";
    //public static String URL = "http://192.168.1.8/WebSite1/Service.asmx";
    public static String URL =WebServiceClass.URL;

    public static String METHOD_NAME="getparktimes";
    public static String SOAP_ACTION="http://tempuri.org/getparktimes";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.time_started);
        msg=(TextView)findViewById(R.id.rent_msg);
        rent_places=(Button)findViewById(R.id.renting_places);
        receipt_button=(Button) findViewById(R.id.receipt_button);
        receipt_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TimeStarted.this, "Receipt Button Clicked", Toast.LENGTH_SHORT).show();
                Intent i=new Intent(TimeStarted.this,GeneratorActivity.class);
                i.putExtra("msg",msg.getText().toString());
                startActivity(i);
            }
        });

        rent_places.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(TimeStarted.this, CustomMain3.class);

                Toast.makeText(TimeStarted.this, "See Govt Places", Toast.LENGTH_SHORT).show();
                startActivity(in);
            }
        });

        new Callwebservice1(u_id).execute();
    }
    private class Callwebservice1  extends AsyncTask<Void,Void,String>
    {
        ProgressDialog dialog;

        String u_id;

        public Callwebservice1( String u_id)
        {
            this.u_id = u_id;

        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(TimeStarted.this);
            dialog.setMessage("Loading");
            dialog.setTitle("Please wait");
            dialog.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            try
            {



                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);


                request.addProperty("u_id", u_id);



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
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            dialog.dismiss();
            String[] abc = result.split("~");
            Toast.makeText(TimeStarted.this, result, Toast.LENGTH_SHORT).show();

            msg.setText("Allowed Parking From "+abc[0]+" To "+abc[1]);

        }
    }
}
