package com.asuscomm.smarts.jibcon_client_android;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.lang.ref.WeakReference;

/**
 * Created by jaeyoung on 09/12/2017.
 */

public class AndroidLinkingRepo {
    private static final String TAG = "AndroidLinkingRepo";

    private static AndroidLinkingRepo sInstance;
    private WeakReference<Context> mContext;

    public AndroidLinkingRepo() {
    }

    public static AndroidLinkingRepo getInstance(Context context) {
        if (sInstance == null) {
            synchronized (AndroidLinkingRepo.class) {
                if (sInstance == null) {
                    sInstance = new AndroidLinkingRepo();
                }
            }
        }

        sInstance.setContext(context);

        return sInstance;
    }

    private void setContext(Context context) {
        mContext = new WeakReference<Context>(context);
    }

    public boolean isLinked() {
        Log.d(TAG, "isLinked: ");
        SharedPreferences sharedPreferences = mContext.get().getSharedPreferences(Consts.SHARED_PREF_NAME, 0);

        return sharedPreferences.getBoolean(Consts.ANDROID_ISLINKED, false);
    }

    public void setLinked(boolean isLinked) {
        Log.d(TAG, "setLinked: ");
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(Consts.SHARED_PREF_NAME, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(Consts.ANDROID_ISLINKED, isLinked);

        editor.apply();
    }

    private Context getContext() {
        Context context = mContext.get();
        if (context == null) {
            throw new NullPointerException("context is null");
        }

        return context;
    }
}
