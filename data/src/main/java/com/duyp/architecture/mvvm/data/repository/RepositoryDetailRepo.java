package com.duyp.architecture.mvvm.data.repository;

import android.support.annotation.NonNull;

import com.duyp.androidutils.realm.LiveRealmObject;
import com.duyp.architecture.mvvm.data.remote.GithubService;
import com.duyp.architecture.mvvm.data.source.Resource;
import com.duyp.architecture.mvvm.utils.scopes.PerFragment;
import com.duyp.architecture.mvvm.local.RealmDatabase;
import com.duyp.architecture.mvvm.local.dao.RepositoryDao;
import com.duyp.architecture.mvvm.model.Repository;

import javax.inject.Inject;

import io.reactivex.Flowable;
import lombok.Getter;

/**
 * Created by duypham on 9/17/17.
 *
 */

@Getter
public class RepositoryDetailRepo extends BaseRepo {

    private final RepositoryDao mRepositoryDao;

    private LiveRealmObject<Repository> data;

    @Inject
    public RepositoryDetailRepo(GithubService githubService, RealmDatabase realmDatabase) {
        super(githubService, realmDatabase);
        mRepositoryDao = realmDatabase.newRepositoryDao();
    }

    @Override
    public void onDestroy() {
        mRepositoryDao.closeRealm();
    }

    public LiveRealmObject<Repository> initRepo(@NonNull Long repoId) {
        data = mRepositoryDao.getById(repoId);
        return data;
    }

    public Flowable<Resource<Repository>> getRepository() {
        Repository localRepo = data.getData();
        return createRemoteSourceMapper(getGithubService().getRepository(localRepo.getOwner().getLogin(), localRepo.getName()),repository -> {
                    // github api dose not support, so we need do it manually
                    repository.setMemberLoginName(localRepo.getMemberLoginName());
                    mRepositoryDao.addOrUpdate(repository);
                });
    }
}
