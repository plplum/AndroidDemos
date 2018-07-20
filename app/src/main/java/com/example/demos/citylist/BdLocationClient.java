package com.example.demos.citylist;

import android.content.Context;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

public class BdLocationClient {

	private static BdLocationClient instance;
	
	private static LocationClient mLocationClient;
	
	
	//定位刷新
	private boolean isNeedFresh = true;
	

	private BdLocationClient() {

	}

	public static BdLocationClient getInstance(Context context) {
		if (instance == null) {
			instance = new BdLocationClient();
			mLocationClient = new LocationClient(context);
			InitLocation();
		}
		return instance;
	}
	
	
	public LocationClient getLocationClient() {
		//return new LocationClient(context);
		return mLocationClient;
	}

	public boolean isNeedFresh() {
		return isNeedFresh;
	}

	public synchronized void  setNeedFresh(boolean isNeedFresh) {
		this.isNeedFresh = isNeedFresh;
	}
	
	
	private static void InitLocation() {
		// 设置定位参数
		LocationClientOption option = new LocationClientOption();
		option.setCoorType("bd09ll"); // 设置坐标类型
		option.setScanSpan(10000); // 10分钟扫描1次
		// 需要地址信息，设置为其他任何值（string类型，且不能为null）时，都表示无地址信息。
		option.setAddrType("all");
		// 设置是否返回POI的电话和地址等详细信息。默认值为false，即不返回POI的电话和地址信息。
		option.setPoiExtraInfo(true);
		// 设置产品线名称。强烈建议您使用自定义的产品线名称，方便我们以后为您提供更高效准确的定位服务。
		option.setProdName("通过GPS定位我当前的位置");
		// 禁用启用缓存定位数据
		option.disableCache(true);
		// 设置最多可返回的POI个数，默认值为3。由于POI查询比较耗费流量，设置最多返回的POI个数，以便节省流量。
		option.setPoiNumber(3);
		// 设置定位方式的优先级。
		// 当gps可用，而且获取了定位结果时，不再发起网络请求，直接返回给用户坐标。这个选项适合希望得到准确坐标位置的用户。如果gps不可用，再发起网络请求，进行定位。
		option.setPriority(LocationClientOption.GpsFirst);
		mLocationClient.setLocOption(option);
	}

	
}
