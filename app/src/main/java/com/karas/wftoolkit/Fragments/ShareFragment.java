package com.karas.wftoolkit.Fragments;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.karas.wftoolkit.AppFragment;
import com.karas.wftoolkit.QqConnect.MsgConvertUtils;
import com.karas.wftoolkit.CommonUtils.ToastUtils;
import com.karas.wftoolkit.QqConnect.TencentUtils;
import com.karas.wftoolkit.R;
import com.karas.wftoolkit.Retrofit2.Bean.PcBean;
import com.karas.wftoolkit.Retrofit2.Retrofit2Interface;
import com.karas.wftoolkit.Retrofit2.Retrofit2Utils;
import com.karas.wftoolkit.UIControl.OnMultiClickListener;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class ShareFragment extends AppFragment {
    final static public String TAG="分享至QQ";
    private Disposable loadPcBeanDisposable;
    private ImageView ivIcon;
    private Bitmap shareBitmap;
    private String filePath;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_share;
    }

    @Override
    public void onDestroyView() {
        if(loadPcBeanDisposable!=null){
            loadPcBeanDisposable.dispose();
            loadPcBeanDisposable=null;
        }
        if(shareBitmap!=null){
            shareBitmap.recycle();
            shareBitmap=null;
        }
        super.onDestroyView();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected String getTAG() {
        return TAG;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        ivIcon=view.findViewById(R.id.iv_icon);
        Button btnShare=view.findViewById(R.id.btn_share_qq);
        btnShare.setOnClickListener(new OnMultiClickListener() {
            @Override
            public void onMultiClick(View view) {
                //分享
                TencentUtils.getInstance().share(getHoldingActivity(),filePath, new IUiListener() {
                    @Override
                    public void onComplete(Object o) {
                        Log.i(TAG,"share2qq="+o.toString());
                        removeFragment();
                    }

                    @Override
                    public void onError(UiError uiError) {
                        ToastUtils.showShortToast("分享出现异常！");
                        Log.e(TAG,"errorCode="+uiError.errorCode);
                        Log.e(TAG,"errorMessage="+uiError.errorMessage);
                        Log.e(TAG,"errorDetail="+uiError.errorDetail);
                    }

                    @Override
                    public void onCancel() {
                        ToastUtils.showShortToast("分享已取消！");
                        removeFragment();
                    }
                });
            }
        });
        loadPcBean();
    }


    private void loadPcBean(){
        if(loadPcBeanDisposable!=null){
            loadPcBeanDisposable.dispose();
            loadPcBeanDisposable=null;
        }
        Retrofit retrofit=Retrofit2Utils.getInstance().getRetrofit(null);
        loadPcBeanDisposable=retrofit.create(Retrofit2Interface.class)
                .getPC()
                .subscribeOn(Schedulers.newThread())
                .map(new Function<PcBean, String>() {
                    @Override
                    public String apply(PcBean pcBean) throws Exception {
                        return MsgConvertUtils.pcBean2msg(pcBean);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        shareBitmap=MsgConvertUtils.str2bitmap(s);
                        ivIcon.setImageBitmap(shareBitmap);
                        filePath=MsgConvertUtils.saveBitmap2png(shareBitmap);
                        Log.i(TAG,"file path="+filePath);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Retrofit2Utils.processError(throwable);
                    }
                });
    }
}
