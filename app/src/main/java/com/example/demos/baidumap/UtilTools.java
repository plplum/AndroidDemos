package com.example.demos.baidumap;

import android.content.Context;
import android.util.DisplayMetrics;

public class UtilTools {
	
	public static int getScreenWidth(Context context) {
		DisplayMetrics dm = new DisplayMetrics();
		dm = context.getResources().getDisplayMetrics();
		return dm.widthPixels;
	}

	public static int getScreenHeight(Context context) {
		DisplayMetrics dm = new DisplayMetrics();
		dm = context.getResources().getDisplayMetrics();
		return dm.heightPixels;
	}

	//代码中默认的设置 的PX，这里转化为dp
	public static float getScreenDensity(Context context,int i) {
		int dp=0;
		DisplayMetrics dm = new DisplayMetrics();
		dm = context.getResources().getDisplayMetrics();
		dp=(int) (i*dm.density);
		return dp;
	}
	
	/** 
     * ����ֻ�ķֱ��ʴ� dp �ĵ�λ ת��Ϊ px(����) 
     */  
    public static int dip2px(Context context, float dpValue) {  
        final float scale = context.getResources().getDisplayMetrics().density;  
        return (int) (dpValue * scale + 0.5f);  
    }  
  
    /** 
     * ����ֻ�ķֱ��ʴ� px(����) �ĵ�λ ת��Ϊ dp 
     */  
    public static int px2dip(Context context, float pxValue) {  
        final float scale = context.getResources().getDisplayMetrics().density;  
        return (int) (pxValue / scale + 0.5f);  
    }

}
