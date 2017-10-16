package com.duyp.architecture.mvvm.data.local.dao;

import com.duyp.androidutils.realm.BaseRealmDaoImpl;
import com.duyp.androidutils.realm.LiveRealmObject;
import com.duyp.architecture.mvvm.data.model.User;

import javax.inject.Inject;

import io.realm.Realm;

/**
 * Created by duypham on 9/20/17.
 *
 */

public class UserDaoImpl extends BaseRealmDaoImpl<User> implements UserDao {

    @Inject
    public UserDaoImpl(Realm realm) {
        super(realm, User.class);
    }

    public LiveRealmObject<User> getUser(String userLogin) {
        return asLiveData(query().equalTo("login", userLogin).findFirst());
    }
}