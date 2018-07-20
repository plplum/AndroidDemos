package com.example.demos.province;

import java.util.List;

import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.demos.R;

public class CityAdapter extends BaseAdapter{
	
	private Context context;
	private List<Area> citys;
	//private LayoutInflater inflater;
	private int selectedItem;
	
	public CityAdapter(Context context, List<Area> citys) {
		this.context = context;
		this.citys = citys;
		//inflater = LayoutInflater.from(this.context);
	}
	
	@Override
	public int getCount() {
		return citys.size();
	}

	@Override
	public Object getItem(int position) {
		return citys.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TextView view;
		if (convertView == null) {
			view = (TextView) LayoutInflater.from(context).inflate(R.layout.choose_item, parent, false);
		} else {
			view = (TextView) convertView;
		}
		view.setTag(position);
		String mString = "";
		if (citys != null) {
			if (position < citys.size()) {
				mString = citys.get(position).getName();
			}
		}
		if (mString.contains("不限"))
			view.setText("不限");
		else
			view.setText(mString);
		view.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);

		if (selectedItem == position) {
			view.setBackgroundResource(R.drawable.choose_item_selected);
		} else {
			view.setBackgroundResource(R.drawable.choose_eara_item_selector);
		}
		view.setPadding(20, 0, 0, 0);
		return view;
	}

	
	public void setSelectedItem(int item) {
		this.selectedItem = item;
	}
	
}
