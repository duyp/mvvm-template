package com.duyp.architecture.mvvm.data.local.daos;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.duyp.androidutils.realm.BaseRealmDaoImpl;
import com.duyp.androidutils.realm.LiveRealmObject;
import com.duyp.architecture.mvvm.data.model.User;
import com.duyp.architecture.mvvm.data.model.UserDetail;
import com.google.gson.Gson;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by duypham on 9/20/17.
 *
 */

public class UserDao extends BaseRealmDaoImpl<UserDetail> {

    private final Gson gson;

    @Inject
    public UserDao(RealmConfiguration realmConfiguration, Gson gson) {
        super(Realm.getInstance(realmConfiguration), UserDetail.class);
        this.gson = gson;
    }

    @NonNull
    public LiveRealmObject<UserDetail> getUserDetail(@NonNull User user) {
        UserDetail detail = query().equalTo("login", user.getLogin()).findFirst();
        if (detail == null) {
            String s = gson.toJson(user);
            UserDetail temp = gson.fromJson(s, UserDetail.class); // clone
            getRealm().beginTransaction();
            detail = getRealm().copyToRealm(temp);
            getRealm().commitTransaction();
        }
        return asLiveData(detail);
    }
}