package com.example.timemanagement.events;

import static com.example.timemanagement.billing.PurchaseManager.productIds;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.ConsumeParams;
import com.android.billingclient.api.ConsumeResponseListener;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.SkuDetails;
import com.android.billingclient.api.SkuDetailsParams;
import com.example.timemanagement.R;
import com.example.timemanagement.activity.CreateEditTimerActivity;
import com.example.timemanagement.activity.MainActivity;
import com.example.timemanagement.commons.Params;
import com.example.timemanagement.dto.SideMenuItem;

import java.util.ArrayList;
import java.util.List;


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
