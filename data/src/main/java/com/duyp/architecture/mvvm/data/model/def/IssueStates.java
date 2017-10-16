package com.duyp.architecture.mvvm.data.model.def;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by duypham on 9/17/17.
 *
 */

@Retention(RetentionPolicy.SOURCE)
@StringDef({IssueStates.OPEN, IssueStates.CLOSED, IssueStates.ALL})
public @interface IssueStates {
    String OPEN = "open";
    String CLOSED = "closed";
    String ALL = "all";
}
