package com.karas.wftoolkit.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.karas.wftoolkit.AppFragment;
import com.karas.wftoolkit.DBFlow.TranslateDataBaseUtils;
import com.karas.wftoolkit.R;
import com.karas.wftoolkit.Retrofit2.Bean.CetusCycleBean;
import com.karas.wftoolkit.Retrofit2.Bean.EarthCycleBean;
import com.karas.wftoolkit.Retrofit2.Bean.VallisCycleBean;
import com.karas.wftoolkit.Retrofit2.Bean.VoidTraderBean;
import com.karas.wftoolkit.Retrofit2.Retrofit2Interface;
import com.karas.wftoolkit.Retrofit2.Retrofit2Utils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class WorldTimeFragment extends AppFragment {
    final static public String TAG="世界时间";

    private TextView tvCetusState;
    private TextView tvCetusTime;
    private TextView tvEarthState;
    private TextView tvEarthTime;
    private TextView tvVallisState;
    private TextView tvVallisTime;
    private TextView tvVoidTraderLocation;
    private TextView tvVoidTraderTime;

    private Disposable cetusCycleDisposable;
    private Disposable earthCycleDisposable;
    private Disposable vallisCycleDisposable;
    private Disposable voidTraderDisposable;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_world_time;
    }

    @Override
    protected void initData() {
        disposeHttpRequest();
    }

    private void disposeHttpRequest(){
        if(cetusCycleDisposable!=null){
            cetusCycleDisposable.dispose();
            cetusCycleDisposable=null;
        }
        if(earthCycleDisposable!=null){
            earthCycleDisposable.dispose();
            earthCycleDisposable=null;
        }
        if(vallisCycleDisposable!=null){
            vallisCycleDisposable.dispose();
            vallisCycleDisposable=null;
        }
        if(voidTraderDisposable!=null){
            voidTraderDisposable.dispose();
            voidTraderDisposable=null;
        }
    }

    @Override
    public void onDestroyView() {
        disposeHttpRequest();
        super.onDestroyView();
    }

    @Override
    protected String getTAG() {
        return TAG;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        tvCetusState=view.findViewById(R.id.tv_cetus_state);
        tvCetusTime=view.findViewById(R.id.tv_cetus_time);
        tvCetusState.setText("loading");
        tvCetusTime.setText("loading");
        tvEarthState=view.findViewById(R.id.tv_earth_state);
        tvEarthTime=view.findViewById(R.id.tv_earth_time);
        tvEarthState.setText("loading");
        tvEarthTime.setText("loading");
        tvVallisState=view.findViewById(R.id.tv_vallis_state);
        tvVallisTime=view.findViewById(R.id.tv_vallis_time);
        tvVallisState.setText("loading");
        tvVallisTime.setText("loading");
        tvVoidTraderLocation=view.findViewById(R.id.tv_voidtrader_location);
        tvVoidTraderTime=view.findViewById(R.id.tv_voidtrader_time);
        tvVoidTraderLocation.setText("loading");
        tvVoidTraderTime.setText("loading");
        loadData();
    }

    private void loadData(){
        Retrofit retrofit=Retrofit2Utils.getInstance().getRetrofit(null);
        cetusCycleDisposable = retrofit.create(Retrofit2Interface.class)
                .getCetusCycle()
                .subscribeOn(Schedulers.io())
                .concatMap(new Function<CetusCycleBean, ObservableSource<List<String>>>() {
                    @Override
                    public ObservableSource<List<String>> apply(CetusCycleBean bean)
                            throws Exception {
                        List<String>strs=new ArrayList<>();
                        strs.add(bean.getState());
                        strs.add(bean.getShortString());
                        return TranslateDataBaseUtils.translateList(strs);
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<String>>() {
                    @Override
                    public void accept(List<String> strs) throws Exception {
                        tvCetusState.setText(strs.get(0));
                        tvCetusTime.setText(strs.get(1));
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Retrofit2Utils.processError(throwable);
                    }
                });
        earthCycleDisposable=retrofit.create(Retrofit2Interface.class)
                .getEarthCycle()
                .subscribeOn(Schedulers.io())
                .concatMap(new Function<EarthCycleBean, ObservableSource<List<String>>>() {
                    @Override
                    public ObservableSource<List<String>> apply(EarthCycleBean bean) throws Exception {
                        List<String>strs=new ArrayList<>();
                        strs.add(bean.getState());
                        strs.add(bean.getTimeLeft());
                        return TranslateDataBaseUtils.translateList(strs);
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<String>>() {
                    @Override
                    public void accept(List<String> strs) throws Exception {
                        tvEarthState.setText(strs.get(0));
                        tvEarthTime.setText(strs.get(1));
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Retrofit2Utils.processError(throwable);
                    }
                });
        vallisCycleDisposable=retrofit.create(Retrofit2Interface.class)
                .getVallisCycle()
                .subscribeOn(Schedulers.io())
                .concatMap(new Function<VallisCycleBean, ObservableSource<List<String>>>() {
                    @Override
                    public ObservableSource<List<String>> apply(VallisCycleBean bean) throws Exception {
                        List<String>strs=new ArrayList<>();
                        strs.add(bean.getState());
                        strs.add(bean.getShortString());
                        return TranslateDataBaseUtils.translateList(strs);
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<String>>() {
                    @Override
                    public void accept(List<String> strs) throws Exception {
                        tvVallisState.setText(strs.get(0));
                        tvVallisTime.setText(strs.get(1));
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Retrofit2Utils.processError(throwable);
                    }
                });
        voidTraderDisposable=retrofit.create(Retrofit2Interface.class)
                .getVoidTrader()
                .subscribeOn(Schedulers.io())
                .concatMap(new Function<VoidTraderBean, ObservableSource<List<String>>>() {
                    @Override
                    public ObservableSource<List<String>> apply(VoidTraderBean bean) throws Exception {
                        List<String>strs=new ArrayList<>();
                        strs.add(bean.getLocation());
                        if (bean.isActive()){
                            strs.add(bean.getEndString()+" Leave");
                        }else {
                            strs.add(bean.getStartString()+" Come");
                        }
                        return TranslateDataBaseUtils.translateList(strs);
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<String>>() {
                    @Override
                    public void accept(List<String> strs) throws Exception {
                        tvVoidTraderLocation.setText(strs.get(0));
                        tvVoidTraderTime.setText(strs.get(1));
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Retrofit2Utils.processError(throwable);
                    }
                });
    }

}
