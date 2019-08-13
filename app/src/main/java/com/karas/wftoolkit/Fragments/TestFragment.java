package com.karas.wftoolkit.Fragments;


import android.os.Bundle;
import android.view.View;

import com.karas.wftoolkit.AppFragment;
import com.karas.wftoolkit.R;

public class TestFragment extends AppFragment {
    final static public String TAG="测试页面";

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_test;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected String getTAG() {
        return TAG;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {

    }

}
