package com.duyp.architecture.mvvm.utils.test;

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

    public static <T> Single<T> successResponse(T data) {
        return Single.just(data);
    }

    public static <T> Single<T> errorResponse() {
        return errorResponse(444, "error");
    }

    public static <T> Single<T> errorResponse(int code, String message) {
        return Single.error(new HttpException(Response.error(code, errorResponseBody(message))));
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

    private static ResponseBody errorResponseBody(String message) {
        return ResponseBody.create(MediaType.parse("text"), getErrorJson(message));
    }

    public static String getErrorJson(String message) {
        return "{ \"message\": \"" + message + "\" }";
    }
}
