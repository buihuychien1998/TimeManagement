package com.example.timemanagement.events;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.CountDownTimer;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.timemanagement.HiitApp;
import com.example.timemanagement.R;
import com.example.timemanagement.activity.MainActivity;
import com.example.timemanagement.activity.TimerActivity;
import com.example.timemanagement.commons.HiitColor;
import com.example.timemanagement.commons.Params;
import com.example.timemanagement.commons.TimerPhase;
import com.example.timemanagement.dto.RunnableTimer;
import com.example.timemanagement.utils.HiitUtils;

public class TimerActivityEventHandler extends BaseEventHandler implements OnClickListener {

	private final long DEFAULT_INTERVAL = 1000;

	private HiitCountDownTimer countDownTimer;

	private TimerActivity timerActivity;
	
	private boolean nextButtonStateBeforeLock;
	
	private boolean prevButtonStateBeforeLock;
	
	private float anglePerMiliSecond;
	
	private float sweepAngle;
	
	private MediaPlayer mediaPlayer;
	
	private long millisUntilFinishedTemp;
	
	public TimerActivityEventHandler(Activity activity) {
		super(activity);
		timerActivity = (TimerActivity) activity;
		mediaPlayer = new MediaPlayer();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnDone:
			done();
			break;
		case R.id.btnReset:
			reset();
			break;
		case R.id.btnPrevious:
			moveBack();
			break;
		case R.id.btnNext:
			moveNext(false);
			break;
		case R.id.btnLock:
			HiitApp.lockState = timerActivity.getLock().isChecked();
			lock();
			break;
		case R.id.btnStart:
			HiitApp.startState = timerActivity.getStart().isChecked();
			startCount(false);
			break;
		}
	}

	private void done() {
		if(timerActivity.getMainTimer().getText().toString().equals(timerActivity.getString(R.string.default_timer_time))) {
			Intent intent = new Intent(activity, MainActivity.class);
			intent.putExtra(Params.FROM_TIMER, "true");
			activity.startActivity(intent);
			HiitApp.isFinish = false;
			HiitApp.currentWarmupColor = null;
			HiitApp.currentHighIntensityColor = null;
			HiitApp.currentLowIntensityColor = null;
			HiitApp.currentCoolDownColor = null;
		} else {
			showDoneAlertBox();
		}
	}
	
	private void reset() {
		showResetAlertBox();
	}

	private void moveBack() {
		this.sweepAngle = 0;
		this.millisUntilFinishedTemp = 0;
		
		RunnableTimer runnableTimer = HiitApp.runnableTimer;
		
		HiitApp.isFinish = false;
		HiitApp.startState = timerActivity.getStart().isChecked();
		
		timerActivity.getImgTimer().setImageResource(android.R.color.transparent);
		
		if(countDownTimer != null) {
			countDownTimer.cancel();
		}
		
		timerActivity.getNext().setEnabled(true);
		timerActivity.getNext().setVisibility(View.VISIBLE);
		
		TimerPhase prevPhase = runnableTimer.getPreviousPhase();
		
		if(runnableTimer.getCurrentInterval() == 1 
				&& runnableTimer.getCurrentPhase().equals(TimerPhase.LOW_INTENSITY) 
				&& runnableTimer.getHiiTTimer().getWarmupDuration().equals(timerActivity.getString(R.string.default_timer_time))) {
			timerActivity.getPrevious().setEnabled(false);
			timerActivity.getPrevious().setVisibility(View.INVISIBLE);
		}
		
		if(prevPhase == TimerPhase.WARMUP) {
			timerActivity.getPrevious().setEnabled(false);
			timerActivity.getPrevious().setVisibility(View.INVISIBLE);
		}
		
		runnableTimer.setCurrentPhase(prevPhase);
		
		switch (runnableTimer.getCurrentPhase()) {
			case WARMUP:
				timerActivity.getScreenTitle().setText(runnableTimer.getHiiTTimer().getWarmupTitle());
				timerActivity.getRemainingTime().setText(runnableTimer.getHiiTTimer().getWarmupDuration());
				timerActivity.getElapsedTime().setText(timerActivity.getString(R.string.default_timer_time));
				timerActivity.getInterval().setText(timerActivity.getString(R.string.warmup_normal));
				timerActivity.getMainTimer().setText(runnableTimer.getCurrentMaxTimeText());
				break;
			case HIGH_INTENSITY:
				timerActivity.getScreenTitle().setText(runnableTimer.getHiiTTimer().getHighIntensityTitle());
				timerActivity.getRemainingTime().setText(runnableTimer.getHiiTTimer().getHighIntensityDuration());
				timerActivity.getElapsedTime().setText(timerActivity.getString(R.string.default_timer_time));
				timerActivity.getInterval().setText(timerActivity.getString(R.string.interval) + " " + runnableTimer.getCurrentInterval() + "/" + runnableTimer.getTotalInterval());
				timerActivity.getMainTimer().setText(runnableTimer.getCurrentMaxTimeText());
				break;
			case LOW_INTENSITY:
				timerActivity.getScreenTitle().setText(runnableTimer.getHiiTTimer().getLowIntensityTitle());
				timerActivity.getRemainingTime().setText(runnableTimer.getHiiTTimer().getLowIntensityDuration());
				timerActivity.getElapsedTime().setText(timerActivity.getString(R.string.default_timer_time));
				timerActivity.getInterval().setText(timerActivity.getString(R.string.interval) + " " + runnableTimer.getCurrentInterval() + "/" + runnableTimer.getTotalInterval());
				timerActivity.getMainTimer().setText(runnableTimer.getCurrentMaxTimeText());
				break;
			case COOLDOWN:
				break;
		}
		
		timerActivity.getProgressBar().setProgress(0);
		timerActivity.getProgressBar().setMax((int)runnableTimer.getCurrentMaxTime());
		timerActivity.getProgressBar().setProgress((int)runnableTimer.getCurrentMaxTime());
		timerActivity.setColorForProgressBar(runnableTimer);
		
		drawStartDot();
		
		startCount(true);
		
		timerActivity.getStart().setChecked(false);
	}

	public void moveNext(boolean isStop) {
		this.sweepAngle = 0;
		this.millisUntilFinishedTemp = 0;
		
		RunnableTimer runnableTimer = HiitApp.runnableTimer;
		
		timerActivity.getImgTimer().setImageResource(android.R.color.transparent);
		
		HiitApp.startState = timerActivity.getStart().isChecked();
		
		if(countDownTimer != null) {
			countDownTimer.cancel();
		}
		
		timerActivity.getPrevious().setEnabled(true);
		timerActivity.getPrevious().setVisibility(View.VISIBLE);
		
		TimerPhase nextPhase = runnableTimer.getNextPhase();
		
		if(runnableTimer.getCurrentInterval() == runnableTimer.getTotalInterval() 
				&& runnableTimer.getCurrentPhase().equals(TimerPhase.HIGH_INTENSITY) 
				&& runnableTimer.getHiiTTimer().getCoolDownDuration().equals(timerActivity.getString(R.string.default_timer_time))) {
			timerActivity.getNext().setEnabled(false);
			timerActivity.getNext().setVisibility(View.INVISIBLE);
		}
		
		if(nextPhase == TimerPhase.COOLDOWN 
				&& runnableTimer.getHiiTTimer().getCoolDownDuration().equals(timerActivity.getString(R.string.default_timer_time))) {
			return;
		}
		
		if(nextPhase == TimerPhase.COOLDOWN) {
			timerActivity.getNext().setEnabled(false);
			timerActivity.getNext().setVisibility(View.INVISIBLE);
		}
		
		runnableTimer.setCurrentPhase(nextPhase);
		
		switch (runnableTimer.getCurrentPhase()) {
		case WARMUP:
			break;
		case HIGH_INTENSITY:
			timerActivity.getScreenTitle().setText(runnableTimer.getHiiTTimer().getHighIntensityTitle());
			timerActivity.getRemainingTime().setText(runnableTimer.getHiiTTimer().getHighIntensityDuration());
			timerActivity.getElapsedTime().setText(timerActivity.getString(R.string.default_timer_time));
			timerActivity.getInterval().setText(timerActivity.getString(R.string.interval) + " " + runnableTimer.getCurrentInterval() + "/" + runnableTimer.getTotalInterval());
			timerActivity.getMainTimer().setText(runnableTimer.getCurrentMaxTimeText());
			break;
		case LOW_INTENSITY:
			timerActivity.getScreenTitle().setText(runnableTimer.getHiiTTimer().getLowIntensityTitle());
			timerActivity.getRemainingTime().setText(runnableTimer.getHiiTTimer().getLowIntensityDuration());
			timerActivity.getElapsedTime().setText(timerActivity.getString(R.string.default_timer_time));
			timerActivity.getInterval().setText(timerActivity.getString(R.string.interval) + " " + runnableTimer.getCurrentInterval() + "/" + runnableTimer.getTotalInterval());
			timerActivity.getMainTimer().setText(runnableTimer.getCurrentMaxTimeText());
			break;
		case COOLDOWN:
			timerActivity.getScreenTitle().setText(runnableTimer.getHiiTTimer().getCoolDownTitle());
			timerActivity.getRemainingTime().setText(runnableTimer.getHiiTTimer().getCoolDownDuration());
			timerActivity.getElapsedTime().setText(timerActivity.getString(R.string.default_timer_time));
			timerActivity.getInterval().setText(timerActivity.getString(R.string.cool_down_normal));
			timerActivity.getMainTimer().setText(runnableTimer.getCurrentMaxTimeText());
			break;
		}
		
		timerActivity.getProgressBar().setProgress(0);
		timerActivity.getProgressBar().setMax((int)runnableTimer.getCurrentMaxTime());
		timerActivity.getProgressBar().setProgress((int)runnableTimer.getCurrentMaxTime());
		timerActivity.setColorForProgressBar(runnableTimer);
		
		drawStartDot();
		
		if(isStop) {
			timerActivity.getStart().setChecked(true);
		} else {
			startCount(true);
			
			timerActivity.getStart().setChecked(false);
		}
	}

	public void lock() {
		ToggleButton lockButton = (ToggleButton) timerActivity.getLock();

		if (lockButton.isChecked()) {
			nextButtonStateBeforeLock = timerActivity.getNext().isEnabled();
			prevButtonStateBeforeLock = timerActivity.getPrevious().isEnabled();
			
			timerActivity.getReset().setEnabled(false);
			timerActivity.getDone().setEnabled(false);
			timerActivity.getPrevious().setEnabled(false);
			timerActivity.getNext().setEnabled(false);
			timerActivity.getStart().setEnabled(false);
		} else {
			timerActivity.getReset().setEnabled(true);
			timerActivity.getDone().setEnabled(true);
			timerActivity.getPrevious().setEnabled(prevButtonStateBeforeLock);
			timerActivity.getNext().setEnabled(nextButtonStateBeforeLock);
			timerActivity.getStart().setEnabled(true);
		}
	}
	
	public void keepLockState() {
		ToggleButton lockButton = (ToggleButton) timerActivity.getLock();

		if (lockButton.isChecked()) {
			timerActivity.getReset().setEnabled(false);
			timerActivity.getDone().setEnabled(false);
			timerActivity.getPrevious().setEnabled(false);
			timerActivity.getNext().setEnabled(false);
			timerActivity.getStart().setEnabled(false);
		} else {
			timerActivity.getReset().setEnabled(true);
			timerActivity.getDone().setEnabled(true);
			timerActivity.getPrevious().setEnabled(true);
			timerActivity.getNext().setEnabled(true);
			timerActivity.getStart().setEnabled(true);
		}
	}

	public void startCount(boolean isContinue) {
		if(HiitApp.timerWidth == 0 && HiitApp.timerHeight == 0) {
			HiitApp.timerWidth = (int) timerActivity.getResources().getDimension(R.dimen.timer_width);
			HiitApp.timerHeight = (int) timerActivity.getResources().getDimension(R.dimen.timer_height);
		}
		
		HiitApp.isFinish = false;
		
		RunnableTimer runnableTimer = HiitApp.runnableTimer;
		
		long maxtime = runnableTimer.getCurrentMaxTime();
		
		if(maxtime != 0) {
			anglePerMiliSecond = 360 / (float)(maxtime * 1000);
			
			ToggleButton startButton = (ToggleButton) timerActivity.getStart();
			TextView currentTimeTextView = timerActivity.getMainTimer();
			String currentTime = runnableTimer.getCurrentTime();
	
			String[] timeArr = currentTime.split(":");
			int timeInSeconds = Integer.parseInt(timeArr[0]) * 60 * 60 + Integer.parseInt(timeArr[1]) * 60 + Integer.parseInt(timeArr[2]);
			if (!startButton.isChecked() || isContinue) {
				if(countDownTimer != null) {
					countDownTimer.cancel();
				}
				
				long countDownTimerMilis = timeInSeconds * 1000;
				if(millisUntilFinishedTemp != 0) {
					countDownTimerMilis = millisUntilFinishedTemp;
				}
				
				countDownTimer = new HiitCountDownTimer(countDownTimerMilis, DEFAULT_INTERVAL - 999);
				countDownTimer.start();
			} else {
				countDownTimer.cancel();
				runnableTimer.setCurrentTime(currentTimeTextView.getText().toString());
				runnableTimer.setElapsedTimeString(timerActivity.getElapsedTime().getText().toString());
				runnableTimer.setCurrentProgress(timerActivity.getProgressBar().getProgress());
			}
		}
	}

	public class HiitCountDownTimer extends CountDownTimer {

		public HiitCountDownTimer(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
		}

		@Override
		public void onFinish() {
			RunnableTimer runnableTimer = HiitApp.runnableTimer;
			millisUntilFinishedTemp = 0;
			
			runnableTimer.setCurrentTime(runnableTimer.getCurrentMaxTimeText());
			timerActivity.getMainTimer().setText("00:00:00");
			timerActivity.getRemainingTime().setText("00:00:00");
			timerActivity.getElapsedTime().setText(runnableTimer.getCurrentMaxTimeText());
			timerActivity.getProgressBar().setProgress(0);
			HiitApp.isFinish = true;
			drawTimer(360);
			
			playSound(R.raw.takeoff);
			
			if(runnableTimer.getCurrentPhase().equals(TimerPhase.COOLDOWN) 
					|| (runnableTimer.getCurrentInterval() == runnableTimer.getTotalInterval() && runnableTimer.getHiiTTimer().getCoolDownDuration().equals(timerActivity.getString(R.string.default_time)))) {
				timerActivity.getStart().setChecked(true);
				HiitApp.startState = timerActivity.getStart().isChecked();
				return;
			}
			
			moveNext(false);
			timerActivity.getStart().setChecked(false);
			startCount(true);
			keepLockState();
		}

		@Override
		public void onTick(long millisUntilFinished) {
			millisUntilFinishedTemp = millisUntilFinished;
			
			RunnableTimer runnableTimer = HiitApp.runnableTimer;
			
			long hours = (millisUntilFinished / 1000) / (60 * 60);
			long minutes = (millisUntilFinished / 1000) % (60 * 60) / 60;
			long seconds = (millisUntilFinished / 1000) % (60 * 60) % 60;
			timerActivity.getMainTimer().setText(HiitUtils.getDisplayText(hours, minutes, seconds + 1));
			
			long maxTime = 0;
			long elapsedTime = 0, elapsedHours, elapsedMinutes, elapsedSeconds;
			String lastCountdown = timerActivity.getRemainingTime().getText().toString();
			switch (runnableTimer.getCurrentPhase()) {
			case WARMUP:
			case COOLDOWN:
				maxTime = runnableTimer.getCurrentMaxTime();
				
				elapsedTime = (maxTime * 1000) - millisUntilFinished;
				elapsedHours = (elapsedTime / 1000) / (60 * 60);
				elapsedMinutes = (elapsedTime / 1000) % (60 * 60) / 60;
				elapsedSeconds = (elapsedTime / 1000) % (60 * 60) % 60;
				
				timerActivity.getRemainingTime().setText(HiitUtils.getDisplayText(hours, minutes, seconds + 1));
				timerActivity.getElapsedTime().setText(HiitUtils.getDisplayText(elapsedHours, elapsedMinutes, elapsedSeconds));
				break;
			case HIGH_INTENSITY:
			case LOW_INTENSITY:
				maxTime = runnableTimer.getCurrentMaxTime();
				
				elapsedTime = (maxTime * 1000) - millisUntilFinished;
				elapsedHours = (elapsedTime / 1000) / (60 * 60);
				elapsedMinutes = (elapsedTime / 1000) % (60 * 60) / 60;
				elapsedSeconds = (elapsedTime / 1000) % (60 * 60) % 60;
				
				timerActivity.getRemainingTime().setText(HiitUtils.getDisplayText(hours, minutes, seconds + 1));
				timerActivity.getElapsedTime().setText(HiitUtils.getDisplayText(elapsedHours, elapsedMinutes, elapsedSeconds));
				break;
			}
			
			timerActivity.getProgressBar().setProgress((int) ((millisUntilFinished / 1000)) + 1);
			
			String currentCountdown = timerActivity.getRemainingTime().getText().toString();
			
			if(isCountdown(lastCountdown, currentCountdown)) {
				playSound(R.raw.countdown);
			}
			
			sweepAngle = (anglePerMiliSecond * elapsedTime) + anglePerMiliSecond;
			drawTimer(sweepAngle);
		}

	}
	
	public void drawStartDot() {
		RunnableTimer runnableTimer = HiitApp.runnableTimer;
		
    	Bitmap bmp = Bitmap.createBitmap(HiitApp.timerWidth, HiitApp.timerHeight, Config.ARGB_8888);
    	Canvas c = new Canvas(bmp);
    	
    	final RectF rect = new RectF();
    	float padding = timerActivity.getResources().getDimension(R.dimen.timer_padding) - 4;
        rect.set(padding, padding, bmp.getWidth() - padding, bmp.getHeight() - padding);

        Paint p = new Paint();
        int color;
        p.setStrokeWidth(timerActivity.getResources().getDimension(R.dimen.timer_stroke_width));
        p.setAntiAlias(true);
        p.setDither(true);
        p.setStyle(Paint.Style.STROKE);
        p.setStrokeJoin(Paint.Join.MITER);
        p.setStrokeCap(Paint.Cap.SQUARE);
        
        double centerX = HiitApp.timerWidth/2;
        double centerY = HiitApp.timerHeight/2;
        double startX = HiitApp.timerWidth - padding;
        double startY = HiitApp.timerHeight/2;
        double aX = startX - centerX;
        double aY = startY - centerY;
        
        double radians = Math.toRadians(sweepAngle); 
        double x = aX * Math.cos(radians) + aY * Math.sin(radians);
        double y = aY * Math.cos(radians) - aX * Math.sin(radians);
        
        double newX = centerX + x;
        double newY = centerY + y;
        
        color = timerActivity.getResources().getColor(R.color.timer_cycle_gray);
        p.setColor(color);
        c.drawCircle((float)centerX, (float)centerY, HiitApp.timerWidth/2 - padding, p);
        
        p.reset();
        p.setStrokeWidth(timerActivity.getResources().getDimension(R.dimen.timer_stroke_width));
        p.setAntiAlias(true);
        p.setDither(true);
        p.setStyle(Paint.Style.STROKE);
        p.setStrokeJoin(Paint.Join.MITER);
        p.setStrokeCap(Paint.Cap.SQUARE);
        
        padding = padding - 3;
        String timerColor = runnableTimer.getCurentColor().replace("#", "");
        timerColor = "#73" + timerColor;
        color = Color.parseColor(timerColor);
        p.setStyle(Paint.Style.FILL);
        p.setColor(color);
        float radius = padding;
        c.drawCircle((float)newX, (float)newY, radius, p);
        
        timerColor = runnableTimer.getCurentColor().replace("#", "");
        timerColor = "#BF" + timerColor;
        color = Color.parseColor(timerColor);
        p.setColor(color);
        radius = (radius * 3) / 5;
        c.drawCircle((float)newX, (float)newY, radius, p);
        
        //Create object of new Matrix.
        Matrix matrix = new Matrix();

        //set image rotation value to 90 degrees in matrix.
        matrix.postRotate(90);

        //Create bitmap with new values.
        Bitmap bMapRotate = Bitmap.createBitmap(bmp, 0, 0, HiitApp.timerWidth, HiitApp.timerWidth, matrix, true);

        //put rotated image in ImageView.
        timerActivity.getImgTimer().setImageBitmap(bMapRotate);
	}
	
	public void drawTimer(float sweepAngle) {
		RunnableTimer runnableTimer = HiitApp.runnableTimer;
		
    	Bitmap bmp = Bitmap.createBitmap(HiitApp.timerWidth, HiitApp.timerHeight, Config.ARGB_8888);
    	Canvas c = new Canvas(bmp);
    	
    	final RectF rect = new RectF();
    	float padding = timerActivity.getResources().getDimension(R.dimen.timer_padding) - 4;
        rect.set(padding, padding, bmp.getWidth() - padding, bmp.getHeight() - padding);

        Paint p = new Paint();
        int color;
        p.setStrokeWidth(timerActivity.getResources().getDimension(R.dimen.timer_stroke_width));
        p.setAntiAlias(true);
        p.setDither(true);
        p.setStyle(Paint.Style.STROKE);
        p.setStrokeJoin(Paint.Join.MITER);
        p.setStrokeCap(Paint.Cap.SQUARE);
        
        float anglePerSec = anglePerMiliSecond * 1000;
        float frequencyAngle = sweepAngle % anglePerSec;
        int factor = (int)(sweepAngle / anglePerSec) % 2;
        double centerX = HiitApp.timerWidth/2;
        double centerY = HiitApp.timerHeight/2;
        double startX = HiitApp.timerWidth - padding;
        double startY = HiitApp.timerHeight/2;
        double aX = startX - centerX;
        double aY = startY - centerY;
        
        double radians = Math.toRadians(sweepAngle); 
        double x = aX * Math.cos(radians) + aY * Math.sin(radians);
        double y = aY * Math.cos(radians) - aX * Math.sin(radians);
        
        double newX = centerX + x;
        double newY = centerY + y;
        
        color = timerActivity.getResources().getColor(R.color.timer_cycle_gray);
        p.setColor(color);
        c.drawCircle((float)centerX, (float)centerY, HiitApp.timerWidth/2 - padding, p);
        
        color = Color.parseColor(runnableTimer.getCurentColor());
        int secondColor = Color.parseColor(HiitColor.fromCode(runnableTimer.getCurentColor()).getSecondCode());
        int[] colors = {secondColor, color}; 
        SweepGradient gradient = new SweepGradient(HiitApp.timerWidth/2, HiitApp.timerHeight/2, colors , null);

        p.setShader(gradient);
        p.setColor(color);
        c.drawArc(rect, 0, -sweepAngle, false, p);
        
        p.reset();
        p.setStrokeWidth(timerActivity.getResources().getDimension(R.dimen.timer_stroke_width));
        p.setAntiAlias(true);
        p.setDither(true);
        p.setStyle(Paint.Style.STROKE);
        p.setStrokeJoin(Paint.Join.MITER);
        p.setStrokeCap(Paint.Cap.SQUARE);
        
        padding = padding - 3;
        String timerColor = runnableTimer.getCurentColor().replace("#", "");
        timerColor = "#73" + timerColor;
        color = Color.parseColor(timerColor);
        p.setStyle(Paint.Style.FILL);
        p.setColor(color);
        float radius;
        if(factor != 0) {
        	radius = padding/2 + ((padding / (anglePerSec * 2)) * frequencyAngle);
        } else {
        	radius = padding - ((padding / (anglePerSec * 2)) * frequencyAngle);
        }
        c.drawCircle((float)newX, (float)newY, radius, p);
        
        timerColor = runnableTimer.getCurentColor().replace("#", "");
        timerColor = "#BF" + timerColor;
        color = Color.parseColor(timerColor);
        p.setColor(color);
        radius = (radius * 3) / 5;
        c.drawCircle((float)newX, (float)newY, radius, p);
        
        //Create object of new Matrix.
        Matrix matrix = new Matrix();

        //set image rotation value to 90 degrees in matrix.
        matrix.postRotate(90);

        //Create bitmap with new values.
        Bitmap bMapRotate = Bitmap.createBitmap(bmp, 0, 0, HiitApp.timerWidth, HiitApp.timerWidth, matrix, true);

        //put rotated image in ImageView.
        timerActivity.getImgTimer().setImageBitmap(bMapRotate);
	}
	
	private boolean isCountdown(String lastCountdown, String currentCountdown) {
		if(lastCountdown.equals("00:00:06") && currentCountdown.equals("00:00:05")
				|| lastCountdown.equals("00:00:05") && currentCountdown.equals("00:00:04")
				|| lastCountdown.equals("00:00:04") && currentCountdown.equals("00:00:03")
				|| lastCountdown.equals("00:00:03") && currentCountdown.equals("00:00:02")
				|| lastCountdown.equals("00:00:02") && currentCountdown.equals("00:00:01")) {
			return true;
		}
		return false;
	}
	
	private void playSound(int soundId) {
		mediaPlayer = MediaPlayer.create(timerActivity, soundId);
		mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
		mediaPlayer.setLooping(false);
		mediaPlayer.start();
		mediaPlayer.setOnCompletionListener(new OnCompletionListener() {
			
			@Override
			public void onCompletion(MediaPlayer mp) {
				mediaPlayer.release();
			}
			
		});
	}
	
	private void showResetAlertBox() {
		final RunnableTimer runnableTimer = HiitApp.runnableTimer;
		
		AlertDialog.Builder builder = new AlertDialog.Builder(activity);
		builder.setMessage(activity.getResources().getString(R.string.reset_alert)).setCancelable(false)
				.setPositiveButton(activity.getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						millisUntilFinishedTemp = 0;
						timerActivity.getImgTimer().setImageResource(android.R.color.transparent);
						
						if(!runnableTimer.getHiiTTimer().getWarmupDuration().equals(timerActivity.getString(R.string.default_timer_time))) {
							runnableTimer.setCurrentTime(runnableTimer.getHiiTTimer().getWarmupDuration());
							runnableTimer.setCurrentMaxTime(runnableTimer.getWarmupTime());
							runnableTimer.setCurrentInterval(1);
							runnableTimer.setCurrentPhase(TimerPhase.WARMUP);
							timerActivity.getScreenTitle().setText(runnableTimer.getHiiTTimer().getWarmupTitle());
							timerActivity.getMainTimer().setText(runnableTimer.getCurrentTime());
							timerActivity.getProgressBar().setMax((int)runnableTimer.getCurrentMaxTime());
							timerActivity.getProgressBar().setProgress((int)runnableTimer.getCurrentMaxTime());
							timerActivity.getRemainingTime().setText(runnableTimer.getCurrentMaxTimeText());
							timerActivity.getElapsedTime().setText(R.string.default_timer_time);
							timerActivity.getInterval().setText(timerActivity.getString(R.string.warmup_normal));
							timerActivity.getPrevious().setEnabled(false);
							timerActivity.getPrevious().setVisibility(View.INVISIBLE);
							timerActivity.getNext().setEnabled(true);
							timerActivity.getNext().setVisibility(View.VISIBLE);
							timerActivity.setColorForProgressBar(runnableTimer);
						} else {
							runnableTimer.setCurrentPhase(TimerPhase.WARMUP);
							runnableTimer.setCurrentInterval(1);
							moveNext(false);
							timerActivity.getPrevious().setEnabled(false);
							timerActivity.getPrevious().setVisibility(View.INVISIBLE);
						}
						
						if(countDownTimer != null) {
							countDownTimer.cancel();
						}
						
						startCount(true);
						timerActivity.getStart().setChecked(false);
						HiitApp.startState = false;
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
	
	private void showDoneAlertBox() {
		AlertDialog.Builder builder = new AlertDialog.Builder(activity);
		builder.setMessage(activity.getResources().getString(R.string.done_alert)).setCancelable(false)
				.setPositiveButton(activity.getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						Intent intent = new Intent(activity, MainActivity.class);
						intent.putExtra(Params.FROM_TIMER, "true");
						activity.startActivity(intent);
						HiitApp.isFinish = false;
						HiitApp.currentWarmupColor = null;
						HiitApp.currentHighIntensityColor = null;
						HiitApp.currentLowIntensityColor = null;
						HiitApp.currentCoolDownColor = null;
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

	public HiitCountDownTimer getCountDownTimer() {
		return countDownTimer;
	}

	public void setCountDownTimer(HiitCountDownTimer countDownTimer) {
		this.countDownTimer = countDownTimer;
	}

	public TimerActivity getTimerActivity() {
		return timerActivity;
	}

	public void setTimerActivity(TimerActivity timerActivity) {
		this.timerActivity = timerActivity;
	}

	public MediaPlayer getMediaPlayer() {
		return mediaPlayer;
	}

	public void setMediaPlayer(MediaPlayer mediaPlayer) {
		this.mediaPlayer = mediaPlayer;
	}

	public float getSweepAngle() {
		return sweepAngle;
	}

	public void setSweepAngle(float sweepAngle) {
		this.sweepAngle = sweepAngle;
	}

	public float getAnglePerMiliSecond() {
		return anglePerMiliSecond;
	}

	public void setAnglePerMiliSecond(float anglePerMiliSecond) {
		this.anglePerMiliSecond = anglePerMiliSecond;
	}
}
