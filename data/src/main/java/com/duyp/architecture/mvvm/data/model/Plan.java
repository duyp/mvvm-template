package com.duyp.architecture.mvvm.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import io.realm.RealmObject;
import lombok.Getter;
import lombok.Setter;

@Parcel
@Getter
@Setter
public class Plan extends RealmObject{
    public Long id;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("space")
    @Expose
    public long space;
    @SerializedName("collaborators")
    @Expose
    public long collaborators;
    @SerializedName("private_repos")
    @Expose
    public long privateRepos;
}