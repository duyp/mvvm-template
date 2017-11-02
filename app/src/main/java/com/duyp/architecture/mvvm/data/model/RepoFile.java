package com.duyp.architecture.mvvm.data.model;

import com.duyp.architecture.mvvm.data.model.type.FilesType;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by duypham on 10/21/17.
 *
 */

@Setter
@Getter
public class RepoFile {
    @PrimaryKey
    long id;
    String name;
    String path;
    String sha;
    long size;
    String url;
    String htmlUrl;
    String gitUrl;
    String downloadUrl;
    FilesType type; // todo file type icon
    String repoId;
    String login;
}
