package com.duyp.architecture.mvvm.data.model;

import android.support.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Commit extends RealmObject{

    @SerializedName("sha")
    @Expose
    private String sha;
    @SerializedName("commit")
    @Expose
    private CommitDetail commitDetail;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("html_url")
    @Expose
    private String htmlUrl;
    @SerializedName("comments_url")
    @Expose
    private String commentsUrl;
    @SerializedName("committer")
    @Expose
    private User committer;
    @SerializedName("author")
    @Expose
    private User author;
    @SerializedName("ref")
    @Expose
    String ref;

    @Nullable
    public User getUser() {
        return committer != null ? committer : author;
    }
}