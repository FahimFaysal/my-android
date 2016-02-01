package com.example.sqliteapp;

import java.util.ArrayList;

import net.sqlcipher.database.SQLiteDatabase;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

	EditText etName, etMail, etPhone, etDesgingation, etSearch, etId, etNewName;

	DatabaseHelper dbHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		etName = (EditText) findViewById(R.id.editTextName);
		etMail = (EditText) findViewById(R.id.editTextEmail);
		etPhone = (EditText) findViewById(R.id.editTextPhone);
		etDesgingation = (EditText) findViewById(R.id.editTextDesigtion);
		etSearch = (EditText) findViewById(R.id.editTextSearch);
		etId = (EditText)findViewById(R.id.editTextfindId);
		etNewName = (EditText)findViewById(R.id.editTextNewname);

		// load form library
//		SQLiteDatabase.loadLibs(getApplicationContext());
		//dbHelper = new DatabaseHelper(this);
		dbHelper = DatabaseHelper.getInstance(getApplicationContext());
	}

	public void Save(View v) {
		String name = etName.getText().toString();
		String email = etMail.getText().toString();
		String phone = etPhone.getText().toString();
		String designation = etDesgingation.getText().toString();

		Employee employee = new Employee(name, email, phone, designation);

		Toast.makeText(getApplicationContext(), employee.toString(), Toast.LENGTH_LONG).show();

		long inserted = dbHelper.insertEmployee(employee);

		if (inserted >= 0)
			Toast.makeText(getApplicationContext(), "Data inserted", Toast.LENGTH_LONG).show();
		else
			Toast.makeText(getApplicationContext(), "insertion fail", Toast.LENGTH_LONG).show();

	}

	public void Show(View v) {
		ArrayList<Employee> employees = dbHelper.getAllEmployee();
		if (employees != null && employees.size() > 0) {
			for (Employee employee : employees)
				Toast.makeText(getApplicationContext(), employee.toString(), Toast.LENGTH_LONG).show();
		} else if (employees.size() == 0)
			Toast.makeText(getApplicationContext(), "No data found", Toast.LENGTH_LONG).show();
	}

	public void Search(View v) {
		String st = etSearch.getText().toString();

		ArrayList<Employee> employees = dbHelper.SearchEmployee(st);
		if (employees != null && employees.size() > 0) {
			for (Employee employee : employees)
				Toast.makeText(getApplicationContext(), employee.toString(), Toast.LENGTH_LONG).show();
		} else if (employees.size() == 0)
			Toast.makeText(getApplicationContext(), "No data found", Toast.LENGTH_LONG).show();
	}
	
	public void Update(View v){
		int id = Integer.parseInt(etId.getText().toString());
				String newName = etNewName.getText().toString();
				
		int updated = dbHelper.updateEmployeeName(id , newName);
		
		if(updated > 0)
			Toast.makeText(getApplicationContext(), "row update "+updated, Toast.LENGTH_LONG).show();
		else Toast.makeText(getApplicationContext(), "no row update", Toast.LENGTH_LONG).show();
	}

}
