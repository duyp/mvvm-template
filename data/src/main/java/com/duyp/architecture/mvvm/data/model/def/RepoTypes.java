package com.duyp.architecture.mvvm.data.model.def;

import android.support.annotation.StringDef;

/**
 * Created by duypham on 9/16/17.
 * Definitions for github repositories types
 * https://developer.github.com/v3/repos/#list-user-repositories
 */

@StringDef({RepoTypes.ALL, RepoTypes.OWNER, RepoTypes.MEMBER})
public @interface RepoTypes {
    String ALL = "all";
    String OWNER = "owner";
    String MEMBER = "member";
}