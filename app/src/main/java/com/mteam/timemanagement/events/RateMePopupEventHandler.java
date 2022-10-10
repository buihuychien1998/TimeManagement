package com.mteam.timemanagement.events;

import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.mteam.timemanagement.R;
import com.mteam.timemanagement.commons.Params;
import com.mteam.timemanagement.utils.HiitUtils;


public class RateMePopupEventHandler implements OnClickListener{

	Activity activity;
	
	Dialog dialog;
	
	public RateMePopupEventHandler(Dialog dialog, Activity activity) {
		this.activity = activity;
		this.dialog = dialog;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnRateNow:
			rate();
			break;

		case R.id.btnNotNow:
			cancel();
			break;

		case R.id.btnDontAskAgain:
			dontAsk();
			break;
		}
	}

	private void rate() {
		Intent intent = new Intent(Intent.ACTION_VIEW);
	    //Try Google play
	    intent.setData(Uri.parse("market://details?id=" + activity.getPackageName()));
	    if (startRateActivity(intent) == false) {
	        //Market (Google play) app seems not installed, let's try to open a webbrowser
	        intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=" + activity.getPackageName()));
	        if (startRateActivity(intent) == false) {
	            //Well if this also fails, we have run out of options, inform the user.
	            Toast.makeText(activity, "Could not open Google Play, please install the Google Play app.", Toast.LENGTH_SHORT).show();
	        }
	    }
		dialog.dismiss();
	}
	
	private boolean startRateActivity(Intent intent) {
	    try
	    {
	    	activity.startActivity(intent);
	        return true;
	    }
	    catch (ActivityNotFoundException e)
	    {
	        return false;
	    }
	}

	private void cancel() {
		dialog.dismiss();
	}
	
	private void dontAsk() {
		HiitUtils.saveData(activity, Params.RATE_DONT_ASK, "true");
		dialog.dismiss();
	}
}
