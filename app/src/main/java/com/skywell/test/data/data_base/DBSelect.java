package com.skywell.test.data.data_base;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.vk.sdk.api.model.VKApiCommunityFull;
import com.vk.sdk.api.model.VKApiPost;
import com.vk.sdk.api.model.VKApiUserFull;
import com.vk.sdk.api.model.VKPostArray;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class DBSelect {

    public static VKApiUserFull getUserById(int id, SQLiteDatabase database){
        String query = "SELECT * FROM " + DBHelper.TABLE_USERS + " WHERE id = ?";
        Cursor cursor = database
                .rawQuery(query, new String[] {String.valueOf(id)});
        if (cursor.moveToFirst()) {
            do {
                String json = cursor.getString(cursor.getColumnIndex("json"));
                try {
                    VKApiUserFull owner = new VKApiUserFull(new JSONObject(json));
                    cursor.close();
                    return owner;
                } catch (JSONException | NullPointerException e) {
                    e.printStackTrace();
                }
            } while (cursor.moveToNext());
        } else
            Log.d(DBHelper.LOG_TAG, "0 rows");

        cursor.close();
        return  null;
    }

    public static HashMap<Integer, VKApiUserFull> getUsersMap(SQLiteDatabase database){
        HashMap<Integer, VKApiUserFull> usersMap = new HashMap<>();
        Cursor cursor = database.query(DBHelper.TABLE_USERS, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                String json = cursor.getString(cursor.getColumnIndex("json"));
                try {
                    VKApiUserFull owner = new VKApiUserFull(new JSONObject(json));
                    usersMap.put(owner.getId(), owner);
                } catch (JSONException | NullPointerException e) {
                    e.printStackTrace();
                }
            } while (cursor.moveToNext());
        } else
            Log.d(DBHelper.LOG_TAG, "0 rows");

        cursor.close();
        return usersMap;
    }

    public static HashMap<Integer, VKApiCommunityFull> getGroupsMap(SQLiteDatabase database) {
        HashMap<Integer, VKApiCommunityFull> groupsMap = new HashMap<>();
        Cursor cursor = database.query(DBHelper.TABLE_GROUPS, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                String json = cursor.getString(cursor.getColumnIndex("json"));
                try {
                    VKApiCommunityFull group = new VKApiCommunityFull().parse(new JSONObject(json));
                    groupsMap.put(group.getId(), group);
                } catch (JSONException |NullPointerException e) {
                    e.printStackTrace();
                }
            } while (cursor.moveToNext());
        } else
            Log.d(DBHelper.LOG_TAG, "0 rows");

        cursor.close();
        return groupsMap;
    }

    public static VKPostArray getPosts(SQLiteDatabase database) {
        VKPostArray posts = new VKPostArray();
        Cursor cursor = database.query(DBHelper.TABLE_POSTS, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                String json = cursor.getString(cursor.getColumnIndex("json"));
                Log.d(DBHelper.LOG_TAG, "JSON " + json);
                try {
                    VKApiPost post = new VKApiPost(new JSONObject(json));
                    posts.add(post);
                } catch (JSONException |NullPointerException e) {
                    e.printStackTrace();
                }
            } while (cursor.moveToNext());
        } else
            Log.d(DBHelper.LOG_TAG, "0 rows");

        cursor.close();
        return posts;
    }
}
