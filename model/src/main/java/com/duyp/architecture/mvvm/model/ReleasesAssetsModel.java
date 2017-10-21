package com.duyp.architecture.mvvm.model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Kosh on 31 Dec 2016, 1:28 PM
 */

@Getter
@Setter
public class ReleasesAssetsModel extends RealmObject{

    @PrimaryKey
    private long id;

    private String url;
    private String browserDownloadUrl;
    private String name;
    private String label;
    private String state;
    private String contentType;
    private int size;
    private int downloadCount;
    private Date createdAt;
    private Date updatedAt;
    private User uploader;
}
