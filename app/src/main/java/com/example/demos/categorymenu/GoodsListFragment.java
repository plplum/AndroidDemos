package com.example.demos.categorymenu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.demos.R;

public class GoodsListFragment extends ListFragment {

	public static final String TAG = "GoodsListFragment";

	private ListAdapter listAdapter;
	private List<Map> list;

	private ArrayAdapter<String> adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		list = new ArrayList<Map>();

		Map<String, List<String>> map = new HashMap<String, List<String>>();
		List<String> subList = new ArrayList<String>();
		subList.add("啤酒");
		subList.add("XO");
		subList.add("红酒");
		subList.add("矿泉水");
		subList.add("可乐");
		map.put("饮料", subList);
		
		Map<String, List<String>> map2 = new HashMap<String, List<String>>();
		List<String> subList2 = new ArrayList<String>();
		subList2.add("核桃");
		subList2.add("栗子");
		subList2.add("苹果");
		map2.put("水果", subList2);

		list.add(map);
		list.add(map2);

		listAdapter = new ListAdapter(getActivity(), list);

		/*//测试数据2---- 定义一个数组
		List<String> data = new ArrayList<String>();
		for (int i = 0; i < 30; i++) {
			data.add("smyh" + i);
		}
		// 将数组加到ArrayAdapter当中
		adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1, data);
		*/
		setListAdapter(listAdapter);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		//View view = inflater.inflate(R.layout.myfragment, null);
		return super.onCreateView(inflater, container, savedInstanceState);
	}
	
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		//获取到listview
		ListView listView = getListView();
		//不显示滚动条
		listView.setVerticalScrollBarEnabled(false);
		//不显示分割线
		listView.setDivider(null);
		
		super.onActivityCreated(savedInstanceState);
	}

	class ListAdapter extends BaseAdapter {

		private Context context;
		private List<Map> list;

		public ListAdapter(Context context, List<Map> list2) {
			this.context = context;
			this.list = list2;
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

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = LayoutInflater.from(context).inflate(R.layout.goods_list_item, null);
			}
			TextView textViewTitle = (TextView) convertView.findViewById(R.id.category_title);
			Map<String, List<String>> map = list.get(position);
			Set<Entry<String, List<String>>> set = map.entrySet();
			Iterator<Entry<String, List<String>>> it = set.iterator();
			while (it.hasNext()) {
				Entry<String, List<String>> title = it.next();
				textViewTitle.setText(title.getKey());
				GridView gridView = (GridView) convertView.findViewById(R.id.goods_gridview);
				List<String> list = new ArrayList<String>();
				list = map.get(title.getKey());
				gridView.setAdapter(new GoodGridViewAdapter(getActivity(), list));
				gridView.setOnItemClickListener(new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
						TextView textView = (TextView) arg1.findViewById(R.id.good_name);
						String text = textView.getText().toString();
						Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
					}
				});
			}
			return convertView;
		}

	}

	class GoodGridViewAdapter extends BaseAdapter {
		private Context context;
		private LayoutInflater inflater;
		private List<String> list;

		public GoodGridViewAdapter(Context context, List<String> list) {
			this.context = context;
			inflater = LayoutInflater.from(this.context);
			this.list = list;
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

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			convertView = inflater.inflate(R.layout.grid_item_good, null);
			TextView city = (TextView) convertView.findViewById(R.id.good_name);
			city.setText(list.get(position));
			return convertView;
		}

	}

}
