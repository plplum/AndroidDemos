package com.example.demos.pulltorefresh;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.demos.R;
import com.example.demos.pulltorefresh.RefreshableView.PullToRefreshListener;

public class PullToRefreshActivity extends Activity {

	RefreshableView refreshableView;
	ListView listView;
	ArrayAdapter<String> adapter;
	String[] items = { "0.15", "0.19", "0.17" };
	
	
	List<String> dataList = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_pull_to_refresh);
		refreshableView = (RefreshableView) findViewById(R.id.refreshable_view);
		listView = (ListView) findViewById(R.id.list_view);
		
		for (String data : items) {
			dataList.add(data);
		}
		
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dataList);
		listView.setAdapter(adapter);
		refreshableView.setOnRefreshListener(new PullToRefreshListener() {
			@Override
			public void onRefresh() {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				new UpdateDataTask().execute();
				refreshableView.finishRefreshing();
			}
		}, 0);
	}

	
	class UpdateDataTask extends AsyncTask<Void, Void, String[]>{

		@Override
		protected String[] doInBackground(Void... params) {
			dataList.clear();
			dataList.add(String.valueOf(new Random().nextFloat()));
			dataList.add(String.valueOf(new Random().nextFloat()));
			dataList.add(String.valueOf(new Random().nextFloat()));
			return items;
		}
		
		@Override
		protected void onPostExecute(String[] result) {
			adapter.notifyDataSetChanged();
			super.onPostExecute(result);
		}
		
	}
	
	
}
