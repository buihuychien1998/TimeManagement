package com.mteam.timemanagement.events;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.mteam.timemanagement.R;
import com.mteam.timemanagement.activity.CreateEditTimerActivity;
import com.mteam.timemanagement.activity.MainActivity;
import com.mteam.timemanagement.commons.Params;
import com.mteam.timemanagement.dto.SideMenuItem;


public class MainActivityEventHandler extends BaseEventHandler implements OnClickListener, OnItemClickListener {



	public MainActivityEventHandler(Activity activity) {
		super(activity);
	}

	@Override
	public void onClick(View v) {
		//		case R.id.imgSideMenu:
		//			openSideMenu();
		//			break;
		if (v.getId() == R.id.btnNewTimer) {
			createNewTimer();

//		case R.id.btnLoadTimer:
//			openSideMenu();
//			break;
		}
	}

	private void openSideMenu() {
		MainActivity mainActivity = (MainActivity) activity;
		//mainActivity.getMenuDrawer().toggleMenu(true);
	}

	private void createNewTimer() {
		Intent intent = new Intent(activity, CreateEditTimerActivity.class);
		intent.putExtra(Params.ACTION, Params.CREATE_TIMER);
		activity.startActivity(intent);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		SideMenuItem savedTimer = (SideMenuItem)parent.getItemAtPosition(position);
		Intent intent = new Intent(activity, CreateEditTimerActivity.class);
		intent.putExtra(Params.TIMER_ID, savedTimer.getId());
		intent.putExtra(Params.ACTION, Params.EDIT_TIMER);
		activity.startActivity(intent);
	}
}
