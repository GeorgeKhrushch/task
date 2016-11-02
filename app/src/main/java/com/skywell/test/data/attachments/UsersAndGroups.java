package com.skywell.test.data.attachments;

import com.vk.sdk.api.model.VKApiCommunityArray;
import com.vk.sdk.api.model.VKApiCommunityFull;
import com.vk.sdk.api.model.VKApiOwner;
import com.vk.sdk.api.model.VKApiUserFull;
import com.vk.sdk.api.model.VKList;

import java.util.HashMap;

public class UsersAndGroups {

    private VKList<VKApiUserFull> mUsers = new VKList<>();
    private VKApiCommunityArray mCommunities = new VKApiCommunityArray();
    private HashMap<Integer, VKApiUserFull> mUsersMap = new HashMap<>();
    private HashMap<Integer, VKApiCommunityFull> mGroupsMap = new HashMap<>();

    public UsersAndGroups() {
    }

    public UsersAndGroups(HashMap<Integer, VKApiCommunityFull> groupsMap,
                          HashMap<Integer, VKApiUserFull> usersMap) {
        mGroupsMap = groupsMap;
        mUsersMap = usersMap;
    }

    public VKList<VKApiUserFull> getUsers() {
        return mUsers;
    }

    public void setUsers(VKList<VKApiUserFull> users) {
        mUsers = users;
    }

    private VKApiCommunityArray getCommunities() {
        return mCommunities;
    }

    public void setCommunities(VKApiCommunityArray communities) {
        mCommunities = communities;
    }

    public void merge(UsersAndGroups otherOne){
        mCommunities.addAll(otherOne.getCommunities());
        mUsers.addAll(otherOne.getUsers());
    }

    public void setUpMaps(){
        for(VKApiCommunityFull communityFull : mCommunities){
            mGroupsMap.put(communityFull.id, communityFull);
        }

        for(VKApiUserFull userFull : mUsers){
            mUsersMap.put(userFull.id, userFull);
        }

        mCommunities = new VKApiCommunityArray();
        mUsers = new VKList<>();
    }

    public HashMap<Integer, VKApiUserFull> getUsersMap() {
        return mUsersMap;
    }

    public HashMap<Integer, VKApiCommunityFull> getGroupsMap() {
        return mGroupsMap;
    }

    public VKApiOwner getMappedOwner(int id) {
        if(id < 0)
            return mGroupsMap.get(-id);
        else
            return mUsersMap.get(id);
    }
}
