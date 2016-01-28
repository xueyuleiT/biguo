package com.example.zenghui.overyearspaper.Utils;

import java.util.List;

import retrofit.http.Body;
import retrofit.http.POST;

/**
 * Created by zenghui on 16/1/19.
 */
public interface ITask {

    @POST("/rest/app/ins")
    void postAppListInfo(@Body List<String> appInfoList);

}
