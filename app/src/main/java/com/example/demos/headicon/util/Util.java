package com.example.demos.headicon.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.demos.R;

public class Util {

	
	/**拍照返回请求吗码*/
	public static final int FROM_CRAMA=0;
	/**图库选择照片返回码*/
	public static final int FROM_LOCAL=1;
	/**裁剪后返回码*/
	public static final int FROM_CUT=2;
	/**拍照后照片地址*/
	public static Uri photoCamare=null;
	/**裁剪后照片地址*/
	public static Uri cutPhoto=null;
	
	public static void getFromCamara(final Activity activity)
	{
		photoCamare=setCutUriImage(activity);
		Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		intent.putExtra(MediaStore.EXTRA_OUTPUT,photoCamare);
		activity.startActivityForResult(intent,FROM_CRAMA);
	}
	
	/**
	 * @category 从图库获得照片
	 * */
	public static void getFromLocation(final Activity context)
	{
		Intent intent=new Intent();
		intent.setType("image/*");
		intent.setAction(Intent.ACTION_GET_CONTENT);
		context.startActivityForResult(intent,FROM_LOCAL);
	}
	
	public static void cutCorePhoto(Activity activity,Uri uri)
	{
		cutPhoto=setCutUriImage(activity);
		Intent intent=new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX",1);
		intent.putExtra("aspectY",1);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, cutPhoto);
		intent.putExtra("return-data", false);
		activity.startActivityForResult(intent, FROM_CUT);
	}
	
	public static Uri setCutUriImage(Context context)
	{
		Uri imagePath=null;
		String status=Environment.getExternalStorageState();
		SimpleDateFormat fomater=new SimpleDateFormat("yyyyMMdd_HHmmss",Locale.CHINA);
		long time=System.currentTimeMillis();
		String timeString=fomater.format(new Date(time));
		ContentValues values=new ContentValues(3);
		values.put(MediaStore.Images.Media.DISPLAY_NAME, timeString);
		values.put(MediaStore.Images.Media.DATE_TAKEN,time);
		values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
		if(status.equals(Environment.MEDIA_MOUNTED))
		{
			imagePath=context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
		}
		else
		{
			imagePath=context.getContentResolver().insert(MediaStore.Images.Media.INTERNAL_CONTENT_URI,values);
		}
		
		return imagePath;
	}

	
	
	
	public static Dialog iconSelectDialog(Context context, String title,
			int[] imagesId, String[] items, final DialogItemOnClickListener listener) {
		
		final Dialog dialog = new Dialog(context, R.style.DialogStyle);

		View view = LayoutInflater.from(context).inflate(R.layout.icon_select_dialog, null);

		dialog.setContentView(view);

		dialog.setCanceledOnTouchOutside(true);

		((TextView) view.findViewById(R.id.dialog_title)).setText(title);

		ImageView local_shape = (ImageView) view.findViewById(R.id.set_image);
		ImageView camera_shape = (ImageView) view.findViewById(R.id.set_image11);

		final TextView local_picture = (TextView) view.findViewById(R.id.me_picture);
		final TextView local_camera = (TextView) view.findViewById(R.id.me_camera_picture);

		local_shape.setImageResource(imagesId[0]);
		camera_shape.setImageResource(imagesId[1]);

		local_picture.setText(items[0]);
		local_camera.setText(items[1]);

		local_picture.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				dialog.dismiss();
				listener.itemSelect(0);
			}
		});

		local_camera.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				dialog.dismiss();
				listener.itemSelect(1);
			}
		});

		Window mWindow = dialog.getWindow();
		WindowManager.LayoutParams lp = mWindow.getAttributes();
		lp.width = getScreenWidth(context);
		mWindow.setGravity(Gravity.CENTER);
		mWindow.setAttributes(lp);
		dialog.show();

		return dialog;

	}

	public interface DialogItemOnClickListener {
		public abstract void itemSelect(int index);
	}

	public static int getScreenWidth(Context context) {
		DisplayMetrics displayMetrics = new DisplayMetrics();
		((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
		return displayMetrics.widthPixels;
	}

}
