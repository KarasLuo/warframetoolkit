package com.karas.wftoolkit.QqConnect;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Environment;
import android.util.Log;

import com.karas.wftoolkit.MyApplication;
import com.karas.wftoolkit.R;
import com.karas.wftoolkit.Retrofit2.Bean.PcBean;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Hongliang Luo on 2019/8/6.
 **/
public class MsgConvertUtils {
    private static final String TAG="MsgConvertUtils";

    public static String pcBean2msg(PcBean pcBean){
        StringBuilder msg=new StringBuilder("***Warframe实时信息***");
        String cetusTime="\n**希图斯时间**\n";
        cetusTime+=pcBean.getCetusCycle().getShortString();
        msg.append("\n").append(cetusTime);
        String voidTrader="\n**虚空商人**\n节点：";
        voidTrader+=pcBean.getVoidTrader().getLocation();
        voidTrader+="\n";
        voidTrader+=pcBean.getVoidTrader().isActive()
                ?pcBean.getVoidTrader().getEndString()+"离开"
                :pcBean.getVoidTrader().getStartString()+"到来";
        msg.append("\n").append(voidTrader);
        String dailyDeals="\n**每日优惠**";
        for (PcBean.DailyDealsBean dealsBean:pcBean.getDailyDeals()){
            dailyDeals+="\n*物品："+dealsBean.getItem()+"\t折扣："+dealsBean.getDiscount()+"%";
            dailyDeals+="\n 售价："+dealsBean.getSalePrice()+"P\t";
            dailyDeals+="原价："+dealsBean.getOriginalPrice()+"P\n";
            dailyDeals+=" 已售："+dealsBean.getSold()+"\t总量："+dealsBean.getTotal();
        }
        msg.append("\n").append(dailyDeals);
        String invasion="\n**派系入侵**";
        for (PcBean.InvasionsBean ib:pcBean.getInvasions()){
            if(!ib.isCompleted()){
                invasion+="\n*节点:";
                invasion+=ib.getNode();
                invasion+="\t进度：";
                invasion+=(int)ib.getCompletion()+"%";
                invasion+="\n防守：";
                invasion+=ib.getDefendingFaction();
                invasion+="\t奖励：";
                invasion+=ib.getDefenderReward().getAsString();
                invasion+="\n入侵：";
                invasion+=ib.getAttackingFaction();
                invasion+="\t奖励：";
                invasion+=ib.getAttackerReward().getAsString();
            }
        }
        msg.append("\n").append(invasion);
        String sortie="\n**每日突击**";
        sortie+="\n";
        sortie+=pcBean.getSortie().getBoss();
        sortie+="\t";
        sortie+=pcBean.getSortie().getFaction();
        for (PcBean.SortieBean.VariantsBean vb:pcBean.getSortie().getVariants()){
            sortie+="\n*";
            sortie+=vb.getNode();
            sortie+="\t";
            sortie+=vb.getMissionType();
            sortie+="\n";
            sortie+=vb.getModifier();
        }
        msg.append("\n").append(sortie);
        String events="\n**活动事件**";
        for (PcBean.EventsBean eb:pcBean.getEvents()){
            events+="\n*";
            events+=eb.getDescription();
            events+="\t到期时间:";
            events+=eb.getExpiry().split("T")[0];
        }
        msg.append("\n").append(events);
        Log.w(TAG,"msg=\n"+msg.toString());
        return msg.toString();
    }

    public static Bitmap str2bitmap(String msg){
        Bitmap icon=BitmapFactory.decodeResource(MyApplication.getAppContext().getResources(),
                R.mipmap.wf_icon);
        int iconResize=100;
        String[] msgs=msg.split("\n");
        int maxCharCount=iconResize;
        for (String str:msgs){
            maxCharCount=Math.max(str.length(),maxCharCount);
        }
        Log.i(TAG,"文本行数="+msgs.length+",文本行最大个数="+maxCharCount);
        int textSize=25;
        int padding=10;
        int bmWidth=maxCharCount*textSize/4+padding;
        int bmHeight=msgs.length*(textSize+padding/2);
        Log.i(TAG,"位图宽度="+bmWidth+",位图高度="+bmHeight);
        Bitmap bitmap=Bitmap.createBitmap(bmWidth,bmHeight,Bitmap.Config.ARGB_8888);
        Canvas canvas=new Canvas(bitmap);
        Paint txPaint=new Paint();
        txPaint.setColor(Color.BLACK);
        txPaint.setTextSize(textSize);
        //绘制背景
        Paint bgPaint=new Paint();
        bgPaint.setColor(Color.WHITE);
        canvas.drawRect(new RectF(0,0,bmWidth,bmHeight),bgPaint);
        //绘制图标
        canvas.drawBitmap(icon,
                new Rect(0,0,icon.getWidth(),icon.getHeight()),
                new RectF(3*padding, 3*padding, iconResize+padding, iconResize+padding),
                new Paint());
        canvas.drawText(MyApplication.getAppString(R.string.app_name)+" By JosephKaras",
                iconResize+padding*6,(iconResize+3*padding)/2,txPaint);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd/ HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        canvas.drawText(""+sdf.format(date),
                iconResize+padding*10,(iconResize+10*padding)/2,txPaint);
        //绘制文本内容
        for (int i=0;i<msgs.length;i++){
            canvas.drawText(msgs[i],3*padding,iconResize+textSize*i+6*padding,txPaint);
        }
        canvas.save();
        canvas.restore();
        icon.recycle();
        return bitmap;
    }

    public static String saveBitmap2png(Bitmap bitmap){
        File file = new File(Environment.getExternalStorageDirectory().getPath() +
                "/warframeInfo.png");
        Log.i(TAG, Environment.getExternalStorageDirectory().getPath());
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 50, fos);
            return file.getPath();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
