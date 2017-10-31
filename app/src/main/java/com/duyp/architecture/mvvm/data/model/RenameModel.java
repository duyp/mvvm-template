package com.duyp.architecture.mvvm.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by Kosh on 13 Dec 2016, 12:33 AM
 */

@Getter
@Setter
public class RenameModel extends RealmObject implements Parcelable {
    @SerializedName("from") String fromValue;
    @SerializedName("to") String toValue;

    public RenameModel() {}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.fromValue);
        dest.writeString(this.toValue);
    }

    protected RenameModel(Parcel in) {
        this.fromValue = in.readString();
        this.toValue = in.readString();
    }

    public static final Parcelable.Creator<RenameModel> CREATOR = new Parcelable.Creator<RenameModel>() {
        @Override
        public RenameModel createFromParcel(Parcel source) {
            return new RenameModel(source);
        }

        @Override
        public RenameModel[] newArray(int size) {
            return new RenameModel[size];
        }
    };
}
