package com.karas.wftoolkit;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.text.TextUtils;
import android.util.Log;

import com.fanjun.keeplive.KeepLive;
import com.fanjun.keeplive.config.ForegroundNotification;
import com.fanjun.keeplive.config.ForegroundNotificationClickListener;
import com.fanjun.keeplive.config.KeepLiveService;
import com.karas.wftoolkit.CommonUtils.CrashHandler;
import com.karas.wftoolkit.CommonUtils.SharedPreferencesUtils;
import com.karas.wftoolkit.DBFlow.TranslateDataBaseUtils;
import com.karas.wftoolkit.DBFlow.TranslateMap;
import com.karas.wftoolkit.DBFlow.TranslateTable;
import com.karas.wftoolkit.Mail.EmailConstants;
import com.raizlabs.android.dbflow.config.FlowManager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Hongliang Luo on 2018/8/7.
 **/
public class MyApplication extends Application {
    private static Context mAppContext;
    private static final String TAG="MyApplication";

    @Override
    public void onCreate() {
        super.onCreate();
//        CrashHandler crashHandler=CrashHandler.getInstance();
//        crashHandler.init(getApplicationContext());
        mAppContext = getApplicationContext();
        FlowManager.init(this);
        initKeepAlive(this);
    }

    /**获取系统上下文*/
    public static Context getAppContext()
    {
        return mAppContext;
    }

    public static void initKeepAlive(Application app){
        ForegroundNotification fn=new ForegroundNotification("测试", "描述", R.mipmap.wf_icon,
                new ForegroundNotificationClickListener() {
                    @Override
                    public void foregroundNotificationClick(Context context, Intent intent) {
                        Log.i(TAG,"KeepLiveService foregroundNotificationClick");
                    }
                });
        KeepLive.startWork(app, KeepLive.RunMode.ROGUE, fn,
                new KeepLiveService() {
                    @Override
                    public void onWorking() {
                        Log.i(TAG,"KeepLiveService onWorking");
                    }

                    @Override
                    public void onStop() {
                        Log.i(TAG,"KeepLiveService onStop");
                    }
                });
    }

    public static void initSkinManager(){
//        SkinCompatManager.withoutActivity(this)                         // 基础控件换肤初始化
//                .addInflater(new SkinMaterialViewInflater())            // material design 控件换肤初始化[可选]
////                .addInflater(new SkinConstraintViewInflater())          // ConstraintLayout 控件换肤初始化[可选]
////                .addInflater(new SkinCardViewInflater())                // CardView v7 控件换肤初始化[可选]
//                .setSkinStatusBarColorEnable(true)                     // 关闭状态栏换肤，默认打开[可选]
//                .setSkinWindowBackgroundEnable(true)                   // 关闭windowBackground换肤，默认打开[可选]
//                .loadSkin();
    }

    public static void initDatabase(){
//        boolean isFirstRun=SharedPreferencesUtils.getInstance()
//                .get(SharedPreferencesUtils.KEY_APP_FIRST_RUN,true);
//        Log.i("first run flag",isFirstRun+"");
//        if(isFirstRun){
//            SharedPreferencesUtils.getInstance().save(SharedPreferencesUtils.KEY_APP_FIRST_RUN,false);
        TranslateMap.clearMap();
        TranslateMap.mapPreInstall();
        HashMap<String,String> map=TranslateMap.getStringHashMap();
        TranslateDataBaseUtils.clearTable();
        ArrayList<TranslateTable>tts=new ArrayList<>();
        for (String key:map.keySet()){
            TranslateTable tt=new TranslateTable();
            tt.cnString=map.get(key);
            tt.enString=key;
            tt.id=key.hashCode();
            Log.i("init database","id="+tt.id+",en="+tt.enString+",cn="+tt.cnString);
            tts.add(tt);
        }
        Log.i(TAG,"tts.size="+tts.size());
        TranslateDataBaseUtils.saveAll(tts);
//        }
    }

    public static String getAppString(int id){
        return MyApplication.getAppContext().getResources()
                .getString(id);
    }

    /**
     * 获取进程号对应的进程名
     *
     * @param pid 进程号
     * @return 进程名
     */
    private static String getProcessName(int pid) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            return processName;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 将px值转换为dip或dp值
     * @param context 上下文
     * @param pxValue px值
     * @return 转换后的值
     */
    public static int px2dp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static int dp2px(Context context,float dpValue){
        float scale=context.getResources().getDisplayMetrics().density;
        return (int)(dpValue*scale+0.5f);
    }

    @Override
    public Resources getResources() {
        Resources res=super.getResources();
        Configuration configuration=res.getConfiguration();
        if(configuration.fontScale!=1.0f){
            configuration.fontScale=1.0f;//app的字体缩放还原为1.0，即不缩放
            res.updateConfiguration(configuration,res.getDisplayMetrics());
        }
        return res;
    }
}
