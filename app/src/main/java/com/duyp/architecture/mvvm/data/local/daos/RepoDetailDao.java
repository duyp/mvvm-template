package com.duyp.architecture.mvvm.data.local.daos;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.duyp.androidutils.realm.BaseRealmDaoImpl;
import com.duyp.androidutils.realm.LiveRealmObject;
import com.duyp.architecture.mvvm.App;
import com.duyp.architecture.mvvm.data.model.Repo;
import com.duyp.architecture.mvvm.data.model.RepoDetail;
import com.google.gson.Gson;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by duypham on 10/29/17.
 *
 */

public class RepoDetailDao extends BaseRealmDaoImpl<RepoDetail> {

    private final Gson gson;

    @Inject
    public RepoDetailDao(RealmConfiguration realmConfiguration, Gson gson) {
        super(Realm.getInstance(realmConfiguration), RepoDetail.class);
        this.gson = gson;
    }

    @NonNull
    public LiveRealmObject<RepoDetail> getRepoDetail(@NonNull Repo repo) {
        LiveRealmObject<RepoDetail> detail = getById(repo.getId());
        if (detail != null && detail.getData() != null) {
            return detail;
        }
        String s = gson.toJson(repo);
        RepoDetail temp = gson.fromJson(s, RepoDetail.class); // clone
        getRealm().beginTransaction();
        RepoDetail data = getRealm().copyToRealm(temp);
        getRealm().commitTransaction();
        return asLiveData(data);
    }

    @Nullable
    public LiveRealmObject<RepoDetail> getRepoDetail(String owner, String repoName) {
        RepoDetail repoDetail = query().equalTo("owner.login", owner)
                .equalTo("name", repoName)
                .findFirst();
        if (repoDetail != null) {
            return asLiveData(repoDetail);
        }
        return null;
    }
}
