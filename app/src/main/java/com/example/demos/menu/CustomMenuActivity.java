package com.example.demos.menu;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.demos.R;

public class CustomMenuActivity extends Activity implements OnClickListener{
	
	private Button button1;
	private PopupWindow window;
	private LinearLayout taskLayout;
	private RelativeLayout menuLayout;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu_main);
		
		TextView textView = (TextView) findViewById(R.id.textView1);
		textView.setText("Try to click menu button");
		
		View popupView = LayoutInflater.from(CustomMenuActivity.this).inflate(R.layout.custom_menu_layout, null);
		window = new PopupWindow(popupView, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		window.setFocusable(true);
		window.setBackgroundDrawable(new ColorDrawable(0));
		window.update();
		
		taskLayout = (LinearLayout) popupView.findViewById(R.id.add_task_layout);
		taskLayout.setOnClickListener(this);
		
		menuLayout = (RelativeLayout) popupView.findViewById(R.id.layout_custom_menu);
		menuLayout.setFocusableInTouchMode(true);
		menuLayout.setOnKeyListener(new View.OnKeyListener() {
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_MENU && window.isShowing()) {
					window.dismiss();
					return true;
				}
				return false;
			}
		});
		
		button1 = (Button) findViewById(R.id.button1);
		button1.setVisibility(View.INVISIBLE);
		
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_MENU && window.isShowing()){
			window.dismiss();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add("menu");
		return super.onCreateOptionsMenu(menu);
	}
	
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button1:
			break;
		case R.id.add_task_layout:
			Toast.makeText(this, "add_task_layout event", Toast.LENGTH_SHORT).show();
			if (window != null) {
				if (window.isShowing())
					window.dismiss();
			}
			break;
		default:
			break;
		}
	}
	
	@Override
	public boolean onMenuOpened(int featureId, Menu menu) {
		if (window != null) {
			if (window.isShowing())
				window.dismiss();
			else {
				window.showAtLocation(findViewById(R.id.button1), Gravity.BOTTOM, 0, 0);
			}
		}
		return false;// 返回为true 则显示系统menu
		// return super.onMenuOpened(featureId, menu);
	}
	
}
