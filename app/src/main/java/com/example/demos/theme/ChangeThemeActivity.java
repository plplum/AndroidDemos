package com.example.demos.theme;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.demos.R;

public class ChangeThemeActivity extends Activity{
	
	private RadioButton radioButton1;
	private RadioButton radioButton2;
	
	private SharedPreferences sharedPreferences;
	private SharedPreferences.Editor editor;
	
	private RadioGroup group;
	
	private static int THEME_DARK = 0;
	private static int THEME_LIGHT = 1;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		sharedPreferences = this.getSharedPreferences("androidDemos", 0);
		editor = sharedPreferences.edit();
		int theme = sharedPreferences.getInt("androidDemos_theme", 0);
		if(theme == THEME_DARK){
			setTheme(R.style.Theme_Dark);
		}else {
			setTheme(R.style.Theme_Light);
		}
		
		setContentView(R.layout.activity_theme);
		radioButton1 = (RadioButton) findViewById(R.id.radioButton1);
		radioButton2 = (RadioButton) findViewById(R.id.radioButton2);
		
		if(theme == THEME_DARK){
			radioButton1.setChecked(true);
			radioButton2.setChecked(false);
		}else {
			radioButton1.setChecked(false);
			radioButton2.setChecked(true);
		}
		
		group = (RadioGroup) findViewById(R.id.system_theme);
		
		radioButton1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				editor.putInt("androidDemos_theme", THEME_DARK);
				editor.commit();
				setTheme(R.style.Theme_Dark);
				overridePendingTransition(0, 0);
				finish();  
				Intent intent = new Intent(ChangeThemeActivity.this, ChangeThemeActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
				overridePendingTransition(0, 0);
		        startActivity(intent);  
			}
		});
		
		radioButton2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				editor.putInt("androidDemos_theme", THEME_LIGHT);
				editor.commit();
				setTheme(R.style.Theme_Light);
				overridePendingTransition(0, 0);
				finish();  
				Intent intent = new Intent(ChangeThemeActivity.this, ChangeThemeActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
				overridePendingTransition(0, 0);
		        startActivity(intent);  
			}
		});
		
	}
}
