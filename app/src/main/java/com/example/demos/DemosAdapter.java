package com.example.demos;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

class DemosAdapter extends ArrayAdapter<DemoListItem>{

	private LayoutInflater inflater;
	private int mLayoutId;
	
	public DemosAdapter(Context context, int textViewResourceId, List list) {
		super(context, textViewResourceId, list);
		inflater = LayoutInflater.from(context);
		this.mLayoutId = textViewResourceId;
	}
	
	public DemosAdapter(Context context, int textViewResourceId) {
		super(context, textViewResourceId);
		inflater = LayoutInflater.from(context);
		this.mLayoutId = textViewResourceId;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		//使用ViewHolder和setTag()/getTag()可以提升性能，不用每次都去布局中查找控件。
		ViewHolder viewHolder;
		if (convertView == null) {
			convertView = inflater.inflate(mLayoutId, parent, false);
			viewHolder = new ViewHolder();
			viewHolder.tv_title = (TextView)convertView.findViewById(R.id.text_view_title);
			viewHolder.tv_description = (TextView)convertView.findViewById(R.id.text_view_description);
			viewHolder.iv_icon = (ImageView)convertView.findViewById(R.id.image_view_menu_icon);
			convertView.setTag(viewHolder);
		}else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		DemoListItem typeItem = this.getItem(position);

		/*TextView textView;
		textView = (TextView)convertView.findViewById(R.id.text_view_title);
		textView.setText(typeItem.getName());
		
		textView = (TextView)convertView.findViewById(R.id.text_view_description);
		textView.setText(typeItem.getDescription());
		
		ImageView imageView = (ImageView)convertView.findViewById(R.id.image_view_menu_icon);
		imageView.setImageResource(typeItem.getIconResId());*/
		viewHolder.tv_title.setText(typeItem.getName());
		viewHolder.tv_description.setText(typeItem.getDescription());
		viewHolder.iv_icon.setImageResource(typeItem.getIconResId());
		return convertView;
	}	
	
	class ViewHolder{
		ImageView iv_icon;
		TextView tv_title;
		TextView tv_description;
	}
}