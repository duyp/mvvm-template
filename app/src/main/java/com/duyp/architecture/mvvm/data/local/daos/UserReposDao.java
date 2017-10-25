package com.duyp.architecture.mvvm.data.local.daos;

import android.support.annotation.Nullable;

import com.duyp.androidutils.realm.BaseRealmDaoImpl;
import com.duyp.androidutils.realm.LiveRealmResults;
import com.duyp.architecture.mvvm.data.model.Repo;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by duypham on 10/25/17.
 *
 */

public class UserReposDao extends BaseRealmDaoImpl<Repo> {

    @Inject
    public UserReposDao(RealmConfiguration realmConfiguration) {
        super(Realm.getInstance(realmConfiguration), Repo.class);
    }

    @Nullable
    @Override
    protected String getDefaultSortField() {
        return "updatedAt";
    }

    /**
     * Get all repositories of given user
     * @param userLogin {@link com.duyp.architecture.mvvm.data.model.User#login}
     * @return RealmResults
     */
    public LiveRealmResults<Repo> getUserRepositories(String userLogin) {
        return findAllSorted(
                query().equalTo("owner.login", userLogin)
                        .or()
                        .equalTo("memberLoginName", userLogin)
        );
    }

    public void deleteAlUseRepos(String userLogin) {
        deleteAll(query().equalTo("owner.login", userLogin)
                .or()
                .equalTo("memberLoginName", userLogin));
    }
}
