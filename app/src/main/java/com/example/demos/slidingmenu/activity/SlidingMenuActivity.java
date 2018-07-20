package com.example.demos.slidingmenu.activity;

import com.example.demos.R;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

import android.graphics.Canvas;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class SlidingMenuActivity extends SlidingFragmentActivity implements OnClickListener{

	SlidingMenu sm;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.slidingmenu_activity_main);
		
		sm = getSlidingMenu();
		
		setBehindContentView(R.layout.slidingmenu_menu_frame1);
		
		Button button = (Button) findViewById(R.id.menu_button);
		button.setOnClickListener(this);
		
		Button button2 = (Button) findViewById(R.id.button1);
		button2.setOnClickListener(this);
		
		sm.setSlidingEnabled(true);
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
	
		// customize the SlidingMenu
		sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		sm.setFadeEnabled(false);
		sm.setBehindScrollScale(0.25f);
		sm.setFadeDegree(0.25f);

		sm.setBackgroundImage(R.drawable.img_frame_background);
		sm.setBehindCanvasTransformer(new SlidingMenu.CanvasTransformer() {
			@Override
			public void transformCanvas(Canvas canvas, float percentOpen) {
				float scale = (float) (percentOpen * 0.25 + 0.75);
				canvas.scale(scale, scale, -canvas.getWidth() / 2,
						canvas.getHeight() / 2);
			}
		});
		
		sm.setAboveCanvasTransformer(new SlidingMenu.CanvasTransformer() {
			@Override
			public void transformCanvas(Canvas canvas, float percentOpen) {
				float scale = (float) (1 - percentOpen * 0.25);
				canvas.scale(scale, scale, 0, canvas.getHeight() / 2);
			}
		});
		
		
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.menu_button:
			Toast toast=Toast.makeText(getApplicationContext(), "按钮被点击！", Toast.LENGTH_SHORT);
			toast.show();
			break;
		case R.id.button1:
			sm.showMenu();
		break;
		default:
			break;
		}
	}
}
