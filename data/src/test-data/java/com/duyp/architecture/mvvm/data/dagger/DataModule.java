package com.duyp.architecture.mvvm.data.dagger;

import android.content.Context;

import com.duyp.architecture.mvvm.data.UserDataStore;
import com.duyp.architecture.mvvm.data.UserManager;
import com.duyp.architecture.mvvm.data.remote.GithubService;
import com.duyp.architecture.mvvm.local.RealmDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import static org.powermock.api.mockito.PowerMockito.mock;

/**
 * Created by duypham on 10/17/17.
 *
 */

@Module
public class DataModule {

    private final RealmDatabase mockRealmDatabase;
    private final UserDataStore mockUserDataStore;

    public DataModule(RealmDatabase mockRealmDatabase, UserDataStore userDataStore) {
        this.mockRealmDatabase = mockRealmDatabase;
        this.mockUserDataStore = userDataStore;
    }

    @Provides
    @Singleton
    UserDataStore provideUserDataStore() {
        return mockUserDataStore;
    }

    @Provides
    @Singleton
    UserManager provideUserManager(GithubService githubService) {
        return new UserManager(mock(Context.class), mockUserDataStore, githubService);
    }

    @Provides
    @Singleton
    RealmDatabase provideRealmDatabase() {
        return mockRealmDatabase;
    }

    @Provides
    @Singleton
    GithubService provideGithubService() {
        return mock(GithubService.class);
    }
}
