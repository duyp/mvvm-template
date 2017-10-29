package com.duyp.architecture.mvvm.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by phamd on 9/14/2017.\
 * Github repository model
 */

@Getter
@Setter
public class Repo extends RealmObject implements Parcelable {

    @PrimaryKey
    @SerializedName("id")
    @Expose
    Long id;

    @SerializedName("name")
    @Expose
    String name;
    @SerializedName("full_name")
    @Expose
    String fullName;

    @SerializedName("owner")
    @Expose
    User owner;

    // if current user is member of this repository, save it to database since github api dose not support
    String memberLoginName;

    @SerializedName("private")
    @Expose
    boolean _private;
    @SerializedName("html_url")
    @Expose
    String htmlUrl;
    @SerializedName("description")
    @Expose
    String description;
    @SerializedName("fork")
    @Expose
    boolean fork;
    @SerializedName("url")
    @Expose
    String url;
    @SerializedName("forks_url")
    @Expose
    String forksUrl;
    @SerializedName("keys_url")
    @Expose
    String keysUrl;
    @SerializedName("collaborators_url")
    @Expose
    String collaboratorsUrl;
    @SerializedName("teams_url")
    @Expose
    String teamsUrl;
    @SerializedName("hooks_url")
    @Expose
    String hooksUrl;
    @SerializedName("issue_events_url")
    @Expose
    String issueEventsUrl;
    @SerializedName("events_url")
    @Expose
    String eventsUrl;
    @SerializedName("assignees_url")
    @Expose
    String assigneesUrl;
    @SerializedName("branches_url")
    @Expose
    String branchesUrl;
    @SerializedName("tags_url")
    @Expose
    String tagsUrl;
    @SerializedName("blobs_url")
    @Expose
    String blobsUrl;
    @SerializedName("git_tags_url")
    @Expose
    String gitTagsUrl;
    @SerializedName("git_refs_url")
    @Expose
    String gitRefsUrl;
    @SerializedName("trees_url")
    @Expose
    String treesUrl;
    @SerializedName("statuses_url")
    @Expose
    String statusesUrl;
    @SerializedName("languages_url")
    @Expose
    String languagesUrl;
    @SerializedName("stargazers_url")
    @Expose
    String stargazersUrl;
    @SerializedName("contributors_url")
    @Expose
    String contributorsUrl;
    @SerializedName("subscribers_url")
    @Expose
    String subscribersUrl;
    @SerializedName("subscription_url")
    @Expose
    String subscriptionUrl;
    @SerializedName("commits_url")
    @Expose
    String commitsUrl;
    @SerializedName("git_commits_url")
    @Expose
    String gitCommitsUrl;
    @SerializedName("comments_url")
    @Expose
    String commentsUrl;
    @SerializedName("issue_comment_url")
    @Expose
    String issueCommentUrl;
    @SerializedName("contents_url")
    @Expose
    String contentsUrl;
    @SerializedName("compare_url")
    @Expose
    String compareUrl;
    @SerializedName("merges_url")
    @Expose
    String mergesUrl;
    @SerializedName("archive_url")
    @Expose
    String archiveUrl;
    @SerializedName("downloads_url")
    @Expose
    String downloadsUrl;
    @SerializedName("issues_url")
    @Expose
    String issuesUrl;
    @SerializedName("pulls_url")
    @Expose
    String pullsUrl;
    @SerializedName("milestones_url")
    @Expose
    String milestonesUrl;
    @SerializedName("notifications_url")
    @Expose
    String notificationsUrl;
    @SerializedName("labels_url")
    @Expose
    String labelsUrl;
    @SerializedName("releases_url")
    @Expose
    String releasesUrl;
    @SerializedName("deployments_url")
    @Expose
    String deploymentsUrl;
    @SerializedName("created_at")
    @Expose
    String createdAt;
    @SerializedName("updated_at")
    @Expose
    String updatedAt;
    @SerializedName("pushed_at")
    @Expose
    String pushedAt;
    @SerializedName("git_url")
    @Expose
    String gitUrl;
    @SerializedName("ssh_url")
    @Expose
    String sshUrl;
    @SerializedName("clone_url")
    @Expose
    String cloneUrl;
    @SerializedName("svn_url")
    @Expose
    String svnUrl;
    @SerializedName("homepage")
    @Expose
    String homepage;
    @SerializedName("size")
    @Expose
    long size;
    @SerializedName("stargazers_count")
    @Expose
    long stargazersCount;
    @SerializedName("watchers_count")
    @Expose
    long watchersCount;
    @SerializedName("language")
    @Expose
    String language;
    @SerializedName("has_issues")
    @Expose
    boolean hasIssues;
    @SerializedName("has_projects")
    @Expose
    boolean hasProjects;
    @SerializedName("has_downloads")
    @Expose
    boolean hasDownloads;
    @SerializedName("has_wiki")
    @Expose
    boolean hasWiki;
    @SerializedName("has_pages")
    @Expose
    boolean hasPages;
    @SerializedName("forks_count")
    @Expose
    long forksCount;
    @SerializedName("mirror_url")
    @Expose
    String mirrorUrl;
    @SerializedName("open_issues_count")
    @Expose
    long openIssuesCount;
    @SerializedName("forks")
    @Expose
    long forks;
    @SerializedName("open_issues")
    @Expose
    long openIssues;
    @SerializedName("watchers")
    @Expose
    long watchers;
    @SerializedName("default_branch")
    @Expose
    String defaultBranch;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.name);
        dest.writeString(this.fullName);
        dest.writeParcelable(this.owner, flags);
        dest.writeString(this.memberLoginName);
        dest.writeByte(this._private ? (byte) 1 : (byte) 0);
        dest.writeString(this.htmlUrl);
        dest.writeString(this.description);
        dest.writeByte(this.fork ? (byte) 1 : (byte) 0);
        dest.writeString(this.url);
        dest.writeString(this.forksUrl);
        dest.writeString(this.keysUrl);
        dest.writeString(this.collaboratorsUrl);
        dest.writeString(this.teamsUrl);
        dest.writeString(this.hooksUrl);
        dest.writeString(this.issueEventsUrl);
        dest.writeString(this.eventsUrl);
        dest.writeString(this.assigneesUrl);
        dest.writeString(this.branchesUrl);
        dest.writeString(this.tagsUrl);
        dest.writeString(this.blobsUrl);
        dest.writeString(this.gitTagsUrl);
        dest.writeString(this.gitRefsUrl);
        dest.writeString(this.treesUrl);
        dest.writeString(this.statusesUrl);
        dest.writeString(this.languagesUrl);
        dest.writeString(this.stargazersUrl);
        dest.writeString(this.contributorsUrl);
        dest.writeString(this.subscribersUrl);
        dest.writeString(this.subscriptionUrl);
        dest.writeString(this.commitsUrl);
        dest.writeString(this.gitCommitsUrl);
        dest.writeString(this.commentsUrl);
        dest.writeString(this.issueCommentUrl);
        dest.writeString(this.contentsUrl);
        dest.writeString(this.compareUrl);
        dest.writeString(this.mergesUrl);
        dest.writeString(this.archiveUrl);
        dest.writeString(this.downloadsUrl);
        dest.writeString(this.issuesUrl);
        dest.writeString(this.pullsUrl);
        dest.writeString(this.milestonesUrl);
        dest.writeString(this.notificationsUrl);
        dest.writeString(this.labelsUrl);
        dest.writeString(this.releasesUrl);
        dest.writeString(this.deploymentsUrl);
        dest.writeString(this.createdAt);
        dest.writeString(this.updatedAt);
        dest.writeString(this.pushedAt);
        dest.writeString(this.gitUrl);
        dest.writeString(this.sshUrl);
        dest.writeString(this.cloneUrl);
        dest.writeString(this.svnUrl);
        dest.writeString(this.homepage);
        dest.writeLong(this.size);
        dest.writeLong(this.stargazersCount);
        dest.writeLong(this.watchersCount);
        dest.writeString(this.language);
        dest.writeByte(this.hasIssues ? (byte) 1 : (byte) 0);
        dest.writeByte(this.hasProjects ? (byte) 1 : (byte) 0);
        dest.writeByte(this.hasDownloads ? (byte) 1 : (byte) 0);
        dest.writeByte(this.hasWiki ? (byte) 1 : (byte) 0);
        dest.writeByte(this.hasPages ? (byte) 1 : (byte) 0);
        dest.writeLong(this.forksCount);
        dest.writeString(this.mirrorUrl);
        dest.writeLong(this.openIssuesCount);
        dest.writeLong(this.forks);
        dest.writeLong(this.openIssues);
        dest.writeLong(this.watchers);
        dest.writeString(this.defaultBranch);
    }

    public Repo() {
    }

    protected Repo(Parcel in) {
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.name = in.readString();
        this.fullName = in.readString();
        this.owner = in.readParcelable(User.class.getClassLoader());
        this.memberLoginName = in.readString();
        this._private = in.readByte() != 0;
        this.htmlUrl = in.readString();
        this.description = in.readString();
        this.fork = in.readByte() != 0;
        this.url = in.readString();
        this.forksUrl = in.readString();
        this.keysUrl = in.readString();
        this.collaboratorsUrl = in.readString();
        this.teamsUrl = in.readString();
        this.hooksUrl = in.readString();
        this.issueEventsUrl = in.readString();
        this.eventsUrl = in.readString();
        this.assigneesUrl = in.readString();
        this.branchesUrl = in.readString();
        this.tagsUrl = in.readString();
        this.blobsUrl = in.readString();
        this.gitTagsUrl = in.readString();
        this.gitRefsUrl = in.readString();
        this.treesUrl = in.readString();
        this.statusesUrl = in.readString();
        this.languagesUrl = in.readString();
        this.stargazersUrl = in.readString();
        this.contributorsUrl = in.readString();
        this.subscribersUrl = in.readString();
        this.subscriptionUrl = in.readString();
        this.commitsUrl = in.readString();
        this.gitCommitsUrl = in.readString();
        this.commentsUrl = in.readString();
        this.issueCommentUrl = in.readString();
        this.contentsUrl = in.readString();
        this.compareUrl = in.readString();
        this.mergesUrl = in.readString();
        this.archiveUrl = in.readString();
        this.downloadsUrl = in.readString();
        this.issuesUrl = in.readString();
        this.pullsUrl = in.readString();
        this.milestonesUrl = in.readString();
        this.notificationsUrl = in.readString();
        this.labelsUrl = in.readString();
        this.releasesUrl = in.readString();
        this.deploymentsUrl = in.readString();
        this.createdAt = in.readString();
        this.updatedAt = in.readString();
        this.pushedAt = in.readString();
        this.gitUrl = in.readString();
        this.sshUrl = in.readString();
        this.cloneUrl = in.readString();
        this.svnUrl = in.readString();
        this.homepage = in.readString();
        this.size = in.readLong();
        this.stargazersCount = in.readLong();
        this.watchersCount = in.readLong();
        this.language = in.readString();
        this.hasIssues = in.readByte() != 0;
        this.hasProjects = in.readByte() != 0;
        this.hasDownloads = in.readByte() != 0;
        this.hasWiki = in.readByte() != 0;
        this.hasPages = in.readByte() != 0;
        this.forksCount = in.readLong();
        this.mirrorUrl = in.readString();
        this.openIssuesCount = in.readLong();
        this.forks = in.readLong();
        this.openIssues = in.readLong();
        this.watchers = in.readLong();
        this.defaultBranch = in.readString();
    }

    public static final Parcelable.Creator<Repo> CREATOR = new Parcelable.Creator<Repo>() {
        @Override
        public Repo createFromParcel(Parcel source) {
            return new Repo(source);
        }

        @Override
        public Repo[] newArray(int size) {
            return new Repo[size];
        }
    };
}
