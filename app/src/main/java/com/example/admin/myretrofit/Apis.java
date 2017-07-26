package com.example.admin.myretrofit;

/**
 * Created by fushuang on 2017/7/25.
 */

public interface Apis {
    @UrlString("?id=%s&key=4d43e6b84de9561bb8fda801f2896a23")
    Call<HeathBean> getData(String s);
}
