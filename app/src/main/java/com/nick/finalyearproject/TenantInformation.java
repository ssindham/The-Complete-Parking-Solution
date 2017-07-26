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
import android.widget.ImageButton;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

/**
 * Created by CompuCareInfotech on 2/24/2017.
 */

public class TenantInformation extends Activity {


    EditText tenantName,contactNo,location,distance,priceText;
    ImageButton directionButton;
    Button payNow;



    public static String NAMESPACE = "http://tempuri.org/";
   // public static String URL = "http://192.168.1.8/WebSite1/Service.asmx";
   public static String URL =WebServiceClass.URL;


    public static String METHOD_NAME = "ReturnTenantInfo";
    public static String SOAP_ACTION = "http://tempuri.org/ReturnTenantInfo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tenant_information);


    /*    String t_name = bundle.getString("tenanat_name");
        String no = bundle.getString("contact_no");
        String l_name = bundle.getString("l_name");

     //   String charge_per_hour = bundle.getString("charge_per_hour");
        String dist = bundle.getString("distance");
        String lati= bundle.getString("lati");
        String longi= bundle.getString("longi");*/


        tenantName=(EditText)findViewById(R.id.tenant_name);
        contactNo=(EditText)findViewById(R.id.contact_no);
        location=(EditText)findViewById(R.id.location);
        distance=(EditText)findViewById(R.id.distance);
        priceText=(EditText)findViewById(R.id.priceText);

        Bundle bundle = getIntent().getExtras();
        String u_id1=bundle.getString("u_id1");
        String r_id1=bundle.getString("r_id");




        new Callwebservice1(u_id1, r_id1).execute();



  /*      tenantName.setText(t_name);
        contactNo.setText(no);
        location.setText(l_name);
        //charge.setText(charge_per_hour);
        distance.setText(dist);*/



        directionButton=(ImageButton)findViewById(R.id.directionButton);
        payNow=(Button)findViewById(R.id.pay_now);

        payNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(TenantInformation.this,"PayNow Button Clicked",Toast.LENGTH_SHORT).show();
                Bundle bundle = getIntent().getExtras();
                Intent in=new Intent(TenantInformation.this,PayOnline.class);
                String email = bundle.getString("email");
                String u_id = bundle.getString("u_id");
                in.putExtra("email",email);
                in.putExtra("u_id",u_id);
                in.putExtra("type","rent_charge");
                in.putExtra("amnt",priceText.getText().toString());
                startActivity(in);


            }
        });

        directionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TenantInformation.this,"Direction Button Clicked",Toast.LENGTH_SHORT).show();
                Intent i=new Intent(TenantInformation.this,Rentee_Route.class);
                Bundle bundle = getIntent().getExtras();
                String lati=bundle.getString("lati");
                String longi=bundle.getString("longi");
                String place=bundle.getString("place");
                i.putExtra("lati",lati);
                i.putExtra("longi",longi);
                i.putExtra("place",place);
                startActivity(i);
            }
        });


        }




    private class Callwebservice1  extends AsyncTask<Void,Void,String>
    {
        ProgressDialog dialog;

        String u_id,r_id;

        public Callwebservice1( String u_id,String r_id)
        {
            this.u_id = u_id;
            this.r_id = r_id;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(TenantInformation.this);
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
                request.addProperty("r_id", r_id);


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
                Toast.makeText(TenantInformation.this,"Exception Caught",Toast.LENGTH_SHORT).show();
                Log.e("ERROR", e.toString());
                return "fail";

            }

        }

        @Override
        protected void onPostExecute(String totalString) {
            super.onPostExecute(totalString);
            Bundle bundle = getIntent().getExtras();
            String dist=bundle.getString("dist");
            dialog.dismiss();

            Toast.makeText(TenantInformation.this, totalString,Toast.LENGTH_SHORT).show();

            String details[] = totalString.split("~");
            tenantName.setText(details[0]);
            contactNo.setText(details[1]);
            location.setText(details[2]);
            priceText.setText(details[3]);
            distance.setText(dist);

        }
    }

}
