package com.karas.wftoolkit.QqConnect;

import android.util.Log;

import com.tencent.open.utils.HttpUtils;
import com.tencent.tauth.IRequestListener;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;

/**
 * Created by Hongliang Luo on 2019/7/16.
 **/
public class RequestListener implements IRequestListener {
    final static private String TAG="RequestListener";
    private HandleResponse responseHandler;

    public RequestListener(HandleResponse responseHandler){
        this.responseHandler=responseHandler;
    }
    public interface HandleResponse{
        void onHandle(JSONObject jsonObject);
    }

    @Override
    public void onComplete(JSONObject jsonObject) {
        Log.w(TAG,"jsonObject="+jsonObject.toString());
        responseHandler.onHandle(jsonObject);
    }

    @Override
    public void onIOException(IOException e) {
        Log.e(TAG,"onIOException");
    }

    @Override
    public void onMalformedURLException(MalformedURLException e) {
        Log.e(TAG,"onMalformedURLException");
    }

    @Override
    public void onJSONException(JSONException e) {
        Log.e(TAG,"onJSONException");
    }

    @Override
    public void onConnectTimeoutException(ConnectTimeoutException e) {
        Log.e(TAG,"onConnectTimeoutException");
    }

    @Override
    public void onSocketTimeoutException(SocketTimeoutException e) {
        Log.e(TAG,"onSocketTimeoutException");
    }

    @Override
    public void onNetworkUnavailableException(HttpUtils.NetworkUnavailableException e) {
        Log.e(TAG,"onNetworkUnavailableException");
    }

    @Override
    public void onHttpStatusException(HttpUtils.HttpStatusException e) {
        Log.e(TAG,"onHttpStatusException");
    }

    @Override
    public void onUnknowException(Exception e) {
        Log.e(TAG,"onUnknowException");
    }
}
