package com.duyp.architecture.mvvm.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import lombok.Getter;

/**
 * Created by duypham on 10/21/17.
 *
 */

@Getter
public class Comment extends RealmObject implements Parcelable {

    @PrimaryKey
    long id;
    User user;
    String url;
    String body;
    String bodyHtml;
    String htmlUrl;
    Date createdAt;
    Date updatedAt;
    int position;
    int line;
    String path;
    String commitId;
    String repoId;
    String login;
    String gistId;
    String issueId;
    String pullRequestId;
    ReactionsModel reactions;
    String authorAssociation;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeParcelable(this.user, flags);
        dest.writeString(this.url);
        dest.writeString(this.body);
        dest.writeString(this.bodyHtml);
        dest.writeString(this.htmlUrl);
        dest.writeLong(this.createdAt != null ? this.createdAt.getTime() : -1);
        dest.writeLong(this.updatedAt != null ? this.updatedAt.getTime() : -1);
        dest.writeInt(this.position);
        dest.writeInt(this.line);
        dest.writeString(this.path);
        dest.writeString(this.commitId);
        dest.writeString(this.repoId);
        dest.writeString(this.login);
        dest.writeString(this.gistId);
        dest.writeString(this.issueId);
        dest.writeString(this.pullRequestId);
        dest.writeParcelable(this.reactions, flags);
        dest.writeString(this.authorAssociation);
    }

    public Comment() {
    }

    protected Comment(Parcel in) {
        this.id = in.readLong();
        this.user = in.readParcelable(User.class.getClassLoader());
        this.url = in.readString();
        this.body = in.readString();
        this.bodyHtml = in.readString();
        this.htmlUrl = in.readString();
        long tmpCreatedAt = in.readLong();
        this.createdAt = tmpCreatedAt == -1 ? null : new Date(tmpCreatedAt);
        long tmpUpdatedAt = in.readLong();
        this.updatedAt = tmpUpdatedAt == -1 ? null : new Date(tmpUpdatedAt);
        this.position = in.readInt();
        this.line = in.readInt();
        this.path = in.readString();
        this.commitId = in.readString();
        this.repoId = in.readString();
        this.login = in.readString();
        this.gistId = in.readString();
        this.issueId = in.readString();
        this.pullRequestId = in.readString();
        this.reactions = in.readParcelable(ReactionsModel.class.getClassLoader());
        this.authorAssociation = in.readString();
    }

    public static final Parcelable.Creator<Comment> CREATOR = new Parcelable.Creator<Comment>() {
        @Override
        public Comment createFromParcel(Parcel source) {
            return new Comment(source);
        }

        @Override
        public Comment[] newArray(int size) {
            return new Comment[size];
        }
    };
}
