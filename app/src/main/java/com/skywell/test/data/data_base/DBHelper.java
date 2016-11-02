package com.skywell.test.data.data_base;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.vk.sdk.VKSdk;
import com.vk.sdk.api.model.VKApiCommunityFull;
import com.vk.sdk.api.model.VKApiModel;
import com.vk.sdk.api.model.VKApiPost;
import com.vk.sdk.api.model.VKApiUserFull;
import com.vk.sdk.api.model.VKList;
import com.vk.sdk.api.model.VKPostArray;

import java.util.Collection;
import java.util.HashMap;

public class DBHelper extends SQLiteOpenHelper {

    public static final String LOG_TAG = "DB Log";
    public static final String TABLE_POSTS = "posts";
    public static final String TABLE_GROUPS = "groups";
    public static final String TABLE_USERS = "users";
    private Context mContext;
    
    public DBHelper(Context context) {
        super(context, "myDB", null, 1);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(LOG_TAG, "--- onCreate database ---");
        createTableWithName(db, TABLE_POSTS);
        createTableWithName(db, TABLE_GROUPS);
        createTableWithName(db, TABLE_USERS);
    }
    
    private void createTableWithName(SQLiteDatabase db, String name){
        db.execSQL("create table " +  name 
                + " (id integer,"
                + "json text);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void putOwner(VKApiUserFull owner){
        ContentValues contentValues = new ContentValues();
        putContentValue(contentValues, owner);
        insertValues(TABLE_USERS, contentValues);
    }

    public void putUsers(HashMap<Integer, VKApiUserFull> users){
        getValues(TABLE_USERS, users.values());
    }

    public void putCommunities(HashMap<Integer, VKApiCommunityFull> groups){
        getValues(TABLE_GROUPS, groups.values());
    }

    public void putPosts(VKList<VKApiPost> posts){
        getValues(TABLE_POSTS, posts);
    }

    private void insertValues(String tableName, ContentValues contentValues){
        getWritableDatabase().insert(tableName, null, contentValues);
    }

    private void getValues(String tableName, Collection<? extends VKApiModel> collection){
        ContentValues contentValues;
        for (VKApiModel model : collection){
            contentValues = new ContentValues();
            putContentValue(contentValues, model);
            insertValues(tableName, contentValues);
        }
    }

    private void putContentValue(ContentValues contentValues, VKApiModel model){
        contentValues.put("id", model.getId());
        contentValues.put("json", model.json);
    }

    public void clearTable(String tableName){
        int clearCount = getWritableDatabase().delete(tableName, null, null);
        Log.d(LOG_TAG, "deleted rows count = " + clearCount);
    }

    public void clearUsersSafe() {
        String[] args={VKSdk.getAccessToken().userId};
        getWritableDatabase().delete(TABLE_USERS, "id != ?", args);
    }

    public void clearAll(){
        clearTable(TABLE_USERS);
        clearTable(TABLE_GROUPS);
        clearTable(TABLE_POSTS);
    }

    public VKApiUserFull getUserById(int id){
        return DBSelect.getUserById(id, getWritableDatabase());
    }

    HashMap<Integer, VKApiUserFull> getUsersMap(){
        return DBSelect.getUsersMap(getWritableDatabase());
    }

    HashMap<Integer, VKApiCommunityFull> getGroupsMap(){
        return DBSelect.getGroupsMap(getWritableDatabase());
    }

    VKPostArray getPosts(){
        return DBSelect.getPosts(getWritableDatabase());
    }

    public Context getContext() {
        return mContext;
    }
}