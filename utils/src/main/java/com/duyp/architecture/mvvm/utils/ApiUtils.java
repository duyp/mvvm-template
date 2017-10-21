package com.duyp.architecture.mvvm.utils;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.duyp.androidutils.rx.functions.PlainConsumer;
import com.duyp.architecture.mvvm.model.GitHubErrorResponse;
import com.duyp.architecture.mvvm.model.ErrorEntity;
import com.duyp.architecture.mvvm.utils.source.Resource;
import com.duyp.architecture.mvvm.utils.source.SimpleRemoteSourceMapper;
import com.google.gson.Gson;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by duypham on 9/10/17.
 *
 */

public final class ApiUtils {

    private static Gson cGson;

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
}
