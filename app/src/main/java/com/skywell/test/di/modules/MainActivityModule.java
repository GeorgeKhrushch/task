package com.skywell.test.di.modules;

import com.skywell.test.MainActivity;
import com.skywell.test.data.api.Authorization;
import com.skywell.test.di.scopes.MainActivityScope;
import com.skywell.test.ui.views.ViewUtilities;

import dagger.Module;
import dagger.Provides;

@Module
public class MainActivityModule {

    private MainActivity mActivity;

    public MainActivityModule(MainActivity application) {
        this.mActivity = application;
    }

    @Provides
    @MainActivityScope
    public MainActivity setUpActivity() {
        ViewUtilities.setScreenDimentions(mActivity);
        return mActivity;
    }

    @Provides
    @MainActivityScope
    public Authorization provideAnalyticsManager(MainActivity activity) {
        Authorization authorization = new Authorization(activity);
        authorization.startAuthentication();
        return authorization;
    }
}