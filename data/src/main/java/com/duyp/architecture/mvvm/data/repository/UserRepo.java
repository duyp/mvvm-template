package com.duyp.architecture.mvvm.data.repository;

import android.support.annotation.NonNull;

import com.duyp.androidutils.realm.LiveRealmObject;
import com.duyp.architecture.mvvm.data.remote.GithubService;
import com.duyp.architecture.mvvm.data.source.Resource;
import com.duyp.architecture.mvvm.local.RealmDatabase;
import com.duyp.architecture.mvvm.local.dao.UserDaoImpl;
import com.duyp.architecture.mvvm.model.User;

import javax.inject.Inject;

import io.reactivex.Flowable;

/**
 * Created by duypham on 9/20/17.
 *
 */

public class UserRepo extends BaseRepo{

    private final UserDaoImpl userDao;

    private LiveRealmObject<User> user;

    @Inject
    public UserRepo(GithubService githubService, RealmDatabase realmDatabase) {
        super(githubService, realmDatabase);
        this.userDao = realmDatabase.getUserDao();
    }

    @Override
    public void onDestroy() {
        userDao.closeRealm();
    }

    public LiveRealmObject<User> initUser(@NonNull String userLogin) {
        return user = userDao.getUser(userLogin);
    }

    public Flowable<Resource<User>> fetchUser() {
        return createRemoteSourceMapper(getGithubService().getUser(user.getData().getLogin()), userDao::addOrUpdate);
    }

}
