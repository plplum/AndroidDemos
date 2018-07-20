package com.example.demos.removablebar;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.example.demos.R;

public class ShowHideSearchBarActivity extends Activity	implements OnTouchListener{
	
	private ScrollView scrollView;
	
	private LinearLayout titleBarTop;
	private LinearLayout titleBarCenter;
	private FrameLayout frameLayout;
	
	private Animation animationShow;
	private Animation animationHide;
	
	
	private long lastScrollY;
	
	private Handler handler = new Handler(){
		public void handleMessage(Message msg) {
			if (lastScrollY != scrollView.getScrollY()) {
				handler.sendMessageDelayed(handler.obtainMessage(), 5);
			}
			if (scrollView.getScrollY() >= (frameLayout.getHeight() - titleBarCenter.getHeight())) {
				//titleBarTop.startAnimation(animationShow);
				titleBarTop.setVisibility(View.VISIBLE);
				titleBarCenter.setVisibility(View.GONE);
			} else{
				titleBarTop.setVisibility(View.GONE);
				titleBarCenter.setVisibility(View.VISIBLE);
				//titleBarTop.startAnimation(animationHide);
			}
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_removable_bar);
		scrollView = (ScrollView) findViewById(R.id.content_scrollview);
		scrollView.setOnTouchListener(this);
		titleBarTop = (LinearLayout) findViewById(R.id.title_bar);
		titleBarCenter = (LinearLayout) findViewById(R.id.sub_title_bar);
		frameLayout = (FrameLayout) findViewById(R.id.main_layout);
		
		/*animationShow = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,   
                 Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,   
                 -1.0f, Animation.RELATIVE_TO_SELF, 0.0f);   
		animationShow.setDuration(500); 
		
		
		animationHide  = new TranslateAnimation(Animation.RELATIVE_TO_SELF,   
                0.0f, Animation.RELATIVE_TO_SELF, 0.0f,   
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,   
                -1.0f);  
		animationHide.setDuration(500);   */
		
	}

	public boolean onTouch(View v, MotionEvent event) {
		if(event.getAction() == MotionEvent.ACTION_MOVE){
			lastScrollY = scrollView.getScrollY();
			handler.sendMessageDelayed(handler.obtainMessage(), 5);
		}
		return false;
	}

	
	
}
