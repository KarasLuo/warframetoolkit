package com.karas.wftoolkit.QqConnect;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.karas.wftoolkit.AppActivity;
import com.karas.wftoolkit.AppFragment;
import com.karas.wftoolkit.CommonUtils.RxBus;
import com.karas.wftoolkit.MyApplication;
import com.karas.wftoolkit.R;
import com.tencent.connect.common.Constants;
import com.tencent.connect.share.QQShare;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by Hongliang Luo on 2019/7/15.
 **/
public class TencentUtils {
    final private static String TAG="TencentUtils";
    final private static String APP_ID="101720474";
    final private static String APP_KEY="94492bb0c2967b2d457e1e58d76a471d";
    private static TencentUtils instance;
    private static Tencent tencent;
    private IUiListener loginListener;

    private String accessToken;
    private String openId;
    private String expires;

    private TencentUtils(){
        tencent=Tencent.createInstance(APP_ID,MyApplication.getAppContext());
//        loginListener=new IUiListener() {
//            @Override
//            public void onComplete(Object o) {
////                       {"ret":0,
////                        "openid":"426968C2FDBF9E47C41812030B6C29CE",
////                        "access_token":"EBDB1C6998246D3187525405FA35462F",
////                        "pay_token":"084C02C669F9B35079D58883C16BF3D2",
////                        "expires_in":7776000,
////                        "pf":"desktop_m_qq-10000144-android-2002-",
////                        "pfkey":"794bda292bc6c4d99ff96a2a749e6049",
////                        "msg":"",
////                        "login_cost":68,
////                        "query_authority_cost":141,
////                        "authority_cost":16530,
////                        "expires_time":1571032004435
////                        }
//                JSONObject jo=(JSONObject)o;
//                loginJsonParse(jo);
//                tencent.setAccessToken(accessToken,expires);
//                tencent.setOpenId(openId);
//                //登录完成获取用户信息
//                getUserInfo();
//            }
//
//            @Override
//            public void onError(UiError uiError) {
//                Log.e(TAG,"login onError:errorCode-"+uiError.errorCode
//                        + ",errorMessage="+uiError.errorMessage
//                        + ",detail="+uiError.errorDetail);
//            }
//
//            @Override
//            public void onCancel() {
//                Log.i(TAG,"login onCancel");
//            }
//        };
    }

    public static TencentUtils getInstance(){
        if(instance==null){
            synchronized (TencentUtils.class){
                if(instance==null){
                    instance=new TencentUtils();
                }
            }
        }
        Log.i(TAG,"instance="+instance);
        return instance;
    }

    public Tencent getTencent(){
        if (tencent==null){
            Log.e(TAG,"tencent is null");
        }
        return tencent;
    }

//    public IUiListener getLoginListener() {
//        return loginListener;
//    }
//
//    public void autoLoginByCache(AppActivity activity){
//        JSONObject jsonObject = null;
//        boolean isValid=tencent.checkSessionValid(APP_ID);
//        if(isValid){
//            jsonObject = tencent.loadSession(APP_ID);
//            tencent.initSessionCache(jsonObject);
//            Log.w(TAG,"jsonObject="+jsonObject.toString());
//            getUserInfo();
//        }
//    }
//
//    public void login(AppActivity activity){
//        JSONObject jsonObject = null;
//        boolean isValid=tencent.checkSessionValid(APP_ID);
//        if(isValid){
//            jsonObject = tencent.loadSession(APP_ID);
//            tencent.initSessionCache(jsonObject);
//            Log.w(TAG,"jsonObject="+jsonObject.toString());
//            getUserInfo();
//        }else {
//            tencent.login(activity, "all",loginListener);
//        }
//    }
//
//    public void login(AppFragment fragment){
//        JSONObject jsonObject = null;
//        boolean isValid=tencent.checkSessionValid(APP_ID);
//        if(isValid){
//            jsonObject = tencent.loadSession(APP_ID);
//            tencent.initSessionCache(jsonObject);
//            getUserInfo();
//        }else {
//            tencent.login(fragment, "all",loginListener);
//        }
//    }
//
//    private void loginJsonParse(JSONObject jsonObject){
//        Log.w(TAG,"loginJsonParse="+jsonObject.toString());
//        try {
//            accessToken=jsonObject.getString("access_token");
//            openId=jsonObject.getString("openid");
//            expires=jsonObject.getString("expires_in");
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void logout(Context context){
//        tencent.logout(context);
//        RxBus.getInstance().post(RxBus.getInstance().new
//                BusEvent<>(RxBus.SET_NICKNAME, "未登录"));
//    }
//
//    private void getUserInfo(){
//        Log.w(TAG,"getUserInfo accessToken="+tencent.getAccessToken());
//        Log.w(TAG,"getUserInfo openId="+tencent.getOpenId());
//        Log.w(TAG,"getUserInfo appid="+tencent.getAppId());
//        Bundle params=new Bundle();
//        params.putString("access_token",tencent.getAccessToken());
//        params.putString("oauth_consumer_key",APP_ID);
//        params.putString("openid",tencent.getOpenId());
//        tencent.requestAsync("user/get_simple_userinfo", params, Constants.HTTP_GET,
//                new RequestListener(new RequestListener.HandleResponse() {
//            @Override
//            public void onHandle(JSONObject jsonObject) {
//                try {
//                    String nickname=jsonObject.getString("nickname");
//                    String figureUrl=jsonObject.getString("figureurl_qq_1");
//                    Log.i(TAG,"nickname="+nickname);
//                    RxBus.getInstance().post(RxBus.getInstance().new
//                            BusEvent<>(RxBus.SET_NICKNAME, nickname));
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }),null);
//    }

    public void share(AppActivity activity,String filePath,IUiListener listener){
        if(filePath==null){
            activity.showToast("分享内容无效！");
            return;
        }
        Bundle params=new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_IMAGE);
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL,filePath);
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME,activity.getString(R.string.app_name));
        params.putInt(QQShare.SHARE_TO_QQ_EXT_INT,QQShare.SHARE_TO_QQ_FLAG_QZONE_ITEM_HIDE);
        tencent.shareToQQ(activity, params,listener);
    }
}
