package com.duyp.architecture.mvvm.data.local;

import com.duyp.architecture.mvvm.data.local.daos.IssueDao;
import com.duyp.architecture.mvvm.data.local.daos.IssueDaoImpl;
import com.duyp.architecture.mvvm.data.local.daos.RepositoryDao;
import com.duyp.architecture.mvvm.data.local.daos.RepositoryDaoImpl;
import com.duyp.architecture.mvvm.data.local.daos.UserDao;
import com.duyp.architecture.mvvm.data.model.Issue;
import com.duyp.architecture.mvvm.data.model.Repo;
import com.duyp.architecture.mvvm.data.model.User;

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

    public void clearAll() {
        Realm mRealm = Realm.getInstance(mRealmConfiguration);
        mRealm.beginTransaction();
        mRealm.delete(Repo.class);
        mRealm.delete(Issue.class);
        mRealm.delete(User.class);
        mRealm.commitTransaction();
        mRealm.close();
    }
}
