package com.duyp.architecture.mvvm.data.model;

import android.support.annotation.Nullable;

import com.duyp.architecture.mvvm.data.model.type.EventType;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import lombok.Getter;

/**
 * Created by duypham on 10/24/17.
 *
 */

@Getter
public class Event extends RealmObject {
    @PrimaryKey
    long id;
    // EventsType type;
    @EventType String type; // TODO: 10/24/17 event type

    @SerializedName("created_at")
    Date createdAt;

    User actor;
    Repo repo;
    PayloadModel payload;

    @SerializedName("public")
    boolean publicEvent;

    @Nullable String login;
}
