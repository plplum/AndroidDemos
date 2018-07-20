package com.example.demos.city.apapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.demos.R;
import com.example.demos.city.bean.City;

public class SearchCityAdapter extends BaseAdapter implements Filterable {

	private List<City> mAllCities;
	private List<City> mResultCities;
	private LayoutInflater mInflater;
	private Context mContext;

	// private String mFilterStr;

	public SearchCityAdapter(Context context, List<City> allCities) {
		mContext = context;
		mAllCities = allCities;
		mResultCities = new ArrayList<City>();
		mInflater = LayoutInflater.from(mContext);
	}

	@Override
	public int getCount() {
		return mResultCities.size();
	}

	@Override
	public City getItem(int position) {
		return mResultCities.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.search_city_item, null);
		}
		TextView provinceTv = (TextView) convertView
				.findViewById(R.id.search_province);
		provinceTv.setText(mResultCities.get(position).getProvince());
		TextView cityTv = (TextView) convertView
				.findViewById(R.id.column_title);
		cityTv.setText(mResultCities.get(position).getName());
		return convertView;
	}

	@Override
	public Filter getFilter() {
		Filter filter = new Filter() {
			@SuppressWarnings("unchecked")
			protected void publishResults(CharSequence constraint,
					FilterResults results) {
				mResultCities = (ArrayList<City>) results.values;
				if (results.count > 0) {
					notifyDataSetChanged();
				} else {
					notifyDataSetInvalidated();
				}
			}

			protected FilterResults performFiltering(CharSequence s) {
				String str = s.toString().toLowerCase();
				// mFilterStr = str;
				FilterResults results = new FilterResults();
				ArrayList<City> cityList = new ArrayList<City>();
				//查找城市
				if (mAllCities != null && mAllCities.size() != 0) {
					for (City cb : mAllCities) {
						// 鍖归厤鍏ㄥ睆銆侀瀛楁瘝銆佸拰鍩庡競鍚嶄腑鏂�						
						if (cb.getPy().indexOf(str) > -1
								|| cb.getPinyin().indexOf(str) > -1
								|| cb.getName().indexOf(str) > -1) {
							cityList.add(cb);
						}
					}
				}
				
				//如果是联系人， 查找联系人
				/*if (mAllCities != null && mAllCities.size() != 0) {
					for (City cb : mAllCities) {
						// 鍖归厤鍏ㄥ睆銆侀瀛楁瘝銆佸拰鍩庡競鍚嶄腑鏂�						
						if (cb.getName().indexOf(str) > -1) {
							cityList.add(cb);
						}
					}
				}*/
				
				results.values = cityList;
				results.count = cityList.size();
				return results;
			}
		};
		return filter;
	}

}
