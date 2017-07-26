package com.nick.finalyearproject;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class Register_test extends AppCompatActivity {


    public static String NAMESPACE="http://tempuri.org/";
    public static String URL="http://192.168.1.70/WEBSERVICE1/WebService.asmx";
    EditText e1 ;
    public static String METHOD_NAME="Reg";
    public static String SOAP_ACTION="http://tempuri.org/Registration";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_test);
    }
    public void click1(View view)
    {
        e1 = (EditText) findViewById(R.id.editText);
        new Callwebservice().execute();
    }

    private class Callwebservice  extends AsyncTask<Void,Void,String> {

        ProgressDialog dialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(Register_test.this);
            dialog.setMessage("Loading");
            dialog.setTitle("Please wait");
            dialog.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            try
            {
                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

                request.addProperty("name", e1.getText().toString());
               // request.addProperty("username", e2.getText().toString());
               // request.addProperty("pass", e3.getText().toString());



                request.addProperty("hobby", "singing,hobby");



                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
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
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            dialog.dismiss();
        }
    }
}
