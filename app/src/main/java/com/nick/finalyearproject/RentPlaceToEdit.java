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
import android.widget.ToggleButton;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import static com.nick.finalyearproject.R.id.fourwheeler;
import static com.nick.finalyearproject.R.id.twowheeler;

/**
 * Created by CompuCareInfotech on 3/28/2017.
 */

public class RentPlaceToEdit extends Activity  {
    public static String NAMESPACE="http://tempuri.org/";
    //public static String URL = "http://192.168.1.8/WebSite1/Service.asmx";
    public static String URL =WebServiceClass.URL;

    public static String METHOD_NAME="deleteorUpdateRentPlace";
    public static String SOAP_ACTION="http://tempuri.org/deleteorUpdateRentPlace";


    EditText longitude;
    EditText latitude;
    EditText location;
    EditText two;
    EditText four;

    EditText from;
    EditText to;
    EditText charge;

    CheckBox all;
    CheckBox mon;
    CheckBox tue;
    CheckBox wed;
    CheckBox thu;
    CheckBox fri;
    CheckBox sat;
    CheckBox sun;

    ToggleButton edit_or_update_button;
    Button delete_button;
    Button edit_location;

    static  String u_id;//
    static  String r_id;
    static  String email;

    static  String long2;
    static  String lat2;
    static  String loc2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rentplace_to_edit);


        longitude=(EditText)findViewById(R.id.longitude_text);
         latitude=(EditText)findViewById(R.id.latitude_text);
         location=(EditText)findViewById(R.id.location_text);
         two=(EditText)findViewById(twowheeler);
         four=(EditText)findViewById(fourwheeler);

         from=(EditText)findViewById(R.id.from);
         to=(EditText)findViewById(R.id.to);
         charge=(EditText)findViewById(R.id.chargeEditText);

         all=(CheckBox)findViewById(R.id.all);
         mon=(CheckBox)findViewById(R.id.mon);
         tue=(CheckBox)findViewById(R.id.tue);
         wed=(CheckBox)findViewById(R.id.wed);
         thu=(CheckBox)findViewById(R.id.thu);
         fri=(CheckBox)findViewById(R.id.fri);
         sat=(CheckBox)findViewById(R.id.sat);
         sun=(CheckBox)findViewById(R.id.sun);

        edit_or_update_button=(ToggleButton) findViewById(R.id.edit_or_update);
         delete_button=(Button)findViewById(R.id.deletebutton);
         edit_location=(Button)findViewById(R.id.edit_location);



        Bundle bundle = getIntent().getExtras();

        RentPlaceToEdit.lat2=bundle.getString("lati");
        RentPlaceToEdit.long2=bundle.getString("longi");
        RentPlaceToEdit.loc2=bundle.getString("area");
        String lati=bundle.getString("lati");
        String longi=bundle.getString("longi");
        final String two_wheeler=bundle.getString("two_wheeler");
        String four_wheeler=bundle.getString("four_wheeler");
        String area=bundle.getString("area");
        final String week_days=bundle.getString("week_days");
        String start_time=bundle.getString("start_time");
        String end_time=bundle.getString("end_time");
        String price=bundle.getString("price");

        Toast.makeText(RentPlaceToEdit.this,"Received: "+lati+" "+longi+" "+two_wheeler+" "+four_wheeler+" "+area+" "+week_days+" "+start_time+" "+end_time+" "+price,Toast.LENGTH_SHORT).show();
    //    String r_id=bundle.getString("r_id");
     //   String u_id=bundle.getString("u_id");
        longitude.setText(longi);
        latitude.setText(lati);
        location.setText(area);
        charge.setText(price);
        two.setText(two_wheeler);
        four.setText(four_wheeler);
        from.setText(start_time);
        to.setText(end_time);






        if(week_days.contains("Mo"))
        {
        //    mon.isChecked();
            mon.setChecked(true);
        }
        if(week_days.contains("Tu"))
        {
        //    tue.isChecked();
            tue.setChecked(true);
        }
        if(week_days.contains("We"))
        {
            //wed.isChecked();
            wed.setChecked(true);
        }
        if(week_days.contains("Th"))
        {
         //   thu.isChecked();
            thu.setChecked(true);
        }
        if(week_days.contains("Fr"))
        {
            //fri.isChecked();
            fri.setChecked(true);
        }
        if(week_days.contains("Sa"))
        {
     //       sat.isChecked();
            sat.setChecked(true);
        }
        if(week_days.contains("Su"))
        {
          //  sun.isChecked();
            sun.setChecked(true);
        }

        longitude.setEnabled(false);
        latitude.setEnabled(false);
        location.setEnabled(false);
        two.setEnabled(false);
        four.setEnabled(false);

        from.setEnabled(false);
        to.setEnabled(false);
        charge.setEnabled(false);

        all.setEnabled(false);
        mon.setEnabled(false);
         tue.setEnabled(false);
         wed.setEnabled(false);
         thu.setEnabled(false);
         fri.setEnabled(false);
         sat.setEnabled(false);
         sun.setEnabled(false);

        edit_location.setEnabled(false);

        edit_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(RentPlaceToEdit.this,"Map Button Clicked",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(RentPlaceToEdit.this,Renter_Location.class);
                startActivity(intent);
            }
        });

    delete_button.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(RentPlaceToEdit.this,"Delete Button Clicked",Toast.LENGTH_SHORT).show();
            //String r_id, String longi, String lati, String two_wheel, String four_wheel, String l_name, String week_days, String start_time, String end_time, String charge_per_hour, String opcode
            new Callwebservice1(r_id,"dummy","dummy","0","0","dummy","dummy","dummy","dummy","0","0").execute();
        }
    });

        edit_or_update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String week_days1=new String();
                Toast.makeText(RentPlaceToEdit.this,edit_or_update_button.getText().toString()+" Button Clicked",Toast.LENGTH_SHORT).show();
                if(edit_or_update_button.getText().toString().equals("Update"))
                {
                    Toast.makeText(RentPlaceToEdit.this,"Update Code Runs!!",Toast.LENGTH_SHORT).show();
                    longitude.setEnabled(true);
                    latitude.setEnabled(true);
                    location.setEnabled(true);
                    two.setEnabled(true);
                    four.setEnabled(true);

                    from.setEnabled(true);
                    to.setEnabled(true);
                    charge.setEnabled(true);

                    all.setEnabled(true);
                    mon.setEnabled(true);
                    tue.setEnabled(true);
                    wed.setEnabled(true);
                    thu.setEnabled(true);
                    fri.setEnabled(true);
                    sat.setEnabled(true);
                    sun.setEnabled(true);

                    edit_location.setEnabled(true);
                }
                if(edit_or_update_button.getText().toString().equals("Edit"))
                {

                    if(all.isChecked()) {
                        week_days1="MoTuWeThFrSaSu";
                    }
                    else {


                        if(mon.isChecked()) {
                            week_days1+="Mo";
                        }
                        if(tue.isChecked()) {
                            week_days1+="Tu";
                        }
                        if(wed.isChecked()) {
                            week_days1+="We";
                        }
                        if(thu.isChecked()) {
                            week_days1+="Th";
                        }
                        if(fri.isChecked()) {
                            week_days1+="Fr";
                        }
                        if(sat.isChecked()) {
                            week_days1+="Sa";
                        }
                        if(sun.isChecked()) {
                            week_days1+="Su";
                        }

                    }
                    Toast.makeText(RentPlaceToEdit.this,"Edit Code Runs!!",Toast.LENGTH_SHORT).show();
                    longitude.setEnabled(false);
                    latitude.setEnabled(false);
                    location.setEnabled(false);
                    two.setEnabled(false);
                    four.setEnabled(false);

                    from.setEnabled(false);
                    to.setEnabled(false);
                    charge.setEnabled(false);

                    all.setEnabled(false);
                    mon.setEnabled(false);
                    tue.setEnabled(false);
                    wed.setEnabled(false);
                    thu.setEnabled(false);
                    fri.setEnabled(false);
                    sat.setEnabled(false);
                    sun.setEnabled(false);

                    edit_location.setEnabled(false);
                    new Callwebservice1(r_id,longitude.getText().toString(), latitude.getText().toString() ,two.getText().toString(), four.getText().toString(),location.getText().toString(),week_days1,from.getText().toString(),to.getText().toString(),charge.getText().toString(),"1").execute();
                    //Yha Se Web Service Call Karni Hai

                }

            }
        });


    }
    protected void onResume() {
        super.onResume();
        latitude.setText(lat2);
        longitude.setText(long2);
        location.setText(loc2);
        //     latitude.setEnabled(false);
        //    longitude.setEnabled(false);
        //   location.setEnabled(false);
    }
    private class Callwebservice1  extends AsyncTask<Void,Void,String>
    {
        ProgressDialog dialog;

        String r_id,  longi,  lati,  two_wheel,  four_wheel,  l_name,  week_days,  start_time,  end_time,  charge_per_hour,  opcode;

        public Callwebservice1( String r_id, String longi, String lati, String two_wheel, String four_wheel, String l_name, String week_days, String start_time, String end_time, String charge_per_hour, String opcode)
        {
            this.r_id= r_id;
            this.longi=longi;
            this.lati=lati;
            this.two_wheel=two_wheel;
            this.four_wheel=four_wheel;
            this.l_name=l_name;
            this.week_days=week_days;
            this.start_time=start_time;
            this.end_time=end_time;
            this.charge_per_hour=charge_per_hour;
            this.opcode=opcode;


        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(RentPlaceToEdit.this);
            dialog.setMessage("Loading");
            dialog.setTitle("Please wait");
            dialog.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            try
            {



                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

//String r_id, String longi, String lati, String two_wheel, String two_wheel, String l_name, String week_days,
// String start_time, String end_time, String charge_per_hour, String opcode
                request.addProperty("r_id", r_id);
                request.addProperty("longi", longi);
                request.addProperty("lati", lati);
                request.addProperty("two_wheel", two_wheel);
                request.addProperty("two_wheel", two_wheel);
                request.addProperty("l_name", l_name);
                request.addProperty("week_days", week_days);
                request.addProperty("start_time", start_time);
                request.addProperty("end_time", end_time);
                request.addProperty("charge_per_hour", charge_per_hour);
                request.addProperty("opcode", opcode);









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
            Toast.makeText(RentPlaceToEdit.this,result,Toast.LENGTH_SHORT).show();

        dialog.dismiss();
            if(result.equals("Deleted Succesfully")) {
                Toast.makeText(RentPlaceToEdit.this,"Deleted Place:"+r_id,Toast.LENGTH_SHORT).show();
                Intent i = new Intent(RentPlaceToEdit.this, CustomMain2.class); //This creates Exception because of no passing data to Bundle

                i.putExtra("u_id",u_id);
                i.putExtra("email",email);
                //     Intent i = new Intent(Renter.this, ChoiceActivity.class);
                startActivity(i);
            }
            else if(result.equals("Updated Succesfully"))
            {
                Toast.makeText(RentPlaceToEdit.this,"Updated Succesfully",Toast.LENGTH_SHORT).show();
                Intent i = new Intent(RentPlaceToEdit.this, CustomMain2.class); //This creates Exception because of no passing data to Bundle

                i.putExtra("u_id",u_id);
                i.putExtra("email",email);
                //     Intent i = new Intent(Renter.this, ChoiceActivity.class);
                startActivity(i);
            }
            else if(result.equals("error in delete"))
            {
                Toast.makeText(RentPlaceToEdit.this,"Delete Error",Toast.LENGTH_SHORT).show();
            }
            else if(result.equals("error in update"))
            {
                Toast.makeText(RentPlaceToEdit.this,"Update Error",Toast.LENGTH_SHORT).show();
            }
        }
    }



}
