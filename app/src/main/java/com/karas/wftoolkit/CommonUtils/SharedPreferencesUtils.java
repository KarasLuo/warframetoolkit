package com.karas.wftoolkit.CommonUtils;

import android.content.Context;
import android.content.SharedPreferences;
import android.nfc.Tag;
import android.util.Log;

import com.karas.wftoolkit.MyApplication;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Hongliang Luo on 2018/9/17.
 **/
public class SharedPreferencesUtils {

    private static SharedPreferences mSharedPreferences;
    private static SharedPreferencesUtils instance;
    //账户
    public static final String KEY_USER_NAME="UserName";
    public static final String KEY_PASSWORD="Password";
    public static final String KEY_EMAIL_RECIPIENTS="EmailRecipients";

    public static final String KEY_SETTING_AUTO_NOTIFY_3F="AutoNotify3F";
    public static final String KEY_SETTING_AUTO_NOTIFY_ALERT="AutoNotifyAlert";
    public static final String KEY_SETTING_AUTO_NOTIFY_VOID_TRADER="AutoNotifyVoidTrader";


    private SharedPreferencesUtils() {
    }

    public static SharedPreferencesUtils getInstance() {
        if (mSharedPreferences == null || instance == null) {
            synchronized (SharedPreferencesUtils.class) {
                if (mSharedPreferences == null || instance == null) {
                    instance = new SharedPreferencesUtils();
                    Context context = MyApplication.getAppContext();
                    mSharedPreferences = context.getSharedPreferences(
                            context.getPackageName() + ".JKSharedPreferences",
                            Context.MODE_PRIVATE);
                }
            }
        }
        return instance;
    }

    /**
     * 清空数据
     *
     * @return true 成功
     */
    public boolean clear() {
        return mSharedPreferences.edit().clear().commit();
    }

    /**
     * 保存数据
     *
     * @param key   键
     * @param value 保存的value
     */
    public boolean save(String key, long value) {
        return mSharedPreferences.edit().putLong(key, value).commit();
    }

    public boolean save(String key,boolean value){
        Log.e("SharedPreferencesUtils","save boolean");
        return mSharedPreferences.edit().putBoolean(key,value).commit();
    }

    public boolean save(String key,String value){
        return mSharedPreferences.edit().putString(key,value).commit();
    }

    public boolean save(String key,int value){
        return mSharedPreferences.edit().putInt(key,value).commit();
    }

    public boolean save(String key, List<String> list,String split){
        if(list.size()>0){
            StringBuilder str= new StringBuilder();
            for (int i=0;i<list.size();i++){
                str.append(list.get(i)).append(split);
            }
            return mSharedPreferences.edit().putString(key,str.toString()).commit();
        }
        return false;
    }

    /**
     * 获取保存的数据
     *
     * @param key      键
     * @param defValue 默认返回的value
     * @return value
     */
    public long get(String key, long defValue) {
        return mSharedPreferences.getLong(key, defValue);
    }

    public boolean get(String key, boolean defValue) {
        return mSharedPreferences.getBoolean(key, defValue);
    }

    public String get(String key, String defValue) {
        return mSharedPreferences.getString(key, defValue);
    }

    public int get(String key, int defValue) {
        return mSharedPreferences.getInt(key, defValue);
    }

    public List<String> get(String key,String split, String defValue) {
        String[] str= mSharedPreferences.getString(key, defValue).split(split);
        Log.i("spu","str[]="+ Arrays.toString(str));
        if(str.length==1&&str[0].equals(defValue)){
            return new ArrayList<String>();
        }
        return new ArrayList<>(Arrays.asList(str));
    }
}
