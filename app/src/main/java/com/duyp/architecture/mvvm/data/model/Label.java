package com.duyp.architecture.mvvm.data.model;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class Label extends RealmObject implements Parcelable {
    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("color")
    @Expose
    private String color;
    @SerializedName("default")
    @Expose
    private Boolean _default;

    public Label() {}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.url);
        dest.writeString(this.name);
        dest.writeString(this.color);
        dest.writeValue(this._default);
    }

    protected Label(Parcel in) {
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.url = in.readString();
        this.name = in.readString();
        this.color = in.readString();
        this._default = (Boolean) in.readValue(Boolean.class.getClassLoader());
    }

    public static final Parcelable.Creator<Label> CREATOR = new Parcelable.Creator<Label>() {
        @Override
        public Label createFromParcel(Parcel source) {
            return new Label(source);
        }

        @Override
        public Label[] newArray(int size) {
            return new Label[size];
        }
    };
}