package com.example.demos.popupwindow;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.demos.R;
import com.example.demos.popupwindow.view.AddPopWindow;

public class PopupWindowActivity extends Activity implements OnClickListener{
	
	private Button setButton;
	private Button addButton;
	private Button menuButton;
	private PopupWindow popupWindow;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_popupwindow);
		
		setButton = (Button) findViewById(R.id.btnSet);
		addButton = (Button) findViewById(R.id.btnAdd);
		menuButton = (Button) findViewById(R.id.button1);
		setButton.setOnClickListener(this);
		addButton.setOnClickListener(this);
		menuButton.setOnClickListener(this);
		
		View contentView = getLayoutInflater().inflate(R.layout.activity_pop_dialog_menu, null);
		popupWindow = new PopupWindow(contentView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		//popupWindow = new PopupWindow(contentView, ViewGroup.LayoutParams.MATCH_PARENT, 500);
		/*popupWindow.setOutsideTouchable(true);
		popupWindow.setFocusable(false);
		*/
		
		
		
		popupWindow.setFocusable(true);//这里必须设置为true才能点击区域外或者消失

		popupWindow.setTouchable(true);//这个控制PopupWindow内部控件的点击事件
		popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));

		popupWindow.setOutsideTouchable(true);

		popupWindow.update();
		
		popupWindow.setAnimationStyle(R.style.pop_animation);

		TextView mServerLogin = (TextView) contentView.findViewById(R.id.mServerLogin);
		mServerLogin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (popupWindow.isShowing())
					popupWindow.dismiss();
			}
		});
	}

	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnSet:
			//MorePopWindow morePopWindow = new MorePopWindow(MainActivity.this);
			//morePopWindow.showPopupWindow(setButton);
			break;
		case R.id.btnAdd:
			AddPopWindow addPopWindow = new AddPopWindow(PopupWindowActivity.this);
			addPopWindow.showPopupWindow(addButton);
			break;
		case R.id.button1:
			if(!popupWindow.isShowing()){
				popupWindow.showAtLocation(menuButton, Gravity.BOTTOM, 0, 0);
				//popupWindow.showAtLocation(menuButton, Gravity.TOP, 0, 148);
			}else {
				popupWindow.dismiss();
			}
			break;
		default:
			break;
		}
	}
}
