package com.duyp.architecture.mvvm.data.model;

import android.support.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Commit extends RealmObject{

    public String sha;
    public String url;
    public String message;
    public User author;
    public User committer;
    public User tree;
    public @SerializedName("distinct") boolean distincted;
    public RealmList<Commit> parents;
    public int commentCount;
    String ref;

    @Nullable
    public User getUser() {
        return committer != null ? committer : author;
    }
}