package com.skywell.test.di.components;

import com.skywell.test.data.api.NetModule;
import com.skywell.test.di.modules.AppModule;
import com.skywell.test.di.modules.FragmentNewsModule;
import com.skywell.test.di.modules.FragmentProfileModule;
import com.skywell.test.di.modules.MainActivityModule;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {AppModule.class, NetModule.class})
@Singleton
public interface AppComponent {

    MainActivityComponent plus (MainActivityModule mainActivityModule);
    FragmentProfileComponent plus (FragmentProfileModule fragmentProfileModule);
    FragmentNewsComponent plus (FragmentNewsModule fragmentNewsModule);
}