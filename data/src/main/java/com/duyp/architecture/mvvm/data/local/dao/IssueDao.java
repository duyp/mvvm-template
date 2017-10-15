package com.duyp.architecture.mvvm.data.local.dao;

import com.duyp.androidutils.realm.BaseRealmDao;
import com.duyp.androidutils.realm.LiveRealmResults;
import com.duyp.architecture.mvvm.data.model.Issue;

/**
 * Created by duypham on 9/18/17.
 * {@link Issue} Data Access Object
 */

public interface IssueDao extends BaseRealmDao<Issue> {
    /**
     * Get repository issues
     * @param repoId @{@link com.duyp.architecture.mvvm.data.model.Repository#id}
     * @return RealmResults list of issues
     */
    LiveRealmResults<Issue> getRepoIssues(Long repoId);
}