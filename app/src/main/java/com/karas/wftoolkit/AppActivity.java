package com.karas.wftoolkit;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.karas.wftoolkit.CommonUtils.RxBus;
import com.karas.wftoolkit.CommonUtils.ToastUtils;
import com.karas.wftoolkit.UIControl.ProgressDialog;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import me.yokeyword.swipebackfragment.SwipeBackActivity;

/**
 * Created by Hongliang Luo on 2019/5/30.
 **/

public abstract class AppActivity extends SwipeBackActivity {
    static final private String TAG="AppActivity";

    //数据初始化
    public abstract void initData();
    //界面初始化
    public abstract void initView();
    //获取布局资源ID
    public abstract int getLayoutResId();
    public abstract int getFragmentContainerId();
    public abstract void onFragmentChanged(String title);

    private Disposable rxBusDisposable;
    private AppFragment lastFragment;
    public ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);//保持屏幕常亮
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//禁止横屏
        setContentView(getLayoutResId());
        initData();
        initView();
        progressDialog=new ProgressDialog();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        Log.i(TAG,"onResume()");
        super.onResume();
        //监听总线事件
        rxBusDisposable= RxBus.getInstance().toObservable(RxBus.BusEvent.class)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<RxBus.BusEvent>() {
                    @Override
                    public void accept(RxBus.BusEvent busEvent) throws Exception {
                        int event=busEvent.getEvent();
                        switch (event){
                            case RxBus.MSG_NOTIFY_EVENT:
                                showToast((String) busEvent.getMsg());
                                break;
                            case RxBus.APP_EXIT:
                                Log.i(TAG,"APP_EXIT");
                                finish();
                                break;
                            default:
                                break;
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(TAG,"RxBus exception="+throwable.toString());
                    }
                });
    }

    @Override
    protected void onPause() {
        Log.i(TAG,"onPause()");
        //取消事件总线的监听
        if(rxBusDisposable!=null){
            rxBusDisposable.dispose();
        }
        super.onPause();
    }

    @Override
    public boolean swipeBackPriority() {
        return super.swipeBackPriority();
    }

    /**
     * 跳转activity
     * @param cls 要跳转到的activity
     */
    public void switchActivity(Class<?> cls){
        startActivity(new Intent(this,cls));
    }

    public boolean hasFragment(String tag){
        return getSupportFragmentManager().findFragmentByTag(tag)!=null;
    }

    /**
     * 添加fragment，不带动画
     * 用于添加activity的第一个fragment
     * @param fragment 目标fragment
     */
    public void addFragment(AppFragment fragment){
        if(fragment!=null){
            lastFragment=fragment;
            Log.i(TAG,"addFragment lastFragment="+lastFragment);
            onFragmentChanged(lastFragment.getTAG());
            getSupportFragmentManager().beginTransaction()
                    .add(getFragmentContainerId(),fragment,fragment.getTAG())
                    .addToBackStack(((Object)fragment).getClass().getSimpleName())
                    .commit();
        }
    }

    /**
     * 添加fragment，带动画
     * @param fragment 目标fragment
     */
    public void addFragmentWithAnimations(AppFragment fragment){
        if(fragment!=null){
            onFragmentChanged(fragment.getTAG());
            int size= getSupportFragmentManager().getFragments().size();
            lastFragment=(AppFragment) (getSupportFragmentManager().getFragments()
                    .get(size-1));
            Log.i(TAG,"addFragmentWithAnimations lastFragment="+lastFragment);
            getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations(R.anim.push_right_in,R.anim.push_left_out,
                            R.anim.push_left_in,R.anim.push_right_out)
                    .add(getFragmentContainerId(),fragment,fragment.getTAG())
                    .addToBackStack(((Object)fragment).getClass().getSimpleName())
                    .hide(lastFragment)
                    .commit();
        }
    }

    /**
     * 顶部fragment出栈
     * @return boolean 是否只剩一个fragment
     */
    public boolean removeFragmentWithAnimations(){
        if(getSupportFragmentManager().getBackStackEntryCount()>1){
            onFragmentChanged(lastFragment.getTAG());
            getSupportFragmentManager().popBackStack();
            Log.i(TAG,"there are more than 1 fragments");
            return false;
        }else {
            Log.i(TAG,"there is only 1 fragment");
            return true;
        }
    }

    /**
     * 注：UserActivity调用Rxpermission库申请权限，使用了RxPermissionFragment，但是没有入栈！！！
     *     故，获取的fragmentList的数目比BackStackEntry的数目多1个！！！
     * @return int
     */
    public int getFragmentCount(){
        Log.i(TAG,"fragment count="+getSupportFragmentManager().getBackStackEntryCount());
        return getSupportFragmentManager().getBackStackEntryCount();
    }

    /**
     * 吐司
     * @param msg 文本
     */
    public void showToast(String msg) {
        ToastUtils.showShortToast(msg);
    }

    /**
     * 隐藏软键盘
     * @param view 控件视图
     * @param event 手势事件
     */
    public static void hideKeyboard(MotionEvent event, View view,
                                    AppActivity activity) {
        try {
            if (view instanceof EditText) {
                int[] location = { 0, 0 };
                view.getLocationInWindow(location);
                int left = location[0], top = location[1], right = left
                        + view.getWidth(), bottom = top + view.getHeight();
                // 判断焦点位置坐标是否在控件内，如果位置在控件外，则隐藏键盘
                if (event.getRawX() < left || event.getRawX() > right
                        || event.getY() < top || event.getRawY() > bottom) {
                    // 隐藏键盘
                    IBinder token = view.getWindowToken();
                    InputMethodManager inputMethodManager = (InputMethodManager) activity
                            .getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (inputMethodManager != null) {
                        inputMethodManager.hideSoftInputFromWindow(token,
                                InputMethodManager.HIDE_NOT_ALWAYS);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 拦截手势监听
     * @param ev 触摸事件
     * @return 布尔
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                View view = getCurrentFocus();
                //调用方法判断是否需要隐藏键盘
                hideKeyboard(ev, view, AppActivity.this);
                break;

            default:
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 设置界面字体大小不随系统变化
     * @return 资源
     */
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == Constants.REQUEST_LOGIN) {
//            TencentUtils tu=TencentUtils.getInstance();
//            Tencent.onActivityResultData(requestCode,resultCode,data,tu.getLoginListener());
//        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}

