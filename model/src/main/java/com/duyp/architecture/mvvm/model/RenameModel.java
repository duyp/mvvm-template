package com.duyp.architecture.mvvm.model;

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
@NoArgsConstructor
public class RenameModel extends RealmObject {
    @SerializedName("from") String fromValue;
    @SerializedName("to") String toValue;
}
