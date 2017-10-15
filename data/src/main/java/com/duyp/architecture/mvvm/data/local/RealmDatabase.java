package com.duyp.architecture.mvvm.data.local;

import com.duyp.architecture.mvvm.data.local.dao.IssueDao;
import com.duyp.architecture.mvvm.data.local.dao.IssueDaoImpl;
import com.duyp.architecture.mvvm.data.local.dao.RepositoryDao;
import com.duyp.architecture.mvvm.data.local.dao.RepositoryDaoImpl;
import com.duyp.architecture.mvvm.data.local.dao.UserDao;
import com.duyp.architecture.mvvm.data.model.Issue;
import com.duyp.architecture.mvvm.data.model.Repository;
import com.duyp.architecture.mvvm.data.model.User;

import io.realm.Realm;

/**
 * Created by duypham on 9/20/17.
 * Database object to provide all Data Access Objects
 */

public class RealmDatabase {

    private final Realm mRealm;

    private RepositoryDao repositoryDao;

    private IssueDao issueDao;

    private UserDao userDao;

    public RealmDatabase(Realm realm) {
        mRealm = realm;
    }

    public RepositoryDao getRepositoryDao() {
        if (repositoryDao == null) {
            repositoryDao = new RepositoryDaoImpl(mRealm);
        }
        return repositoryDao;
    }

    public IssueDao getIssueDao() {
        if (issueDao == null) {
            issueDao = new IssueDaoImpl(mRealm);
        }
        return issueDao;
    }

    public UserDao getUserDao() {
        if (userDao == null) {
            userDao = new UserDao(mRealm);
        }
        return userDao;
    }

    /**
     * Closes the Realm instance and all its resources.
     * see {@link Realm#close()}
     */
    public void close() {
        mRealm.close();
    }

    public void clearAll() {
        mRealm.beginTransaction();
        mRealm.delete(Repository.class);
        mRealm.delete(Issue.class);
        mRealm.delete(User.class);
        mRealm.commitTransaction();
        mRealm.close();
    }
}
