package com.karas.wftoolkit.CommonUtils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.karas.wftoolkit.MyApplication;
import com.karas.wftoolkit.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static android.os.Environment.DIRECTORY_DOCUMENTS;

/**
 * Created by Hongliang Luo on 2018/8/6.
 **/
public class CrashHandler implements UncaughtExceptionHandler{
    public static final String TAG = "CrashHandler";
    private UncaughtExceptionHandler mDefaultHandler;
    private static CrashHandler Instance = new CrashHandler();
    private Context mContext;
    //用来存储设备信息和异常信息
    private Map<String, String> infos= new HashMap<String, String>();

    //获取日期,作为日志文件名
    @SuppressLint("SimpleDateFormat")
    private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");

    //构造函数
    private CrashHandler() {
        Log.i(TAG,"crash handler is created.");
    }

    // 获取CrashHandler实例
    public static CrashHandler getInstance() {
        return Instance;
    }

    /**
     * 初始化
     *
     * @param context 上下文
     */
    public void init(Context context) {
        mContext = context;
        //获取系统默认的UncaughtException处理器
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        //设置该CrashHandler为程序的默认处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /**
     * 异常处理
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if (!handleException(ex) && mDefaultHandler != null) {
            //如果用户没有处理则让系统默认的异常处理器来处理
            mDefaultHandler.uncaughtException(thread, ex);
        } else {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                Log.e(TAG, "error : ", e);
            }
            //退出程序
            RxBus.getInstance().post(RxBus.getInstance()
                    .new BusEvent(RxBus.APP_EXIT,""));
        }
    }

    /**
     * 自定义错误处理,收集错误信息 发送错误报告等.
     *
     * @param ex 异常
     * @return true:处理了该异常信息;否则返回false.
     */
    private boolean handleException(Throwable ex) {
        if (ex == null) {
            return false;
        }
        //使用Toast来显示异常信息
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                Log.e(TAG,"程序即将退出1");
                Toast.makeText(mContext,"程序出现异常",Toast.LENGTH_LONG).show();
                Log.e(TAG,"程序即将退出2");
                Looper.loop();
            }
        }.start();
        //收集设备参数信息
        collectDeviceInfo(mContext);
        //保存日志文件
        String fn=saveCrashInfo2File(ex);
        Log.e(TAG,"crashFileName="+fn);
        return true;
    }

    /**
     * 收集设备参数信息
     * @param ctx 上下文
     */
    private void collectDeviceInfo(Context ctx) {
        try {
            String androidVersion= PhoneInformationUtils.getDeviceAndroidVersion();
            int sdk= PhoneInformationUtils.getDeviceSDK();
            String deviceModel= PhoneInformationUtils.getDeviceModel();
            infos.put("deviceAndroidVersion",androidVersion);
            infos.put("deviceModel",deviceModel);
            infos.put("deviceSDK",sdk+"");
        }catch (Exception e){
            Log.e(TAG, "An error occurred when collect device information.", e);
        }
        try {
            PackageManager pm = ctx.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(), PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                String versionName = pi.versionName == null ? "null" : pi.versionName;
                String versionCode = pi.versionCode + "";
                infos.put("appVersionName", versionName);
                infos.put("appVersionCode", versionCode);
                infos.put("others", "***********************************************************");
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "An error occurred when collect package information.", e);
        }
        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                infos.put(field.getName(), field.get(null).toString());
               // Log.e(TAG, field.getName() + " : " + field.get(null));
            } catch (Exception e) {
                Log.e(TAG, "An error occurred when collect crash information.", e);
            }
        }
    }

    /**
     * 保存错误信息到文件中
     *
     * @param ex 异常
     * @return  返回文件名称,便于将文件传送到服务器
     */
    private String saveCrashInfo2File(Throwable ex) {
        Log.e(TAG,"saveCrashInfo2File");
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : infos.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            sb.append(key).append("=").append(value).append("\n");
        }

        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        String result = writer.toString();
        sb.append(result);
        String time = formatter.format(new Date());
        String fileName = MyApplication.getAppString(R.string.app_name)+ "crash-" + time+ ".log";
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            String path = Environment.getExternalStoragePublicDirectory(DIRECTORY_DOCUMENTS) + "/";
            try {
                File dir = new File(path);
                if (!dir.exists()) {
                    dir.mkdirs();
                    Log.e(TAG, "创建文件夹 " +dir);
                }else {
                    Log.e(TAG, "文件夹已存在 " +dir);
                }
                FileOutputStream fos = new FileOutputStream(path + fileName);
                fos.write(sb.toString().getBytes());
                fos.flush();
                fos.close();
                return path + fileName;
            }catch (Exception e) {
                Log.e(TAG, "An error occurred when create log file.", e);
            }
        }
        return null;
    }
}
