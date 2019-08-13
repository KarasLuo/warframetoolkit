package com.karas.wftoolkit.UIControl;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.karas.wftoolkit.R;


/**
 * Created by Hongliang Luo on 2018/9/10.
 **/
public class TipDialog extends AlertDialog {
    final private static String TAG="TipDialog";
    private Context context;
    private String title;
    private String msg;
    private String textPositive;
    private String textNegative;
    private String textNeutral;
    private OnPositiveListener positiveListener;
    private OnNegativeListener negativeListener;
    private OnNeutralListener neutralListener;

    //三个按钮
    public TipDialog(@NonNull Context context,
                     String title,
                     String msg,
                     String textPositive,
                     String textNegative,
                     String textNeutral,
                     OnPositiveListener positiveListener,
                     OnNegativeListener negativeListener,
                     OnNeutralListener neutralListener) {
        super(context);
        this.context=context;
        this.title=title;
        this.msg=msg;
        this.textPositive=textPositive;
        this.textNegative=textNegative;
        this.textNeutral=textNeutral;
        this.positiveListener=positiveListener;
        this.negativeListener=negativeListener;
        this.neutralListener=neutralListener;
    }

    public TipDialog(@NonNull Context context,
                     String title,
                     String msg,
                     String textPositive,
                     String textNeutral,
                     OnPositiveListener positiveListener,
                     OnNeutralListener neutralListener) {
        super(context);
        this.context=context;
        this.title=title;
        this.msg=msg;
        this.textPositive=textPositive;
        this.textNegative="取消";
        this.textNeutral=textNeutral;
        this.positiveListener=positiveListener;
        this.negativeListener=null;
        this.neutralListener=neutralListener;
    }

    public TipDialog(@NonNull Context context,
                     String msg,
                     String textPositive,
                     String textNeutral,
                     OnPositiveListener positiveListener,
                     OnNeutralListener neutralListener) {
        super(context);
        this.context=context;
        this.title="提示";
        this.msg=msg;
        this.textPositive=textPositive;
        this.textNegative="取消";
        this.textNeutral=textNeutral;
        this.positiveListener=positiveListener;
        this.negativeListener=null;
        this.neutralListener=neutralListener;
    }

    //两个按钮
    public TipDialog(@NonNull Context context,
                     String title,
                     String msg,
                     String textPositive,
                     String textNegative,
                     OnPositiveListener positiveListener,
                     OnNegativeListener negativeListener) {
        super(context);
        this.context=context;
        this.title=title;
        this.msg=msg;
        this.textPositive=textPositive;
        this.textNegative=textNegative;
        this.textNeutral=null;
        this.positiveListener=positiveListener;
        this.negativeListener=negativeListener;
        this.neutralListener=null;
    }

    public TipDialog(@NonNull Context context,
                     String title,
                     String msg,
                     OnPositiveListener positiveListener,
                     OnNegativeListener negativeListener) {
        super(context);
        this.context=context;
        this.title=title;
        this.msg=msg;
        this.textPositive="确定";
        this.textNegative="取消";
        this.textNeutral=null;
        this.positiveListener=positiveListener;
        this.negativeListener=negativeListener;
        this.neutralListener=null;
    }

    public TipDialog(@NonNull Context context,
                     String title,
                     String msg,
                     OnPositiveListener positiveListener) {
        super(context);
        this.context=context;
        this.title=title;
        this.msg=msg;
        this.textPositive="确定";
        this.textNegative="取消";
        this.textNeutral=null;
        this.positiveListener=positiveListener;
        this.negativeListener=null;
        this.neutralListener=null;
    }

    public TipDialog(@NonNull Context context,
                     String msg,
                     OnPositiveListener positiveListener) {
        super(context);
        this.context=context;
        this.title="提示";
        this.msg=msg;
        this.textPositive="确定";
        this.textNegative="取消";
        this.textNeutral=null;
        this.positiveListener=positiveListener;
        this.negativeListener=null;
        this.neutralListener=null;
    }

    //一个按钮
    public TipDialog(@NonNull Context context,
                     String title,
                     String msg,
                     String textPositive,
                     OnPositiveListener positiveListener) {
        super(context);
        this.context=context;
        this.title=title;
        this.msg=msg;
        this.textPositive=textPositive;
        this.textNegative=null;
        this.textNeutral=null;
        this.positiveListener=positiveListener;
        this.negativeListener=null;
        this.neutralListener=null;
    }

    public TipDialog(@NonNull Context context,
                     String title,
                     String msg) {
        super(context);
        this.context=context;
        this.title=title;
        this.msg=msg;
        this.textPositive="确定";
        this.textNegative=null;
        this.textNeutral=null;
        this.positiveListener=null;
        this.negativeListener=null;
        this.neutralListener=null;
    }

    public TipDialog(@NonNull Context context,
                     String msg) {
        super(context);
        this.context=context;
        this.title="提示";
        this.msg=msg;
        this.textPositive="确定";
        this.textNegative=null;
        this.textNeutral=null;
        this.positiveListener=null;
        this.negativeListener=null;
        this.neutralListener=null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_tip);
        TextView tvTitle = findViewById(R.id.tip_title);
        if(tvTitle !=null&&title!=null){
            tvTitle.setText(title);
        }
        TextView tvMsg = findViewById(R.id.tip_content);
        if(tvMsg !=null&&msg!=null){
            tvMsg.setText(msg);
            tvMsg.setMovementMethod(ScrollingMovementMethod.getInstance());
        }
        Button btnPositive = findViewById(R.id.btn_tip_positive);
        if(btnPositive !=null&&textPositive!=null){
            btnPositive.setText(textPositive);
            btnPositive.setOnClickListener(new OnMultiClickListener() {
                @Override
                public void onMultiClick(View view) {
                    dismiss();
                    if(positiveListener!=null){
                        positiveListener.onPositive();
                    }
                }
            });
        }
        TextView btnNegative = findViewById(R.id.btn_tip_negative);
        if(btnNegative !=null&&textNegative!=null){
            btnNegative.setText(textNegative);
            btnNegative.setOnClickListener(new OnMultiClickListener() {
                @Override
                public void onMultiClick(View view) {
                    dismiss();
                    if(negativeListener!=null){
                        negativeListener.onNegative();
                    }
                }
            });
        }else {
            if (btnNegative != null) {
                btnNegative.setVisibility(View.INVISIBLE);
            }
        }
        TextView btnNeutral = findViewById(R.id.btn_tip_neutral);
        if(btnNeutral !=null&&textNeutral!=null){
            btnNeutral.setText(textNeutral);
            btnNeutral.setOnClickListener(new OnMultiClickListener() {
                @Override
                public void onMultiClick(View view) {
                    dismiss();
                    if(neutralListener!=null){
                        neutralListener.onNeutral();
                    }
                }
            });
        }else {
            if (btnNeutral != null) {
                btnNeutral.setVisibility(View.INVISIBLE);
            }
        }
        setCanceledOnTouchOutside(false);
        setCancelable(false);
    }

    public interface OnPositiveListener {
        void onPositive();
    }

    public interface OnNegativeListener{
        void onNegative();
    }

    public interface OnNeutralListener{
        void onNeutral();
    }
}
