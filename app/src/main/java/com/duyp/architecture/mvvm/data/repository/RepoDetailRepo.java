package com.duyp.architecture.mvvm.data.repository;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.duyp.androidutils.realm.LiveRealmObject;
import com.duyp.architecture.mvvm.data.local.daos.RepoDetailDao;
import com.duyp.architecture.mvvm.data.local.user.UserManager;
import com.duyp.architecture.mvvm.data.model.Repo;
import com.duyp.architecture.mvvm.data.model.RepoDetail;
import com.duyp.architecture.mvvm.data.remote.RepoService;
import com.duyp.architecture.mvvm.data.source.Resource;
import com.duyp.architecture.mvvm.helper.RestHelper;

import javax.inject.Inject;

import io.reactivex.Flowable;
import lombok.Getter;

/**
 * Created by duypham on 10/29/17.
 *
 */

public class RepoDetailRepo extends BaseRepo<RepoDetail, RepoDetailDao> {

    private final RepoService repoService;
    @Getter
    private LiveRealmObject<RepoDetail> data;

    @Inject
    public RepoDetailRepo(UserManager userManager, RepoDetailDao dao, RepoService repoService) {
        super(userManager, dao);
        this.repoService = repoService;
    }

    @NonNull
    public LiveRealmObject<RepoDetail> initRepo(@NonNull Repo repo) {
        data = dao.getRepoDetail(repo);
        return data;
    }

    @Nullable
    public LiveRealmObject<RepoDetail> initRepo(String owner, String repoName) {
        data = dao.getRepoDetail(owner, repoName);
        return data;
    }

    public Flowable<Resource<RepoDetail>> getRepo(String login, String repoName) {
        return RestHelper.createRemoteSourceMapper(repoService.getRepo(login, repoName), dao::addOrUpdate);
    }

    public void updateWatched(boolean increase) {
        if (data.getData() != null) {
            getRealm().executeTransaction(realm -> {
                data.getData().setSubsCount(data.getData().getSubsCount() + (increase ? 1 : -1));
                data.getData().setWatchersCount(data.getData().getWatchersCount() + (increase ? 1 : -1));
            });
        }
    }

    public void updateStarred(boolean increase) {
        if (data.getData() != null) {
            getRealm().executeTransaction(realm -> {
                data.getData().setStargazersCount(data.getData().getStargazersCount() + (increase ? 1 : -1));
            });
        }
    }
}
