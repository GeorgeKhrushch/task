package com.skywell.test.ui.images;

import android.widget.ImageView;

import com.skywell.test.ui.views.RecycleScrollListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class LoadListViewImage {

    public static void validLoad(ImageView imageView, String url){
        if(url != null && !url.equals(""))
            Picasso.with(imageView.getContext())
                    .load(url)
                    .tag(RecycleScrollListener.sPicassoTag)
                    .into(imageView);
    }

    public static void validLoad(ImageView imageView, LoadInfo loadInfo){
        if(loadInfo != null && !loadInfo.equals(""))
            Picasso.with(imageView.getContext())
                    .load(loadInfo.url)
                    .tag(RecycleScrollListener.sPicassoTag)
                    .resize(loadInfo.width, loadInfo.height)
                    .into(imageView);
    }

    public static void validLoadButch(LoadArrays loadArrays) {
        getNextImage(0, loadArrays);
    }

    private static void getNextImage(final int i,
                                     final LoadArrays loadArrays){
        if(i == loadArrays.mImageViews.size())
            return;

        final LoadInfo info = loadArrays.mLoadInfos.get(i);
        Picasso.with(loadArrays.mImageViews.get(i).getContext())
                .load(info.url)
                .tag(RecycleScrollListener.sPicassoTag)
                .into(loadArrays.mImageViews.get(i), new Callback() {
                    @Override
                    public void onSuccess() {
                        getNextImage(i+1, loadArrays);
                    }

                    @Override
                    public void onError() {

                    }
                });
    }
}
