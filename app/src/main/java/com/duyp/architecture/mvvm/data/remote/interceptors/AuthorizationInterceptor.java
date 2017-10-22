package com.duyp.architecture.mvvm.data.remote.interceptors;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.duyp.architecture.mvvm.data.provider.ServiceFactory;
import com.duyp.architecture.mvvm.data.remote.RemoteConstants;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by duypham on 10/21/17.
 *
 */

public class AuthorizationInterceptor implements Interceptor {

    private final ServiceFactory.UserTokenProducer tokenProducer;

    public AuthorizationInterceptor(@NonNull ServiceFactory.UserTokenProducer tokenProducer) {
        this.tokenProducer = tokenProducer;
    }

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request();
        Request.Builder requestBuilder = request.newBuilder();

        if (TextUtils.isEmpty(request.header("Accept"))) {
            // github accept header
            // https://developer.github.com/v3/#current-version
            requestBuilder.addHeader("Accept", "application/vnd.github.v3+json");
        }

        String header = request.header(RemoteConstants.HEADER_AUTH);

        String token;
        if (TextUtils.isEmpty(header) && !(token = tokenProducer.getUserToken()).isEmpty()) {
            requestBuilder.addHeader(RemoteConstants.HEADER_AUTH, token);
        }
        return chain.proceed(requestBuilder.build());
    }
}
