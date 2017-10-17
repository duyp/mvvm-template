package com.duyp.architecture.mvvm.data.dagger;

import com.duyp.architecture.mvvm.data.remote.GithubService;
import com.duyp.architecture.mvvm.local.RealmDatabase;

import static org.powermock.api.mockito.PowerMockito.mock;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by duypham on 10/17/17.
 *
 */

@Module
public class DataModule {

    RealmDatabase mockRealmDatabase;

    public DataModule(RealmDatabase mockRealmDatabase) {
        this.mockRealmDatabase = mockRealmDatabase;
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
