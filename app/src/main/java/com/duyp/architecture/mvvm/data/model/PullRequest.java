package com.duyp.architecture.mvvm.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.duyp.architecture.mvvm.data.model.type.IssueState;

import java.util.ArrayList;
import java.util.Date;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by duypham on 10/21/17.
 *
 */

@Getter
@Setter
public class PullRequest extends RealmObject implements Parcelable {
    long id;
    String url;
    String body;
    String title;
    int comments;
    int number;
    boolean locked;
    boolean mergable;
    boolean merged;
    boolean mergeable;
    int commits;
    int additions;
    int deletions;
    @IssueState String state; // todo IssueState2
    String bodyHtml;
    String htmlUrl;
    Date closedAt;
    Date createdAt;
    Date updatedAt;
    int changedFiles;
    String diffUrl;
    String patchUrl;
    String mergeCommitSha;
    Date mergedAt;
    String mergeState;
    int reviewComments;
    String repoId;
    String login;
    RealmList<User> assignees;
    User mergedBy;
    User closedBy;
    User user;
    User assignee;
    RealmList<Label> labels;
    MilestoneModel milestone;
    Commit base;
    Commit head;
    PullRequest pullRequest;
    ReactionsModel reactions;

    public PullRequest() {}

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
        dest.writeByte(this.mergable ? (byte) 1 : (byte) 0);
        dest.writeByte(this.merged ? (byte) 1 : (byte) 0);
        dest.writeByte(this.mergeable ? (byte) 1 : (byte) 0);
        dest.writeInt(this.commits);
        dest.writeInt(this.additions);
        dest.writeInt(this.deletions);
        dest.writeString(this.state);
        dest.writeString(this.bodyHtml);
        dest.writeString(this.htmlUrl);
        dest.writeLong(this.closedAt != null ? this.closedAt.getTime() : -1);
        dest.writeLong(this.createdAt != null ? this.createdAt.getTime() : -1);
        dest.writeLong(this.updatedAt != null ? this.updatedAt.getTime() : -1);
        dest.writeInt(this.changedFiles);
        dest.writeString(this.diffUrl);
        dest.writeString(this.patchUrl);
        dest.writeString(this.mergeCommitSha);
        dest.writeLong(this.mergedAt != null ? this.mergedAt.getTime() : -1);
        dest.writeString(this.mergeState);
        dest.writeInt(this.reviewComments);
        dest.writeString(this.repoId);
        dest.writeString(this.login);
        dest.writeTypedList(this.assignees);
        dest.writeParcelable(this.mergedBy, flags);
        dest.writeParcelable(this.closedBy, flags);
        dest.writeParcelable(this.user, flags);
        dest.writeParcelable(this.assignee, flags);
        dest.writeTypedList(this.labels);
        dest.writeParcelable(this.milestone, flags);
        dest.writeParcelable(this.base, flags);
        dest.writeParcelable(this.head, flags);
        dest.writeParcelable(this.pullRequest, flags);
        dest.writeParcelable(this.reactions, flags);
    }

    protected PullRequest(Parcel in) {
        this.id = in.readLong();
        this.url = in.readString();
        this.body = in.readString();
        this.title = in.readString();
        this.comments = in.readInt();
        this.number = in.readInt();
        this.locked = in.readByte() != 0;
        this.mergable = in.readByte() != 0;
        this.merged = in.readByte() != 0;
        this.mergeable = in.readByte() != 0;
        this.commits = in.readInt();
        this.additions = in.readInt();
        this.deletions = in.readInt();
        this.state = in.readString();
        this.bodyHtml = in.readString();
        this.htmlUrl = in.readString();
        long tmpClosedAt = in.readLong();
        this.closedAt = tmpClosedAt == -1 ? null : new Date(tmpClosedAt);
        long tmpCreatedAt = in.readLong();
        this.createdAt = tmpCreatedAt == -1 ? null : new Date(tmpCreatedAt);
        long tmpUpdatedAt = in.readLong();
        this.updatedAt = tmpUpdatedAt == -1 ? null : new Date(tmpUpdatedAt);
        this.changedFiles = in.readInt();
        this.diffUrl = in.readString();
        this.patchUrl = in.readString();
        this.mergeCommitSha = in.readString();
        long tmpMergedAt = in.readLong();
        this.mergedAt = tmpMergedAt == -1 ? null : new Date(tmpMergedAt);
        this.mergeState = in.readString();
        this.reviewComments = in.readInt();
        this.repoId = in.readString();
        this.login = in.readString();

        ArrayList<User> userList = in.createTypedArrayList(User.CREATOR);
        this.assignees = new RealmList<>();
        if (userList != null && !userList.isEmpty()) {
            assignees.addAll(userList);
        }

        this.mergedBy = in.readParcelable(User.class.getClassLoader());
        this.closedBy = in.readParcelable(User.class.getClassLoader());
        this.user = in.readParcelable(User.class.getClassLoader());
        this.assignee = in.readParcelable(User.class.getClassLoader());

        ArrayList<Label> labelList = in.createTypedArrayList(Label.CREATOR);
        this.labels = new RealmList<>();
        if (labelList != null && labelList.size() > 0) {
            this.labels.addAll(labelList);
        }

        this.milestone = in.readParcelable(MilestoneModel.class.getClassLoader());
        this.base = in.readParcelable(Commit.class.getClassLoader());
        this.head = in.readParcelable(Commit.class.getClassLoader());
        this.pullRequest = in.readParcelable(PullRequest.class.getClassLoader());
        this.reactions = in.readParcelable(ReactionsModel.class.getClassLoader());
    }

    public static final Creator<PullRequest> CREATOR = new Creator<PullRequest>() {
        @Override
        public PullRequest createFromParcel(Parcel source) {
            return new PullRequest(source);
        }

        @Override
        public PullRequest[] newArray(int size) {
            return new PullRequest[size];
        }
    };
}
