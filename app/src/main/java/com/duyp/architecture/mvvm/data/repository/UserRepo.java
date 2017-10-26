package com.duyp.architecture.mvvm.data.repository;

import android.support.annotation.NonNull;

import com.duyp.androidutils.realm.LiveRealmObject;
import com.duyp.architecture.mvvm.data.local.daos.UserDao;
import com.duyp.architecture.mvvm.data.local.user.UserManager;
import com.duyp.architecture.mvvm.data.model.User;
import com.duyp.architecture.mvvm.data.model.UserDetail;

import javax.inject.Inject;

/**
 * Created by duypham on 10/26/17.
 *
 */

public class UserRepo extends BaseRepo<UserDetail, UserDao> {

    @Inject
    public UserRepo(UserManager userManager, UserDao dao) {
        super(userManager, dao);
    }

    public LiveRealmObject<UserDetail> initUser(@NonNull User user) {
        return dao.getUserDetail(user);
    }
}
