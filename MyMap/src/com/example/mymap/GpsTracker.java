package com.example.mymap;

import java.util.ArrayList;

import android.app.Activity;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;



public class GpsTracker extends  Activity implements ConnectionCallbacks, OnConnectionFailedListener, LocationListener, com.google.android.gms.location.LocationListener {

	// LogCat tag
	private static final String TAG = GpsTracker.class.getSimpleName();

	private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;

	private Location mLastLocation;

	// Google client to interact with Google API
	private GoogleApiClient mGoogleApiClient;

	// boolean flag to toggle periodic location updates
	private boolean mRequestingLocationUpdates = false;

	private LocationRequest mLocationRequest;

	// Location updates intervals in sec
	private static int UPDATE_INTERVAL = 1000; // 1 sec
	private static int FATEST_INTERVAL = 1000; // 1 sec
	private static int DISPLACEMENT = 1; // 1 meters

	// UI elements
	private TextView tvLLocation, tvData;
	private Button btnStartLocationUpdates, btnDelete; // btnShowLocation,

	//
	private int no = 0;
	private String x, y, i = "", j = "";

	DatabaseHelper dbHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gps);

		tvLLocation = (TextView) findViewById(R.id.lblLocation);

		tvData = (TextView) findViewById(R.id.textViewData);
		// btnShowLocation = (Button) findViewById(R.id.btnShowLocation);
		btnDelete = (Button) findViewById(R.id.buttonDelete);
		btnStartLocationUpdates = (Button) findViewById(R.id.btnLocationUpdates);

		dbHelper = new DatabaseHelper(this);

		// First we need to check availability of play services
		if (checkPlayServices()) {

			// Building the GoogleApi client
			buildGoogleApiClient();

			createLocationRequest();
		}

		displayLocation();

		// Toggling the periodic location updates
		btnStartLocationUpdates.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				togglePeriodicLocationUpdates();
			}
		});

		btnDelete.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				dbHelper.delelteAll();
				no = 0;
				tvData.setText("NO: latitude  ,   longitude");
			}
		});

	}

	// Method to display the location on UI

	private void displayLocation() {

		mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

		if (mLastLocation != null) {
			double latitude = mLastLocation.getLatitude();
			double longitude = mLastLocation.getLongitude();

			x = "";
			x += latitude;
			y = "";
			y += longitude;

			if (!i.equals(x) || !j.equals(y)) {
				no++;
				tvLLocation.setText(latitude + ", " + longitude);

				// ////////////////////////////////////////////////////////

				GPS gps = new GPS();
				gps.setNo(no);
				gps.setLatitude(x);
				gps.setLongitude(y);

				long inserted = dbHelper.insertGPS(gps);
				if (inserted >= 0) {
					Toast.makeText(getApplicationContext(), "Data inserted", Toast.LENGTH_LONG).show();
				} else {
					Toast.makeText(getApplicationContext(), "Data insertion failed...", Toast.LENGTH_LONG).show();
				}
				// //////////////////////////////////////////////////////

				// tvData.setText(tvData.getText()+"\n"+no+": "+latitude + ", " + longitude);
				ArrayList<GPS> allGPS = dbHelper.getAllGPS();
				if (allGPS != null && allGPS.size() > 0) {
					
					tvData.setText("NO: latitude  ,   longitude");
					
					for (GPS ob : allGPS) {
						// Toast.makeText(getApplicationContext(), employee.toString(), Toast.LENGTH_SHORT).show();
						tvData.setText(tvData.getText() + "\n" + ob.toString());
					}
				} else if (allGPS.size() == 0) {
					Toast.makeText(getApplicationContext(), "No data found", Toast.LENGTH_SHORT).show();
				}
			}
			i = x;
			j = y;

		} else {

			tvLLocation.setText("(Couldn't get the location. Make sure location is enabled on the device)");
		}
	}

	// Method to toggle periodic location updates

	private void togglePeriodicLocationUpdates() {
		if (!mRequestingLocationUpdates) {
			// Changing the button text
			btnStartLocationUpdates.setText("start");

			mRequestingLocationUpdates = true;

			// Starting the location updates
			startLocationUpdates();

			Log.d(TAG, "Periodic location updates started!");

		} else {
			// Changing the button text
			btnStartLocationUpdates.setText("stop");

			mRequestingLocationUpdates = false;

			// Stopping the location updates
			stopLocationUpdates();

			Log.d(TAG, "Periodic location updates stopped!");
		}
	}

	// Creating google api client object
	protected synchronized void buildGoogleApiClient() {
		mGoogleApiClient = new GoogleApiClient.Builder(this).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(LocationServices.API).build();
	}

	// Creating location request object
	protected void createLocationRequest() {
		mLocationRequest = new LocationRequest();
		mLocationRequest.setInterval(UPDATE_INTERVAL);
		mLocationRequest.setFastestInterval(FATEST_INTERVAL);
		mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
		mLocationRequest.setSmallestDisplacement(DISPLACEMENT);
	}

	// Method to verify google play services on the device
	private boolean checkPlayServices() {
		int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
		if (resultCode != ConnectionResult.SUCCESS) {
			if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
				GooglePlayServicesUtil.getErrorDialog(resultCode, this, PLAY_SERVICES_RESOLUTION_REQUEST).show();
			} else {
				Toast.makeText(getApplicationContext(), "This device is not supported.", Toast.LENGTH_LONG).show();
				finish();
			}
			return false;
		}
		return true;
	}

	// Starting the location updates
	protected void startLocationUpdates() {

		LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);

	}

	// Stopping location updates
	protected void stopLocationUpdates() {
		LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
	}

	// Google api callback methods
	@Override
	public void onConnectionFailed(ConnectionResult result) {
		Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = " + result.getErrorCode());
	}

	@Override
	public void onConnected(Bundle arg0) {

		// Once connected with google api, get the location
		displayLocation();

		if (mRequestingLocationUpdates) {
			startLocationUpdates();
		}
	}

	@Override
	public void onConnectionSuspended(int arg0) {
		mGoogleApiClient.connect();
	}

	@Override
	public void onLocationChanged(Location location) {
		// Assign the new location
		mLastLocation = location;

		 Toast.makeText(getApplicationContext(), "Location changed!", Toast.LENGTH_SHORT).show();

		// Displaying the new location on UI
		displayLocation();

	}

	@Override
	protected void onStart() {
		super.onStart();
		if (mGoogleApiClient != null) {
			mGoogleApiClient.connect();
		}
	}

	@Override
	protected void onResume() {
		super.onResume();

		checkPlayServices();

		// Resuming the periodic location updates
		if (mGoogleApiClient.isConnected() && mRequestingLocationUpdates) {
			startLocationUpdates();
			
		}
	}

	@Override
	protected void onStop() {
		super.onStop();
		if (mGoogleApiClient.isConnected()) {
			
			mGoogleApiClient.disconnect();
		}
	}

	@Override
	protected void onPause() {
		
		super.onPause();
		stopLocationUpdates();
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}
}
