package com.duyp.architecture.mvvm.local.dao;

import com.duyp.androidutils.realm.BaseRealmDaoImpl;
import com.duyp.androidutils.realm.LiveRealmResults;
import com.duyp.architecture.mvvm.model.Repository;

import javax.inject.Inject;

import io.realm.Realm;

/**
 * Created by duypham on 9/18/17.
 * {@link Repository} Data Access Bbject
 */

public class RepositoryDaoImpl extends BaseRealmDaoImpl<Repository> implements RepositoryDao {

    @Inject
    public RepositoryDaoImpl(Realm realm) {
        super(realm, Repository.class);
    }

    @Override
    public LiveRealmResults<Repository> getRepositoriesWithNameLike(String repoName) {
        return findAllSorted(query().like("name", "*" + repoName + "*"));
    }

    @Override
    public LiveRealmResults<Repository> getUserRepositories(String userLogin) {
        return findAllSorted(
                query().equalTo("owner.login", userLogin)
                        .or()
                        .equalTo("memberLoginName", userLogin)
        );
    }
}
