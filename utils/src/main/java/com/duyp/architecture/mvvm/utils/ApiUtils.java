package com.duyp.architecture.mvvm.utils;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.duyp.androidutils.rx.functions.PlainConsumer;
import com.duyp.architecture.mvvm.model.base.ErrorEntity;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

/**
 * Created by duypham on 9/10/17.
 *
 */

public final class ApiUtils {

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
            Single<Response<T>> request, boolean shouldUpdateUi,
            @NonNull PlainConsumer<T> responseConsumer,
            @Nullable PlainConsumer<ErrorEntity> errorConsumer) {

        Single<Response<T>> single = request.subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io());
        if (shouldUpdateUi) {
            single = single.observeOn(AndroidSchedulers.mainThread());
        }

        return single.subscribe(response -> {
            if (response.isSuccessful()) {
                responseConsumer.accept(response.body());
            } else if (errorConsumer != null) {
                errorConsumer.accept(ErrorEntity.getError(response.message()));
            }
        }, throwable -> {
            if (throwable instanceof RuntimeException) {
                // must be fixed while developing
                throw new Exception(throwable);
            }
            // handle error
            if (errorConsumer != null) {
                errorConsumer.accept(ErrorEntity.getError(throwable));
            }
        });
    }
}
