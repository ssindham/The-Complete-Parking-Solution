package com.nick.finalyearproject;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
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
 * Created by CompuCareInfotech on 4/8/2017.
 */

public class TimeEnded extends Activity {
    EditText extra_mins;
    Button extra_charge_button;
    EditText extra_charge;
    Button pay;

    static String email=null;
    static String u_id=null;
    static  String old_end_time=null;


    public static String NAMESPACE="http://tempuri.org/";
    //public static String URL = "http://192.168.1.8/WebSite1/Service.asmx";
    public static String URL =WebServiceClass.URL;

    public static String METHOD_NAME="updateendtime";
    public static String SOAP_ACTION="http://tempuri.org/updateendtime";

    private PendingIntent pendingIntent;
    private AlarmManager manager;

    int mins;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.time_ended);
        extra_mins=(EditText)findViewById(R.id.extra_time);
        extra_charge_button=(Button)findViewById(R.id.extra_charge_button);
        extra_charge=(EditText)findViewById(R.id.extra_charge_text);
        pay=(Button)findViewById(R.id.pay_button_new);
        Toast.makeText(TimeEnded.this,"Email:"+email,Toast.LENGTH_SHORT).show();
        Toast.makeText(TimeEnded.this,"U_Id:"+ u_id,Toast.LENGTH_SHORT).show();
        Toast.makeText(TimeEnded.this,"Old End Time:"+old_end_time,Toast.LENGTH_SHORT).show();


        extra_charge_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TimeEnded.this,"Charge Button Clicked",Toast.LENGTH_LONG).show();
                double charge_per_minute=(double)2/3;
                mins=Integer.parseInt(extra_mins.getText().toString());
                double price_to_pay=(mins*charge_per_minute);
                int charge_final=(int)(price_to_pay+0.5);
                extra_charge.setText(Integer.toString(charge_final));
            }
        });

        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TimeEnded.this,"Pay Charge Button Clicked",Toast.LENGTH_SHORT).show();
                Intent in=new Intent(TimeEnded.this,PayOnline.class);
                Bundle bundle = getIntent().getExtras();
                in.putExtra("email",email);
                in.putExtra("u_id",u_id);
                in.putExtra("type","central_rent_charge");
                in.putExtra("amnt",extra_charge.getText().toString());

                String[] hm = old_end_time.split(":");
                int h = Integer.parseInt(hm[0]);
                int m = Integer.parseInt(hm[1]);
                int t = h * 60 + m;      // total minutes
                t += mins;            // add the desired offset

                while (t < 0) {          // fix `t` so that it's never negative
                    t += 1440;             // 1440 minutes in a day
                }

                int nh = (t / 60) % 24;  // calculate new hours
                int nm = t % 60;         // calculate new minutes

                String newTime = String.format("%02d:%02d", nh, nm);

                    new Callwebservice1(u_id, newTime).execute();
                Intent alarmIntent = new Intent(TimeEnded.this, AlarmReceiver.class);
                pendingIntent = PendingIntent.getBroadcast(TimeEnded.this, 0, alarmIntent, 0);
                startActivity(in);
            }
        });

    }
    private class Callwebservice1  extends AsyncTask<Void,Void,String>
    {
        ProgressDialog dialog;

        String u_id,newTime;

        public Callwebservice1( String u_id,String newTime)
        {
            this.u_id = u_id;
            this.newTime = newTime;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(TimeEnded.this);
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
                request.addProperty("newTime", newTime);



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
            Toast.makeText(TimeEnded.this, result, Toast.LENGTH_SHORT).show();

        }
    }
}
