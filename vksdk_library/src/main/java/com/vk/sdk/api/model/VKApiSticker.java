package com.vk.sdk.api.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import static com.vk.sdk.api.model.VKAttachments.TYPE_STICKER;

/**
 * Created by admin on 25.08.2015.
 */
public class VKApiSticker extends VKAttachments.VKApiAttachment implements Parcelable, Identifiable {
    private int id;
    private int product_id;
    private String photo_64;
    private String photo_128;
    public String photo_256;
    private String photo_352;
    private String photo_512;
    private VKPhotoSizes src = new VKPhotoSizes();
    public int width;
    public int height;

    public VKApiSticker(){

    }

    @Override
    public CharSequence toAttachmentString() {
        return null;
    }

    @Override
    public String getType() {
        return TYPE_STICKER;
    }

    public VKApiSticker parse(JSONObject from) {

        height = from.optInt("height");
        width = from.optInt("width");
        id = from.optInt("id");
        product_id = from.optInt("product_id");

        photo_64 = from.optString("photo_64");
        photo_128 = from.optString("photo_128");
        photo_256 = from.optString("photo_256");
        photo_352 = from.optString("photo_352");
        photo_512 = from.optString("photo_512");



        src.setOriginalDimension(width, height);
        JSONArray photo_sizes = from.optJSONArray("sizes");
        if(photo_sizes != null) {
            src.fill(photo_sizes);
        } else {
            if(!TextUtils.isEmpty(photo_64)) {
                src.add(VKApiPhotoSize.create(photo_64, VKApiPhotoSize.S, width, height));
            }
            if(!TextUtils.isEmpty(photo_128)) {
                src.add(VKApiPhotoSize.create(photo_128, VKApiPhotoSize.M, width, height));
            }
            if(!TextUtils.isEmpty(photo_256)) {
                src.add(VKApiPhotoSize.create(photo_256, VKApiPhotoSize.X, width, height));
            }
            if(!TextUtils.isEmpty(photo_352)) {
                src.add(VKApiPhotoSize.create(photo_352, VKApiPhotoSize.Y, width, height));
            }
            if(!TextUtils.isEmpty(photo_512)) {
                src.add(VKApiPhotoSize.create(photo_512, VKApiPhotoSize.Z, width, height));
            }

            src.sort();
        }
        return this;
    }

    public VKApiSticker(Parcel in) {
        this.id = in.readInt();
        this.product_id = in.readInt();
        this.width = in.readInt();
        this.height = in.readInt();
        this.src = in.readParcelable(VKPhotoSizes.class.getClassLoader());
        this.photo_64 = in.readString();
        this.photo_128 = in.readString();
        this.photo_256 = in.readString();
        this.photo_352 = in.readString();
        this.photo_512 = in.readString();

    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.product_id);
        dest.writeInt(this.width);
        dest.writeInt(this.height);
        dest.writeParcelable(this.src, flags);
        dest.writeString(this.photo_64);
        dest.writeString(this.photo_128);
        dest.writeString(this.photo_256);
        dest.writeString(this.photo_352);
        dest.writeString(this.photo_512);
    }

    @Override
    public int getId() {
        return id;
    }

    public static Creator<VKApiSticker> CREATOR = new Creator<VKApiSticker>() {
        public VKApiSticker createFromParcel(Parcel source) {
            return new VKApiSticker(source);
        }

        public VKApiSticker[] newArray(int size) {
            return new VKApiSticker[size];
        }
    };
}
