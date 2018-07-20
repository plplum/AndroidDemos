package com.example.demos.headicon.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.example.demos.R;
import com.example.demos.headicon.util.Util;
import com.example.demos.headicon.util.Util.DialogItemOnClickListener;
import com.example.demos.headicon.view.CircleImageView;
import com.example.demos.headicon.view.RoundImageView;

public class HeadIconActivity extends Activity implements OnClickListener{

	private CircleImageView circleImageView;
	private ImageView imageView;
	private RoundImageView roundImageView;
	
	private SharedPreferences sharedPreferences;
	
	//仅限演示代码用法
	private int index = -1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_headicon);
		
		circleImageView = (CircleImageView) findViewById(R.id.icon);
		circleImageView.setOnClickListener(this);
		
		imageView = (ImageView) findViewById(R.id.icon02);
		imageView.setOnClickListener(this);
		
		roundImageView = (RoundImageView) findViewById(R.id.icon03);
		roundImageView.setOnClickListener(this);
		
		sharedPreferences = this.getSharedPreferences("test_image", 0);
		String imageUri = sharedPreferences.getString("imageUri", "");
		Uri uri = Uri.parse(imageUri);
		circleImageView.setImageURI(uri);
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.icon:
			Util.iconSelectDialog(HeadIconActivity.this, "更改头像", 
					new int[] {R.drawable.conversation_options_secretfile,
					R.drawable.conversation_options_camera }, 
					new String[] {"相册", "照相" }, new DialogItemOnClick());
			
			index = 0;
			break;
		case R.id.icon02:
			Util.iconSelectDialog(HeadIconActivity.this, "更改头像", 
					new int[] {R.drawable.conversation_options_secretfile,
					R.drawable.conversation_options_camera }, 
					new String[] {"相册", "照相" }, new DialogItemOnClick());
			index = 1;
			break;
		case R.id.icon03:
			Util.iconSelectDialog(HeadIconActivity.this, "更改头像", 
					new int[] {R.drawable.conversation_options_secretfile,
					R.drawable.conversation_options_camera }, 
					new String[] {"相册", "照相" }, new DialogItemOnClick());
			index = 2;
			break;
		default:
			break;
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case Util.FROM_CRAMA:
			if (Util.photoCamare != null) {
				Util.cutCorePhoto(this, Util.photoCamare);
			}
			break;
		case Util.FROM_LOCAL:
			if (data != null && data.getData() != null) {
				Util.cutCorePhoto(this, data.getData());
			}
			break;
		case Util.FROM_CUT:
			if (Util.cutPhoto != null&&resultCode!=RESULT_CANCELED) {
				if(index==0)
					circleImageView.setImageURI(Util.cutPhoto);
				sharedPreferences.edit().putString("imageUri", Util.cutPhoto.toString()).commit();
				if(index==1)
					imageView.setImageURI(Util.cutPhoto);
				if(index==2)
					roundImageView.setImageURI(Util.cutPhoto);
			}
			break;

		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	class DialogItemOnClick implements DialogItemOnClickListener {

		@Override
		public void itemSelect(int index) {
			if (index == 0) {
				 Util.getFromLocation(HeadIconActivity.this);

			} else if (index == 1) {
				 Util.getFromCamara(HeadIconActivity.this);
			}
		}
		
	}
}
