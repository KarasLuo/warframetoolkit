package com.karas.wftoolkit.CommonUtils;

import android.annotation.SuppressLint;
import android.view.Gravity;
import android.widget.Toast;

import com.karas.wftoolkit.MyApplication;

import static com.karas.wftoolkit.MyApplication.dp2px;

/**
 * Created by Hongliang Luo on 2018/8/17.
 **/
public class ToastUtils {

    private static Toast shortToast;
    private static Toast longToast;

    /**
     * 短时间显示Toast
     * @param msg 显示的内容*/
    @SuppressLint("ShowToast")
    public static void showShortToast(String msg) {
        if(MyApplication.getAppContext() != null){
            if (shortToast == null) {
                shortToast = Toast.makeText(MyApplication.getAppContext(),
                        msg,
                        Toast.LENGTH_SHORT);
            } else {
                shortToast.setText(msg);
            }
            shortToast.setGravity(Gravity.BOTTOM,
                    0,
                    dp2px(MyApplication.getAppContext(),
                            64));
            shortToast.show();
        }
    }
    /**
     * 短时间显示Toast
     * @param msg 显示的内容*/
    public static void showShortToastCenter(String msg){
        if(MyApplication.getAppContext() != null) {
            if (shortToast == null) {
                shortToast = Toast.makeText(MyApplication.getAppContext(),
                        msg,
                        Toast.LENGTH_SHORT);
            } else {
                shortToast.setText(msg);
            }
            shortToast.setGravity(Gravity.CENTER, 0, 0);
            shortToast.show();
        }
    }

    /**
     * 短时间显示Toast【居上】
     * @param msg 显示的内容*/
    public static void showShortToastTop(String msg){
        if(MyApplication.getAppContext() != null) {
            if (shortToast == null) {
                shortToast = Toast.makeText(MyApplication.getAppContext(),
                        msg,
                        Toast.LENGTH_SHORT);
            } else {
                shortToast.setText(msg);
            }
            shortToast.setGravity(Gravity.TOP, 0, 0);
            shortToast.show();
        }
    }

    /**
     * 长时间显示Toast【居下】
     * @param msg 显示的内容*/
    public static void showLongToast(String msg) {
        if(MyApplication.getAppContext() != null) {
            if (longToast == null) {
                longToast = Toast.makeText(MyApplication.getAppContext(),
                        msg,
                        Toast.LENGTH_LONG);
            } else {
                longToast.setText(msg);
            }
            longToast.setGravity(Gravity.BOTTOM,
                    0,
                    dp2px(MyApplication.getAppContext(),
                            64));
            longToast.show();
        }
    }
    /**
     * 长时间显示Toast【居中】
     * @param msg 显示的内容*/
    public static void showLongToastCenter(String msg){
        if(MyApplication.getAppContext() != null) {
            if (longToast == null) {
                longToast = Toast.makeText(MyApplication.getAppContext(),
                        msg,
                        Toast.LENGTH_LONG);
            } else {
                longToast.setText(msg);
            }
            longToast.setGravity(Gravity.CENTER, 0, 0);
            longToast.show();
        }
    }
    /**
     * 长时间显示Toast【居上】
     * @param msg 显示的内容*/
    public static void showLongToastTop(String msg){
        if(MyApplication.getAppContext() != null) {
            if (longToast == null) {
                longToast = Toast.makeText(MyApplication.getAppContext(),
                        msg,
                        Toast.LENGTH_LONG);
            } else {
                longToast.setText(msg);
            }
            longToast.setGravity(Gravity.TOP, 0, 0);
            longToast.show();
        }
    }

}
