package com.duyp.architecture.mvvm.data.model;

import android.support.annotation.Nullable;

import com.duyp.architecture.mvvm.data.model.type.EventType;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by duypham on 10/24/17.
 *
 */

@Setter
@Getter
public class Event extends RealmObject {
    @PrimaryKey
    long id;
    @EventType String type;

    @SerializedName("created_at")
    Date createdAt;

    User actor;
    Repo repo;
    PayloadModel payload;

    @SerializedName("public")
    boolean publicEvent;

    // not a response field from github api, used to identify this event is belong to whom (user received events)
    @Nullable String receivedOwner;
}
