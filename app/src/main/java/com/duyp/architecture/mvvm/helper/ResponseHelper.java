package com.duyp.architecture.mvvm.helper;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.duyp.architecture.mvvm.data.model.ErrorEntity;
import com.duyp.architecture.mvvm.data.model.GitHubErrorResponse;
import com.duyp.architecture.mvvm.data.provider.GsonProvider;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.HttpException;

/**
 * Created by duypham on 10/22/17.
 *
 */

public class ResponseHelper {

    /**
     * Get http error code from {@link Throwable} if it is instance of {@link HttpException}
     * @param throwable input throwable
     * @return http code or -1 if throwable isn't a instance of {@link HttpException}
     */
    public static int getErrorCode(Throwable throwable) {
        if (throwable instanceof HttpException) {
            return ((HttpException) throwable).code();

        }
        return -1;
    }

    /**
     * Get error response from all github api's response
     * @param throwable throwable instance from retrofit service's response
     * @return an instance of {@link GitHubErrorResponse} contains error message and some other fields
     */
    @Nullable
    public static GitHubErrorResponse getErrorResponse(@NonNull Throwable throwable) {
        ResponseBody body = null;
        if (throwable instanceof HttpException) {
            body = ((HttpException) throwable).response().errorBody();
        }
        if (body != null) {
            try {
                return GsonProvider.makeGson().fromJson(body.string(), GitHubErrorResponse.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * Get a error message from retrofit response throwable
     * @param throwable retrofit rx throwable
     * @return error message
     */
    public static String getPrettifiedErrorMessage(@Nullable Throwable throwable) {
        if (throwable instanceof HttpException) {
            return ErrorEntity.NETWORK_UNAVAILABLE;
        } else if (throwable instanceof IOException) {
            return ErrorEntity.NETWORK_UNAVAILABLE;
        }
        return ErrorEntity.OOPS;
    }
}
