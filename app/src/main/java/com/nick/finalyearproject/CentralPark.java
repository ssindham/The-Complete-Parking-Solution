package com.nick.finalyearproject;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by CompuCareInfotech on 2/25/2017.
 *
 *
 */

public class CentralPark extends Activity {

    public static String NAMESPACE="http://tempuri.org/";
    //public static String URL = "http://192.168.1.8/WebSite1/Service.asmx";
    public static String URL =WebServiceClass.URL;

    public static String METHOD_NAME="storeParkingTimings";
    public static String SOAP_ACTION="http://tempuri.org/storeParkingTimings";

    EditText date1;
    EditText time1;
    EditText date2;
    EditText time2;
    Button calculate_charge;
    EditText duration;
    EditText charge;
    Button pay_charge;


    private PendingIntent pendingIntent;
    private AlarmManager manager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.central_park1);
        date1=(EditText)findViewById(R.id.date1);
        date2=(EditText)findViewById(R.id.date2);
        time1=(EditText)findViewById(R.id.time1);
        time2=(EditText)findViewById(R.id.time2);
        duration=(EditText)findViewById(R.id.time);
        charge=(EditText)findViewById(R.id.charge);
        calculate_charge=(Button)findViewById(R.id.calculate_charge);
        pay_charge=(Button)findViewById(R.id.pay_button);




        date1.setText("01-03-2017");
      //  time1.setText("21:40");
        date2.setText("01-03-2017");
      //  time2.setText("22:40");

        //Intent alarmIntent = new Intent(this, AlarmReceiver.class);
//        pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, 0);

        calculate_charge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CentralPark.this,"Calculate Charge Button Clicked",Toast.LENGTH_SHORT).show();
          //      duration.setText("something");
          //      charge.setText("something");
                int days=0;
                int hours=0;
                int min=0;
                int charge_final;


                DateFormat df = DateFormat.getInstance();
                try {
                   SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
             //       SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd 'at' hh:mm");
                    String s1=time1.getText().toString();
                    String s2=time2.getText().toString();
                    //String s1="2017.12.01 12:00";
                    //String s2="2017.12.01 13:00";
                    Date startDate = simpleDateFormat.parse(s1);
                    Date endDate = simpleDateFormat.parse(s2);

                    long difference = endDate.getTime() - startDate.getTime();
                    if(difference<0)
                    {
                        Date dateMax = simpleDateFormat.parse("24:00");
                        Date dateMin = simpleDateFormat.parse("00:00");
                        difference=(dateMax.getTime() -startDate.getTime() )+(endDate.getTime()-dateMin.getTime());
                    }
                     days = (int) (difference / (1000*60*60*24));
                     hours = (int) ((difference - (1000*60*60*24*days)) / (1000*60*60));
                     min = (int) (difference - (1000*60*60*24*days) - (1000*60*60*hours)) / (1000*60);
                    Log.i("log_tag","Hours: "+hours+", Mins: "+min);
                    Toast.makeText(CentralPark.this,"Differece is:"+hours+" Hours and"+min+" Mins",Toast.LENGTH_SHORT).show();
                    duration.setText(hours+" Hours "+min+" Mins");

                }
                catch (Exception e)
                {
                    Toast.makeText(CentralPark.this,"Exception",Toast.LENGTH_SHORT).show();
                }


                double charge_per_minute=(double)2/3;

                double price_to_pay=(hours*(charge_per_minute*60))+(min*charge_per_minute);
                charge_final=(int)(price_to_pay+0.5);
                Toast.makeText(CentralPark.this,"Charge is:"+charge_final,Toast.LENGTH_SHORT).show();
                charge.setText(Integer.toString(charge_final));



          //      start_time=time1.getText().toString();
           //     end_time=time2.getText().toString();



                manager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
                int interval = 5000; // 5 seconds

                manager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), interval, pendingIntent);


            }
        });

        pay_charge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CentralPark.this,"Pay Charge Button Clicked",Toast.LENGTH_SHORT).show();
                Intent in=new Intent(CentralPark.this,PayOnline.class);
                Bundle bundle = getIntent().getExtras();
                String email = bundle.getString("email");
                String u_id = bundle.getString("u_id");
                TimeEnded.u_id=u_id;
                TimeStarted.u_id=u_id;
                TimeEnded.email=email;
                TimeEnded.old_end_time=time2.getText().toString();
                in.putExtra("email",email);
                in.putExtra("u_id",u_id);
                in.putExtra("type","central_rent_charge");
                in.putExtra("amnt",charge.getText().toString());



                new Callwebservice1(u_id,time1.getText().toString(), time2.getText().toString()).execute();
                Intent alarmIntent = new Intent(CentralPark.this, AlarmReceiver.class);
                pendingIntent = PendingIntent.getBroadcast(CentralPark.this, 0, alarmIntent, 0);


                startActivity(in);


            }
        });

    }
    private class Callwebservice1  extends AsyncTask<Void,Void,String>
    {
        ProgressDialog dialog;

        String u_id,start_time, end_time;

        public Callwebservice1( String u_id,String start_time,String end_time)
        {
            this.u_id = u_id;
            this.start_time = start_time;
            this.end_time = end_time;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(CentralPark.this);
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
                request.addProperty("start_time", start_time);
                request.addProperty("end_time", end_time);

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
            Toast.makeText(CentralPark.this, result, Toast.LENGTH_SHORT).show();
            //   String result="58,Citizen";
            if(result.equals("timings inserted"))
            Toast.makeText(CentralPark.this,"Time Inserted",Toast.LENGTH_SHORT).show();
            if(result.equals("Timings Updated successfully") )
            {
                Toast.makeText(CentralPark.this,"Time Updated",Toast.LENGTH_SHORT).show();
            }
        }
    }

}
