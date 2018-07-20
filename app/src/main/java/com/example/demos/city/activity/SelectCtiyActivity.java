package com.example.demos.city.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.demos.R;
import com.example.demos.city.apapter.CityAdapter;
import com.example.demos.city.apapter.SearchCityAdapter;
import com.example.demos.city.app.Application;
import com.example.demos.city.bean.City;
import com.example.demos.city.plistview.BladeView;
import com.example.demos.city.plistview.BladeView.OnItemClickListener;
import com.example.demos.city.plistview.PinnedHeaderListView;
import com.example.demos.city.util.L;
import com.example.demos.contacts.model.Contact;

public class SelectCtiyActivity  extends Activity implements TextWatcher,
		OnClickListener {
	private EditText mSearchEditText;
	private ImageButton mClearSearchBtn;
	private View mCityContainer;
	private View mSearchContainer;
	private PinnedHeaderListView mCityListView;
	private BladeView mLetter;
	private ListView mSearchListView;
	private List<City> mCities;
	private SearchCityAdapter mSearchCityAdapter;
	private CityAdapter mCityAdapter;
	// 首字母集
	private List<String> mSections;
	// 根据首字母存放数�?	
	private Map<String, List<City>> mMap;
	// 首字母位置集
	private List<Integer> mPositions;
	// 首字母对应的位置
	private Map<String, Integer> mIndexer;
	private Application mApplication;
	private InputMethodManager mInputMethodManager;

	private TextView mTitleTextView;
	private ImageView mBackBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.biz_plugin_weather_select_city);
		initView();
		initData();

	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	private void initView() {
		mTitleTextView = (TextView) findViewById(R.id.title_name);
		mBackBtn = (ImageView) findViewById(R.id.title_back);
		mBackBtn.setOnClickListener(this);
		mTitleTextView.setText(Application.getInstance().getSharePreferenceUtil().getCity());

		mSearchEditText = (EditText) findViewById(R.id.search_edit);
		mSearchEditText.addTextChangedListener(this);
		mClearSearchBtn = (ImageButton) findViewById(R.id.ib_clear_text);
		mClearSearchBtn.setOnClickListener(this);

		mCityContainer = findViewById(R.id.city_content_container);
		mSearchContainer = findViewById(R.id.search_content_container);
		mCityListView = (PinnedHeaderListView) findViewById(R.id.citys_list);
		mCityListView.setEmptyView(findViewById(R.id.citys_list_empty));
		mLetter = (BladeView) findViewById(R.id.citys_bladeview);
		mLetter.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(String s) {
				if (mIndexer.get(s) != null) {
					mCityListView.setSelection(mIndexer.get(s));
				}
			}
		});
		mLetter.setVisibility(View.GONE);
		mSearchListView = (ListView) findViewById(R.id.search_list);
		mSearchListView.setEmptyView(findViewById(R.id.search_empty));
		mSearchContainer.setVisibility(View.GONE);
		mSearchListView.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				mInputMethodManager.hideSoftInputFromWindow(
						mSearchEditText.getWindowToken(), 0);
				return false;
			}
		});
		mCityListView
				.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						L.i(mCityAdapter.getItem(position).toString());
						startActivity(mCityAdapter.getItem(position));
					}
				});

		mSearchListView
				.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						L.i(mSearchCityAdapter.getItem(position).toString());
						startActivity(mSearchCityAdapter.getItem(position));
					}
				});
	}

	private void startActivity(City city) {
		Intent i = new Intent();
		i.putExtra("city", city);
		setResult(RESULT_OK, i);
		finish();
	}

	private void initData() {
		mApplication = Application.getInstance();
		mInputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
		mCities = mApplication.getCityList();
		mSections = mApplication.getSections();
		mMap = mApplication.getMap();
		mPositions = mApplication.getPositions();
		mIndexer = mApplication.getIndexer();

		mCityAdapter = new CityAdapter(SelectCtiyActivity.this, mCities, mMap,
				mSections, mPositions);
		mCityListView.setAdapter(mCityAdapter);
		mCityListView.setOnScrollListener(mCityAdapter);
		mCityListView.setPinnedHeaderView(LayoutInflater.from(
				SelectCtiyActivity.this).inflate(
				R.layout.biz_plugin_weather_list_group_item, mCityListView,
				false));
		mLetter.setVisibility(View.VISIBLE);

	}

	
	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		// do nothing
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		mSearchCityAdapter = new SearchCityAdapter(SelectCtiyActivity.this,
				mCities);
		mSearchListView.setAdapter(mSearchCityAdapter);
		mSearchListView.setTextFilterEnabled(true);
		if (mCities.size() < 1 || TextUtils.isEmpty(s)) {
			mCityContainer.setVisibility(View.VISIBLE);
			mSearchContainer.setVisibility(View.INVISIBLE);
			mClearSearchBtn.setVisibility(View.GONE);
		} else {
			mClearSearchBtn.setVisibility(View.VISIBLE);
			mCityContainer.setVisibility(View.INVISIBLE);
			mSearchContainer.setVisibility(View.VISIBLE);
			mSearchCityAdapter.getFilter().filter(s);
		}
	}

	@Override
	public void afterTextChanged(Editable s) {
		// 如何搜索字符串长度为0，是否隐藏输入法
		// if(TextUtils.isEmpty(s)){
		// mInputMethodManager.hideSoftInputFromWindow(
		// mSearchEditText.getWindowToken(), 0);
		// }

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ib_clear_text:
			if (!TextUtils.isEmpty(mSearchEditText.getText().toString())) {
				mSearchEditText.setText("");
				mInputMethodManager.hideSoftInputFromWindow(
						mSearchEditText.getWindowToken(), 0);
			}
			break;
		case R.id.title_back:
			finish();
			break;
		default:
			break;
		}
	}

	

}
