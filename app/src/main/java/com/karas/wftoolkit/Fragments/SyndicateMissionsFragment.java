package com.karas.wftoolkit.Fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.google.gson.JsonParseException;
import com.karas.wftoolkit.AppFragment;
import com.karas.wftoolkit.R;
import com.karas.wftoolkit.Retrofit2.Bean.SyndicateMissionsBean;
import com.karas.wftoolkit.Retrofit2.Retrofit2Interface;
import com.karas.wftoolkit.Retrofit2.Retrofit2Utils;
import com.karas.wftoolkit.UIControl.SyndicateElvAdapter;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class SyndicateMissionsFragment extends AppFragment {
    final static public String TAG="赏金任务";

    private ExpandableListView elvSyndicate;
    private SyndicateElvAdapter syndicateElvAdapter;
    private Disposable loadSyndicateDisposable;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_syndicate_missions;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected String getTAG() {
        return TAG;
    }

    @Override
    public void onDestroyView() {
        disposeHttpRequest();
        super.onDestroyView();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        elvSyndicate=view.findViewById(R.id.elv_syndicate);
        loadSyndicate();
    }

    private void loadSyndicate(){
        disposeHttpRequest();
        getHoldingActivity().progressDialog.show(getHoldingActivity(),getString(R.string.loading));
        Retrofit retrofit=Retrofit2Utils.getInstance().getRetrofit(null);
        loadSyndicateDisposable=retrofit.create(Retrofit2Interface.class)
                .getSyndicateMissions()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<SyndicateMissionsBean>>() {
                    @Override
                    public void accept(List<SyndicateMissionsBean> beans)
                            throws Exception {
                        List<SyndicateMissionsBean> list=new ArrayList<>();
                        for (SyndicateMissionsBean bean:beans){
                            if(bean.getJobs()!=null){
                                if(bean.getJobs().size()>0){
                                    list.add(bean);
                                }
                            }
                        }
                        syndicateElvAdapter=new SyndicateElvAdapter(list);
                        elvSyndicate.setAdapter(syndicateElvAdapter);
                        getHoldingActivity().progressDialog.dismiss();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        getHoldingActivity().progressDialog.dismiss();
                        if (throwable instanceof JsonParseException){
                            getHoldingActivity().showToast("赏金任务奖池更新中，请稍后再查询！");
                        }else {
                            Retrofit2Utils.processError(throwable);
                        }
                    }
                });
    }

    private void disposeHttpRequest(){
        if(loadSyndicateDisposable!=null){
            loadSyndicateDisposable.dispose();
            loadSyndicateDisposable=null;
        }
        if(syndicateElvAdapter!=null){
            if(syndicateElvAdapter.translateDisposable!=null){
                syndicateElvAdapter.translateDisposable.dispose();
                syndicateElvAdapter.translateDisposable=null;
            }
        }
    }
}
