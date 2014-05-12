package com.codebag.code.mycode.z_StressTesting.b.copy;

import android.content.Context;
import android.graphics.Color;
import android.widget.TextView;
import android.widget.Toast;

import com.codebag.bag.CaseListView;

public class CopyOfCopy_3_of_CopyOfCopyOfg extends CaseListView {

	public CopyOfCopy_3_of_CopyOfCopyOfg(Context context) {
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
