package com.skywell.test.di.modules;

import com.skywell.test.data.api.RequestManager;
import com.skywell.test.data.data_base.DBHelper;
import com.skywell.test.di.scopes.FragmentNewsScope;
import com.skywell.test.ui.fragments.FragmentNews;
import com.skywell.test.ui.presenters.PresenterFragmentNews;

import dagger.Module;
import dagger.Provides;

@Module
public class FragmentNewsModule {

    private FragmentNews mFragmentNews;

    public FragmentNewsModule(FragmentNews fragmentNews) {
        this.mFragmentNews = fragmentNews;
    }

    @Provides
    @FragmentNewsScope
    public FragmentNews provideFragment() {
        return mFragmentNews;
    }

    @Provides
    @FragmentNewsScope
    public PresenterFragmentNews providePresenter(RequestManager requestManager, DBHelper helper){
        return new PresenterFragmentNews(mFragmentNews, requestManager, helper);
    }
}