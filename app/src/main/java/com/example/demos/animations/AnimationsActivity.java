package com.example.demos.animations;

import com.example.demos.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

public class AnimationsActivity extends Activity implements OnClickListener{
	private ImageView imageView;
	private Button buttonAlpha;
	private Button buttonScale;
	private Button buttonTranlate;
	private Button buttonRotate;
	private Button buttonRotate1;
	
	private Animation animationAlpha;
	private Animation animationScale;
	private Animation animationTranlate;
	private Animation animationRotate;
	private Animation animationRotate1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_animations_main);
		imageView = (ImageView) findViewById(R.id.imageView);
		buttonAlpha = (Button) findViewById(R.id.button_alpha);
		buttonScale = (Button) findViewById(R.id.button_scale);
		buttonTranlate = (Button) findViewById(R.id.button_translate);
		buttonRotate = (Button) findViewById(R.id.button_rotate);
		buttonRotate1 = (Button) findViewById(R.id.button_rotate2);
		
		buttonAlpha.setOnClickListener(this);
		buttonScale.setOnClickListener(this);
		buttonTranlate.setOnClickListener(this);
		buttonTranlate.setOnClickListener(this);
		buttonRotate.setOnClickListener(this);
		buttonRotate1.setOnClickListener(this);
		//imageView.setAnimation(animation); 效果同imageView.startAnimation(animation);
		
		animationAlpha = AnimationUtils.loadAnimation(this, R.anim.imageview_alpha);
		animationScale = AnimationUtils.loadAnimation(this, R.anim.imageview_scale);
		animationTranlate = AnimationUtils.loadAnimation(this, R.anim.imageview_tranlate);
		animationRotate = AnimationUtils.loadAnimation(this, R.anim.imageview_rotate);
		animationRotate =  AnimationUtils.loadAnimation(this, R.anim.imageview_rotate);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button_alpha:
			imageView.startAnimation(animationAlpha);
			break;
		case R.id.button_scale:
			imageView.startAnimation(animationScale);
			break;
		case R.id.button_translate:
			imageView.startAnimation(animationTranlate);
			break;
		case R.id.button_rotate:
			imageView.startAnimation(animationRotate);
			break;
		case R.id.button_rotate2:
			imageView.startAnimation(animationRotate);
			//imageView.setBackground(getResources().getDrawable(R.drawable.refresh_whirl));
			break;

		default:
			break;
		}
	}

}
