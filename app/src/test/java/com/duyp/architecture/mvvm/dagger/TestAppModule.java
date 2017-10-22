package com.duyp.architecture.mvvm.dagger;

import android.app.Application;
import android.content.Context;

import com.duyp.androidutils.CustomSharedPreferences;
import com.duyp.architecture.mvvm.App;
import com.duyp.architecture.mvvm.data.local.Constants;
import com.duyp.architecture.mvvm.data.provider.GsonProvider;
import com.duyp.architecture.mvvm.injection.qualifier.ApplicationContext;
import com.google.gson.Gson;
import com.squareup.leakcanary.RefWatcher;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by duypham on 10/23/17.
 * test module for {@link com.duyp.architecture.mvvm.injection.AppModule}
 */

@Module
public class TestAppModule {

    @Provides
    @Singleton
    @ApplicationContext
    Context provideContext(TestApplication application) {
        return application;
    }

    @Provides
    @Singleton
    static Gson provideGson() {
        return GsonProvider.makeGsonForRealm();
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
        return App.getInstance().getRefWatcher();
    }
}
