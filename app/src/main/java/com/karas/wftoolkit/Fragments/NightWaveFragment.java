package com.karas.wftoolkit.Fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.karas.wftoolkit.AppFragment;
import com.karas.wftoolkit.DBFlow.TranslateDataBaseUtils;
import com.karas.wftoolkit.R;
import com.karas.wftoolkit.Retrofit2.Bean.NightWaveBean;
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

public class NightWaveFragment extends AppFragment {
    final static public String TAG="午夜电波";

    private TextView tvTag;
    private TextView tvSeason;
    private TextView tvExpiry;
    private RecyclerView rvNightWave;
    private Disposable loadNightWaveDisposable;
    class SimpleNightWaveBean{
        String title;
        String expiry;
        String desc;
        String reputation;
    }

    private void disposeHttpRequest(){
        if(loadNightWaveDisposable!=null){
            loadNightWaveDisposable.dispose();
            loadNightWaveDisposable=null;
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_night_wave;
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
        tvTag=view.findViewById(R.id.tv_tag);
        tvExpiry=view.findViewById(R.id.tv_expiry);
        tvSeason=view.findViewById(R.id.tv_season);
        rvNightWave=view.findViewById(R.id.rv_night_wave);
        rvNightWave.setLayoutManager(new LinearLayoutManager(rvNightWave.getContext(),
                LinearLayoutManager.VERTICAL,false));
        loadNightWave();
    }

    private void loadNightWave(){
        disposeHttpRequest();
        getHoldingActivity().progressDialog.show(getHoldingActivity(),getString(R.string.loading));
        Retrofit retrofit=Retrofit2Utils.getInstance().getRetrofit(null);
        loadNightWaveDisposable=retrofit.create(Retrofit2Interface.class)
                .getNightWave()
                .subscribeOn(Schedulers.io())
                .concatMap(new Function<NightWaveBean, ObservableSource<List<String>>>() {
                    @Override
                    public ObservableSource<List<String>> apply(NightWaveBean bean) throws Exception {
                        List<List<String>>strsList=new ArrayList<>();
                        //list 0
                        List<String>totalStrs=new ArrayList<>();
                        totalStrs.add(bean.getTag());//title
                        totalStrs.add(bean.getExpiry().split("T")[0]);//expiry
                        totalStrs.add(bean.getSeason()+"");//desc
                        totalStrs.add("");//reputation
                        strsList.add(totalStrs);
                        for (NightWaveBean.ActiveChallengesBean b:bean.getActiveChallenges()){
                            List<String>strs=new ArrayList<>();
                            String title=b.getTitle()
                                    .replace("Season Daily ","")
                                    .replace("Season Weekly ","");
                            strs.add(title);
                            strs.add(b.getExpiry().split("T")[0]);
                            String desc=b.getDesc();
                            strs.add(desc.contains("[PH]")?"":desc);
                            strs.add(b.getReputation()+"");
                            strsList.add(strs);
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
                .map(new Function<List<String>, SimpleNightWaveBean>() {
                    @Override
                    public SimpleNightWaveBean apply(List<String> strings) throws Exception {
                        SimpleNightWaveBean snwb=new SimpleNightWaveBean();
                        snwb.title=strings.get(0);
                        snwb.expiry=strings.get(1);
                        snwb.desc=strings.get(2);
                        snwb.reputation=strings.get(3);
                        return snwb;
                    }
                })
                .toList().toObservable()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<SimpleNightWaveBean>>() {
                    @Override
                    public void accept(List<SimpleNightWaveBean> beans) throws Exception {
                        String tag=beans.get(0).title;
                        String expiry="到期时间:"+beans.get(0).expiry;
                        String season="第"+beans.get(0).desc+"季";
                        tvExpiry.setText(expiry);
                        tvSeason.setText(season);
                        tvTag.setText(tag);
                        List<SimpleNightWaveBean>snwb=beans.subList(1,beans.size());
                        Log.i(TAG,"beans size="+beans.size()+",snwb size="+snwb.size());
                        rvNightWave.setAdapter(new NightWaveRvAdapter(snwb));
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

    class NightWaveRvAdapter extends RecyclerView.Adapter<NightWaveRvAdapter.NightWaveItemView>{
        private List<SimpleNightWaveBean>list;

        NightWaveRvAdapter(List<SimpleNightWaveBean>list){
            this.list=list;
        }

        @NonNull
        @Override
        public NightWaveItemView onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view=LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.item_night_wave,viewGroup,false);
            return new NightWaveItemView(view);
        }

        @Override
        public void onBindViewHolder(@NonNull NightWaveItemView nightWaveItemView, int i) {
            String expiry="到期时间:"+list.get(i).expiry;
            String desc=list.get(i).desc;
            String title=list.get(i).title;
            String reputation="声望奖励:"+list.get(i).reputation;
            nightWaveItemView.tvReputation.setText(reputation);
            nightWaveItemView.tvExpiry.setText(expiry);
            nightWaveItemView.tvDesc.setText(desc);
            nightWaveItemView.tvTitle.setText(title);
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class NightWaveItemView extends RecyclerView.ViewHolder{

            TextView tvTitle;
            TextView tvExpiry;
            TextView tvReputation;
            TextView tvDesc;
            public NightWaveItemView(@NonNull View itemView) {
                super(itemView);
                tvTitle=itemView.findViewById(R.id.tv_title);
                tvDesc=itemView.findViewById(R.id.tv_description);
                tvExpiry=itemView.findViewById(R.id.tv_expiry);
                tvReputation=itemView.findViewById(R.id.tv_reputation);
            }
        }
    }
}
