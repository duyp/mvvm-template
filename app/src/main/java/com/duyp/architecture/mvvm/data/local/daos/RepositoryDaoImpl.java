package com.duyp.architecture.mvvm.data.local.daos;

import com.duyp.androidutils.realm.BaseRealmDaoImpl;
import com.duyp.androidutils.realm.LiveRealmResults;
import com.duyp.architecture.mvvm.data.model.Repo;

import io.realm.Realm;

/**
 * Created by duypham on 9/18/17.
 * {@link Repo} Data Access Bbject
 */

public class RepositoryDaoImpl extends BaseRealmDaoImpl<Repo> implements RepositoryDao {

    public RepositoryDaoImpl(Realm realm) {
        super(realm, Repo.class);
    }

    @Override
    public LiveRealmResults<Repo> getRepositoriesWithNameLike(String repoName) {
        return findAllSorted(query().like("name", "*" + repoName + "*"));
    }

    @Override
    public LiveRealmResults<Repo> getUserRepositories(String userLogin) {
        return findAllSorted(
                query().equalTo("owner.login", userLogin)
                        .or()
                        .equalTo("memberLoginName", userLogin)
        );
    }
}
