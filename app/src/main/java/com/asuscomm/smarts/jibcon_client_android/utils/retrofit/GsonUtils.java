package com.asuscomm.smarts.jibcon_client_android.utils.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by jaeyoung on 17/12/2017.
 */

public class GsonUtils {
    private static Gson sGson;

    public static Gson getGson() {
        if (sGson == null) {
            sGson = new GsonBuilder()
                    .create();
        }

        return sGson;
    }
}
