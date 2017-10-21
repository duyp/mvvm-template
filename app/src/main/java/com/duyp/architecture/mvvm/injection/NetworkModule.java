package com.duyp.architecture.mvvm.injection;

import android.content.Context;

import com.duyp.architecture.mvvm.data.UserDataStore;
import com.duyp.architecture.mvvm.data.remote.GithubService;
import com.duyp.architecture.mvvm.data.remote.ServiceFactory;
import com.duyp.architecture.mvvm.utils.qualifier.ApplicationContext;
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
}
