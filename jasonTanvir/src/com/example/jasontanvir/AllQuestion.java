package com.example.jasontanvir;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class AllQuestion extends ListActivity {

	// Progress Dialog
	private ProgressDialog progressDialog;

	// Creating JSON Parser object
	JSONParser jsonParser = new JSONParser();

	ArrayList<HashMap<String, String>> jsonArrayList;

	// url to get all jsonArray list
	private static String url_all_jsonArray = "http://10.0.2.2/tanvir_android/allquestion.php";

	// JSON Node names
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_SAMPLE_QUESTION = "sample_question";
	private static final String TAG_QID = "question_no";
	private static final String TAG_QUESTION = "question";

	// jsonArray JSONArray
	JSONArray jsonArray = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.all_question);

		// Hashmap for ListView
		jsonArrayList = new ArrayList<HashMap<String, String>>();

		// Loading jsonArray in Background Thread
		new LoadAllQuestion().execute();

		// Get listview
//		ListView lv = getListView();

		// on seleting single product
		// launching Edit Product Screen
		/*lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// getting values from selected ListItem
				String pid = ((TextView) view.findViewById(R.id.pid)).getText().toString();

				// Starting new intent
				Intent in = new Intent(getApplicationContext(), EditProductActivity.class);
				// sending pid to next activity
				in.putExtra(TAG_QID, pid);

				// starting new activity and expecting some response back
				startActivityForResult(in, 100);
			}
		});*/

	}
/*
	// Response from Edit Product Activity
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		// if result code 100
		if (resultCode == 100) {
			// if result code 100 is received
			// means user edited/deleted product
			// reload this screen again
			Intent intent = getIntent();
			finish();
			startActivity(intent);
		}

	}
*/
	/**
	 * Background Async Task to Load all product by making HTTP Request
	 * */
	class LoadAllQuestion extends AsyncTask<String, String, String> {

		
		
		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progressDialog = new ProgressDialog(AllQuestion.this);
			progressDialog.setMessage("Loading question. Please wait...");
			progressDialog.setIndeterminate(false);
			progressDialog.setCancelable(false);
			progressDialog.show();
		}

		/**
		 * getting All jsonArray from url
		 * */
		protected String doInBackground(String... args) {
			
		
			
			// Building Parameters
			List<NameValuePair> questionList = new ArrayList<NameValuePair>();
			
			
			// getting JSON string from URL
			
			JSONObject json = jsonParser.makeHttpRequest(url_all_jsonArray, "GET", questionList);

			// Check your log cat for JSON reponse
			Log.d("All question :) ", json.toString());
			
			try {
				// Checking for SUCCESS TAG
				int success = json.getInt(TAG_SUCCESS);
				
				if (success == 1) {
					// jsonArray found
					// Getting Array of jsonArray
					jsonArray = json.getJSONArray(TAG_SAMPLE_QUESTION);
					
					// looping through All jsonArray
					for (int i = 0; i < jsonArray.length(); i++) {
						
						
						JSONObject jsonObject = jsonArray.getJSONObject(i);
						
						// Storing each json item in variable
						String id = jsonObject.getString(TAG_QID);
						String question = jsonObject.getString(TAG_QUESTION);
						String op1 = jsonObject.getString("option_01");
						String op2 = jsonObject.getString("option_02");
						String op3 = jsonObject.getString("option_03");
						
						// creating new HashMap
						HashMap<String, String> map = new HashMap<String, String>();

						// adding each child node to HashMap key => value
						map.put(TAG_QID, id);
						map.put(TAG_QUESTION, question);
						map.put("OP1", op1);
						map.put("OP2", op2);
						map.put("OP3", op3);

						// adding HashList to ArrayList
						jsonArrayList.add(map);
						
					}
				} else {
					// no jsonArray found
					// Launch Add New product Activity
//					Intent i = new Intent(getApplicationContext(), NewProductActivity.class);
					// Closing all previous activities
//					i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//					startActivity(i);
					
					Toast.makeText(getApplicationContext(),  "No question found", Toast.LENGTH_LONG).show();
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return null;
		}

		/**
		 * After completing background task Dismiss the progress dialog
		 * **/
		protected void onPostExecute(String file_url) {
			// dismiss the dialog after getting all jsonArray
			progressDialog.dismiss();
			// updating UI from Background Thread
			runOnUiThread(new Runnable() {
				public void run() {
					/**
					 * Updating parsed JSON data into ListView
					 * */
					ListAdapter adapter = new SimpleAdapter(AllQuestion.this, jsonArrayList, R.layout.list_item, new String[] { TAG_QID, TAG_QUESTION, "OP1", "OP2", "OP3" }, new int[] { R.id.pid, R.id.name, R.id.textViewOp1, R.id.textViewOp2, R.id.textViewOp3 });
					// updating listview
					setListAdapter(adapter);
				}
			});

		}

	}
}