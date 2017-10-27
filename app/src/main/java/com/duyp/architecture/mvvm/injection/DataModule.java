package com.duyp.architecture.mvvm.injection;

import android.content.Context;

import com.duyp.androidutils.CustomSharedPreferences;
import com.duyp.architecture.mvvm.data.local.RealmDatabase;
import com.duyp.architecture.mvvm.data.local.daos.UserDetailDao;
import com.duyp.architecture.mvvm.data.local.user.UserDataStore;
import com.duyp.architecture.mvvm.data.local.user.UserManager;
import com.duyp.architecture.mvvm.data.remote.GithubService;
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

    public DataModule() {}

    @Provides
    @Singleton
    UserDataStore provideUserRepo(CustomSharedPreferences sharedPreferences, Gson gson, UserDetailDao dao) {
        return new UserDataStore(sharedPreferences, gson, dao);
    }

    @Provides
    @Singleton
    UserManager provideUserManager(@ApplicationContext Context context, UserDataStore userDataStore, GithubService githubService) {
        return new UserManager(context, userDataStore, githubService);
    }

    @Provides
    @Singleton
    RealmConfiguration provideRealmConfiguration(@ApplicationContext Context context) {
        int schemaVersion = 1; // first version
        Realm.init(context);
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