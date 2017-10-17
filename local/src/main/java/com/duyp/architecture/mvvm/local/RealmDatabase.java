package com.duyp.architecture.mvvm.local;

import com.duyp.architecture.mvvm.local.dao.IssueDao;
import com.duyp.architecture.mvvm.local.dao.IssueDaoImpl;
import com.duyp.architecture.mvvm.local.dao.RepositoryDao;
import com.duyp.architecture.mvvm.local.dao.RepositoryDaoImpl;
import com.duyp.architecture.mvvm.local.dao.UserDao;
import com.duyp.architecture.mvvm.local.dao.UserDaoImpl;
import com.duyp.architecture.mvvm.model.Issue;
import com.duyp.architecture.mvvm.model.Repository;
import com.duyp.architecture.mvvm.model.User;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by duypham on 9/20/17.
 * Database object to provide all Data Access Objects
 */

public class RealmDatabase {

    protected final RealmConfiguration mRealmConfiguration;

    public RealmDatabase(RealmConfiguration realmConfiguration) {
        this.mRealmConfiguration = realmConfiguration;
    }

    public RepositoryDao newRepositoryDao() {
        return new RepositoryDaoImpl(Realm.getInstance(mRealmConfiguration));
    }

    public IssueDao newIssueDao() {
        return new IssueDaoImpl(Realm.getInstance(mRealmConfiguration));
    }

    public UserDao getUserDao() {
        return new UserDaoImpl(Realm.getInstance(mRealmConfiguration));
    }

    public void clearAll() {
        Realm mRealm = Realm.getDefaultInstance();
        mRealm.beginTransaction();
        mRealm.delete(Repository.class);
        mRealm.delete(Issue.class);
        mRealm.delete(User.class);
        mRealm.commitTransaction();
        mRealm.close();
    }
}
