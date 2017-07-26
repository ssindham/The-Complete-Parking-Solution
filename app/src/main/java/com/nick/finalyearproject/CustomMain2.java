package com.nick.finalyearproject;

/**
 * Created by CompuCareInfotech on 3/21/2017.
 */

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CompuCareInfotech on 2/23/2017.
 */

public class CustomMain2 extends Activity {

    public static String NAMESPACE="http://tempuri.org/";
    //public static String URL = "http://192.168.1.8/WebSite1/Service.asmx";
    public static String URL =WebServiceClass.URL;

    public static String METHOD_NAME="ReturnAllRentPlacesOfAUser";
    public static String SOAP_ACTION="http://tempuri.org/ReturnAllRentPlacesOfAUser";
    private ListView lvList;
    private CustomListAdapter2 adapter;
    private List<CustomMenu2> mCustomMenu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_main2);
        Bundle bundle = getIntent().getExtras();
        final String u_id = bundle.getString("u_id");
        final String email = bundle.getString("email");

        Toast.makeText(getApplicationContext(), "Logged In User:"+ u_id, Toast.LENGTH_SHORT).show();
        lvList=(ListView)findViewById(R.id.listview);
        mCustomMenu=new ArrayList<>();

        new Callwebservice1(u_id).execute();

        lvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getApplicationContext(), "Clicked Area Id="+view.getTag(), Toast.LENGTH_LONG).show();
                Object o=lvList.getItemAtPosition(position);
                CustomMenu2 cus=(CustomMenu2)o;
                Intent i=new Intent(CustomMain2.this,RentPlaceToEdit.class);


                i.putExtra("lati",Double.toString(cus.getLati()));
                i.putExtra("longi",Double.toString(cus.getLongi()));
                i.putExtra("two_wheeler",Integer.toString(cus.getTwo_wheel()));
                i.putExtra("four_wheeler",Integer.toString(cus.getFour_wheel()));
                i.putExtra("area",cus.getArea());
                i.putExtra("week_days",cus.getWeek_days());
                i.putExtra("start_time",cus.getStart_time());
                i.putExtra("end_time",cus.getEnd_time());
                i.putExtra("price",Integer.toString(cus.getCharge()));
              //  i.putExtra("r_id",Integer.toString(cus.getR_id()));
                RentPlaceToEdit.u_id=u_id;
                RentPlaceToEdit.email=email;
                RentPlaceToEdit.r_id=Integer.toString(cus.getR_id());

                Toast.makeText(CustomMain2.this, "Sending:"+cus.getLati()+" "+cus.getLongi()+" "+cus.getTwo_wheel()+" "+cus.getFour_wheel()+" "+cus.getArea()+" "+cus.getWeek_days()+" "+cus.getStart_time()+" "+cus.getEnd_time()+" "+cus.getCharge()+" "+cus.getR_id(), Toast.LENGTH_SHORT).show();

                Toast.makeText(getApplicationContext(), "Clicked Area Id="+view.getTag(), Toast.LENGTH_LONG).show();
                startActivity(i);



            }
        });
    }
    private class Callwebservice1  extends AsyncTask<Void,Void,String>
    {
        ProgressDialog dialog;

        String u_id;

        public Callwebservice1( String u_id) {
            this.u_id = u_id;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(CustomMain2.this);
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
            //Toast.makeText(CustomMain2.this, totalString, Toast.LENGTH_SHORT).show();
            //totalString="98.23445~87.2344~1~2~Kandivali~ThFr~9pm~1pm~98#";
            //Toast.makeText(CustomMain2.this, totalString, Toast.LENGTH_SHORT).show();
            if(totalString.equals("No Place Registered"))
            {
                Toast.makeText(CustomMain2.this,"You haven't registered any rent place .. Please Try Again",Toast.LENGTH_SHORT).show();
                Bundle bundle = getIntent().getExtras();
                Intent i = new Intent(CustomMain2.this, RentPlaceErrorPage.class);
                String u_id = bundle.getString("u_id");
                String email = bundle.getString("email");
                i.putExtra("u_id",u_id);
                i.putExtra("email",email);
            //    Intent i=new Intent(CustomMain2.this,Renter.class);

                   startActivity(i);
            }
            else {

                //  String totalString="Nk,4 am,34#Jk,5 am,98#Jakatnaka,7pm,8#";
                String[] LocationArray = totalString.split("#");
                for (int i = 0; i < LocationArray.length; i++) {
                    String each_loc[] = LocationArray[i].split("~");
                    //     mCustomMenu.add(new CustomMenu(i + 1, each_loc[0], Integer.parseInt(each_loc[1]), Integer.parseInt(each_loc[2])));
                                                    //totalString="98.23445~87.2344~1~2~Kandivali~ThFr~9pm~1pm~98#";
                    mCustomMenu.add(new CustomMenu2(i + 1, Double.parseDouble(each_loc[0]), Double.parseDouble(each_loc[1]), Integer.parseInt(each_loc[2]),Integer.parseInt(each_loc[3]),each_loc[4],each_loc[5],each_loc[6],each_loc[7],Integer.parseInt(each_loc[8]),Integer.parseInt(each_loc[9])));
        //            Toast.makeText(CustomMain2.this, "Adding:"+(i+1)+" "+each_loc[0]+" "+each_loc[1]+" "+each_loc[2]+" "+each_loc[3]+" "+each_loc[4]+" "+each_loc[5]+" "+each_loc[6]+" "+each_loc[7]+" "+each_loc[8]+" "+each_loc[9], Toast.LENGTH_SHORT).show();

                    //    each_loc[1]=String.valueOf(distance(latitude,longitude,Double.parseDouble(each_loc[5]),Double.parseDouble(each_loc[6])));


                }





                adapter = new CustomListAdapter2(getApplicationContext(), mCustomMenu);

                lvList.setAdapter(adapter);

            }

        }
    }
}

