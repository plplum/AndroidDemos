package com.example.demos.webservice;

import java.util.ArrayList;
import java.util.List;

import org.ksoap2.serialization.SoapObject;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.demos.R;
import com.example.demos.util.ToastUtil;

public class WebServiceActivity extends Activity {
	
	private List<String> provinceList = new ArrayList<String>();
	
	public static final String WEB_SERVER_URL = "http://www.webxml.com.cn/WebServices/WeatherWebService.asmx";
	
	public static final String NAME_SPACE = "http://WebXml.com.cn/";
	
	private ListView listView;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_webservice_main);
		listView = (ListView) findViewById(R.id.demoes_list);

		//调用WebService接口
		SoapUtil.callService(WEB_SERVER_URL, NAME_SPACE, "getSupportProvince", null, new SoapUtil.WebServiceCallBack() {
			
			@Override
			public void onSucced(SoapObject result) {
				
				if(result != null){
					provinceList = parseSoapObject(result);
					listView.setAdapter(new ArrayAdapter<String>(WebServiceActivity.this, android.R.layout.simple_list_item_1, provinceList));
				}else{
					ToastUtil.show(WebServiceActivity.this, "请求WebService-->getSupportProvince失败", 1000);
				}
			}

			@Override
			public void onFailure(String result) {
				ToastUtil.show(WebServiceActivity.this, "请求WebService-->getSupportProvince失败，原因："+result, 1000);
			}
		});
		
	}

	
	/**
	 * 解析SoapObject对象
	 * @param result SoapObject查询结果对象
	 * @return
	 */
	private List<String> parseSoapObject(SoapObject result){
		List<String> list = new ArrayList<String>();
		SoapObject provinceSoapObject = (SoapObject) result.getProperty("getSupportProvinceResult");
		if(provinceSoapObject == null) {
			return null;
		}
		for(int i=0; i<provinceSoapObject.getPropertyCount(); i++){
			list.add(provinceSoapObject.getProperty(i).toString());
		}
		
		return list;
	}
	
	
}
