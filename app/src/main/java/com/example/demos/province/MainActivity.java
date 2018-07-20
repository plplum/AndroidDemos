package com.example.demos.province;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.example.demos.R;

public class MainActivity extends Activity implements OnClickListener{
	
	
	private EditText editText;
	private PopupWindow popupWindow;
	
	private ListView listViewP;
	private ListView listViewC;
	private ListView listViewR;
	
	private CityAdapter cityAdapter;
	private CityAdapter cityAdapterC;
	private CityAdapter cityAdapterR;
	
	private List<Area> provinceList;
	private ArrayList<Area> cityList = new ArrayList<Area>();
	private ArrayList<Area> cityListC  = new ArrayList<Area>();
	private ArrayList<Area> cityListR  = new ArrayList<Area>();
	
	private DBhelper dBhelper;
	
	private String p;
	private String c;
	private String r;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_province);
		
		editText = (EditText) findViewById(R.id.editText1);
		editText.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.editText1:
			showPopupWindows();
			break;

		default:
			break;
		}
	}
	
	private void showPopupWindows(){
		if(popupWindow == null) {
			LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View conentView = inflater.inflate(R.layout.province_select, null);
			popupWindow = new PopupWindow(conentView);
			popupWindow.setWidth(LayoutParams.MATCH_PARENT);
			popupWindow.setHeight(LayoutParams.MATCH_PARENT);
			//不加下面两句代码，事件不响应。
			popupWindow.setBackgroundDrawable(new ColorDrawable(0));
			popupWindow.setFocusable(true);
			
			listViewP = (ListView) conentView.findViewById(R.id.listView);
			listViewC = (ListView) conentView.findViewById(R.id.listView2);
			listViewR = (ListView) conentView.findViewById(R.id.listView3);
			
			dBhelper = new DBhelper(this);
			provinceList = dBhelper.getProvince();
			cityAdapter = new CityAdapter(getApplicationContext(), provinceList);
			listViewP.setAdapter(cityAdapter);
			
			cityAdapterC = new CityAdapter(getApplicationContext(), cityListC);
			cityAdapterR = new CityAdapter(getApplicationContext(), cityListR);
			
			listViewC.setAdapter(cityAdapterC);
			listViewR.setAdapter(cityAdapterR);

			updateCityAdapter(0);
			updateCityAdapterR(0);
			
			listViewP.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					cityAdapter.setSelectedItem(position);
					cityAdapter.notifyDataSetChanged();
					cityAdapterC.setSelectedItem(0);
					updateCityAdapter(position);
					updateCityAdapterR(0);
				}	
			});
			
			
			listViewC.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					cityAdapterC.setSelectedItem(position);
					cityAdapterC.notifyDataSetChanged();
					cityAdapterR.setSelectedItem(0);
					updateCityAdapterR(position);
				}
			});
			
			listViewR.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					cityAdapterR.setSelectedItem(position);
					cityAdapterR.notifyDataSetChanged();
					Area area = (Area) cityAdapterR.getItem(position);
					r = area.getName();
					//R.drawable.choose_item_right,
					//R.drawable.choose_plate_item_selector
					editText.setText(p+"/"+c+"/"+r);
					popupWindow.dismiss();
				}
			});
			
			
		} else if (popupWindow.isShowing()){
			popupWindow.dismiss();
			return;
		}
		popupWindow.showAsDropDown(editText, 0, 0);
		
	}

	private void updateCityAdapter(int position){
		Area area = (Area) cityAdapter.getItem(position);
		cityListC.clear();
		cityListC.addAll(dBhelper.getCity(area.getCode()));
		cityAdapterC.notifyDataSetChanged();
		p = area.getName();
	}
	
	
	private void updateCityAdapterR(int position){
		Area area = (Area) cityAdapterC.getItem(position);
		cityListR.clear();
		cityListR.addAll(dBhelper.getDistrict(area.getCode()));
		cityAdapterR.notifyDataSetChanged();
		c = area.getName();
	}
	
}
