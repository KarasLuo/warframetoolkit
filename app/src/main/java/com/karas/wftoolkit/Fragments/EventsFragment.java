package com.karas.wftoolkit.Fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
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
import com.karas.wftoolkit.Retrofit2.Bean.EventsBean;
import com.karas.wftoolkit.Retrofit2.Retrofit2Interface;
import com.karas.wftoolkit.Retrofit2.Retrofit2Utils;
import com.karas.wftoolkit.UIControl.ProgressDialog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class EventsFragment extends AppFragment {
    final static public String TAG="活动事件";

    private RecyclerView rvEvents;
    private Disposable loadEventsDisposable;
    class SimpleEventsBean{
        String expiry;
        String description;
        String asString;
    }
    private List<SimpleEventsBean>eventsBeans=new ArrayList<>();
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_events;
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
        rvEvents=view.findViewById(R.id.rv_events);
        rvEvents.setLayoutManager(new LinearLayoutManager(rvEvents.getContext(),
                LinearLayoutManager.VERTICAL,false));
        loadEvents();
    }

    private void loadEvents(){
        disposeHttpRequest();
        getHoldingActivity().progressDialog.show(getHoldingActivity(),getString(R.string.loading));
        final Retrofit retrofit=Retrofit2Utils.getInstance().getRetrofit(null);
        loadEventsDisposable=retrofit.create(Retrofit2Interface.class)
                .getEvents()
                .subscribeOn(Schedulers.io())
                .concatMap(new Function<List<EventsBean>, ObservableSource<List<String>>>() {
                    @Override
                    public ObservableSource<List<String>> apply(List<EventsBean> beans) throws Exception {
                        List<List<String>>strsList=new ArrayList<>();
                        for (EventsBean bean:beans){
                            if(!bean.isExpired()){
                                List<String>strs=new ArrayList<>();
                                strs.add(bean.getExpiry().split("\\.")[0].replace("T"," "));
                                strs.add(bean.getDescription());
                                strs.add(bean.getAsString());
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
                .map(new Function<List<String>, SimpleEventsBean>() {
                    @Override
                    public SimpleEventsBean apply(List<String> strings) throws Exception {
                        SimpleEventsBean seb=new SimpleEventsBean();
                        seb.expiry=strings.get(0);
                        seb.description=strings.get(1);
                        seb.asString=strings.get(2);
                        return seb;
                    }
                }).toList().toObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<SimpleEventsBean>>() {
                    @Override
                    public void accept(List<SimpleEventsBean> beans) throws Exception {
                        rvEvents.setAdapter(new EventRvAdapter(beans));
                        getHoldingActivity().progressDialog.dismiss();
                        if(beans.size()==0){
                            getHoldingActivity().showToast("暂无活动！");
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Retrofit2Utils.processError(throwable);
                        getHoldingActivity().progressDialog.dismiss();
                    }
                });
    }

    private void disposeHttpRequest(){
        if(loadEventsDisposable!=null){
            loadEventsDisposable.dispose();
            loadEventsDisposable=null;
        }
    }

    class EventRvAdapter extends RecyclerView.Adapter<EventRvAdapter.EventView>{
        List<SimpleEventsBean> list;

        EventRvAdapter(@NonNull List<SimpleEventsBean> list){
            this.list=list;
        }

        @NonNull
        @Override
        public EventView onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view=LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.item_events,viewGroup,false);
            return new EventView(view);
        }

        @Override
        public void onBindViewHolder(@NonNull EventView eventView, int i) {
            eventView.tvDescription.setText(list.get(i).description);
            eventView.tvAsString.setText(list.get(i).asString);
            eventView.tvExpiry.setText(list.get(i).expiry);
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class EventView extends RecyclerView.ViewHolder{
            TextView tvDescription;
            TextView tvExpiry;
            TextView tvAsString;
            public EventView(@NonNull View itemView) {
                super(itemView);
                tvAsString=itemView.findViewById(R.id.tv_event_string);
                tvExpiry=itemView.findViewById(R.id.tv_expiry);
                tvDescription=itemView.findViewById(R.id.tv_description);
            }
        }
    }
}
