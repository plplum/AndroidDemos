package com.example.demos.removablebar.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

public class MyScrollView extends ScrollView{
	
	private OnScrollListener listener;

	public MyScrollView(Context context) {
		super(context, null);
	}

	public MyScrollView(Context context, AttributeSet attrs) {  
        this(context, attrs, 0);  
    }  
  
    public MyScrollView(Context context, AttributeSet attrs, int defStyle) {  
        super(context, attrs, defStyle);  
    }  	
    
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		if (listener == null) {
			return false;
		}
		if(ev.getAction() == MotionEvent.ACTION_MOVE){
			listener.onScroll(this.getScrollY());
		}
		return super.onTouchEvent(ev);
	}
	
	
	public void setOnScrollListener(OnScrollListener listener) {
		this.listener = listener;
	}



	/**
	 * ScrollView 滚动事件
	 * @author Administrator
	 *
	 */
	public interface OnScrollListener {
		
		/**
		 * @param y y坐标的值
		 */
		public void onScroll(int y);
			
		
	}
	
}
