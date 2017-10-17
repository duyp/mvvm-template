package com.duyp.architecture.mvvm.data.dagger;

import android.content.Context;

import com.duyp.androidutils.CustomSharedPreferences;
import com.duyp.architecture.mvvm.data.TestApplication;
import com.duyp.architecture.mvvm.data.TestConstants;
import com.duyp.architecture.mvvm.utils.qualifier.ApplicationContext;
import com.duyp.architecture.mvvm.utils.ServiceFactory;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by duypham on 9/21/17.
 *
 */

@Module
public class TestAppModule {

    private final TestApplication application;

    public TestAppModule(TestApplication application) {
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
        // IMPORTANCE to use Test 's shared preference file separated to app Pref file
        // else test functions on shared preferences will affect app shared preferences data
        return CustomSharedPreferences.getInstance(context, TestConstants.TEST_PREF_NAME);
    }

    @Provides
    @Singleton
    EventBus provideEventBus() {
        return EventBus.getDefault();
    }
}
