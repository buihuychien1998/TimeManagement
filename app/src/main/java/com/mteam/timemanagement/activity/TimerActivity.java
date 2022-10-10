package com.mteam.timemanagement.activity;

import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.mteam.timemanagement.R;
import com.mteam.timemanagement.HiitApp;
import com.mteam.timemanagement.commons.HiitColor;
import com.mteam.timemanagement.commons.Params;
import com.mteam.timemanagement.commons.TimerPhase;
import com.mteam.timemanagement.dto.HiiTTimer;
import com.mteam.timemanagement.dto.RunnableTimer;
import com.mteam.timemanagement.events.TimerActivityEventHandler;

public class TimerActivity extends Activity {
	
	private Button mDone;

	private Button mReset;
	
	private Button mPrevious;

	private Button mNext;
	
	private ToggleButton mLock;

	private ToggleButton mStart;
	
	private TextView mTitle;

	private TextView mMainTimer;
	
	private TextView mInterval;
	
	private TextView mElapsed;
	
	private TextView mRemaining;

	private TextView mElapsedTime;
	
	private TextView mRemainingTime;
	
	private ImageView mImgTimer;
	
	private ProgressBar mProgressBar;
	
	private TimerActivityEventHandler eventHandler;
	
	private boolean isFromBackground;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		prepareData();
		
		HiiTTimer hiiTTimer = (HiiTTimer) getIntent().getSerializableExtra(Params.TIMER);
		RunnableTimer runnableTimer = new RunnableTimer(hiiTTimer);
		runnableTimer.setCurrentPhase(TimerPhase.WARMUP);
		runnableTimer.setCurrentTime(hiiTTimer.getWarmupDuration());
		runnableTimer.setCurrentMaxTime(runnableTimer.getWarmupTime());
		
		eventHandler = new TimerActivityEventHandler(this);
		mDone.setOnClickListener(eventHandler);
		mReset.setOnClickListener(eventHandler);
		mPrevious.setOnClickListener(eventHandler);
		mNext.setOnClickListener(eventHandler);
		mLock.setOnClickListener(eventHandler);
		mStart.setOnClickListener(eventHandler);
		
		if(HiitApp.timerWidth == 0 && HiitApp.timerHeight == 0) {
			HiitApp.timerWidth = (int) getResources().getDimension(R.dimen.timer_width);
			HiitApp.timerHeight = (int) getResources().getDimension(R.dimen.timer_height);
		}
		
		HiitApp.runnableTimer = runnableTimer;
		
		if(!hiiTTimer.getWarmupDuration().equals(getString(R.string.default_timer_time))) {
			mTitle.setText(runnableTimer.getHiiTTimer().getWarmupTitle());
			mMainTimer.setText(runnableTimer.getCurrentTime());
			mProgressBar.setMax((int)runnableTimer.getCurrentMaxTime());
			mProgressBar.setProgress((int)runnableTimer.getCurrentMaxTime());
			mRemainingTime.setText(runnableTimer.getCurrentMaxTimeText());
			mElapsedTime.setText(R.string.default_timer_time);
			mInterval.setText(getString(R.string.warmup_normal));
			mPrevious.setEnabled(false);
			mPrevious.setVisibility(View.INVISIBLE);
		} else {
			eventHandler.moveNext(true);
			mPrevious.setEnabled(false);
			mPrevious.setVisibility(View.INVISIBLE);
		}
		
		setColorForProgressBar(runnableTimer);
		
		HiitApp.timerEventHandler = eventHandler;
		isFromBackground = false;
		
		//drawGrayCycle();
		eventHandler.drawStartDot();
		
		HiitApp.lockState = getLock().isChecked();
		HiitApp.startState = getStart().isChecked();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		
		if(HiitApp.timerEventHandler != null) {
			if(HiitApp.timerEventHandler.getCountDownTimer() != null) {
				HiitApp.timerEventHandler.getCountDownTimer().cancel();
				if(!HiitApp.isFinish) {
					HiitApp.runnableTimer.setCurrentTime(getMainTimer().getText().toString());
				}
			}
		}
		
		isFromBackground = true;
	}

	@Override
	protected void onResume() {
		super.onResume();
		
		if(HiitApp.timerEventHandler != null && isFromBackground && !getStart().isChecked()) {
			HiitApp.timerEventHandler.startCount(true);
		}
		
		setFonts();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		
		prepareData();
		
		eventHandler = HiitApp.timerEventHandler;
		eventHandler.setTimerActivity(this);
		mDone.setOnClickListener(eventHandler);
		mReset.setOnClickListener(eventHandler);
		mPrevious.setOnClickListener(eventHandler);
		mNext.setOnClickListener(eventHandler);
		mLock.setOnClickListener(eventHandler);
		mStart.setOnClickListener(eventHandler);
		
		if(HiitApp.runnableTimer.getCurrentInterval() == HiitApp.runnableTimer.getTotalInterval()
				&& HiitApp.runnableTimer.getCurrentPhase().equals(TimerPhase.LOW_INTENSITY)
				&& HiitApp.runnableTimer.getHiiTTimer().getCoolDownDuration().equals(getString(R.string.default_timer_time))) {
			getNext().setEnabled(false);
			getNext().setVisibility(View.INVISIBLE);
		} else if(HiitApp.runnableTimer.getCurrentPhase() == TimerPhase.COOLDOWN){
			getNext().setEnabled(false);
			getNext().setVisibility(View.INVISIBLE);
		} else {
			getNext().setEnabled(true);
			getNext().setVisibility(View.VISIBLE);
		}
		
		if(HiitApp.runnableTimer.getCurrentInterval() == 1
				&& HiitApp.runnableTimer.getCurrentPhase().equals(TimerPhase.HIGH_INTENSITY)
				&& HiitApp.runnableTimer.getHiiTTimer().getWarmupDuration().equals(getString(R.string.default_timer_time))) {
			getPrevious().setEnabled(false);
			getPrevious().setVisibility(View.INVISIBLE);
		} else if(HiitApp.runnableTimer.getCurrentPhase() == TimerPhase.WARMUP){
			getPrevious().setEnabled(false);
			getPrevious().setVisibility(View.INVISIBLE);
		} else {
			getPrevious().setEnabled(true);
			getPrevious().setVisibility(View.VISIBLE);
		}
		
		getLock().setChecked(HiitApp.lockState);
		getStart().setChecked(HiitApp.startState);
		
		if (HiitApp.lockState) {
			getReset().setEnabled(false);
			getDone().setEnabled(false);
			getPrevious().setEnabled(false);
			getNext().setEnabled(false);
			getStart().setEnabled(false);
		} else {
			getReset().setEnabled(true);
			getDone().setEnabled(true);
		}
		
		getProgressBar().setProgress(0);
		getProgressBar().setMax((int) HiitApp.runnableTimer.getCurrentMaxTime());
		
		switch (HiitApp.runnableTimer.getCurrentPhase()) {
		case WARMUP:
			getScreenTitle().setText(HiitApp.runnableTimer.getHiiTTimer().getWarmupTitle());
			getInterval().setText(getString(R.string.warmup_normal));
			
			getRemainingTime().setText(HiitApp.runnableTimer.getHiiTTimer().getWarmupDuration());
			getElapsedTime().setText(getString(R.string.default_timer_time));
			getMainTimer().setText(HiitApp.runnableTimer.getCurrentMaxTimeText());
			
			break;
		case HIGH_INTENSITY:
			getScreenTitle().setText(HiitApp.runnableTimer.getHiiTTimer().getHighIntensityTitle());
			getInterval().setText(getString(R.string.interval) + " " + HiitApp.runnableTimer.getCurrentInterval() + "/" + HiitApp.runnableTimer.getTotalInterval());
			
			getRemainingTime().setText(HiitApp.runnableTimer.getHiiTTimer().getHighIntensityDuration());
			getElapsedTime().setText(getString(R.string.default_timer_time));
			getMainTimer().setText(HiitApp.runnableTimer.getCurrentMaxTimeText());
			
			break;
		case LOW_INTENSITY:
			getScreenTitle().setText(HiitApp.runnableTimer.getHiiTTimer().getLowIntensityTitle());
			getInterval().setText(getString(R.string.interval) + " " + HiitApp.runnableTimer.getCurrentInterval() + "/" + HiitApp.runnableTimer.getTotalInterval());
			
			getRemainingTime().setText(HiitApp.runnableTimer.getHiiTTimer().getLowIntensityDuration());
			getElapsedTime().setText(getString(R.string.default_timer_time));
			getMainTimer().setText(HiitApp.runnableTimer.getCurrentMaxTimeText());
			
			break;
		case COOLDOWN:
			getScreenTitle().setText(HiitApp.runnableTimer.getHiiTTimer().getCoolDownTitle());
			getInterval().setText(getString(R.string.cool_down_normal));
			
			getRemainingTime().setText(HiitApp.runnableTimer.getHiiTTimer().getCoolDownDuration());
			getElapsedTime().setText(getString(R.string.default_timer_time));
			getMainTimer().setText(HiitApp.runnableTimer.getCurrentMaxTimeText());
			break;
		}
		
		getProgressBar().setProgress(0);
		getProgressBar().setMax((int) HiitApp.runnableTimer.getCurrentMaxTime());
		getProgressBar().setProgress((int) HiitApp.runnableTimer.getCurrentMaxTime());
		setColorForProgressBar(HiitApp.runnableTimer);
		
		HiitApp.timerWidth = (int) getResources().getDimension(R.dimen.timer_width);
		HiitApp.timerHeight = (int) getResources().getDimension(R.dimen.timer_height);
		mImgTimer.invalidate();
		mImgTimer.refreshDrawableState();
		
		eventHandler.drawStartDot();
		
		if(HiitApp.isFinish) {
			eventHandler.drawTimer(360);
			getRemainingTime().setText(getString(R.string.default_timer_time));
			getElapsedTime().setText(HiitApp.runnableTimer.getCurrentMaxTimeText());
			getMainTimer().setText(getString(R.string.default_timer_time));
			
			getProgressBar().setProgress(0);
			getProgressBar().setMax((int) HiitApp.runnableTimer.getCurrentMaxTime());
			setColorForProgressBar(HiitApp.runnableTimer);
		} else {
			if(eventHandler.getSweepAngle() != 0) {
				eventHandler.drawTimer(eventHandler.getSweepAngle());
				getRemainingTime().setText(HiitApp.runnableTimer.getCurrentTime());
				getElapsedTime().setText(HiitApp.runnableTimer.getElapsedTimeString());
				getMainTimer().setText(HiitApp.runnableTimer.getCurrentTime());
				
				getProgressBar().setProgress(HiitApp.runnableTimer.getCurrentProgress());
			}
		}
		
		setFonts();
		
		HiitApp.lockState = getLock().isChecked();
		HiitApp.startState = getStart().isChecked();
		System.gc();
	}
	
	private void setFonts() {
		mDone.setTypeface(HiitApp.helveticaNeueCyrLight);
		mReset.setTypeface(HiitApp.helveticaNeueCyrLight);
		mStart.setTypeface(HiitApp.helveticaNeueCyrThin);
		mTitle.setTypeface(HiitApp.helveticaNeueCyrThin);
		mMainTimer.setTypeface(HiitApp.helveticaNeueCyrUltraLight);
		mInterval.setTypeface(HiitApp.helveticaNeueCyrThin);
		mElapsed.setTypeface(HiitApp.helveticaNeueCyrThin);
		mRemaining.setTypeface(HiitApp.helveticaNeueCyrThin);
		mElapsedTime.setTypeface(HiitApp.helveticaNeueCyrThin);
		mRemainingTime.setTypeface(HiitApp.helveticaNeueCyrThin);
	}
	
	private void prepareData() {
		setContentView(R.layout.activity_timer);
		
		mDone = (Button) findViewById(R.id.btnDone);
		mReset = (Button) findViewById(R.id.btnReset);
		mPrevious = (Button) findViewById(R.id.btnPrevious);
		mNext = (Button) findViewById(R.id.btnNext);
		mLock = (ToggleButton) findViewById(R.id.btnLock);
		mStart = (ToggleButton) findViewById(R.id.btnStart);
		mTitle = (TextView) findViewById(R.id.txtTitle);
		mMainTimer = (TextView) findViewById(R.id.txtTimer);
		mInterval = (TextView) findViewById(R.id.txtInterval);
		mElapsed = (TextView) findViewById(R.id.txtElapsed);
		mRemaining = (TextView) findViewById(R.id.txtRemaining);
		mElapsedTime = (TextView) findViewById(R.id.txtElapsedTime);
		mRemainingTime = (TextView) findViewById(R.id.txtRemainingTime);
		mImgTimer = (ImageView) findViewById(R.id.imgTimer);
		mProgressBar = (ProgressBar) findViewById(R.id.progressTime);
		mImgTimer.bringToFront();
	}
	
	public void setColorForProgressBar(RunnableTimer runnableTimer) {
		Drawable colorDrawable = getResources().getDrawable(R.drawable.progress_bar_red);;
		if(runnableTimer.getCurentColor().equals(HiitColor.DEFAULT.getCode())) {
			colorDrawable = getResources().getDrawable(R.drawable.progress_bar_default);
		} else if(runnableTimer.getCurentColor().equals(HiitColor.RED.getCode())) {
			colorDrawable = getResources().getDrawable(R.drawable.progress_bar_red);
		} else if(runnableTimer.getCurentColor().equals(HiitColor.ORANGE.getCode())) {
			colorDrawable = getResources().getDrawable(R.drawable.progress_bar_orange);
		} else if(runnableTimer.getCurentColor().equals(HiitColor.YELLOW.getCode())) {
			colorDrawable = getResources().getDrawable(R.drawable.progress_bar_yellow);
		} else if(runnableTimer.getCurentColor().equals(HiitColor.GREEN.getCode())) {
			colorDrawable = getResources().getDrawable(R.drawable.progress_bar_green);
		} else if(runnableTimer.getCurentColor().equals(HiitColor.TEAL.getCode())) {
			colorDrawable = getResources().getDrawable(R.drawable.progress_bar_teal);
		} else if(runnableTimer.getCurentColor().equals(HiitColor.BLUE.getCode())) {
			colorDrawable = getResources().getDrawable(R.drawable.progress_bar_blue);
		} else if(runnableTimer.getCurentColor().equals(HiitColor.PURPLE.getCode())) {
			colorDrawable = getResources().getDrawable(R.drawable.progress_bar_purple);
		} else if(runnableTimer.getCurentColor().equals(HiitColor.MAGENTA.getCode())) {
			colorDrawable = getResources().getDrawable(R.drawable.progress_bar_magenta);
		} else if(runnableTimer.getCurentColor().equals(HiitColor.DEEP_PURPLE.getCode())) {
			colorDrawable = getResources().getDrawable(R.drawable.progress_bar_deep_purple);
		}
		mProgressBar.setProgressDrawable(colorDrawable);
	}
	
	public Button getDone() {
		return mDone;
	}

	public Button getReset() {
		return mReset;
	}

	public Button getPrevious() {
		return mPrevious;
	}

	public Button getNext() {
		return mNext;
	}

	public ToggleButton getLock() {
		return mLock;
	}

	public ToggleButton getStart() {
		return mStart;
	}

	public TextView getScreenTitle() {
		return mTitle;
	}

	public TextView getMainTimer() {
		return mMainTimer;
	}

	public TextView getInterval() {
		return mInterval;
	}

	public TextView getElapsedTime() {
		return mElapsedTime;
	}

	public TextView getRemainingTime() {
		return mRemainingTime;
	}

	public ProgressBar getProgressBar() {
		return mProgressBar;
	}

	public ImageView getImgTimer() {
		return mImgTimer;
	}
	
	public TextView getElapsed() {
		return mElapsed;
	}

	public void setElapsed(TextView mElapsed) {
		this.mElapsed = mElapsed;
	}

	public TextView getRemaining() {
		return mRemaining;
	}

	public void setRemaining(TextView mRemaining) {
		this.mRemaining = mRemaining;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			HiitApp.isFinish = false;
			finish();
		}
		
		return true;
	}
}
