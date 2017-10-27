package com.duyp.architecture.mvvm.data.repository;

import android.support.annotation.NonNull;

import com.duyp.androidutils.realm.LiveRealmObject;
import com.duyp.architecture.mvvm.data.local.daos.UserDao;
import com.duyp.architecture.mvvm.data.local.user.UserManager;
import com.duyp.architecture.mvvm.data.model.User;
import com.duyp.architecture.mvvm.data.model.UserDetail;
import com.duyp.architecture.mvvm.data.remote.UserRestService;
import com.duyp.architecture.mvvm.data.source.Resource;
import com.duyp.architecture.mvvm.helper.RestHelper;

import javax.inject.Inject;

import io.reactivex.Flowable;

/**
 * Created by duypham on 10/26/17.
 *
 */

public class UserRepo extends BaseRepo<UserDetail, UserDao> {

    private final UserRestService userRestService;

    @Inject
    public UserRepo(UserManager userManager, UserDao dao, UserRestService service) {
        super(userManager, dao);
        this.userRestService = service;
    }

    public LiveRealmObject<UserDetail> initUser(@NonNull User user) {
        return dao.getUserDetail(user);
    }

    public Flowable<Resource<UserDetail>> getUser(String user) {
        return RestHelper.createRemoteSourceMapper(userRestService.getUser(user), dao::addOrUpdate);
    }
}
