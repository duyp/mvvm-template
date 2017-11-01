package com.duyp.architecture.mvvm.data.local.daos;

import android.support.annotation.Nullable;

import com.duyp.androidutils.realm.BaseRealmDaoImpl;
import com.duyp.architecture.mvvm.data.model.Repo;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by duypham on 11/1/17.
 *
 */

public class AllReposDao extends BaseRealmDaoImpl<Repo> {

    @Inject
    public AllReposDao(RealmConfiguration realmConfiguration) {
        super(Realm.getInstance(realmConfiguration), Repo.class);
    }

    @Override
    protected String getDefaultSortField() {
        return "createdAt";
    }

    public RealmResults<Repo> searchRepo(String name) {
        return query().like("name", name).findAllSorted(getDefaultSortField(), Sort.DESCENDING);
    }
}
