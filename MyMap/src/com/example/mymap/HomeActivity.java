package com.example.mymap;

import com.google.android.gms.location.FusedLocationProviderApi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class HomeActivity extends Activity implements OnClickListener {

	Button btnMapview, btnFragment, btnGeo, btnMyLocation, btnFusedLocationApi, btnGPS, btnJustGPS;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		btnFragment = (Button) findViewById(R.id.buttonMapFragment);
		btnMapview = (Button) findViewById(R.id.buttonMapView);
		btnGeo = (Button) findViewById(R.id.buttonGeo);
		btnMyLocation = (Button) findViewById(R.id.buttonMyLocation);
		btnFusedLocationApi= (Button) findViewById(R.id.buttonFusedLocationApi);
		btnGPS = (Button) findViewById(R.id.buttonGPS);
		btnJustGPS = (Button) findViewById(R.id.buttonLatLan);

		btnFragment.setOnClickListener(this);
		btnMapview.setOnClickListener(this);
		btnGeo.setOnClickListener(this);
		btnMyLocation.setOnClickListener(this);
		btnFusedLocationApi.setOnClickListener(this);
		btnGPS.setOnClickListener(this);
		btnJustGPS.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent i;
		switch (v.getId()) {
		case R.id.buttonMapFragment:
			i = new Intent(HomeActivity.this, MapViewActivity.class);
			startActivity(i);
			break;

		case R.id.buttonMapView:
			i = new Intent(HomeActivity.this, Fragment.class);
			startActivity(i);
			break;

		case R.id.buttonGeo:
			i = new Intent(HomeActivity.this, GeoCode.class);
			startActivity(i);
			break;

		case R.id.buttonMyLocation:
			i = new Intent(HomeActivity.this, MyLocation.class);
			startActivity(i);
			break;
			
		case R.id.buttonFusedLocationApi:
			i = new Intent(HomeActivity.this, FusedLocationApi.class);
			startActivity(i);
			break;
			
		case R.id.buttonGPS:
//			Toast.makeText(getApplicationContext(), "I'M HREE", Toast.LENGTH_LONG).show();
			i = new Intent(HomeActivity.this, GpsTracker.class);
			startActivity(i);
			
			break;

		case R.id.buttonLatLan:
//			Toast.makeText(getApplicationContext(), "I'M HREE", Toast.LENGTH_LONG).show();
			i = new Intent(HomeActivity.this, ShowLocationActivity.class);
			startActivity(i);
			
			break;
		}

	}

}
