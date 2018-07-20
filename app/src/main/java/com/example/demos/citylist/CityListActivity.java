package com.example.demos.citylist;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.example.demos.R;
import com.example.demos.citylist.LetterListView.OnTouchingLetterChangedListener;

public class CityListActivity extends Activity implements OnTouchingLetterChangedListener, TextWatcher {

	//字母列表
	private LetterListView letterListView;
	
	//对话框首字母textview
	private TextView overlayTextView; 
	
	//是否已经初始化overlay
	//private boolean overlayReady;
	
	//弹出/隐藏首字母overlay的handler
	private Handler handler;
	
	//弹出/隐藏首字母overlay的thread
	private OverlayThread overlayThread;
	
	//所有城市listView
	private ListView cityListView;
	
	//搜索结果listView
	private ListView searchResultList;
	
	//搜索结果adapter
	private ResultListAdapter resultListAdapter;
	
	//无结果提示
	private TextView txt_noresult;
	
	//存放listView中汉语拼音首字母和与index对应的位置
	private HashMap<String, Integer> alphaIndexer;
	
	//记录当前定位的状态 正在定位-定位成功-定位失败
	private int locateProcess = 1; 
	
	//City List Adapter
	private CityListAdapter cityListAdapter;
	
	//search EditText
	private EditText editText;
	
	private ArrayList<City> allCity_lists; // 所有城市列表
	private ArrayList<City> city_lists;// 城市列表
	private ArrayList<City> city_hot;
	private ArrayList<City> city_result;
	private ArrayList<String> city_history;
	
	private LocationClient mLocationClient;
	private MyLocationListener mMyLocationListener;
	
	//定位刷新
	private boolean isNeedFresh = true;
	// 用于保存定位到的城市
	private String currentCity; 
	
	
	private TextView textView;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.acticity_city_list);
		
		letterListView = (LetterListView) findViewById(R.id.letter_list);
		letterListView.setOnTouchingLetterChangedListener(this);
		
		handler = new Handler();
		overlayThread = new OverlayThread();
		
		initOverlay();
		
		allCity_lists = new ArrayList<City>();
		city_hot = new ArrayList<City>();
		city_result = new ArrayList<City>();
		city_history = new ArrayList<String>();
		
		cityInit();
		hotCityInit();
		hisCityInit();
		
		cityListAdapter = new CityListAdapter(this, allCity_lists, city_hot, city_history, locateProcess, currentCity);
		cityListView = (ListView) findViewById(R.id.list_view_city);
		cityListView.setAdapter(cityListAdapter);
		cityListView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if (position >= 4) {
					Toast.makeText(getApplicationContext(), allCity_lists.get(position).getName(), Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		searchResultList = (ListView) findViewById(R.id.search_result);
		resultListAdapter = new ResultListAdapter(this, city_result);
		searchResultList.setAdapter(resultListAdapter);
		
		editText = (EditText) findViewById(R.id.edittext_city);
		editText.addTextChangedListener(this);
		txt_noresult = (TextView) findViewById(R.id.txt_noresult);
		
		//定位部分
		BdLocationClient.getInstance(getApplicationContext()).setNeedFresh(true);
		//isNeedFresh = true;
		mLocationClient = BdLocationClient.getInstance(getApplicationContext()).getLocationClient();
		//mLocationClient = new LocationClient(getApplicationContext());
		mMyLocationListener = new MyLocationListener();
		mLocationClient.registerLocationListener(mMyLocationListener);
		//InitLocation();
		mLocationClient.start();
		
	}


	@Override
	public void onTouchingLetterChanged(String s) {
		alphaIndexer = cityListAdapter.getAlphaIndexer();
		if (alphaIndexer.get(s) != null) {
			int position = alphaIndexer.get(s);
			cityListView.setSelection(position);
			overlayTextView.setText(s);
			overlayTextView.setVisibility(View.VISIBLE);
			handler.removeCallbacks(overlayThread);
			// 延迟一秒后执行，让overlay为不可见
			handler.postDelayed(overlayThread, 1000);
		}
	};
	
	
	/**
	 * 初始化汉语拼音首字母弹出提示框
	 */
	private void initOverlay() {
		//overlayReady = true;
		LayoutInflater inflater = LayoutInflater.from(this);
		overlayTextView = (TextView) inflater.inflate(R.layout.overlay, null);
		overlayTextView.setVisibility(View.INVISIBLE);
		WindowManager.LayoutParams lp = new WindowManager.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,
				WindowManager.LayoutParams.TYPE_APPLICATION,
				WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
				PixelFormat.TRANSLUCENT);
		WindowManager windowManager = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
		windowManager.addView(overlayTextView, lp);
	}

	/**
	 * @author Administrator
	 * 设置overlay不可见
	 */
	private class OverlayThread implements Runnable {
		
		public void run() {
			overlayTextView.setVisibility(View.GONE);
		}
	}

	
	private void cityInit() {
		City city = new City("定位", "0"); // 当前定位城市
		allCity_lists.add(city);
		city = new City("最近", "1"); // 最近访问的城市
		allCity_lists.add(city);
		city = new City("热门", "2"); // 热门城市
		allCity_lists.add(city);
		city = new City("全部", "3"); // 全部城市
		allCity_lists.add(city);
		city_lists = getCityList();
		allCity_lists.addAll(city_lists);
	}

	/**
	 * 热门城市
	 */
	public void hotCityInit() {
		City city = new City("上海", "2");
		city_hot.add(city);
		city = new City("北京", "2");
		city_hot.add(city);
		city = new City("广州", "2");
		city_hot.add(city);
		city = new City("深圳", "2");
		city_hot.add(city);
		city = new City("武汉", "2");
		city_hot.add(city);
		city = new City("天津", "2");
		city_hot.add(city);
		city = new City("西安", "2");
		city_hot.add(city);
		city = new City("南京", "2");
		city_hot.add(city);
		city = new City("杭州", "2");
		city_hot.add(city);
		city = new City("成都", "2");
		city_hot.add(city);
		city = new City("重庆", "2");
		city_hot.add(city);
	}

	private void hisCityInit() {
		city_history.add("重庆");
		city_history.add("深圳");
		/*try {
			DBHelper dbHelper = new DBHelper(this);
			dbHelper.createDataBase();
			SQLiteDatabase db = dbHelper.getWritableDatabase();
			Cursor cursor = db.rawQuery(
					"select * from recentcity order by date desc limit 0, 3", null);
			while (cursor.moveToNext()) {
				city_history.add(cursor.getString(1));
			}
			cursor.close();
			db.close();
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		
	}

	@SuppressWarnings("unchecked")
	private ArrayList<City> getCityList() {
		DBHelper dbHelper = new DBHelper(this);
		ArrayList<City> list = new ArrayList<City>();
		try {
			dbHelper.createDataBase();
			SQLiteDatabase db = dbHelper.getWritableDatabase();
			Cursor cursor = db.rawQuery("select * from city", null);
			City city;
			while (cursor.moveToNext()) {
				city = new City(cursor.getString(1), cursor.getString(2));
				list.add(city);
			}
			cursor.close();
			db.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Collections.sort(list, comparator);
		return list;
	}

	@SuppressWarnings("unchecked")
	private void getResultCityList(String keyword) {
		DBHelper dbHelper = new DBHelper(this);
		try {
			dbHelper.createDataBase();
			SQLiteDatabase db = dbHelper.getWritableDatabase();
			Cursor cursor = db.rawQuery(
					"select * from city where name like \"%" + keyword
							+ "%\" or pinyin like \"%" + keyword + "%\"", null);
			City city;
			Log.e("info", "length = " + cursor.getCount());
			while (cursor.moveToNext()) {
				city = new City(cursor.getString(1), cursor.getString(2));
				city_result.add(city);
			}
			cursor.close();
			db.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Collections.sort(city_result, comparator);
	}

	/**
	 * a-z排序
	 */
	@SuppressWarnings("rawtypes")
	Comparator comparator = new Comparator<City>() {
		@Override
		public int compare(City lhs, City rhs) {
			String a = lhs.getPinyi().substring(0, 1);
			String b = rhs.getPinyi().substring(0, 1);
			int flag = a.compareTo(b);
			if (flag == 0) {
				return a.compareTo(b);
			} else {
				return flag;
			}
		}
	};

	
	/**
	 * 实现实位回调监听
	 */
	public class MyLocationListener implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation arg0) {
			Log.e("info", "city = " + arg0.getCity());
			boolean isNeedFresh = BdLocationClient.getInstance(getApplicationContext()).isNeedFresh();
			if (!isNeedFresh) {
				return;
			}
			isNeedFresh = false;
			if (arg0.getCity() == null) {
				locateProcess = 3; // 定位失败
			}else {
				currentCity = arg0.getCity().substring(0, arg0.getCity().length() - 1);
				locateProcess = 2; // 定位成功
				cityListAdapter.setCurrentCity(currentCity);
			}
			cityListAdapter.setLocateProcess(locateProcess);
			cityListAdapter.notifyDataSetChanged();
		}

		@Override
		public void onReceivePoi(BDLocation arg0) {

		}
	}
	

	@Override
	public void afterTextChanged(Editable s) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		if (s.toString() == null || "".equals(s.toString())) {
			letterListView.setVisibility(View.VISIBLE);
			cityListView.setVisibility(View.VISIBLE);
			searchResultList.setVisibility(View.GONE);
			txt_noresult.setVisibility(View.GONE);
		} else {
			city_result.clear();
			letterListView.setVisibility(View.GONE);
			cityListView.setVisibility(View.GONE);
			getResultCityList(s.toString());
			if (city_result.size() <= 0) {
				txt_noresult.setVisibility(View.VISIBLE);
				searchResultList.setVisibility(View.GONE);
			} else {
				txt_noresult.setVisibility(View.GONE);
				searchResultList.setVisibility(View.VISIBLE);
				resultListAdapter.notifyDataSetChanged();
			}
		}
	}
	
}
