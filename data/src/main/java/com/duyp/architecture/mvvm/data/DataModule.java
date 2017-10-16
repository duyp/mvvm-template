package com.duyp.architecture.mvvm.data;

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
 * Created by duypham on 9/18/17.
 * Data module provider
 */

@Module
public class DataModule {

    private final Context mContext;

    public DataModule(@ApplicationContext Context context) {
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
        int schemaVersion = 1; // first version
        Realm.init(mContext);
        RealmConfiguration realmConfig = new RealmConfiguration.Builder()
                .schemaVersion(schemaVersion)
                .migration((realm, oldVersion, newVersion) -> {
                    // migrate realm here
                })
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfig);
        return realmConfig;
    }

    @Provides
    @Singleton
    RealmDatabase provideRealmDatabase(RealmConfiguration realmConfig) {
        return new RealmDatabase(realmConfig);
    }
}