package com.duyp.architecture.mvvm.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by duypham on 10/21/17.
 *
 */

public class Release extends RealmObject {
    @PrimaryKey
    long id;
    String url;
    String htmlUrl;
    String assetsUrl;
    String uploadUrl;
    String tagName;
    String targetCommitish;
    String name;
    boolean draft;
    boolean preRelease;
    Date createdAt;
    Date publishedAt;
    String repoId;
    String login;
    @SerializedName("tarball_url")
    String tarballUrl;
    @SerializedName("body_html")
    String body;
    @SerializedName("zipball_url")
    String zipBallUrl;
    User author;
    RealmList<ReleasesAssetsModel> assets;
}
