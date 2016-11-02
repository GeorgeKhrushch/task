package com.skywell.test.data.attachments;

import android.view.ViewGroup;

import com.skywell.test.R;
import com.skywell.test.ui.views.ContainerLayout;
import com.vk.sdk.api.model.SortedAttachments;
import com.vk.sdk.api.model.VKAttachments;

public class PasteAttachment {

    public static void pasteTopAttachments(VKAttachments attachments, ContainerLayout linearLayout,
                                           UsersAndGroups usersAndGroups){
        putSortedAttachments(SortedAttachments.getSortedAttachments(attachments),
                linearLayout, usersAndGroups);
    }

    public static void putSortedAttachments(SortedAttachments sortedAttachments,
                                            ContainerLayout linearLayout,
                                            UsersAndGroups usersAndGroups){
        if(sortedAttachments == null)
            return;

        ((ViewGroup.MarginLayoutParams)linearLayout.getLayoutParams()).topMargin =
                (int) linearLayout.getResources().getDimension(R.dimen.news_padding);

        if(sortedAttachments.mPhotos != null)
            GraphicalAttachments.putPhotosAttachments(sortedAttachments.mPhotos, linearLayout);
        if(sortedAttachments.mVideos != null)
            GraphicalAttachments.putVideoAttachment(sortedAttachments.mVideos, linearLayout);
        if(sortedAttachments.mAudios != null)
            GraphicalAttachments.putAudioAttachment(sortedAttachments.mAudios, linearLayout);
        if(sortedAttachments.mLinks != null)
            GraphicalAttachments.putLinkAttachment(sortedAttachments.mLinks, linearLayout);
        if(sortedAttachments.mPosts != null)
            GraphicalAttachments.putPostAttachment(sortedAttachments.mPosts,
                    usersAndGroups, linearLayout, true);
    }
}
