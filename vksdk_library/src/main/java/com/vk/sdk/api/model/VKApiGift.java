package com.vk.sdk.api.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import static com.vk.sdk.api.model.VKAttachments.TYPE_GIFT;

/**
 * Created by admin on 25.08.2015.
 */
public class VKApiGift extends VKAttachments.VKApiAttachment implements Parcelable, Identifiable {
    private int id;
    private String thumb_48;
    private String thumb_96;
    public String thumb_256;
    private String thumb_352;
    private String thumb_512;
    private VKPhotoSizes sizes = new VKPhotoSizes();
    private int width;
    private int height;

    public VKApiGift(){

    }

    @Override
    public CharSequence toAttachmentString() {
        return null;
    }

    @Override
    public String getType() {
        return TYPE_GIFT;
    }

    public VKApiGift parse(JSONObject from) {
        height = from.optInt("height");
        width = from.optInt("width");
        id = from.optInt("id");

        thumb_48 = from.optString("thumb_48");
        thumb_96 = from.optString("thumb_96");
        thumb_256 = from.optString("thumb_256");
        thumb_352 = from.optString("thumb_352");
        thumb_512 = from.optString("thumb_512");

        sizes.setOriginalDimension(width, height);
        JSONArray photo_sizes = from.optJSONArray("sizes");
        if(photo_sizes != null) {
            sizes.fill(photo_sizes);
        } else {
            if(!TextUtils.isEmpty(thumb_48)) {
                sizes.add(VKApiPhotoSize.create(thumb_48, VKApiPhotoSize.S, width, height));
            }
            if(!TextUtils.isEmpty(thumb_96)) {
                sizes.add(VKApiPhotoSize.create(thumb_96, VKApiPhotoSize.M, width, height));
            }
            if(!TextUtils.isEmpty(thumb_256)) {
                sizes.add(VKApiPhotoSize.create(thumb_256, VKApiPhotoSize.X, width, height));
            }
            if(!TextUtils.isEmpty(thumb_352)) {
                sizes.add(VKApiPhotoSize.create(thumb_352, VKApiPhotoSize.Y, width, height));
            }
            if(!TextUtils.isEmpty(thumb_512)) {
                sizes.add(VKApiPhotoSize.create(thumb_512, VKApiPhotoSize.Z, width, height));
            }
            sizes.sort();
        }
        return this;
    }

    public VKApiGift(Parcel in) {
        this.id = in.readInt();
        this.width = in.readInt();
        this.height = in.readInt();
        this.sizes = in.readParcelable(VKPhotoSizes.class.getClassLoader());
        this.thumb_48 = in.readString();
        this.thumb_96 = in.readString();
        this.thumb_256 = in.readString();
        this.thumb_352 = in.readString();
        this.thumb_512 = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.width);
        dest.writeInt(this.height);
        dest.writeParcelable(this.sizes, flags);
        dest.writeString(this.thumb_48);
        dest.writeString(this.thumb_96);
        dest.writeString(this.thumb_256);
        dest.writeString(this.thumb_352);
        dest.writeString(this.thumb_512);
    }

    @Override
    public int getId() {
        return id;
    }

    public static Creator<VKApiGift> CREATOR = new Creator<VKApiGift>() {
        public VKApiGift createFromParcel(Parcel source) {
            return new VKApiGift(source);
        }

        public VKApiGift[] newArray(int size) {
            return new VKApiGift[size];
        }
    };
}
