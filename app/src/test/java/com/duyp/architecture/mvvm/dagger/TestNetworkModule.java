package com.duyp.architecture.mvvm.dagger;

import com.duyp.architecture.mvvm.data.remote.GithubService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import static org.powermock.api.mockito.PowerMockito.mock;
/**
 * Created by duypham on 10/21/17.
 *
 */

@Module
public class TestNetworkModule {
    @Provides
    @Singleton
    static GithubService provideGithubService() {
        return mock(GithubService.class);
    }
}
