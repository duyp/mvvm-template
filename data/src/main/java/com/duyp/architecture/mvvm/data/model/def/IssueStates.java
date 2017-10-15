package com.duyp.architecture.mvvm.data.model.def;

import android.support.annotation.StringDef;

/**
 * Created by duypham on 9/17/17.
 *
 */

@StringDef({IssueStates.OPEN, IssueStates.CLOSED, IssueStates.ALL})
public @interface IssueStates {
    String OPEN = "open";
    String CLOSED = "closed";
    String ALL = "all";
}
