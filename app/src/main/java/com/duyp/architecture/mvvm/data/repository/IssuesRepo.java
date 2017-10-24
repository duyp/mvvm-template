package com.duyp.architecture.mvvm.data.repository;

import android.support.annotation.NonNull;

import com.duyp.androidutils.realm.LiveRealmResults;
import com.duyp.architecture.mvvm.data.local.RealmDatabase;
import com.duyp.architecture.mvvm.data.local.daos.IssueDao;
import com.duyp.architecture.mvvm.data.local.daos.RepositoryDao;
import com.duyp.architecture.mvvm.data.model.Issue;
import com.duyp.architecture.mvvm.data.model.Repo;
import com.duyp.architecture.mvvm.data.remote.GithubService;
import com.duyp.architecture.mvvm.data.source.Resource;
import com.duyp.architecture.mvvm.helper.RestHelper;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import lombok.Getter;

/**
 * Created by duypham on 9/17/17.
 *
 */

@Getter
public class IssuesRepo extends BaseRepo {

    private Repo mRepo;

    private IssueDao mIssuesDao;

    @Getter
    private LiveRealmResults<Issue> data;

    @Inject
    public IssuesRepo(GithubService githubService, RealmDatabase realmDatabase) {
        super(githubService, realmDatabase);
        this.mIssuesDao = realmDatabase.newIssueDao();
    }

    public void initRepo(@NonNull Long repoId) {
        RepositoryDao repositoryDao = getRealmDatabase().newRepositoryDao();
        this.mRepo = repositoryDao.getById(repoId).getData();
        data = mIssuesDao.getRepoIssues(mRepo.getId());
        repositoryDao.closeRealm();
    }

    public Flowable<Resource<List<Issue>>> getRepoIssues() {
        return RestHelper.createRemoteSourceMapper(getGithubService().getRepoIssues(mRepo.getOwner().getLogin(), mRepo.getName()), issues -> {
            for (Issue issue : issues) {
                issue.setRepoId(mRepo.getId());
            }
            mIssuesDao.addAll(issues);
        });
    }

    @Override
    public void onDestroy() {
        mIssuesDao.closeRealm();
    }
}