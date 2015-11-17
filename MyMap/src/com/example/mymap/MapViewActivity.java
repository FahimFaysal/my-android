package com.example.mymap;

////////////////////18 no tutorial 
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;

public class MapViewActivity extends FragmentActivity {

	private static final int Gps_ErrorDialogRequest = 9001;
	GoogleMap googleMap;
	MapView mapView;

	private double shohidMinerLat = 23.726572;
	double shohidMinerLng = 90.395442;

	private float defualtZoom = 10;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (serviceOk()) {

			Toast.makeText(this, "Ready to map :D", Toast.LENGTH_SHORT).show();
			setContentView(R.layout.activity_mapview);
			mapView = (MapView) findViewById(R.id.map);
			mapView.onCreate(savedInstanceState);

		} else {

			setContentView(R.layout.activity_home);
		}

	}

	/*
	 * private boolean initMap(){ if(googleMap == null){ SupportMapFragment
	 * supportMapFragment = (SupportMapFragment)
	 * getSupportFragmentManager().findFragmentById(R.id.map); googleMap =
	 * supportMapFragment.getMap(); } return (googleMap != null); }
	 */

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mapView.onDestroy();

	}

	@Override
	public void onLowMemory() {
		super.onLowMemory();
		mapView.onLowMemory();
	}

	@Override
	protected void onPause() {
		super.onPause();
		mapView.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		mapView.onResume();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		mapView.onSaveInstanceState(outState);
	}

	public boolean serviceOk() {
		boolean isServiceOk = false;
		int isAvailable = GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(this);

		if (isAvailable == ConnectionResult.SUCCESS) {
			isServiceOk = true;

		} else if (GooglePlayServicesUtil.isUserRecoverableError(isAvailable)) {
			Dialog dialog = GooglePlayServicesUtil.getErrorDialog(isAvailable,
					this, getRequestedOrientation());
			dialog.show();
		} else {
			Toast.makeText(this, "Can't connect to Google Play Service",
					Toast.LENGTH_SHORT).show();
		}

		return isServiceOk;
	}

	/*
	 * private void gotoLocation(double lat, double lng){ LatLng lagLng = new
	 * LatLng(lat, lng); CameraUpdate cameraUpdate =
	 * CameraUpdateFactory.newLatLng(lagLng);
	 * googleMap.moveCamera(cameraUpdate); }
	 * 
	 * private void gotoLocation(double lat, double lng, float defualtZoom2) {
	 * LatLng lagLng = new LatLng(lat, lng); CameraUpdate cameraUpdate =
	 * CameraUpdateFactory.newLatLngZoom(lagLng, defualtZoom2);
	 * googleMap.moveCamera(cameraUpdate);
	 * 
	 * }
	 */

}
