package com.duyp.architecture.mvvm.local.dagger;

import android.content.Context;

import com.duyp.androidutils.CustomSharedPreferences;
import com.duyp.architecture.mvvm.injection.qualifier.ApplicationContext;
import com.duyp.architecture.mvvm.local.RealmDatabase;
import com.duyp.architecture.mvvm.local.UserDataStore;
import com.google.gson.Gson;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by duypham on 9/21/17.
 * Test module for data module
 */

@Module
public class TestDataModule {

    private final Context mContext;

    public TestDataModule(@ApplicationContext Context context) {
        mContext = context;
    }

    @Provides
    @Singleton
    UserDataStore provideUserRepo(CustomSharedPreferences sharedPreferences, Gson gson, RealmDatabase database) {
        return new UserDataStore(sharedPreferences, gson, database);
    }

    @Provides
    @Singleton
    RealmConfiguration provideRealmConfiguration() {
        Realm.init(mContext);
        return new RealmConfiguration.Builder().inMemory().name("test-realm").build();
    }

    @Provides
    @Singleton
    RealmDatabase provideRealmDatabase(RealmConfiguration realm) {
        return new RealmDatabase(realm);
    }
}
