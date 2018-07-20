package com.example.demos.language;

import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RadioButton;

import com.example.demos.R;

public class ChangeLanguageActivity extends Activity{
	
	public static final int  LANGUAGE_ENG = 0;
	public static final int  LANGUAGE_SCH = 1;
	public static final int  LANGUAGE_TCH = 2;
	
	
	private RadioButton radioButton1;
	private RadioButton radioButton2;
	private RadioButton radioButton3;
	
	private RadioButton[] radioButtons;
	
	private SharedPreferences sharedPreferences;
	private SharedPreferences.Editor editor;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		sharedPreferences = this.getSharedPreferences("androidDemos", 0);
		editor = sharedPreferences.edit();
		int language = sharedPreferences.getInt("androidDemos_language", 0);
		setAppLanguage(getBaseContext(), language);
		setContentView(R.layout.activity_language);
		
		radioButton1 = (RadioButton) findViewById(R.id.radioButton1);
		radioButton2 = (RadioButton) findViewById(R.id.radioButton2);
		radioButton3 = (RadioButton) findViewById(R.id.radioButton3);
		
		radioButtons = new RadioButton[]{radioButton1, radioButton2, radioButton3};
		
		radioButtons[language].setChecked(true);
		
		radioButton1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				setAppLanguage(getBaseContext(), LANGUAGE_ENG);
				editor.putInt("androidDemos_language", LANGUAGE_ENG);
				editor.commit();
				reloadAvtivity();
			}
		});
		radioButton2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				setAppLanguage(getBaseContext(), LANGUAGE_SCH);
				editor.putInt("androidDemos_language", LANGUAGE_SCH);
				editor.commit();
				reloadAvtivity();
			}
		});
		radioButton3.setOnClickListener(new OnClickListener() {
	
			@Override
			public void onClick(View v) {
				setAppLanguage(getBaseContext(), LANGUAGE_TCH);
				editor.putInt("androidDemos_language", LANGUAGE_TCH);
				editor.commit();
				reloadAvtivity();
			}
		});
		
	}
	
	private void reloadAvtivity() {
		overridePendingTransition(0, 0);
		finish();  
		Intent intent = new Intent(ChangeLanguageActivity.this, ChangeLanguageActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		overridePendingTransition(0, 0);
        startActivity(intent);  
	}
	
	public static void setAppLanguage(Context context, int languageId) {
		Locale locale = Locale.ENGLISH;
		switch (languageId) {
		case LANGUAGE_ENG:
			locale = Locale.ENGLISH;
			break;
		case LANGUAGE_SCH:
			locale = Locale.SIMPLIFIED_CHINESE;
			break;
		case LANGUAGE_TCH:
			locale = Locale.TRADITIONAL_CHINESE;
			break;
		}
		Locale.setDefault(locale);
		Configuration configuration = new Configuration();
		configuration.locale = locale;
		context.getResources().updateConfiguration(configuration,context.getResources().getDisplayMetrics());
	}
	

}
