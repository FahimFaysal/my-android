package com.example.mymap;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

@SuppressWarnings("unused")
public class MapStateManager {
	private static final String LONGITUDE = "longitude", 
			LATTITUDE = "lattitude", 
			ZOOM = "zoom", 
			BEARING = "bearing", 
			TILT = "tilt", 
			MAPTYPE = "maptype", 
			PREFS_NAME = "mapCameraState";

	private SharedPreferences mapSharedPreferences;

	public MapStateManager(Context context) {
		mapSharedPreferences = context.getSharedPreferences(PREFS_NAME, context.MODE_PRIVATE);

	}

	public void saveMapState(GoogleMap googleMap) {

		SharedPreferences.Editor editor = mapSharedPreferences.edit();

		CameraPosition cameraPosition = googleMap.getCameraPosition();

		editor.putFloat(LATTITUDE, (float) cameraPosition.target.latitude);
		editor.putFloat(LONGITUDE, (float) cameraPosition.target.longitude);
		editor.putFloat(ZOOM, cameraPosition.zoom);
		editor.putFloat(TILT, cameraPosition.tilt);
		editor.putFloat(BEARING, cameraPosition.bearing);

		editor.putInt(MAPTYPE, googleMap.getMapType());

		editor.commit();
	}

	public CameraPosition getSavedCameraPosition() {

		CameraPosition cameraPosition = null;

		double latitude = mapSharedPreferences.getFloat(LATTITUDE, 0); // by default0
		double longitude = mapSharedPreferences.getFloat(LONGITUDE, 0);

		LatLng target = new LatLng(latitude, longitude);

		float zoom = mapSharedPreferences.getFloat(ZOOM, 0);
		float bearing = mapSharedPreferences.getFloat(BEARING, 0);
		float tilt = mapSharedPreferences.getFloat(TILT, 0);

		if (latitude == 0) { // if 0 than ignore
			return null;
		} else {
			cameraPosition = new CameraPosition(target, zoom, tilt, bearing);
		}

		return cameraPosition;
	}

	public String getSaveMapTyep() {

		String mapType = null;
		GoogleMap googleMap;
//		 20 no tutorial challenge 
//		mapType = 
//		System.out.println(mapSharedPreferences.getString(MAPTYPE, "00"));
//		googleMap = mapSharedPreferences.get(MAPTYPE, 0));
		return mapType;

	}

}
