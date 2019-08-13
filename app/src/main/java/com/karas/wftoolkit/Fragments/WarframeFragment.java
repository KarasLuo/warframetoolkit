package com.karas.wftoolkit.Fragments;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.karas.wftoolkit.AppFragment;
import com.karas.wftoolkit.DBFlow.TranslateDataBaseUtils;
import com.karas.wftoolkit.DBFlow.TranslateTable;
import com.karas.wftoolkit.DBFlow.TranslateTable_Table;
import com.karas.wftoolkit.R;
import com.karas.wftoolkit.UIControl.OnMultiClickListener;
import com.raizlabs.android.dbflow.rx2.language.RXSQLite;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.database.transaction.QueryTransaction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import io.reactivex.Maybe;
import io.reactivex.MaybeObserver;
import io.reactivex.MaybeSource;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class WarframeFragment extends AppFragment {
    final static public String TAG="WF实时信息";
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_warframe;
    }

    @SuppressLint("CheckResult")
    @Override
    protected void initData() {
//        String enstr="Cetus chine warm mars";
//        TranslateDataBaseUtils.translateSingle(enstr)
//                .subscribeOn(Schedulers.newThread())
//                .observeOn(AndroidSchedulers.mainThread())
//        .subscribe(new Consumer<String>() {
//            @Override
//            public void accept(String s) throws Exception {
//                Log.e(TAG,"observer:"+s);
//            }
//        });

//        List<String>strings=new ArrayList<>();
//        strings.add("day");
//        strings.add("warm");
//        strings.add("corpus");
//        strings.add("to");
//        TranslateDataBaseUtils.translateList(strings)
//                .subscribeOn(Schedulers.newThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<List<String>>() {
//                    @Override
//                    public void accept(List<String> strings) throws Exception {
//                        Log.e(TAG,"list search:"+ Arrays.toString(strings.toArray()));
//                    }
//                });
    }

    @Override
    protected String getTAG() {
        return TAG;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        TextView tvTime=view.findViewById(R.id.tv_world_time);
        tvTime.setOnClickListener(new OnMultiClickListener() {
            @Override
            public void onMultiClick(View view) {
                addFragment(new WorldTimeFragment());
            }
        });
        TextView tvInvasions=view.findViewById(R.id.tv_invasions);
        tvInvasions.setOnClickListener(new OnMultiClickListener() {
            @Override
            public void onMultiClick(View view) {
                addFragment(new InvasionsFragment());
            }
        });
        TextView tvFissures=view.findViewById(R.id.tv_fissures);
        tvFissures.setOnClickListener(new OnMultiClickListener() {
            @Override
            public void onMultiClick(View view) {
                addFragment(new FissuresFragment());
            }
        });
        TextView tvSortie=view.findViewById(R.id.tv_sortie);
        tvSortie.setOnClickListener(new OnMultiClickListener() {
            @Override
            public void onMultiClick(View view) {
                addFragment(new SortieFragment());
            }
        });
        TextView tvSyndicateMissions=view.findViewById(R.id.tv_syndicate_missions);
        tvSyndicateMissions.setOnClickListener(new OnMultiClickListener() {
            @Override
            public void onMultiClick(View view) {
                addFragment(new SyndicateMissionsFragment());
            }
        });
        TextView tvEvents=view.findViewById(R.id.tv_events);
        tvEvents.setOnClickListener(new OnMultiClickListener() {
            @Override
            public void onMultiClick(View view) {
                addFragment(new EventsFragment());
            }
        });
        TextView tvNightWave=view.findViewById(R.id.tv_night_wave);
        tvNightWave.setOnClickListener(new OnMultiClickListener() {
            @Override
            public void onMultiClick(View view) {
                addFragment(new NightWaveFragment());
            }
        });

    }
}
