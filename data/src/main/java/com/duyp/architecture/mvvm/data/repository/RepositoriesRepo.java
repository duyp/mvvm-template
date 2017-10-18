package com.duyp.architecture.mvvm.data.repository;

import android.support.annotation.Nullable;
import android.util.Log;

import com.duyp.androidutils.realm.LiveRealmResults;
import com.duyp.architecture.mvvm.data.remote.GithubService;
import com.duyp.architecture.mvvm.data.source.Resource;
import com.duyp.architecture.mvvm.local.RealmDatabase;
import com.duyp.architecture.mvvm.local.UserDataStore;
import com.duyp.architecture.mvvm.local.dao.RepositoryDao;
import com.duyp.architecture.mvvm.model.Repository;
import com.duyp.architecture.mvvm.model.User;
import com.duyp.architecture.mvvm.model.def.RepoTypes;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.Single;
import lombok.Getter;
import retrofit2.Response;

public class RepositoriesRepo extends BaseRepo {

    public static final int PER_PAGE = 100;

    @Getter
    protected final RepositoryDao repositoryDao;

    @Getter
    private LiveRealmResults<Repository> data;

    private int currentPage = 1;

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
    public Flowable<Resource<List<Repository>>> getAllRepositories(@Nullable Long sinceId) {
        Log.d(TAG, "RepositoriesRepo: getting all repo with sinceId = " + sinceId);
        if (sinceId != null) {
            currentPage ++;
        } else {
            currentPage = 1;
        }
//        data = repositoryDao.getAll(currentPage * PER_PAGE);
        data = repositoryDao.getAll();
        return createRemoteSourceMapper(getGithubService().getAllPublicRepositories(sinceId), repositoryDao::addAll);
    }

    /**
     * Find repositories by given repository name
     * @param repoName
     * @return resource mapper flowable
     */
    public Flowable<Resource<List<Repository>>> findRepositories(String repoName) {
        Log.d(TAG, "RepositoriesRepo: finding repo: " + repoName);
        data = repositoryDao.getRepositoriesWithNameLike(repoName);
        return createRemoteSourceMapper(getGithubService().getAllPublicRepositories(null), repositoryDao::addAll);
    }

    /**
     * Get repositories of given user
     * if given user is current saved user, we get his own repositories by {@link GithubService#getMyRepositories(String)}
     * @param userNameLogin user login name
     * @return resource mapper flowable
     */
    public Flowable<Resource<List<Repository>>> getUserRepositories(String userNameLogin) {
        data = repositoryDao.getUserRepositories(userNameLogin);

        boolean isOwner = mUser != null && mUser.getLogin().equals(userNameLogin);
        Single<Response<List<Repository>>> remote = isOwner ? getGithubService().getMyRepositories(RepoTypes.ALL) :
                getGithubService().getUserRepositories(userNameLogin, RepoTypes.ALL);

        return createRemoteSourceMapper(remote, repositories -> {
            if (isOwner) {
                for (Repository repository : repositories) {
                    if (!repository.getOwner().getLogin().equals(mUser.getLogin())) {
                        repository.setMemberLoginName(mUser.getLogin());
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