package com.nick.finalyearproject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

/**
 * Created by CompuCareInfotech on 2/26/2017.
 */

public class Temp_Renter extends Activity  {

    static String lat1;
    static String long1;
    static  String l_name1;

    Button locationButton ;
    EditText longitude;
    EditText latitude;
    EditText location;

    EditText twowheeler;
    EditText fourwheeler;

    CheckBox all,mon,tue,wed,thu,fri,sat,sun;
    EditText from,to;

    EditText chargeText;

    Button submitButton;

    public static String NAMESPACE = "http://tempuri.org/";
    //   public static String URL = "http://192.168.1.8/WebSite1/Service.asmx";
    public static String URL = WebServiceClass.URL;

    public static String METHOD_NAME = "RentPlace";
    public static String SOAP_ACTION = "http://tempuri.org/RentPlace";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.temp_renter);



        locationButton=(Button)findViewById(R.id.location_button);
        longitude=(EditText)findViewById(R.id.longitude_text);
        latitude=(EditText)findViewById(R.id.latitude_text);
        location=(EditText)findViewById(R.id.location_text);

        twowheeler=(EditText)findViewById(R.id.twowheeler);
        fourwheeler=(EditText)findViewById(R.id.fourwheeler);



        all=(CheckBox)findViewById(R.id.all);
        mon=(CheckBox)findViewById(R.id.mon);
        tue=(CheckBox)findViewById(R.id.tue);
        wed=(CheckBox)findViewById(R.id.wed);
        thu=(CheckBox)findViewById(R.id.thu);
        fri=(CheckBox)findViewById(R.id.fri);
        sat=(CheckBox)findViewById(R.id.sat);
        sun=(CheckBox)findViewById(R.id.sun);



        all.setChecked(false);
        mon.setChecked(false);
        tue.setChecked(false);
        wed.setChecked(false);
        thu.setChecked(false);
        fri.setChecked(false);
        sat.setChecked(false);
        sun.setChecked(false);



        from=(EditText)findViewById(R.id.from);
        to=(EditText)findViewById(R.id.to);
        chargeText=(EditText)findViewById(R.id.chargeText);
        submitButton=(Button)findViewById(R.id.submitButton);





    }

    @Override
    protected void onResume() {
        super.onResume();
        latitude.setText(lat1);
        longitude.setText(long1);
        location.setText(l_name1);
    }

    public void location_button_onclick(View view)
    {
        Toast.makeText(Temp_Renter.this, "Location Button Clicked", Toast.LENGTH_SHORT).show();
        //String uri = "http://maps.google.com/";
       // Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri));
       // intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
       // startActivity(intent);

        Intent intent = new Intent(Temp_Renter.this,Renter_Location.class);
        startActivity(intent);



    /*    Bundle bundle = getIntent().getExtras();
        String lat = bundle.getString("lat");
        latitude.setText(lat);
        String long1 = bundle.getString("long");
        longitude.setText(long1);*/



    }


    public void rent_register_onclick(View view) {
        Toast.makeText(Temp_Renter.this, "Submit Button Clicked", Toast.LENGTH_SHORT).show();
        int flag1 = 0, flag2 = 0, flag3 = 0, flag4 = 0, flag5 = 0, flag6 = 0, flag7 = 0, flag8 = 0, flag9 = 0, flag10 = 0;

        String twowheeler_data="0";
        String fourwheeler_data="0";
        String week_days=new String();
        String alloted="No";

        Bundle bundle = getIntent().getExtras();
        String u_id = bundle.getString("u_id"); //gets data from login page
       String email = bundle.getString("email"); //gets data from login page



/*        if(twowheeler.getText().toString().length()==0) {

            twowheeler_data="0";
        }

        if(fourwheeler.getText().toString().length()==0) {

            fourwheeler_data="0";
        }
*/

        Boolean go = true;

        if(all.isChecked()) {
            week_days="MTuWeThFrSaSu";
        }
        else {


        if(mon.isChecked()) {
            week_days+="Mo";
        }
        if(tue.isChecked()) {
            week_days+="Tu";
        }
        if(wed.isChecked()) {
            week_days+="We";
        }
        if(thu.isChecked()) {
            week_days+="Th";
        }
        if(fri.isChecked()) {
            week_days+="Fr";
        }
        if(sat.isChecked()) {
            week_days+="Sa";
        }
        if(sun.isChecked()) {
            week_days+="Su";
        }

        }


        if (from.getText().toString().length() == 10){
                from.setError("Enter Time");
            from.requestFocus();
            flag5 = 1;
        }

        if (to.getText().toString().length() == 0){
            to.setError("Enter Time");
            to.requestFocus();
            flag6 = 1;
        }
        if (chargeText.getText().toString().length() == 0){
            chargeText.setError("Enter Charge");
            chargeText.requestFocus();
            flag7 = 1;
        }

        //new Callwebservice(email,longitude.getText().toString(), latitude.getText().toString(), location.getText().toString(),alloted, twowheeler_data, fourwheeler_data,week_days,from.getText().toString(),to.getText().toString(),chargeText.getText().toString()).execute();
        new Callwebservice(email,longitude.getText().toString(), latitude.getText().toString() , twowheeler.getText().toString(), fourwheeler.getText().toString(),location.getText().toString(),alloted,week_days,from.getText().toString(),to.getText().toString(),chargeText.getText().toString()).execute();


    }



    private class Callwebservice extends AsyncTask<Void, Void, String> {
        ProgressDialog dialog;

        String  email, longi, lati, two_wheel, four_wheel, l_name,alloted,week_days, start_time, end_time, charge_per_hour;

        public Callwebservice(String email, String longi, String lati, String two_wheel, String four_wheel, String l_name, String alloted, String week_days, String start_time, String end_time, String charge_per_hour)
        {

            this.email = email;
            this.longi=longi;
            this.lati=lati;
            this.two_wheel=two_wheel;
            this.four_wheel=four_wheel;
            this.l_name=l_name;
            this.alloted=alloted;
            this.week_days=week_days;
            this.start_time=start_time;
            this.end_time=end_time;
            this.charge_per_hour=charge_per_hour;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(Temp_Renter.this);
            dialog.setMessage("Loading");
            dialog.setTitle("Please wait");
            dialog.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            try {


                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

                request.addProperty("email", email);
                request.addProperty("longi", longi);
                request.addProperty("lati", lati);
                request.addProperty("two_wheel", two_wheel);
                request.addProperty("four_wheel", four_wheel);
                request.addProperty("l_name", l_name);
                request.addProperty("alloted", alloted);
                request.addProperty("week_days", week_days);
                request.addProperty("start_time", start_time);
                request.addProperty("end_time", end_time);
                request.addProperty("charge_per_hour", charge_per_hour);



                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet = true;
                envelope.setOutputSoapObject(request);
                HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
                androidHttpTransport.call(SOAP_ACTION, envelope);

                SoapPrimitive resultstr = (SoapPrimitive) envelope.getResponse();

                Log.d("message", "MEssage : " + resultstr.toString());
                return resultstr.toString();


            } catch (Exception e) {
//                Toast.makeText(Renter.this,"Exception Caught",Toast.LENGTH_SHORT).show();
                Log.e("ERROR", e.toString());
                return "fail";

            }

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(Temp_Renter.this,s,Toast.LENGTH_SHORT).show();
            dialog.dismiss();
            if(s.equals("rent place registered successfully"))
            {
                Toast.makeText(Temp_Renter.this,"Rent Place Registered",Toast.LENGTH_SHORT).show();
                Intent i = new Intent(Temp_Renter.this, CustomMain2.class); //This creates Exception because of no passing data to Bundle
                Bundle bundle = getIntent().getExtras();
                String u_id = bundle.getString("u_id");
                i.putExtra("u_id",u_id);
                //     Intent i = new Intent(Renter.this, ChoiceActivity.class);
                startActivity(i);
            }

            else if( s.equals("error") )
            {
                Toast.makeText(Temp_Renter.this,"Rent Place Not Registered",Toast.LENGTH_SHORT).show();
            }


        }
    }




}
