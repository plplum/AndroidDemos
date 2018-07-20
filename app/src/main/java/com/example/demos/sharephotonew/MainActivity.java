package com.example.demos.sharephotonew;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ListView;

import com.example.demos.R;
import com.example.demos.sharephotonew.adapter.ListItemAdapter;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class MainActivity extends Activity {

	/** Item数据实体集合 */
	private ArrayList<ItemEntity> itemEntities;
	/** ListView对象 */
	private ListView listview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_sharephotonew);
		listview = (ListView) findViewById(R.id.listview);
		initData();
		//获取图片名称
		//getResources().getResourceEntryName(R.drawable.action_add_contacts);
		listview.setAdapter(new ListItemAdapter(this, itemEntities));
		
		//image config
		DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder() //
		.showImageForEmptyUri(R.drawable.ic_launcher) //
		.showImageOnFail(R.drawable.ic_launcher) //
		.cacheInMemory(true) //
		.cacheOnDisc(true) //
		.build();//
		ImageLoaderConfiguration config = new ImageLoaderConfiguration//
		.Builder(getApplicationContext())//
				.defaultDisplayImageOptions(defaultOptions)//
				.discCacheSize(5 * 1024 * 1024)//
				.discCacheFileCount(100)// 缓存一百张图片
				.writeDebugLogs()//
				.build();//
		ImageLoader.getInstance().init(config);
		
	}

	/**
	 * 初始化数据
	 */
	private void initData() {
		itemEntities = new ArrayList<ItemEntity>();
		// 1.无图片
		ItemEntity entity1 = new ItemEntity(//
				"http://img.my.csdn.net/uploads/201410/19/1413698871_3655.jpg", "张三", "今天天气不错...", null);
		itemEntities.add(entity1);
		// 2.1张图片
		ArrayList<String> urls_1 = new ArrayList<String>();
		urls_1.add("http://img.my.csdn.net/uploads/201410/19/1413698883_5877.jpg");
		ItemEntity entity2 = new ItemEntity(//
				"http://img.my.csdn.net/uploads/201410/19/1413698865_3560.jpg", "李四", "今天雾霾呢...", urls_1);
		itemEntities.add(entity2);
		// 3.3张图片
		ArrayList<String> urls_2 = new ArrayList<String>();
		urls_2.add("http://img.my.csdn.net/uploads/201410/19/1413698867_8323.jpg");
		urls_2.add("http://img.my.csdn.net/uploads/201410/19/1413698883_5877.jpg");
		urls_2.add("http://img.my.csdn.net/uploads/201410/19/1413698837_5654.jpg");
		ItemEntity entity3 = new ItemEntity(//
				"http://img.my.csdn.net/uploads/201410/19/1413698837_5654.jpg", "王五", "今天好大的太阳...", urls_2);
		itemEntities.add(entity3);
		// 4.6张图片
		ArrayList<String> urls_3 = new ArrayList<String>();
		urls_3.add("http://img.my.csdn.net/uploads/201410/19/1413698837_7507.jpg");
		urls_3.add("http://img.my.csdn.net/uploads/201410/19/1413698865_3560.jpg");
		urls_3.add("http://img.my.csdn.net/uploads/201410/19/1413698867_8323.jpg");
		urls_3.add("http://img.my.csdn.net/uploads/201410/19/1413698837_5654.jpg");
		urls_3.add("http://img.my.csdn.net/uploads/201410/19/1413698883_5877.jpg");
		urls_3.add("http://img.my.csdn.net/uploads/201410/19/1413698839_2302.jpg");
		ItemEntity entity4 = new ItemEntity(//
				"http://img.my.csdn.net/uploads/201410/19/1413698883_5877.jpg", "赵六", "今天下雨了...", urls_3);
		itemEntities.add(entity4);
		
		// 4.6张图片
		ArrayList<String> urls_4 = new ArrayList<String>();
		urls_4.add("http://img.my.csdn.net/uploads/201410/19/1413698837_7507.jpg");
		urls_4.add("http://img.my.csdn.net/uploads/201410/19/1413698865_3560.jpg");
		urls_4.add("http://img.my.csdn.net/uploads/201410/19/1413698867_8323.jpg");
		urls_4.add("http://img.my.csdn.net/uploads/201410/19/1413698837_5654.jpg");
		urls_4.add("http://img.my.csdn.net/uploads/201410/19/1413698883_5877.jpg");
		urls_4.add("http://img.my.csdn.net/uploads/201410/19/1413698839_2302.jpg");
		urls_4.add("http://img.my.csdn.net/uploads/201410/19/1413698837_5654.jpg");
		urls_4.add("http://img.my.csdn.net/uploads/201410/19/1413698883_5877.jpg");
		urls_4.add("http://img.my.csdn.net/uploads/201410/19/1413698839_2302.jpg");
		ItemEntity entity5= new ItemEntity(//
				"http://img.my.csdn.net/uploads/201410/19/1413698883_5877.jpg", "赵六11", "今天下雨了111...", urls_4);
		itemEntities.add(entity5);
		
		
		// 4.6张图片
		ArrayList<String> urls_5 = new ArrayList<String>();
		urls_5.add("http://img.my.csdn.net/uploads/201410/19/1413698837_7507.jpg");
		urls_5.add("http://img.my.csdn.net/uploads/201410/19/1413698865_3560.jpg");
		urls_5.add("http://img.my.csdn.net/uploads/201410/19/1413698867_8323.jpg");
		urls_5.add("http://img.my.csdn.net/uploads/201410/19/1413698867_8323.jpg");
		ItemEntity entity6= new ItemEntity(//
				"http://img.my.csdn.net/uploads/201410/19/1413698883_5877.jpg", "赵六11", "今天下雨了111...", urls_5);
		itemEntities.add(entity6);
		
	}
}
