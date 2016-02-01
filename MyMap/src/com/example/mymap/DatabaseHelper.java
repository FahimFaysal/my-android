package com.example.mymap;

import java.util.ArrayList;

import android.R.bool;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
	public static final String DB_NAME = "gps_manager";
	public static final int DB_VERSION = 1;

	public static final String GPS_TABLE = "gps_table";

	public static final String NO = "_id";
	public static final String GPS_LATITUDE = "latitude";
	public static final String GPS_LONGITUDE = "longitude";

	public static final String GPS_TABLE_SQL = "CREATE TABLE " + GPS_TABLE + " (" + NO + " INTEGER PRIMARY KEY, " + GPS_LATITUDE + " TEXT, " + GPS_LONGITUDE + " TEXT)";

	public DatabaseHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);

	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// create tables
		db.execSQL(GPS_TABLE_SQL);
		// Log.e("TABLE CREATE", GPS_TABLE_SQL);
	}

	public boolean delelteAll() {
		boolean isdelete = false;

		SQLiteDatabase db = this.getWritableDatabase();

		db.execSQL("DELETE FROM " + GPS_TABLE);
		db.close();
		return false;
	}

	public long insertGPS(GPS gps) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();

		values.put(NO, gps.getNo());
		values.put(GPS_LATITUDE, gps.getLatitude());
		values.put(GPS_LONGITUDE, gps.getLongitude());

		long inserted = 0;

		try {
			inserted = db.insert(GPS_TABLE, null, values);
		} catch (Exception e) {
			// TODO: handle exception
			Log.e("i'm here ", e.getMessage());
		}

		db.close();
		return inserted;
	}

	// query
	public ArrayList<GPS> getAllGPS() {
		ArrayList<GPS> allGps = new ArrayList<GPS>();
		SQLiteDatabase db = this.getReadableDatabase();

		// String[] columns={NAME_FIELD, EMAIL_FIELD, PHONE_FIELD};
		// SELECT * FROM EMPLOYEE;
		Cursor cursor = db.query(GPS_TABLE, null, null, null, null, null, null);

		// Cursor cursor = db.rawQuery("SELECT * FROM EMPLOYEE", null);
		if (cursor != null && cursor.getCount() > 0) {
			cursor.moveToFirst();
			for (int i = 0; i < cursor.getCount(); i++) {
				//
				int no = cursor.getInt(cursor.getColumnIndex(NO));
				String latitude = cursor.getString(cursor.getColumnIndex(GPS_LATITUDE));
				String longitude = cursor.getString(cursor.getColumnIndex(GPS_LONGITUDE));

				GPS gps = new GPS();
				gps.setNo(no);
				gps.setLatitude(latitude);
				gps.setLongitude(longitude);
				allGps.add(gps);
				cursor.moveToNext();
			}
		}
		cursor.close();
		db.close();

		return allGps;
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}
}
