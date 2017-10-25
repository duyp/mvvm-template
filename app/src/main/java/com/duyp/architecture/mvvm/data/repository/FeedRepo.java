package com.duyp.architecture.mvvm.data.repository;

import com.duyp.androidutils.realm.LiveRealmResults;
import com.duyp.architecture.mvvm.data.local.RealmDatabase;
import com.duyp.architecture.mvvm.data.local.daos.EventDao;
import com.duyp.architecture.mvvm.data.local.user.UserDataStore;
import com.duyp.architecture.mvvm.data.local.user.UserManager;
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

public class FeedRepo extends BaseRepo<Event, EventDao> {

    private final UserRestService userRestService;

    @Getter
    private LiveRealmResults<Event> data;

    private String targetUser;
    private boolean isMyUser;

    @Inject
    public FeedRepo(UserManager userManager, UserRestService userRestService, EventDao eventDao) {
        super(userManager, eventDao);
        this.userRestService = userRestService;
    }

    public void initTargetUser(String user) {
        String myUser = getCurrentUserLogin();
        if (myUser == null && user == null) {
            throw new IllegalStateException("Both saved user and target user is null");
        }
        isMyUser = user == null || (myUser != null && user.equals(myUser));
        if (isMyUser) {
            targetUser = myUser;
            data = dao.getReceivedEventsByUser(targetUser);
        } else {
            targetUser = user;
            data = dao.getEventsByActor(targetUser);
        }
    }

    public Flowable<Resource<Pageable<Event>>> getEvents(int page) {
        return RestHelper.createRemoteSiourceMapper(page == 1,
                isMyUser ? userRestService.getReceivedEvents(targetUser, page) :
                        userRestService.getUserEvents(targetUser, page), (events, isRefresh) -> {
            if (isRefresh) {
                if (isMyUser) {
                    dao.deleteAllUserReceivedEvents(targetUser);
                } else {
                    dao.deleteAllEventsByActor(targetUser);
                }
            }
            if (isMyUser) {
                for (Event event : events.getItems()) {
                    event.setReceivedOwner(targetUser);
                }
            }
            dao.addAll(events.getItems());
        });
    }
}
