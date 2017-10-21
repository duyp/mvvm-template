package com.duyp.architecture.mvvm.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by duypham on 10/21/17.
 *
 */

public class RepoFile extends RealmObject {
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
    String type; // todo file type icon
    String repoId;
    String login;
}
