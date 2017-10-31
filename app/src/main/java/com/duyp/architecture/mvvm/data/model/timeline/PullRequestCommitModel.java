package com.duyp.architecture.mvvm.data.model.timeline;

import android.os.Parcel;
import android.os.Parcelable;

import com.duyp.architecture.mvvm.data.model.Comment;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by kosh on 15/08/2017.
 */

@Getter
@Setter
public class PullRequestCommitModel implements Parcelable {
    private String login;
    private String path;
    private int position;
    private String commitId;
    private List<Comment> comments;
    private int line;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.login);
        dest.writeString(this.path);
        dest.writeInt(this.position);
        dest.writeString(this.commitId);
        dest.writeTypedList(this.comments);
        dest.writeInt(this.line);
    }

    protected PullRequestCommitModel(Parcel in) {
        this.login = in.readString();
        this.path = in.readString();
        this.position = in.readInt();
        this.commitId = in.readString();
        this.comments = in.createTypedArrayList(Comment.CREATOR);
        this.line = in.readInt();
    }

    public static final Creator<PullRequestCommitModel> CREATOR = new Creator<PullRequestCommitModel>() {
        @Override
        public PullRequestCommitModel createFromParcel(Parcel source) {
            return new PullRequestCommitModel(source);
        }

        @Override
        public PullRequestCommitModel[] newArray(int size) {
            return new PullRequestCommitModel[size];
        }
    };
}
