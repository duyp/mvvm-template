package com.duyp.architecture.mvvm.data.remote.interceptors;

import android.support.annotation.NonNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by duypham on 10/24/17.
 *
 */

public class ContentTypeInterceptor implements Interceptor {

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request();
        return chain.proceed(request.newBuilder()
                .addHeader("Accept", "application/vnd.github.v3+json")
                .addHeader("Content-type", "application/vnd.github.v3+json")
                .method(request.method(), request.body())
                .build());
    }
}
