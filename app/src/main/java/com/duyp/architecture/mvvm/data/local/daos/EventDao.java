package com.duyp.architecture.mvvm.data.local.daos;

import android.support.annotation.Nullable;

import com.duyp.androidutils.realm.BaseRealmDaoImpl;
import com.duyp.androidutils.realm.LiveRealmResults;
import com.duyp.architecture.mvvm.data.model.Event;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;

/**
 * Created by duypham on 10/24/17.
 *
 */

public class EventDao extends BaseRealmDaoImpl<Event> {

    @Inject
    public EventDao(RealmConfiguration config) {
        super(Realm.getInstance(config), Event.class);
    }


    // ===========================================================================================
    // User's received events (event actor is others)
    // ===========================================================================================
    public LiveRealmResults<Event> getReceivedEventsByUser(String userLoginName) {
        return findAllSorted(getUserReceivedEventQuery(userLoginName));
    }

    public void deleteAllUserReceivedEvents(String userLoginName) {
        deleteAll(getUserReceivedEventQuery(userLoginName));
    }

    private RealmQuery<Event> getUserReceivedEventQuery(String userLoginName) {
        return query().equalTo("receivedOwner", userLoginName);
    }

    // ===========================================================================================
    // User events (event actor is user)
    // ===========================================================================================

    public LiveRealmResults<Event> getEventsByActor(String actorLoginName) {
        return findAllSorted(getEventsByActorQuery(actorLoginName));
    }

    public void deleteAllEventsByActor(String actorLoginName) {
        deleteAll(getEventsByActorQuery(actorLoginName));
    }

    private RealmQuery<Event> getEventsByActorQuery(String actorLoginName) {
        return query().equalTo("actor.login", actorLoginName);
    }

}
