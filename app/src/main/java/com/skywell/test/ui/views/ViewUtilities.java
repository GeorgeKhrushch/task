package com.skywell.test.ui.views;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class ViewUtilities {
    public static int screenWidth;
    public static int screenHeight;

    public static void hideKeyboard(Context context){
        try {
            View view = ((Activity) context).getCurrentFocus();
            if (view != null) {
                InputMethodManager imm = (InputMethodManager) context
                        .getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }catch (Exception ignored){}
    }

    public static void setScreenDimentions(Context context){
        DisplayMetrics displaymetrics = new DisplayMetrics();
        ((Activity)context).getWindowManager().getDefaultDisplay()
                .getMetrics(displaymetrics);
        screenHeight = displaymetrics.heightPixels;
        screenWidth = displaymetrics.widthPixels;
    }
}
