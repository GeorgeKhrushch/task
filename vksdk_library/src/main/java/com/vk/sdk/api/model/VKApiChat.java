//
//  Copyright (c) 2014 VK.com
//
//  Permission is hereby granted, free of charge, to any person obtaining a copy of
//  this software and associated documentation files (the "Software"), to deal in
//  the Software without restriction, including without limitation the rights to
//  use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
//  the Software, and to permit persons to whom the Software is furnished to do so,
//  subject to the following conditions:
//
//  The above copyright notice and this permission notice shall be included in all
//  copies or substantial portions of the Software.
//
//  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
//  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
//  FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
//  COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
//  IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
//  CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
//

/**
 * Chat.java
 * vk-android-sdk
 *
 * Created by Babichev Vitaly on 19.01.14.
 * Copyright (c) 2014 VK. All rights reserved.
 */
package com.vk.sdk.api.model;

import android.content.Context;
import android.content.res.Resources;
import android.os.Parcel;
import android.util.Log;
import android.util.SparseArray;

import com.vk.sdk.R;
import com.vk.sdk.VKSdk;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Chat object describes a user's chat.
 */
@SuppressWarnings("unused")
public class VKApiChat extends VKApiModel implements Identifiable, android.os.Parcelable {

    /**
     * Chat ID, positive number.
     */
    public int id;
    public String photo_100;

    /**
     * Chat title.
     */
    public String title;

    /**
     * ID of the chat starter, positive number
     */
    public int admin_id;
    public String action;
    public String action_text;
    public int action_mid;

    /**
     * List of chat participants' IDs.
     */
    public int[] users;
    public SparseArray<VKApiUserFull> usersFullArray = new SparseArray<>();

    public VKApiChat(JSONObject from) {
        parse(from);
    }
    /**
     * Fills a Chat instance from JSONObject.
     */
    public VKApiChat parse(JSONObject source) {
        id = source.optInt("chat_id");
        title = source.optString("title");
        admin_id = source.optInt("admin_id");
        photo_100 = source.optString("photo_100");
        action = source.optString("action");
        action_text = source.optString("action_text");
        action_mid = source.optInt("action_mid");
        JSONArray users = source.optJSONArray("chat_active");
        if(users != null) {
            this.users = new int[users.length()];
            for(int i = 0; i < this.users.length; i++) {
                this.users[i] = users.optInt(i);
            }
        }
        return this;
    }

    /**
     * Creates empty Chat instance.
     */
    public VKApiChat() {

    }

    @Override
    public int getId() {
        return id;
    }

    public String getActionText(Context context, VKApiUserFull userFull){
        boolean isOwner;
        try {
            isOwner = userFull.id == Integer.parseInt(VKSdk.getAccessToken().userId);
        }catch (NullPointerException e){
            isOwner = false;
        }

        String actionString = "";
        Resources resources = context.getResources();
        switch (action){
            case "chat_create": {
                String lastPart = resources.getString(R.string.chat_action_create)
                        + " \'" + action_text + "\'";
                if(isOwner)
                    actionString = resources.getString(R.string.chat_action_you) + " " + lastPart;
                else
                    try {
                        actionString = userFull + " " + lastPart;
                    }catch (NullPointerException e){
                        Log.d("action", "bad id " + userFull.id);
                    }
                break;
            }
            case "chat_kick_user": {
                actionString = resources.getString(R.string.chat_user_left);
                break;
            }
            case "chat_invite_user": {
                String firstPart;
                try{
                    firstPart = usersFullArray.get(action_mid).name;
                }catch (Exception e){
                    firstPart = resources.getString(R.string.chat_user);
                }
                actionString = firstPart + " " + resources.getString(R.string.chat_has_been_invited);
                break;
            }
            case "chat_title_update": {
                actionString = resources.getString(R.string.chat_new_name)
                        + " \'" + action_text + "\'";
                break;
            }
        }
        return actionString;
    }

    public String getActionText(Context context, int id) {
        Log.d("dialog", "chat id " + id);
        return getActionText(context, usersFullArray.get(id));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.photo_100);
        dest.writeString(this.title);
        dest.writeInt(this.admin_id);
        dest.writeString(this.action);
        dest.writeString(this.action_text);
        dest.writeInt(this.action_mid);
        dest.writeIntArray(this.users);
        dest.writeSparseArray((SparseArray) this.usersFullArray);
    }

    protected VKApiChat(Parcel in) {
        this.id = in.readInt();
        this.photo_100 = in.readString();
        this.title = in.readString();
        this.admin_id = in.readInt();
        this.action = in.readString();
        this.action_text = in.readString();
        this.action_mid = in.readInt();
        this.users = in.createIntArray();
        this.usersFullArray = in.readSparseArray(VKApiUserFull.class.getClassLoader());
    }

    public static final Creator<VKApiChat> CREATOR = new Creator<VKApiChat>() {
        @Override
        public VKApiChat createFromParcel(Parcel source) {
            return new VKApiChat(source);
        }

        @Override
        public VKApiChat[] newArray(int size) {
            return new VKApiChat[size];
        }
    };
}
