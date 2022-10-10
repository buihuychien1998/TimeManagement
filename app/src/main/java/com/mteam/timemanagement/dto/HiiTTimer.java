package com.mteam.timemanagement.dto;


import androidx.room.PrimaryKey;

import com.google.firebase.database.annotations.NotNull;

import java.io.Serializable;

public class HiiTTimer implements Serializable {
	/**
	 * Default serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	@PrimaryKey
	@NotNull
	private Integer id;
	
	private String name;

	private int sets;

	private String warmupTitle;

	private String warmupDuration;

	private String warmupColor;

	private String highIntensityTitle;

	private String highIntensityDuration;

	private String highIntensityColor;

	private String lowIntensityTitle;

	private String lowIntensityDuration;

	private String lowIntensityColor;

	private String coolDownTitle;

	private String coolDownDuration;

	private String coolDownColor;
	
	public HiiTTimer() {
		super();
	}

	public HiiTTimer(Integer id, String name, int sets, String warmupTitle, String warmupDuration, String warmupColor, String highIntensityTitle, String highIntensityDuration, String highIntensityColor,
			String lowIntensityTitle, String lowIntensityDuration, String lowIntensityColor, String coolDownTitle, String coolDownDuration, String coolDownColor) {
		super();
		this.id = id;
		this.name = name;
		this.sets = sets;
		this.warmupTitle = warmupTitle;
		this.warmupDuration = warmupDuration;
		this.warmupColor = warmupColor;
		this.highIntensityTitle = highIntensityTitle;
		this.highIntensityDuration = highIntensityDuration;
		this.highIntensityColor = highIntensityColor;
		this.lowIntensityTitle = lowIntensityTitle;
		this.lowIntensityDuration = lowIntensityDuration;
		this.lowIntensityColor = lowIntensityColor;
		this.coolDownTitle = coolDownTitle;
		this.coolDownDuration = coolDownDuration;
		this.coolDownColor = coolDownColor;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSets() {
		return sets;
	}

	public void setSets(int sets) {
		this.sets = sets;
	}

	public String getWarmupTitle() {
		return warmupTitle;
	}

	public void setWarmupTitle(String warmupTitle) {
		this.warmupTitle = warmupTitle;
	}

	public String getWarmupDuration() {
		return warmupDuration;
	}

	public void setWarmupDuration(String warmupDuration) {
		this.warmupDuration = warmupDuration;
	}

	public String getWarmupColor() {
		return warmupColor;
	}

	public void setWarmupColor(String warmupColor) {
		this.warmupColor = warmupColor;
	}

	public String getHighIntensityTitle() {
		return highIntensityTitle;
	}

	public void setHighIntensityTitle(String highIntensityTitle) {
		this.highIntensityTitle = highIntensityTitle;
	}

	public String getHighIntensityDuration() {
		return highIntensityDuration;
	}

	public void setHighIntensityDuration(String highIntensityDuration) {
		this.highIntensityDuration = highIntensityDuration;
	}

	public String getHighIntensityColor() {
		return highIntensityColor;
	}

	public void setHighIntensityColor(String highIntensityColor) {
		this.highIntensityColor = highIntensityColor;
	}

	public String getLowIntensityTitle() {
		return lowIntensityTitle;
	}

	public void setLowIntensityTitle(String lowIntensityTitle) {
		this.lowIntensityTitle = lowIntensityTitle;
	}

	public String getLowIntensityDuration() {
		return lowIntensityDuration;
	}

	public void setLowIntensityDuration(String lowIntensityDuration) {
		this.lowIntensityDuration = lowIntensityDuration;
	}

	public String getLowIntensityColor() {
		return lowIntensityColor;
	}

	public void setLowIntensityColor(String lowIntensityColor) {
		this.lowIntensityColor = lowIntensityColor;
	}

	public String getCoolDownTitle() {
		return coolDownTitle;
	}

	public void setCoolDownTitle(String coolDownTitle) {
		this.coolDownTitle = coolDownTitle;
	}

	public String getCoolDownDuration() {
		return coolDownDuration;
	}

	public void setCoolDownDuration(String coolDownDuration) {
		this.coolDownDuration = coolDownDuration;
	}

	public String getCoolDownColor() {
		return coolDownColor;
	}

	public void setCoolDownColor(String coolDownColor) {
		this.coolDownColor = coolDownColor;
	}
}
