package com.example.demos.broadcast;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.example.demos.R;

/**
 * @author Administrator
 * 
 *         两种注册BroadcastReceiver的方法：
 * 
 *         1.在应用程序的代码中进行注册 注册:BroadcastReceiver
 * 
 *         IntentFilter filter = new
 *         IntentFilter("android.provider.Telephony.SMS_RECEIVED");
 * 
 *         filter.setPriority(2147483647);
 * 
 *         receiver=new BroadReceiver();
 * 
 *         registerReceiver（receiver,filter）;
 * 
 * 
 *         取消注册BroadcastReceiver unregisterReceiver（receiver);
 * 
 *         2.在AndroidManifest.xml当中进行注册
 * 
 *         <receiver android:name="SMSReceiver"> <intent-filter
 *         android:priority="2147483647" > <action
 *         android:name="android.provider.Telephony.SMS_RECEIVED" />
 *         </intent-filter> </receiver>
 * 
 */
public class BroadCastActivity extends Activity {

	private final String ACTION_NAME = "broadcast_test";
	private Button mBtnMsgEvent = null;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 注册广播
		registerBoradcastReceiver();
		
		setContentView(R.layout.activity_broadcast);
		mBtnMsgEvent = (Button) findViewById(R.id.button1);
		

		mBtnMsgEvent.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent mIntent = new Intent(ACTION_NAME);
				mIntent.putExtra("yaner", "发送广播，相当于在这里传送数据");

				// 发送广播
				sendBroadcast(mIntent);
			}
		});
	}

	private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (action.equals(ACTION_NAME)) {
				Toast.makeText(BroadCastActivity.this, "接收到广播消息，处理需要的逻辑。", 200).show();
				
				//点击的意图ACTION是跳转到Intent
				Intent resultIntent = new Intent(BroadCastActivity.this, TestActivity.class);
				resultIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
				PendingIntent pendingIntent = PendingIntent.getActivity(BroadCastActivity.this, 0,resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
				
				//显示通知
				//第一步：获取状态通知栏管理
				NotificationManager mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);  
				//实例化通知栏构造器NotificationCompat.Builder：
				NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(BroadCastActivity.this);
				//对Builder进行配置
				mBuilder.setContentTitle("测试标题")//设置通知栏标题
				.setContentText("测试内容")
				.setContentIntent(pendingIntent) //设置通知栏点击意图
//				.setNumber(number) //设置通知集合的数量
				.setTicker("测试通知来啦") //通知首次出现在通知栏，带上升动画效果的
				.setWhen(System.currentTimeMillis())//通知产生的时间，会在通知信息里显示，一般是系统获取到的时间
				.setPriority(Notification.PRIORITY_DEFAULT) //设置该通知优先级
				.setAutoCancel(true)//设置这个标志当用户单击面板就可以让通知将自动取消  
				.setOngoing(false)//ture，设置他为一个正在进行的通知。他们通常是用来表示一个后台任务,用户积极参与(如播放音乐)或以某种方式正在等待,因此占用设备(如一个文件下载,同步操作,主动网络连接)
				.setDefaults(Notification.DEFAULT_VIBRATE)//向通知添加声音、闪灯和振动效果的最简单、最一致的方式是使用当前的用户默认设置，使用defaults属性，可以组合
				//Notification.DEFAULT_ALL  Notification.DEFAULT_SOUND 添加声音 // requires VIBRATE permission
				.setSmallIcon(R.drawable.ic_launcher);//设置通知小ICON
				
				Notification notify = mBuilder.build();
				mNotificationManager.notify(1011, notify);
			}
		}

	};

	public void registerBoradcastReceiver() {
		IntentFilter myIntentFilter = new IntentFilter();
		myIntentFilter.addAction(ACTION_NAME);
		// 注册广播
		registerReceiver(mBroadcastReceiver, myIntentFilter);
	}
	
	
	public PendingIntent getDefalutIntent(int flags){  
	    PendingIntent pendingIntent= PendingIntent.getActivity(this, 1, new Intent(), flags);  
	    return pendingIntent;  
	}  
	
	
}
