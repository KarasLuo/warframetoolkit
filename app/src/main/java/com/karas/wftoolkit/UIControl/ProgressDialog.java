package com.karas.wftoolkit.UIControl;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.karas.wftoolkit.AppActivity;
import com.karas.wftoolkit.R;

import java.text.MessageFormat;

/**
 * POAVO
 * Created by Hongliang Luo on 2018/9/12.
 **/
public class ProgressDialog{
    final static private String TAG="ProgressDialog";
   // private static Context context;
    private static AlertDialog dialog=null;
    //private int dialogType;

    public  void dismiss(){
        if(dialog!=null){
            Log.i(TAG,"dismiss");
            dialog.dismiss();
            dialog=null;
        }
    }

    public boolean isShowing(){
        if(dialog!=null) {
//            Log.i(TAG,"isShowing="+dialog.isShowing());
            return dialog.isShowing();
        }
        else return false;
    }

    public void show(AppActivity activity, String msg){
       // this.context=context;
        dialog=new ProgressDialogWithMsg(activity,msg);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    public void show(AppActivity activity,String title,int percent){
        dialog=new ProgressDialogWithPercent(activity,title,percent);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    public void setPercent(int percent){
        if(dialog!=null){
            try{
                ProgressDialogWithPercent dialogWithPercent=
                        (ProgressDialogWithPercent)dialog;
                dialogWithPercent.setProgress(percent);
            }catch (Exception e){
                Log.e(TAG,"setPercent e="+e.toString());
            }
        }
    }

    //带有提示信息的进度框
    private  class ProgressDialogWithMsg extends AppDialog{
        String msg;
        TextView tvMsg;
        protected ProgressDialogWithMsg(@NonNull AppActivity activity,
                                        String msg) {
            super(activity);
            this.msg=msg;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.dialog_progress_with_msg);
            tvMsg=findViewById(R.id.tv_progress_dialog_msg);
            if (tvMsg != null) {
                tvMsg.setText(msg);
            }
        }
    }

    //带有百分比的进度框
    private  class ProgressDialogWithPercent extends AppDialog{
        String title;
        int percent;
        TextView tvPercent;
        ProgressBar progressBar;
        protected ProgressDialogWithPercent(@NonNull AppActivity activity,
                                        String title,
                                        int percent) {
            super(activity);
            this.title=title;
            this.percent=percent;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.dialog_progress_with_percent);
            //设置标题
            TextView tvTitle=findViewById(R.id.tv_progress_dialog_title);
            if (tvTitle != null) {
                tvTitle.setText(title);
            }
            //设置0%
            tvPercent =findViewById(R.id.tv_progress_dialog_msg);
            if (tvPercent != null) {
                tvPercent.setText(MessageFormat.format("{0}%", percent));
            }
            progressBar=findViewById(R.id.progress_bar);
            if (progressBar != null) {
                progressBar.setProgress(0);
            }
        }

        protected void setProgress(int percent){
            if (tvPercent != null&&percent>=0&&percent<=100) {
                tvPercent.setText(MessageFormat.format("{0}%", percent));
                progressBar.setProgress(percent);
            }
        }
    }
}
