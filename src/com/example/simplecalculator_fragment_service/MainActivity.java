package com.example.simplecalculator_fragment_service;


import java.util.Locale;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends FragmentActivity 
						  implements FragmentOperations.OnButtonClickListener,
						  OnInitListener {

	private String operation;
	private boolean bRegistered = false;
	
	private TextToSpeech mTts; // Instance of Text to Speech engine
	private int MY_DATA_CHECK_CODE = 1701;
	private boolean mTTS_available = false;
	
	private boolean mCalFactorial = false;

	// keep a reference to our service
	private FactorialService factServiceBinder;
	
    /** Flag indicating whether we have called bind on the service. */
    boolean mBound;
	
	IntentFilter intentFilter;
    private BroadcastReceiver intentReceiver = new BroadcastReceiver(){

        @Override
        public void onReceive(Context context, Intent intent) {
        	long resultFactorial = intent.getExtras().getLong("FACTORIAL");

        	startResultsIntent(resultFactorial);
        }
        
    };

	// handle the connection between the service and activity
	private ServiceConnection mSvcConnection = new ServiceConnection() {
		
		@Override
		public void onServiceConnected(ComponentName className, IBinder service){
			//This is called when the connection is made
			factServiceBinder = ((FactorialService.FactorialServiceBinder)service).getService();
			mBound = true;
		}
		
		@Override
		public void onServiceDisconnected(ComponentName className){
			//Received when the service unexpectedly disconnects
			factServiceBinder = null;
			mBound = false;
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

        // startup the Text to Speech engine
        Intent checkIntent = new Intent(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        startActivityForResult(checkIntent, MY_DATA_CHECK_CODE);

		// TODO:
		// Try using the Fragment Manager to detach the fragments we don't want to
		// have visible at launch. Attach them when needed after the button presses.
		
		intentFilter = new IntentFilter();
		intentFilter.addAction("FACTORIAL_SHOW");

		// Bind to the Factorial Service
		Intent factorialBindIntent = new Intent(MainActivity.this, FactorialService.class);
		bindService(factorialBindIntent, mSvcConnection, Context.BIND_AUTO_CREATE);
		
		blankDisplay();
	}


	@Override
	protected void onResume(){
		super.onResume();
		
		Log.d("MainActivity", "OnResume:");
		
		if(!bRegistered){
			Log.d("MainActivity", "OnResume: FACTORIAL_SHOW receiver registered");
			registerReceiver(intentReceiver, intentFilter);
			bRegistered = true;
		}

		hideMathFragments();
	}
	
	@Override
	protected void onPause(){
		super.onPause();
		Log.d("MainActivity", "onPause:");

		if(bRegistered){
			Log.d("MainActivity", "onPause: FACTORIAL_SHOW receiver UNregistered");
			unregisterReceiver(intentReceiver); 
			bRegistered = false;
		}
	}

	
	void hideMathFragments(){
		hideFragment(R.id.fragment_second_term);
        hideFragment(R.id.fragment_result);
        hideFragment(R.id.fragment_buttons);
	}
	
	void showMathFragments(){
		showFragment(R.id.fragment_second_term);
        showFragment(R.id.fragment_buttons);
	}
	
	void showFragment(int fragmentId) {
		Log.d("MainActivity", "showFragment:");
        FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
        
		ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		
		Fragment fragment = fm.findFragmentById(fragmentId);
        if (fragment != null && fragment.isHidden()){
        	ft.show(fragment);
        }
	    ft.commit();
    }
	
    void hideFragment(int fragmentId) {
		Log.d("MainActivity", "hideFragment:");
        FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
        
		ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
		
		Fragment fragment = fm.findFragmentById(fragmentId);
        if (fragment != null && fragment.isVisible()){
        	ft.hide(fragment);
        }
	    ft.commit();
    }


    
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_answer) {
			calculateAnswer();
			return true;
		}
		if (id == R.id.action_clear) {
			blankDisplay();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * Respond to the Subtract button click.
	 * @param v
	 */
	public void operationSubtract(View v){
		TextView txtOperation = (TextView)findViewById(R.id.textOperation);
		operation = getString(R.string.subtract);
		txtOperation.setText(operation);
		showMathFragments();
	}
	
	/**
	 * Respond to the Multiply button click.
	 * @param v
	 */
	public void operationMultiply(View v){
		TextView txtOperation = (TextView)findViewById(R.id.textOperation);
		operation = getString(R.string.multiply);
		txtOperation.setText(operation);
		showMathFragments();
		
	}
	
	/**
	 * Respond to the Add button click.
	 * @param v
	 */
	public void operationAdd(View v){
		TextView txtOperation = (TextView)findViewById(R.id.textOperation);
		operation = getString(R.string.add);
		txtOperation.setText(operation);
		showMathFragments();
		
	}
	
	/**
	 * Respond to the Divide button click.
	 * @param v
	 */
	public void operationDivide(View v){
		TextView txtOperation = (TextView)findViewById(R.id.textOperation);
		operation = getString(R.string.divide);
		txtOperation.setText(operation);
		showMathFragments();
		
	}
	
	/**
	 * Respond to the Calculate button click.
	 * @param v
	 */
	public void calculateAnswer(View v){
		calculateAnswer();
	}
	
	public void calculateAnswer(){
		EditText editNum1 = (EditText)findViewById(R.id.editNumber1);
		EditText editNum2 = (EditText)findViewById(R.id.editNumber2);

		String strNum1 = editNum1.getText().toString();
		String strNum2 = editNum2.getText().toString();

		if (operation.isEmpty() || strNum1.isEmpty() || strNum2.isEmpty()){
			setAnswer(0.0f);
		}
		else{
			
			float num1 = Float.parseFloat(strNum1); 
			float num2 = Float.parseFloat(strNum2); 
			
			float result = 0.0f;
	
			if (operation.compareToIgnoreCase("-") == 0){
				result = num1 - num2;
			}
			else if (operation.compareToIgnoreCase("+") == 0){
				result = num1 + num2;
			}
			else if (operation.compareToIgnoreCase("*") == 0){
				result = num1 * num2;
			}
			else if (operation.compareToIgnoreCase("/") == 0){
				result = num1 / num2;
			}
				
			setAnswer(result);
		}
		
	}
	
	
	/**
	 * Set the calculation result into the textview
	 * @param result
	 */
	private void setAnswer(float result){
        showFragment(R.id.fragment_result);
        showFragment(R.id.fragment_buttons);

        TextView txtResult = (TextView)findViewById(R.id.textAnswer);
		String toastString = "";
		String strResult = "";
		
		strResult = String.valueOf(result);
		txtResult.setText(strResult);

		toastString = getString(R.string.answer) + " " + strResult;
		Toast.makeText(getApplicationContext(), 
						toastString,
						Toast.LENGTH_LONG).show();
		
		if(mTTS_available){
			EditText editNum1 = (EditText)findViewById(R.id.editNumber1);
			EditText editNum2 = (EditText)findViewById(R.id.editNumber2);
			TextView txtOperation = (TextView)findViewById(R.id.textOperation);

			String strNum1 = editNum1.getText().toString();
			String strOpp = txtOperation.getText().toString();
			String strNum2 = editNum2.getText().toString();
			
			String strOp = "unknown operation";
			if (strOpp.compareToIgnoreCase("-") == 0){
				strOp = "minus";
			}
			else if (strOpp.compareToIgnoreCase("+") == 0){
				strOp = "plus";
			}
			else if (strOpp.compareToIgnoreCase("*") == 0){
				strOp = "times";
			}
			else if (strOpp.compareToIgnoreCase("/") == 0){
				strOp = "divided by";
			}

			String speakString = strNum1 + " " + strOp  + " " + strNum2 + " " + " equals " + strResult;

			if(mCalFactorial){
				speakString = "The factorial of " + strNum1 + " is " + strResult;
			}
			mTts.setLanguage(Locale.UK);
			mTts.speak(speakString, TextToSpeech.QUEUE_ADD, null);
		}
		
		mCalFactorial = false;
	}
	
	
	private void startResultsIntent(long result){
		
		Log.d("MainActivity", "startResultsIntent: " + result + " in startResultsIntent!");
		
		// Open the Result activity when clicking the Answer button
		Intent intent = new Intent(this, ResultActivity.class);
		
		// Pass the Result value to be displayed
		intent.putExtra(getString(R.string.resultextra), result+"");
		
		// Set flags to reuse intent if it still exists
		intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		startActivity(intent);

	}
	
	
	/**
	 * Respond to the Clear button click
	 * @param v
	 */
	public void clearNumbers(View v){
		blankDisplay();
		hideMathFragments();
	}
	
	
	/**
	 * set all the appropriate fields to empty strings
	 */
	private void blankDisplay(){
	
		TextView txtOperation = (TextView)findViewById(R.id.textOperation);
		operation = "";
		txtOperation.setText(operation);

		EditText editNum1 = (EditText)findViewById(R.id.editNumber1);
		EditText editNum2 = (EditText)findViewById(R.id.editNumber2);
		TextView textAns = (TextView)findViewById(R.id.textAnswer);

		editNum1.setText("");
		editNum2.setText("");
		textAns.setText("");
		
	}

	/**
	 * Respond to the Factorial button click.
	 * @param v
	 */
	public void calcFactorial(View v){
		mCalFactorial = true;
		EditText editNum1 = (EditText)findViewById(R.id.editNumber1);
		String strNum1 = editNum1.getText().toString();

		try{
			int num1 = Integer.parseInt(strNum1); 
			
			if (mBound){
				long factNumber = factServiceBinder.getFactorial(num1);
				setAnswer(factNumber);
			}
			else{
				String toastString = "Error connecting to the Factorial Service!";
				Toast.makeText(getApplicationContext(), 
								toastString,
								Toast.LENGTH_LONG).show();

			}
		}
		catch(NumberFormatException e){
			String toastString = strNum1 + " is not an Integer!";
			Toast.makeText(getApplicationContext(), 
							toastString,
							Toast.LENGTH_LONG).show();
			
		}
		
	}
	
	/**
	 * Respond to the FactorialService button click
	 * @param v
	 */
	public void calcFactorialService(View v){

		Log.d("MainActivity", "calcFactorialService:");

		mCalFactorial = true;
		EditText editNum1 = (EditText)findViewById(R.id.editNumber1);
		String strNum1 = editNum1.getText().toString();

		try{
			int num1 = Integer.parseInt(strNum1); 

			Log.d("MainActivity", "calcFactorialService: Start Service for value:" + num1);

			// Implicitly start the service to get the work done.
			Intent factorialIntent = new Intent(this, FactorialService.class);
			factorialIntent.putExtra("NUMBER", num1);

			startService(factorialIntent);
			
		}
		catch(NumberFormatException e){
			String toastString = strNum1 + " is not an Integer!";
			Toast.makeText(getApplicationContext(), 
							toastString,
							Toast.LENGTH_LONG).show();
			
		}
		
	}

    /**
     *
     * Respond to startActivityForResult() call to setup Text to Speech
     *
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // Decide what to do based on the original request code
        if (requestCode == MY_DATA_CHECK_CODE) {
            if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
                // success, create the TTS instance
                mTts = new TextToSpeech(getApplicationContext(), this);
            } else {
                // missing data, install it
                Intent installIntent = new Intent();
                installIntent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
                startActivity(installIntent);
            }
        }

    }

	@Override
	public void onInit(int status) {
        if(status == TextToSpeech.SUCCESS){
            mTTS_available = true;
        }
        else{
            mTTS_available = false;
        }
	}

}
