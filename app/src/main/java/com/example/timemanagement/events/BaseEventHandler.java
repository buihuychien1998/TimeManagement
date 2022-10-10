package com.example.timemanagement.events;

import android.app.Activity;

import androidx.fragment.app.Fragment;

public class BaseEventHandler {
	protected Activity activity;
	
	protected Fragment fragment;

	public BaseEventHandler(Activity activity) {
		super();
		this.activity = activity;
	}

	public BaseEventHandler(Fragment fragment) {
		super();
		this.fragment = fragment;
	}
}
