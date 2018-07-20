package com.example.demos.sharephotonew.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap.Config;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.demos.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

public class NoScrollGridAdapter extends BaseAdapter {

	/** 上下文 */
	private Context ctx;
	/** 图片Url集合 */
	private ArrayList<String> imageUrls;

	public NoScrollGridAdapter(Context ctx, ArrayList<String> urls) {
		this.ctx = ctx;
		this.imageUrls = urls;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return imageUrls == null ? 0 : imageUrls.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return imageUrls.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = View.inflate(ctx, R.layout.item_gridview, null);
		final ImageView imageView = (ImageView) view.findViewById(R.id.iv_image);
		DisplayImageOptions options = new DisplayImageOptions.Builder()//
				.cacheInMemory(true)//
				.cacheOnDisc(true)//
				.bitmapConfig(Config.RGB_565)//
				.build();
		ImageLoader.getInstance().displayImage(imageUrls.get(position), imageView, options);
		
		
		//设置每个项目的大小
		/*ViewGroup.LayoutParams layoutparams = imageView.getLayoutParams();
        layoutparams.height = 300;
        layoutparams.width = 300;
        imageView.setLayoutParams(layoutparams);*/
		
		//changeLight(imageView, 0);  
        /* imageView.setOnLongClickListener(new OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View v) {
				changeLight((ImageView) v, 0);  
				return true;
			}
		});
        
       imageView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				 changeLight((ImageView) v, -80);  
			}
		});*/
        //设置触摸事件，改变图片背景色
		//imageView.setOnTouchListener(onTouchListener);
		
		return view;
	}
	
	public OnTouchListener onTouchListener = new View.OnTouchListener() {  
        @Override  
        public boolean onTouch(View view, MotionEvent event) {  
            switch (event.getAction()) {  
            case MotionEvent.ACTION_UP:  
                changeLight((ImageView) view, 0);  
                break;  
            case MotionEvent.ACTION_DOWN:  
                changeLight((ImageView) view, -80);  
                break;  
            case MotionEvent.ACTION_MOVE:  
            	changeLight((ImageView) view, 0);  
                break;  
            case MotionEvent.ACTION_CANCEL:  
                changeLight((ImageView) view, 0);  
                break;
            default:  
                break;  
            }  
            return view.onTouchEvent(event);
        }  
        
    };  
  
private void changeLight(ImageView imageview, int brightness) {  
    ColorMatrix matrix = new ColorMatrix();  
    matrix.set(new float[] { 1, 0, 0, 0, brightness, 0, 1, 0, 0,  
            brightness, 0, 0, 1, 0, brightness, 0, 0, 0, 1, 0 });  
    imageview.setColorFilter(new ColorMatrixColorFilter(matrix));  
  
}  
	
}
