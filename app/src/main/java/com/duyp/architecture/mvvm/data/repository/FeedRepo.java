package com.duyp.architecture.mvvm.data.repository;

import com.duyp.androidutils.realm.LiveRealmResults;
import com.duyp.architecture.mvvm.data.local.RealmDatabase;
import com.duyp.architecture.mvvm.data.local.daos.EventDao;
import com.duyp.architecture.mvvm.data.local.user.UserDataStore;
import com.duyp.architecture.mvvm.data.model.Event;
import com.duyp.architecture.mvvm.data.model.Pageable;
import com.duyp.architecture.mvvm.data.model.User;
import com.duyp.architecture.mvvm.data.remote.GithubService;
import com.duyp.architecture.mvvm.data.remote.UserRestService;
import com.duyp.architecture.mvvm.data.source.Resource;
import com.duyp.architecture.mvvm.helper.RestHelper;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.realm.RealmConfiguration;
import lombok.Getter;

/**
 * Created by duypham on 10/24/17.
 *
 */

public class FeedRepo extends BaseRepo {

    private final EventDao eventDao;
    private final UserRestService userRestService;

    @Getter
    private final LiveRealmResults<Event> data;

    private final User mUser;

    @Inject
    public FeedRepo(GithubService githubService, UserRestService userRestService,
                    RealmDatabase realmDatabase, EventDao eventDao, UserDataStore userDataStore) {
        super(githubService, realmDatabase);
        mUser = userDataStore.getUser();
        this.eventDao = eventDao;
        this.userRestService = userRestService;
        data = eventDao.getAll();
    }

    public Flowable<Resource<Pageable<Event>>> getEvents(int page) {
        return RestHelper.createRemoteSiourceMapper(page == 1, userRestService.getUserEvents(mUser.getLogin(), page), (events, isRefresh) -> {
            if (isRefresh) {
                eventDao.deleteAll();
            }
            eventDao.addAll(events.getItems());
        });
    }

    @Override
    public void onDestroy() {
        eventDao.closeRealm();
    }
}
