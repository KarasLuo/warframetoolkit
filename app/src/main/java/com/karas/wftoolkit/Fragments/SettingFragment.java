package com.karas.wftoolkit.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.karas.wftoolkit.AppFragment;
import com.karas.wftoolkit.CommonUtils.SharedPreferencesUtils;
import com.karas.wftoolkit.DBFlow.TranslateDataBaseUtils;
import com.karas.wftoolkit.DBFlow.TranslateTable;
import com.karas.wftoolkit.R;
import com.karas.wftoolkit.UIControl.OnMultiClickListener;
import com.raizlabs.android.dbflow.structure.database.transaction.QueryTransaction;

public class SettingFragment extends AppFragment {
    final static public String TAG="设置";

    private Switch sNotifyTotal;
    private Switch sNotify3F;
    private Switch sNotifyAlert;
    private Switch sNotifyVoidTrader;
    private ImageView ivExbandTranslatSetting;
    private EditText etEnInput;
    private EditText etCnInput;
    private Button btnSaveWordMap;
    private LinearLayout llCn;
    private LinearLayout llEn;
    private boolean isExpand=false;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_setting;
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
        sNotifyTotal=view.findViewById(R.id.s_total);
        sNotify3F=view.findViewById(R.id.s_3f);
        sNotifyAlert=view.findViewById(R.id.s_alert);
        sNotifyVoidTrader=view.findViewById(R.id.s_voidtrader);
        ivExbandTranslatSetting=view.findViewById(R.id.iv_exband_translate);
        etEnInput=view.findViewById(R.id.et_en);
        etCnInput=view.findViewById(R.id.et_cn);
        btnSaveWordMap=view.findViewById(R.id.btn_save_word);
        llCn=view.findViewById(R.id.ll_cn_input);
        llEn=view.findViewById(R.id.ll_en_input);
        initState();
        ivExbandTranslatSetting.setOnClickListener(new OnMultiClickListener() {
            @Override
            public void onMultiClick(View view) {
                Log.e(TAG,"isExpand="+isExpand);
                if(isExpand){
                    btnSaveWordMap.setVisibility(View.GONE);
                    llCn.setVisibility(View.GONE);
                    llEn.setVisibility(View.GONE);
                    isExpand=false;
                    ivExbandTranslatSetting.setRotation(0);
                }else {
                    btnSaveWordMap.setVisibility(View.VISIBLE);
                    llCn.setVisibility(View.VISIBLE);
                    llEn.setVisibility(View.VISIBLE);
                    isExpand=true;
                    ivExbandTranslatSetting.setRotation(180);
                }
            }
        });
        sNotifyTotal.setOnClickListener(new OnMultiClickListener() {
            @Override
            public void onMultiClick(View view) {
                boolean isChecked=sNotifyTotal.isChecked();
                Log.e(TAG,"sNotifyTotal clicked="+isChecked);
                sNotify3F.setChecked(isChecked);
                sNotifyAlert.setChecked(isChecked);
                sNotifyVoidTrader.setChecked(isChecked);
            }
        });
        sNotify3F.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.e(TAG,"sNotify3F changed="+isChecked);
                SharedPreferencesUtils.getInstance()
                        .save(SharedPreferencesUtils.KEY_SETTING_AUTO_NOTIFY_3F,isChecked);
                switchTotalListen();
            }
        });
        sNotifyAlert.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.e(TAG,"sNotifyAlert changed="+isChecked);
                SharedPreferencesUtils.getInstance()
                        .save(SharedPreferencesUtils.KEY_SETTING_AUTO_NOTIFY_ALERT,isChecked);
                switchTotalListen();
            }
        });
        sNotifyVoidTrader.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.e(TAG,"sNotifyVoidTrader changed="+isChecked);
                SharedPreferencesUtils.getInstance()
                        .save(SharedPreferencesUtils.KEY_SETTING_AUTO_NOTIFY_VOID_TRADER,isChecked);
                switchTotalListen();
            }
        });
        btnSaveWordMap.setOnClickListener(new OnMultiClickListener() {
            @Override
            public void onMultiClick(View view) {
                //save to file and add to database

                //reset state
                etCnInput.setText("");
                etEnInput.setText("");
                btnSaveWordMap.setVisibility(View.GONE);
                llCn.setVisibility(View.GONE);
                llEn.setVisibility(View.GONE);
                isExpand=false;
                ivExbandTranslatSetting.setRotation(0);
            }
        });
    }

    private void initState(){
        SharedPreferencesUtils spu=SharedPreferencesUtils.getInstance();
        boolean b3f=spu.get(SharedPreferencesUtils.KEY_SETTING_AUTO_NOTIFY_3F,true);
        boolean bAlert=spu.get(SharedPreferencesUtils.KEY_SETTING_AUTO_NOTIFY_ALERT,true);
        boolean bVoidTrader=spu.get(SharedPreferencesUtils.KEY_SETTING_AUTO_NOTIFY_VOID_TRADER,true);
        sNotifyTotal.setChecked(b3f||bAlert||bVoidTrader);
        sNotify3F.setChecked(b3f);
        sNotifyAlert.setChecked(bAlert);
        sNotifyVoidTrader.setChecked(bVoidTrader);
    }

    private void switchTotalListen(){
        boolean isAllChecked=sNotify3F.isChecked() ||sNotifyAlert.isChecked()||sNotifyVoidTrader.isChecked();
        sNotifyTotal.setChecked(isAllChecked);
    }
}
