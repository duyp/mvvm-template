package com.duyp.architecture.mvvm.dagger;

import android.content.Context;

import com.duyp.androidutils.CustomSharedPreferences;
import com.duyp.architecture.mvvm.local.TestApplication;
import com.duyp.architecture.mvvm.local.TestConstants;
import com.duyp.architecture.mvvm.utils.ServiceFactory;
import com.duyp.architecture.mvvm.injection.qualifier.ApplicationContext;
import com.google.gson.Gson;

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
}
