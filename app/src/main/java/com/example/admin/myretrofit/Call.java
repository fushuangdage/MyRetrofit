package com.example.admin.myretrofit;

import android.os.AsyncTask;

import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * Created by fushuang on 2017/7/25.
 */

public class Call<T> extends AsyncTask<Call.CallBack<T>,Void,Object>{

    private  Class<T> tClass;  //用于接受之前 api接口的方法的返回值中的泛型
    public String url;
    private CallBack<T> callBack;

    public interface CallBack<S>{
        void onSuccess(S t);
        void onFail(Exception e);
    }


    public Call(String url, Class<T> tClass) {
        this.url = url;
        this.tClass = tClass;
    }

    @Override
    protected Object doInBackground(CallBack<T>... params) {
        callBack = params[0];
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            int responseCode = connection.getResponseCode();
            if (responseCode==200){
                InputStream inputStream = connection.getInputStream();
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                int len;
                byte[] bytes = new byte[102400];
                while ((len=inputStream.read(bytes))!=-1){
                    byteArrayOutputStream.write(bytes, 0, len);
                }
                String s = byteArrayOutputStream.toString("UTF-8");
//
                return new Gson().fromJson(s,tClass);
            }else {
                return new RuntimeException("网络错误,responseCode "+responseCode);
            }



        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        if (tClass.isInstance(o)){
            callBack.onSuccess(((T) o));
        }else if (o instanceof Exception){
            callBack.onFail((Exception) o);
        }
    }
}
