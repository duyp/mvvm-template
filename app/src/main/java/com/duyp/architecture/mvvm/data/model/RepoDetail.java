package com.duyp.architecture.mvvm.data.model;

import android.support.annotation.Nullable;

import com.duyp.androidutils.FileUtils;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
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
public class RepoDetail extends RealmObject {

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

    RepoPermissionsModel permissions;

    RealmList<RealmString> topics;

    User organization;
    Repo parent;
    Repo source;
    LicenseModel license;
    @SerializedName("subscribers_count") int subsCount;

    int networkCount;
    String starredUser;
    String reposOwner;

    public String getHumanReadableSize() {
        return FileUtils.getHumanReadableByteCount(size * 1000, false);
    }
}
