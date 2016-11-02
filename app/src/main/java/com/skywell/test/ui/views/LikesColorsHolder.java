package com.skywell.test.ui.views;

import android.content.Context;

import com.skywell.test.R;

public class LikesColorsHolder {

    public final int regularColor;
    public final int likedColor;

    public LikesColorsHolder(Context context){
        regularColor = context.getResources().getColor(R.color.main_gray_text);
        likedColor = context.getResources().getColor(R.color.status_bar_color);
    }
}
