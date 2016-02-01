package com.example.sqliteapp;

import java.util.ArrayList;

import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteOpenHelper;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
	
	
	public static DatabaseHelper sInstance; 

	public static final String DB_NAME = "task_management";
	public static final int DB_VERSION = 1;

	public static final String EMPLOYER_TABEL = "employee";
	public static final String ID_FIELD = "_id";
	public static final String NAME_FIELD = "name";
	public static final String EMAIL_FIELD = "email";
	public static final String PHONE_FIELD = "phone";
	public static final String DESIGNATION_FIELD = "designation";

	// public static final String EMPLOYEE_TABEL_SQL = "CREATE TABEL " + EMPLOYER_TABEL + " (" + ID_FIELD + " INTEGER PRIMARY KEY, " + NAME_FIELD + " TEXT, " + EMAIL_FIELD + " TEXT, " + PHONE_FIELD + " TEXT, " + DESIGNATION_FIELD + " TEXT)";
	public static final String EMPLOYEE_TABLE_SQL = "CREATE TABLE " + EMPLOYER_TABEL + " (" + ID_FIELD + " INTEGER PRIMARY KEY, " + NAME_FIELD + " TEXT, " + EMAIL_FIELD + " TEXT, " + PHONE_FIELD + " TEXT, " + DESIGNATION_FIELD + " TEXT)";

	private DatabaseHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
		// TODO Auto-generated constructor stub
	}
	
	public static DatabaseHelper getInstance(Context context){
		
		if(sInstance == null)
			sInstance = new DatabaseHelper(context);
		
		return sInstance;
		
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(EMPLOYEE_TABLE_SQL);
		Log.e("tabe create", EMPLOYEE_TABLE_SQL);
	}

	public long insertEmployee(Employee emp) {
		SQLiteDatabase db = this.getWritableDatabase("test1232");
		ContentValues val = new ContentValues();

		val.put(NAME_FIELD, emp.getName());
		val.put(EMAIL_FIELD, emp.getEmail());
		val.put(PHONE_FIELD, emp.getPhone());
		val.put(DESIGNATION_FIELD, emp.getDesignation());

		long insert = db.insert(EMPLOYER_TABEL, null, val);

		db.close();
		return insert; // to check correctly insert
	}

	public ArrayList<Employee> getAllEmployee() {
		ArrayList<Employee> allEmployees = new ArrayList<Employee>();
		SQLiteDatabase db = this.getReadableDatabase("test1232");
		// String[] columns;// if select all then null
		// selection is where condition : "name = 'Fahim' AND designation = 'Programmer'" => "name = ? AND designation = ?"
		// selectionArgs : new String []{"Fahim", "Programmer"}

		Cursor cursor = db.query(EMPLOYER_TABEL, null, null, null, null, null, null); // select * from Employee
		// or cursor = db.rowQuery("select * from Employee, null);

		// first check the ob is null else it will crash
		if (cursor != null && cursor.getCount() > 0) {
			cursor.moveToFirst();
			for (int i = 0; i < cursor.getCount(); i++) {

				int id = cursor.getInt(cursor.getColumnIndex(ID_FIELD));
				String name = cursor.getString(cursor.getColumnIndex(NAME_FIELD));
				String email = cursor.getString(cursor.getColumnIndex(EMAIL_FIELD));
				String phone = cursor.getString(cursor.getColumnIndex(PHONE_FIELD));
				String designation = cursor.getString(cursor.getColumnIndex(DESIGNATION_FIELD));

				// create a object with above field
				Employee ep = new Employee(id, name, email, phone, designation);
				// add this object to the array list
				allEmployees.add(ep);

				cursor.moveToNext();
			}
			cursor.close();
			db.close();
		}
		return allEmployees;
	}

	public ArrayList<Employee> SearchEmployee(String keyword) {
		ArrayList<Employee> employees = new ArrayList<Employee>();
		SQLiteDatabase db = this.getReadableDatabase("test1232");
		Cursor cursor = db.query(EMPLOYER_TABEL, null, NAME_FIELD + " LIKE '%" + keyword + "%' or " + EMAIL_FIELD + " LIKE '%" + keyword + "%' or " + PHONE_FIELD + " LIKE '%" + keyword + "%' or " + DESIGNATION_FIELD + " LIKE '%" + keyword + "%'", null, null, null, null);
		// Cursor cursor1 = db.rawQuery("select * from employee where name + " LIKE '%" + keyword + "%' or " + EMAIL_FIELD + " LIKE '%" + keyword + "%' or " + PHONE_FIELD + " LIKE '%" + keyword + "%' or " + DESIGNATION_FIELD + " LIKE '%" + keyword + "%';", null);
		if (cursor != null && cursor.getCount() > 0) {
			cursor.moveToFirst();
			for (int i = 0; i < cursor.getCount(); i++) {

				int id = cursor.getInt(cursor.getColumnIndex(ID_FIELD));
				String name = cursor.getString(cursor.getColumnIndex(NAME_FIELD));
				String email = cursor.getString(cursor.getColumnIndex(EMAIL_FIELD));
				String phone = cursor.getString(cursor.getColumnIndex(PHONE_FIELD));
				String designation = cursor.getString(cursor.getColumnIndex(DESIGNATION_FIELD));

				// create a object with above field
				Employee ep = new Employee(id, name, email, phone, designation);
				// add this object to the array list
				employees.add(ep);

				cursor.moveToNext();
			}
			cursor.close();// / check it ****************
			db.close();
		}

		return employees;
	}

	public int updateEmployeeName(int eId, String nwName) {
		SQLiteDatabase db = this.getReadableDatabase("test1232");
		ContentValues values = new ContentValues();
		values.put(NAME_FIELD, nwName);
		int update = db.update(EMPLOYER_TABEL, values, ID_FIELD + "=?", new String[] { "" + eId });

		db.close();

		return update;
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
