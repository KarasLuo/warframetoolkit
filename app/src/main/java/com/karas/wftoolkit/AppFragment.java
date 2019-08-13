package com.karas.wftoolkit;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.karas.wftoolkit.QqConnect.TencentUtils;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.Tencent;

import me.yokeyword.swipebackfragment.SwipeBackFragment;
import me.yokeyword.swipebackfragment.SwipeBackLayout;

/**
 * Created by Hongliang Luo on 2019/5/30.
 **/
public abstract class AppFragment extends SwipeBackFragment {

    final static public String TAG="AppFragment";

    protected abstract int getLayoutId();
    protected abstract void initData();
    protected abstract String getTAG();
    protected abstract void initView(View view, Bundle savedInstanceState);

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(getLayoutId(),container,false);
        initData();
        initView(view,savedInstanceState);
        setSwipeBackListener();
        return attachToSwipeBack(view);
    }

    /**
     * 设置滑动监听
     */
    private void setSwipeBackListener(){
        getSwipeBackLayout().addSwipeListener(new SwipeBackLayout.OnSwipeListener() {
            @Override
            public void onDragStateChange(int state) {

            }

            @Override
            public void onEdgeTouch(int oritentationEdgeFlag) {
                //判断activity里是否不止一个fragment，否则不让滑动
                if(getHoldingActivity().getFragmentCount()>1){
                    Log.i(TAG,"fragment onEdgeTouch:swipeBackEnable=true");
                    setSwipeBackEnable(false);//有抽屉菜单，不滑动
//                    setSwipeBackEnable(true);
                }else {
                    Log.i(TAG,"fragment onEdgeTouch:swipeBackEnable=false");
                    setSwipeBackEnable(false);
                }
            }

            @Override
            public void onDragScrolled(float scrollPercent) {

            }
        });
    }

    /**
     * 获取activity容器的实例，方便使用activity的方法
     * @return AppActivity
     */
    protected AppActivity getHoldingActivity(){
        if(getActivity() instanceof AppActivity){
            return (AppActivity)getActivity();
        }else {
            throw new ClassCastException("activity must extends AppActivity:"+
                    " get holding activity failed!");
        }
    }

    /**
     * 当前fragment添加新的fragment
     * 从fragment中添加的fragment都不是栈底，要用跳转动画
     * @param fragment fragment
     */
    protected void addFragment(AppFragment fragment){
        getHoldingActivity().addFragmentWithAnimations(fragment);
    }

    /**
     * 移除fragment
     */
    protected void removeFragment(){
        getHoldingActivity().removeFragmentWithAnimations();
    }

    @Override
    public void onDestroyView() {
        getHoldingActivity().progressDialog.dismiss();
        super.onDestroyView();
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
