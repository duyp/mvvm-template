package com.duyp.architecture.mvvm.dagger;

import com.apollographql.apollo.ApolloClient;
import com.duyp.architecture.mvvm.data.remote.GithubService;
import com.duyp.architecture.mvvm.data.remote.IssueService;
import com.duyp.architecture.mvvm.data.remote.OrganizationService;
import com.duyp.architecture.mvvm.data.remote.RepoService;
import com.duyp.architecture.mvvm.data.remote.SearchService;
import com.duyp.architecture.mvvm.data.remote.UserRestService;

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

    @Provides
    @Singleton
    static UserRestService provideUserRestService() {
        return mock(UserRestService.class);
    }

    @Provides
    @Singleton
    static OrganizationService provideOrganizationService() {
        return mock(OrganizationService.class);
    }

    @Provides
    @Singleton
    static RepoService provideRepoService() {
        return mock(RepoService.class);
    }

    @Provides
    @Singleton
    static IssueService provideIssueService() {
        return mock(IssueService.class);
    }

    @Provides
    @Singleton
    static SearchService provideSearchService() {
        return mock(SearchService.class);
    }

    @Provides
    @Singleton
    static ApolloClient provideApolloClient() {
        return mock(ApolloClient.class);
    }
}
