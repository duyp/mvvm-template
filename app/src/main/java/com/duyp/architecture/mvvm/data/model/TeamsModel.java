package com.duyp.architecture.mvvm.data.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by Kosh on 03 Apr 2017, 7:40 PM
 */

@Getter
@Setter
@NoArgsConstructor
public class TeamsModel extends RealmObject{
    @PrimaryKey
    private long id;
    private String url;
    private String name;
    private String slug;
    private String description;
    private String privacy;
    private String permission;
    private String membersUrl;
    private String repositoriesUrl;
}
