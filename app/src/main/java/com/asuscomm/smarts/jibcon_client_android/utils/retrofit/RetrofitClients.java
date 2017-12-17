package com.asuscomm.smarts.jibcon_client_android.utils.retrofit;

import com.asuscomm.smarts.jibcon_client_android.utils.UrlUtils;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.HashMap;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by jaeyoung on 17/12/2017.
 */

public class RetrofitClients {
    private static RetrofitClients sInstance;

    private HashMap<Class, Object> services = new HashMap<>();

    public static RetrofitClients getInstance() {
        if (sInstance == null) {
            synchronized(RetrofitClients.class) {
                if (sInstance == null) {
                    sInstance = new RetrofitClients();
                }
            }
        }
        return sInstance;
    }

    public <T> T getService(Class<? extends T> type) {
        T service = (T) services.get(type);
        if (service == null) {
            Retrofit client = new Retrofit.Builder()
                    .baseUrl(UrlUtils.getUrlWithClass(type))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(GsonUtils.getGson()))
                    .build();

            service = client.create(type);
            services.put(type, service);
        }

        return service;
    }
}