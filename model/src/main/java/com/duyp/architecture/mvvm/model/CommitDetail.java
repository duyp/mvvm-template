package com.duyp.architecture.mvvm.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommitDetail extends RealmObject{

//    @SerializedName("author")
//    @Expose
//    private Author author;
    @SerializedName("committer")
    @Expose
    private Author committer;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("comment_count")
    @Expose
    private long commentCount;
}