package com.example.mymap;

import java.io.IOException;
import java.util.List;

import android.app.Dialog;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

public class GeoCode extends FragmentActivity {

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

			setContentView(R.layout.geo_code_activity);

			if (initMap()) {
				Toast.makeText(this, "Ready to map :D", Toast.LENGTH_SHORT).show();
//				googleMap.setMyLocationEnabled(true);  // must enable location from device 
				// gotoLocation(shohidMinerLat, shohidMinerLng);
//				gotoLocation(shohidMinerLat, shohidMinerLng, defualtZoom);
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

	public void geoLocate(View v) throws IOException {

		hideSoftKeyBoard(v);

		EditText et = (EditText) findViewById(R.id.editTextLocation);
		String locationName = et.getText().toString();

		Geocoder geocoder = new Geocoder(this);

		List<Address> listAdress = geocoder.getFromLocationName(locationName, 1);
		Address address = listAdress.get(0);

		String stLocality = address.getLocality();

		Toast.makeText(getApplicationContext(), stLocality, Toast.LENGTH_LONG).show();

		double lattitide = address.getLatitude(), langitude = address.getLongitude();

		gotoLocation(lattitide, langitude, 10);

	}

	private void hideSoftKeyBoard(View v) {
		InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
		inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);

	}

	private void gotoLocation(double lat, double lng) {
		LatLng lagLng = new LatLng(lat, lng);
		CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLng(lagLng);
		googleMap.moveCamera(cameraUpdate);
	}

	private void gotoLocation(double lat, double lng, float defualtZoom2) {
		LatLng lagLng = new LatLng(lat, lng);
		CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(lagLng, defualtZoom2);
		googleMap.moveCamera(cameraUpdate);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.mapTypeNone:
			googleMap.setMapType(GoogleMap.MAP_TYPE_NONE);
			break;

		case R.id.mapTypeSatallite:
			googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
			break;

		case R.id.mapTypeNormal:
			googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
			break;

		case R.id.mapTypeTerrain:
			googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
			break;

		case R.id.mapTypeHybrid:
			googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
			break;

		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onStop() {
		
		super.onStop();
		MapStateManager mapStateManager = new MapStateManager(this);
		mapStateManager.saveMapState(googleMap);
	}
	
	@Override
	protected void onResume() {
		
		super.onResume();
	MapStateManager mapStateManager = new MapStateManager(this);
		
		CameraPosition cameraPosition = mapStateManager.getSavedCameraPosition();
		if(cameraPosition != null){
			CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
			googleMap.moveCamera(cameraUpdate);
		}
		
//		String st  = mapStateManager.getSaveMapTyep();
		Toast.makeText(getApplicationContext(), "....", Toast.LENGTH_SHORT).show();
		Log.i("MapType..", mapStateManager.getSaveMapTyep()+".............");
		
	}

}