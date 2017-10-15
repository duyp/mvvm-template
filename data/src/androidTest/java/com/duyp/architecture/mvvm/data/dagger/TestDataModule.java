package com.duyp.architecture.mvvm.data.dagger;

import android.content.Context;

import com.duyp.androidutils.CustomSharedPreferences;
import com.duyp.architecture.mvvm.data.local.RealmDatabase;
import com.duyp.architecture.mvvm.data.local.UserDataStore;
import com.duyp.architecture.mvvm.injection.qualifier.ApplicationContext;
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
    protected Realm provideRealm() {
        Realm.init(mContext);
        RealmConfiguration configuration = new RealmConfiguration.Builder().inMemory().name("test-realm").build();
        return Realm.getInstance(configuration);
    }

    @Provides
    @Singleton
    RealmDatabase provideRealmDatabase(Realm realm) {
        return new RealmDatabase(realm);
    }
}
