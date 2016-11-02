package com.skywell.test.di.components;

import com.skywell.test.MainActivity;
import com.skywell.test.di.modules.MainActivityModule;
import com.skywell.test.di.scopes.MainActivityScope;

import dagger.Subcomponent;

@MainActivityScope
@Subcomponent(modules = MainActivityModule.class)

public interface MainActivityComponent {
    void inject(MainActivity mainActivity);
}