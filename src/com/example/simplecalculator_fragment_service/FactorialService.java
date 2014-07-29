package com.example.simplecalculator_fragment_service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class FactorialService extends IntentService {
	
	
	public FactorialService() {
		super("FactorialService");
	}

	public static String FACTORIAL_SERVICE = "FactorialService";
	
	private final IBinder factBinder = new FactorialServiceBinder();
	
	public class FactorialServiceBinder extends Binder {
		FactorialService getService(){
			return FactorialService.this;
		}
	}

	@Override
	public IBinder onBind(Intent intent) {
		return factBinder;
	}
	
	@Override
	public void onCreate(){
		super.onCreate();
	}
		
	@Override
	protected void onHandleIntent(Intent intent) {
		Log.d("FactorialService", "onHandleIntent:");

		Log.d("FactorialReceiver", "onHandleIntent: Factorial Service starting");

		Bundle bundle = intent.getExtras();
		if(bundle != null){
			int number = (Integer) bundle.get("NUMBER");
			sendFactorialBroadcast(number);
		}

	}
	
	/**
	 * Calculate the factorial of an unsigned integer
	 * @param number >= 1
	 * @return
	 */
	public long getFactorial(int number){
		return (number <= 1) ? 1 : number * getFactorial(number - 1);
	}
	
	private void sendFactorialBroadcast(int number){
		Log.d("FactorialService", "sendFactorialBroadcast:");
		long result = getFactorial(number);
		Intent intent = new Intent();
		intent.setAction("FACTORIAL_READY");

		// Add result to intent before sending
		intent.putExtra("FACTORIAL", result);
		sendBroadcast(intent);
	}
}
