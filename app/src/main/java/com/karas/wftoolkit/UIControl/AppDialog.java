package com.karas.wftoolkit.UIControl;

import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.karas.wftoolkit.AppActivity;


/**
 * POAVO
 * Created by Hongliang Luo on 2019/6/3.
 **/
public abstract class AppDialog extends AlertDialog {
    private final static String TAG="AppDialog";
    private AppActivity activity;

    protected AppDialog(@NonNull AppActivity activity) {
        super(activity);
        this.activity=activity;
    }

    @Override
    public void show() {
        //对于一定延时后弹出的对话框，如网络请求、复杂计算后弹出
        //如果要弹出对话框的activity被销毁则不弹
        if(!activity.isDestroyed()){
            super.show();
        }else {
            Log.e(TAG,"activity is destroyed!!!");
        }
    }
}
