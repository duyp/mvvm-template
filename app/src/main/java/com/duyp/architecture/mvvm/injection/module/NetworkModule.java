package com.duyp.architecture.mvvm.injection.module;

import android.content.Context;

import com.duyp.architecture.mvp.dagger.qualifier.ApplicationContext;
import com.duyp.architecture.mvp.dagger.qualifier.OkHttpNoAuth;
import com.duyp.architecture.mvp.data.local.user.UserDataStore;
import com.duyp.architecture.mvp.data.remote.GithubService;
import com.duyp.architecture.mvp.data.remote.ServiceFactory;
import com.google.gson.Gson;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;

/**
 * Created by Duy Pham on 4/30/17.
 * Network modules for {@link com.duyp.architecture.mvp.dagger.component.AppComponent}
 */

@Module
public class NetworkModule {

    protected Context context;

    public NetworkModule(Context context) {
        this.context = context;
    }

    @Provides
    @OkHttpNoAuth
    @Singleton
    static OkHttpClient provideOkHttpClientNoAuth(@ApplicationContext Context context, UserDataStore userDataStore) {
        return ServiceFactory.makeOkHttpClientBuilder(context, userDataStore).build();
    }

    @Provides
    @Singleton
    static GithubService provideGithubService(Gson gson, @OkHttpNoAuth OkHttpClient okHttpClient) {
        return ServiceFactory.makeService(GithubService.class, gson, okHttpClient);
    }
}
