package com.duyp.architecture.mvvm.data.model;

import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Plan extends RealmObject implements Parcelable {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.name);
        dest.writeLong(this.space);
        dest.writeLong(this.collaborators);
        dest.writeLong(this.privateRepos);
    }

    public Plan() {
    }

    protected Plan(android.os.Parcel in) {
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.name = in.readString();
        this.space = in.readLong();
        this.collaborators = in.readLong();
        this.privateRepos = in.readLong();
    }

    public static final Parcelable.Creator<Plan> CREATOR = new Parcelable.Creator<Plan>() {
        @Override
        public Plan createFromParcel(android.os.Parcel source) {
            return new Plan(source);
        }

        @Override
        public Plan[] newArray(int size) {
            return new Plan[size];
        }
    };
}