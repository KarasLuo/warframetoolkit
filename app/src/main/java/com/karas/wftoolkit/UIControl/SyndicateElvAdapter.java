package com.karas.wftoolkit.UIControl;

import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.karas.wftoolkit.DBFlow.TranslateDataBaseUtils;
import com.karas.wftoolkit.R;
import com.karas.wftoolkit.Retrofit2.Bean.SyndicateMissionsBean;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Hongliang Luo on 2019/6/18.
 **/
public class SyndicateElvAdapter extends BaseExpandableListAdapter {
    List<SyndicateMissionsBean> list;
    public CompositeDisposable translateDisposable;

    public SyndicateElvAdapter(@NonNull List<SyndicateMissionsBean>list){
        this.list=list;
        translateDisposable=new CompositeDisposable();
    }

    @Override
    public int getGroupCount() {
        return list.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return list.get(groupPosition).getJobs().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return list.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return list.get(groupPosition).getJobs().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition,
                             boolean isExpanded,
                             View convertView,
                             ViewGroup parent) {
        ParentViewHolder viewHolder;
        if(convertView==null){
            convertView=LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_expandable_listview_parent,parent,false);
            viewHolder=new ParentViewHolder();
            viewHolder.tvSyndicate=convertView.findViewById(R.id.tv_syndicate);
            viewHolder.tvEta=convertView.findViewById(R.id.tv_eta);
            convertView.setTag(viewHolder);
        }else {
            viewHolder=(ParentViewHolder) convertView.getTag();
        }
        viewHolder.tvSyndicate.setText(list.get(groupPosition).getSyndicate());
        textViewTranslate(viewHolder.tvSyndicate,list.get(groupPosition).getSyndicate());//
        viewHolder.tvEta.setText(list.get(groupPosition).getEta());
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition,
                             int childPosition,
                             boolean isLastChild,
                             View convertView,
                             ViewGroup parent) {
        ChildViewHolder viewHolder;
        if(convertView==null){
            convertView=LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_expandable_listview_child,parent,false);
            viewHolder=new ChildViewHolder();
            viewHolder.tvType=convertView.findViewById(R.id.tv_type);
            viewHolder.tvLevel=convertView.findViewById(R.id.tv_enemy_level);
            viewHolder.tvRewards=convertView.findViewById(R.id.tv_rewards);
            convertView.setTag(viewHolder);
        }else {
            viewHolder=(ChildViewHolder) convertView.getTag();
        }
        SyndicateMissionsBean.JobsBean jobsBean=list.get(groupPosition)
                .getJobs().get(childPosition);
        viewHolder.tvType.setText(jobsBean.getType());
        textViewTranslate(viewHolder.tvType,jobsBean.getType());//
        String level="\n赏金"+(childPosition+1)+"\n敌人等级:"+jobsBean.getEnemyLevels().get(0)+"—"+jobsBean.getEnemyLevels().get(1);
        viewHolder.tvLevel.setText(level);
        viewHolder.tvRewards.setText("\n");
        for (String s:jobsBean.getRewardPool()){
            textViewTranslateWithAppend(viewHolder.tvRewards,s);
        }
//        viewHolder.tvRewards.setText(rewards);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    static class ParentViewHolder {
        TextView tvSyndicate;
        TextView tvEta;
    }

    static class ChildViewHolder {
        TextView tvType;
        TextView tvLevel;
        TextView tvRewards;
    }

    private void textViewTranslate(final TextView tv, String text){
        Disposable disposable=TranslateDataBaseUtils.translateSingle(text)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        tv.setText(s);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e("SyndicateElvAdapter","throwable:");
                        throwable.printStackTrace();
                    }
                });
        translateDisposable.add(disposable);
    }

    private void textViewTranslateWithAppend(final TextView tv, String text){
        Disposable disposable=TranslateDataBaseUtils.translateSingle(text)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        tv.append(s+"\n");
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e("SyndicateElvAdapter","throwable:");
                        throwable.printStackTrace();
                    }
                });
        translateDisposable.add(disposable);
    }
}
