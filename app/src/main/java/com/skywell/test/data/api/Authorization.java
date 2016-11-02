package com.skywell.test.data.api;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;
import com.vk.sdk.util.VKUtil;

import javax.inject.Inject;

public class Authorization {

    private final Context mContext;

    @Inject
    public Authorization(Context context){
        mContext = context;
    }

    private static final String[] sMyScope = new String[] {
            VKScope.PHOTOS,
            VKScope.MESSAGES,
            VKScope.GROUPS,
            VKScope.FRIENDS,
            VKScope.VIDEO,
            VKScope.NOTIFICATIONS,
            VKScope.AUDIO,
            VKScope.WALL,
            VKScope.DOCS
    };

    public void startAuthentication() {
        String[] fingerprint = VKUtil.getCertificateFingerprint(mContext, mContext.getPackageName());

        for (String aFingerprint : fingerprint) {
            Log.d("FingerPrint ", aFingerprint);
        }

        if (!VKSdk.wakeUpSession(mContext)) {
            VKSdk.login((Activity) mContext, sMyScope);
        }
    }
}
