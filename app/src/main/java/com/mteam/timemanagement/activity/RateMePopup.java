package com.mteam.timemanagement.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;

import com.mteam.timemanagement.R;
import com.mteam.timemanagement.events.RateMePopupEventHandler;


public class RateMePopup extends Dialog {

	Activity activity;
	
	public RateMePopup(Context context) {
		super(context);
		this.activity = (Activity) context;
	}
	
	private Button mRateNow;
	
	private Button mNotNow;
	
	private Button mDontAskAgain;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);  
		
		setContentView(R.layout.popup_rate);
		setTitle(getContext().getString(R.string.rate_text));
		
		mRateNow = (Button) findViewById(R.id.btnRateNow);
		mNotNow = (Button) findViewById(R.id.btnNotNow);
		mDontAskAgain = (Button) findViewById(R.id.btnDontAskAgain);
		
		RateMePopupEventHandler eventHandler = new RateMePopupEventHandler(this, activity);
		mRateNow.setOnClickListener(eventHandler);
		mNotNow.setOnClickListener(eventHandler);
		mDontAskAgain.setOnClickListener(eventHandler);
	}

	public Button getmRateNow() {
		return mRateNow;
	}

	public void setmRateNow(Button mRateNow) {
		this.mRateNow = mRateNow;
	}

	public Button getmNotNow() {
		return mNotNow;
	}

	public void setmNotNow(Button mNotNow) {
		this.mNotNow = mNotNow;
	}

	public Button getmDontAskAgain() {
		return mDontAskAgain;
	}

	public void setmDontAskAgain(Button mDontAskAgain) {
		this.mDontAskAgain = mDontAskAgain;
	}
}
