package com.duyp.architecture.mvvm.data.model;

import com.duyp.architecture.mvvm.data.model.def.IssueStates;
import com.duyp.architecture.mvvm.data.model.type.IssueState;
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
    @PrimaryKey long id;
    String url;
    String body;
    String title;
    int comments;
    int number;
    boolean locked;
    @IssueState String state; //IssueState2
    String repoUrl;
    String bodyHtml;
    String htmlUrl;
    Date closedAt;
    Date createdAt;
    Date updatedAt;
    String repoName;
    String login;
    User user;
    User assignee;
    RealmList<User> assignees;
    RealmList<Label> labels;
    MilestoneModel milestone;
    Repo repository;
    PullRequest pullRequest;
    User closedBy;
    ReactionsModel reactions;
}
