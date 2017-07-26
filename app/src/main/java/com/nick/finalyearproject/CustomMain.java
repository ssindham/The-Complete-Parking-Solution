//CustomMain.java
package com.nick.finalyearproject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

//import com.google.android.gms.location.LocationListener;

/**
 * Created by CompuCareInfotech on 2/23/2017.
 */

public class CustomMain extends Activity implements LocationListener {
    protected double latitude,longitude;
    @Override
    public void onLocationChanged(Location location) {
        latitude=location.getLatitude();
        longitude=location.getLatitude();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    public static String NAMESPACE="http://tempuri.org/";
    //public static String URL = "http://192.168.1.8/WebSite1/Service.asmx";
    public static String URL =WebServiceClass.URL;

    public static String METHOD_NAME="ReturnRentPlaces";
    public static String SOAP_ACTION="http://tempuri.org/ReturnRentPlaces";

    private ListView lvList;
    private CustomListAdapter adapter;
    private List<CustomMenu> mCustomMenu;


    protected LocationManager locationManager;
    protected LocationListener locationListener;
    protected Context context;

    //String lat;
    String lat;
    String provider;


    //static String lati;
    //static String longi;
    static String u_id;
    static String r_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_main);

        Bundle bundle = getIntent().getExtras();
        String two = bundle.getString("twowheeler");
        String four = bundle.getString("fourwheeler");

        lvList=(ListView)findViewById(R.id.listview);

        locationManager=(LocationManager)getSystemService(Context.LOCATION_SERVICE);
      //  locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,this);
        try {
       //     location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,this);
        } catch (SecurityException e) {

            //dialogGPS(this.getContext()); // lets the user know there is a problem with the gps
        }


        mCustomMenu=new ArrayList<>();
        String today_name = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(System.currentTimeMillis());

        new Callwebservice1(two, four,today_name).execute();


        //mCustomMenu.add(new CustomMenu(1,"Katargam",25,30));
  /*      mCustomMenu.add(new CustomMenu(2,"Ghoddod Road",20,35));
        mCustomMenu.add(new CustomMenu(3,"Citylight Road",10,23));
        mCustomMenu.add(new CustomMenu(4,"Dabholi",50,20));
        mCustomMenu.add(new CustomMenu(5,"Adajan",40,12));
        mCustomMenu.add(new CustomMenu(6,"Vesu",10,5));*/

    //    adapter=new CustomListAdapter(getApplicationContext(),mCustomMenu);
     //   lvList.setAdapter(adapter);

        lvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getApplicationContext(), "Clicked Area Id="+view.getTag(), Toast.LENGTH_SHORT).show();
        Object o=lvList.getItemAtPosition(position);
                CustomMenu cus=(CustomMenu)o;
       //         Toast.makeText(CustomMain.this,cus.getUid()+" "+ cus.getRid()+" "+ cus.getArea()+" "+ cus.getCharge()+" "+ cus.getDist(),Toast.LENGTH_SHORT).show();

                //CustomMain.u_id=(EditText)view.findViewById(R.id.tenant_name)
                Intent i=new Intent(CustomMain.this,TenantInformation.class);
             /*   i.putExtra("tenanat_name","Ramesh Bhai");
                i.putExtra("contact_no","9898185671");
                i.putExtra("l_name","Dhaboli");
                i.putExtra("charge_per_hour","21");
                i.putExtra("distance","45");
                i.putExtra("longi",longi);
                i.putExtra("lati",lati);*/
                //return
                i.putExtra("dist",String.format("%.2f",cus.getDist())+" km");
                i.putExtra("u_id1",cus.getUid());
                i.putExtra("r_id",cus.getRid());
                i.putExtra("lati",Double.toString(cus.getLati()));
                i.putExtra("longi",Double.toString(cus.getLongi()));
                i.putExtra("place",cus.getArea());
                i.putExtra("price",cus.getCharge());

                Bundle bundle = getIntent().getExtras();
                String email = bundle.getString("email");
                String u_id = bundle.getString("u_id");
                i.putExtra("email",email);
                i.putExtra("u_id",u_id);


                startActivity(i);

            }
        });



    }

    private double distance(double lat1, double lon1, double lat2, double lon2) {
        int Radius = 6371;// radius of earth in K
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1))
                * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2)
                * Math.sin(dLon / 2);
        double c = 2 * Math.asin(Math.sqrt(a));
        double valueResult = Radius * c;
        double km = valueResult / 1;
        DecimalFormat newFormat = new DecimalFormat("####");
        int kmInDec = Integer.valueOf(newFormat.format(km));
        double meter = valueResult % 1000;
        int meterInDec = Integer.valueOf(newFormat.format(meter));
        Log.i("Radius Value", "" + valueResult + "   KM  " + kmInDec
                + " Meter   " + meterInDec);
        return (Radius * c)/1000;
     //   return String.format("%.2f",(Radius * c)/1000);
    }


    private class Callwebservice1  extends AsyncTask<Void,Void,String>
    {
        ProgressDialog dialog;

        String two,four,today_name;

        public Callwebservice1( String two,String four,String today_name)
        {
            this.two = two;
            this.four = four;
            this.today_name=today_name;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(CustomMain.this);
            dialog.setMessage("Loading");
            dialog.setTitle("Please wait");
            dialog.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            try
            {



                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);


                request.addProperty("two_wheel", two);
                request.addProperty("four_wheel", four);
                request.addProperty("today_name", today_name);




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
        protected void onPostExecute(String totalString) {
            super.onPostExecute(totalString);
            dialog.dismiss();
          if(totalString.equals("no place found"))
          {
              Toast.makeText(CustomMain.this,"No Place Found Matching Your Requirements !! Please try later !!",Toast.LENGTH_SHORT).show();
              Intent i=new Intent(CustomMain.this,Rentee.class);
              startActivity(i);
          }
            else {


              Toast.makeText(CustomMain.this,"Today is "+today_name,Toast.LENGTH_SHORT).show();
              //Toast.makeText(CustomMain.this, totalString, Toast.LENGTH_SHORT).show();

              String[] LocationArray = totalString.split("#");
              for (int i = 0; i < LocationArray.length; i++) {
                  String each_loc[] = LocationArray[i].split("~");
                  //System.out.println(LocationArray[i]);
                  //   System.out.println(each_loc[0]); //l_name
                  //   System.out.println(each_loc[1]);  //dist
                  //   System.out.println(each_loc[2]);  //charge_per_hour
                  CustomMain.u_id=each_loc[3];
                  CustomMain.r_id=each_loc[4];
        //  String totalString="Jakatnaka~7pm~8~54~4~897.98~89.86~MoTuWeThFrSaSu#";CustomMain
             //     mCustomMenu.add(new CustomMenu(i + 1, each_loc[0], Integer.parseInt(each_loc[1]), Integer.parseInt(each_loc[2])));
                  double temp_dist=distance( latitude,longitude, Double.parseDouble(each_loc[5]) , Double.parseDouble(each_loc[6]) );
                  String temp_dist_string =String.format("%.2f",temp_dist);

                      mCustomMenu.add(new CustomMenu(i + 1, each_loc[0], Integer.parseInt(each_loc[2]), Double.parseDouble(temp_dist_string), each_loc[3], each_loc[4], Double.parseDouble(each_loc[5]), Double.parseDouble(each_loc[6])));
                //  mCustomMenu.add(new CustomMenu(1,"Katargam",25,30,"hkh","dgs",43.34,343.34));
     //             Toast.makeText(CustomMain.this, "Adding:"+(i+1)+" "+each_loc[0]+" "+each_loc[2]+" "+temp_dist_string+" "+each_loc[3]+" "+each_loc[4]+" "+each_loc[5]+" "+each_loc[6], Toast.LENGTH_SHORT).show();

              //    each_loc[1]=String.valueOf(distance(latitude,longitude,Double.parseDouble(each_loc[5]),Double.parseDouble(each_loc[6])));

              }

             Collections.sort(mCustomMenu);


              adapter = new CustomListAdapter(getApplicationContext(), mCustomMenu);
              lvList.setAdapter(adapter);

          }

        }
    }
}
