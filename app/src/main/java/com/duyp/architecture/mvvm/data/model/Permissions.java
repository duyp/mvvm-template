package com.duyp.architecture.mvvm.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

/**
 * Created by phamd on 9/14/2017.
 *
 */

public class Permissions  extends RealmObject{
    @SerializedName("admin")
    @Expose
    private Boolean admin;
    @SerializedName("push")
    @Expose
    private Boolean push;
    @SerializedName("pull")
    @Expose
    private Boolean pull;
}
