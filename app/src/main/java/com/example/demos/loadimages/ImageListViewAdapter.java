package com.example.demos.loadimages;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.demos.R;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ImageListViewAdapter extends BaseAdapter {

	private LayoutInflater inflater;

	private List<Object> mDataList;
	
	private ImageLoader universalimageloader;

	public ImageListViewAdapter(Context context, List<Object> dataList) {
		inflater = LayoutInflater.from(context);
		// 图片异步加载器
		universalimageloader = ToolImage.initImageLoader(context);
		this.mDataList = dataList;

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder mViewHolder;
		if (null == convertView) {
			convertView = inflater.inflate(R.layout.activity_image_listview_item, null);
			mViewHolder = new ViewHolder();
			mViewHolder.iv_icon = (ImageView) convertView.findViewById(R.id.iv_icon);
			mViewHolder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
			convertView.setTag(mViewHolder);
		} else {
			mViewHolder = (ViewHolder) convertView.getTag();
		}
		// 设置数据
		Map<String, Object> rowData = (Map) getItem(position);
		// 异步加载图片防止错位方法一：com.android.volley.toolbox.ImageLoader
		// ImageLoader mImageLoader = MApplication.getImageLoader();
		// ImageListener mImageListener =
		// mImageLoader.getImageListener(mViewHolder.iv_icon,
		// R.drawable.default_icon, R.drawable.ic_launcher);
		// mImageLoader.get((String)rowData.get("imageUrl"), mImageListener);

		// 异步加载图片防止错位方法二：com.nostra13.universalimageloader.core.ImageLoader
		universalimageloader.displayImage((String) rowData.get("imageUrl"),
				mViewHolder.iv_icon, ToolImage.getFadeOptions(
						R.drawable.default_icon, R.drawable.ic_launcher,
						R.drawable.ic_launcher));
		mViewHolder.tv_title.setText((String) rowData.get("title"));
		return convertView;
	}

	class ViewHolder {
		ImageView iv_icon;
		TextView tv_title;
	}

	@Override
	public int getCount() {
		return mDataList.size();
	}

	@Override
	public Object getItem(int position) {
		return mDataList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
}