package com.nick.finalyearproject;

/**
 * Created by CompuCareInfotech on 4/8/2017.
 */

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class AlarmReceiver extends BroadcastReceiver {

    static  String u_ids[]={"0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0"};
    static String end_time[]={"0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0"};
    static String start_time[]={"0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0"};
    static  int j;


    public static String NAMESPACE="http://tempuri.org/";
    //public static String URL = "http://192.168.1.8/WebSite1/Service.asmx";
    public static String URL =WebServiceClass.URL;

    public static String METHOD_NAME="getParkingTimingsorsendnotification";
    public static String SOAP_ACTION="http://tempuri.org/getParkingTimingsorsendnotification";


    @Override
    public void onReceive(Context arg0, Intent arg1) {
        new Callwebservice1("0", "dummy").execute(); //0 is for inserting timings
        //1 to start time
        //2 to end time

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df3 = new SimpleDateFormat("HH:mm");
        String formattedDate3 = df3.format(c.getTime());

        Toast.makeText(arg0, "Current Time is:" + formattedDate3, Toast.LENGTH_SHORT).show();

        SimpleDateFormat format = new SimpleDateFormat("HH:mm");


        Date d0 = null; //start time
        Date d1 = null; //current time
        Date d2 = null; //end time

        try {
            for(int k=0;k<j;k++){
                d0 = format.parse(start_time[k]);
                d1 = format.parse(formattedDate3);
                d2 = format.parse(end_time[k]);

                long diff = d2.getTime() - d1.getTime();//End time and cuurent time
                long diff1 = d0.getTime() - d1.getTime();//Start time and cuurent time
                long seconds1 = TimeUnit.MILLISECONDS.toSeconds(diff1);
                if (seconds1 == 0) {
                    Toast.makeText(arg0, "Time Shuru !!", Toast.LENGTH_SHORT).show();
                    new Callwebservice1("1",u_ids[k]).execute();
                     //1 is for time start notification

               //      Toast.makeText(arg0, u_ids[k], Toast.LENGTH_SHORT).show();

                }


                long seconds = TimeUnit.MILLISECONDS.toSeconds(diff);
                long minutes = TimeUnit.MILLISECONDS.toMinutes(diff);

                if (seconds == 60) {
                    Toast.makeText(arg0, "1 min Left.. Send Notification", Toast.LENGTH_SHORT).show();


                }
                if (seconds == 0) {
                    Toast.makeText(arg0, "Time Khatam", Toast.LENGTH_SHORT).show();
              //      Toast.makeText(arg0, u_ids[k], Toast.LENGTH_SHORT).show();
                    new Callwebservice1("2", u_ids[k]).execute();

                }
            } //for ends

        } catch (ParseException e) {
            e.printStackTrace();

            Toast.makeText(arg0, "Exception", Toast.LENGTH_SHORT).show();
        }
    }





    private class Callwebservice1  extends AsyncTask<Void,Void,String> {
        ProgressDialog dialog;
        String  opcode,u_id;
        public Callwebservice1(String  opcode,String u_id) {
        this.opcode=opcode;
            this.u_id=u_id;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {
            try {

                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
                request.addProperty("opcode", opcode);
                request.addProperty("u_id", u_id);

                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope((SoapEnvelope.VER11));
                envelope.dotNet = true;
                envelope.setOutputSoapObject(request);
                HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
                androidHttpTransport.call(SOAP_ACTION, envelope);

                SoapPrimitive resultstr = (SoapPrimitive) envelope.getResponse();

                Log.d("message", "MEssage : " + resultstr.toString());
                return resultstr.toString();


            } catch (Exception e) {
                Log.e("ERROR", e.toString());
                return "fail";

            }

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            //dialog.dismiss();

            if(result.contains("#")) {
                String[] LocationArray = result.split("#");
                j = 0;
                for (int i = 0; i < LocationArray.length; i++) {
                    String each_loc[] = LocationArray[i].split("~");
                    u_ids[j]=each_loc[0];
                    start_time[j] = each_loc[1];
                    end_time[j] = each_loc[2];
                    j++;
                }
            }
        }
    }
    }

