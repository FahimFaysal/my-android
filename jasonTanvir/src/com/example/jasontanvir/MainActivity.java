package com.example.jasontanvir;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class MainActivity extends ActionBarActivity implements OnClickListener {
	
	Button btnAddQuestion, btnAllQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        btnAddQuestion = (Button) findViewById(R.id.buttonAddQuestion);
        btnAllQuestion = (Button) findViewById(R.id.buttonAllQuestion);
        
        btnAddQuestion.setOnClickListener(this);
        btnAllQuestion.setOnClickListener(this);
    }

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent i;
		switch (v.getId()) {
		case R.id.buttonAddQuestion:
			i = new Intent(getApplicationContext(), AddQuestion.class);
			startActivity(i);
			break;

		case R.id.buttonAllQuestion:
			i = new Intent(getApplicationContext(), AllQuestion.class);
			startActivity(i);
			break;
		}
	}

}
