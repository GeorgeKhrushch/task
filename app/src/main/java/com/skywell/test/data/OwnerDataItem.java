package com.skywell.test.data;

public class OwnerDataItem {

    private String mName;
    private String mValue;

    public OwnerDataItem(String name, String value) {
        mName = name;
        mValue = value;
    }

    public String getName() {
        return mName;
    }

    public String getValue() {
        return mValue;
    }
}
