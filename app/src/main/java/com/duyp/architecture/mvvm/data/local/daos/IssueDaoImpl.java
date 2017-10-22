package com.duyp.architecture.mvvm.data.local.daos;

import android.support.annotation.Nullable;

import com.duyp.androidutils.realm.BaseRealmDaoImpl;
import com.duyp.androidutils.realm.LiveRealmResults;
import com.duyp.architecture.mvvm.data.model.Issue;

import io.realm.Realm;

/**
 * Created by duypham on 9/18/17.
 * Issue Data Access Object Implement
 */

public class IssueDaoImpl extends BaseRealmDaoImpl<Issue> implements IssueDao {

    public IssueDaoImpl(Realm realm) {
        super(realm, Issue.class);
    }

    @Override
    public LiveRealmResults<Issue> getRepoIssues(Long repoId) {
        return findAllSorted(query().equalTo("repoId", repoId));
    }

    @Override
    @Nullable
    protected String getDefaultSortField() {
        return "createdAt";
    }
}
