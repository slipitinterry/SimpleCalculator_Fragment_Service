package com.example.simplecalculator_fragment_service;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class FragmentOperations extends Fragment {
		
	private OnButtonClickListener listener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        
    	View view = inflater.inflate(R.layout.fragment_operations, container, false);

        Button button1 = (Button) view.findViewById(R.id.button1);
            button1.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
            	  operationAdd(v);
              }
            });
        Button button2 = (Button) view.findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
        	  operationSubtract(v);
          }
        });
        Button button3 = (Button) view.findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
        	  operationMultiply(v);
          }
        });
        Button button4 = (Button) view.findViewById(R.id.button4);
        button4.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
        	  operationDivide(v);
          }
        });
        return view;
    }
    
	public interface OnButtonClickListener {
		public void operationSubtract(View v);
		public void operationMultiply(View v);
		public void operationAdd(View v);
		public void operationDivide(View v);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		if (activity instanceof OnButtonClickListener) {
			listener = (OnButtonClickListener) activity;
		} else {
			throw new ClassCastException(activity.toString()
		      + " must implemenet FragmentOperations.OnButtonClickListener");
		}
	}

    
	/**
	 * Respond to the Subtract button click.
	 * @param v
	 */
	public void operationSubtract(View v){
		listener.operationSubtract(v);	
	}
	
	/**
	 * Respond to the Multiply button click.
	 * @param v
	 */
	public void operationMultiply(View v){
		listener.operationMultiply(v);	
	}
	
	/**
	 * Respond to the Add button click.
	 * @param v
	 */
	public void operationAdd(View v){
		listener.operationAdd(v);	
	}
	
	/**
	 * Respond to the Divide button click.
	 * @param v
	 */
	public void operationDivide(View v){
		listener.operationDivide(v);	
	}

}
