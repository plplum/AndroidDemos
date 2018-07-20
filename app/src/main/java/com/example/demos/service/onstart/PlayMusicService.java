package com.example.demos.service.onstart;

import java.io.IOException;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.example.demos.R;

public class PlayMusicService extends Service {
	private static final String TAG = "PlayMusicService";

	private MediaPlayer mediaPlayer;

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public void onCreate() {
		Log.v(TAG, "onCreate");
		if (mediaPlayer == null) {
			mediaPlayer = MediaPlayer.create(this, R.raw.tmp);
			mediaPlayer.setLooping(false);
		}
	}

	@Override
	public void onDestroy() {
		Log.v(TAG, "onDestroy");
		if (mediaPlayer != null) {
			mediaPlayer.stop();
			mediaPlayer.release();
		}
	}
	
	@Override
	public void onStart(Intent intent, int startId) {
		Log.v(TAG, "onStart");
		if (intent != null) {
			Bundle bundle = intent.getExtras();
			if (bundle != null) {
				int op = bundle.getInt("operationFlag");
				switch (op) {
				case 0:
					play();
					break;
				case 1:
					stop();
					break;
				case 2:
					pause();
					break;
				}
			}
		}
	}

	public void play() {
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
				mediaPlayer.prepare(); // 在调用stop后如果需要再次通过start进行播放,需要之前调用prepare函数
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
}
