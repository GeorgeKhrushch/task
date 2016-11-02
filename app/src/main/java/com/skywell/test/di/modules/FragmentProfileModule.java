package com.skywell.test.di.modules;

import com.skywell.test.data.api.RequestManager;
import com.skywell.test.data.data_base.DBHelper;
import com.skywell.test.di.scopes.FragmentProfileScope;
import com.skywell.test.ui.fragments.FragmentProfile;
import com.skywell.test.ui.presenters.PresenterFragmentProfile;

import dagger.Module;
import dagger.Provides;
@Module
public class FragmentProfileModule {

    private FragmentProfile mFragmentProfile;

    public FragmentProfileModule(FragmentProfile fragmentProfile) {
        this.mFragmentProfile = fragmentProfile;
    }

    @Provides
    @FragmentProfileScope
    public FragmentProfile provideFragment() {
        return mFragmentProfile;
    }

    @Provides
    @FragmentProfileScope
    public PresenterFragmentProfile providePresenter(RequestManager requestManager, DBHelper helper){
        return new PresenterFragmentProfile(mFragmentProfile, requestManager, helper);
    }
}