package com.duyp.architecture.mvvm.data.model;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by Kosh on 03 Mar 2017, 9:08 PM
 */

@Getter
@Setter
@NoArgsConstructor
public class BranchesModel extends RealmObject {

    public String name;
    public Commit commit;
    @SerializedName("protected")
    public boolean protectedBranch;
    public String protectionUrl;
    public boolean isTag;

    @Override public String toString() {
        return name;
    }
}
