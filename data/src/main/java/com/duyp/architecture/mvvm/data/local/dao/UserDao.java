package com.duyp.architecture.mvvm.data.local.dao;

import com.duyp.androidutils.realm.BaseRealmDao;
import com.duyp.androidutils.realm.LiveRealmObject;
import com.duyp.architecture.mvvm.data.model.Issue;
import com.duyp.architecture.mvvm.data.model.User;

/**
 * Created by duypham on 9/18/17.
 * {@link Issue} Data Access Object
 */

public interface UserDao extends BaseRealmDao<User> {
    /**
     * Get User by user's login name
     * @param loginName {@link com.duyp.architecture.mvvm.data.model.User#login}
     * @return live data realm object
     */
    LiveRealmObject<User> getUser(String loginName);
}