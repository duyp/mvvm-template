package com.duyp.architecture.mvvm.data.model;

import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by duypham on 9/7/17.
 * Github User model
 */

@Getter
@Setter
public class User extends RealmObject implements Parcelable {

    @PrimaryKey
    @SerializedName("id")
    @Expose
    public Long id;

    @SerializedName("login")
    @Expose
    public String login;
    @SerializedName("avatar_url")
    @Expose
    public String avatarUrl;
    @SerializedName("gravatar_id")
    @Expose
    public String gravatarId;
    @SerializedName("url")
    @Expose
    public String url;
    @SerializedName("html_url")
    @Expose
    public String htmlUrl;
    @SerializedName("followers_url")
    @Expose
    public String followersUrl;
    @SerializedName("following_url")
    @Expose
    public String followingUrl;
    @SerializedName("gists_url")
    @Expose
    public String gistsUrl;
    @SerializedName("starred_url")
    @Expose
    public String starredUrl;
    @SerializedName("subscriptions_url")
    @Expose
    public String subscriptionsUrl;
    @SerializedName("organizations_url")
    @Expose
    public String organizationsUrl;
    @SerializedName("repos_url")
    @Expose
    public String reposUrl;
    @SerializedName("events_url")
    @Expose
    public String eventsUrl;
    @SerializedName("received_events_url")
    @Expose
    public String receivedEventsUrl;
    @SerializedName("type")
    @Expose
    public String type;
    @SerializedName("site_admin")
    @Expose
    public Boolean siteAdmin;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("company")
    @Expose
    public String company;
    @SerializedName("blog")
    @Expose
    public String blog;
    @SerializedName("location")
    @Expose
    public String location;
    @SerializedName("email")
    @Expose
    public String email;
    @SerializedName("hireable")
    @Expose
    public Boolean hireable;
    @SerializedName("bio")
    @Expose
    public String bio;
    @SerializedName("public_repos")
    @Expose
    public long publicRepos;
    @SerializedName("public_gists")
    @Expose
    public long publicGists;
    @SerializedName("followers")
    @Expose
    public long followers;
    @SerializedName("following")
    @Expose
    public long following;
    @SerializedName("created_at")
    @Expose
    public Date createdAt;
    @SerializedName("updated_at")
    @Expose
    public Date updatedAt;
    @SerializedName("private_gists")
    @Expose
    public long privateGists;
    @SerializedName("total_private_repos")
    @Expose
    public long totalPrivateRepos;
    @SerializedName("owned_private_repos")
    @Expose
    public long ownedPrivateRepos;
    @SerializedName("disk_usage")
    @Expose
    public long diskUsage;
    @SerializedName("collaborators")
    @Expose
    public long collaborators;
    @SerializedName("two_factor_authentication")
    @Expose
    public Boolean twoFactorAuthentication;
    @SerializedName("plan")
    @Expose
    public Plan plan;

    public boolean equals(String userLogin) {
        return this.login.equals(userLogin);
    }

    public boolean equals(@Nullable User user) {
        return user != null && id.equals(user.getId());
    }

    public String getDisplayName() {
        return TextUtils.isEmpty(name) ? login : name;
    }

    public boolean isOrganizationType() {
        return type != null && type.equalsIgnoreCase("Organization");
    }

    public User partialClone() {
        User user = new User();
        user.setId(id);
        user.setName(name);
        user.setAvatarUrl(avatarUrl);
        user.setLogin(login);
        user.setBio(bio);
        return user;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.login);
        dest.writeString(this.avatarUrl);
        dest.writeString(this.gravatarId);
        dest.writeString(this.url);
        dest.writeString(this.htmlUrl);
        dest.writeString(this.followersUrl);
        dest.writeString(this.followingUrl);
        dest.writeString(this.gistsUrl);
        dest.writeString(this.starredUrl);
        dest.writeString(this.subscriptionsUrl);
        dest.writeString(this.organizationsUrl);
        dest.writeString(this.reposUrl);
        dest.writeString(this.eventsUrl);
        dest.writeString(this.receivedEventsUrl);
        dest.writeString(this.type);
        dest.writeValue(this.siteAdmin);
        dest.writeString(this.name);
        dest.writeString(this.company);
        dest.writeString(this.blog);
        dest.writeString(this.location);
        dest.writeString(this.email);
        dest.writeValue(this.hireable);
        dest.writeString(this.bio);
        dest.writeLong(this.publicRepos);
        dest.writeLong(this.publicGists);
        dest.writeLong(this.followers);
        dest.writeLong(this.following);
        dest.writeLong(this.createdAt != null ? this.createdAt.getTime() : -1);
        dest.writeLong(this.updatedAt != null ? this.updatedAt.getTime() : -1);
        dest.writeLong(this.privateGists);
        dest.writeLong(this.totalPrivateRepos);
        dest.writeLong(this.ownedPrivateRepos);
        dest.writeLong(this.diskUsage);
        dest.writeLong(this.collaborators);
        dest.writeValue(this.twoFactorAuthentication);
        dest.writeParcelable(this.plan, flags);
    }

    public User() {
    }

    protected User(android.os.Parcel in) {
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.login = in.readString();
        this.avatarUrl = in.readString();
        this.gravatarId = in.readString();
        this.url = in.readString();
        this.htmlUrl = in.readString();
        this.followersUrl = in.readString();
        this.followingUrl = in.readString();
        this.gistsUrl = in.readString();
        this.starredUrl = in.readString();
        this.subscriptionsUrl = in.readString();
        this.organizationsUrl = in.readString();
        this.reposUrl = in.readString();
        this.eventsUrl = in.readString();
        this.receivedEventsUrl = in.readString();
        this.type = in.readString();
        this.siteAdmin = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.name = in.readString();
        this.company = in.readString();
        this.blog = in.readString();
        this.location = in.readString();
        this.email = in.readString();
        this.hireable = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.bio = in.readString();
        this.publicRepos = in.readLong();
        this.publicGists = in.readLong();
        this.followers = in.readLong();
        this.following = in.readLong();
        long tmpCreatedAt = in.readLong();
        this.createdAt = tmpCreatedAt == -1 ? null : new Date(tmpCreatedAt);
        long tmpUpdatedAt = in.readLong();
        this.updatedAt = tmpUpdatedAt == -1 ? null : new Date(tmpUpdatedAt);
        this.privateGists = in.readLong();
        this.totalPrivateRepos = in.readLong();
        this.ownedPrivateRepos = in.readLong();
        this.diskUsage = in.readLong();
        this.collaborators = in.readLong();
        this.twoFactorAuthentication = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.plan = in.readParcelable(Plan.class.getClassLoader());
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        @Override
        public User createFromParcel(android.os.Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
