package com.asuscomm.smarts.jibcon_client_android.utils;

import com.asuscomm.smarts.jibcon_client_android.BuildConfig;
import com.asuscomm.smarts.jibcon_client_android.data.network.ProductInstanceService;

import java.util.HashMap;

/**
 * Created by jaeyoung on 17/12/2017.
 */

public class UrlUtils {
    private static HashMap<Class, String> urls;

    static {
        urls = new HashMap<>();

        String apiUrl = BuildConfig.DEBUG ?
                "http://192.168.0.25:3000/" :
                "http://52.79.142.130/";

        urls.put(ProductInstanceService.class, apiUrl);
    }

    public static String getUrlWithClass(Class clazz) {
        return urls.get(clazz);
    }
}
