package com.duyp.architecture.mvvm.data.repository;

import android.support.annotation.Nullable;

import com.duyp.androidutils.rx.functions.PlainConsumer;
import com.duyp.architecture.mvvm.data.remote.GithubService;
import com.duyp.architecture.mvvm.utils.source.Resource;
import com.duyp.architecture.mvvm.utils.source.SimpleRemoteSourceMapper;
import com.duyp.architecture.mvvm.local.RealmDatabase;
import com.duyp.architecture.mvvm.model.remote.ApiResponse;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.Single;
import lombok.Getter;

/**
 * Created by duypham on 9/15/17.
 *
 */

@Getter
public abstract class BaseRepo {

    protected static final String TAG = "repo";

    private final GithubService githubService;

    private final RealmDatabase realmDatabase;

    public BaseRepo(GithubService githubService, RealmDatabase realmDatabase) {
        this.githubService = githubService;
        this.realmDatabase = realmDatabase;
    }

    protected <T> Flowable<Resource<T>> createRemoteSourceMapper(@Nullable Single<T> remote,
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

    public abstract void onDestroy();
}