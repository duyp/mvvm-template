package com.duyp.architecture.mvvm.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.duyp.architecture.mvvm.data.model.def.IssueStates;
import com.duyp.architecture.mvvm.data.model.type.IssueState;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by duypham on 9/17/17.
 *
 */


@Getter
@Setter
public class Issue extends RealmObject implements Parcelable {
    @PrimaryKey long id;
    String url;
    String body;
    String title;
    int comments;
    int number;
    boolean locked;
    @IssueState String state; //IssueState2
    String repoUrl;
    String bodyHtml;
    String htmlUrl;
    Date closedAt;
    Date createdAt;
    Date updatedAt;
    String repoName;
    String login;
    User user;
    User assignee;
    RealmList<User> assignees;
    RealmList<Label> labels;
    MilestoneModel milestone;
    Repo repository;
    PullRequest pullRequest;
    User closedBy;
    ReactionsModel reactions;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.url);
        dest.writeString(this.body);
        dest.writeString(this.title);
        dest.writeInt(this.comments);
        dest.writeInt(this.number);
        dest.writeByte(this.locked ? (byte) 1 : (byte) 0);
        dest.writeString(this.state);
        dest.writeString(this.repoUrl);
        dest.writeString(this.bodyHtml);
        dest.writeString(this.htmlUrl);
        dest.writeLong(this.closedAt != null ? this.closedAt.getTime() : -1);
        dest.writeLong(this.createdAt != null ? this.createdAt.getTime() : -1);
        dest.writeLong(this.updatedAt != null ? this.updatedAt.getTime() : -1);
        dest.writeString(this.repoName);
        dest.writeString(this.login);
        dest.writeParcelable(this.user, flags);
        dest.writeParcelable(this.assignee, flags);
        dest.writeTypedList(this.assignees);
        dest.writeTypedList(this.labels);
        dest.writeParcelable(this.milestone, flags);
        dest.writeParcelable(this.repository, flags);
        dest.writeParcelable(this.pullRequest, flags);
        dest.writeParcelable(this.closedBy, flags);
        dest.writeParcelable(this.reactions, flags);
    }

    public Issue() {
    }

    protected Issue(Parcel in) {
        this.id = in.readLong();
        this.url = in.readString();
        this.body = in.readString();
        this.title = in.readString();
        this.comments = in.readInt();
        this.number = in.readInt();
        this.locked = in.readByte() != 0;
        this.state = in.readString();
        this.repoUrl = in.readString();
        this.bodyHtml = in.readString();
        this.htmlUrl = in.readString();
        long tmpClosedAt = in.readLong();
        this.closedAt = tmpClosedAt == -1 ? null : new Date(tmpClosedAt);
        long tmpCreatedAt = in.readLong();
        this.createdAt = tmpCreatedAt == -1 ? null : new Date(tmpCreatedAt);
        long tmpUpdatedAt = in.readLong();
        this.updatedAt = tmpUpdatedAt == -1 ? null : new Date(tmpUpdatedAt);
        this.repoName = in.readString();
        this.login = in.readString();
        this.user = in.readParcelable(User.class.getClassLoader());
        this.assignee = in.readParcelable(User.class.getClassLoader());
        this.assignees = new RealmList<>(); assignees.addAll(in.createTypedArrayList(User.CREATOR));
        this.labels = new RealmList<>(); labels.addAll(in.createTypedArrayList(Label.CREATOR));
        this.milestone = in.readParcelable(MilestoneModel.class.getClassLoader());
        this.repository = in.readParcelable(Repo.class.getClassLoader());
        this.pullRequest = in.readParcelable(PullRequest.class.getClassLoader());
        this.closedBy = in.readParcelable(User.class.getClassLoader());
        this.reactions = in.readParcelable(ReactionsModel.class.getClassLoader());
    }

    public static final Creator<Issue> CREATOR = new Creator<Issue>() {
        @Override
        public Issue createFromParcel(Parcel source) {
            return new Issue(source);
        }

        @Override
        public Issue[] newArray(int size) {
            return new Issue[size];
        }
    };
}
