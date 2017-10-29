package com.duyp.architecture.mvvm.data.repository;

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
import lombok.NonNull;

/**
 * Created by duypham on 10/29/17.
 *
 */

public class RepoDetailRepo extends BaseRepo<RepoDetail, RepoDetailDao> {

    private final RepoService repoService;

    @Inject
    public RepoDetailRepo(UserManager userManager, RepoDetailDao dao, RepoService repoService) {
        super(userManager, dao);
        this.repoService = repoService;
    }

    public LiveRealmObject<RepoDetail> initRepo(@NonNull Repo repo) {
        return dao.getRepoDetail(repo);
    }

    public Flowable<Resource<RepoDetail>> getRepo(String login, String repoName) {
        return RestHelper.createRemoteSourceMapper(repoService.getRepo(login, repoName), dao::addOrUpdate);
    }
}
