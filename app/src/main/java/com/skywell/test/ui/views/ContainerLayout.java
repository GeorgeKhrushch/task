package com.skywell.test.ui.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.skywell.test.R;

public class ContainerLayout extends LinearLayout {
    private int mTrueWidth;
    public ContainerLayout(Context context) {
        super(context);
    }

    public ContainerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ContainerLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ContainerLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public int getTrueWidth() {
        return mTrueWidth;
    }

    public void setTrueWidth() {
        if(mTrueWidth > 0)
            return;

        if(getMeasuredWidth() != 0)
            mTrueWidth = getMeasuredWidth() -
                    getResources().getDimensionPixelOffset(R.dimen.news_padding)*2;
        else
            mTrueWidth = ViewUtilities.screenWidth -
                    getResources().getDimensionPixelOffset(R.dimen.news_padding)*2;
    }

    public void setTrueWidth(int width){
        mTrueWidth = width;
    }
}
