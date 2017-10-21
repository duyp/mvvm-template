package com.duyp.architecture.mvvm.model;

import java.util.Date;
import java.util.List;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by duypham on 10/21/17.
 *
 */

public class IssueEvent extends RealmObject{

    @PrimaryKey
    long id;
    String url;
    String event; // todo IssueEventType
    User actor;
    User assigner;
    User assignee;
    User requestedReviewer;
    TeamsModel requestedTeam;
    MilestoneModel milestone;
    RenameModel rename;
    Issue source;
    Label label;
    String commitId;
    String commitUrl;
    Date createdAt;
    String issueId;
    String repoId;
    String login;

    transient List<Label> labels;
    transient Issue issue;

}
