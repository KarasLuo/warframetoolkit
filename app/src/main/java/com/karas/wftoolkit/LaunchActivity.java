package com.karas.wftoolkit;

import android.content.Intent;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.karas.wftoolkit.CommonUtils.SharedPreferencesUtils;
import com.karas.wftoolkit.CommonUtils.ToastUtils;
import com.karas.wftoolkit.DBFlow.TranslateDataBaseUtils;
import com.karas.wftoolkit.DBFlow.TranslateMap;
import com.karas.wftoolkit.DBFlow.TranslateTable;
import com.karas.wftoolkit.Mail.EmailConstants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class LaunchActivity extends AppCompatActivity {
    static private final String TAG="LaunchActivity";
    private Disposable disposable;

    @Override
    protected void onDestroy() {
        if(disposable!=null){
            disposable.dispose();
            disposable=null;
        }
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        disposable=Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                if(!emitter.isDisposed()){
                    //do something here
                    Log.e("thread test","线程任务");
                    EmailConstants.init();
                    MyApplication.initDatabase();
                    emitter.onNext("");
                    emitter.onComplete();
                }
            }
        })
                .delay(1,TimeUnit.SECONDS)
                .observeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        //跳转至 MainActivity
                        Log.e("thread test", "跳转至主页");
                        Intent intent = new Intent(LaunchActivity.this, MainActivity.class);
                        startActivity(intent);
                        //结束当前的 Activity
                        LaunchActivity.this.finish();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e("error","error msg");
                        throwable.printStackTrace();
                    }
                });
    }

}
