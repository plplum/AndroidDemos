package com.example.demos.fragmentmenu;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.demos.R;

public class KindAdapter extends BaseAdapter {
	private List<String> list;
	private LayoutInflater inflater;

	public KindAdapter(Context context) {
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list == null ? 0 : list.size();
	}

	public List<String> getList() {
		return list;
	}

	public void setList(List<String> list) {
		this.list = list;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list == null ? null : list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return list == null ? -1 : position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater
					.inflate(R.layout.adapter_fragment_list, null);
			holder.text = (TextView) convertView
					.findViewById(R.id.adapter_kind_text);
			convertView.setTag(holder);
		} else
			holder = (ViewHolder) convertView.getTag();
		if (position != clickItem) {
			convertView.setBackgroundColor(Color.WHITE);
		} else {
			convertView.setBackgroundColor(Color.LTGRAY);
		}
		holder.text.setText(list.get(position));
		return convertView;
	}

	private int clickItem = -1;;

	public void setClickItem(int clickItem) {
		this.clickItem = clickItem;
	}

	class ViewHolder {
		TextView text;
	}
}
