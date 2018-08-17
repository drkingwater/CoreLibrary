package com.pxq.corelibrary.utils;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

public class NetworkUtils {

	private NetworkUtils() {  
        throw new UnsupportedOperationException("u can't instance me...");  
    }  
  
    public static final int NETWORK_WIFI = 1;    // wifi network  
    public static final int NETWORK_4G = 4;    // "4G" networks  
    public static final int NETWORK_3G = 3;    // "3G" networks  
    public static final int NETWORK_2G = 2;    // "2G" networks  
    public static final int NETWORK_UNKNOWN = 5;    // unknown network  
    public static final int NETWORK_NO = -1;   // no network  
  
    private static final int NETWORK_TYPE_GSM = 16;  
    private static final int NETWORK_TYPE_TD_SCDMA = 17;  
    private static final int NETWORK_TYPE_IWLAN = 18;  
  
    public static List<String> typeList = new ArrayList<String>();  
    static  
    {  
        typeList.add("���������ý���");  
        typeList.add("��ȡ�������Ϣ");  
        typeList.add("�ж������Ƿ����");  
        typeList.add("�ж������Ƿ���4G");  
        typeList.add("�ж�wifi�Ƿ�����״̬");  
        typeList.add("��ȡ�ƶ�������Ӫ������");  
        typeList.add("��ȡ��ǰ����������");  
        typeList.add("��ȡ��ǰ����������(WIFI,2G,3G,4G)");  
    }  
    /** 
     * ���������ý��� 
     * <p>3.0���´����ý���</p> 
     * 
     * @param context ������ 
     */  
    public static void openWirelessSettings(Context context) {  
        if (android.os.Build.VERSION.SDK_INT > 10) {  
            context.startActivity(new Intent(android.provider.Settings.ACTION_SETTINGS));  
        } else {  
            context.startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));  
        }  
    }  
  
    /** 
     * ��ȡ�������Ϣ 
     * 
     * @param context ������ 
     * @return NetworkInfo 
     */  
    public static NetworkInfo getActiveNetworkInfo(Context context) {  
        ConnectivityManager cm = (ConnectivityManager) context  
                .getSystemService(Context.CONNECTIVITY_SERVICE);  
        return cm.getActiveNetworkInfo();  
    }  
  
    /** 
     * �ж������Ƿ���� 
     * <p>�����Ȩ�� {@code <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>}</p> 
     * 
     * @param context ������ 
     * @return {@code true}: ����<br>{@code false}: ������ 
     */  
    public static boolean isAvailable(Context context) {  
        NetworkInfo info = getActiveNetworkInfo(context);  
        return info != null && info.isAvailable();  
    }  
  
    /** 
     * �ж������Ƿ����� 
     * <p>�����Ȩ�� {@code <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>}</p> 
     * 
     * @param context ������ 
     * @return {@code true}: ��<br>{@code false}: �� 
     */  
    public static boolean isConnected(Context context) {  
        NetworkInfo info = getActiveNetworkInfo(context);  
        return info != null && info.isConnected();  
    }  
  
    /** 
     * �ж������Ƿ���4G 
     * <p>�����Ȩ�� {@code <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>}</p> 
     * 
     * @param context ������ 
     * @return {@code true}: ��<br>{@code false}: ���� 
     */  
    public static boolean is4G(Context context) {  
        NetworkInfo info = getActiveNetworkInfo(context);  
        return info != null && info.isAvailable() && info.getSubtype() == TelephonyManager.NETWORK_TYPE_LTE;  
    }  
  
    /** 
     * �ж�wifi�Ƿ�����״̬ 
     * <p>�����Ȩ�� {@code <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>}</p> 
     * 
     * @param context ������ 
     * @return {@code true}: ����<br>{@code false}: δ���� 
     */  
    public static boolean isWifiConnected(Context context) {  
        ConnectivityManager cm = (ConnectivityManager) context  
                .getSystemService(Context.CONNECTIVITY_SERVICE);  
        return cm != null && cm.getActiveNetworkInfo().getType() == ConnectivityManager.TYPE_WIFI;  
    }  
  
    /** 
     * ��ȡ�ƶ�������Ӫ������ 
     * <p>���й���ͨ���й��ƶ����й�����</p> 
     * 
     * @param context ������ 
     * @return �ƶ�������Ӫ������ 
     */  
    public static String getNetworkOperatorName(Context context) {  
        TelephonyManager tm = (TelephonyManager) context  
                .getSystemService(Context.TELEPHONY_SERVICE);  
        return tm != null ? tm.getNetworkOperatorName() : null;  
    }  
  
    /** 
     * ��ȡ�ƶ��ն����� 
     * 
     * @param context ������ 
     * @return �ֻ���ʽ 
     * <ul> 
     * <li>{@link TelephonyManager#PHONE_TYPE_NONE } : 0 �ֻ���ʽδ֪</li> 
     * <li>{@link TelephonyManager#PHONE_TYPE_GSM  } : 1 �ֻ���ʽΪGSM���ƶ�����ͨ</li> 
     * <li>{@link TelephonyManager#PHONE_TYPE_CDMA } : 2 �ֻ���ʽΪCDMA������</li> 
     * <li>{@link TelephonyManager#PHONE_TYPE_SIP  } : 3</li> 
     * </ul> 
     */  
    public static int getPhoneType(Context context) {  
        TelephonyManager tm = (TelephonyManager) context  
                .getSystemService(Context.TELEPHONY_SERVICE);  
        return tm != null ? tm.getPhoneType() : -1;  
    }  
  
  
    /** 
     * ��ȡ��ǰ����������(WIFI,2G,3G,4G) 
     * <p>�����Ȩ�� {@code <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>}</p> 
     * 
     * @param context ������ 
     * @return �������� 
     * <ul> 
     * <li>{@link #NETWORK_WIFI   } = 1;</li> 
     * <li>{@link #NETWORK_4G     } = 4;</li> 
     * <li>{@link #NETWORK_3G     } = 3;</li> 
     * <li>{@link #NETWORK_2G     } = 2;</li> 
     * <li>{@link #NETWORK_UNKNOWN} = 5;</li> 
     * <li>{@link #NETWORK_NO     } = -1;</li> 
     * </ul> 
     */  
    public static int getNetWorkType(Context context) {  
        int netType = NETWORK_NO;  
        NetworkInfo info = getActiveNetworkInfo(context);  
        if (info != null && info.isAvailable()) {  
  
            if (info.getType() == ConnectivityManager.TYPE_WIFI) {  
                netType = NETWORK_WIFI;  
            } else if (info.getType() == ConnectivityManager.TYPE_MOBILE) {  
                switch (info.getSubtype()) {  
  
                    case NETWORK_TYPE_GSM:  
                    case TelephonyManager.NETWORK_TYPE_GPRS:  
                    case TelephonyManager.NETWORK_TYPE_CDMA:  
                    case TelephonyManager.NETWORK_TYPE_EDGE:  
                    case TelephonyManager.NETWORK_TYPE_1xRTT:  
                    case TelephonyManager.NETWORK_TYPE_IDEN:  
                        netType = NETWORK_2G;  
                        break;  
  
                    case NETWORK_TYPE_TD_SCDMA:  
                    case TelephonyManager.NETWORK_TYPE_EVDO_A:  
                    case TelephonyManager.NETWORK_TYPE_UMTS:  
                    case TelephonyManager.NETWORK_TYPE_EVDO_0:  
                    case TelephonyManager.NETWORK_TYPE_HSDPA:  
                    case TelephonyManager.NETWORK_TYPE_HSUPA:  
                    case TelephonyManager.NETWORK_TYPE_HSPA:  
                    case TelephonyManager.NETWORK_TYPE_EVDO_B:  
                    case TelephonyManager.NETWORK_TYPE_EHRPD:  
                    case TelephonyManager.NETWORK_TYPE_HSPAP:  
                        netType = NETWORK_3G;  
                        break;  
  
                    case NETWORK_TYPE_IWLAN:  
                    case TelephonyManager.NETWORK_TYPE_LTE:  
                        netType = NETWORK_4G;  
                        break;  
                    default:  
  
                        String subtypeName = info.getSubtypeName();  
                        if (subtypeName.equalsIgnoreCase("TD-SCDMA")  
                                || subtypeName.equalsIgnoreCase("WCDMA")  
                                || subtypeName.equalsIgnoreCase("CDMA2000")) {  
                            netType = NETWORK_3G;  
                        } else {  
                            netType = NETWORK_UNKNOWN;  
                        }  
                        break;  
                }  
            } else {  
                netType = NETWORK_UNKNOWN;  
            }  
        }  
        return netType;  
    }  
  
    /** 
     * ��ȡ��ǰ����������(WIFI,2G,3G,4G) 
     * <p>��������ķ���</p> 
     * 
     * @param context ������ 
     * @return ������������ 
     * <ul> 
     * <li>NETWORK_WIFI   </li> 
     * <li>NETWORK_4G     </li> 
     * <li>NETWORK_3G     </li> 
     * <li>NETWORK_2G     </li> 
     * <li>NETWORK_UNKNOWN</li> 
     * <li>NETWORK_NO     </li> 
     * </ul> 
     */  
    public static String getNetWorkTypeName(Context context) {  
        switch (getNetWorkType(context)) {  
            case NETWORK_WIFI:  
                return "NETWORK_WIFI";  
            case NETWORK_4G:  
                return "NETWORK_4G";  
            case NETWORK_3G:  
                return "NETWORK_3G";  
            case NETWORK_2G:  
                return "NETWORK_2G";  
            case NETWORK_NO:  
                return "NETWORK_NO";  
            default:  
                return "NETWORK_UNKNOWN";  
        }  
    }  

}
