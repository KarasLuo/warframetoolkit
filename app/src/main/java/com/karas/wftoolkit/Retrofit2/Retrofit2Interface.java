package com.karas.wftoolkit.Retrofit2;

import com.karas.wftoolkit.Retrofit2.Bean.CetusCycleBean;
import com.karas.wftoolkit.Retrofit2.Bean.NightWaveBean;
import com.karas.wftoolkit.Retrofit2.Bean.ConstructionProgressBean;
import com.karas.wftoolkit.Retrofit2.Bean.EarthCycleBean;
import com.karas.wftoolkit.Retrofit2.Bean.EventsBean;
import com.karas.wftoolkit.Retrofit2.Bean.FissuresBean;
import com.karas.wftoolkit.Retrofit2.Bean.InvasionsBean;
import com.karas.wftoolkit.Retrofit2.Bean.PcBean;
import com.karas.wftoolkit.Retrofit2.Bean.SortieBean;
import com.karas.wftoolkit.Retrofit2.Bean.SyndicateMissionsBean;
import com.karas.wftoolkit.Retrofit2.Bean.VallisCycleBean;
import com.karas.wftoolkit.Retrofit2.Bean.VoidTraderBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;


/**
 * Created by Hongliang Luo on 2018/8/10.
 **/
public interface Retrofit2Interface {

    //获取pc所有状态
    @GET("pc")
    Observable<PcBean> getPC();

    //获取服务器时间戳 such as"2019-05-31T01:59:09.000Z"
    @GET("pc/timestamp")
    Observable<String> getTimestamp();

    //获取希图斯昼夜循环
    @GET("pc/cetusCycle")
    Observable<CetusCycleBean> getCetusCycle();

    //获取地球昼夜循环
    @GET("pc/earthCycle")
    Observable<EarthCycleBean> getEarthCycle();

    //获取奥布山谷冷暖循环
    @GET("pc/vallisCycle")
    Observable<VallisCycleBean> getVallisCycle();

    //虚空商人
    @GET("pc/voidTrader")
    Observable<VoidTraderBean> getVoidTrader();

    //获取巨人战舰和利刃豺狼建造进度
    @GET("pc/constructionProgress")
    Observable<ConstructionProgressBean> getConstructionProgress();

    //派系入侵
    @GET("pc/invasions")
    Observable<List<InvasionsBean>> getInvasions();

    //虚空裂隙任务
    @GET("pc/fissures")
    Observable<List<FissuresBean>> getFissures();

    //集团任务
    @GET("pc/syndicateMissions")
    Observable<List<SyndicateMissionsBean>> getSyndicateMissions();

    //突击任务
    @GET("pc/sortie")
    Observable<SortieBean> getSortie();

    //活动事件
    @GET("pc/events")
    Observable<List<EventsBean>> getEvents();

    //每日每周挑战
    @GET("pc/nightwave")
    Observable<NightWaveBean> getNightWave();
}
