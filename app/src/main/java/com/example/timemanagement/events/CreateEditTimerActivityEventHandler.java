package com.example.timemanagement.events;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.EditText;
import android.widget.TextView;

import com.example.timemanagement.HiitApp;
import com.example.timemanagement.R;
import com.example.timemanagement.activity.ColorOptionsActivity;
import com.example.timemanagement.activity.CreateEditTimerActivity;
import com.example.timemanagement.activity.MainActivity;
import com.example.timemanagement.activity.TimerActivity;
import com.example.timemanagement.commons.Params;
import com.example.timemanagement.dto.HiiTTimer;


public class CreateEditTimerActivityEventHandler extends BaseEventHandler implements OnClickListener, TextWatcher, OnFocusChangeListener {

	private Integer timerId = null;
	
	private String action;
	
	private String enteredStr = "";
	
	private EditText currentFocusEditText;
	
	private String result = "";
	
	public CreateEditTimerActivityEventHandler(Activity activity) {
		super(activity);
	}

	@Override
	public void onClick(View v) {
		CreateEditTimerActivity creatEditTimerActivity = (CreateEditTimerActivity) activity;
		switch (v.getId()) {
		case R.id.btnCancel:
			cancel();
			break;

		case R.id.btnStart:
			start();
			break;

		case R.id.btnSaveThisTimer:
			saveThisTimer();
			break;

		case R.id.btnSaveNewTimer:
			saveNewTimer();
			break;
			
		case R.id.btnDeleteTimer:
			deleteThisTimer();
			break;
			
		case R.id.imgWarmupColor:
		case R.id.imgHighIntensityColor:
		case R.id.imgLowIntensityColor:
		case R.id.imgCoolDownColor:
			chooseColor(v);
			break;
			
		case R.id.layoutWarmupColor:
			creatEditTimerActivity.getWarmupColor().performClick();
			break;
			
		case R.id.layoutHighIntensityColor:
			creatEditTimerActivity.getHighIntensityColor().performClick();
			break;
			
		case R.id.layoutLowIntensityColor:
			creatEditTimerActivity.getLowIntensityColor().performClick();
			break;
			
		case R.id.layoutCoolDownColor:
			creatEditTimerActivity.getCoolDownColor().performClick();
			break;
		}
	}

	private void cancel() {
		HiitApp.currentWarmupColor = null;
		HiitApp.currentHighIntensityColor = null;
		HiitApp.currentLowIntensityColor = null;
		HiitApp.currentCoolDownColor = null;
		activity.finish();
	}

	private void start() {
		CreateEditTimerActivity creatEditTimerActivity = (CreateEditTimerActivity) activity;
		Intent intent = new Intent(activity, TimerActivity.class);
		
		HiiTTimer hiiTTimer;
		if (action.equals(Params.CREATE_TIMER)) {
			hiiTTimer = getTimerOnScreen(creatEditTimerActivity, null);
		} else {
			hiiTTimer = getTimerOnScreen(creatEditTimerActivity, timerId);
		}
		
		if(hiiTTimer == null) {
			showAlertBox(R.string.zero_alert);
			return;
		}
		
		String durationWarmup = hiiTTimer.getWarmupDuration();
		String durationHighIntensity = hiiTTimer.getHighIntensityDuration();
		String durationLowIntensity = hiiTTimer.getLowIntensityDuration();
		String durationCoolDown = hiiTTimer.getCoolDownDuration();
		
		if(!checkTimeValid(durationWarmup)) {
			return;
		}
		
		if(hiiTTimer.getHighIntensityDuration().equals(activity.getString(R.string.default_timer_time))) {
			showAlertBox(R.string.time_required_alert);
			return;
		}
		
		if(!checkTimeValid(durationHighIntensity)) {
			return;
		}
		
		if(hiiTTimer.getLowIntensityDuration().equals(activity.getString(R.string.default_timer_time))) {
			showAlertBox(R.string.time_required_alert);
			return;
		}
		
		if(!checkTimeValid(durationLowIntensity)) {
			return;
		}
		
		if(!checkTimeValid(durationCoolDown)) {
			return;
		}
		
		intent.putExtra(Params.TIMER, hiiTTimer);
		activity.startActivity(intent);
	}
	
	private void chooseColor(View v) {
		Intent intent = new Intent(activity, ColorOptionsActivity.class);
		intent.putExtra(Params.COLOR, v.getTag().toString());
		activity.startActivityForResult(intent, v.getId());
	}

	private void saveThisTimer() {
		CreateEditTimerActivity creatEditTimerActivity = (CreateEditTimerActivity) activity;
		
		HiiTTimer hiiTTimer;
		if (action.equals(Params.CREATE_TIMER)) {
			hiiTTimer = getTimerOnScreen(creatEditTimerActivity, null);
		} else {
			hiiTTimer = getTimerOnScreen(creatEditTimerActivity, timerId);
		}
		
		if(hiiTTimer == null) {
			showAlertBox(R.string.zero_alert);
			return;
		}
		
		String durationWarmup = hiiTTimer.getWarmupDuration();
		String durationHighIntensity = hiiTTimer.getHighIntensityDuration();
		String durationLowIntensity = hiiTTimer.getLowIntensityDuration();
		String durationCoolDown = hiiTTimer.getCoolDownDuration();
		
		if(hiiTTimer.getName().equals("")) {
			showAlertBox(R.string.no_name_alert);
			return;
		}
		
		if(!hiiTTimer.getWarmupDuration().equals(activity.getString(R.string.default_timer_time)) 
				&& hiiTTimer.getWarmupTitle().equals("")) {
			showAlertBox(R.string.warmup_title_alert);
			return;
		}
		
		if(!checkTimeValid(durationWarmup)) {
			return;
		}
		
		if(hiiTTimer.getHighIntensityTitle().equals("")) {
			showAlertBox(R.string.high_title_alert);
			return;
		}
		
		if(hiiTTimer.getHighIntensityDuration().equals(activity.getString(R.string.default_timer_time))) {
			showAlertBox(R.string.time_required_alert);
			return;
		}
		
		if(!checkTimeValid(durationHighIntensity)) {
			return;
		}
		
		if(hiiTTimer.getLowIntensityTitle().equals("")) {
			showAlertBox(R.string.low_title_alert);
			return;
		}
		
		if(hiiTTimer.getLowIntensityDuration().equals(activity.getString(R.string.default_timer_time))) {
			showAlertBox(R.string.time_required_alert);
			return;
		}
		
		if(!checkTimeValid(durationLowIntensity)) {
			return;
		}
		
		if(!hiiTTimer.getCoolDownDuration().equals(activity.getString(R.string.default_timer_time)) 
				&& hiiTTimer.getCoolDownTitle().equals("")) {
			showAlertBox(R.string.cooldown_title_alert);
			return;
		}
		
		if(!checkTimeValid(durationCoolDown)) {
			return;
		}
		
//		DatabaseHelper databaseHelper = new DatabaseHelper(activity);
//		if(databaseHelper.update(hiiTTimer) == 0) {
//			databaseHelper.insert(hiiTTimer);
//		}
		showAlertBox(R.string.saved_alert);
	}

	private void saveNewTimer() {
		CreateEditTimerActivity creatEditTimerActivity = (CreateEditTimerActivity) activity;
		HiiTTimer hiiTTimer = getTimerOnScreen(creatEditTimerActivity, null);
		
		if(hiiTTimer == null) {
			showAlertBox(R.string.zero_alert);
			return;
		}
		
		String durationWarmup = hiiTTimer.getWarmupDuration();
		String durationHighIntensity = hiiTTimer.getHighIntensityDuration();
		String durationLowIntensity = hiiTTimer.getLowIntensityDuration();
		String durationCoolDown = hiiTTimer.getCoolDownDuration();
		
		if(hiiTTimer.getName().equals("")) {
			showAlertBox(R.string.no_name_alert);
			return;
		}
		
		if(!hiiTTimer.getWarmupDuration().equals(activity.getString(R.string.default_timer_time)) 
				&& hiiTTimer.getWarmupTitle().equals("")) {
			showAlertBox(R.string.warmup_title_alert);
			return;
		}
		
		if(!checkTimeValid(durationWarmup)) {
			return;
		}
		
		if(hiiTTimer.getHighIntensityTitle().equals("")) {
			showAlertBox(R.string.high_title_alert);
			return;
		}
		
		if(hiiTTimer.getHighIntensityDuration().equals(activity.getString(R.string.default_timer_time))) {
			showAlertBox(R.string.time_required_alert);
			return;
		}
		
		if(!checkTimeValid(durationHighIntensity)) {
			return;
		}
		
		if(hiiTTimer.getLowIntensityTitle().equals("")) {
			showAlertBox(R.string.low_title_alert);
			return;
		}
		
		if(hiiTTimer.getLowIntensityDuration().equals(activity.getString(R.string.default_timer_time))) {
			showAlertBox(R.string.time_required_alert);
			return;
		}
		
		if(!checkTimeValid(durationLowIntensity)) {
			return;
		}
		
		if(!hiiTTimer.getCoolDownDuration().equals(activity.getString(R.string.default_timer_time)) 
				&& hiiTTimer.getCoolDownTitle().equals("")) {
			showAlertBox(R.string.cooldown_title_alert);
			return;
		}
		
		if(!checkTimeValid(durationCoolDown)) {
			return;
		}
		
//		DatabaseHelper databaseHelper = new DatabaseHelper(activity);
//		databaseHelper.insert(hiiTTimer);
		showAlertBox(R.string.saved_alert);
	}
	
	private void deleteThisTimer() {
		showDeleteAlertBox();
	}
	
	private boolean checkTimeValid(String time) {
		String[] timeArr = time.split(":");
		int minutes = Integer.valueOf(timeArr[1]);
		int seconds = Integer.valueOf(timeArr[2]);
		if(minutes > 59) {
			showAlertBox(R.string.over_59_minutes_alert);
			return false;
		} else if(seconds > 59) {
			showAlertBox(R.string.over_59_seconds_alert);
			return false;
		}
		return true;
	}
	
	private HiiTTimer getTimerOnScreen(CreateEditTimerActivity creatEditTimerActivity, Integer id) {
		HiiTTimer hiiTTimer = new HiiTTimer();
		hiiTTimer.setId(id);
		hiiTTimer.setName(creatEditTimerActivity.getTimerDetailsName().getText().toString());
		
		if(creatEditTimerActivity.getTimerDetailsSets().getText().toString().equals("0")
				|| creatEditTimerActivity.getTimerDetailsSets().getText().toString().equals("")) {
			return null;
		}
		
		hiiTTimer.setSets(Integer.parseInt(creatEditTimerActivity.getTimerDetailsSets().getText().toString()));
		
		hiiTTimer.setWarmupTitle(creatEditTimerActivity.getWarmupName().getText().toString());
		hiiTTimer.setWarmupDuration(creatEditTimerActivity.getWarmupDuration().getText().toString());
		hiiTTimer.setWarmupColor(creatEditTimerActivity.getWarmupColor().getTag().toString());
		
		hiiTTimer.setHighIntensityTitle(creatEditTimerActivity.getHighIntensityName().getText().toString());
		hiiTTimer.setHighIntensityDuration(creatEditTimerActivity.getHighIntensityDuration().getText().toString());
		hiiTTimer.setHighIntensityColor(creatEditTimerActivity.getHighIntensityColor().getTag().toString());
		
		hiiTTimer.setLowIntensityTitle(creatEditTimerActivity.getLowIntensityName().getText().toString());
		hiiTTimer.setLowIntensityDuration(creatEditTimerActivity.getLowIntensityDuration().getText().toString());
		hiiTTimer.setLowIntensityColor(creatEditTimerActivity.getLowIntensityColor().getTag().toString());
		
		hiiTTimer.setCoolDownTitle(creatEditTimerActivity.getCoolDownName().getText().toString());
		hiiTTimer.setCoolDownDuration(creatEditTimerActivity.getCoolDownDuration().getText().toString());
		hiiTTimer.setCoolDownColor(creatEditTimerActivity.getCoolDownColor().getTag().toString());
		
		return hiiTTimer;
	}
	
	private void showAlertBox(int messageId) {
		AlertDialog.Builder builder = new AlertDialog.Builder(activity);
		builder.setMessage(activity.getResources().getString(messageId))
		       .setCancelable(false)
		       .setPositiveButton(activity.getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		        	   dialog.dismiss();
		           }
		       });
		AlertDialog alertDialog = builder.create();
		alertDialog.show();
		TextView messageText = (TextView)alertDialog.findViewById(android.R.id.message);
		messageText.setGravity(Gravity.CENTER);
	}
	
	private void showDeleteAlertBox() {
		CreateEditTimerActivity creatEditTimerActivity = (CreateEditTimerActivity) activity;
		final HiiTTimer hiiTTimer = getTimerOnScreen(creatEditTimerActivity, timerId);
		String alertMessage = String.format(activity.getResources().getString(R.string.delete_alert), hiiTTimer.getName());
		
		AlertDialog.Builder builder = new AlertDialog.Builder(activity);
		builder.setMessage(alertMessage).setCancelable(false)
				.setPositiveButton(activity.getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
//						DatabaseHelper databaseHelper = new DatabaseHelper(activity);
//						databaseHelper.delete(hiiTTimer);
//
						Intent intent = new Intent(activity, MainActivity.class);
						activity.startActivity(intent);
						
						dialog.dismiss();
					}
				}).setNegativeButton(activity.getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
		AlertDialog alertDialog = builder.create();
		alertDialog.show();
		TextView messageText = (TextView) alertDialog.findViewById(android.R.id.message);
		messageText.setGravity(Gravity.CENTER);
	}
	
	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		if(hasFocus) {
			currentFocusEditText = (EditText) v;
		} else {
			String duration = currentFocusEditText.getText().toString();
			String[] timeArr = duration.split(":");
			int minutes = Integer.valueOf(timeArr[1]);
			int seconds = Integer.valueOf(timeArr[2]);
			if(minutes > 59) {
				showAlertBox(R.string.over_59_minutes_alert);
			} else if(seconds > 59) {
				showAlertBox(R.string.over_59_seconds_alert);
			}
		}
		enteredStr = currentFocusEditText.getText().toString().replaceAll(":", "").replaceFirst("^0+(?!$)", "");
		if(enteredStr.equals("0")) {
			enteredStr = "";
		}
	}

	@Override
	public void afterTextChanged(Editable s) {
		if(!(s.toString()).equals(result)) {
			if(enteredStr.length() == 1) {
				result = "00:00:0" + enteredStr;
			} else if(enteredStr.length() == 2) {
				result = "00:00:" + enteredStr;
			} else if(enteredStr.length() == 3) {
				result = "00:0" + String.valueOf(enteredStr.charAt(0)) 
						+ ":" + String.valueOf(enteredStr.charAt(1)) + String.valueOf(enteredStr.charAt(2));
			} else if(enteredStr.length() == 4) {
				result = "00:" + String.valueOf(enteredStr.charAt(0)) + String.valueOf(enteredStr.charAt(1)) 
						+ ":" + String.valueOf(enteredStr.charAt(2)) + String.valueOf(enteredStr.charAt(3));
			} else if(enteredStr.length() == 5) {
				result = "0" + String.valueOf(enteredStr.charAt(0)) 
						+ ":" + String.valueOf(enteredStr.charAt(1)) + String.valueOf(enteredStr.charAt(2)) 
						+ ":" + String.valueOf(enteredStr.charAt(3)) + String.valueOf(enteredStr.charAt(4));
			} else if(enteredStr.length() == 6) {
				result = String.valueOf(enteredStr.charAt(0)) + String.valueOf(enteredStr.charAt(1)) 
						+ ":" + String.valueOf(enteredStr.charAt(2)) + String.valueOf(enteredStr.charAt(3)) 
						+ ":" + String.valueOf(enteredStr.charAt(4)) + String.valueOf(enteredStr.charAt(5));
			} else {
				result = "00:00:00";
			}
			
			if(currentFocusEditText != null) {
				currentFocusEditText.setText(result);
			}
		}
		
		if(currentFocusEditText != null) {
			currentFocusEditText.setSelection(currentFocusEditText.getText().length());
		}
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count, int after) {
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		if(enteredStr.length() == 6 && s.length() > 8) {
			return;
		}
		if(!(s.toString()).equals(result)) {
			if(s.length() < 8) {
				if(!enteredStr.equals("")) {
					enteredStr = enteredStr.substring(0, enteredStr.length() - 1);
				}
			} else {
				enteredStr += String.valueOf(s.charAt(start));
			}
		}
	}

	public int getTimerId() {
		return timerId;
	}

	public void setTimerId(int timerId) {
		this.timerId = timerId;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}
}
