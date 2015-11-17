package com.example.mymap;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

public class Fragment extends FragmentActivity {

	private static final int Gps_ErrorDialogRequest = 9001;
	GoogleMap googleMap;
	// Fragment mapView;

	private double shohidMinerLat = 23.726572;
	double shohidMinerLng = 90.395442;

	private float defualtZoom = 10;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (serviceOk()) {

			setContentView(R.layout.activity_map_fragment);

			if (initMap()) {
				Toast.makeText(this, "Ready to map :D", Toast.LENGTH_SHORT)
						.show();
				// gotoLocation(shohidMinerLat, shohidMinerLng);
				gotoLocation(shohidMinerLat, shohidMinerLng, defualtZoom);
			} else {
				Toast.makeText(this, "Map is not avialable", Toast.LENGTH_SHORT)
						.show();
			}

		} else {

			setContentView(R.layout.activity_home);
		}

	}

	private boolean initMap() {
		if (googleMap == null) {
			SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager()
					.findFragmentById(R.id.map);
			googleMap = supportMapFragment.getMap();
		}
		return (googleMap != null);
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

	private void gotoLocation(double lat, double lng) {
		LatLng lagLng = new LatLng(lat, lng);
		CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLng(lagLng);
		googleMap.moveCamera(cameraUpdate);
	}

	private void gotoLocation(double lat, double lng, float defualtZoom2) {
		LatLng lagLng = new LatLng(lat, lng);
		CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(lagLng,
				defualtZoom2);
		googleMap.moveCamera(cameraUpdate);

	}

}
