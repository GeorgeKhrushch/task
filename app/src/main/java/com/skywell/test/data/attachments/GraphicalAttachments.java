package com.skywell.test.data.attachments;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.skywell.test.MainActivity;
import com.skywell.test.R;
import com.skywell.test.ui.images.LoadArrays;
import com.skywell.test.ui.images.LoadInfo;
import com.skywell.test.ui.images.LoadListViewImage;
import com.skywell.test.ui.views.ContainerLayout;
import com.skywell.test.ui.views.ImageWithTextOnIt;
import com.skywell.test.ui.views.ViewUtilities;
import com.vk.sdk.api.model.VKApiAudio;
import com.vk.sdk.api.model.VKApiLink;
import com.vk.sdk.api.model.VKApiPhoto;
import com.vk.sdk.api.model.VKApiPost;
import com.vk.sdk.api.model.VKApiVideo;
import com.vk.sdk.api.model.VKList;

import java.util.ArrayList;

public class GraphicalAttachments {

    private static final int firstBound = 2;
    private static final int secondBound = 6;
    private static final int thirdBound = 10;

    public static void putPhotosAttachments(@NonNull VKList<VKApiPhoto> photos,
                                            ContainerLayout container){

        final Context context = container.getContext();
        final boolean isSinglePhoto = photos.size() == 1;

        if(isSinglePhoto){
            ImageView imageView;
            VKApiPhoto photo;
            photo = photos.get(0);
            LoadInfo loadInfo = new LoadInfo(photo.photo_604, 0, 0);
            double coefficient = (double) photo.width / container.getTrueWidth();
            loadInfo.width = container.getTrueWidth();
            loadInfo.height = (int) (photo.height / coefficient);

            imageView = new ImageView(context);
            LoadListViewImage.validLoad(imageView, loadInfo);
            container.addView(imageView, getValidViewPosition(container),
                    getLastPhotoParams(loadInfo));
            return;
        }

        LoadArrays loadArrays = new LoadArrays();
        loadArrays.add(addPhotoViews(container, 0, firstBound, photos));
        if(photos.size() > firstBound)
            loadArrays.add(addPhotoViews(container, firstBound, secondBound, photos));

        if(photos.size() > secondBound)
            loadArrays.add(addPhotoViews(container, secondBound, thirdBound, photos));

        LoadListViewImage.validLoadButch(loadArrays);
    }

    public static int getValidViewPosition(ViewGroup container){
        return container.getChildCount() > 0 ? container.getChildCount() - 1 : 0;
    }

    private static LoadArrays addPhotoViews(ContainerLayout container, int bottomBound,
                                            int topBound,
                                            VKList<VKApiPhoto> photos){
        LinearLayout smallContainer = new LinearLayout(container.getContext());
        ImageView imageView;
        LoadArrays loadArrays = new LoadArrays();
        ArrayList<LoadInfo> loadInfos = new ArrayList<>();
        topBound = photos.size() > topBound ? topBound : photos.size();
        int smallestHeight = 10000;
        int totalWidth = 0;
        for(int i = bottomBound; i < topBound; i++) {
            if(photos.get(i).height < smallestHeight)
                smallestHeight = photos.get(i).height;
        }

        for(int i = bottomBound; i < topBound; i++) {
            double coefficient = (double) photos.get(i).height / smallestHeight;
            LoadInfo loadInfo = new LoadInfo(photos.get(i).photo_604,
                    (int) (photos.get(i).width / coefficient), smallestHeight);
            loadInfos.add(loadInfo);
            totalWidth += loadInfo.width;
        }

        double widthCoefficient = (double) totalWidth / container.getTrueWidth();

        for(int i = bottomBound; i < topBound; i++) {
            loadInfos.get(i-bottomBound).height =
                    (int) (loadInfos.get(i-bottomBound).height / widthCoefficient);
            loadInfos.get(i-bottomBound).width =
                    (int) (loadInfos.get(i-bottomBound).width / widthCoefficient);
        }

        for(int i = bottomBound; i < topBound; i++){
            imageView = new ImageView(container.getContext());
            loadArrays.mImageViews.add(imageView);

            if(topBound - bottomBound == 1) {
                container.addView(imageView, getValidViewPosition(container),
                        getPhotoLayoutParams(loadInfos.get(i - bottomBound)));
                break;
            }
            else
                smallContainer.addView(imageView, getPhotoLayoutParams(loadInfos.get(i-bottomBound)));

            if(i == topBound-1)
                container.addView(smallContainer, getValidViewPosition(container));
        }

        loadArrays.mLoadInfos = loadInfos;
        return loadArrays;
    }

    private static LinearLayout.LayoutParams getPhotoLayoutParams(LoadInfo loadInfo) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(loadInfo.width,
                loadInfo.height);
        params.rightMargin = 2;
        params.topMargin = 2;
        return params;
    }

    private static ViewGroup.LayoutParams getLastPhotoParams(LoadInfo loadInfo){
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                loadInfo.width, loadInfo.height);
        params.topMargin = 2;
        return params;
    }

    public static int getImageHeight(VKApiPhoto photo){
        double coefficient = photo == null ? 1 :(double)photo.width/(ViewUtilities.screenWidth);
        double imageHeight = photo == null ? ViewUtilities.screenHeight/3 : photo.height/coefficient;
        return (int) imageHeight == 0 ? ViewUtilities.screenHeight/3 : (int) imageHeight;
    }


    public static void putVideoAttachment(@NonNull VKList<VKApiVideo> videos, ContainerLayout container){

        boolean isSingleVideo = videos.size() == 1;
        Context context = container.getContext();
        ImageWithTextOnIt imageWithText;
        if(isSingleVideo){
            VKApiVideo video = videos.get(0);
            imageWithText = new ImageWithTextOnIt(context,
                    AttachmentHolders.getDurationString(video.duration));

            double widthCoefficient = (double) video.photo.get(1).width / container.getTrueWidth();
            LoadInfo loadInfo = new LoadInfo(video.photo_320,
                    (int) (video.photo.get(1).width/widthCoefficient),
                    (int) (video.photo.get(1).height/widthCoefficient/4*3));
            LoadListViewImage.validLoad(imageWithText, loadInfo);
            container.addView(imageWithText, getValidViewPosition(container),
                    getLastPhotoParams(loadInfo));
            return;
        }

        LoadArrays loadArrays = new LoadArrays();
        loadArrays.add(addVideoViews(container, 0, firstBound, videos));
        if(videos.size() > firstBound)
            loadArrays.add(addVideoViews(container, firstBound, secondBound, videos));

        if(videos.size() > secondBound)
            loadArrays.add(addVideoViews(container, secondBound, thirdBound, videos));

        LoadListViewImage.validLoadButch(loadArrays);
    }

    private static LoadArrays addVideoViews(ContainerLayout container, int bottomBound,
                                            int topBound, VKList<VKApiVideo> videos) {
        LinearLayout smallContainer = new LinearLayout(container.getContext());
        LoadArrays loadArrays = new LoadArrays();
        VKApiVideo video;
        ImageWithTextOnIt imageView;
        int totalWidth = 0;
        LoadInfo loadInfo;
        topBound = videos.size() > topBound ? topBound : videos.size();

        for(int i = bottomBound; i < topBound; i++) {
            totalWidth += videos.get(i).photo.get(1).width;
        }

        double widthCoefficient = (double) totalWidth / container.getTrueWidth();

        for(int i = bottomBound; i < topBound; i++){
            video = videos.get(i);
            imageView = new ImageWithTextOnIt(container.getContext(),
                    AttachmentHolders.getDurationString(video.duration));
            loadInfo = new LoadInfo(video.photo_320,
                    (int) (video.photo.get(1).width/widthCoefficient),
                    (int) (video.photo.get(1).height/widthCoefficient/4*3));
            loadArrays.mLoadInfos.add(loadInfo);
            loadArrays.mImageViews.add(imageView);

            if(topBound - bottomBound == 1) {
                container.addView(imageView, getValidViewPosition(container),
                        getPhotoLayoutParams(loadInfo));
                break;
            }
            else
                smallContainer.addView(imageView, getPhotoLayoutParams(loadInfo));

            if(i == topBound-1)
                container.addView(smallContainer, getValidViewPosition(container));
        }
        return loadArrays;
    }

    public static void putAudioAttachment(@NonNull final VKList<VKApiAudio> apiAudios, ViewGroup container){
        final Context context = container.getContext();
        Drawable audioDrawable = container.getResources().getDrawable(R.drawable.ic_menu_slideshow);
        final ArrayList<TextView> audioNames = new ArrayList<>();
        for (VKApiAudio apiAudio : apiAudios){
            ProgramHolders.AudioHolder holder =
                    new ProgramHolders.AudioHolder(context, apiAudio, audioDrawable);
            container.addView(holder.getView(),
                    getValidViewPosition(container));
            audioNames.add(holder.getTextViewTitle());
        }

        new AsyncTask<Object, Object, Object>(){
            final ArrayList<CharSequence> mSequenceArrayList = new ArrayList<>();
            @Override
            protected Object doInBackground(Object... params) {
                for(VKApiAudio apiAudio : apiAudios){
                    mSequenceArrayList.add(ProgramHolders.AudioHolder.setUpName(apiAudio, context));
                }
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                for(int i = 0; i < mSequenceArrayList.size(); i++){
                    audioNames.get(i).setText(mSequenceArrayList.get(i));
                }
            }
        }.execute();
    }

    public static void putLinkAttachment(@NonNull VKList<VKApiLink> links, ViewGroup container){
        Context context = container.getContext();
        AttachmentHolders.LinkHolder holder;
        View view;
        for (VKApiLink link : links){
            view = ((MainActivity) context).getLayoutInflater()
                    .inflate(R.layout.list_item_link, container, false);
            holder = new AttachmentHolders.LinkHolder(view, link);
            view.setTag(holder);
            container.addView(view, getValidViewPosition(container));
        }
    }

    public static void putPostAttachment(@NonNull VKList<VKApiPost> posts,
                                         UsersAndGroups usersAndGroups,
                                         ContainerLayout container, boolean showFullInfo){

        for(VKApiPost post : posts){
            putPostAttachment(post, usersAndGroups, container, showFullInfo);
        }
    }

    private static void putPostAttachment(VKApiPost post, UsersAndGroups usersAndGroups,
                                          ContainerLayout container, boolean showFullInfo){
        new AttachmentHolders.PostHolder(container, post, usersAndGroups,
                showFullInfo);
    }
}
