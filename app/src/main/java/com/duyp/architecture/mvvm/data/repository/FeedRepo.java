package com.duyp.architecture.mvvm.data.repository;

import com.duyp.architecture.mvvm.data.local.RealmDatabase;
import com.duyp.architecture.mvvm.data.local.daos.EventDao;
import com.duyp.architecture.mvvm.data.remote.GithubService;
import com.duyp.architecture.mvvm.data.remote.UserRestService;

import javax.inject.Inject;

import io.realm.RealmConfiguration;

/**
 * Created by duypham on 10/24/17.
 *
 */

public class FeedRepo extends BaseRepo {

    private final EventDao eventDao;
    private final UserRestService userRestService;

    @Inject
    public FeedRepo(GithubService githubService, UserRestService userRestService, RealmDatabase realmDatabase, EventDao eventDao) {
        super(githubService, realmDatabase);
        this.eventDao = eventDao;
        this.userRestService = userRestService;
    }



    @Override
    public void onDestroy() {
        eventDao.closeRealm();
    }
}
