package com.skywell.test.data;


import com.skywell.test.data.attachments.UsersAndGroups;
import com.vk.sdk.api.model.VKPostArray;

public class NewsInfo {

    private String mNewsNextMark;
    private UsersAndGroups mUsersAndGroups;
    private VKPostArray mPosts = new VKPostArray();

    public NewsInfo() {
    }

    public String getNewsNextMark() {
        return mNewsNextMark;
    }

    public void setNewsNextMark(String newsNextMark) {
        mNewsNextMark = newsNextMark;
    }

    public UsersAndGroups getUsersAndGroups() {
        return mUsersAndGroups;
    }

    public void setUsersAndGroups(UsersAndGroups usersAndGroups) {
        mUsersAndGroups = usersAndGroups;
    }

    public VKPostArray getPosts() {
        return mPosts;
    }

    public void setPosts(VKPostArray posts) {
        mPosts = posts;
    }
}
