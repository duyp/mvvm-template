package com.duyp.architecture.mvvm.data.repository;

import android.arch.lifecycle.LifecycleOwner;
import android.support.annotation.NonNull;

import com.duyp.androidutils.realm.LiveRealmObject;
import com.duyp.architecture.mvvm.data.local.RealmDatabase;
import com.duyp.architecture.mvvm.data.local.dao.UserDao;
import com.duyp.architecture.mvvm.data.model.User;
import com.duyp.architecture.mvvm.data.remote.GithubService;
import com.duyp.architecture.mvvm.data.source.Resource;

import javax.inject.Inject;

import io.reactivex.Flowable;

/**
 * Created by duypham on 9/20/17.
 *
 */

public class UserRepo extends BaseRepo{

    private final UserDao userDao;

    private LiveRealmObject<User> user;

    @Inject
    public UserRepo(LifecycleOwner owner, GithubService githubService, RealmDatabase realmDatabase) {
        super(owner, githubService, realmDatabase);
        this.userDao = realmDatabase.getUserDao();
    }

    public LiveRealmObject<User> initUser(@NonNull String userLogin) {
        return user = userDao.getUser(userLogin);
    }

    public Flowable<Resource<User>> fetchUser() {
        return createResource(getGithubService().getUser(user.getData().getLogin()), userDao::addOrUpdate);
    }

}
