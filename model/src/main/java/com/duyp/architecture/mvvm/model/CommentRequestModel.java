package com.duyp.architecture.mvvm.model;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Kosh on 20 Nov 2016, 10:40 AM
 */

@Getter
@Setter
public class CommentRequestModel {

    public String body;
    @SerializedName("in_reply_to")

    public Long inReplyTo;

    public String path;

    public Integer position;

    public Integer line;

    public CommentRequestModel() {}

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CommentRequestModel that = (CommentRequestModel) o;
        return (path != null ? path.equals(that.path) : that.path == null) &&
                (position != null ? position.equals(that.position) : that.position == null);
    }

    @Override public int hashCode() {
        int result = path != null ? path.hashCode() : 0;
        result = 31 * result + (position != null ? position.hashCode() : 0);
        return result;
    }
}
