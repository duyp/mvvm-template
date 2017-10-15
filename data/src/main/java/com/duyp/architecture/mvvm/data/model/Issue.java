package com.duyp.architecture.mvvm.data.model;

import com.duyp.architecture.mvvm.data.model.def.IssueStates;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by duypham on 9/17/17.
 *
 */


@Getter
@Setter
public class Issue extends RealmObject{

    @PrimaryKey
    @SerializedName("id")
    @Expose
    Long id;

    Long repoId;

    @SerializedName("url")
    @Expose
    String url;
    @SerializedName("repository_url")
    @Expose
    String repositoryUrl;
    @SerializedName("labels_url")
    @Expose
    String labelsUrl;
    @SerializedName("comments_url")
    @Expose
    String commentsUrl;
    @SerializedName("events_url")
    @Expose
    String eventsUrl;
    @SerializedName("html_url")
    @Expose
    String htmlUrl;
    @SerializedName("number")
    @Expose
    long number;
    @SerializedName("title")
    @Expose
    String title;
    @SerializedName("user")
    @Expose
    User user;
    @SerializedName("labels")
    @Expose
    RealmList<Label> labels = null;
    @SerializedName("state")
    @Expose
    @IssueStates
    String state;
    @SerializedName("locked")
    @Expose
    Boolean locked;
    @SerializedName("assignee")
    @Expose
    User assignee;
//    @SerializedName("assignees")
//    @Expose
//    List<User> assignees = null;
//    @SerializedName("milestone")
//    @Expose
//    Object milestone;
    @SerializedName("comments")
    @Expose
    long comments;
    @SerializedName("created_at")
    @Expose
    Date createdAt;
    @SerializedName("updated_at")
    @Expose
    Date updatedAt;
    @SerializedName("closed_at")
    @Expose
    Date closedAt;
    @SerializedName("author_association")
    @Expose
    String authorAssociation;
    @SerializedName("body")
    @Expose
    String body;
}
