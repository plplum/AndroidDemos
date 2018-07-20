package com.example.demos.refreshview.activity;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.GridView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.demos.R;
import com.example.demos.gridview.MyGridAdapter;
import com.example.demos.gridview.MyGridView;
import com.example.demos.refreshview.view.PullToRefreshBase;
import com.example.demos.refreshview.view.PullToRefreshBase.OnRefreshListener;
import com.example.demos.refreshview.view.PullToRefreshScrollView;

public class RefreshableScrollViewActivity extends Activity {
    
	private ScrollView mScrollView;
	private PullToRefreshScrollView pullToRefreshScrollView = null;
	private SimpleDateFormat mDateFormat = new SimpleDateFormat("MM-dd HH:mm");
	
	MyGridAdapter adapter;
	
	
	public String[] img_text = { "转账", "余额宝", "手机充值", "信用卡还款", "淘宝电影", "彩票",
			"当面付", "亲密付", "机票" };
	public int[] imgs = { R.drawable.app_transfer, R.drawable.app_fund,
			R.drawable.app_phonecharge, R.drawable.app_creditcard,
			R.drawable.app_movie, R.drawable.app_lottery,
			R.drawable.app_facepay, R.drawable.app_close, R.drawable.app_plane };
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refreshable_scrollview);
        
        pullToRefreshScrollView = (PullToRefreshScrollView) findViewById(R.id.refresh_scrolllist);
        
        pullToRefreshScrollView.setOnRefreshListener(new OnRefreshListener<ScrollView>() {

            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                new GetDataTask().execute();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                
            }
        });
        
        mScrollView = pullToRefreshScrollView.getRefreshableView();
      //  mScrollView.addView(createTextView());
        View view = getLayoutInflater().inflate(R.layout.activity_gridview_main, null);
        
        adapter = new MyGridAdapter(this, img_text, imgs);
        GridView gridview=(MyGridView)view.findViewById(R.id.gridview);
		gridview.setAdapter(adapter);
        
		gridview.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
				Toast.makeText(RefreshableScrollViewActivity.this, "测试"+position, Toast.LENGTH_SHORT).show();
				return false;
			}
		});
        mScrollView.addView(view);
        setLastUpdateTime();
    }
    
    private class GetDataTask extends AsyncTask<Void, Void, String[]> {

        @Override
        protected String[] doInBackground(Void... params) {
            // Simulates a background job.
          /*  try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
            }*/
            return null;
        }

        @Override
        protected void onPostExecute(String[] result) {
        	pullToRefreshScrollView.onPullDownRefreshComplete();
            setLastUpdateTime();
            
            MyGridAdapter.img_text = new String[]{ "转账", "余额宝", "手机充值", "信用卡还款", "淘宝电影", "彩票",
        			"当面付", "亲密付", "机票", "机票", "机票", "机票", "机票", "机票", "机票", "机票", "机票", "机票" };

            MyGridAdapter.imgs = 	new int[]{ R.drawable.ic_launcher, R.drawable.ic_launcher,
        			R.drawable.ic_launcher, R.drawable.ic_launcher,
        			R.drawable.ic_launcher, R.drawable.ic_launcher,
        			R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher };
	
            adapter.notifyDataSetChanged();
            super.onPostExecute(result);
        }
    }
    private void setLastUpdateTime() {
        String text = formatDateTime(System.currentTimeMillis());
        pullToRefreshScrollView.setLastUpdatedLabel(text);
    }
    
    private String formatDateTime(long time) {
        if (0 == time) {
            return "";
        }
        
        return mDateFormat.format(new Date(time));
    }
    
    private TextView createTextView() {
        TextView textView = new TextView(this);
        
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < 2; ++i) {
            sb.append(String.format(" %03d", i)).append("\n");
        }
        
        textView.setText(sb.toString());
        textView.setTextColor(Color.WHITE);
        textView.setTextColor(Color.BLACK);
        textView.setTextSize(18);
        
        return textView;
    }
}
