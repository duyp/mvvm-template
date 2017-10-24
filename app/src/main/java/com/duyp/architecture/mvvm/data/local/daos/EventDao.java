package com.duyp.architecture.mvvm.data.local.daos;

import android.support.annotation.Nullable;

import com.duyp.androidutils.realm.BaseRealmDaoImpl;
import com.duyp.architecture.mvvm.data.model.Event;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by duypham on 10/24/17.
 *
 */

public class EventDao extends BaseRealmDaoImpl<Event> {

    @Inject
    public EventDao(RealmConfiguration config) {
        super(Realm.getInstance(config), Event.class);
    }

    @Nullable
    @Override
    protected String getDefaultSortField() {
        return "createdAt";
    }
}
