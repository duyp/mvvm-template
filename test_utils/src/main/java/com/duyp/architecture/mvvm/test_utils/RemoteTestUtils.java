package com.duyp.architecture.mvvm.test_utils;

import io.reactivex.Single;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.BufferedSource;
import retrofit2.HttpException;
import retrofit2.Response;

/**
 * Created by duypham on 10/17/17.
 *
 */

public class RemoteTestUtils {

    public static <T> Single<Response<T>> successResponse(T data) {
        return Single.just(Response.success(data));
    }

    public static <T> Single<Response<T>> errorResponse(int httpCode) {
        return Single.just(Response.error(httpCode, emptyResponseBody()));
    }

    private static ResponseBody emptyResponseBody() {
        return new ResponseBody() {
            @Override
            public MediaType contentType() {
                return null;
            }

            @Override
            public long contentLength() {
                return 0;
            }

            @Override
            public BufferedSource source() {
                return null;
            }
        };
    }
}
