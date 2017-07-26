package com.nick.finalyearproject;

import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by CompuCareInfotech on 3/4/2017.
 */

public class Renter_Location extends FragmentActivity {
  /*  private  static String lat1;
    private  static String long1;
    public static String getLong1()
    {
        return long1;
    }

    public static String getLat1()
    {
        return lat1;
    }

    public static void setLong1(String long1)
    {
        Renter_Location.long1=long1;
    }
    public static void setLat1(String lat1)
    {
        Renter_Location.lat1=lat1;
    }*/


    Marker currentmarker;
    GoogleMap mGoogleMap;
    ArrayList<LatLng> mMarkerPoints;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.renter_location);
        mMarkerPoints = new ArrayList<LatLng>();

        // Getting reference to SupportMapFragment of the renter_location
        SupportMapFragment fm = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map1);


        // Getting Map for the SupportMapFragment
        fm.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mGoogleMap = googleMap;
                mGoogleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(LatLng latLng) {
                        if(mGoogleMap != null) {
                            Toast.makeText(Renter_Location.this, latLng.longitude + "-" + latLng.latitude, Toast.LENGTH_SHORT).show();

                            Renter.lat1=Double.toString(latLng.latitude);
                            Renter.long1=Double.toString(latLng.longitude);
                            Renter.l_name1=getCompleteAddressString(latLng.latitude,latLng.longitude);

                            RentPlaceToEdit.lat2=Double.toString(latLng.latitude);
                            RentPlaceToEdit.long2=Double.toString(latLng.longitude);
                            RentPlaceToEdit.loc2=getCompleteAddressString(latLng.latitude,latLng.longitude);

                            Toast.makeText(Renter_Location.this,Renter.l_name1,Toast.LENGTH_SHORT).show();
                            finish();

                        /*    Intent in=new Intent(Renter_Location.this,Temp_Renter.class);
                            //in.putExtra("lat", latLng.latitude);
                            //in.putExtra("long", latLng.longitude);
                      //      long1=latLng.longitude;
                        //    lat1=latLng.latitude;
                            startActivity(in);*/


                        }
                    }
                });


                // Enable MyLocation Button in the Map
                mGoogleMap.setMyLocationEnabled(true);
           /*     if (ContextCompat.checkSelfPermission(Renter_Location.this, android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED &&
                        ContextCompat.checkSelfPermission(Renter_Location.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) ==
                                PackageManager.PERMISSION_GRANTED) {
                    googleMap.setMyLocationEnabled(true);
                    googleMap.getUiSettings().setMyLocationButtonEnabled(true);
                } else {
                    Toast.makeText(Renter_Location.this, "Error In mAp", Toast.LENGTH_LONG).show();
                }*/


                // Getting LocationManager object from System Service LOCATION_SERVICE
                LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

                // Creating a criteria object to retrieve provider
                Criteria criteria = new Criteria();

                // Getting the name of the best provider
                String provider = locationManager.getBestProvider(criteria, true);

                // Getting Current Location From GPS
                if (ActivityCompat.checkSelfPermission(Renter_Location.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(Renter_Location.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                Location location = locationManager.getLastKnownLocation(provider);

                if (location != null) {
                    // onLocationChanged(location);

                }

                // locationManager.requestLocationUpdates(provider, 3000, 0, Renter_Location.this);

                mMarkerPoints.clear();
                mGoogleMap.clear();

      /*          for(int i =0;i<parkingpoints.size();i++)
                {
                    drawMarker(parkingpoints.get(i));
                }
                mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(parkingpoints.get(parkingpoints.size()-1)));
                mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(18));*/
                mGoogleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);

            }
        });


    }


    public String getCompleteAddressString(double LATITUDE, double LONGITUDE) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            //  List<Address> addresses =geocoder.getFromLocation(LATITUDE,LONGITUDE,1);
            List<Address> addresses=geocoder.getFromLocation(LATITUDE,LONGITUDE,1);

            if (addresses != null) {
                android.location.Address returnedAddress=addresses.get(0);
                //Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");

                for (int i = 0; i < returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                }
                strAdd = strReturnedAddress.toString();
                Log.w("Current loction", "" + strReturnedAddress.toString());
            } else {
                Log.w("Current loction", "No Address returned!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.w("Current loction", "Canont get Address!");
        }
        //Toast.makeText(Renter_Location.this,strAdd,Toast.LENGTH_SHORT).show();
        return strAdd;
    }



}
