package com.karas.wftoolkit.Fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.karas.wftoolkit.AppFragment;
import com.karas.wftoolkit.DBFlow.TranslateDataBaseUtils;
import com.karas.wftoolkit.R;
import com.karas.wftoolkit.Retrofit2.Bean.FissuresBean;
import com.karas.wftoolkit.Retrofit2.Retrofit2Interface;
import com.karas.wftoolkit.Retrofit2.Retrofit2Utils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class FissuresFragment extends AppFragment {
    final static public String TAG="虚空裂隙";

    private RecyclerView rvFissures;
    private Disposable loadFissuresDisposabale;
    class SimpleFissuresBean{
        String node;
        String tier;
        String mission;
        String enemy;
        String eta;
    }
    private List<SimpleFissuresBean> fissuresBeans=new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_fissures;
    }

    @Override
    protected void initData() {

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
        rvFissures=view.findViewById(R.id.rv_fissures);
        rvFissures.setLayoutManager(new LinearLayoutManager(rvFissures.getContext(),
                LinearLayoutManager.VERTICAL,false));
        loadFissures();
    }

    private void disposeHttpRequest(){
        if(loadFissuresDisposabale!=null){
            loadFissuresDisposabale.dispose();
            loadFissuresDisposabale=null;
        }
    }

    private void loadFissures(){
        disposeHttpRequest();
        getHoldingActivity().progressDialog.show(getHoldingActivity(),getString(R.string.loading));
        Retrofit retrofit=Retrofit2Utils.getInstance().getRetrofit(null);
        loadFissuresDisposabale=retrofit.create(Retrofit2Interface.class)
                .getFissures()
                .subscribeOn(Schedulers.io())
                .concatMap(new Function<List<FissuresBean>, ObservableSource<List<String>>>() {
                    @Override
                    public ObservableSource<List<String>> apply(List<FissuresBean> beans) throws Exception {
                        List<List<String>>strsList=new ArrayList<>();
                        for (FissuresBean bean:beans){
                            if(!bean.isExpired()){
                                List<String>strs=new ArrayList<>();
                                strs.add(bean.getNode());
                                strs.add(bean.getTier());
                                strs.add(bean.getMissionType());
                                strs.add(bean.getEnemy());
                                strs.add(bean.getEta());
                                strsList.add(strs);
                            }
                        }
                        return Observable.fromIterable(strsList);
                    }
                })
                .concatMap(new Function<List<String>, ObservableSource<List<String>>>() {
                    @Override
                    public ObservableSource<List<String>> apply(List<String> strings) throws Exception {
                        return TranslateDataBaseUtils.translateList(strings);
                    }
                })
                .map(new Function<List<String>, SimpleFissuresBean>() {
                    @Override
                    public SimpleFissuresBean apply(List<String> ss) throws Exception {
                        SimpleFissuresBean sfb=new SimpleFissuresBean();
                        sfb.node=ss.get(0);
                        sfb.tier=ss.get(1);
                        sfb.mission=ss.get(2);
                        sfb.enemy=ss.get(3);
                        sfb.eta=ss.get(4);
                        return sfb;
                    }
                }).toList().toObservable()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<SimpleFissuresBean>>() {
                    @Override
                    public void accept(List<SimpleFissuresBean> beans) throws Exception {
                        fissuresBeans=beans;
                        rvFissures.setAdapter(new FissuresRvAdapter());
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

    class FissuresRvAdapter extends RecyclerView.Adapter<FissuresRvAdapter.FissureView>{

        @NonNull
        @Override
        public FissureView onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view=LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.item_fissures,viewGroup,false);
            return new FissureView(view);
        }

        @Override
        public void onBindViewHolder(@NonNull FissureView fissureView, int i) {
            String node=fissuresBeans.get(i).node;
            String tier=fissuresBeans.get(i).tier;
            String mission=fissuresBeans.get(i).mission;
            String enemy=fissuresBeans.get(i).enemy;
            String eta=fissuresBeans.get(i).eta;
            fissureView.tvTier.setText(tier);
            fissureView.tvNode.setText(node);
            fissureView.tvMission.setText(mission);
            fissureView.tvEta.setText(eta);
            fissureView.tvEnemy.setText(enemy);
        }

        @Override
        public int getItemCount() {
            return fissuresBeans.size();
        }

        class FissureView extends RecyclerView.ViewHolder{
            TextView tvNode;
            TextView tvTier;
            TextView tvMission;
            TextView tvEnemy;
            TextView tvEta;
            public FissureView(@NonNull View itemView) {
                super(itemView);
                tvEnemy=itemView.findViewById(R.id.tv_enemy);
                tvEta=itemView.findViewById(R.id.tv_eta);
                tvMission=itemView.findViewById(R.id.tv_mission_type);
                tvNode=itemView.findViewById(R.id.tv_node);
                tvTier=itemView.findViewById(R.id.tv_tier);
            }
        }
    }
}
