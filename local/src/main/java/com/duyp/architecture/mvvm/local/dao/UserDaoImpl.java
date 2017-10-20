package com.duyp.architecture.mvvm.local.dao;

import com.duyp.androidutils.realm.BaseRealmDaoImpl;
import com.duyp.androidutils.realm.LiveRealmObject;
import com.duyp.architecture.mvvm.model.User;

import io.realm.Realm;

/**
 * Created by duypham on 9/20/17.
 *
 */

public class UserDaoImpl extends BaseRealmDaoImpl<User> implements UserDao {

    public UserDaoImpl(Realm realm) {
        super(realm, User.class);
    }

    public LiveRealmObject<User> getUser(String userLogin) {
        return asLiveData(query().equalTo("login", userLogin).findFirst());
    }
}