package com.asuscomm.smarts.jibcon_client_android.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.asuscomm.smarts.jibcon_client_android.utils.RingingUtils;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by jaeyoung on 08/12/2017.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "MyFirebaseMessagingServ";
    
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d(TAG, "onMessageReceived: ");
        Log.d(TAG, "onMessageReceived() called with:" +
                " remoteMessage.getFrom() = [" + remoteMessage.getFrom() + "]" +
                " remoteMessage.getData() = [" + remoteMessage.getData() + "]");
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "onMessageReceived:" +
                    " Message Notification Body: " + remoteMessage.getNotification().getBody());
        }

        RingingUtils.activate(this);
    }
}
