package com.duyp.architecture.mvvm.data.repository;

import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;

import com.duyp.androidutils.realm.BaseRealmDaoImpl;
import com.duyp.androidutils.rx.functions.PlainConsumer;
import com.duyp.architecture.mvvm.data.local.RealmDatabase;
import com.duyp.architecture.mvvm.data.local.user.UserManager;
import com.duyp.architecture.mvvm.data.model.User;
import com.duyp.architecture.mvvm.data.remote.GithubService;
import com.duyp.architecture.mvvm.data.source.Resource;
import com.duyp.architecture.mvvm.data.source.SimpleRemoteSourceMapper;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.Single;
import io.realm.Realm;
import io.realm.RealmObject;
import lombok.Getter;

/**
 * Created by duypham on 9/15/17.
 *
 */

@Getter
public abstract class BaseRepo<T extends RealmObject, DAO extends BaseRealmDaoImpl<T>> {

    protected static final String TAG = "repo";

    protected final DAO dao;

    private final UserManager userManager;

    public BaseRepo(UserManager userManager, DAO dao) {
        this.userManager = userManager;
        this.dao = dao;
    }

    @Nullable
    public User getCurrentUser() {
        return userManager.getCurrentUser();
    }

    @Nullable
    public String getCurrentUserLogin() {
        return getCurrentUser() != null ? getCurrentUser().getLogin() : null;
    }

    public Realm getRealm(){
        return dao.getRealm();
    }

    @CallSuper
    public void onDestroy() {
        dao.closeRealm();
    }
}