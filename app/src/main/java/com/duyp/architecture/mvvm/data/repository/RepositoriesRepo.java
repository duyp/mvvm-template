package com.duyp.architecture.mvvm.data.repository;

import android.support.annotation.Nullable;
import android.util.Log;

import com.duyp.androidutils.realm.LiveRealmResults;
import com.duyp.architecture.mvvm.data.local.RealmDatabase;
import com.duyp.architecture.mvvm.data.local.daos.RepositoryDao;
import com.duyp.architecture.mvvm.data.model.Repo;
import com.duyp.architecture.mvvm.data.model.User;
import com.duyp.architecture.mvvm.data.remote.GithubService;
import com.duyp.architecture.mvvm.data.source.Resource;
import com.duyp.architecture.mvvm.data.local.user.UserDataStore;
import com.duyp.architecture.mvvm.data.model.def.RepoTypes;
import com.duyp.architecture.mvvm.helper.RestHelper;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.Single;
import lombok.Getter;

public class RepositoriesRepo extends BaseRepo {

    @Getter
    protected final RepositoryDao repositoryDao;

    @Getter
    private LiveRealmResults<Repo> data;

    @Nullable
    private final User mUser;

    @Inject
    public RepositoriesRepo(GithubService githubService, RealmDatabase realmDatabase,
                            UserDataStore userDataStore) {
        super(githubService, realmDatabase);
        this.repositoryDao = realmDatabase.newRepositoryDao();
        mUser = userDataStore.getUser();
    }

    /**
     * Get all public repositories from github api
     * @param sinceId last repository item got
     * @return resource mapper flowable
     */
    public Flowable<Resource<List<Repo>>> getAllRepositories(@Nullable Long sinceId) {
        Log.d(TAG, "RepositoriesRepo: getting all repo with sinceId = " + sinceId);
//        if (sinceId != null) {
//            currentPage ++;
//        } else {
//            currentPage = 1;
//        }
//        data = repositoryDao.getAll(currentPage * PER_PAGE);
        data = repositoryDao.getAll();
        return RestHelper.createRemoteSourceMapper(getGithubService().getAllPublicRepositories(sinceId), repositoryDao::addAll);
    }

    /**
     * Find repositories by given repository name
     * @param repoName
     * @return resource mapper flowable
     */
    public Flowable<Resource<List<Repo>>> findRepositories(String repoName) {
        Log.d(TAG, "RepositoriesRepo: finding repo: " + repoName);
        data = repositoryDao.getRepositoriesWithNameLike(repoName);
        return RestHelper.createRemoteSourceMapper(getGithubService().getAllPublicRepositories(null), repositoryDao::addAll);
    }

    /**
     * Get repositories of given user
     * if given user is current saved user, we get his own repositories by {@link GithubService#getMyRepositories(String)}
     * @param userNameLogin user login name
     * @return resource mapper flowable
     */
    public Flowable<Resource<List<Repo>>> getUserRepositories(String userNameLogin) {
        data = repositoryDao.getUserRepositories(userNameLogin);

        boolean isOwner = mUser != null && mUser.getLogin().equals(userNameLogin);
        Single<List<Repo>> remote = isOwner ? getGithubService().getMyRepositories(RepoTypes.ALL) :
                getGithubService().getUserRepositories(userNameLogin, RepoTypes.ALL);

        return RestHelper.createRemoteSourceMapper(remote, repositories -> {
            if (isOwner) {
                for (Repo repo : repositories) {
                    if (!repo.getOwner().getLogin().equals(mUser.getLogin())) {
                        repo.setMemberLoginName(mUser.getLogin());
                    }
                }
            }
            repositoryDao.addAll(repositories);
        });
    }

    @Override
    public void onDestroy() {
        repositoryDao.closeRealm();
    }
}