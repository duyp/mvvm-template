package com.duyp.architecture.mvvm.data.source;

import android.util.Log;

import com.duyp.architecture.mvvm.utils.ApiUtils;

import io.reactivex.FlowableEmitter;
import io.reactivex.Single;
import retrofit2.Response;

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
        // since realm was create on Main Thread, so if we need to touch on realm database after calling
        // api, must make request on main thread by setting shouldUpdateUi params = true
        ApiUtils.makeRequest(getRemote(), true, response -> {
            Log.d(TAG, "SimpleRemoteSourceMapper: call API success!");
            saveCallResult(response);
            emitter.onNext(Resource.success(response));
        }, errorEntity -> {
            Log.d(TAG, "SimpleRemoteSourceMapper: call API error: " + errorEntity.getMessage());
            emitter.onNext(Resource.error(errorEntity.getMessage(), null));
        });
    }

    public abstract Single<Response<T>> getRemote();

    public abstract void saveCallResult(T data);
}