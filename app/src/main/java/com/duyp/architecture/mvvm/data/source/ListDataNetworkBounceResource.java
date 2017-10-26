package com.duyp.architecture.mvvm.data.source;

import android.util.Log;

import com.duyp.architecture.mvvm.helper.RestHelper;

import java.util.List;

import io.reactivex.FlowableEmitter;
import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.realm.RealmObject;
import io.realm.RealmResults;

/**
 * A generic class that can provide a resource backed by both the sqlite database and the network.
 * <p>
 * You can read more about it in the <a href="https://developer.android.com/arch">Architecture
 * Guide</a>.
 */
public abstract class ListDataNetworkBounceResource<LocalType, RemoteType> {

    public static final String TAG = "source";

    public ListDataNetworkBounceResource(FlowableEmitter<Resource<LocalType>> emitter) {
        emitter.onNext(Resource.success(getLocal()));

        emitter.onNext(Resource.loading(null));

        // since realm instance was created on Main Thread, so if we need to touch on realm database after calling
        // api (such as save response data to local database, we must make request on main thread
        // by setting shouldUpdateUi params = true
        Disposable disposable = RestHelper.makeRequest(getRemote(), true, response -> {
            Single.just(response)
                    .map(mapper())
                    .subscribe(localData -> {
                        Log.d(TAG, "SimpleRemoteSourceMapper: call API success!");
                        saveCallResult(localData);
                        emitter.onNext(Resource.success(localData));
                    });
        }, errorEntity -> {
            Log.d(TAG, "SimpleRemoteSourceMapper: call API error: " + errorEntity.getMessage());
            emitter.onNext(Resource.error(errorEntity.getMessage(), null));
        });

        // set emitter disposable to ensure that when it is going to be disposed, our api request should be disposed as well
        emitter.setDisposable(disposable);
    }

    public abstract LocalType getLocal();

    public abstract Single<RemoteType> getRemote();

    public abstract void saveCallResult(LocalType data);

    public abstract Function<RemoteType, LocalType> mapper();
}