package com.duyp.architecture.mvvm.dagger;

import android.content.Context;

import com.duyp.androidutils.CustomSharedPreferences;
import com.duyp.architecture.mvvm.data.UserDataStore;
import com.duyp.architecture.mvvm.data.UserManager;
import com.duyp.architecture.mvvm.data.remote.GithubService;
import com.duyp.architecture.mvvm.local.RealmDatabase;
import com.duyp.architecture.mvvm.utils.qualifier.ApplicationContext;
import com.google.gson.Gson;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;
import io.realm.RealmConfiguration;

import static org.powermock.api.mockito.PowerMockito.mock;

/**
 * Created by duypham on 10/21/17.
 *
 */

@Module
public class TestDataModule {

    public TestDataModule() {}

    @Provides
    @Singleton
    UserDataStore provideUserRepo() {
        return mock(UserDataStore.class);
    }

    @Provides
    @Singleton
    UserManager provideUserManager() {
        return mock(UserManager.class);
    }

    @Provides
    @Singleton
    RealmDatabase provideRealmDatabase() {
        return mock(RealmDatabase.class);
    }

}
