package com.example.simplecalculator_fragment_service;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragmentFactorial extends Fragment {
		
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_factorial, container, false);

        return v;
    }
    
	/**
	 * Respond to the Factorial button click.
	 * @param v
	 */
	public void calcFactorial(View v){

	}
	
	/**
	 * Respond to the FactorialService button click
	 * @param v
	 */
	public void calcFactorialService(View v){

	}

}
