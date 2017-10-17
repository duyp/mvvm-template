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
     * @param request observable request
     * @param shouldUpdateUi true if should update UI after response returned
     * @param responseConsumer consume response data
     * @param errorConsumer consume errors
     * @param <T> Type of response body
     */
    public static <T> Disposable makeRequest(
            Single<Response<T>> request, boolean shouldUpdateUi,
            @NonNull PlainConsumer<T> responseConsumer,
            @Nullable PlainConsumer<ErrorEntity> errorConsumer,
            @Nullable Action onComplete) {

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
            if (onComplete != null) {
                onComplete.run();
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
            if (onComplete != null) {
                onComplete.run();
            }
        });
    }

    public static <T> Disposable makeRequest(Single<Response<T>> request, boolean shouldUpdateUi, @NonNull PlainConsumer<T> responseConsumer) {
        return makeRequest(request, shouldUpdateUi, responseConsumer, null, null);
    }

    public static <T> Disposable makeRequest(Single<Response<T>> request, boolean shouldUpdateUi,
                                             @NonNull PlainConsumer<T> responseConsumer,
                                             @Nullable PlainConsumer<ErrorEntity> errorConsumer) {
        return makeRequest(request, shouldUpdateUi, responseConsumer, errorConsumer, null);
    }


    /// for RESOURCE processing
//    /**
//     * Create new retrofit api request
//     * @param request observable request
//     * @param shouldUpdateUi true if should update UI after response returned
//     * @param responseConsumer consume response data
//     * @param errorConsumer consume errors
//     * @param <T> Type of response body
//     */
//    public static <T> Disposable makeRequest(
//            Single<Response<T>> request, boolean shouldUpdateUi,
//            @NonNull PlainConsumer<T> responseConsumer,
//            @Nullable PlainConsumer<ErrorEntity> errorConsumer,
//            @Nullable Action onComplete) {
//
//        Single<Response<T>> single = request.subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io());
//        if (shouldUpdateUi) {
//            single = single.observeOn(AndroidSchedulers.mainThread());
//        }
//
//        return single.subscribe(response -> {
//            if (response.isSuccessful()) {
//                responseConsumer.accept(response.body());
//            } else if (errorConsumer != null) {
//                errorConsumer.accept(ErrorEntity.getError(response.message()));
//            }
//            if (onComplete != null) {
//                onComplete.run();
//            }
//        }, throwable -> {
//            if (throwable instanceof RuntimeException) {
//                // must be fixed while developing
//                throw new Exception(throwable);
//            }
//            // handle error
//            if (errorConsumer != null) {
//                errorConsumer.accept(ErrorEntity.getError(throwable));
//            }
//            if (onComplete != null) {
//                onComplete.run();
//            }
//        });
//    }
//
//    public static <T> Disposable makeRequest(Single<Response<T>> request, boolean shouldUpdateUi, @NonNull PlainConsumer<T> responseConsumer) {
//        return makeRequest(request, shouldUpdateUi, responseConsumer, null, null);
//    }
}
