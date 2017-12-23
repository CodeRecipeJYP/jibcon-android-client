package com.asuscomm.smarts.jibcon_client_android.data.domains;

/**
 * Created by jaeyoung on 17/12/2017.
 */

public class ProductInstance {
    public String uuid;
    public String fcmToken;

    public ProductInstance(String uuid, String fcmToken) {
        this.uuid = uuid;
        this.fcmToken = fcmToken;
    }
    public ProductInstance() {
    }
}
