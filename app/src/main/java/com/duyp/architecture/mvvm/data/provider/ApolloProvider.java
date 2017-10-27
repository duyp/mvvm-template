package com.duyp.architecture.mvvm.data.provider;

import com.apollographql.apollo.ApolloClient;
import com.duyp.architecture.mvvm.BuildConfig;

import okhttp3.OkHttpClient;

/**
 * Created by duypham on 10/27/17.
 */

public class ApolloProvider {

    public static ApolloClient getApollo(OkHttpClient okHttpClient) {
        return ApolloClient.builder()
                .serverUrl(BuildConfig.REST_URL + "graphql")
                .okHttpClient(okHttpClient)
                .build();
    }
}
