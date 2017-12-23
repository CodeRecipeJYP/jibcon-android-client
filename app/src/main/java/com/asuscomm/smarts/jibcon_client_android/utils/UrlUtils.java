package com.asuscomm.smarts.jibcon_client_android.utils;

import com.asuscomm.smarts.jibcon_client_android.BuildConfig;
import com.asuscomm.smarts.jibcon_client_android.data.network.ProductInstanceService;

import java.util.HashMap;

/**
 * Created by jaeyoung on 17/12/2017.
 */

public class UrlUtils {
//    private static final String INTERNAL_IP = "192.168.114.38";
//    private static final String INTERNAL_IP = "192.168.50.116";
    private static final String INTERNAL_IP = "10.0.2.2";
    private static HashMap<Class, String> urls;

    static {
        urls = new HashMap<>();

        String apiUrl = BuildConfig.DEBUG ?
                String.format("http://%s:3000/", INTERNAL_IP) :
                "http://52.79.142.130/";

        urls.put(ProductInstanceService.class, apiUrl);
    }

    public static String getUrlWithClass(Class clazz) {
        return urls.get(clazz);
    }
}
