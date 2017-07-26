package com.nick.finalyearproject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gcm.GCMRegistrar;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

/**
 * Created by CompuCareInfotech on 4/4/2017.
 */

public class PushPoliceId extends Activity {
    public static String NAMESPACE = "http://tempuri.org/";
    //public static String URL = "http://192.168.1.173/WebSite1/Service.asmx";
    public static String URL =WebServiceClass.URL;


    public static String METHOD_NAME = "RegisterPushId";
    public static String SOAP_ACTION = "http://tempuri.org/RegisterPushId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        callapi1();

    }
    private void callapi1() {

        String regId;
        GCMRegistrar.checkDevice(PushPoliceId.this);
        GCMRegistrar.checkManifest(PushPoliceId.this);
        regId = GCMRegistrar.getRegistrationId(PushPoliceId.this);
        if (regId.equals("")) {
            //GCMRegistrar.register(ChoiceActivity.this, "481100784977");
            GCMRegistrar.register(PushPoliceId.this, "1034075539594");
            Toast.makeText(PushPoliceId.this,regId,Toast.LENGTH_LONG).show();
            Log.v("Push",regId+"hi");


        } else {
            Bundle bundle = getIntent().getExtras();
            String police_id = bundle.getString("police_id");
            Toast.makeText(PushPoliceId.this,police_id+" "+ regId,Toast.LENGTH_LONG).show();
            new Callwebservice1(police_id,regId).execute();
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
            dialog = new ProgressDialog(PushPoliceId.this);
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

        @Override
        protected void onPostExecute(String totalString) {
            super.onPostExecute(totalString);

            dialog.dismiss();

            Toast.makeText(PushPoliceId.this, totalString,Toast.LENGTH_SHORT).show();
            Bundle bundle = getIntent().getExtras();
            Intent i=new Intent(PushPoliceId.this,PoliceHome.class);
            String police_id = bundle.getString("police_id");
            i.putExtra("type","police_indirect_fine");
            i.putExtra("police_id",police_id);
            startActivity(i);



        }
    }
}
