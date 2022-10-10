package com.example.timemanagement.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.example.timemanagement.HiitApp;
import com.example.timemanagement.R;
import com.example.timemanagement.commons.HiitColor;
import com.example.timemanagement.commons.Params;
import com.example.timemanagement.events.CreateEditTimerActivityEventHandler;

import java.util.List;

public class CreateEditTimerActivity extends Activity {

	private Button mCancel;

	private TextView mTitle;

	private Button mStart;

	private Button mSaveThisTimer;

	private Button mSaveNewTimer;
	
	private Button mDeleteTimer;

	private TextView mTxtTimerDetails;

	private TextView mTxtTimerDetailsName;

	private TextView mTxtTimerDetailsSets;

	private EditText mTimerDetailsName;

	private EditText mTimerDetailsSets;

	private TextView mTxtWarmup;

	private TextView mTxtWarmupName;

	private TextView mTxtWarmupDuration;

	private TextView mTxtWarmupColor;

	private EditText mWarmupName;

	private EditText mWarmupDuration;

	private ImageView mWarmupColor;
	
	private RelativeLayout mLayoutWarmupColor;

	private TextView mTxtHighIntensity;

	private TextView mTxtHighIntensityName;

	private TextView mTxtHighIntensityDuration;

	private TextView mTxtHighIntensityColor;

	private EditText mHighIntensityName;

	private EditText mHighIntensityDuration;

	private ImageView mHighIntensityColor;
	
	private RelativeLayout mLayoutHighIntensityColor;

	private TextView mTxtLowIntensity;

	private TextView mTxtLowIntensityName;

	private TextView mTxtLowIntensityDuration;

	private TextView mTxtLowIntensityColor;

	private EditText mLowIntensityName;

	private EditText mLowIntensityDuration;

	private ImageView mLowIntensityColor;
	
	private RelativeLayout mLayoutLowIntensityColor;

	private TextView mTxtCoolDown;

	private TextView mTxtCoolDownName;

	private TextView mTxtCoolDownDuration;

	private TextView mTxtCoolDownColor;

	private EditText mCoolDownName;

	private EditText mCoolDownDuration;

	private ImageView mCoolDownColor;
	
	private RelativeLayout mLayoutCoolDownColor;
	
	private boolean isOrientationChanged;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_creat_edit_timer);

		prepareData();
		
		if(isOrientationChanged) {
			getWarmupName().setText(HiitApp.currentWarmupName);
			getHighIntensityName().setText(HiitApp.currentHighIntensityName);
			getLowIntensityName().setText(HiitApp.currentLowIntensityName);
			getCoolDownName().setText(HiitApp.currentCoolDownName);
			
			getWarmupDuration().setText(HiitApp.currentWarmupDuration);
			getHighIntensityDuration().setText(HiitApp.currentHighIntensityDuration);
			getLowIntensityDuration().setText(HiitApp.currentLowIntensityDuration);
			getCoolDownDuration().setText(HiitApp.currentCoolDownDuration);
			
			getTimerDetailsName().setText(HiitApp.currentTimerName);
			getTimerDetailsSets().setText(HiitApp.currentTimerSets);
		}
		
		if(HiitApp.currentWarmupColor != null) {
			getWarmupColor().setImageResource(HiitApp.currentWarmupColor.getId());
			getWarmupColor().setTag(HiitApp.currentWarmupColor.getName());
		}
		if(HiitApp.currentHighIntensityColor != null) {
			getHighIntensityColor().setImageResource(HiitApp.currentHighIntensityColor.getId());
			getHighIntensityColor().setTag(HiitApp.currentHighIntensityColor.getName());
		}
		if(HiitApp.currentLowIntensityColor != null) {
			getLowIntensityColor().setImageResource(HiitApp.currentLowIntensityColor.getId());
			getLowIntensityColor().setTag(HiitApp.currentLowIntensityColor.getId());
		}
		if(HiitApp.currentCoolDownColor != null) {
			getCoolDownColor().setImageResource(HiitApp.currentCoolDownColor.getId());
			getCoolDownColor().setTag(HiitApp.currentCoolDownColor.getId());
		}
	}

//	private void loadSavedTimer(int id) {
//		DatabaseHelper databaseHelper = new DatabaseHelper(getApplicationContext());
//		String idStr = String.valueOf(id);
//		String[] selectionArgs = { idStr };
//		List<HiiTTimer> timers = databaseHelper.select(HiiTTimer.class, "id=?", selectionArgs, null, null);
//
//		if (timers.size() != 0) {
//			HiiTTimer timer = timers.get(0);
//
//			mTimerDetailsName.setText(timer.getName());
//			mTimerDetailsSets.setText(String.valueOf(timer.getSets()));
//
//			mWarmupName.setText(timer.getWarmupTitle());
//			mWarmupDuration.setText(timer.getWarmupDuration());
//			mWarmupColor.setImageResource(HiitColor.fromName(timer.getWarmupColor()).getId());
//			mWarmupColor.setTag(timer.getWarmupColor());
//
//			mHighIntensityName.setText(timer.getHighIntensityTitle());
//			mHighIntensityDuration.setText(timer.getHighIntensityDuration());
//			mHighIntensityColor.setImageResource(HiitColor.fromName(timer.getHighIntensityColor()).getId());
//			mHighIntensityColor.setTag(timer.getHighIntensityColor());
//
//			mLowIntensityName.setText(timer.getLowIntensityTitle());
//			mLowIntensityDuration.setText(timer.getLowIntensityDuration());
//			mLowIntensityColor.setImageResource(HiitColor.fromName(timer.getLowIntensityColor()).getId());
//			mLowIntensityColor.setTag(timer.getLowIntensityColor());
//
//			mCoolDownName.setText(timer.getCoolDownTitle());
//			mCoolDownDuration.setText(timer.getCoolDownDuration());
//			mCoolDownColor.setImageResource(HiitColor.fromName(timer.getCoolDownColor()).getId());
//			mCoolDownColor.setTag(timer.getCoolDownColor());
//
//		}
//	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			HiitColor color = HiitColor.fromName(data.getStringExtra(Params.COLOR));
			
			switch (requestCode) {
			case R.id.imgWarmupColor:
				HiitApp.currentWarmupColor = color;
				mWarmupColor.setImageResource(color.getId());
				mWarmupColor.setTag(color.getName());
				break;
			case R.id.imgHighIntensityColor:
				HiitApp.currentHighIntensityColor = color;
				mHighIntensityColor.setImageResource(color.getId());
				mHighIntensityColor.setTag(color.getName());
				break;
			case R.id.imgLowIntensityColor:
				HiitApp.currentLowIntensityColor = color;
				mLowIntensityColor.setImageResource(color.getId());
				mLowIntensityColor.setTag(color.getName());
				break;
			case R.id.imgCoolDownColor:
				HiitApp.currentCoolDownColor = color;
				mCoolDownColor.setImageResource(color.getId());
				mCoolDownColor.setTag(color.getName());
				break;
			}
		}
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		
		isOrientationChanged = true;
		
		HiitApp.currentWarmupName = getWarmupName().getText().toString();
		HiitApp.currentHighIntensityName = getHighIntensityName().getText().toString();
		HiitApp.currentLowIntensityName = getLowIntensityName().getText().toString();
		HiitApp.currentCoolDownName = getCoolDownName().getText().toString();
		
		HiitApp.currentWarmupDuration = getWarmupDuration().getText().toString();
		HiitApp.currentHighIntensityDuration = getHighIntensityDuration().getText().toString();
		HiitApp.currentLowIntensityDuration = getLowIntensityDuration().getText().toString();
		HiitApp.currentCoolDownDuration = getCoolDownDuration().getText().toString();
		
		HiitApp.currentTimerName = getTimerDetailsName().getText().toString();
		HiitApp.currentTimerSets = getTimerDetailsSets().getText().toString();
	}

//	@Override
//	public void onConfigurationChanged(Configuration newConfig) {
//		super.onConfigurationChanged(newConfig);
//		
//		prepareData();
//		
//		mWarmupColor.setImageResource(HiitApp.currentWarmupColor.getId());
//		mWarmupColor.setTag(HiitApp.currentWarmupColor.getName());
//		
//		mHighIntensityColor.setImageResource(HiitApp.currentHighIntensityColor.getId());
//		mHighIntensityColor.setTag(HiitApp.currentHighIntensityColor.getName());
//		
//		mLowIntensityColor.setImageResource(HiitApp.currentLowIntensityColor.getId());
//		mLowIntensityColor.setTag(HiitApp.currentLowIntensityColor.getId());
//		
//		mCoolDownColor.setImageResource(HiitApp.currentCoolDownColor.getId());
//		mCoolDownColor.setTag(HiitApp.currentCoolDownColor.getId());
//		
//		setFonts();
//		System.gc();
//	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		setFonts();
	}
	
	private void setFonts() {
		// Set font
		mTitle.setTypeface(HiitApp.helveticaNeueCyrThin);
		mCancel.setTypeface(HiitApp.helveticaNeueCyrLight);
		mStart.setTypeface(HiitApp.helveticaNeueCyrLight);
		
		mTxtTimerDetails.setTypeface(HiitApp.helveticaNeueCyrLight);
		mTxtTimerDetailsName.setTypeface(HiitApp.helveticaNeueCyrThin);
		mTxtTimerDetailsSets.setTypeface(HiitApp.helveticaNeueCyrThin);
		mTimerDetailsName.setTypeface(HiitApp.helveticaNeueCyrLight);
		mTimerDetailsSets.setTypeface(HiitApp.helveticaNeueCyrLight);
		mTxtTimerDetails.setTypeface(HiitApp.helveticaNeueCyrLight);
		mTxtTimerDetails.setTypeface(HiitApp.helveticaNeueCyrLight);
		
		mTxtWarmup.setTypeface(HiitApp.helveticaNeueCyrLight);
		mTxtWarmupName.setTypeface(HiitApp.helveticaNeueCyrThin);
		mTxtWarmupDuration.setTypeface(HiitApp.helveticaNeueCyrThin);
		mTxtWarmupColor.setTypeface(HiitApp.helveticaNeueCyrThin);
		mWarmupName.setTypeface(HiitApp.helveticaNeueCyrLight);
		mWarmupDuration.setTypeface(HiitApp.helveticaNeueCyrLight);
		
		mTxtHighIntensity.setTypeface(HiitApp.helveticaNeueCyrLight);
		mTxtHighIntensityName.setTypeface(HiitApp.helveticaNeueCyrThin);
		mTxtHighIntensityDuration.setTypeface(HiitApp.helveticaNeueCyrThin);
		mTxtHighIntensityColor.setTypeface(HiitApp.helveticaNeueCyrThin);
		mHighIntensityName.setTypeface(HiitApp.helveticaNeueCyrLight);
		mHighIntensityDuration.setTypeface(HiitApp.helveticaNeueCyrLight);
		
		mTxtLowIntensity.setTypeface(HiitApp.helveticaNeueCyrLight);
		mTxtLowIntensityName.setTypeface(HiitApp.helveticaNeueCyrThin);
		mTxtLowIntensityDuration.setTypeface(HiitApp.helveticaNeueCyrThin);
		mTxtLowIntensityColor.setTypeface(HiitApp.helveticaNeueCyrThin);
		mLowIntensityName.setTypeface(HiitApp.helveticaNeueCyrLight);
		mLowIntensityDuration.setTypeface(HiitApp.helveticaNeueCyrLight);
		
		mTxtCoolDown.setTypeface(HiitApp.helveticaNeueCyrLight);
		mTxtCoolDownName.setTypeface(HiitApp.helveticaNeueCyrThin);
		mTxtCoolDownDuration.setTypeface(HiitApp.helveticaNeueCyrThin);
		mTxtCoolDownColor.setTypeface(HiitApp.helveticaNeueCyrThin);
		mCoolDownName.setTypeface(HiitApp.helveticaNeueCyrLight);
		mCoolDownDuration.setTypeface(HiitApp.helveticaNeueCyrLight);
		
		mSaveThisTimer.setTypeface(HiitApp.helveticaNeueCyrThin);
		mSaveNewTimer.setTypeface(HiitApp.helveticaNeueCyrThin);
		mDeleteTimer.setTypeface(HiitApp.helveticaNeueCyrThin);
	}

	private void prepareData() {
		setContentView(R.layout.activity_creat_edit_timer);

		mCancel = (Button) findViewById(R.id.btnCancel);
		mTitle = (TextView) findViewById(R.id.txtTitle);
		mStart = (Button) findViewById(R.id.btnStart);
		mSaveThisTimer = (Button) findViewById(R.id.btnSaveThisTimer);
		mSaveNewTimer = (Button) findViewById(R.id.btnSaveNewTimer);
		mDeleteTimer = (Button) findViewById(R.id.btnDeleteTimer);
		mTxtTimerDetails = (TextView) findViewById(R.id.txtTimerDetails);
		mTxtTimerDetailsName = (TextView) findViewById(R.id.txtName);
		mTxtTimerDetailsSets = (TextView) findViewById(R.id.txtSets);
		mTimerDetailsName = (EditText) findViewById(R.id.edtTimerName);
		mTimerDetailsSets = (EditText) findViewById(R.id.edtTimerSets);
		mTxtWarmup = (TextView) findViewById(R.id.txtWarmup);
		mTxtWarmupName = (TextView) findViewById(R.id.txtWarmupTitle);
		mTxtWarmupDuration = (TextView) findViewById(R.id.txtWarmupDurationTitle);
		mTxtWarmupColor = (TextView) findViewById(R.id.txtWarmupColorTitle);
		mWarmupName = (EditText) findViewById(R.id.edtWarmupName);
		mWarmupDuration = (EditText) findViewById(R.id.edtWarmupDurationTime);
		mWarmupColor = (ImageView) findViewById(R.id.imgWarmupColor);
		mTxtHighIntensity = (TextView) findViewById(R.id.txtHighIntensity);
		mTxtHighIntensityName = (TextView) findViewById(R.id.txtHighIntensityTitle);
		mTxtHighIntensityDuration = (TextView) findViewById(R.id.txtHighIntensityDurationTitle);
		mTxtHighIntensityColor = (TextView) findViewById(R.id.txtHighIntensityColorTitle);
		mHighIntensityName = (EditText) findViewById(R.id.edtHighIntensityName);
		mHighIntensityDuration = (EditText) findViewById(R.id.edtHighIntensityDurationTime);
		mHighIntensityColor = (ImageView) findViewById(R.id.imgHighIntensityColor);
		mTxtLowIntensity = (TextView) findViewById(R.id.txtLowIntensity);
		mTxtLowIntensityName = (TextView) findViewById(R.id.txtLowIntensityTitle);
		mTxtLowIntensityDuration = (TextView) findViewById(R.id.txtLowIntensityDurationTitle);
		mTxtLowIntensityColor = (TextView) findViewById(R.id.txtLowIntensityColorTitle);
		mLowIntensityName = (EditText) findViewById(R.id.edtLowIntensityName);
		mLowIntensityDuration = (EditText) findViewById(R.id.edtLowIntensityDurationTime);
		mLowIntensityColor = (ImageView) findViewById(R.id.imgLowIntensityColor);
		mTxtCoolDown = (TextView) findViewById(R.id.txtCoolDown);
		mTxtCoolDownName = (TextView) findViewById(R.id.txtCoolDownTitle);
		mTxtCoolDownDuration = (TextView) findViewById(R.id.txtCoolDownDurationTitle);
		mTxtCoolDownColor = (TextView) findViewById(R.id.txtCoolDownColorTitle);
		mCoolDownName = (EditText) findViewById(R.id.edtCoolDownName);
		mCoolDownDuration = (EditText) findViewById(R.id.edtCoolDownDurationTime);
		mCoolDownColor = (ImageView) findViewById(R.id.imgCoolDownColor);
		mLayoutWarmupColor = (RelativeLayout) findViewById(R.id.layoutWarmupColor);
		mLayoutHighIntensityColor = (RelativeLayout) findViewById(R.id.layoutHighIntensityColor);
		mLayoutLowIntensityColor = (RelativeLayout) findViewById(R.id.layoutLowIntensityColor);
		mLayoutCoolDownColor = (RelativeLayout) findViewById(R.id.layoutCoolDownColor);
		
		setupUI(findViewById(R.id.create_timer_layout));

		String action = getIntent().getStringExtra(Params.ACTION);
		int timerId = getIntent().getIntExtra(Params.TIMER_ID, -1);
		if (action.equals(Params.CREATE_TIMER)) {
			mSaveThisTimer.setVisibility(View.VISIBLE);
			mDeleteTimer.setVisibility(View.GONE);
			mSaveNewTimer.setVisibility(View.GONE);
			mTimerDetailsName.setText("");
		} else {
			mSaveThisTimer.setVisibility(View.VISIBLE);
			mSaveNewTimer.setVisibility(View.VISIBLE);
			mDeleteTimer.setVisibility(View.VISIBLE);

			//loadSavedTimer(timerId);
		}

		CreateEditTimerActivityEventHandler eventHandler = new CreateEditTimerActivityEventHandler(this);
		eventHandler.setTimerId(timerId);
		eventHandler.setAction(action);
		mCancel.setOnClickListener(eventHandler);
		mStart.setOnClickListener(eventHandler);
		mSaveThisTimer.setOnClickListener(eventHandler);
		mDeleteTimer.setOnClickListener(eventHandler);
		mSaveNewTimer.setOnClickListener(eventHandler);
		mWarmupColor.setOnClickListener(eventHandler);
		mHighIntensityColor.setOnClickListener(eventHandler);
		mLowIntensityColor.setOnClickListener(eventHandler);
		mCoolDownColor.setOnClickListener(eventHandler);
		mWarmupDuration.addTextChangedListener(eventHandler);
		mHighIntensityDuration.addTextChangedListener(eventHandler);
		mLowIntensityDuration.addTextChangedListener(eventHandler);
		mCoolDownDuration.addTextChangedListener(eventHandler);
		mWarmupDuration.setOnFocusChangeListener(eventHandler);
		mHighIntensityDuration.setOnFocusChangeListener(eventHandler);
		mLowIntensityDuration.setOnFocusChangeListener(eventHandler);
		mCoolDownDuration.setOnFocusChangeListener(eventHandler);
		mLayoutWarmupColor.setOnClickListener(eventHandler);
		mLayoutHighIntensityColor.setOnClickListener(eventHandler);
		mLayoutLowIntensityColor.setOnClickListener(eventHandler);
		mLayoutCoolDownColor.setOnClickListener(eventHandler);
		
		HiitInputTextFocusListener focusListener = new HiitInputTextFocusListener();
		mTimerDetailsName.setOnFocusChangeListener(focusListener);
		mTimerDetailsSets.setOnFocusChangeListener(focusListener);
		mWarmupName.setOnFocusChangeListener(focusListener);
		mHighIntensityName.setOnFocusChangeListener(focusListener);
		mLowIntensityName.setOnFocusChangeListener(focusListener);
		mCoolDownName.setOnFocusChangeListener(focusListener);
		
		mTitle.setFocusableInTouchMode(true);
		mTitle.requestFocus();
	}
	
	public void setupUI(View view) {

	    //Set up touch listener for non-text box views to hide keyboard.
	    if(!(view instanceof EditText)) {

	        view.setOnTouchListener(new OnTouchListener() {

	            public boolean onTouch(View v, MotionEvent event) {
	                hideSoftKeyboard();
	                return false;
	            }

	        });
	    }

	    //If a layout container, iterate over children and seed recursion.
	    if (view instanceof ViewGroup) {

	        for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {

	            View innerView = ((ViewGroup) view).getChildAt(i);

	            setupUI(innerView);
	        }
	    }
	}
	
	public void hideSoftKeyboard() {
	    InputMethodManager inputMethodManager = (InputMethodManager)  getSystemService(Activity.INPUT_METHOD_SERVICE);
	    inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
	}
	
	private class HiitInputTextFocusListener implements OnFocusChangeListener {

		@Override
		public void onFocusChange(View v, boolean hasFocus) {
			if(hasFocus) {
				EditText currentFocusEditText = (EditText) v;
				currentFocusEditText.setSelection(currentFocusEditText.getText().length());
			}
		}
		
	}

	public Button getCancel() {
		return mCancel;
	}

	public TextView getScreenTitle() {
		return mTitle;
	}

	public Button getStart() {
		return mStart;
	}

	public Button getSaveThisTimer() {
		return mSaveThisTimer;
	}

	public Button getSaveNewTimer() {
		return mSaveNewTimer;
	}

	public TextView getTxtTimerDetails() {
		return mTxtTimerDetails;
	}

	public TextView getTxtTimerDetailsName() {
		return mTxtTimerDetailsName;
	}

	public TextView getTxtTimerDetailsSets() {
		return mTxtTimerDetailsSets;
	}

	public EditText getTimerDetailsName() {
		return mTimerDetailsName;
	}

	public EditText getTimerDetailsSets() {
		return mTimerDetailsSets;
	}

	public TextView getTxtWarmup() {
		return mTxtWarmup;
	}

	public TextView getTxtWarmupName() {
		return mTxtWarmupName;
	}

	public TextView getTxtWarmupDuration() {
		return mTxtWarmupDuration;
	}

	public TextView getTxtWarmupColor() {
		return mTxtWarmupColor;
	}

	public EditText getWarmupName() {
		return mWarmupName;
	}

	public EditText getWarmupDuration() {
		return mWarmupDuration;
	}

	public TextView getTxtHighIntensity() {
		return mTxtHighIntensity;
	}

	public TextView getTxtHighIntensityName() {
		return mTxtHighIntensityName;
	}

	public TextView getTxtHighIntensityDuration() {
		return mTxtHighIntensityDuration;
	}

	public TextView getTxtHighIntensityColor() {
		return mTxtHighIntensityColor;
	}

	public EditText getHighIntensityName() {
		return mHighIntensityName;
	}

	public EditText getHighIntensityDuration() {
		return mHighIntensityDuration;
	}

	public TextView getTxtLowIntensity() {
		return mTxtLowIntensity;
	}

	public TextView getTxtLowIntensityName() {
		return mTxtLowIntensityName;
	}

	public TextView getTxtLowIntensityDuration() {
		return mTxtLowIntensityDuration;
	}

	public TextView getTxtLowIntensityColor() {
		return mTxtLowIntensityColor;
	}

	public EditText getLowIntensityName() {
		return mLowIntensityName;
	}

	public EditText getLowIntensityDuration() {
		return mLowIntensityDuration;
	}

	public TextView getTxtCoolDown() {
		return mTxtCoolDown;
	}

	public TextView getTxtCoolDownName() {
		return mTxtCoolDownName;
	}

	public TextView getTxtCoolDownDuration() {
		return mTxtCoolDownDuration;
	}

	public TextView getTxtCoolDownColor() {
		return mTxtCoolDownColor;
	}

	public EditText getCoolDownName() {
		return mCoolDownName;
	}

	public EditText getCoolDownDuration() {
		return mCoolDownDuration;
	}

	public ImageView getWarmupColor() {
		return mWarmupColor;
	}

	public ImageView getHighIntensityColor() {
		return mHighIntensityColor;
	}

	public ImageView getLowIntensityColor() {
		return mLowIntensityColor;
	}

	public ImageView getCoolDownColor() {
		return mCoolDownColor;
	}

	public Button getDeleteTimer() {
		return mDeleteTimer;
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			HiitApp.currentWarmupColor = null;
			HiitApp.currentHighIntensityColor = null;
			HiitApp.currentLowIntensityColor = null;
			HiitApp.currentCoolDownColor = null;
			Intent intent = new Intent(this, MainActivity.class);
			startActivity(intent);
		}
		
		return true;
	}
}
