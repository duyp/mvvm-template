package com.duyp.architecture.mvvm.injection.module;

import android.content.Context;

import com.duyp.androidutils.CustomSharedPreferences;
import com.duyp.architecture.mvp.dagger.qualifier.ApplicationContext;
import com.duyp.architecture.mvp.data.local.RealmDatabase;
import com.duyp.architecture.mvp.data.local.user.UserDataStore;
import com.duyp.architecture.mvp.data.local.user.UserManager;
import com.duyp.architecture.mvp.data.remote.GithubService;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

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
        initRealm();
    }

    private void initRealm() {
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
    }

    @Provides
    @Singleton
    UserDataStore provideUserRepo(CustomSharedPreferences sharedPreferences, Gson gson, RealmDatabase database) {
        return new UserDataStore(sharedPreferences, gson, database);
    }

    @Provides
    @Singleton
    UserManager provideUserManager(@ApplicationContext Context context, UserDataStore userDataStore,
                                             EventBus eventBus, GithubService service) {
        return new UserManager(context, userDataStore, eventBus, service);
    }

    @Provides
    @Singleton
    Realm provideRealm() {
        return Realm.getDefaultInstance();
    }

    @Provides
    @Singleton
    RealmDatabase provideRealmDatabase(Realm realm) {
        return new RealmDatabase(realm);
    }
}