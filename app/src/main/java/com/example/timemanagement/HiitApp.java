package com.example.timemanagement;

import android.app.Application;
import android.graphics.Typeface;

import com.example.timemanagement.commons.HiitColor;
import com.example.timemanagement.dto.RunnableTimer;
import com.example.timemanagement.events.TimerActivityEventHandler;

public class HiitApp extends Application {
	
	public static HiitColor currentWarmupColor;
	
	public static HiitColor currentHighIntensityColor;
	
	public static HiitColor currentLowIntensityColor;
	
	public static HiitColor currentCoolDownColor;
	
	public static String currentWarmupName;
	
	public static String currentHighIntensityName;
	
	public static String currentLowIntensityName;
	
	public static String currentCoolDownName;
	
	public static String currentWarmupDuration;
	
	public static String currentHighIntensityDuration;
	
	public static String currentLowIntensityDuration;
	
	public static String currentCoolDownDuration;
	
	public static String currentTimerName;
	
	public static String currentTimerSets;
	
	public static RunnableTimer runnableTimer;
	
	public static int timerWidth, timerHeight;
	
	public static TimerActivityEventHandler timerEventHandler;
	
	public static boolean lockState;
	
	public static boolean startState;
	
	public static boolean isFinish;
	
	public static Typeface din1451Mittel, din1451Breit36, helveticaNeueCyrBlack, helveticaNeueCyrBlackItalic
						, helveticaNeueCyrBold, helveticaNeueCyrBoldItalic, helveticaNeueCyrItalic, helveticaNeueCyrLight
						, helveticaNeueCyrLightItalic, helveticaNeueCyrMedium, helveticaNeueCyrRoman, helveticaNeueCyrThin
						, helveticaNeueCyrThinItalic, helveticaNeueCyrUltraLight, helveticaNeueCyrUltraLightItalic;

	public static Typeface lobsterTwoBoldItalic;

	public static Typeface lobsterTwoItalic;

	public static Typeface lobsterTwoRegular;
	
	@Override
	public void onCreate() {
		super.onCreate();
		
//		DatabaseHelper databaseHelper = new DatabaseHelper(getApplicationContext());
//		databaseHelper.createTable(HiiTTimer.class, true);
		
		currentWarmupColor = HiitColor.YELLOW;
		currentHighIntensityColor = HiitColor.ORANGE;
		currentLowIntensityColor = HiitColor.PURPLE;
		currentCoolDownColor = HiitColor.DEFAULT;
		
		// initialize font face
		din1451Mittel = Typeface.createFromAsset(getApplicationContext().getAssets(),
				"fonts/DIN_1451_Std_Mittelschrift.ttf");
		din1451Breit36 = Typeface.createFromAsset(getApplicationContext().getAssets(),
				"fonts/DIN1451-36breit.ttf");
		helveticaNeueCyrBlack = Typeface.createFromAsset(getApplicationContext().getAssets(),
				"fonts/HelveticaNeueCyr-Black.otf");
		helveticaNeueCyrBlackItalic = Typeface.createFromAsset(getApplicationContext().getAssets(),
				"fonts/HelveticaNeueCyr-BlackItalic.otf");

		helveticaNeueCyrBold = Typeface.createFromAsset(getApplicationContext().getAssets(),
				"fonts/HelveticaNeueCyr-Bold.otf");
		helveticaNeueCyrBoldItalic = Typeface.createFromAsset(getApplicationContext().getAssets(),
				"fonts/HelveticaNeueCyr-BoldItalic.otf");
		helveticaNeueCyrItalic = Typeface.createFromAsset(getApplicationContext().getAssets(),
				"fonts/HelveticaNeueCyr-Italic.otf");
		helveticaNeueCyrLight = Typeface.createFromAsset(getApplicationContext().getAssets(),
				"fonts/HelveticaNeueCyr-Light.otf");
		
		helveticaNeueCyrLightItalic = Typeface.createFromAsset(getApplicationContext().getAssets(),
				"fonts/HelveticaNeueCyr-LightItalic.otf");
		helveticaNeueCyrMedium = Typeface.createFromAsset(getApplicationContext().getAssets(),
				"fonts/HelveticaNeueCyr-Medium.otf");
		helveticaNeueCyrRoman = Typeface.createFromAsset(getApplicationContext().getAssets(),
				"fonts/HelveticaNeueCyr-Roman.otf");
		helveticaNeueCyrThin = Typeface.createFromAsset(getApplicationContext().getAssets(),
				"fonts/HelveticaNeueCyr-Thin.otf");
		
		helveticaNeueCyrThinItalic = Typeface.createFromAsset(getApplicationContext().getAssets(),
				"fonts/HelveticaNeueCyr-ThinItalic.otf");
		helveticaNeueCyrUltraLight = Typeface.createFromAsset(getApplicationContext().getAssets(),
				"fonts/HelveticaNeueCyr-UltraLight.otf");
		helveticaNeueCyrUltraLightItalic = Typeface.createFromAsset(getApplicationContext().getAssets(),
				"fonts/HelveticaNeueCyr-UltraLightItalic.otf");
	}
}
