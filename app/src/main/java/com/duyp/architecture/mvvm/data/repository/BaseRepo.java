package com.duyp.architecture.mvvm.data.repository;

import android.support.annotation.Nullable;

import com.duyp.androidutils.rx.functions.PlainConsumer;
import com.duyp.architecture.mvvm.data.local.RealmDatabase;
import com.duyp.architecture.mvvm.data.remote.GithubService;
import com.duyp.architecture.mvvm.data.source.Resource;
import com.duyp.architecture.mvvm.data.source.SimpleRemoteSourceMapper;

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

    public abstract void onDestroy();
}