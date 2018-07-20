package com.example.demos.service.onbind;

import java.io.IOException;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;

import com.example.demos.R;


public class BindMusicService extends Service {

	private MediaPlayer mediaPlayer;

	private final IBinder binder = new MyBinder();

	public class MyBinder extends Binder {
		BindMusicService getService() {
			return BindMusicService.this;
		}
	}

	@Override
	public IBinder onBind(Intent intent) {
		return binder;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		
		if(mediaPlayer != null){
			mediaPlayer.stop();
			mediaPlayer.release();
		}
	}

	public void play() {
		if (mediaPlayer == null) {
			mediaPlayer = MediaPlayer.create(this, R.raw.tmp);
			mediaPlayer.setLooping(false);
		}
		if (!mediaPlayer.isPlaying()) {
			mediaPlayer.start();
		}
	}

	public void pause() {
		if (mediaPlayer != null && mediaPlayer.isPlaying()) {
			mediaPlayer.pause();
		}
	}

	public void stop() {
		if (mediaPlayer != null) {
			mediaPlayer.stop();
			try {
				mediaPlayer.prepare();// 在调用stop后如果需要再次通过start进行播放,需要之前调用prepare函数
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

}
