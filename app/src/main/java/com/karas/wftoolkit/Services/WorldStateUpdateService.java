package com.karas.wftoolkit.Services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.karas.wftoolkit.CommonUtils.SharedPreferencesUtils;
import com.karas.wftoolkit.Mail.MailUtils;
import com.karas.wftoolkit.Retrofit2.Bean.PcBean;
import com.karas.wftoolkit.Retrofit2.Retrofit2Interface;
import com.karas.wftoolkit.Retrofit2.Retrofit2Utils;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

import static com.karas.wftoolkit.CommonUtils.SharedPreferencesUtils.KEY_USER_NAME;

public class WorldStateUpdateService extends Service {
    private final static String TAG="WorldStateUpdateService";
    private Disposable loadDisposable;

    public WorldStateUpdateService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG,"onBind");
      return null;
    }

    @Override
    public void onDestroy() {
        Log.i(TAG,"onDestroy");
        disposeHttpRequest();
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG,"onStartCommand");
        loadWorldState();
        return START_STICKY;
    }

    private void disposeHttpRequest(){
        if(loadDisposable!=null){
            loadDisposable.dispose();
            loadDisposable=null;
        }
    }

    private void loadWorldState(){
        Log.i(TAG,"loadWorldState");
        disposeHttpRequest();
        loadDisposable=Observable.interval(600,TimeUnit.SECONDS)
                .switchMap(new Function<Long, ObservableSource<PcBean>>() {
                    @Override
                    public ObservableSource<PcBean> apply(Long aLong) throws Exception {
                        Retrofit retrofit=Retrofit2Utils.getInstance().getRetrofit(null);
                        return retrofit.create(Retrofit2Interface.class)
                                        .getPC();
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.newThread())
                .subscribe(new Consumer<PcBean>() {
                    @Override
                    public void accept(PcBean pcBean) throws Exception {
                        processPcBean(pcBean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(TAG,"load error");
                        throwable.printStackTrace();
                        loadWorldState();
                    }
                });

    }

    private void processPcBean(PcBean pcBean){
        Log.i(TAG,"processPcBean");
        if(pcBean==null){
            Log.e(TAG,"pcBean is null");
            return;
        }
        //夜灵三傻
        PcBean.CetusCycleBean cetusCycleBean=pcBean.getCetusCycle();
        if(cetusCycleBean!=null){
            String cetusState=cetusCycleBean.getState();
            String cetusTimeLeft=cetusCycleBean.getTimeLeft();
            int timeInteger=timeToInt(cetusTimeLeft);
            Log.i(TAG,"cetusTimeLeft="+cetusCycleBean.getShortString()+",timeInteger="+timeInteger);
            if(cetusState.equals("day")&&timeInteger>0&&timeInteger<=1000
                    &&SharedPreferencesUtils.getInstance()
                    .get(SharedPreferencesUtils.KEY_SETTING_AUTO_NOTIFY_3F,true)){//触发
                sendEmail("夜灵三傻","夜灵平野马上就要天黑了，请做好狩猎夜灵三傻的准备！");
                Log.e(TAG,"********Lotus: Tenno, 3 Fools will walk from underground after 5m !********");
            }
        }
        //警报
        List<PcBean.AlertsBean> alertList=pcBean.getAlerts();
        if(alertList!=null&&alertList.size()!=0){
            StringBuilder alertMsg= new StringBuilder("********限时警报*****\n");
            for (PcBean.AlertsBean bean:alertList){
                int timeInteger=timeToInt(bean.getStartString());
                Log.i(TAG,"限时警报:"+"timeInteger="+timeInteger);
                if(timeInteger>0&&timeInteger<=1000
                        &&SharedPreferencesUtils.getInstance()
                        .get(SharedPreferencesUtils.KEY_SETTING_AUTO_NOTIFY_ALERT,true)){//触发
                    alertMsg.append(bean.getMission().getDescription()).append("\n")
                            .append(bean.getMission().getNode()).append("  ")
                            .append(bean.getMission().getType()).append("\n");
                    for (String reward:bean.getMission().getReward().getItems()){
                        alertMsg.append("   ").append(reward).append("\n");
                    }
                    alertMsg.append(bean.getEta()).append("\n");
                    sendEmail("限时警报","新的限时警报任务\n\n"+alertMsg.toString());
                    Log.e(TAG,""+alertMsg);
                }
            }
        }
        //虚空商人
        PcBean.VoidTraderBean voidTraderBean=pcBean.getVoidTrader();
        int timeInt=timeToInt(voidTraderBean.getStartString());
        Log.i(TAG,"voidTraderStartString="+voidTraderBean.getStartString()+",timeInt="+timeInt);
        if(timeInt>0&&timeInt<=1000
                &&SharedPreferencesUtils.getInstance()
                .get(SharedPreferencesUtils.KEY_SETTING_AUTO_NOTIFY_VOID_TRADER,true)){//触发
            StringBuilder voidTraderMsg=new StringBuilder("********虚空商人*****\n");
            voidTraderMsg.append(voidTraderBean.getCharacter()).append("\n");
            voidTraderMsg.append(voidTraderBean.getLocation()).append("\n");
            if(voidTraderBean.isActive()){
                voidTraderMsg.append("离开时间：").append(voidTraderBean.getEndString());
            }else {
                voidTraderMsg.append("到达时间：").append(voidTraderBean.getStartString());
            }
            sendEmail("刺杀奸商","虚空奸商回来啦，大伙儿一起来盘他！\n\n"+voidTraderMsg.toString());
            Log.e(TAG,""+voidTraderMsg);
        }
        //活动事件 与限时警报公用一个设置选项
        List<PcBean.EventsBean> eventsBean=pcBean.getEvents();
        if(eventsBean!=null&&eventsBean.size()>0){
            StringBuilder eventsMsg=new StringBuilder("********限时活动*****\n");
            for (PcBean.EventsBean event:eventsBean){
                int timeInteger=timeToInt(event.getStartString());
                Log.i(TAG,"限时活动:"+"timeInteger="+timeInteger);
                if(timeInteger>0&&timeInteger<=1000
                        &&SharedPreferencesUtils.getInstance()
                        .get(SharedPreferencesUtils.KEY_SETTING_AUTO_NOTIFY_ALERT,true)){
                    eventsMsg.append("活动名称：").append(event.getDescription()).append("\n");
                    eventsMsg.append("到期时间：").append(event.getExpiry().split("T")[0]);
                    eventsMsg.append("\n").append(event.getAsString()).append("\n");
                    sendEmail("限时活动","新的限时活动，冲冲冲！\n\n"+eventsMsg.toString());
                    Log.e(TAG,""+eventsMsg);
                }
            }
        }
    }

    private void sendEmail(final String title, final String msg){
        String userEmail=SharedPreferencesUtils.getInstance()
                .get(KEY_USER_NAME,"");
        String password=SharedPreferencesUtils.getInstance()
                .get(SharedPreferencesUtils.KEY_PASSWORD,"");
        List<String> recipientList=SharedPreferencesUtils.getInstance()
                .get(SharedPreferencesUtils.KEY_EMAIL_RECIPIENTS,"###","");
        Log.i(TAG,"recipientList="+ Arrays.toString(recipientList.toArray()));
        Log.i(TAG,"userEmail="+userEmail+",password="+password);
        try {
            MailUtils.getInstance().sendEmail(userEmail,password,recipientList,
                    title+"----来自Warrame实时信息自动提醒", msg);
            Log.i(TAG,"邮件已发送");
        } catch (Exception e) {
            Log.e(TAG,"邮件发送异常");
            e.printStackTrace();
        }
    }

    /**
     * 时间转换为整型数
     * d-h-m-s
     * @param time
     * @return
     */
    private static int timeToInt(String time){
        boolean isNeg=time.contains("-");
        if(isNeg){
            time=time.replace("-","");
        }
        String[] strs=time.split(" ");
        int d=0;
        int h=0;
        int m=0;
        int s=0;
        switch (strs.length){
            case 4:
                d=Integer.valueOf(strs[0].replace("d",""));
                h=Integer.valueOf(strs[1].replace("h",""));
                m=Integer.valueOf(strs[2].replace("m",""));
                s=Integer.valueOf(strs[3].replace("s",""));
                break;
            case 3:
                h=Integer.valueOf(strs[0].replace("h",""));
                m=Integer.valueOf(strs[1].replace("m",""));
                s=Integer.valueOf(strs[2].replace("s",""));
                break;
            case 2:
                m=Integer.valueOf(strs[0].replace("m",""));
                s=Integer.valueOf(strs[1].replace("s",""));
                break;
            case 1:
                s=Integer.valueOf(strs[0].replace("s",""));
                break;
            default:
                break;
        }
        int number=d*1000000+h*10000+m*100+s;
        return isNeg?-1*number:number;
    }

}
