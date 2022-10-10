package com.example.timemanagement.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.timemanagement.HiitApp;
import com.example.timemanagement.R;
import com.example.timemanagement.adapter.ColorOptionsListAdapter;
import com.example.timemanagement.commons.Params;
import com.example.timemanagement.dto.ColorOptionsItem;
import com.example.timemanagement.events.ColorOptionsActivityEventHandler;

import java.util.ArrayList;
import java.util.List;


public class ColorOptionsActivity extends Activity {

    private Button mBack;

    private TextView mTitle;

    private ListView mColorList;

    private int selectedIndex;

    private int[] imgColorIds = {
            R.drawable.color_default,
            R.drawable.red,
            R.drawable.orange,
            R.drawable.yellow,
            R.drawable.green,
            R.drawable.teal,
            R.drawable.blue,
            R.drawable.purple,
            R.drawable.magenta,
            R.drawable.deep_purple};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_options);

        mBack = (Button) findViewById(R.id.btnBack);
        mTitle = (TextView) findViewById(R.id.txtTitle);
        mColorList = (ListView) findViewById(R.id.colorList);

        String color = getIntent().getStringExtra(Params.COLOR);

        ColorOptionsListAdapter colorOptionsListAdapter = new ColorOptionsListAdapter(this, loadColors(color));
        mColorList.setAdapter(colorOptionsListAdapter);

        ColorOptionsActivityEventHandler eventHandler = new ColorOptionsActivityEventHandler(this);
        eventHandler.setSelectedColor(color);
        eventHandler.setSelectedIndex(selectedIndex);
        mBack.setOnClickListener(eventHandler);
        mColorList.setOnItemClickListener(eventHandler);
    }

    private List<ColorOptionsItem> loadColors(String currentColor) {
        List<ColorOptionsItem> colorOptionsItems = new ArrayList<ColorOptionsItem>();
        String[] colorList = getResources().getStringArray(R.array.colorList);

        for (int i = 0; i < colorList.length; i++) {
            if (currentColor.equalsIgnoreCase(colorList[i])) {
                colorOptionsItems.add(new ColorOptionsItem(colorList[i], imgColorIds[i], true));
                selectedIndex = i;
            } else {
                colorOptionsItems.add(new ColorOptionsItem(colorList[i], imgColorIds[i], false));
            }
        }

        return colorOptionsItems;
    }

    public Button getBack() {
        return mBack;
    }

    public TextView getScreenTitle() {
        return mTitle;
    }

    public ListView getColorList() {
        return mColorList;
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Set font
        mTitle.setTypeface(HiitApp.helveticaNeueCyrThin);
        mBack.setTypeface(HiitApp.helveticaNeueCyrLight);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
        }

        return true;
    }
}
