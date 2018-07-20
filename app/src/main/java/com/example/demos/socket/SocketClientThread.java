package com.example.demos.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

public class SocketClientThread implements Runnable {
	
	private Socket socket;
	// 定义向UI线程发送消息的Handler对象
	private Handler handler;
	// 定义接收UI线程的Handler对象
	//public Handler revHandler;
	private BufferedReader br = null;
	private OutputStream os = null;

	public SocketClientThread(Handler handler) {
		this.handler = handler;
	}

	@Override
	public void run() {
		socket = new Socket();
		try {
			socket.connect(new InetSocketAddress("191.168.1.118", 8009), 5000);
			br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			os = socket.getOutputStream();

			String content = null;
			// 不断的读取Socket输入流的内容
			try {
				while ((content = br.readLine()) != null) {
					// 每当读取到来自服务器的数据之后，发送的消息通知程序
					// 界面显示该数据
					Message msg = new Message();
					msg.what = 0x123;
					msg.obj = content;
					handler.sendMessage(msg);
				}
			} catch (IOException io) {
				io.printStackTrace();
			}
			
			/*// 启动一条子线程来读取服务器相应的数据
			new Thread() {

				@Override
				public void run() {
					String content = null;
					// 不断的读取Socket输入流的内容
					try {
						while ((content = br.readLine()) != null) {
							System.out.println(content);
							// 每当读取到来自服务器的数据之后，发送的消息通知程序
							// 界面显示该数据
							Message msg = new Message();
							msg.what = 0x123;
							msg.obj = content;
							handler.sendMessage(msg);
						}
					} catch (IOException io) {
						io.printStackTrace();
					}
				}

			}.start();*/
			/*// 为当前线程初始化Looper
			Looper.prepare();
			// 创建revHandler对象
			revHandler = new Handler() {

				@Override
				public void handleMessage(Message msg) {
					// 接收到UI线程的中用户输入的数据
					if (msg.what == 0x345) {
						// 将用户在文本框输入的内容写入网络
						try {
							os.write((msg.obj.toString() + "\r\n").getBytes("UTF-8"));
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}

			}; 
			// 启动Looper
			Looper.loop();*/

		} catch (SocketTimeoutException e) {
			Message msg = new Message();
			msg.what = 0x123;
			msg.obj = "网络连接超时！";
			handler.sendMessage(msg);
		} catch (IOException io) {
			io.printStackTrace();
		}

	}
	
	
	//考虑下不用Looper.prepare()和loop9()的区别
	/** 使线程拥有自己的消息列队，主线程拥有自己的消息列队，
	 * 一般线程创建时没有自己的消息列队，消息处理时就在主线程中完成，
	 * 如果线程中使用Looper.prepare()和Looper.loop()创建了消息队列就可以让消息处理在该线程中完成*/
	@SuppressLint("HandlerLeak")
	public Handler revHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// 接收到UI线程的中用户输入的数据
			if (msg.what == 0x345) {
				// 将用户在文本框输入的内容写入网络
				try {
					os.write((msg.obj.toString() + "\r\n").getBytes("UTF-8"));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

	}; 
	
	
}
