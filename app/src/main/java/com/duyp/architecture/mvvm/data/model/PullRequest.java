package com.duyp.architecture.mvvm.data.model;

import java.util.Date;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by duypham on 10/21/17.
 *
 */

@Getter
@Setter
@NoArgsConstructor
public class PullRequest extends RealmObject{
    @PrimaryKey
    long id;
    String url;
    String body;
    String title;
    int comments;
    int number;
    boolean locked;
    boolean mergable;
    boolean merged;
    boolean mergeable;
    int commits;
    int additions;
    int deletions;
    String state; // todo IssueState
    String bodyHtml;
    String htmlUrl;
    Date closedAt;
    Date createdAt;
    Date updatedAt;
    int changedFiles;
    String diffUrl;
    String patchUrl;
    String mergeCommitSha;
    Date mergedAt;
    String mergeState;
    int reviewComments;
    String repoId;
    String login;
    RealmList<User> assignees;
    User mergedBy;
    User closedBy;
    User user;
    User assignee;
    RealmList<Label> labels;
    MilestoneModel milestone;
    Commit base;
    Commit head;
    PullRequest pullRequest;
    ReactionsModel reactions;
}
