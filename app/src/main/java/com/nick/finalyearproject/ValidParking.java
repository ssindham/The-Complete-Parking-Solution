package com.nick.finalyearproject;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;


public class ValidParking extends FragmentActivity implements LocationListener {

	Marker currentmarker;
	GoogleMap mGoogleMap;
	ArrayList<LatLng> mMarkerPoints;
	ArrayList<LatLng> parkingpoints = new ArrayList<LatLng>();
	double mLatitude = 0;
	double mLongitude = 0;

	public static String NAMESPACE="http://tempuri.org/";
	//public static String URL = "http://192.168.1.8/WebSite1/Service.asmx";
	public static String URL =WebServiceClass.URL;

	public static String METHOD_NAME="getgovtparkingplaces";
	public static String SOAP_ACTION="http://tempuri.org/getgovtparkingplaces";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.validparking_main);

		new Getdatafromserver().execute();


	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private void drawMarker(LatLng point) {
		mMarkerPoints.add(point);


		MarkerOptions options = new MarkerOptions();


		options.position(point);

		options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
		/*
		if (mMarkerPoints.size() == 1) {
			options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
		} else if (mMarkerPoints.size() == 2) {
			options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
		} else {
			options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
		}
*/
		// Add new marker to the Google Map Android API V2
		mGoogleMap.addMarker(options);
	}

	private void drawMarker1(LatLng point) {
		mMarkerPoints.add(point);

		MarkerOptions options = new MarkerOptions();

		options.position(point);

		options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));

		if (currentmarker != null) {
			currentmarker.remove();
		}

		currentmarker = mGoogleMap.addMarker(options);
	}

	@Override
	public void onLocationChanged(Location location) {

		mLatitude = location.getLatitude();
		mLongitude = location.getLongitude();
		LatLng point = new LatLng(mLatitude, mLongitude);


		Toast.makeText(ValidParking.this, String.valueOf(point.latitude)+","+String.valueOf(point.longitude), Toast.LENGTH_SHORT).show();
		mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(point));
		mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(12));

		drawMarker1(point);

		boolean isinregion = false;
		for (int i = 0; i < parkingpoints.size(); i++) {
			if (distanceBetween(parkingpoints.get(i), point) < 5) {
				isinregion = true;
			}
		}
		if(isinregion==false)
		{
		Toast.makeText(ValidParking.this, "Invalid Parking Spot", Toast.LENGTH_SHORT).show();
		}
		else {
			Toast.makeText(ValidParking.this, "Valid Parking Spot", Toast.LENGTH_SHORT).show();
		}
	}

	private float distanceBetween(LatLng latLng1, LatLng latLng2) {
		Location loc1 = new Location(LocationManager.GPS_PROVIDER);
		Location loc2 = new Location(LocationManager.GPS_PROVIDER);
		loc1.setLatitude(latLng1.latitude);
		loc1.setLongitude(latLng1.longitude);

		loc2.setLatitude(latLng2.latitude);
		loc2.setLongitude(latLng2.longitude);
		return loc1.distanceTo(loc2);
	}


	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
	}


	private class Getdatafromserver extends AsyncTask<Void, Void, String> {

		ProgressDialog dialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog = new ProgressDialog(ValidParking.this);
			dialog.show();
		}

		@Override
		protected String doInBackground(Void... voids) {
//soapwebservice
            try
            {
                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

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

            String[] LocationArray = s.split("#");
            for (int i = 0; i < LocationArray.length; i++) {
                String each_loc[] = LocationArray[i].split("~");
                parkingpoints.add(new LatLng(Double.parseDouble(each_loc[0]),Double.parseDouble(each_loc[1])));
            }


            int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getBaseContext());

			if (status != ConnectionResult.SUCCESS) { // Google Play Services are not available

				int requestCode = 10;
				Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, ValidParking.this, requestCode);
				dialog.show();

			} else {

				mMarkerPoints = new ArrayList<LatLng>();

				// Getting reference to SupportMapFragment of the activity_main
				SupportMapFragment fm = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

				// Getting Map for the SupportMapFragment
				fm.getMapAsync(new OnMapReadyCallback() {
					@Override
					public void onMapReady(GoogleMap googleMap) {
						mGoogleMap = googleMap;

						// Enable MyLocation Button in the Map
						mGoogleMap.setMyLocationEnabled(true);


						// Getting LocationManager object from System Service LOCATION_SERVICE
						LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

						// Creating a criteria object to retrieve provider
						Criteria criteria = new Criteria();

						// Getting the name of the best provider
						String provider = locationManager.getBestProvider(criteria, true);

						// Getting Current Location From GPS
						if (ActivityCompat.checkSelfPermission(ValidParking.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(ValidParking.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
							onLocationChanged(location);

						}

						locationManager.requestLocationUpdates(provider, 3000, 0, ValidParking.this);

						mMarkerPoints.clear();
						mGoogleMap.clear();

						for(int i =0;i<parkingpoints.size();i++)
						{
							drawMarker(parkingpoints.get(i));
						}
						mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(parkingpoints.get(parkingpoints.size()-1)));
						mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(18));
						mGoogleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);

					}
				});


			}
		}
	}
}