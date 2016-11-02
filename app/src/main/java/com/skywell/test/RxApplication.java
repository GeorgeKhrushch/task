package com.skywell.test;

import android.app.Application;
import android.content.Context;

import com.skywell.test.di.components.AppComponent;
import com.skywell.test.di.components.DaggerAppComponent;
import com.skywell.test.di.modules.AppModule;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKAccessTokenTracker;
import com.vk.sdk.VKSdk;

public class RxApplication extends Application {
    private AppComponent appComponent;

    private final VKAccessTokenTracker vkAccessTokenTracker = new VKAccessTokenTracker() {
        @Override
        public void onVKAccessTokenChanged(VKAccessToken oldToken, VKAccessToken newToken) {
            if (newToken == null) {
            }
        }
    };

    public static RxApplication get(Context context) {
        return (RxApplication) context.getApplicationContext();
    }

    private void initAppComponent() {
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        vkAccessTokenTracker.startTracking();
        VKSdk.customInitialize(this, 5704486, "5.52");
        initAppComponent();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
