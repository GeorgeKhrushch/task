package com.skywell.test.di.components;

import com.skywell.test.di.modules.FragmentNewsModule;
import com.skywell.test.di.scopes.FragmentNewsScope;
import com.skywell.test.ui.fragments.FragmentNews;

import dagger.Subcomponent;

@FragmentNewsScope
@Subcomponent(modules = FragmentNewsModule.class)

public interface FragmentNewsComponent {

    void inject(FragmentNews fragmentNews);
}