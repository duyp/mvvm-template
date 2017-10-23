package com.duyp.architecture.mvvm.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by duypham on 10/24/17.
 *
 */

public class Gist extends RealmObject{

    @PrimaryKey
    @SerializedName("nooope")
    long id;

    String url;
    String forksUrl;
    String commitsUrl;
    String gitPullUrl;
    String gitPushUrl;
    String htmlUrl;
    boolean publicX;
    Date createdAt;
    Date updatedAt;
    String description;
    int comments;
    String commentsUrl;
    boolean truncated;
    String ownerName;

    @SerializedName("id")
    String gistId;

//    GithubFileModel files;
    User user;
    User owner;
}
