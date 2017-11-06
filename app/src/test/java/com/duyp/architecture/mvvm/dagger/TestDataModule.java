package com.duyp.architecture.mvvm.dagger;

import com.duyp.architecture.mvvm.data.local.RealmDatabase;
import com.duyp.architecture.mvvm.data.local.user.UserDataStore;
import com.duyp.architecture.mvvm.data.local.user.UserManager;
import com.duyp.architecture.mvvm.data.provider.GsonProvider;
import com.duyp.architecture.mvvm.helper.RestHelper;
import com.google.gson.Gson;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
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

    @Provides
    @Singleton
    RealmConfiguration provideRealmConfiguration() {
        return mock(RealmConfiguration.class);
    }
}
