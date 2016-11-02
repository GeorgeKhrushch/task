package com.skywell.test.data.api;

import com.skywell.test.data.NewsInfo;
import com.skywell.test.data.VkService;
import com.skywell.test.data.format.DataUtils;
import com.vk.sdk.api.model.VKUsersArray;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RequestManager {

    private VkService mVkService;

    public RequestManager(VkService vkService) {
        mVkService = vkService;
    }

    public Observable<VKUsersArray> getOwnerInfo(){
        return mVkService
                .getUserInfo(ParamsHolder.getUserMap())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(responseBody -> {
                    try {
                        return  (VKUsersArray)
                                new VKUsersArray().parse(new JSONObject(responseBody.string()));
                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
                    return null;
                });
    }

    public Observable<NewsInfo> getNewsInfo(String mark){
        return mVkService
                .getNews(ParamsHolder.getNewsMap(mark))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(responseBody -> {
                    try {
                        return  DataUtils.parseNewsInfo(new JSONObject(responseBody.string()));
                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
                    return null;
                });
    }
}
