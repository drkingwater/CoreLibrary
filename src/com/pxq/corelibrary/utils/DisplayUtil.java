package com.pxq.corelibrary.utils;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;

public class DisplayUtil {
	
	/**
	 * 获取屏幕宽度
	 * 			单位：px
	 * @param activity
	 * @return
	 */
	public static int getDisplayWidthPixels(final Activity activity){
		DisplayMetrics metrics = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
		return metrics.widthPixels;
	}
	
	/**
	 * 获取屏幕高度
	 * 			单位：px
	 * @param activity
	 * @return
	 */
	public static int getDisplayHeightPixels(final Activity activity){
		DisplayMetrics metrics = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
		return metrics.heightPixels;
	}
	
	/**
	 * px转dp
	 * 
	 * @param context
	 * @param pxValue
	 * @return
	 */
	public static int px2dp(Context context, float pxValue){
		final float scale = context.getResources().getDisplayMetrics().density;  //屏幕密度
		return (int) (pxValue / scale + 0.5f);
	}
	
	
	/**
	 * dp转px
	 * 
	 * @param context
	 * @param dpValue
	 * @return
	 */
	public static int dp2px(Context context, float dpValue){
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}
	
	/**
	 * dp转sp
	 * 
	 * @param context
	 * @param dpValue
	 * @return
	 */
	public static int px2sp(Context context, float dpValue){
		final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (dpValue / fontScale + 0.5f);
	}
	
	/**
	 * sp转dp
	 * 
	 * @param context
	 * @param spValue
	 * @return
	 */
	public static int sp2dp(Context context, float spValue){
		final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (spValue * fontScale + 0.5f);
	}

}
