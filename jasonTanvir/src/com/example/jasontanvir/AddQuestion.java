package com.example.jasontanvir;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddQuestion extends Activity{
	
	EditText etQuestion, etOption1, etOptin2, etOption3;
	
	Button btnUpQuesion;
	
	JSONParser jsonParser = new JSONParser();
	
	// Progress Dialog
	private ProgressDialog progressDialog;
	
	// url to create new product
	private static String url_create_product = "http://10.0.2.2/tanvir_android/create_question.php";

	// JSON Node names
	private static final String TAG_SUCCESS = "success";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_question);
		
		etQuestion = (EditText) findViewById(R.id.editTextQuestion);
		etOption1  = (EditText)findViewById(R.id.editTextOp1);
		etOptin2  = (EditText)findViewById(R.id.editTextOp2);
		etOption3 = (EditText)findViewById(R.id.editTextOp3);
		
		btnUpQuesion = (Button) findViewById(R.id.buttonUpQuestion);
		
		btnUpQuesion.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				// creating new product in background thread
				System.out.println("i'm here ........................");
				new CreateNewProduct().execute();
			}
		});
		
	}
	
	class CreateNewProduct extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progressDialog = new ProgressDialog(AddQuestion.this);
			progressDialog.setMessage("Question Product..");
			progressDialog.setIndeterminate(false);
			progressDialog.setCancelable(true);
			progressDialog.show();
		}

		/**
		 * Creating product
		 * */
		protected String doInBackground(String... args) {
			
			
			
			String stQuestion = etQuestion.getText().toString();
			String stOp1 = etOption1.getText().toString();
			String stOp2 = etOptin2.getText().toString();
			String stOp3 = etOption3.getText().toString();
		
			

			// Building Parameters
			List<NameValuePair> questionList = new ArrayList<NameValuePair>();
			questionList.add(new BasicNameValuePair("Question", stQuestion));
			questionList.add(new BasicNameValuePair("option_01", stOp1));
			questionList.add(new BasicNameValuePair("option_02", stOp2));
			questionList.add(new BasicNameValuePair("option_03", stOp3));

			
			
			// getting JSON Object
			// Note that create product url accepts POST method
			JSONObject jsonObject = jsonParser.makeHttpRequest(url_create_product, "POST", questionList);

			
			
			// check log cat fro response
			Log.d("Create Response", jsonObject.toString());

			// check for success tag
			try {
				int success = jsonObject.getInt(TAG_SUCCESS);

				if (success == 1) {
					// successfully created product
					
					Intent i = new Intent(getApplicationContext(), MainActivity.class);
					startActivity(i);

					// closing this screen
					finish();
					
				} else {
					// failed to create product
				}
			} catch (JSONException e) {
//				e.printStackTrace();
				Toast.makeText(getApplicationContext(), ""+e.getMessage(), Toast.LENGTH_LONG).show();
			}

			return null;
		}

		/**
		 * After completing background task Dismiss the progress dialog
		 * **/
		protected void onPostExecute(String file_url) {
			progressDialog.dismiss();
		}

	}
	
}
