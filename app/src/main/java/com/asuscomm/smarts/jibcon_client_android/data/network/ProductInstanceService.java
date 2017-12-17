package com.asuscomm.smarts.jibcon_client_android.data.network;

import com.asuscomm.smarts.jibcon_client_android.data.domains.ProductInstance;

import io.reactivex.Flowable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by jaeyoung on 17/12/2017.
 */

public interface ProductInstanceService {
    @POST("/product_instances/")
    Flowable<ProductInstance> post(@Header("Authorization") String token,
                                   @Body ProductInstance deviceItem);
}
