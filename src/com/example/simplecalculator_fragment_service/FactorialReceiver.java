package com.example.simplecalculator_fragment_service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class FactorialReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.d("FactorialReceiver", "onReceive:");

		Bundle bundle = intent.getExtras();
		if(bundle != null){
			long result = bundle.getLong("FACTORIAL");
			
			Log.d("FactorialReceiver", "onReceive: " + result + " in FactorialReceiver:onReceive()!");


		    //---send a broadcast intent to update the Factorial value in the activity---
		    Intent broadcastIntent = new Intent();
		    broadcastIntent.setAction("FACTORIAL_SHOW");
		    broadcastIntent.putExtra("FACTORIAL", result); 
		    context.sendBroadcast(broadcastIntent);
		}
	}
}
