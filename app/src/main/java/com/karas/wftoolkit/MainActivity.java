package com.karas.wftoolkit;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.karas.wftoolkit.CommonUtils.RxBus;
import com.karas.wftoolkit.CommonUtils.SharedPreferencesUtils;
import com.karas.wftoolkit.Fragments.EmailFragment;
import com.karas.wftoolkit.Fragments.SettingFragment;
import com.karas.wftoolkit.Fragments.ShareFragment;
import com.karas.wftoolkit.Fragments.WarframeFragment;
import com.karas.wftoolkit.Services.WorldStateUpdateService;
import com.karas.wftoolkit.UIControl.OnMultiClickListener;
import com.karas.wftoolkit.UIControl.TipDialog;
import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class MainActivity extends AppActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    static final private String TAG="MainActivity";

    private TextView toolbarTitle;
    private NavigationView navigationView;
    private LinearLayout llLogin;
    private ImageView ivHeadImage;
    private TextView tvUsername;
    private TextView tvUserQQ;
    private Disposable rxBusDisposable;
    @Override
    public void initData() {
        requestPermission();
    }

    @Override
    protected void onPause() {
        Log.i(TAG,"onPause");
        //取消事件总线的监听
        if(rxBusDisposable!=null){
            rxBusDisposable.dispose();
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        Log.i(TAG,"onResume");
        super.onResume();
        //监听总线事件
        rxBusDisposable= RxBus.getInstance().toObservable(RxBus.BusEvent.class)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<RxBus.BusEvent>() {
                    @Override
                    public void accept(RxBus.BusEvent busEvent) throws Exception {
                        int event=busEvent.getEvent();
                        switch (event){
                            case RxBus.SET_FIGURE:
                                break;
                            case RxBus.SET_NICKNAME:
                                Log.i(TAG,"更改用户名");
                                String name=(String) busEvent.getMsg();
                                tvUsername.setText(name);
                                break;
                            default:
                                break;
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(TAG,"RxBus exception="+throwable.toString());
                    }
                });
    }

    @Override
    public void initView() {

        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ImageView ivUser=(ImageView) findViewById(R.id.toolbar_user);
        ivUser.setOnClickListener(new OnMultiClickListener() {
            @Override
            public void onMultiClick(View view) {
                drawer.openDrawer(GravityCompat.START,true);
            }
        });
        ImageView ivSetting=(ImageView) findViewById(R.id.toolbar_setting);
        ivSetting.setOnClickListener(new OnMultiClickListener() {
            @Override
            public void onMultiClick(View view) {
                if(!hasFragment(SettingFragment.TAG)){
                    addFragmentWithAnimations(new SettingFragment());
                }else {
                    showToast("目标页面已存在，先关闭当前页面！");
                }
            }
        });
        toolbarTitle=(TextView) findViewById(R.id.toolbar_title);

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView=navigationView.getHeaderView(0);
        llLogin=(LinearLayout)headerView.findViewById(R.id.ll_login);
        llLogin.setOnClickListener(new OnMultiClickListener() {
            @Override
            public void onMultiClick(View view) {
//                TencentUtils.getInstance().login(MainActivity.this);
                if(!hasFragment(EmailFragment.TAG)){
                    addFragmentWithAnimations(new EmailFragment());
                }else {
                    showToast("目标页面已存在，先关闭当前页面！");
                }
                drawer.closeDrawer(GravityCompat.START);
            }
        });
        ivHeadImage=(ImageView)headerView.findViewById(R.id.iv_user_head);
        tvUsername=(TextView)headerView.findViewById(R.id.tv_username);
        tvUsername.setText(SharedPreferencesUtils.getInstance()
                .get(SharedPreferencesUtils.KEY_USER_NAME,"未登录"));
        tvUserQQ=(TextView)headerView.findViewById(R.id.tv_qq_number);
        addFragment(new WarframeFragment());
        startService(new Intent(this,WorldStateUpdateService.class));
//        TencentUtils.getInstance().autoLoginByCache(MainActivity.this);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    public int getFragmentContainerId() {
        return R.id.fragment_container;
    }

    @Override
    public void onFragmentChanged(String title) {
        toolbarTitle.setText(title);
    }

    /**
     * 获取动态权限
     */
    public void requestPermission(){
        RxPermissions rxPermissions=new RxPermissions(this);
        Disposable disposable = rxPermissions.request(
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_COARSE_LOCATION
//                Manifest.permission.INTERNET,
//                Manifest.permission.READ_LOGS
        )
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            //已开启所有权限
                            Log.i(TAG, "已开启所有权限");
                        } else {
                            //权限被拒绝
                            Log.e(TAG, "权限被拒绝");
                            TipDialog tipDialog = new TipDialog(MainActivity.this,
                                    "权限受限，功能将无法使用，\n请前往手机设置开启权限！");
                            tipDialog.show();
                        }
                    }
                });
    }

    @Override
    public boolean swipeBackPriority() {
        return false;//activity不通过滑动退出
    }

    //记录用户首次点击返回键的时间
    private long firstTime = 0;
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if(removeFragmentWithAnimations()){
                long secondTime = System.currentTimeMillis();
                if (secondTime - firstTime > 2000) {
                    showToast("再点一次退出程序！");
                    firstTime = secondTime;
                } else{
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_HOME);
                    startActivity(intent);
                }
            }
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_wf_website) {
            openBrowser("https://www.warframe.com/");
        } else if (id == R.id.nav_wiki_cn) {
            openBrowser("https://warframe.huijiwiki.com/");
        } else if (id == R.id.nav_wiki_en) {
            openBrowser("https://warframe.fandom.com/");
        } else if (id == R.id.nav_riven_im) {
            openBrowser("https://riven.im/");
        } else if (id == R.id.nav_wf_market) {
            openBrowser("https://warframe.market/");
        } else if (id == R.id.nav_riven_market) {
            openBrowser("https://riven.market/");
        } else if (id == R.id.nav_share) {
            if(!hasFragment(ShareFragment.TAG)){
                addFragmentWithAnimations(new ShareFragment());
            }else {
                showToast("目标页面已存在，先关闭当前页面！");
            }
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void openBrowser(String netAddress){
        Uri uri=Uri.parse(netAddress);
        Intent intent=new Intent(Intent.ACTION_VIEW,uri);
        startActivity(intent);
    }

}
