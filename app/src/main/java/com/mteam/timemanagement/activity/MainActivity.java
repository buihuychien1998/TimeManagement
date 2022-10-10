package com.mteam.timemanagement.activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mteam.timemanagement.R;
import com.mteam.timemanagement.HiitApp;
import com.mteam.timemanagement.commons.Params;
import com.mteam.timemanagement.events.MainActivityEventHandler;
import com.mteam.timemanagement.utils.HiitUtils;


public class MainActivity extends Activity {

    private ImageView mSideMenuIcon;

    private Button mNewTimer;

    private Button mLoadTimer;

    private TextView mAppName;

    private ListView mTimerList;

    private RelativeLayout mMenuDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
//            mMenuDrawer = MenuDrawer.attach(this, Position.BOTTOM);
//        } else {
//            mMenuDrawer = MenuDrawer.attach(this, Position.RIGHT);
//        }
//        mMenuDrawer.setTouchMode(MenuDrawer.TOUCH_MODE_FULLSCREEN);
//
//        mMenuDrawer.setMenuView(R.layout.menu_frame);

//        if(HiitUtils.isTablet(this)) {
//            mMenuDrawer.setMenuSize((int)getResources().getDimension(R.dimen.slidingmenu_size));
//        } else {
//            mMenuDrawer.setMenuSize((int)getResources().getDimension(R.dimen.slidingmenu_size_phone));
//        }

       // mSideMenuIcon = findViewById(R.id.imgSideMenu);
        mNewTimer =  findViewById(R.id.btnNewTimer);
      //  mLoadTimer =  findViewById(R.id.btnLoadTimer);
       // mTimerList =  findViewById(R.id.timerList);
        // mAppName = (TextView) findViewById(R.id.txtAppName);

        MainActivityEventHandler eventHandler = new MainActivityEventHandler(this);
        mNewTimer.setOnClickListener(eventHandler);

       // mTimerList.setOnItemClickListener(eventHandler);

        HiitApp.currentWarmupColor = null;
        HiitApp.currentHighIntensityColor = null;
        HiitApp.currentLowIntensityColor = null;
        HiitApp.currentCoolDownColor = null;

        String fromTimer = getIntent().getStringExtra(Params.FROM_TIMER);
        String dontAsk = HiitUtils.loadData(this, Params.RATE_DONT_ASK);
        if (fromTimer != null && dontAsk.equals("")) {
            showRateMe();
        }
    }

//    private List<SideMenuItem> loadSavedTimers() {
//        DatabaseHelper databaseHelper = new DatabaseHelper(getApplicationContext());
//       //List<HiiTTimer> timers = databaseHelper.selectAll(HiiTTimer.class);
//        List<SideMenuItem> savedTimers = new ArrayList<SideMenuItem>();
//
//        for (HiiTTimer hiiTTimer : timers) {
//            String displayTime = hiiTTimer.getWarmupDuration();
//            if(displayTime.equals(getString(R.string.default_time))) {
//                displayTime = hiiTTimer.getHighIntensityDuration();
//            }
//            SideMenuItem sideMenuItem = new SideMenuItem(hiiTTimer.getName(), displayTime, hiiTTimer.getId());
//            savedTimers.add(sideMenuItem);
//        }
//
//        return savedTimers;
//    }

    @Override
    protected void onResume() {
        super.onResume();

        //  List<SideMenuItem> savedTimers = loadSavedTimers();

//        if(savedTimers.size() == 0) {
//            mSideMenuIcon.setVisibility(View.INVISIBLE);
//            mLoadTimer.setVisibility(View.INVISIBLE);
//           // mMenuDrawer.setTouchMode(MenuDrawer.TOUCH_MODE_NONE);
//        } else {
//            mSideMenuIcon.setVisibility(View.VISIBLE);
//            mLoadTimer.setVisibility(View.VISIBLE);
//            mMenuDrawer.setTouchMode(MenuDrawer.TOUCH_MODE_FULLSCREEN);
//
//            SideMenuListAdapter sideMenuListAdapter = new SideMenuListAdapter(this, savedTimers);
//            mTimerList.setAdapter(sideMenuListAdapter);
//        }
//
//        // Set font
//        mNewTimer.setTypeface(HiitApp.helveticaNeueCyrThin);
//        mLoadTimer.setTypeface(HiitApp.helveticaNeueCyrThin);
//        if(mAppName != null) {
//            mAppName.setTypeface(HiitApp.din1451Breit36);
//        }
    }

    public ImageView getSideMenuIcon() {
        return mSideMenuIcon;
    }

    public Button getNewTimer() {
        return mNewTimer;
    }

    public Button getLoadTimer() {
        return mLoadTimer;
    }

//    public MenuDrawer getMenuDrawer() {
//        return mMenuDrawer;
//    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            moveTaskToBack(true);
        }

        return true;
    }

    private void showRateMe() {
        String completeCountStr = HiitUtils.loadData(this, Params.COMPLETE_COUNT);
        int completeCount = 0;
        if (!completeCountStr.equals("")) {
            completeCount = Integer.parseInt(completeCountStr);
        }
        int currentCompleteCount = completeCount + 1;
        HiitUtils.saveData(this, Params.COMPLETE_COUNT, currentCompleteCount + "");

        if ((currentCompleteCount % 2) == 0) {
            RateMePopup rateMePopup = new RateMePopup(this);
            rateMePopup.show();
        }
    }
}
