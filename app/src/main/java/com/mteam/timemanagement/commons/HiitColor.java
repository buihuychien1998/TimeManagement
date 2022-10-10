package com.mteam.timemanagement.commons;


import com.mteam.timemanagement.R;

public enum HiitColor {
	DEFAULT("Default", R.drawable.color_default, "#4095E6", "#5FD8EF"), RED("Red", R.drawable.red, "#C70505", "#FF5E5E"),
	ORANGE("Orange", R.drawable.orange, "#F24317", "#FF8948"), YELLOW("Yellow", R.drawable.yellow, "#FD7F02", "#CFCA35"),
	GREEN("Green", R.drawable.green, "#077624", "#86C338"), TEAL("Teal", R.drawable.teal, "#03609E", "#35BDE0"),
	BLUE("Blue", R.drawable.blue, "#1B2CBF", "#4F92F7"), PURPLE("Purple", R.drawable.purple, "#620B95", "#C571F6"),
	MAGENTA("Magenta", R.drawable.magenta, "#AA066B", "#FF75D1"), DEEP_PURPLE("Deep Purple", R.drawable.deep_purple, "#4B326D", "#A47DE0");

	private String name;

	private int id;
	
	private String code;
	
	private String secondCode;

	HiitColor(String name, int id, String code, String secondCode) {
		this.name = name;
		this.id = id;
		this.code = code;
		this.secondCode = secondCode;
	}

	public String getName() {
		return name;
	}

	public int getId() {
		return id;
	}

	public String getCode() {
		return code;
	}

	public String getSecondCode() {
		return secondCode;
	}

	public void setSecondCode(String secondCode) {
		this.secondCode = secondCode;
	}

	public static HiitColor fromName(String name) {
		for (HiitColor color : HiitColor.values()) {
			if (color.getName().equals(name)) {
				return color;
			}
		}
		return HiitColor.DEFAULT;
	}
	
	public static HiitColor fromId(int id) {
		for (HiitColor color : HiitColor.values()) {
			if (color.getId() == id) {
				return color;
			}
		}
		return HiitColor.DEFAULT;
	}
	
	public static HiitColor fromCode(String code) {
		for (HiitColor color : HiitColor.values()) {
			if (color.getCode().equals(code)) {
				return color;
			}
		}
		return HiitColor.DEFAULT;
	}
}
