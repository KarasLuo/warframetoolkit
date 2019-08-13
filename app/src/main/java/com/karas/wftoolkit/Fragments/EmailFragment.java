package com.karas.wftoolkit.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.karas.wftoolkit.AppFragment;
import com.karas.wftoolkit.CommonUtils.RxBus;
import com.karas.wftoolkit.CommonUtils.SharedPreferencesUtils;
import com.karas.wftoolkit.CommonUtils.ToastUtils;
import com.karas.wftoolkit.Mail.MailUtils;
import com.karas.wftoolkit.R;
import com.karas.wftoolkit.UIControl.OnMultiClickListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

import static com.karas.wftoolkit.CommonUtils.SharedPreferencesUtils.KEY_EMAIL_RECIPIENTS;
import static com.karas.wftoolkit.CommonUtils.SharedPreferencesUtils.KEY_PASSWORD;
import static com.karas.wftoolkit.CommonUtils.SharedPreferencesUtils.KEY_USER_NAME;

/**
 * Created by Hongliang Luo on 2019/8/7.
 **/
public class EmailFragment extends AppFragment {
    final static public String TAG="邮件设置";
    final static public String REX_MAIL_ADDRESS= "^[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]@[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z]$";

    private String userEmail;
    private String password;
    private List<String>recipientList=new ArrayList<>();
    private RecyclerView rvRecipient;
    private RvRecipientAdapter recipientAdapter;
    private Disposable verifyEmailDisposable;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_email;
    }

    @Override
    public void onDestroyView() {
        if(verifyEmailDisposable!=null){
            verifyEmailDisposable.dispose();
            verifyEmailDisposable=null;
        }
        super.onDestroyView();
    }

    @Override
    protected void initData() {
        userEmail=SharedPreferencesUtils.getInstance()
                .get(KEY_USER_NAME,"");
        password=SharedPreferencesUtils.getInstance()
                .get(SharedPreferencesUtils.KEY_PASSWORD,"");
        recipientList=SharedPreferencesUtils.getInstance()
                .get(SharedPreferencesUtils.KEY_EMAIL_RECIPIENTS,"###","");
        Log.i(TAG,"recipientList="+ Arrays.toString(recipientList.toArray()));
        recipientAdapter=new RvRecipientAdapter();
    }

    @Override
    protected String getTAG() {
        return TAG;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        final TextInputLayout tilLoginMail=view.findViewById(R.id.til_email);
        tilLoginMail.getEditText().setText(userEmail);
        final TextInputLayout tilLoginPassword=view.findViewById(R.id.til_password);
        tilLoginPassword.getEditText().setText(password);
        rvRecipient=view.findViewById(R.id.rv_recipient);
        rvRecipient.setLayoutManager(new LinearLayoutManager(view.getContext(),
                LinearLayoutManager.VERTICAL,false));
        rvRecipient.addItemDecoration(new DividerItemDecoration(rvRecipient.getContext(),
                DividerItemDecoration.VERTICAL));
        rvRecipient.setAdapter(recipientAdapter);
        recipientAdapter.notifyDataSetChanged();
        final TextInputEditText tietMail=view.findViewById(R.id.tiet_recipient);
        ImageView ivAdd=view.findViewById(R.id.iv_new_mail);
        ivAdd.setOnClickListener(new OnMultiClickListener() {
            @Override
            public void onMultiClick(View view) {
                if(tietMail.getText()==null){
                    return;
                }
                String newmail=tietMail.getText().toString();
                if(newmail.matches(REX_MAIL_ADDRESS)){
                    if(!recipientList.contains(newmail)){
                        recipientList.add(newmail);
                        //更新列表
                        recipientAdapter.notifyDataSetChanged();
                    }else {
                        ToastUtils.showShortToast("收件邮箱已存在！");
                    }
                }else {
                    ToastUtils.showShortToast("收件邮箱格式不正确！");
                }
                tietMail.setText("");
            }
        });
        Button btnSave=view.findViewById(R.id.btn_mail_sure);
        btnSave.setOnClickListener(new OnMultiClickListener() {
            @Override
            public void onMultiClick(View view) {
                final String email=tilLoginMail.getEditText().getText().toString();
                if(!email.matches(REX_MAIL_ADDRESS)){
                    ToastUtils.showShortToast("邮箱格式错误，请重新输入！");
                    return;
                }
                final String pw=tilLoginPassword.getEditText().getText().toString();
                if(pw.equals("")){
                    ToastUtils.showShortToast("请输入邮箱授权码！");
                    return;
                }
                if(verifyEmailDisposable!=null){
                    verifyEmailDisposable.dispose();
                    verifyEmailDisposable=null;
                }
                verifyEmailDisposable=Observable.just(email+"!!!!!"+pw)
                        .map(new Function<String, Boolean>() {
                            @Override
                            public Boolean apply(String s) throws Exception {
                                String[] account=s.split("!!!!!");
                                Log.i(TAG,"account="+ Arrays.toString(account));
                                SharedPreferencesUtils.getInstance().save(KEY_EMAIL_RECIPIENTS,recipientList,"###");
                                return MailUtils.getInstance().verifyAccount(account[0],account[1]);
                            }
                        })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<Boolean>() {
                            @Override
                            public void accept(Boolean aBoolean) throws Exception {
                                if(aBoolean){
                                    Log.i(TAG,"验证邮箱，保存信息");
                                    RxBus.getInstance().post(RxBus.getInstance().new BusEvent<>(RxBus.SET_NICKNAME,email));
                                    SharedPreferencesUtils.getInstance().save(KEY_USER_NAME,email);
                                    SharedPreferencesUtils.getInstance().save(KEY_PASSWORD,pw);
                                    removeFragment();
                                }else {
                                    Log.i(TAG,"邮箱验证失败");
                                    ToastUtils.showShortToast("邮箱验证失败,请确保授权码正确！");
                                }
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                Log.i(TAG,"邮箱验证失败");
                                throwable.printStackTrace();
                                ToastUtils.showShortToast("邮箱验证失败");
                            }
                        });
            }
        });
    }

    class RvRecipientAdapter extends RecyclerView.Adapter<RvRecipientAdapter.RecipientView>{

        @NonNull
        @Override
        public RecipientView onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view=LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.item_recipient,viewGroup,false);
            return new RecipientView(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecipientView view, int i) {
            final int index=i;
            Log.i(TAG,"index="+index);
            view.tvEmail.setText(recipientList.get(i));
            view.ivDelete.setOnClickListener(new OnMultiClickListener() {
                @Override
                public void onMultiClick(View view) {
                    recipientList.remove(index);
                    recipientAdapter.notifyDataSetChanged();
                }
            });
        }

        @Override
        public int getItemCount() {
            return recipientList.size();
        }

        class RecipientView extends RecyclerView.ViewHolder{

            TextView tvEmail;
            ImageView ivDelete;
            public RecipientView(@NonNull View itemView) {
                super(itemView);
                tvEmail=itemView.findViewById(R.id.tv_email);
                ivDelete=itemView.findViewById(R.id.iv_mail_delete);
            }
        }
    }
}
