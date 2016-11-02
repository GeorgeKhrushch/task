package com.skywell.test.data.data_base;

import com.skywell.test.data.MarkPreference;
import com.skywell.test.data.NewsInfo;
import com.skywell.test.data.attachments.UsersAndGroups;
import com.skywell.test.data.format.DataUtils;

/**
 * Created by Admin on 02.11.2016.
 */

public class NewsData {

    public static NewsInfo restoreNewsInfo(DBHelper helper){
        UsersAndGroups usersAndGroups = new UsersAndGroups(
                helper.getGroupsMap(), helper.getUsersMap());
        NewsInfo newsInfo = new NewsInfo();
        newsInfo.setNewsNextMark(MarkPreference.getNewsMark(helper.getContext()));
        newsInfo.setPosts(helper.getPosts());
        newsInfo.setUsersAndGroups(usersAndGroups);
        DataUtils.fillInPostSortedAttachments(newsInfo.getPosts());
        return newsInfo;
    }

    public static void saveNewsInfo(NewsInfo newsInfo, DBHelper helper){
        clearNewsTable(helper);
        helper.putPosts(newsInfo.getPosts());
        helper.putCommunities(newsInfo.getUsersAndGroups().getGroupsMap());
        helper.putUsers(newsInfo.getUsersAndGroups().getUsersMap());
        MarkPreference.saveMark(newsInfo.getNewsNextMark(), helper.getContext());

        restoreNewsInfo(helper);
    }

    public static void clearNewsTable(DBHelper helper){
        helper.clearTable(DBHelper.TABLE_POSTS);
        helper.clearTable(DBHelper.TABLE_GROUPS);
        helper.clearUsersSafe();
    }
}
