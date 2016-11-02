package com.skywell.test.data;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import rx.Observable;

public interface VkService {
    @GET("users.get?")
    Observable<ResponseBody> getUserInfo(@QueryMap Map<String,String> map);

    @GET("newsfeed.get?")
    Observable<ResponseBody> getNews(@QueryMap Map<String,String> map);
}