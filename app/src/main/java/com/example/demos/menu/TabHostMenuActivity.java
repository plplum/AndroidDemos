package com.example.demos.menu;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RadioButton;
import android.widget.TabHost;

import com.example.demos.R;

public class TabHostMenuActivity extends TabActivity implements
		OnCheckedChangeListener, OnTouchListener {

	private TabHost mTabHost;
	private Intent mAIntent;
	private Intent mBIntent;
	private Intent mCIntent;
	private Intent mDIntent;
	private Intent mEIntent;
	
	//控制手势滑动
	private GestureDetector gestureDetector;  
	private int currentView = 0;  
    private static int maxTabIndex = 4;  
    
    private RadioButton[] radioButtons;
    private RadioButton radioButton0;
    private RadioButton radioButton1;
    private RadioButton radioButton2;
    private RadioButton radioButton3;
    private RadioButton radioButton4;
    
    
    
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.tabhost_layout);

		this.mAIntent = new Intent(this, AActivity.class);
		this.mBIntent = new Intent(this, BActivity.class);
		this.mCIntent = new Intent(this, CActivity.class);
		this.mDIntent = new Intent(this, DActivity.class);
		this.mEIntent = new Intent(this, EActivity.class);

		radioButton0 = ((RadioButton) findViewById(R.id.radio_button0));
		radioButton0.setOnCheckedChangeListener(this);
		radioButton1 = ((RadioButton) findViewById(R.id.radio_button1));
		radioButton1.setOnCheckedChangeListener(this);
		radioButton2 = ((RadioButton) findViewById(R.id.radio_button2));
		radioButton2.setOnCheckedChangeListener(this);
		radioButton3 = ((RadioButton) findViewById(R.id.radio_button3));
		radioButton3.setOnCheckedChangeListener(this);
		radioButton4 = ((RadioButton) findViewById(R.id.radio_button4));
		radioButton4.setOnCheckedChangeListener(this);
		
		radioButtons = new RadioButton[]{radioButton0, radioButton1, radioButton2, radioButton3, radioButton4};
		
		setupIntent();
		
		
		gestureDetector = new GestureDetector(this,new MyGestureDetector());  
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		if (isChecked) {
			switch (buttonView.getId()) {
			case R.id.radio_button0:
				this.mTabHost.setCurrentTabByTag("A_TAB");
				currentView = 0;
				break;
			case R.id.radio_button1:
				this.mTabHost.setCurrentTabByTag("B_TAB");
				currentView = 1;
				break;
			case R.id.radio_button2:
				this.mTabHost.setCurrentTabByTag("C_TAB");
				currentView = 2;
				break;
			case R.id.radio_button3:
				this.mTabHost.setCurrentTabByTag("D_TAB");
				currentView = 3;
				break;
			case R.id.radio_button4:
				this.mTabHost.setCurrentTabByTag("MORE_TAB");
				currentView = 4;
				break;
			}
		}

	}

	private void setupIntent() {
		this.mTabHost = getTabHost();
		TabHost localTabHost = this.mTabHost;

		localTabHost.addTab(buildTabSpec("A_TAB", R.string.main_home,
				R.drawable.icon_1_n, this.mAIntent));

		localTabHost.addTab(buildTabSpec("B_TAB", R.string.main_news,
				R.drawable.icon_2_n, this.mBIntent));

		localTabHost.addTab(buildTabSpec("C_TAB", R.string.main_manage_date,
				R.drawable.icon_3_n, this.mCIntent));

		localTabHost.addTab(buildTabSpec("D_TAB", R.string.main_friends,
				R.drawable.icon_4_n, this.mDIntent));

		localTabHost.addTab(buildTabSpec("MORE_TAB", R.string.more,
				R.drawable.icon_5_n, this.mEIntent));

	}

	private TabHost.TabSpec buildTabSpec(String tag, int resLabel, int resIcon,
			final Intent content) {
		return this.mTabHost
				.newTabSpec(tag)
				.setIndicator(getString(resLabel),
						getResources().getDrawable(resIcon))
				.setContent(content);
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if (gestureDetector.onTouchEvent(event)) {  
            return true;  
        }  
        return false;  
	}
	
	// 左右滑动刚好页面也有滑动效果
	class MyGestureDetector extends SimpleOnGestureListener {
		private static final int SWIPE_MIN_DISTANCE = 120;
		private static final int SWIPE_MAX_OFF_PATH = 250;
		private static final int SWIPE_THRESHOLD_VELOCITY = 200;

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			TabHost tabHost = getTabHost();
			try {
				if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
					return false;
				// right to left swipe
				if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
					if (currentView == maxTabIndex) {
						currentView = 0;
					} else {
						currentView++;
					}
					tabHost.setCurrentTab(currentView);
					radioButtons[currentView].setChecked(true);
				} else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
					if (currentView == 0) {
						currentView = maxTabIndex;
					} else {
						currentView--;
					}
					tabHost.setCurrentTab(currentView);
					radioButtons[currentView].setChecked(true);
				}
			} catch (Exception e) {
			}
			return false;
		}
	}
  
    @Override  
    public boolean dispatchTouchEvent(MotionEvent event) {  
        if (gestureDetector.onTouchEvent(event)) {  
            event.setAction(MotionEvent.ACTION_CANCEL);  
        }  
        return super.dispatchTouchEvent(event);  
    }  
  
}
