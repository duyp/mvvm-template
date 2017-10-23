package com.duyp.architecture.mvvm.data.model;

import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by duypham on 10/24/17.
 *
 */

public class Event extends RealmObject {
    @PrimaryKey
    long id;
    // EventsType type;
    String type; // TODO: 10/24/17 event type
    Date createdAt;
    User actor;
    Repo repo;
    PayloadModel payload;

    @SerializedName("public")
    boolean publicEvent;

    @Nullable String login;
}
