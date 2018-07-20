package com.example.demos.gridview;

import com.example.demos.R;

import android.app.Activity;
import android.os.Bundle;
/**
 * @Description:主页
 * @author http://blog.csdn.net/finddreams
 */ 
public class MainActivity extends Activity{
	private MyGridView gridview;
	
	public String[] img_text = { "转账", "余额宝", "手机充值", "信用卡还款", "淘宝电影", "彩票",
			"当面付", "亲密付", "机票" };
	public int[] imgs = { R.drawable.app_transfer, R.drawable.app_fund,
			R.drawable.app_phonecharge, R.drawable.app_creditcard,
			R.drawable.app_movie, R.drawable.app_lottery,
			R.drawable.app_facepay, R.drawable.app_close, R.drawable.app_plane };
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gridview_main);
		initView();
	}

	private void initView() {
		gridview=(MyGridView) findViewById(R.id.gridview);
		gridview.setAdapter(new MyGridAdapter(this, img_text, imgs));
		
	}
}
