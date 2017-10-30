package com.duyp.architecture.mvvm.data.local.daos;

import android.support.annotation.Nullable;

import com.duyp.androidutils.realm.BaseRealmDaoImpl;
import com.duyp.androidutils.realm.LiveRealmResults;
import com.duyp.architecture.mvvm.data.model.Issue;
import com.duyp.architecture.mvvm.data.model.type.IssueState;

import java.util.List;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by duypham on 9/18/17.
 * Issue Data Access Object Implement
 */

public class IssueDaoImpl extends BaseRealmDaoImpl<Issue> implements IssueDao {

    @Inject
    public IssueDaoImpl(RealmConfiguration realmConfiguration) {
        super(Realm.getInstance(realmConfiguration), Issue.class);
    }

    @Override
    public LiveRealmResults<Issue> getRepoIssues(Long repoId) {
        return findAllSorted(query().equalTo("repoId", repoId));
    }

    public LiveRealmResults<Issue> getRepoIssues(String repoName, String login, @IssueState String state) {
        return findAllSorted(
                query().equalTo("repoName", repoName)
                        .equalTo("login", login)
                        .equalTo("state", state)
        );
    }

    public void saveAllAsync(List<Issue> issues, String repoName, String login) {
        for(Issue issue : issues) {
            issue.setLogin(login);
            issue.setRepoName(repoName);
        }
        this.addAllAsync(issues);
    }

    @Override
    @Nullable
    protected String getDefaultSortField() {
        return "createdAt";
    }
}
