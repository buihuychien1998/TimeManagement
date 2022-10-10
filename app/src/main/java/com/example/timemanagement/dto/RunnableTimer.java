package com.example.timemanagement.dto;

import com.example.timemanagement.commons.HiitColor;
import com.example.timemanagement.commons.TimerPhase;
import com.example.timemanagement.utils.HiitUtils;

public class RunnableTimer {
	private HiiTTimer hiiTTimer;

	private long totalTime;
	
	private long elapsedTime;

	private TimerPhase currentPhase;

	private int currentInterval;
	
	private long totalInterval;

	private String currentTime;
	
	private String elapsedTimeString;

	private long currentMaxTime;

	private boolean isPaused;

	private boolean isLocked;

	private long warmupTime;

	private long highIntensityTime;

	private long lowIntensityTime;

	private long coolDownTime;
	
	private int currentProgress;

	public RunnableTimer(HiiTTimer hiiTTimer) {
		super();
		this.hiiTTimer = hiiTTimer;

		String[] timeArr = hiiTTimer.getHighIntensityDuration().split(":");
		int highIntensityInSeconds = Integer.parseInt(timeArr[0]) * 60 * 60 + Integer.parseInt(timeArr[1]) * 60 + Integer.parseInt(timeArr[2]);
		this.highIntensityTime = highIntensityInSeconds;

		timeArr = hiiTTimer.getLowIntensityDuration().split(":");
		int lowIntensityInSeconds = Integer.parseInt(timeArr[0]) * 60 * 60 + Integer.parseInt(timeArr[1]) * 60 + Integer.parseInt(timeArr[2]);
		this.lowIntensityTime = lowIntensityInSeconds;

		this.totalTime = (highIntensityInSeconds + lowIntensityInSeconds) * hiiTTimer.getSets();

		timeArr = hiiTTimer.getWarmupDuration().split(":");
		int warmupInSeconds = Integer.parseInt(timeArr[0]) * 60 * 60 + Integer.parseInt(timeArr[1]) * 60 + Integer.parseInt(timeArr[2]);
		this.warmupTime = warmupInSeconds;

		timeArr = hiiTTimer.getCoolDownDuration().split(":");
		int cooldownInSeconds = Integer.parseInt(timeArr[0]) * 60 * 60 + Integer.parseInt(timeArr[1]) * 60 + Integer.parseInt(timeArr[2]);
		this.coolDownTime = cooldownInSeconds;

		this.totalInterval = hiiTTimer.getSets() * 2;
		
		this.currentInterval = 1;
	}

	public HiiTTimer getHiiTTimer() {
		return hiiTTimer;
	}

	public void setHiiTTimer(HiiTTimer hiiTTimer) {
		this.hiiTTimer = hiiTTimer;
	}

	public long getTotalTime() {
		return totalTime;
	}

	public void setTotalTime(long totalTime) {
		this.totalTime = totalTime;
	}

	public TimerPhase getCurrentPhase() {
		return currentPhase;
	}

	public void setCurrentPhase(TimerPhase currentPhase) {
		this.currentPhase = currentPhase;
	}

	public int getCurrentInterval() {
		return currentInterval;
	}

	public void setCurrentInterval(int currentInterval) {
		this.currentInterval = currentInterval;
	}

	public String getCurrentTime() {
		return currentTime;
	}

	public void setCurrentTime(String currentTime) {
		this.currentTime = currentTime;
	}

	public boolean isPaused() {
		return isPaused;
	}

	public void setPaused(boolean isPaused) {
		this.isPaused = isPaused;
	}

	public boolean isLocked() {
		return isLocked;
	}

	public void setLocked(boolean isLocked) {
		this.isLocked = isLocked;
	}

	public long getWarmupTime() {
		return warmupTime;
	}

	public void setWarmupTime(long warmupTime) {
		this.warmupTime = warmupTime;
	}

	public long getHighIntensityTime() {
		return highIntensityTime;
	}

	public void setHighIntensityTime(long highIntensityTime) {
		this.highIntensityTime = highIntensityTime;
	}

	public long getLowIntensityTime() {
		return lowIntensityTime;
	}

	public void setLowIntensityTime(long lowIntensityTime) {
		this.lowIntensityTime = lowIntensityTime;
	}

	public long getCoolDownTime() {
		return coolDownTime;
	}

	public void setCoolDownTime(long coolDownTime) {
		this.coolDownTime = coolDownTime;
	}

	public long getTotalInterval() {
		return totalInterval;
	}

	public void setTotalInterval(long totalInterval) {
		this.totalInterval = totalInterval;
	}

	public long getCurrentMaxTime() {
		return currentMaxTime;
	}

	public void setCurrentMaxTime(long currentMaxTime) {
		this.currentMaxTime = currentMaxTime;
	}

	public String getTotalTimeText() {
		return HiitUtils.getDisplayText(this.totalTime / (60 * 60), (this.totalTime % (60 * 60)) / 60, (this.totalTime % (60 * 60)) % 60);
	}

	public String getCurrentMaxTimeText() {
		return HiitUtils.getDisplayText(this.currentMaxTime / (60 * 60), (this.currentMaxTime % (60 * 60)) / 60, (this.currentMaxTime % (60 * 60)) % 60);
	}
	
	public long getElapsedTime() {
		return elapsedTime;
	}

	public void setElapsedTime(long elapsedTime) {
		this.elapsedTime = elapsedTime;
	}
	
	public String getElapsedTimeText() {
		return HiitUtils.getDisplayText(this.elapsedTime / (60 * 60), (this.elapsedTime % (60 * 60)) / 60, (this.elapsedTime % (60 * 60)) % 60);
	}
	
	public String getElapsedTimeString() {
		return elapsedTimeString;
	}

	public void setElapsedTimeString(String elapsedTimeString) {
		this.elapsedTimeString = elapsedTimeString;
	}

	public int getCurrentProgress() {
		return currentProgress;
	}

	public void setCurrentProgress(int currentProgress) {
		this.currentProgress = currentProgress;
	}

	public String getCurentColor() {
		String colorName = "";
		switch (currentPhase) {
		case WARMUP:
			colorName = hiiTTimer.getWarmupColor();
			break;

		case HIGH_INTENSITY:
			colorName = hiiTTimer.getHighIntensityColor();
			break;

		case LOW_INTENSITY:
			colorName = hiiTTimer.getLowIntensityColor();
			break;

		case COOLDOWN:
			colorName = hiiTTimer.getCoolDownColor();
			break;
		}
		HiitColor hiitColor = HiitColor.fromName(colorName);
		String code = hiitColor.getCode();
		return code;
	}

	public TimerPhase getNextPhase() {
		TimerPhase result = TimerPhase.WARMUP;
		switch (currentPhase) {
		case WARMUP:
			currentMaxTime = highIntensityTime;
			currentTime = hiiTTimer.getHighIntensityDuration();
			result = TimerPhase.HIGH_INTENSITY;
			break;

		case HIGH_INTENSITY:
//			elapsedTime = highIntensityTime * (currentInterval/2 + 1) + lowIntensityTime * (currentInterval/2);
//			totalTime = ((highIntensityTime + lowIntensityTime) * hiiTTimer.getSets()) - (highIntensityTime * (currentInterval/2 + 1) + lowIntensityTime * (currentInterval/2));
			currentInterval++;
			currentMaxTime = lowIntensityTime;
			currentTime = hiiTTimer.getLowIntensityDuration();
			result = TimerPhase.LOW_INTENSITY;
			break;

		case LOW_INTENSITY:
			if(currentInterval == totalInterval) {
				currentMaxTime = coolDownTime;
				currentTime = hiiTTimer.getCoolDownDuration();
				result = TimerPhase.COOLDOWN;
			} else {
//				elapsedTime = (highIntensityTime + lowIntensityTime) * (currentInterval/2);
//				totalTime = ((highIntensityTime + lowIntensityTime) * hiiTTimer.getSets()) - ((highIntensityTime + lowIntensityTime) * (currentInterval/2));
				currentInterval++;
				currentMaxTime = highIntensityTime;
				currentTime = hiiTTimer.getHighIntensityDuration();
				result = TimerPhase.HIGH_INTENSITY;
			}
			
			break;

		case COOLDOWN:
			result = null;
			break;
		}
		return result;
	}
	
	public TimerPhase getPreviousPhase() {
		TimerPhase result = TimerPhase.COOLDOWN;
		switch (currentPhase) {
		case WARMUP:
			result = null;
			break;

		case HIGH_INTENSITY:
			if(currentInterval == 1) {
				currentMaxTime = warmupTime;
				currentTime = hiiTTimer.getWarmupDuration();
				result = TimerPhase.WARMUP;
			} else {
//				totalTime += lowIntensityTime;
				currentInterval--;
//				elapsedTime = (highIntensityTime + lowIntensityTime) * (currentInterval/2) - lowIntensityTime;
				currentMaxTime = lowIntensityTime;
				currentTime = hiiTTimer.getLowIntensityDuration();
				result = TimerPhase.LOW_INTENSITY;
			}
			
			break;

		case LOW_INTENSITY:
//			totalTime += highIntensityTime;
			currentInterval--;
//			elapsedTime = (highIntensityTime + lowIntensityTime) * (currentInterval/2);
			currentMaxTime = highIntensityTime;
			currentTime = hiiTTimer.getHighIntensityDuration();
			result = TimerPhase.HIGH_INTENSITY;
			break;

		case COOLDOWN:
			currentMaxTime = lowIntensityTime;
			currentTime = hiiTTimer.getLowIntensityDuration();
			result = TimerPhase.LOW_INTENSITY;
			break;
		}
		return result;
	}
}
