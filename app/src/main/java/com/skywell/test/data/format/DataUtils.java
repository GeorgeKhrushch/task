package com.skywell.test.data.format;

import com.skywell.test.data.NewsInfo;
import com.skywell.test.data.attachments.UsersAndGroups;
import com.vk.sdk.api.model.VKApiPost;
import com.vk.sdk.api.model.VKList;
import com.vk.sdk.api.model.VKPostArray;

import org.json.JSONException;
import org.json.JSONObject;

public class DataUtils {
    public static NewsInfo parseNewsInfo(final JSONObject response){
        NewsInfo newsInfo = new NewsInfo();
        UsersAndGroups usersAndGroups = new UsersAndGroups();
        usersAndGroups.setUsers(JsonUtils.getUsersFromNewsResponse(response));
        usersAndGroups.setCommunities(
                JsonUtils.getCommunitiesFromNewsResponse(response));
        newsInfo.setUsersAndGroups(usersAndGroups);
        newsInfo.setNewsNextMark(JsonUtils.getNewsNextFrom(response));
        try {
            newsInfo.setPosts((VKPostArray) new VKPostArray().parse(response));
            fillInPostSortedAttachments(newsInfo.getPosts());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return newsInfo;
    }

    public static void fillInPostSortedAttachments(VKList<VKApiPost> posts) {
        for(VKApiPost post : posts){
            post.fillInSortedAttachments();
        }
    }

    public static void mergeNews(NewsInfo newsInfoOld, NewsInfo newsInfoNew) {
        newsInfoOld.getUsersAndGroups().merge(newsInfoNew.getUsersAndGroups());
        newsInfoOld.setNewsNextMark(newsInfoNew.getNewsNextMark());
        newsInfoOld.getPosts().addAll(newsInfoNew.getPosts());
    }
}
