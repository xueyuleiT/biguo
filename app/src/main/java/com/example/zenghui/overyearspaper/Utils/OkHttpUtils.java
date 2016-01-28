package com.example.zenghui.overyearspaper.Utils;

import com.squareup.okhttp.OkHttpClient;

import retrofit.client.OkClient;

/**
 * Created by zenghui on 16/1/19.
 */
public class OkHttpUtils extends OkClient {

    public static OkHttpUtils OkHttpClientIntance;
    private OkHttpClient okHttpClient;

    public static OkHttpUtils getOkHttpClientIntance() {
        if (OkHttpClientIntance == null) {
            OkHttpClientIntance = new OkHttpUtils();
        }
        return OkHttpClientIntance;
    }


}
