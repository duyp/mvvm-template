package com.duyp.architecture.mvvm.injection.module;

import android.app.Application;
import android.content.Context;

import com.duyp.androidutils.CustomSharedPreferences;
import com.duyp.architecture.mvp.app.Constants;
import com.duyp.architecture.mvp.dagger.qualifier.ApplicationContext;
import com.duyp.architecture.mvp.data.remote.ServiceFactory;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by air on 4/30/17.
 * Module for app component
 */

@Module
public class AppModule {

    protected Application application;

    public AppModule(Application application) {
        this.application = application;
    }

    @Provides
    @Singleton
    @ApplicationContext
    Context provideContext() {
        return application;
    }

    @Provides
    @Singleton
    static Gson provideGson() {
        return ServiceFactory.makeGsonForRealm();
    }

    @Provides
    @Singleton
    CustomSharedPreferences provideMySharedPreferences(@ApplicationContext Context context) {
        return CustomSharedPreferences.getInstance(context, Constants.PREF_NAME);
    }

    @Provides
    @Singleton
    EventBus provideEventBus() {
        return EventBus.getDefault();
    }
}