package com.duyp.architecture.mvvm.data.source;

import android.util.Log;

import com.duyp.architecture.mvvm.model.remote.ApiResponse;
import com.duyp.architecture.mvvm.utils.ApiUtils;

import io.reactivex.FlowableEmitter;
import io.reactivex.Single;
import io.reactivex.disposables.Disposable;

/**
 * A generic class that can provide a resource backed by both the sqlite database and the network.
 * <p>
 * You can read more about it in the <a href="https://developer.android.com/arch">Architecture
 * Guide</a>.
 */
public abstract class SimpleRemoteSourceMapper<T> {

    public static final String TAG = "source";

    public SimpleRemoteSourceMapper(FlowableEmitter<Resource<T>> emitter) {
        emitter.onNext(Resource.loading(null));
        // since realm instance was created on Main Thread, so if we need to touch on realm database after calling
        // api (such as save response data to local database, we must make request on main thread
        // by setting shouldUpdateUi params = true
        Disposable disposable = ApiUtils.makeRequest(getRemote(), true, response -> {
            Log.d(TAG, "SimpleRemoteSourceMapper: call API success!");
            saveCallResult(response.body);
            emitter.onNext(Resource.success(response));
        }, errorEntity -> {
            Log.d(TAG, "SimpleRemoteSourceMapper: call API error: " + errorEntity.getMessage());
            emitter.onNext(Resource.error(errorEntity.getMessage(), null));
        });

        // set emitter disposable to ensure that when it is going to be disposed, our api request should be disposed as well
        emitter.setDisposable(disposable);
    }

    public abstract Single<ApiResponse<T>> getRemote();

    public abstract void saveCallResult(T data);
}