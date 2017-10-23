package com.duyp.architecture.mvvm.data.model;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * Created by Kosh on 08 Feb 2017, 10:03 PM
 */


@Getter
@Setter
@NoArgsConstructor
public class PayloadModel extends RealmObject {

    public String action;
    public Repo forkee;
    public Issue issue;
    public PullRequest pullRequest;
    public String refType;
    public Comment comment;
    public User target;
    public User member;
    public TeamsModel team;
    public Comment commitComment;
    public String description;
    public ReleasesAssetsModel download;
    public Gist gist;
    public RealmList<WikiModel> pages;
    public String before;
    public String head;
    public String ref;
    public int size;
    public RealmList<Commit> commits;
    public User user;
    public Release release;
    public User blockedUser;
    public User organization;
    public User invitation;
}
