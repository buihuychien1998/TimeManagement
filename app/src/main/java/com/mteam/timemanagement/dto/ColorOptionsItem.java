package com.mteam.timemanagement.dto;

public class ColorOptionsItem {
	private String name;
	
	private int colorImageId;
	
	private boolean checked;
	
	public ColorOptionsItem(String name, int colorImageId, boolean checked) {
		super();
		this.name = name;
		this.colorImageId = colorImageId;
		this.checked = checked;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getColorImageId() {
		return colorImageId;
	}

	public void setColorImageId(int colorImageId) {
		this.colorImageId = colorImageId;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}
}
