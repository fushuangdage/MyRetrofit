package com.example.admin.myretrofit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;


public class MainActivity extends AppCompatActivity implements Call.CallBack<HeathBean>{

    private static final String TAG = "fs666";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Retrofit.Builder builder = new Retrofit.Builder();
        Retrofit retrofit = builder.baseUrl("http://japi.juhe.cn/health_knowledge/infoDetail").build();
        Apis apis = retrofit.create(Apis.class);

        apis.getData("5").execute(this);

    }

    @Override
    public void onSuccess(HeathBean t) {
        Log.d(TAG, "onSuccess: ");
    }

    @Override
    public void onFail(Exception e) {
        Log.d(TAG, "onFail: ");
    }
}
