package com.pxq.corelibrary.utils;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;

public class DisplayUtil {
	
	/**
	 * ��ȡ��Ļ���
	 * 			��λ��px
	 * @param activity
	 * @return
	 */
	public static int getDisplayWidthPixels(final Activity activity){
		DisplayMetrics metrics = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
		return metrics.widthPixels;
	}
	
	/**
	 * ��ȡ��Ļ�߶�
	 * 			��λ��px
	 * @param activity
	 * @return
	 */
	public static int getDisplayHeightPixels(final Activity activity){
		DisplayMetrics metrics = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
		return metrics.heightPixels;
	}
	
	/**
	 * pxתdp
	 * 
	 * @param context
	 * @param pxValue
	 * @return
	 */
	public static int px2dp(Context context, float pxValue){
		final float scale = context.getResources().getDisplayMetrics().density;  //��Ļ�ܶ�
		return (int) (pxValue / scale + 0.5f);
	}
	
	
	/**
	 * dpתpx
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
	 * dpתsp
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
	 * spתdp
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
