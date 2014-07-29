package com.example.simplecalculator_fragment_service;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.simplecalculator_fragment_service.R;

public class ResultActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result);

		Intent in = getIntent();
		String strResult = in.getExtras().getString(getString(R.string.resultextra));

		Log.d("ResultActivity", "onCreate: " + strResult + " in startResultsIntent!");

		TextView txtView = (TextView)findViewById(R.id.txtResult);
		txtView.setText(strResult);
		txtView.invalidate();
		
	}

	public void onGoBack(View v){
		// Return to the main activity when clicking the Go Back button
		Intent intent = new Intent(this, MainActivity.class);
		
		// Set flags to reuse intent if it still exists
		intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		startActivity(intent);

	}
}
