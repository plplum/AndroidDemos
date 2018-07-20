package com.example.demos.service.onstart;

import com.example.demos.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ServiceOnStartActivity extends Activity implements OnClickListener{

	private Button playBtn;
	private Button stopBtn;
	private Button pauseBtn;
	private Button exitBtn;
	private Button backBtn;
	
	private Intent musicServiceIntent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_service);
		playBtn = (Button) findViewById(R.id.button1);
		playBtn.setOnClickListener(this);
		stopBtn = (Button) findViewById(R.id.button2);
		stopBtn.setOnClickListener(this);
		pauseBtn = (Button) findViewById(R.id.button3);
		pauseBtn.setOnClickListener(this);
		exitBtn = (Button) findViewById(R.id.button4);
		exitBtn.setOnClickListener(this);
		backBtn = (Button) findViewById(R.id.button5);
		backBtn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		int operationFlag = -1;
	//	musicServiceIntent = new Intent("com.example.demos.service.onstart.playMusicService");
		musicServiceIntent = new Intent(ServiceOnStartActivity.this, PlayMusicService.class);
		
		switch (v.getId()) {
		case R.id.button1:
			operationFlag = 0;
			break;
		case R.id.button2:
			operationFlag = 1;
			break;
		case R.id.button3:
			operationFlag = 2;
			break;
		case R.id.button4:
			stopService(musicServiceIntent);
			this.finish();
			break;
		case R.id.button5:
			this.finish();
			break;

		default:
			break;
		}
		
		Bundle bundle = new Bundle();
		bundle.putInt("operationFlag", operationFlag);
		musicServiceIntent.putExtras(bundle);
		startService(musicServiceIntent);
	}
	
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		/*if (musicServiceIntent != null) {
			stopService(musicServiceIntent);
		}*/

	}
}
