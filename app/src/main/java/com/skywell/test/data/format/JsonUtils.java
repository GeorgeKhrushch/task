package com.skywell.test.data.format;

import com.vk.sdk.api.model.VKApiCommunityArray;
import com.vk.sdk.api.model.VKUsersArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonUtils {

    private static String getExtendedJsonString(JSONObject response, String part){
        String json = "{'response':";
        try {
            JSONObject res = response.getJSONObject("response");
            JSONArray array = res.getJSONArray(part);
            json += array.toString() + "}";
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }

    public static String getNewsNextFrom(JSONObject response){
        try {
            JSONObject res = response.getJSONObject("response");
            return res.getString("next_from");
        } catch (JSONException e) {
            //e.printStackTrace();
            return null;
        }
    }

    public static VKApiCommunityArray getCommunitiesFromNewsResponse(JSONObject response){
        JSONObject json;
        try {
            json = new JSONObject(getExtendedJsonString(response, "groups"));
            return  (VKApiCommunityArray) new VKApiCommunityArray().parse(json);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static VKUsersArray getUsersFromNewsResponse(JSONObject response){
        JSONObject json;
        try {
            json = new JSONObject(getExtendedJsonString(response, "profiles"));
            return  (VKUsersArray) new VKUsersArray().parse(json);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
