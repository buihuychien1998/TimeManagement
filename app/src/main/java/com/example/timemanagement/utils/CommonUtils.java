package com.example.timemanagement.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Paint;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.UnderlineSpan;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class CommonUtils {
	public static final String GORILLA_PREFS = "GorillaFitness";

	public static final String LOG_KEY = "message_data";

	public static void savePopupReview(Context context, boolean vale) {
		SharedPreferences prefs = getPrefs(context);
		prefs.edit().putBoolean("firstworkout_complete", vale).apply();
	}

	public static SharedPreferences getPrefs(Context context) {
		return context.getSharedPreferences(GORILLA_PREFS, Context.MODE_PRIVATE);
	}

	public static String getAllMessages(Context context) {
		return getPrefs(context).getString(LOG_KEY, "");
	}

	public static boolean isShowPopupReview(Context context) {
		return getPrefs(context).getBoolean("firstworkout_complete", true);
	}

	public static String getBasicText(String input) {
		String tmp = input.replace(" ", "");
		tmp = tmp.replace("-", "");

		return tmp;
	}

	public static int getId(String seed, @SuppressWarnings("rawtypes") Class type) throws SecurityException,
			NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
		Field field = type.getField(seed);

		int id = field.getInt(null);

		return id;
	}

	public static void saveData(Activity activity, String key, String data) {
		SharedPreferences settings = activity.getSharedPreferences(GORILLA_PREFS, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString(key, data);
		editor.commit();
	}

	public static String loadData(Context context, String key) {
		SharedPreferences settings = context.getSharedPreferences(GORILLA_PREFS, 0);
		String data = settings.getString(key, "");
		return data;
	}


	public static SpannableStringBuilder formatContent(String source) {
		String[] contents = source.split("<br/>");

		SpannableStringBuilder builder = new SpannableStringBuilder();
		int firstBlank = 0;
		for (String line : contents) {
			line = line.trim();

			firstBlank = line.indexOf(" ");

			line = line.substring(firstBlank + 1, line.length()).trim() + "\n";
			SpannableString content = new SpannableString(line);
			content.setSpan(new UnderlineSpan(), 0, line.length() - 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			builder.append(content);
		}

		return builder;
	}

	public static String formatContent1(String source) {
		String[] contents = source.split("<br/>");

		StringBuilder builder = new StringBuilder();
		int firstBlank = 0;
		for (String line : contents) {
			line = line.trim();

			firstBlank = line.indexOf(" ");

			line = line.substring(0, firstBlank).trim() + "\n";
			builder.append(line);
		}

		return builder.toString();
	}

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

	public static ArrayList<String> formatAlignString(String input, int maximum) {
		String[] temp = input.split(" ");

		int length = 0;

		ArrayList<String> result = new ArrayList<String>();

		String line = "";
		String preline = "";
		for (int i = 0; i < temp.length; i++) {
			length += temp[i].length();

			preline = line;
			line += temp[i] + " ";

			if (i > 0) {
				length++;
			}

			if (length > maximum) {
				length = 0;
				result.add(preline.trim());
				i--;
				line = "";
				preline = "";
				continue;
			}
		}

		return result;
	}

	public static ArrayList<String> breakLongLink(String input, double textWidth, Paint textPaint) {
		String[] temp = input.split(" ");

		double lineWidth = 0;

		ArrayList<String> result = new ArrayList<String>();

		String line = "";
		String preline = "";
		boolean isFirstLine = true;
		for (int i = 0; i < temp.length; i++) {
			preline = line;
			line += temp[i] + " ";

			lineWidth += textPaint.measureText(temp[i]);

			if (lineWidth > textWidth) {
				lineWidth = 0;

				String test = preline.trim();
				if (!isFirstLine) {
					test = "     " + preline.trim();
				}

				if (textPaint.measureText(test) > textWidth) {
					result.add(preline.trim().substring(0, preline.trim().lastIndexOf(" ")));
					i = i - 2;
				} else {
					result.add(preline.trim());
					i--;
				}

				isFirstLine = false;
				line = "";
				preline = "";
				continue;
			}
		}
		if (line.length() != 0) {
			result.add(line.trim());
		}
		return result;
	}




}
