package com.duyp.architecture.mvvm.model;

import java.util.Date;

import io.realm.RealmObject;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by Kosh on 08 Dec 2016, 8:47 PM
 */

@Getter
@Setter
@NoArgsConstructor
public class MilestoneModel extends RealmObject {

    long id;
    String url;
    String title;
    String state;
    String description;
    int number;
    User creator;
    String htmlUr;
    int openIssues;
    int closedIssues;
    Date createdAt;
    Date updatedAt;
    Date closedAt;
    Date dueOn;
}
