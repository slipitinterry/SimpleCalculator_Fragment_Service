package com.example.simplecalculator_fragment_service;

import com.example.simplecalculator_fragment_service.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class FragmentButtons extends Fragment {
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_buttons, container, false);

        return v;
    }
    
	/**
	 * Respond to the Calculate button click.
	 * @param v
	 */
	public void calculateAnswer(View v){

	}
	
	/**
	 * Respond to the Clear button click
	 * @param v
	 */
	public void clearNumbers(View v){

	}


}
