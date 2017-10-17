package com.duyp.architecture.mvvm.data.dagger;

import com.duyp.architecture.mvvm.data.remote.GithubService;
import com.duyp.architecture.mvvm.local.RealmDatabase;

import org.mockito.Mockito;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by duypham on 10/17/17.
 *
 */

@Module
public class DataModule {

    @Provides
    @Singleton
    RealmDatabase provideRealmDatabase() {
        return Mockito.mock(RealmDatabase.class);
    }

    @Provides
    @Singleton
    GithubService provideGithubService() {
        return Mockito.mock(GithubService.class);
    }
}
