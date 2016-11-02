package com.skywell.test.data.api;

import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKApiConst;

import java.util.HashMap;
import java.util.Map;

class ParamsHolder {
    private static final String USER_PARAMS = "photo_50, photo_100, photo_200, online, city," +
            " status, followers_count, education, friend_status, can_see_audio, counters, last_seen";

    static Map<String, String> getNewsMap(String mark) {
        Map<String, String> userParams = new HashMap<>();
        userParams.put(VKApiConst.FILTERS, "post");
        userParams.put(VKApiConst.COUNT, String.valueOf(10));
        userParams.put(VKApiConst.START_FROM, mark);
        userParams.put("fields", "photo_100");
        addTokens(userParams);
        return userParams;
    }

    static Map<String, String> getUserMap() {
        Map<String, String> userParams = new HashMap<>();
        userParams.put("fields", USER_PARAMS);
        addTokens(userParams);
        return userParams;
    }

    private static void addTokens(Map<String, String> userParams) {
        userParams.put("v", "5.52");
        userParams.put("access_token", VKSdk.getAccessToken().accessToken);
    }
}
