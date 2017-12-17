package com.asuscomm.smarts.jibcon_client_android.data;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.asuscomm.smarts.jibcon_client_android.utils.rxjava.Ignore;

import java.util.UUID;

import io.reactivex.Single;
import io.reactivex.subjects.SingleSubject;

/**
 * Created by jaeyoung on 09/12/2017.
 */

public class ProductInstanceRepo {
    private static final String TAG = "ProductInstanceRepo";

    private static ProductInstanceRepo sInstance;

    public static ProductInstanceRepo getInstance() {
        if (sInstance == null) {
            synchronized (ProductInstanceRepo.class) {
                if (sInstance == null) {
                    sInstance = new ProductInstanceRepo();
                }
            }
        }

        return sInstance;
    }

    @SuppressLint("MissingPermission")
    private Single<String> getDevicesUUID(final Context context) {
        SingleSubject<String> singleSubject = SingleSubject.create();

        chkReadPhoneStatePermission(context)
                .subscribe(
                        (__) -> {
                            final TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                            final String tmDevice, tmSerial, androidId;
                            tmDevice = "" + (tm != null ? tm.getDeviceId() : null);
                            tmSerial = "" + (tm != null ? tm.getSimSerialNumber() : null);
                            androidId = "" + Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
                            UUID deviceUuid = new UUID(androidId.hashCode(), ((long)tmDevice.hashCode() << 32) | tmSerial.hashCode());
                            String deviceId = deviceUuid.toString();
                            Log.d(TAG, String.format("getDevicesUUID: tmDevice=%s, tmSerial=%s, androidId=%s, Uuid=%s", tmDevice, tmSerial, androidId, deviceId));
                            singleSubject.onSuccess(deviceId);
                        },
                        singleSubject::onError
                );

        return singleSubject;
    }

    private Single<Ignore> chkReadPhoneStatePermission(Context context) {
        Log.d(TAG, "chkReadPhoneStatePermission: ");

        SingleSubject<Ignore> singleSubject = SingleSubject.create();
        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            singleSubject.onError(new Throwable("ReadPhoneState Permission required."));
        } else {
            singleSubject.onSuccess(Ignore.PLEASE_IGNORE);
        }

        return singleSubject;
    }

    public void create(Context context) {
        Log.d(TAG, "create: ");
        Single<String> devicesUUID = getDevicesUUID(context);
        devicesUUID.subscribe(
                (uuid) -> {
                    Log.d(TAG, "create: UUID=" + uuid);
                }
        );
    }
}
