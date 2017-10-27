package com.duyp.architecture.mvvm.injection;

import android.content.Context;

import com.apollographql.apollo.ApolloClient;
import com.duyp.architecture.mvvm.data.local.user.UserDataStore;
import com.duyp.architecture.mvvm.data.provider.ApolloProvider;
import com.duyp.architecture.mvvm.data.remote.GithubService;
import com.duyp.architecture.mvvm.data.provider.ServiceFactory;
import com.duyp.architecture.mvvm.data.remote.IssueService;
import com.duyp.architecture.mvvm.data.remote.OrganizationService;
import com.duyp.architecture.mvvm.data.remote.RepoService;
import com.duyp.architecture.mvvm.data.remote.UserRestService;
import com.duyp.architecture.mvvm.injection.qualifier.ApplicationContext;
import com.google.gson.Gson;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;

/**
 * Created by Duy Pham on 4/30/17.
 * This module provide net work dependencies: OkHttpClient and Retrofit service
 */

@Module
public class NetworkModule {


    public NetworkModule() {}

    @Provides
    @Singleton
    static OkHttpClient provideOkHttpClientNoAuth(@ApplicationContext Context context, UserDataStore userDataStore) {
        return ServiceFactory.makeOkHttpClientBuilder(context, userDataStore::getUserToken).build();
    }

    @Provides
    @Singleton
    static GithubService provideGithubService(Gson gson, OkHttpClient okHttpClient) {
        return ServiceFactory.makeService(GithubService.class, gson, okHttpClient);
    }

    @Provides
    @Singleton
    static IssueService issueService(Gson gson, OkHttpClient okHttpClient) {
        return ServiceFactory.makeService(IssueService.class, gson, okHttpClient);
    }

    @Provides
    @Singleton
    static RepoService repoService(Gson gson, OkHttpClient okHttpClient) {
        return ServiceFactory.makeService(RepoService.class, gson, okHttpClient);
    }

    @Provides
    @Singleton
    static UserRestService userRestService(Gson gson, OkHttpClient okHttpClient) {
        return ServiceFactory.makeService(UserRestService.class, gson, okHttpClient);
    }

    @Provides
    @Singleton
    static OrganizationService organizationService(Gson gson, OkHttpClient okHttpClient) {
        return ServiceFactory.makeService(OrganizationService.class, gson, okHttpClient);
    }

    @Provides
    @Singleton
    static ApolloClient provideApolloClient(OkHttpClient okHttpClient) {
        return ApolloProvider.getApollo(okHttpClient);
    }
}
