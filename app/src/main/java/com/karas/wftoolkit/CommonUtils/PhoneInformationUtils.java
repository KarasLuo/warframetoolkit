package com.karas.wftoolkit.CommonUtils;

import android.os.Build;

import java.util.Locale;

/**
 * Created by Hongliang Luo on 2018/8/7.
 **/
public class PhoneInformationUtils {
    static final private String TAG="PhoneInformationUtils";

    /**
     * @return 厂商名
     */
    public static String getDeviceManufacturer(){
        return Build.MANUFACTURER;
    }

    /**
     * @return 产品名
     */
    public static String getDeviceProduct(){
        return Build.PRODUCT;
    }

    /**
     * @return 手机品牌
     */
    public static String getDeviceBrand(){
        return Build.BRAND;
    }

    /**
     * @return 手机型号
     */
    public static  String getDeviceModel(){
        return Build.MODEL;
    }

    /**
     * @return 主板名
     */
    public static String getDeviceBoard(){
        return Build.BOARD;
    }

    /**
     * @return 设备名
     */
    public static String getDeviceDevice(){
        return Build.DEVICE;
    }

    /**
     * @return android SDK
     */
    public static int getDeviceSDK(){
        return Build.VERSION.SDK_INT;
    }

    /**
     * @return android 版本
     */
    public static String getDeviceAndroidVersion(){
        return Build.VERSION.RELEASE;
    }

    /**
     * @return 当前语言
     */
    public static String getDeviceDefaultLanguage(){
        return Locale.getDefault().getLanguage();
    }

}
