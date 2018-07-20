package com.example.demos.socket;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.demos.R;

public class SocketClientActivity extends Activity {

	private EditText input;
	private TextView show;
	private Button send;
	private SocketClientThread clientThread;

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if (msg.what == 0x123) {
				show.append("\n" + "接收到服务端的消息： "+ msg.obj.toString());
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_socket);
		input = (EditText) findViewById(R.id.input);
		show = (TextView) findViewById(R.id.show);
		send = (Button) findViewById(R.id.send);

		clientThread = new SocketClientThread(handler);
		new Thread(clientThread).start();

		send.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					Message msg = new Message();
					msg.what = 0x345;
					msg.obj = input.getText().toString();
					clientThread.revHandler.sendMessage(msg);
					input.setText("");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
