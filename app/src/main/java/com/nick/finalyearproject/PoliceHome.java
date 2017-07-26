package com.nick.finalyearproject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

/**
 * Created by CompuCareInfotech on 2/24/2017.
 */

public class PoliceHome extends Activity {

    public static String NAMESPACE="http://tempuri.org/";
    //public static String URL = "http://192.168.1.8/WebSite1/Service.asmx";
    public static String URL =WebServiceClass.URL;

    public static String METHOD_NAME="getPushIdbyVehNoAndSendNotification";
    public static String SOAP_ACTION="http://tempuri.org/getPushIdbyVehNoAndSendNotification";


    EditText vehicleNo,fineAmount;
    Button submitButton;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.police_home);
        Bundle bundle = getIntent().getExtras();
        final String police_id = bundle.getString("police_id");
        Toast.makeText(PoliceHome.this,"Police Logged In:"+police_id,Toast.LENGTH_SHORT).show();

        vehicleNo = (EditText) findViewById(R.id.vehicle_no);
        fineAmount = (EditText) findViewById(R.id.fine_amount);

        submitButton = (Button) findViewById(R.id.submit_button);


        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Callwebservice1(vehicleNo.getText().toString(), fineAmount.getText().toString(),police_id).execute();


            }
        });
    }
        private class Callwebservice1  extends AsyncTask<Void,Void,String>
        {
            ProgressDialog dialog;

            String vehicleNo,fineAmount,police_id;

            public Callwebservice1( String vehicleNo,String fineAmount,String police_id)
            {
                this.vehicleNo = vehicleNo;
                this.fineAmount = fineAmount;
                this.police_id=police_id;
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                dialog = new ProgressDialog(PoliceHome.this);
                dialog.setMessage("Loading");
                dialog.setTitle("Please wait");
                dialog.show();
            }

            @Override
            protected String doInBackground(Void... params) {
                try
                {



                    SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);


                    request.addProperty("veh_no", vehicleNo);
                    request.addProperty("fine", fineAmount);
                    request.addProperty("police_id", police_id);




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
                Toast.makeText(PoliceHome.this,result+" Sent a Notification",Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                if(!result.equals("error"))// || u_id.equals("fail"))
                {
                    
                    Intent i=new Intent(getApplicationContext(),CollectionAmount.class);
                    i.putExtra("type","police_indirect_fine");
                    i.putExtra("police_id",result);
                    startActivity(i);

                }
                else
                {

                    Toast.makeText(PoliceHome.this,"User Not Registered In The Database",Toast.LENGTH_SHORT).show();

                }
            }
        }


}
