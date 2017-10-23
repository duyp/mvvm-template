package com.duyp.architecture.mvvm.data.local;

import android.support.annotation.Nullable;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by duypham on 10/24/17.
 *
 */

public class Login extends RealmObject{
    @PrimaryKey
    long id;
    String login;
    String avatarUrl;
    String gravatarId;
    String url;
    String htmlUrl;
    String followersUrl;
    String followingUrl;
    String gistsUrl;
    String starredUrl;
    String subscriptionsUrl;
    String organizationsUrl;
    String reposUrl;
    String eventsUrl;
    String receivedEventsUrl;
    String type;
    boolean siteAdmin;
    String name;
    String company;
    String blog;
    String location;
    String email;
    boolean hireable;
    String bio;
    long publicRepos;
    long publicGists;
    long followers;
    long following;
    Date createdAt;
    Date updatedAt;
    String token;
    int contributions;
    @Nullable Boolean isLoggedIn;
    @Nullable Boolean isEnterprise;
    @Nullable String otpCode;
    @Nullable String enterpriseUrl;
}
