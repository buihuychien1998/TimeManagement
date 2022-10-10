package com.mteam.timemanagement.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mteam.timemanagement.R;
import com.mteam.timemanagement.HiitApp;
import com.mteam.timemanagement.dto.ColorOptionsItem;

import java.util.List;


public class ColorOptionsListAdapter extends BaseAdapter {

	private Context mContext;

	private List<ColorOptionsItem> mItems;

	public ColorOptionsListAdapter(Context context, List<ColorOptionsItem> items) {
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
			v = inflater.inflate(R.layout.color_options_item, null);
		}

		ColorOptionsItem colorOptionsItem = (ColorOptionsItem) getItem(position);

		TextView name = (TextView) v.findViewById(R.id.txtName);
		ImageView imgColor = (ImageView) v.findViewById(R.id.imgColor);
		ImageView imgCheck = (ImageView) v.findViewById(R.id.imgCheck);
		
		name.setText(colorOptionsItem.getName());
		imgColor.setImageResource(colorOptionsItem.getColorImageId());
		if(colorOptionsItem.isChecked()) {
			imgCheck.setVisibility(View.VISIBLE);
			v.setBackgroundResource(R.drawable.color_options_item_active_background);
		} else {
			imgCheck.setVisibility(View.INVISIBLE);
			v.setBackgroundResource(R.drawable.color_options_item_background);
		}
		
		if(name.getText().toString().equals(mContext.getString(R.string.default_item))) {
			name.setTypeface(HiitApp.helveticaNeueCyrRoman);
		} else {
			name.setTypeface(HiitApp.helveticaNeueCyrThin);
		}

		return v;
	}

}
