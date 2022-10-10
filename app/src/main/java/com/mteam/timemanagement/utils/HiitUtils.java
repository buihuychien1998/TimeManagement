package com.mteam.timemanagement.utils;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.res.Configuration;

public class HiitUtils {
	
	public static final String HIIT_PREFS = "Hiit";
	
	public static boolean isTablet(Activity activity) {
		int screenSize = activity.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK;

		boolean result = false;

		switch (screenSize) {
		case Configuration.SCREENLAYOUT_SIZE_XLARGE:
		case Configuration.SCREENLAYOUT_SIZE_LARGE:
			result = true;
			break;
		case Configuration.SCREENLAYOUT_SIZE_NORMAL:
		case Configuration.SCREENLAYOUT_SIZE_SMALL:
			result = false;
			break;
		}

		return result;
	}
	
	public static String getDisplayText(long hours, long minutes, long seconds) {
		String hoursStr = String.valueOf(hours);
		String minutesStr = String.valueOf(minutes);
		String secondsStr = String.valueOf(seconds);
		hoursStr = hoursStr.length() == 2 ? hoursStr : "0" + hoursStr; 
		minutesStr = minutesStr.length() == 2 ? minutesStr : "0" + minutesStr; 
		secondsStr = secondsStr.length() == 2 ? secondsStr : "0" + secondsStr;
		return hoursStr + ":" + minutesStr + ":" + secondsStr;
	}
	
	public static void saveData(Activity activity, String key, String data) {
		SharedPreferences settings = activity.getSharedPreferences(HIIT_PREFS, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString(key, data);
		editor.commit();
	}

	public static String loadData(Activity activity, String key) {
		SharedPreferences settings = activity.getSharedPreferences(HIIT_PREFS, 0);
	    String data = settings.getString(key, "");
	    return data;
	}
}
