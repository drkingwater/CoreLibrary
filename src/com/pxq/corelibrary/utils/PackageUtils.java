package com.pxq.corelibrary.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.util.Log;

public class PackageUtils {

	/**
	 * @Title: getAllAppsPkg
	 * @Description: 获取所有应用名称、和对应的包
	 * @param context
	 * @return
	 * Created by panxq on 下午6:06:57 2017-9-28
	 */
	public static Map<String, String> getAllAppsPkg(Context context) {

		Map<String, String> appMaps = new HashMap<String, String>();

		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.addCategory(Intent.CATEGORY_LAUNCHER);
		
		if (context == null) {
			Log.d("getAllAppsPkg ", "context == null");
			return null;
		}
		
		List<ResolveInfo> apps = context.getPackageManager()
				.queryIntentActivities(intent, 0);

		for (ResolveInfo resolveInfo : apps) {
			String pkg = resolveInfo.activityInfo.packageName;
			String appName = resolveInfo.activityInfo.applicationInfo.loadLabel(context.getPackageManager()).toString();
			
			appMaps.put(appName, pkg);
		}

		return appMaps;
	}
	
	/**
	 * @Title: startActivity
	 * @Description: 
	 * @param context
	 * @param pkg
	 * @return
	 * Created by panxq on 下午6:14:49 2017-9-28
	 */
	public static boolean startActivity(Context context, String pkg){
		
		Intent intent = context.getPackageManager().getLaunchIntentForPackage(pkg);
		
		if (intent == null) {
			return false;
		}
		
		context.startActivity(intent);
		
		return true;
	}
	
	/**
	 * @Title: createExplicitFromImplicitIntent
	 * @Description: 隐式service转显示 兼容android 5.0版本
	 * @param context
	 * @param implicitIntent
	 * @return
	 * Created by panxq on 下午5:59:29 2017-10-17
	 */
	public static Intent createExplicitFromImplicitIntent(Context context, Intent implicitIntent) {  
        // Retrieve all services that can match the given intent  
        PackageManager pm = context.getPackageManager();  
        List<ResolveInfo> resolveInfo = pm.queryIntentServices(implicitIntent, 0);  
   
        // Make sure only one match was found  
        if (resolveInfo == null || resolveInfo.size() != 1) {  
            return null;  
        }  
   
        // Get component info and create ComponentName  
        ResolveInfo serviceInfo = resolveInfo.get(0);  
        String packageName = serviceInfo.serviceInfo.packageName;  
        String className = serviceInfo.serviceInfo.name;  
        ComponentName component = new ComponentName(packageName, className);  
   
        Log.d("panxq", "createExplicitFromImplicitIntent:\npackageName: " + packageName + "\nclassName " + className);
        
        // Create a new intent. Use the old one for extras and such reuse  
        Intent explicitIntent = new Intent(implicitIntent);  
   
        // Set the component to be explicit  
        explicitIntent.setComponent(component);  
   
        return explicitIntent;  
    } 


}
