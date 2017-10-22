package com.duyp.architecture.mvvm.injection;

import android.app.Application;
import android.content.Context;

import com.duyp.androidutils.CustomSharedPreferences;
import com.duyp.architecture.mvvm.MyApplication;
import com.duyp.architecture.mvvm.data.local.Constants;
import com.duyp.architecture.mvvm.data.provider.RestHelper;
import com.duyp.architecture.mvvm.injection.view_model.ViewModelModule;
import com.duyp.architecture.mvvm.data.provider.GsonProvider;
import com.duyp.architecture.mvvm.injection.qualifier.ApplicationContext;
import com.google.gson.Gson;
import com.squareup.leakcanary.RefWatcher;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by air on 4/30/17.
 * Module for app component
 */

@Module(includes = ViewModelModule.class)
class AppModule {

    AppModule() {}

    @Provides
    @Singleton
    @ApplicationContext
    Context provideContext(Application application) {
        return application;
    }

    @Provides
    @Singleton
    static Gson provideGson() {
        Gson gson = GsonProvider.makeGsonForRealm();
        RestHelper.initGson(gson);
        return gson;
    }

    @Provides
    @Singleton
    CustomSharedPreferences provideMySharedPreferences(@ApplicationContext Context context) {
        return CustomSharedPreferences.getInstance(context, Constants.PREF_FILE_NAME);
    }

    @Provides
    @Singleton
    EventBus provideEventBus() {
        return EventBus.getDefault();
    }

    @Provides
    @Singleton
    RefWatcher provideRefWatcher() {
        return MyApplication.getInstance().getRefWatcher();
    }
}