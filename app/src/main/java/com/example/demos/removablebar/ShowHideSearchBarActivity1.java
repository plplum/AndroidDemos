package com.example.demos.removablebar;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.example.demos.R;
import com.example.demos.removablebar.view.MyScrollView;
import com.example.demos.removablebar.view.MyScrollView.OnScrollListener;

public class ShowHideSearchBarActivity1 extends Activity implements OnScrollListener{
	
	//自定义的ScrollView，实现了滚动事件
	private MyScrollView scrollView;
	
	private LinearLayout titleBarTop;
	private LinearLayout titleBarCenter;
	private FrameLayout frameLayout;
	
	private boolean isTopbarShow = true;
	
	private long lastScrollY;
	
	private Handler handler = new Handler(){
		public void handleMessage(Message msg) {
			int scrollY = scrollView.getScrollY();
			if (lastScrollY != scrollY) {
				//lastScrollY = scrollY;
				handler.sendMessageDelayed(handler.obtainMessage(), 5);
			}
			if (scrollY >= (frameLayout.getHeight() - titleBarCenter.getHeight())) {
				if(isTopbarShow){
					//titleBarTop.startAnimation(animationShow);
					titleBarTop.setVisibility(View.VISIBLE);
					titleBarCenter.setVisibility(View.GONE);
					isTopbarShow = false;
				}
			} else{
				if(!isTopbarShow){
					titleBarTop.setVisibility(View.GONE);
					titleBarCenter.setVisibility(View.VISIBLE);
					//titleBarTop.startAnimation(animationHide);
					isTopbarShow = true;
				}
			}
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_removable_bar1);
		scrollView = (MyScrollView) findViewById(R.id.content_scrollview);
		scrollView.setOnScrollListener(this);
		titleBarTop = (LinearLayout) findViewById(R.id.title_bar);
		titleBarCenter = (LinearLayout) findViewById(R.id.sub_title_bar);
		frameLayout = (FrameLayout) findViewById(R.id.main_layout);
	}


	@Override
	public void onScroll(int y) {
		lastScrollY = y;
		handler.sendMessageDelayed(handler.obtainMessage(), 5);
	}
	
}
