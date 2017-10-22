package com.duyp.architecture.mvvm.dagger;

import com.duyp.architecture.mvvm.data.local.RealmDatabase;
import com.duyp.architecture.mvvm.data.user.UserDataStore;
import com.duyp.architecture.mvvm.data.user.UserManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

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
