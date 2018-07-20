package com.example.demos.fragmentmenu;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.demos.R;

public class MainActivity extends FragmentActivity implements OnClickListener {
	private RadioGroup group;
	private RadioButton kindBtn, distanceBtn, sortBtn;
	private KindFragment kindFragment;
	private DistanceFragment distanceFragment;
	private SortFragment sortFragment;
	private Fragment fragment;

	/**
	 * 用于接收从Fragment中发送来的消息
	 */
	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case 0:// 分类发来的消息 
				Toast.makeText(MainActivity.this, msg.obj.toString(),
						Toast.LENGTH_SHORT).show();
				getSupportFragmentManager().beginTransaction().remove(fragment)
						.commit();
				fragment = null;
				group.clearCheck();
				break;
			case 1:// 范围发来的消息
				Toast.makeText(MainActivity.this, msg.obj.toString(),
						Toast.LENGTH_SHORT).show();
				getSupportFragmentManager().beginTransaction().remove(fragment)
						.commit();
				fragment = null;
				group.clearCheck();
				break;
			case 2:// 排序发来的消息
				Toast.makeText(MainActivity.this, msg.obj.toString(),
						Toast.LENGTH_SHORT).show();
				getSupportFragmentManager().beginTransaction().remove(fragment)
						.commit();
				fragment = null;
				group.clearCheck();
				break;
			}
		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fragment_menu_main);
		kindFragment = new KindFragment();
		kindFragment.setHandler(handler);
		distanceFragment = new DistanceFragment();
		distanceFragment.setHandler(handler);
		sortFragment = new SortFragment();
		sortFragment.setHandler(handler);
		init();
	}

	/**
	 * 初始化控件
	 */
	private void init() {
		// TODO Auto-generated method stub
		group = (RadioGroup) findViewById(R.id.group);
		kindBtn = (RadioButton) findViewById(R.id.kind);
		kindBtn.setOnClickListener(this);
		distanceBtn = (RadioButton) findViewById(R.id.distance);
		distanceBtn.setOnClickListener(this);
		sortBtn = (RadioButton) findViewById(R.id.sort);
		sortBtn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.kind:
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.fragment_container, kindFragment).commit();
			fragment = kindFragment;
			break;
		case R.id.distance:
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.fragment_container, distanceFragment)
					.commit();
			fragment = distanceFragment;
			break;
		case R.id.sort:
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.fragment_container, sortFragment).commit();
			fragment = sortFragment;
			break;
		}
	}

	/**
	 * 监控手机返回按键，如果此时Fragment存在就把它移除，不存在就直接关闭界面
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			if (fragment != null) {
				getSupportFragmentManager().beginTransaction().remove(fragment)
						.commit();
				fragment = null;
				group.clearCheck();
			} else
				finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}
