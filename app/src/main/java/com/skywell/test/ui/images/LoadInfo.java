package com.skywell.test.ui.images;

import android.os.Parcel;
import android.os.Parcelable;


public class LoadInfo implements Parcelable {

    public final String url;
    public int width;
    public int height;

    public LoadInfo(String url, int width, int height) {
        this.url = url;
        this.width = width;
        this.height = height;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.url);
        dest.writeInt(this.width);
        dest.writeInt(this.height);
    }

    private LoadInfo(Parcel in) {
        this.url = in.readString();
        this.width = in.readInt();
        this.height = in.readInt();
    }

    public static final Creator<LoadInfo> CREATOR = new Creator<LoadInfo>() {
        @Override
        public LoadInfo createFromParcel(Parcel source) {
            return new LoadInfo(source);
        }

        @Override
        public LoadInfo[] newArray(int size) {
            return new LoadInfo[size];
        }
    };
}
