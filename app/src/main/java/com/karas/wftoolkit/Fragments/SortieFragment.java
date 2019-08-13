package com.karas.wftoolkit.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.karas.wftoolkit.AppFragment;
import com.karas.wftoolkit.DBFlow.TranslateDataBaseUtils;
import com.karas.wftoolkit.R;
import com.karas.wftoolkit.Retrofit2.Bean.SortieBean;
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

public class SortieFragment extends AppFragment {
    final static public String TAG="每日突击";
    private TextView tvBoss;
    private TextView tvFaction;
    private TextView tvnode1;
    private TextView tvMisson1;
    private TextView tvModifier1;
    private TextView tvnode2;
    private TextView tvMisson2;
    private TextView tvModifier2;
    private TextView tvnode3;
    private TextView tvMisson3;
    private TextView tvModifier3;
    private Disposable loadSortieDisposable;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_sortie;
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
    protected void initView(View view, Bundle savedInstanceState) {
        tvBoss=view.findViewById(R.id.tv_boss);
        tvFaction=view.findViewById(R.id.tv_faction);
        tvnode1=view.findViewById(R.id.tv_node_1);
        tvnode2=view.findViewById(R.id.tv_node_2);
        tvnode3=view.findViewById(R.id.tv_node_3);
        tvMisson1=view.findViewById(R.id.tv_mission_1);
        tvMisson2=view.findViewById(R.id.tv_mission_2);
        tvMisson3=view.findViewById(R.id.tv_mission_3);
        tvModifier1=view.findViewById(R.id.tv_modifier_1);
        tvModifier2=view.findViewById(R.id.tv_modifier_2);
        tvModifier3=view.findViewById(R.id.tv_modifier_3);
        loadSortie();
    }

    private void loadSortie(){
        disposeHttpRequest();
        getHoldingActivity().progressDialog.show(getHoldingActivity(),getString(R.string.loading));
        Retrofit retrofit=Retrofit2Utils.getInstance().getRetrofit(null);
        loadSortieDisposable=retrofit.create(Retrofit2Interface.class)
                .getSortie()
                .subscribeOn(Schedulers.io())
                .concatMap(new Function<SortieBean, ObservableSource<List<String>>>() {
                    @Override
                    public ObservableSource<List<String>> apply(SortieBean sortieBean) throws Exception {
                        List<String>strs=new ArrayList<>();
                        String boss=sortieBean.getBoss();
                        String faction=sortieBean.getFaction();
                        List<SortieBean.VariantsBean>variantsBeans=sortieBean.getVariants();
                        String node1=variantsBeans.get(0).getNode();
                        String mission1=variantsBeans.get(0).getMissionType();
                        String modifier1=variantsBeans.get(0).getModifier();
                        String node2=variantsBeans.get(1).getNode();
                        String mission2=variantsBeans.get(1).getMissionType();
                        String modifier2=variantsBeans.get(1).getModifier();
                        String node3=variantsBeans.get(2).getNode();
                        String mission3=variantsBeans.get(2).getMissionType();
                        String modifier3=variantsBeans.get(2).getModifier();
                        strs.add(boss);
                        strs.add(faction);
                        strs.add(node1);
                        strs.add(mission1);
                        strs.add(modifier1);
                        strs.add(node2);
                        strs.add(mission2);
                        strs.add(modifier2);
                        strs.add(node3);
                        strs.add(mission3);
                        strs.add(modifier3);
                        return TranslateDataBaseUtils.translateList(strs);
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<String>>() {
                    @Override
                    public void accept(List<String> strs) throws Exception {
                        tvBoss.setText(strs.get(0));
                        tvFaction.setText(strs.get(1));
                        tvnode1.setText(strs.get(2).replace("(","\n("));
                        tvnode2.setText(strs.get(5).replace("(","\n("));
                        tvnode3.setText(strs.get(8).replace("(","\n("));
                        tvMisson1.setText(strs.get(3));
                        tvMisson2.setText(strs.get(6));
                        tvMisson3.setText(strs.get(9));
                        tvModifier1.setText(strs.get(4).replace(":",":\n"));
                        tvModifier2.setText(strs.get(7).replace(":",":\n"));
                        tvModifier3.setText(strs.get(10).replace(":",":\n"));
                        getHoldingActivity().progressDialog.dismiss();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        getHoldingActivity().progressDialog.dismiss();
                        Retrofit2Utils.processError(throwable);
                    }
                });
    }

    private void disposeHttpRequest(){
        if(loadSortieDisposable!=null){
            loadSortieDisposable.dispose();
            loadSortieDisposable=null;
        }
    }
}
