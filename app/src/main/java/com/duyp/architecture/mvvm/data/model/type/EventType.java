package com.duyp.architecture.mvvm.data.model.type;

import android.support.annotation.StringDef;

import com.duyp.architecture.mvvm.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import static com.duyp.architecture.mvvm.data.model.type.EventType.*;

/**
 * Created by duypham on 10/24/17.
 *
 */
@StringDef({WatchEvent, CreateEvent, CommitCommentEvent, DownloadEvent, FollowEvent, ForkEvent, GistEvent,
            GollumEvent, IssueCommentEvent, IssuesEvent, MemberEvent, PublicEvent, PullRequestEvent, PullRequestReviewCommentEvent,
            PullRequestReviewEvent, RepositoryEvent, PushEvent, TeamAddEvent, DeleteEvent, ReleaseEvent,
            ForkApplyEvent, OrgBlockEvent, ProjectCardEvent, ProjectColumnEvent, OrganizationEvent, ProjectEvent})
@Retention(RetentionPolicy.SOURCE)
public @interface EventType {
    String WatchEvent = "WatchEvent";
    String CreateEvent = "CreateEvent";
    String CommitCommentEvent = "CommitCommentEvent";
    String DownloadEvent = "DownloadEvent";
    String FollowEvent = "FollowEvent";
    String ForkEvent = "ForkEvent";
    String GistEvent = "GistEvent";
    String GollumEvent = "GollumEvent";
    String IssueCommentEvent = "IssueCommentEvent";
    String IssuesEvent = "IssuesEvent";
    String MemberEvent = "MemberEvent";
    String PublicEvent = "PublicEvent";
    String PullRequestEvent = "PullRequestEvent";
    String PullRequestReviewCommentEvent = "PullRequestReviewCommentEvent";
    String PullRequestReviewEvent = "PullRequestReviewEvent";
    String RepositoryEvent = "RepositoryEvent";
    String PushEvent = "PushEvent";
    String TeamAddEvent = "TeamAddEvent";
    String DeleteEvent = "DeleteEvent";
    String ReleaseEvent = "ReleaseEvent";
    String ForkApplyEvent = "ForkApplyEvent";
    String OrgBlockEvent = "OrgBlockEvent";
    String ProjectCardEvent = "ProjectCardEvent";
    String ProjectColumnEvent = "ProjectColumnEvent";
    String OrganizationEvent = "OrganizationEvent";
    String ProjectEvent = "ProjectEvent";
}