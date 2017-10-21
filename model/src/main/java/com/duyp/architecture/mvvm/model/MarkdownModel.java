package com.duyp.architecture.mvvm.model;

import io.realm.RealmObject;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by Kosh on 18 Feb 2017, 7:20 PM
 */

@Getter
@Setter
@NoArgsConstructor
public class MarkdownModel extends RealmObject{
    private String text;
    private String mode = "gfm";
    private String context;
}
