package com.karas.wftoolkit.Retrofit2;

import android.net.ParseException;
import android.util.Log;

import com.google.gson.JsonParseException;
import com.google.gson.stream.MalformedJsonException;
import com.karas.wftoolkit.CommonUtils.ToastUtils;

import org.json.JSONException;

import java.io.File;
import java.io.InterruptedIOException;
import java.net.ConnectException;
import java.net.NoRouteToHostException;
import java.net.SocketException;
import java.net.URLDecoder;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.HttpException;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Hongliang Luo on 2018/8/9.
 **/
public class Retrofit2Utils {
    final static private String TAG="Retrofit2Utils";
//    private static final String SEVER_ADDRESS = "https://104.28.20.115//";
    private static final String SEVER_ADDRESS = "https://api.warframestat.us/";

    static private Retrofit2Utils instance;
    private static final long DEFAULT_TIMEOUT=6000;
    private static final String DEFAULT_RESPONSE_BODY="LHL";
    private InterceptorListener listener=null;

    private Retrofit2Utils(){

    }

    public static Retrofit2Utils getInstance(){
        if(instance==null){
            synchronized (Retrofit2Utils.class){
                if(instance==null){
                    instance=new Retrofit2Utils();
                }
            }
        }
        Log.i(TAG,"instance="+instance);
        return instance;
    }

    /**
     * 配置okHttp
     * @return builder
     */
    private OkHttpClient.Builder getOkHttpClientBuilder(){
        final HttpLoggingInterceptor loggingInterceptor=new HttpLoggingInterceptor(
                new HttpLoggingInterceptor.Logger() {
                    @Override
                    public void log(String message) {
                        Disposable disposable = io.reactivex.Observable.just(message)
                                .map(new Function<String, String>() {
                                    @Override
                                    public String apply(String message) throws Exception {
                                        //处理http拦截信息
                                       return httpInterceptorMsgProcess(message);
                                    }
                                }).observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Consumer<String>() {
                                    @Override
                                    public void accept(String s) throws Exception {
                                        if(!s.equals(DEFAULT_RESPONSE_BODY)){
                                            Log.e(TAG,"http response error msg="+s);
                                            if(listener!=null){
                                                listener.onResolveErrorMsg(s);
                                            }else {
                                                Log.e(TAG,"网络请求返回异常信息，但是回调为空");
                                            }
                                        }
                                    }
                                }, new Consumer<Throwable>() {
                                    @Override
                                    public void accept(Throwable throwable) throws Exception {
                                        Log.e(TAG,"onError error="+ throwable.getMessage());
                                    }
                                });
                    }
                });
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        File cacheFile=new File("","Cache");
        Cache cache=new Cache(cacheFile,1024*1024*20);//设置20M缓存
        return new OkHttpClient.Builder()
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)
                .connectTimeout(DEFAULT_TIMEOUT,TimeUnit.MILLISECONDS)
                .addInterceptor(loggingInterceptor)
                .cache(cache);
    }

    /**
     * 配置retrofit
     * @return Retrofit
     */
    public Retrofit getRetrofit(InterceptorListener listener) {
        this.listener=listener;
        OkHttpClient okHttpClient = instance.getOkHttpClientBuilder().build();
        return new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(SEVER_ADDRESS)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    /**
     * 处理http拦截的信息，提取错误信息
     * @param message msg
     * @return 错误信息
     */
    private static String httpInterceptorMsgProcess(String message){
        //打印拦截http信息
        try{
            Log.w(TAG,
                    "-----------HttpLoggingInterceptor-----------");
            String body = URLDecoder.decode(message, "utf-8");
            int logPartCount=(body.length()/3000)+1;
            int endIndex;
            for (int i=0;i<logPartCount;i++){
                if(i==logPartCount-1){
                    endIndex=body.length();
                }else {
                    endIndex=(i+1)*3000;
                }
                Log.w(TAG,body.substring(i*3000,endIndex));
            }
            Log.w(TAG,
                    "-------------------end---------------------");
//            //解析拦截的内容，用于处理错误的网络响应下的响应信息
//            //一般格式{"result":false,"message":["用户名\/密码错误"]}
//            if (body.contains("result") &&
//                    body.contains("message")) {
//                JSONObject json = new JSONObject(body);
//                String msg=json.getString("message");
//                msg=msg.replace("[","")
//                        .replace("]","")
//                        .replace("\"","")
//                        .replace("\\","");
//                if (!json.getBoolean("result")) {
//                    return msg;
//                }
//            }
        }catch (Exception e){
            Log.e(TAG,"解析拦截器信息出错");
            String eString=e.getMessage();
            int logPartCount=(eString.length()/3000)+1;
            int endIndex;
            for (int i=0;i<logPartCount;i++){
                if(i==logPartCount-1){
                    endIndex=eString.length();
                }else {
                    endIndex=(i+1)*3000;
                }
                Log.e(TAG,eString.substring(i*3000,endIndex));
            }
        }
        return DEFAULT_RESPONSE_BODY;
    }

    /**
     * 异常处理
     * 一般由服务器返回异常，这里处理无法连接服务器和服务器没有返回的异常
     * @param e 抛出的异常
     */
    public static void processError(Throwable e) {
        Log.e(TAG,"onError error="+ e.toString());
        if (e instanceof HttpException) {     //   HTTP错误
            //不处理
            Log.e(TAG,"HttpException---未进行处理");
        } else if (e instanceof ConnectException
                || e instanceof UnknownHostException
                ||e instanceof NoRouteToHostException
                ||e instanceof SocketException) {   //   连接错误
            ToastUtils.showShortToast("网络连接异常");
        } else if (e instanceof InterruptedIOException) {   //  连接超时
            ToastUtils.showShortToast("网络连接超时");
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException
                ||e instanceof MalformedJsonException) {   //  解析错误
            ToastUtils.showShortToast("数据解析异常");
        }else {
            //
        }
    }

    public interface InterceptorListener {
        void onResolveErrorMsg(String errorResponse);
    }

}

