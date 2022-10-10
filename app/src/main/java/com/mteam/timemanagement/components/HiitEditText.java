package com.mteam.timemanagement.components;

import android.content.Context;
import android.util.AttributeSet;

public class HiitEditText extends androidx.appcompat.widget.AppCompatEditText {

	public HiitEditText(Context context) {
		super(context);
	}

	public HiitEditText(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public HiitEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public void onSelectionChanged(int start, int end) {
		CharSequence text = getText();
		if (text != null) {
			if (start != text.length() || end != text.length()) {
				setSelection(text.length(), text.length());
				return;
			}
		}

		super.onSelectionChanged(start, end);
	}

}