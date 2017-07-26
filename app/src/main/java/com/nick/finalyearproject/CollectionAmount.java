package com.nick.finalyearproject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

/**
 * Created by CompuCareInfotech on 2/25/2017.
 */

public class CollectionAmount extends Activity {

    public static String NAMESPACE="http://tempuri.org/";
    //public static String URL = "http://192.168.1.8/WebSite1/Service.asmx";
    public static String URL =WebServiceClass.URL;

    public static String METHOD_NAME="getcollectionamount";
    public static String SOAP_ACTION="http://tempuri.org/getcollectionamount";
    EditText collected_amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.collection_amount);

        collected_amount=(EditText)findViewById(R.id.collected_amount);

        Bundle bundle = getIntent().getExtras();





        String type=bundle.getString("type");
        String amnt=new String();
        String police_id=bundle.getString("police_id");

        if(type.equals("police_direct_fine")) {
            String s=bundle.getString("msg");
            String[] parts = s.split("y");
            String part1 = parts[0]; // 004
            String part2 = parts[1];
            police_id = part2.replaceAll("[^0-9]", "");

        }
        Toast.makeText(CollectionAmount.this,"Police Id:"+police_id,Toast.LENGTH_LONG).show();

        new Callwebservice1(police_id).execute();
    }
    private class Callwebservice1  extends AsyncTask<Void,Void,String>
    {
        ProgressDialog dialog;

        String police_id;

        public Callwebservice1( String police_id)
        {
            this.police_id = police_id;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(CollectionAmount.this);
            dialog.setMessage("Loading");
            dialog.setTitle("Please wait");
            dialog.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            try
            {



                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);


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
            Toast.makeText(CollectionAmount.this, result, Toast.LENGTH_SHORT).show();
            //   String result="58,Citizen";

            dialog.dismiss();
            //	if(u_id.equals("invalid") ||u_id.equals("fail") )// || u_id.equals("fail"))
            if(!result.equals("error"))
            {
                collected_amount.setText(result);
            }



        }
    }
}