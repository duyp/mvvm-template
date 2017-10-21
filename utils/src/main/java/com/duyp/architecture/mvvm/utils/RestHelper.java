package com.duyp.architecture.mvvm.utils;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.duyp.architecture.mvvm.model.GitHubErrorResponse;
import com.duyp.architecture.mvvm.model.ErrorEntity;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.HttpException;

/**
 * Created by duypham on 10/21/17.
 *
 */

public class RestHelper {

    public static int getErrorCode(Throwable throwable) {
        if (throwable instanceof HttpException) {
            return ((HttpException) throwable).code();

        }
        return -1;
    }

    @Nullable
    public static GitHubErrorResponse getErrorResponse(Gson gson, @NonNull Throwable throwable) {
        ResponseBody body = null;
        if (throwable instanceof HttpException) {
            body = ((HttpException) throwable).response().errorBody();
        }
        if (body != null) {
            try {
                return gson.fromJson(body.string(), GitHubErrorResponse.class);
            } catch (Exception ignored) {}
        }
        return null;
    }

    public static String getPrettifiedErrorMessage(@Nullable Throwable throwable) {
        if (throwable instanceof HttpException) {
            return ErrorEntity.NETWORK_UNAVAILABLE;
        } else if (throwable instanceof IOException) {
            return ErrorEntity.NETWORK_UNAVAILABLE;
        }
        return ErrorEntity.OOPS;
    }
}
