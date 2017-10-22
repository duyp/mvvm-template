package com.duyp.architecture.mvvm.data.provider;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.duyp.androidutils.rx.functions.PlainConsumer;
import com.duyp.architecture.mvvm.data.model.ErrorEntity;
import com.duyp.architecture.mvvm.data.model.GitHubErrorResponse;
import com.duyp.architecture.mvvm.data.source.Resource;
import com.duyp.architecture.mvvm.data.source.SimpleRemoteSourceMapper;
import com.google.gson.Gson;

import java.io.IOException;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.HttpException;

/**
 * Created by duypham on 10/21/17.
 * Helper class to deal with operations on reotrofit Rest API calling
 */

public class RestHelper {


    private static Gson cGson;

    /**
     * MUST CALL THIS
     * Init a {@link Gson} instance to parse github error response
     * @param gson singleton gson instance, from {@link com.duyp.architecture.mvvm.injection.DataModule}
     */
    public static void initGson(Gson gson) {
        cGson = gson;
    }

    /**
     * Create new retrofit api request
     * @param request single api request
     * @param shouldUpdateUi true if should update UI after request done
     * @param responseConsumer consume parsed response data (in pojo object)
     * @param errorConsumer consume {@link ErrorEntity} object which is construct by which error is occurred
     *                      with a code and an message (will be shown to user)
     *                      the error might by a HttpException, Runtime Exception, or an error respond from back-end api...
     *
     * @param <T> Type of response body
     * @return a disposable
     */
    public static <T> Disposable makeRequest(
            Single<T> request, boolean shouldUpdateUi,
            @NonNull PlainConsumer<T> responseConsumer,
            @Nullable PlainConsumer<ErrorEntity> errorConsumer) {

        Single<T> single = request.subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io());
        if (shouldUpdateUi) {
            single = single.observeOn(AndroidSchedulers.mainThread());
        }

        return single.subscribe(responseConsumer, throwable -> {
            // handle error
            throwable.printStackTrace();
            if (errorConsumer != null) {
                int code = RestHelper.getErrorCode(throwable);
                String message;

                GitHubErrorResponse errorResponse = RestHelper.getErrorResponse(cGson, throwable);
                if (errorResponse != null && errorResponse.getMessage() != null) {
                    message = errorResponse.getMessage();
                } else {
                    message = RestHelper.getPrettifiedErrorMessage(throwable);
                }
                errorConsumer.accept(new ErrorEntity(message, code));
            }
        });
    }

    /**
     * Create a mapper from retrofit service to {@link Resource} with rx's {@link Flowable}
     * To indicate current state while execute an rest api (loading, error, success with status and message if error)
     * @param remote from retrofit service
     * @param onSave will be called after success response come, to save response data into local database
     * @param <T> type of response
     * @return a {@link Flowable} instance to deal with progress showing and error handling
     */
    public static <T> Flowable<Resource<T>> createRemoteSourceMapper(@Nullable Single<T> remote,
                                                                     @Nullable PlainConsumer<T> onSave) {
        return Flowable.create(emitter -> {
            new SimpleRemoteSourceMapper<T>(emitter) {

                @Override
                public Single<T> getRemote() {
                    return remote;
                }

                @Override
                public void saveCallResult(T data) {
                    if (onSave != null) {
                        onSave.accept(data);
                    }
                }
            };
        }, BackpressureStrategy.BUFFER);
    }

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
     * @param gson gson instance to parse response data to pojo object
     * @param throwable throwable instance from retrofit service's response
     * @return an instance of {@link GitHubErrorResponse} contains error message and some other fields
     */
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
