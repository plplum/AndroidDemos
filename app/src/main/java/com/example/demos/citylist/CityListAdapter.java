package com.example.demos.citylist;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.LocationClient;
import com.example.demos.R;

public class CityListAdapter extends BaseAdapter {
		private Context context;
		private LayoutInflater inflater;
		private List<City> list;
		private List<City> hotList;
		private List<String> hisCity;
		final int VIEW_TYPE = 5;
		
		//存放listView中汉语拼音首字母和与index对应的位置
		private HashMap<String, Integer> alphaIndexer;
		
		// 存放汉语拼音首字母
		private String[] sections;
		
		//记录当前定位的状态 正在定位-定位成功-定位失败
		private int locateProcess = 1; 
		//定位到的城市
		private String currentCity;
		
		private LocationClient mLocationClient;

		public CityListAdapter(Context context, List<City> list, List<City> hotList, List<String> hisCity, int locateProcess, String currentCity) {
			this.inflater = LayoutInflater.from(context);
			this.list = list;
			this.context = context;
			this.hotList = hotList;
			this.hisCity = hisCity;
			this.currentCity = currentCity;
			this.locateProcess = locateProcess;
			
			alphaIndexer = new HashMap<String, Integer>();
			sections = new String[list.size()];
			City city = null;
			for (int i = 0; i < list.size(); i++) {
				city = list.get(i);
				// 当前汉语拼音首字母
				String currentStr = Util.getAlpha(city.getPinyi());
				// 上一个汉语拼音首字母，如果不存在为" "
				String previewStr = (i - 1) >= 0 ? Util.getAlpha(list.get(i - 1).getPinyi()) : " ";
				if (!previewStr.equals(currentStr)) {
					String name = Util.getAlpha(list.get(i).getPinyi());
					alphaIndexer.put(name, i);
					sections[i] = name;
				}
			}
		}

		@Override
		public int getViewTypeCount() {
			return VIEW_TYPE;
		}

		@Override
		public int getItemViewType(int position) {
			return position < 4 ? position : 4;
		}

		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		ViewHolder holder;

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			final TextView city;
			int viewType = getItemViewType(position);
			if (viewType == 0) { // 定位
				convertView = inflater.inflate(R.layout.frist_list_item, null);
				final TextView locateHint = (TextView) convertView.findViewById(R.id.locateHint);
				final ProgressBar pbLocate = (ProgressBar) convertView.findViewById(R.id.pbLocate);
				city = (TextView) convertView.findViewById(R.id.lng_city);
				city.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						if (locateProcess == 2) {
							Toast.makeText(context, city.getText().toString(), Toast.LENGTH_SHORT).show();
						} else if (locateProcess == 3) {
							locateProcess = 1;
							mLocationClient = BdLocationClient.getInstance(context).getLocationClient();
							mLocationClient.stop();
							BdLocationClient.getInstance(context).setNeedFresh(true);
							mLocationClient.start();
							locateHint.setText("正在定位");
							city.setVisibility(View.GONE);
							pbLocate.setVisibility(View.VISIBLE);
						}
					}
				});
				
				if (locateProcess == 1) { // 正在定位
					locateHint.setText("正在定位");
					city.setVisibility(View.GONE);
					pbLocate.setVisibility(View.VISIBLE);
				} else if (locateProcess == 2) { // 定位成功
					locateHint.setText("当前定位城市");
					city.setVisibility(View.VISIBLE);
					city.setText(currentCity);
					//mLocationClient.stop();
					pbLocate.setVisibility(View.GONE);
				} else if (locateProcess == 3) {
					locateHint.setText("未定位到城市,请选择");
					city.setVisibility(View.VISIBLE);
					city.setText("重新选择");
					pbLocate.setVisibility(View.GONE);
				}
			} else if (viewType == 1) { // 最近访问城市
				convertView = inflater.inflate(R.layout.recent_city, null);
				GridView rencentCity = (GridView) convertView.findViewById(R.id.recent_city);
				rencentCity.setAdapter(new HistoryCityAdapter(context, this.hisCity));
				rencentCity.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {

						Toast.makeText(context,hisCity.get(position), Toast.LENGTH_SHORT).show();

					}
				});
				TextView recentHint = (TextView) convertView.findViewById(R.id.recentHint);
				recentHint.setText("最近访问的城市");
			} else if (viewType == 2) {
				convertView = inflater.inflate(R.layout.recent_city, null);
				GridView hotCity = (GridView) convertView.findViewById(R.id.recent_city);
				hotCity.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
						Toast.makeText(context, hotList.get(position).getName(), Toast.LENGTH_SHORT).show();
					}
				});
				hotCity.setAdapter(new HotCityAdapter(context, this.hotList));
				TextView hotHint = (TextView) convertView.findViewById(R.id.recentHint);
				hotHint.setText("热门城市");
			} else if (viewType == 3) {
				convertView = inflater.inflate(R.layout.total_item, null);
			} else {
				if (convertView == null) {
					convertView = inflater.inflate(R.layout.list_item, null);
					holder = new ViewHolder();
					holder.alpha = (TextView) convertView
							.findViewById(R.id.alpha);
					holder.name = (TextView) convertView
							.findViewById(R.id.name);
					convertView.setTag(holder);
				} else {
					holder = (ViewHolder) convertView.getTag();
				}
				if (position >= 1) {
					holder.name.setText(list.get(position).getName());
					String currentStr = Util.getAlpha(list.get(position).getPinyi());
					String previewStr = (position - 1) >= 0 ? Util.getAlpha(list
							.get(position - 1).getPinyi()) : " ";
					if (!previewStr.equals(currentStr)) {
						holder.alpha.setVisibility(View.VISIBLE);
						holder.alpha.setText(currentStr);
					} else {
						holder.alpha.setVisibility(View.GONE);
					}
				}
			}
			return convertView;
		}

		private class ViewHolder {
			TextView alpha; // 首字母标题
			TextView name; // 城市名字
		}

		public void setLocateProcess(int locateProcess) {
			this.locateProcess = locateProcess;
		}

		public void setCurrentCity(String currentCity) {
			this.currentCity = currentCity;
		}

		public HashMap<String, Integer> getAlphaIndexer() {
			return alphaIndexer;
		}
		
		
		
	}