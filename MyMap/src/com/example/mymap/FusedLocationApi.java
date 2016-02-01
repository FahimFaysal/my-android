package com.example.mymap;

import android.app.Dialog;
import android.content.IntentSender;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

public class FusedLocationApi extends FragmentActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

	private static final int Gps_ErrorDialogRequest = 9001;
	private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
	GoogleMap googleMap;

	private GoogleApiClient googleApiClient;

	public static final String TAG = FusedLocationApi.class.getSimpleName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (serviceOk()) {
			setContentView(R.layout.fused_location_api);
			if (initMap()) {
				// locationCallback = new LocationCallback(this, this, this) ;
				// Toast.makeText(this, "Ready to map :D",
				// Toast.LENGTH_SHORT).show();

				googleApiClient = new GoogleApiClient.Builder(this).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(LocationServices.API).build();

			} else {
				Toast.makeText(this, "Map is not avialable", Toast.LENGTH_SHORT).show();
			}
		} else {
			setContentView(R.layout.activity_home);
		}
	}

	private boolean initMap() {
		if (googleMap == null) {
			SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
			googleMap = supportMapFragment.getMap();
		}
		return (googleMap != null);
	}

	public boolean serviceOk() {
		boolean isServiceOk = false;
		int isAvailable = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);

		if (isAvailable == ConnectionResult.SUCCESS) {
			isServiceOk = true;

		} else if (GooglePlayServicesUtil.isUserRecoverableError(isAvailable)) {
			Dialog dialog = GooglePlayServicesUtil.getErrorDialog(isAvailable, this, getRequestedOrientation());
			dialog.show();
		} else {
			Toast.makeText(this, "Can't connect to Google Play Service", Toast.LENGTH_SHORT).show();
		}

		return isServiceOk;
	}

	@Override
	public void onConnectionFailed(ConnectionResult connectionResult) {
		if (connectionResult.hasResolution()) {
	        try {
	            // Start an Activity that tries to resolve the error
	            connectionResult.startResolutionForResult(this, CONNECTION_FAILURE_RESOLUTION_REQUEST);
	        } catch (IntentSender.SendIntentException e) {
	            e.printStackTrace();
	        }
	    } else {
	        Log.i(TAG, "Location services connection failed with code " + connectionResult.getErrorCode());
	    }

	}

	@Override
	public void onConnected(Bundle arg0) {
		// TODO Auto-generated method stub
		Toast.makeText(getApplicationContext(), "map service is connected", Toast.LENGTH_SHORT).show();
		
		Location location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
		
		if (location == null) {
		    // Blank for a moment...
		}
		else {
		    handleNewLocation(location);
		};
	}

	@Override
	public void onConnectionSuspended(int arg0) {
		Toast.makeText(getApplicationContext(), "map service is Disconnected", Toast.LENGTH_SHORT).show();

	}

	public void goToCurrentLocation(View v) {
		Location location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);

		if (location == null) {
			Toast.makeText(this, "Current location is not avialable", Toast.LENGTH_SHORT).show();
		} else {
			LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
			CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(ll, 10);
			googleMap.animateCamera(cameraUpdate);
		}
	}

	/*
	 * @Override protected void onResume() { super.onResume();
	 * setUpMapIfNeeded(); googleMap.connect(); }
	 * 
	 * @Override protected void onPause() { super.onPause(); if
	 * (mGoogleApiClient.isConnected()) { googleMap.disconnect(); } }
	 */
	private void handleNewLocation(Location location) {
	    Log.d(TAG, location.toString());
	}

}
