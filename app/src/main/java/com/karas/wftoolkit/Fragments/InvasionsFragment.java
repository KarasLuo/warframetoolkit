package com.karas.wftoolkit.Fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.karas.wftoolkit.AppFragment;
import com.karas.wftoolkit.DBFlow.TranslateDataBaseUtils;
import com.karas.wftoolkit.R;
import com.karas.wftoolkit.Retrofit2.Bean.ConstructionProgressBean;
import com.karas.wftoolkit.Retrofit2.Bean.InvasionsBean;
import com.karas.wftoolkit.Retrofit2.Retrofit2Interface;
import com.karas.wftoolkit.Retrofit2.Retrofit2Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class InvasionsFragment extends AppFragment {
    final static public String TAG="派系入侵";

    private RecyclerView rvInvasions;
    private Disposable loadInvasionsDisposabale;
    private Disposable loadConstructionProgressDisposabale;

    class simpleInvasionBean{
        String node;
        String defendFaction;
        String defendReward;
        String attackingFaction;
        String attackingReward;
        int progress;
    }

    private List<simpleInvasionBean> invasionsBeans=new ArrayList<>();
    private TextView tvProgress1;
    private TextView tvProgress2;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_invasions;
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
        tvProgress1=view.findViewById(R.id.tv_progress1);
        tvProgress2=view.findViewById(R.id.tv_progress2);
        rvInvasions=view.findViewById(R.id.rv_invasions);
        rvInvasions.setLayoutManager(new LinearLayoutManager(rvInvasions.getContext(),
                LinearLayoutManager.VERTICAL,false));
        loadInvasions();
    }

    class InvasionsRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view=LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.item_invations,viewGroup,false);
            return new InvasionsItemView(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
            if(viewHolder instanceof InvasionsItemView){
                InvasionsItemView iiv=(InvasionsItemView)viewHolder;
                String node=invasionsBeans.get(i).node;
                String defendFaction=invasionsBeans.get(i).defendFaction;
                String defendReward=invasionsBeans.get(i).defendReward;
//                String defendMission=invasionsBeans.get(i).
                int progress=Math.round((float) invasionsBeans.get(i).progress);
                Log.i(TAG,"progress="+progress+","+invasionsBeans.get(i).progress);
                String attackingFaction=invasionsBeans.get(i).attackingFaction;
                String attackingReward=invasionsBeans.get(i).attackingReward;
//                String attackingMission
                iiv.tvNode.setText(node);
                iiv.tvAttackingFaction.setText(attackingFaction);
                iiv.tvAttackingReward.setText(attackingReward);
                iiv.pbProgress.setProgress(progress);
                iiv.tvDefendFaction.setText(defendFaction);
                iiv.tvDefendReward.setText(defendReward);
            }
        }

        @Override
        public int getItemCount() {
            return invasionsBeans.size();
        }

        class InvasionsItemView extends RecyclerView.ViewHolder{

            TextView tvNode;
            TextView tvDefendFaction;
            TextView tvDefendReward;
            TextView tvDefendMission;
            TextView tvAttackingFaction;
            TextView tvAttackingReward;
            TextView tvAttackingMission;
            ProgressBar pbProgress;
            public InvasionsItemView(@NonNull View itemView) {
                super(itemView);
                tvNode=itemView.findViewById(R.id.tv_node);
                tvAttackingFaction=itemView.findViewById(R.id.tv_attacking_faction);
                tvAttackingReward=itemView.findViewById(R.id.tv_attacking_reward);
                tvAttackingMission=itemView.findViewById(R.id.tv_attacking_mission);
                pbProgress=itemView.findViewById(R.id.pb_invasions);
                tvDefendFaction=itemView.findViewById(R.id.tv_defend_faction);
                tvDefendReward=itemView.findViewById(R.id.tv_defend_reward);
                tvDefendMission=itemView.findViewById(R.id.tv_defend_mission);
            }
        }
    }

    private void loadInvasions(){
        disposeHttpRequest();
        getHoldingActivity().progressDialog.show(getHoldingActivity(),getString(R.string.loading));
        Retrofit retrofit=Retrofit2Utils.getInstance().getRetrofit(null);
        loadInvasionsDisposabale=retrofit.create(Retrofit2Interface.class)
                .getInvasions()
                .subscribeOn(Schedulers.io())
                .concatMap(new Function<List<InvasionsBean>, ObservableSource<List<String>>>() {
                    @Override
                    public ObservableSource<List<String>> apply(List<InvasionsBean> beans) throws Exception {
                        List<List<String>>strsList=new ArrayList<>();
                        for (InvasionsBean bean:beans){
                            if(!bean.isCompleted()){
                                List<String>strs=new ArrayList<>();
                                strs.add(bean.getNode());
                                strs.add(Math.round((float) bean.getCompletion())+"");
                                strs.add(bean.getDefendingFaction());
                                strs.add(bean.getDefenderReward().getAsString());
                                strs.add(bean.getAttackingFaction());
                                strs.add(bean.getAttackerReward().getAsString());
                                strsList.add(strs);
                            }
                        }
                        return Observable.fromIterable(strsList);
                    }
                })
                .concatMap(new Function<List<String>, ObservableSource<List<String>>>() {
                    @Override
                    public ObservableSource<List<String>> apply(List<String> ss) throws Exception {
                        return TranslateDataBaseUtils.translateList(ss);
                    }
                })
                .map(new Function<List<String>, simpleInvasionBean>() {
                    @Override
                    public simpleInvasionBean apply(List<String> strings) throws Exception {
                        simpleInvasionBean sib=new simpleInvasionBean();
                        sib.node=strings.get(0);
                        sib.progress=Integer.valueOf(strings.get(1));
                        sib.defendFaction=strings.get(2);
                        sib.defendReward=strings.get(3);
                        sib.attackingFaction=strings.get(4);
                        sib.attackingReward=strings.get(5);
                        return sib;
                    }
                }).toList().toObservable()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<simpleInvasionBean>>() {
                    @Override
                    public void accept(List<simpleInvasionBean> sibs) throws Exception {
                        invasionsBeans=sibs;
                        rvInvasions.setAdapter(new InvasionsRecyclerViewAdapter());
                        getHoldingActivity().progressDialog.dismiss();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        getHoldingActivity().progressDialog.dismiss();
                        Retrofit2Utils.processError(throwable);
                    }
                });
        loadConstructionProgressDisposabale=retrofit.create(Retrofit2Interface.class)
                .getConstructionProgress()
                .subscribeOn(Schedulers.io())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ConstructionProgressBean>() {
                    @Override
                    public void accept(ConstructionProgressBean bean) throws Exception {
                        String s1="巨人战舰\n"+bean.getFomorianProgress()+"%";
                        String s2="利刃豺狼\n"+bean.getRazorbackProgress()+"%";
                        tvProgress1.setText(s1);
                        tvProgress2.setText(s2);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Retrofit2Utils.processError(throwable);
                    }
                });
    }

    private void disposeHttpRequest(){
        if(loadInvasionsDisposabale!=null){
            loadInvasionsDisposabale.dispose();
            loadInvasionsDisposabale=null;
        }
        if(loadConstructionProgressDisposabale!=null){
            loadConstructionProgressDisposabale.dispose();
            loadConstructionProgressDisposabale=null;
        }
    }
}
