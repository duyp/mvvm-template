package com.duyp.architecture.mvvm.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import io.realm.RealmObject;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by Kosh on 03 Dec 2016, 11:12 AM
 */

@Getter
@Setter
public class RepoPermissionsModel extends RealmObject implements Parcelable {

    boolean admin;
    boolean push;
    boolean pull;

    public RepoPermissionsModel() {}

    @Override public int describeContents() { return 0; }

    @Override public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.admin ? (byte) 1 : (byte) 0);
        dest.writeByte(this.push ? (byte) 1 : (byte) 0);
        dest.writeByte(this.pull ? (byte) 1 : (byte) 0);
    }

    protected RepoPermissionsModel(Parcel in) {
        this.admin = in.readByte() != 0;
        this.push = in.readByte() != 0;
        this.pull = in.readByte() != 0;
    }

    public static final Creator<RepoPermissionsModel> CREATOR = new Creator<RepoPermissionsModel>() {
        @Override public RepoPermissionsModel createFromParcel(Parcel source) {return new RepoPermissionsModel(source);}

        @Override public RepoPermissionsModel[] newArray(int size) {return new RepoPermissionsModel[size];}
    };
}
