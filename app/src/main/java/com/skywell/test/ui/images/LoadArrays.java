package com.skywell.test.ui.images;

import android.widget.ImageView;

import java.util.ArrayList;

public class LoadArrays {

    public final ArrayList<ImageView> mImageViews = new ArrayList<>();
    public ArrayList<LoadInfo> mLoadInfos = new ArrayList<>();

    public void add(LoadArrays loadArrays){
        mImageViews.addAll(loadArrays.mImageViews);
        mLoadInfos.addAll(loadArrays.mLoadInfos);
    }
}
