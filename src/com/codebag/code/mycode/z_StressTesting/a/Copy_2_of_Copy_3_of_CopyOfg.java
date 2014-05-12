package com.codebag.code.mycode.z_StressTesting.a;

import android.content.Context;
import android.graphics.Color;
import android.widget.TextView;
import android.widget.Toast;

import com.codebag.bag.CaseListView;

public class Copy_2_of_Copy_3_of_CopyOfg extends CaseListView {

	public Copy_2_of_Copy_3_of_CopyOfg(Context context) {
		super(context);
	}

	public void run_functionA() {
		Toast.makeText(getContext(), "run_functionA", Toast.LENGTH_SHORT).show();
	}
	
	public void run_functionB() {
		Toast.makeText(getContext(), "run_functionB", Toast.LENGTH_SHORT).show();
	}
	
	public void run_functionC() {
		Toast.makeText(getContext(), "run_functionB", Toast.LENGTH_SHORT).show();
		TextView v = new TextView(getContext());
		v.setText("run_functionC");
		v.setTextColor(Color.BLUE);
		v.setTextSize(30);
		showView(v);
	}
}
