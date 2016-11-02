package com.vk.sdk.api.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by admin on 02.07.2016.
 */
public class SortedAttachments implements Parcelable {
    public VKList<VKApiPhoto> mPhotos;
    public VKList<VKApiAudio> mAudios;
    public VKList<VKApiVideo> mVideos;
    public VKList<VKApiDocument> mDocuments;
    public VKList<VKApiLink> mLinks;
    private VKList<VKApiNote> mNotes;
    public VKList<VKApiSticker> mStickers;
    public VKList<VKApiGift> mGifts;
    public VKList<VKApiPost> mPosts;
    private VKList<VKApiWikiPage> mWikiPages;

    private VKList checkForNull(VKList list){
        if(list == null)
            list = new VKList<>();
        return list;
    }
    private void pushAttachment(VKAttachments.VKApiAttachment apiAttachment){
        switch (apiAttachment.getType()){
            case VKAttachments.TYPE_POST:{
                mPosts = checkForNull(mPosts);
                mPosts.add((VKApiPost) apiAttachment);
                break;
            }
            case VKAttachments.TYPE_PHOTO:{
                mPhotos = checkForNull(mPhotos);
                mPhotos.add((VKApiPhoto) apiAttachment);
                break;
            }
            case VKAttachments.TYPE_VIDEO:{
                mVideos = checkForNull(mVideos);
                mVideos.add((VKApiVideo) apiAttachment);
                break;
            }
            case VKAttachments.TYPE_AUDIO:{
                mAudios = checkForNull(mAudios);
                mAudios.add((VKApiAudio) apiAttachment);
                break;
            }
            case VKAttachments.TYPE_LINK:{
                mLinks = checkForNull(mLinks);
                mLinks.add((VKApiLink) apiAttachment);
                break;
            }
            case VKAttachments.TYPE_DOC:{
                mDocuments = checkForNull(mDocuments);
                mDocuments.add((VKApiDocument) apiAttachment);
                break;
            }
            case VKAttachments.TYPE_GIFT:{
                mGifts = checkForNull(mGifts);
                mGifts.add((VKApiGift) apiAttachment);
                break;
            }
            case VKAttachments.TYPE_STICKER:{
                mStickers = checkForNull(mStickers);
                mStickers.add((VKApiSticker) apiAttachment);
                break;
            }
            case VKAttachments.TYPE_WIKI_PAGE:{
                mWikiPages = checkForNull(mWikiPages);
                mWikiPages.add((VKApiWikiPage) apiAttachment);
                break;
            }
            case VKAttachments.TYPE_NOTE:{
                mNotes = checkForNull(mNotes);
                mNotes.add((VKApiNote) apiAttachment);
                break;
            }
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.mPhotos, flags);
        dest.writeParcelable(this.mAudios, flags);
        dest.writeParcelable(this.mVideos, flags);
        dest.writeParcelable(this.mDocuments, flags);
        dest.writeParcelable(this.mLinks, flags);
        dest.writeParcelable(this.mNotes, flags);
        dest.writeParcelable(this.mStickers, flags);
        dest.writeParcelable(this.mGifts, flags);
        dest.writeParcelable(this.mPosts, flags);
        dest.writeParcelable(this.mWikiPages, flags);
    }

    public SortedAttachments() {
    }

    SortedAttachments(Parcel in) {
        this.mPhotos = in.readParcelable(VKList.class.getClassLoader());
        this.mAudios = in.readParcelable(VKList.class.getClassLoader());
        this.mVideos = in.readParcelable(VKList.class.getClassLoader());
        this.mDocuments = in.readParcelable(VKList.class.getClassLoader());
        this.mLinks = in.readParcelable(VKList.class.getClassLoader());
        this.mNotes = in.readParcelable(VKList.class.getClassLoader());
        this.mStickers = in.readParcelable(VKList.class.getClassLoader());
        this.mGifts = in.readParcelable(VKList.class.getClassLoader());
        this.mPosts = in.readParcelable(VKList.class.getClassLoader());
        this.mWikiPages = in.readParcelable(VKList.class.getClassLoader());
    }

    public static final Parcelable.Creator<SortedAttachments> CREATOR = new Parcelable.Creator<SortedAttachments>() {
        @Override
        public SortedAttachments createFromParcel(Parcel source) {
            return new SortedAttachments(source);
        }

        @Override
        public SortedAttachments[] newArray(int size) {
            return new SortedAttachments[size];
        }
    };

    public static SortedAttachments getSortedAttachments(VKAttachments apiAttachments){
        SortedAttachments sortedAttachments = new SortedAttachments();
        for(VKAttachments.VKApiAttachment attachment : apiAttachments){
            sortedAttachments.pushAttachment(attachment);
        }

        return sortedAttachments;
    }
}
