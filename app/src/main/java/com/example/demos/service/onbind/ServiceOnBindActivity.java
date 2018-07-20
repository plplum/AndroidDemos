package com.example.demos.service.onbind;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.example.demos.R;

public class ServiceOnBindActivity extends Activity implements OnClickListener{

	private Button playBtn;
	private Button stopBtn;
	private Button pauseBtn;
	private Button exitBtn;
	private Button backBtn;
	
	private BindMusicService musicService;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_service);
		playBtn = (Button) findViewById(R.id.button11);
		playBtn.setOnClickListener(this);
		stopBtn = (Button) findViewById(R.id.button21);
		stopBtn.setOnClickListener(this);
		pauseBtn = (Button) findViewById(R.id.button31);
		pauseBtn.setOnClickListener(this);
		exitBtn = (Button) findViewById(R.id.button41);
		exitBtn.setOnClickListener(this);
		backBtn = (Button) findViewById(R.id.button51);
		backBtn.setOnClickListener(this);
		
		Intent intent = new Intent(ServiceOnBindActivity.this, BindMusicService.class);
		// bindService
		bindService(intent, sc, Context.BIND_AUTO_CREATE);			
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button11:
			musicService.play();
			break;
		case R.id.button21:
			if (musicService != null) {
				musicService.pause();
			}
			break;
		case R.id.button31:
			if (musicService != null) {
				musicService.stop();
			}
			break;
		case R.id.button41:
			this.finish();
			break;
		case R.id.button51:
			this.finish();
			break;
		default:
			break;
		}
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		/*
		if(sc != null){
			unbindService(sc);
		}*/
	}
	
	private ServiceConnection sc = new ServiceConnection() {

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			musicService = ((BindMusicService.MyBinder) (service)).getService();
			if (musicService != null) {
				musicService.play();
			}
		}
		
		@Override
		public void onServiceDisconnected(ComponentName name) {
			musicService = null;
		}
	};
	
}
