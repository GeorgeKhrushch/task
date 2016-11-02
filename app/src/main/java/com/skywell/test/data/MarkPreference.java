package com.skywell.test.data;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Admin on 02.11.2016.
 */

public class MarkPreference {

    private static final String PREF_NAME = "mark_pref";
    private static final String NEWS_MARK = "news_mark";

    public static void saveMark(String mark, Context context){
        getPrefs(context).edit().putString(NEWS_MARK, mark).apply();
    }

    public static String getNewsMark(Context context){
        return getPrefs(context).getString(NEWS_MARK, "");
    }

    private static SharedPreferences getPrefs(Context context){
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }
}
