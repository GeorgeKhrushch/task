package com.skywell.test.di.modules;

import android.app.Application;

import com.skywell.test.data.data_base.DBHelper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    private Application application;

    public AppModule(Application application) {
        this.application = application;
    }

    @Provides
    @Singleton
    public Application provideApplication() {
        return application;
    }

    @Provides
    @Singleton
    public DBHelper provideDBHelper(Application application) {
        return new DBHelper(application);
    }
}