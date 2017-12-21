package com.asuscomm.smarts.jibcon_client_android.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.asuscomm.smarts.jibcon_client_android.data.ProductInstanceRepo;
import com.asuscomm.smarts.jibcon_client_android.R;
import com.asuscomm.smarts.jibcon_client_android.data.AndroidLinkingRepo;
import com.asuscomm.smarts.jibcon_client_android.utils.RingingUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;

import android.Manifest;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        permissionCheckNCreateProductInstance();
    }

    private void initView() {
        findViewById(R.id.btn_ringing).setOnClickListener(
                __ -> {
                    RingingUtils.activate(this);
                }
        );
    }

    private void permissionCheckNCreateProductInstance() {
        Log.d(TAG, "permissionCheckNCreateProductInstance: ");

        if (!AndroidLinkingRepo.getInstance(this).isLinked()) {
            RxPermissions rxPermissions = new RxPermissions(this);

            rxPermissions
                    .request(Manifest.permission.READ_PHONE_STATE)
                    .subscribe(
                            (granted) -> {
                                if (granted) {
                                    createProductInstance();
                                } else {
                                    showPermissionRequired();
                                }
                            }
                    );
        }
    }

    private void showPermissionRequired() {
        Log.d(TAG, "showPermissionRequired: ");
    }

    private void createProductInstance() {
        Log.d(TAG, "createProductInstance: ");

        ProductInstanceRepo.getInstance().create(this);
    }
}
