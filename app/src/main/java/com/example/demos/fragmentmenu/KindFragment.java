package com.example.demos.fragmentmenu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.example.demos.R;

public class KindFragment extends Fragment implements OnItemClickListener {
	private ListView listView, detailListView;
	private List<String> list = new ArrayList<String>(),
			detaillist = new ArrayList<String>();
	private KindAdapter listAdapter, detailAdapter;
	private Map<String, Integer> map = new HashMap<String, Integer>();

	//用于保存选中的列表索引值
	private int listSelect = -1, detailSelect = -1;

	private Handler handler;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_kind, null);
		initData();
		init(view);

		return view;
	}

	/**
	 * 初始化数据
	 */
	private void initData() {
		// TODO Auto-generated method stub
		map.put("快餐", R.array.kind_kuaichan);
		map.put("中餐", R.array.kind_zhongchan);
		map.put("西餐", R.array.kind_xichan);

		if (list != null && list.size() > 0)
			list.clear();
		for (Map.Entry entry : map.entrySet())
			list.add(entry.getKey().toString());
	}

	/**
	 * 初始化控件
	 * 
	 * @param view
	 */
	private void init(View view) {
		// TODO Auto-generated method stub
		listView = (ListView) view.findViewById(R.id.kind_list);
		listAdapter = new KindAdapter(getActivity());
		listAdapter.setList(list);
		listAdapter.setClickItem(listSelect);
		listView.setAdapter(listAdapter);
		listView.setOnItemClickListener(this);
		detailListView = (ListView) view.findViewById(R.id.kind_detil_list);
		detailAdapter = new KindAdapter(getActivity());
		detailAdapter.setList(detaillist);
		detailAdapter.setClickItem(detailSelect);
		detailListView.setAdapter(detailAdapter);
		detailListView.setOnItemClickListener(this);
	}

	/**
	 * 加载子列表数据
	 * 
	 * @param key
	 * @return
	 */
	private boolean addDetailList(String key) {
		if (detaillist != null && detaillist.size() > 0)
			detaillist.clear();
		String[] detailArray = getResources().getStringArray(map.get(key));
		if (detailArray.length <= 0)
			return false;
		for (String str : detailArray)
			detaillist.add(str);
		return true;
	}

	/**
	 * ListView点击事件
	 */

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		if (parent.getId() == R.id.kind_list) {
			listAdapter.setClickItem(position);
			//listAdapter.notifyDataSetChanged();

			listSelect = position;
			detailSelect = -1;

			boolean hasDetail = addDetailList(list.get(position));
			detailAdapter.setClickItem(-1);
			detailAdapter.notifyDataSetChanged();
			if (!hasDetail)// 将点击结果发送到主界面
				handler.obtainMessage(0, list.get(position)).sendToTarget();
		} else if (parent.getId() == R.id.kind_detil_list) {
			detailAdapter.setClickItem(position);
			//detailAdapter.notifyDataSetChanged();
			detailSelect = position;
			//将点击结果发送到主界面
			handler.obtainMessage(0, detaillist.get(position)).sendToTarget();

		}

	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}

}
