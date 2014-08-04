package com.codebag.code.mycode.cleanmasterdemo;

import android.content.Context;
import android.graphics.Color;
import android.widget.FrameLayout;

import com.codebag.R;
import com.codebag.bag.CaseListView;
import com.codebag.bag.Entry;
import com.codebag.code.mycode.cleanmasterdemo.RingView.AniminationListener;
import com.codebag.code.mycode.utils.Log;

public class Invoker extends CaseListView {

	public Invoker(Context context) {
		super(context);
	}
	
	@Entry
	public void showCircle() {
		RingView view = new RingView(getContext(), 80, 540);
		view.setColor(0xE624a0ff, 0x19000000);
		view.setBackgroundColor(Color.WHITE);
		view.setAniminationListener(new AniminationListener() {
			
			@Override
			public void start() {
				Log.addLog(this, "start ----");
			}
			
			@Override
			public void end() {
				Log.addLog(this, "end ----");
			}
		});
		view.startAnimination(0);
		showView(view);
	}
	
	@Entry
	public void showCleanDial() {
		
		FrameLayout f = new FrameLayout(getContext()){

			@Override
			protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
				super.onMeasure(widthMeasureSpec, heightMeasureSpec);
			}
			
		};
		FrameLayout.LayoutParams p = new FrameLayout.LayoutParams(-2, -2);
		f.setBackgroundColor(Color.BLUE);
		
		CleanDial clean = new CleanDial(getContext());
		clean.setDialMarkResource(R.drawable.memery);
		clean.setRoatingBg(R.drawable.fan);
		clean.setSmallMarkResource(R.drawable.roket);
		clean.setProgress(30);
		clean.start();
		
		f.addView(clean, p);
		showView(f);
	}

}