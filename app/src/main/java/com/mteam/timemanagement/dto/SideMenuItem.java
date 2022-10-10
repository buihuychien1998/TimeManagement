package com.mteam.timemanagement.dto;

public class SideMenuItem {
	private String name;
	
	private String time;
	
	private int id;
	
	public SideMenuItem(String name, String time, int id) {
		super();
		this.id = id;
		this.name = name;
		this.time = time;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
