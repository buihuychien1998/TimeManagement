package com.mteam.timemanagement.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mteam.timemanagement.R;
import com.mteam.timemanagement.HiitApp;
import com.mteam.timemanagement.dto.SideMenuItem;

import java.util.List;

public class SideMenuListAdapter extends BaseAdapter {

	private Context mContext;

	private List<SideMenuItem> mItems;

	public SideMenuListAdapter(Context context, List<SideMenuItem> items) {
		mContext = context;
		mItems = items;
	}

	@Override
	public int getCount() {
		return mItems == null ? 0 : mItems.size();
	}

	@Override
	public Object getItem(int position) {
		return mItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		if (v == null) {
			LayoutInflater inflater = (LayoutInflater) mContext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = inflater.inflate(R.layout.side_menu_item, null);
		}

		SideMenuItem sideMenuItem = (SideMenuItem) getItem(position);

		TextView name = (TextView) v.findViewById(R.id.txtName);
		TextView time = (TextView) v.findViewById(R.id.txtTime);
		
		name.setText(sideMenuItem.getName());
		time.setText(sideMenuItem.getTime());
		
		// Set font
		name.setTypeface(HiitApp.helveticaNeueCyrThin);
		time.setTypeface(HiitApp.helveticaNeueCyrThin);

		return v;
	}

}
