package com.example.mymap;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;

public class MyLocation extends FragmentActivity implements ConnectionCallbacks, OnConnectionFailedListener {

	private static final int Gps_ErrorDialogRequest = 9001;
	GoogleMap googleMap;
	GoogleApiClient googleApiClient;
	LocationRequest locationRequest;
	LocationCallback locationCallback ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (serviceOk()) {
			setContentView(R.layout.mylocaltion);
			if (initMap()) {
//				locationCallback = new LocationCallback(this, this, this) ;
//				Toast.makeText(this, "Ready to map :D", Toast.LENGTH_SHORT).show();
//				googleMap.setMyLocationEnabled(true); // must enable location	// from device
				
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
	public void onConnectionFailed(ConnectionResult arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onConnected(Bundle arg0) {
		// TODO Auto-generated method stub
		Toast.makeText(getApplicationContext(), "map service is connected", Toast.LENGTH_SHORT).show();
		
	}

	@Override
	public void onConnectionSuspended(int arg0) {
		Toast.makeText(getApplicationContext(), "map service is Disconnected", Toast.LENGTH_SHORT).show();
		
	}

}
